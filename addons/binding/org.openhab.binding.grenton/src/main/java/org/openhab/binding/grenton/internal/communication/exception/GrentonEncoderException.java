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
package org.openhab.binding.grenton.internal.communication.exception;

/**
 * @author tciunelis - Initial contribution
 */
public class GrentonEncoderException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GrentonEncoderException(String message) {
        super(message);
    }

    public GrentonEncoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
