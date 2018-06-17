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

/**
 * @author tciunelis - Initial contribution
 */
final public class MessageDecoded {
    private byte[] msg;

    private int length;

    public MessageDecoded(String msg) {
        this.msg = msg.getBytes();
        this.length = msg.getBytes().length;
    }

    public MessageDecoded(byte[] msg) {
        this.msg = msg;
        this.length = msg.length;
    }

    public byte[] getMsg() {
        return msg;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return new String(this.getMsg());
    }
}
