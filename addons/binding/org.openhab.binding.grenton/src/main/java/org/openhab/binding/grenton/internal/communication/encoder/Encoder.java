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
final public class Encoder {

    private CipherKey cipherKey;

    public Encoder(CipherKey cipherKey) {
        this.cipherKey = cipherKey;
    }

    public MessageEncoded encode(MessageDecoded message) throws NoSuchPaddingException, NoSuchAlgorithmException,
            NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.cipherKey.getSecretKey(), this.cipherKey.getIvSpec());

        byte[] result = new byte[cipher.getOutputSize(message.getLength())];

        int tmp = cipher.update(message.getMsg(), 0, message.getLength(), result, 0);
        cipher.doFinal(result, tmp);

        return new MessageEncoded(result, result.length);
    }
}
