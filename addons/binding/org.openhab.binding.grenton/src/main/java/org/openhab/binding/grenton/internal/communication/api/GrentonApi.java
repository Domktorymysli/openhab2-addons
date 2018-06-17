/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * @author tciunelis
 */
package org.openhab.binding.grenton.internal.communication.api;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import org.openhab.binding.grenton.internal.communication.api.command.ApiCommand;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.openhab.binding.grenton.internal.communication.model.Clu;
import org.openhab.binding.grenton.internal.communication.model.MessageDecoded;
import org.openhab.binding.grenton.internal.communication.model.MessageEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonApi implements Api {

    private final Clu clu;
    private final Encoder encoder;
    private final Logger logger = LoggerFactory.getLogger(GrentonApi.class);

    public GrentonApi(Clu clu, Encoder encoder) {
        this.clu = clu;
        this.encoder = encoder;
    }

    @Override
    public Response send(ApiCommand command) throws GrentonEncoderException, GrentonIoException {
        String tmp = replacePlaceholders(command.getCommand());
        try {
            MessageEncoded messageEncoded = encoder.encode(new MessageDecoded(tmp));
            byte[] message = messageEncoded.getMsg();
            InetAddress address = this.clu.getIp();
            int port = this.clu.getPort();

            DatagramSocket socket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length, address, port);
            DatagramPacket response = new DatagramPacket(new byte[Api.BUFFOR_LENGTH], Api.BUFFOR_LENGTH);

            logger.debug("Sending command: {} to {}", tmp, this.clu.getIp().getHostName());
            long startTime = System.currentTimeMillis();

            socket.setSoTimeout(Api.TIMEOUT);
            socket.send(datagramPacket);
            socket.receive(response);
            socket.close();
            long estimatedTime = System.currentTimeMillis() - startTime;

            MessageDecoded messageDecoded = encoder
                    .decode(new MessageEncoded(response.getData(), response.getLength()));
            Response cluCommandResponse = new Response(messageDecoded);

            logger.debug("Clu response: {} in {} ms ", cluCommandResponse.toString(), estimatedTime);

            return cluCommandResponse;

        } catch (IOException e) {
            logger.error("There was error while sending message {} ", tmp, e);
            throw new GrentonIoException(e);
        }
    }

    private String replacePlaceholders(String command) {
        String tmp = command;
        tmp = tmp.replace("[HOST]", clu.getIp().getHostAddress());
        tmp = tmp.replace("[TARGET]", clu.getTargetIp().getHostAddress());
        tmp = tmp.replace("[SESSION_ID]", generateSessionId());
        tmp = tmp.replace("[PORT]", clu.getCallbackPort().toString());
        return tmp;
    }

    private String generateSessionId() {
        Random randomGenerator = new Random();
        String randomSessionId;

        for (randomSessionId = Integer.toHexString(randomGenerator.nextInt(65534)); randomSessionId
                .length() < 6; randomSessionId = "0" + randomSessionId) {
        }

        return randomSessionId;
    }
}
