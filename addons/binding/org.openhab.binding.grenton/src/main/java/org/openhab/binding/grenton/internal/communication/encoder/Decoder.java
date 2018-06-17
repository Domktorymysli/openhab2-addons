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
package org.openhab.binding.grenton.internal.communication.encoder;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.openhab.binding.grenton.internal.communication.model.CipherKey;
import org.openhab.binding.grenton.internal.communication.model.MessageDecoded;
import org.openhab.binding.grenton.internal.communication.model.MessageEncoded;

/**
 * @author tciunelis - Initial contribution
 */
final public class Decoder {

    private CipherKey cipherKey;

    public Decoder(CipherKey cipherKey) {
        this.cipherKey = cipherKey;
    }

    public MessageDecoded decode(MessageEncoded msg) throws NoSuchPaddingException, ShortBufferException,
            InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            NoSuchProviderException, InvalidAlgorithmParameterException {
        return this.decode(msg.getMsg(), msg.getLength());
    }

    public MessageDecoded decode(byte[] message, int msgLength) throws NoSuchPaddingException, BadPaddingException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException,
            ShortBufferException, NoSuchProviderException, InvalidKeyException {
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, cipherKey.getSecretKey(), cipherKey.getIvSpec());

            byte[] output = new byte[cipher.getOutputSize(msgLength)];
            int length = cipher.update(message, 0, msgLength, output, 0);
            length += cipher.doFinal(output, length);
            byte[] result = new byte[length];
            System.arraycopy(output, 0, result, 0, length);

            return new MessageDecoded(result);
        }
    }

}
