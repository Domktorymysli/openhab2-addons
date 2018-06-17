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
import java.net.SocketTimeoutException;
import java.util.Observable;

import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.model.Clu;
import org.openhab.binding.grenton.internal.communication.model.MessageDecoded;
import org.openhab.binding.grenton.internal.communication.model.MessageEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible to listen on UDP port for callback from clu. By sending request
 *
 * @see package org.openhab.binding.homematic.internal.bus;
 *
 * @author tciunelis - Initial contribution
 */
final public class GrentonServer extends Observable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(GrentonServer.class);
    private DatagramSocket socket;
    private boolean accept = true;
    private Encoder encoder;
    private Object lock;

    /**
     * @param clu
     * @param encoder
     * @param lock
     * @throws IOException
     */
    public GrentonServer(Clu clu, Encoder encoder, Object lock) throws IOException {
        this.encoder = encoder;
        this.lock = lock;
        socket = new DatagramSocket(clu.getCallbackPort());
        // socket.setSoTimeout(10000);
        logger.debug("Starting listening on port: {}", clu.getCallbackPort());
    }

    /**
     *
     */
    @Override
    public void run() {
        logger.debug("Starting Grenton API listener");
        DatagramPacket response;
        while (accept) {
            try {
                response = new DatagramPacket(new byte[Api.BUFFOR_LENGTH], Api.BUFFOR_LENGTH);
                socket.receive(response);

                MessageDecoded messageDecoded = encoder
                        .decode(new MessageEncoded(response.getData(), response.getLength()));

                logger.debug("Received message from CLU: {}", messageDecoded.toString().trim());

                setChanged();

                notifyObservers(new ResponseEvent(new Response(messageDecoded)));

            } catch (SocketTimeoutException e) {
                logger.error("socekt timeout");
            } catch (GrentonEncoderException e) {
                logger.error("Msg: {}, Exception: {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.error("Msg: {}, Exception: {}", e.getMessage(), e);
            }
        }

        socket.close();
    }

    /**
     * Stops the listening.
     *
     * @todo https://www.ibm.com/developerworks/library/j-jtp0924/index.html
     */
    public void shutdown() {
        logger.debug("Shutting down {}", this.getClass().getSimpleName());
        accept = false;
    }
}
