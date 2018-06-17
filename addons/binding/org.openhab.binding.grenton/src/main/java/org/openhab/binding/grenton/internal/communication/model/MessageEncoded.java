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
package org.openhab.binding.grenton.internal.communication.model;

import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @author tciunelis - Initial contribution
 */
final public class MessageEncoded {

    private byte[] msg;

    private int length = 0;

    public MessageEncoded(byte[] msg, int msgLength) {
        this.msg = msg;
        this.length = msgLength;
    }

    public byte[] getMsg() {
        return this.msg;
    }

    public int getLength() {
        return this.length;
    }

    public String encodeToString() {
        return Base64.getEncoder().encodeToString(this.msg);
    }

    public static MessageEncoded createFromBase64(String msg) throws DecoderException {
        byte[] byteMessage = Base64.getDecoder().decode(msg);
        byte[] messageHolder = new byte[2048];
        System.arraycopy(byteMessage, 0, messageHolder, 0, byteMessage.length);

        return new MessageEncoded(messageHolder, byteMessage.length);
    }

    public static MessageEncoded createFromString(String msg) throws DecoderException {
        byte[] byteMessage = Hex.decodeHex(msg.toCharArray());
        byte[] messageHolder = new byte[2048];
        System.arraycopy(byteMessage, 0, messageHolder, 0, byteMessage.length);

        return new MessageEncoded(messageHolder, byteMessage.length);
    }

    @Override
    public String toString() {
        return "MessageEncoded{" + "\nstr=" + new String(this.msg) + "\nmsg=" + Arrays.toString(this.msg) + "\nlength="
                + this.length + '}';
    }
}
