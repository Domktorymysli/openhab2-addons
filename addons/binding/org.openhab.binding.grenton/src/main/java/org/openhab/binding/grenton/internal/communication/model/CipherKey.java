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

import java.util.Base64;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author tciunelis - Initial contribution
 */
final public class CipherKey {

    private transient SecretKeySpec key;
    private transient IvParameterSpec ivSpec;

    public CipherKey(byte[] key, byte[] iv) {
        this.key = new SecretKeySpec(key, "AES");
        this.ivSpec = new IvParameterSpec(iv);
    }

    static public CipherKey createFromString(String key, String iv) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        byte[] ivBytes = Base64.getDecoder().decode(iv);

        return new CipherKey(keyBytes, ivBytes);
    }

    public SecretKeySpec getSecretKey() {
        return this.key;
    }

    public IvParameterSpec getIvSpec() {
        return this.ivSpec;
    }

}
