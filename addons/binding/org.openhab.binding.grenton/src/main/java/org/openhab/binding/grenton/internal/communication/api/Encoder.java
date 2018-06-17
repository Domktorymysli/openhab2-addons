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

import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.model.MessageDecoded;
import org.openhab.binding.grenton.internal.communication.model.MessageEncoded;

/**
 * @author tciunelis - Initial contribution
 */
public interface Encoder {

    MessageDecoded decode(MessageEncoded message) throws GrentonEncoderException;

    MessageEncoded encode(MessageDecoded message) throws GrentonEncoderException;

}
