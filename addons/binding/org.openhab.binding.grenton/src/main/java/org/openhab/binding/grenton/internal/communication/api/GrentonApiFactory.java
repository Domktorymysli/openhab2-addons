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

import java.net.SocketException;
import java.net.UnknownHostException;

import org.openhab.binding.grenton.internal.communication.model.Clu;
import org.openhab.binding.grenton.internal.devices.CluConfiguration;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonApiFactory {

    public GrentonApi create(CluConfiguration cluConfiguration) throws UnknownHostException, SocketException {
        Clu clu = new Clu(cluConfiguration.cluIp, cluConfiguration.openHabIp, cluConfiguration.cluPort,
                cluConfiguration.callbackPort);
        GrentonEncoder grentonEncoder = new GrentonEncoder(cluConfiguration.cluKey, cluConfiguration.cluIv);
        GrentonApi grentonApi = new GrentonApi(clu, grentonEncoder);
        return grentonApi;
    }
}
