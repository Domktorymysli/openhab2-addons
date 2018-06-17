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

import org.openhab.binding.grenton.internal.communication.model.Clu;
import org.openhab.binding.grenton.internal.devices.CluConfiguration;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonServerFactory {

    public GrentonServer create(CluConfiguration cluConfiguration) throws IOException {
        Clu clu = new Clu(cluConfiguration.cluIp, cluConfiguration.openHabIp, cluConfiguration.cluPort,
                cluConfiguration.callbackPort);
        GrentonEncoder grentonEncoder = new GrentonEncoder(cluConfiguration.cluKey, cluConfiguration.cluIv);
        GrentonServer grentonApiListener = new GrentonServer(clu, grentonEncoder, new Object());
        return grentonApiListener;
    }

}
