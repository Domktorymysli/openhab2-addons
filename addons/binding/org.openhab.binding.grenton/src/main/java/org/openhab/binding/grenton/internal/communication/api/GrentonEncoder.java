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

import org.openhab.binding.grenton.internal.communication.encoder.Decoder;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.model.CipherKey;
import org.openhab.binding.grenton.internal.communication.model.MessageDecoded;
import org.openhab.binding.grenton.internal.communication.model.MessageEncoded;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonEncoder implements Encoder {

    private CipherKey cipherKey;

    private Decoder decoder;

    private org.openhab.binding.grenton.internal.communication.encoder.Encoder encoder;

    public GrentonEncoder(String key, String iv) {
        this.cipherKey = CipherKey.createFromString(key, iv);
        this.decoder = new Decoder(this.cipherKey);
        this.encoder = new org.openhab.binding.grenton.internal.communication.encoder.Encoder(this.cipherKey);
    }

    @Override
    public MessageDecoded decode(MessageEncoded message) throws GrentonEncoderException {

        try {
            return this.decoder.decode(message);
        } catch (Exception e) {
            throw new GrentonEncoderException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public MessageEncoded encode(MessageDecoded message) throws GrentonEncoderException {
        try {
            return this.encoder.encode(message);
        } catch (Exception e) {
            throw new GrentonEncoderException(e.getMessage(), e.getCause());
        }
    }

}
