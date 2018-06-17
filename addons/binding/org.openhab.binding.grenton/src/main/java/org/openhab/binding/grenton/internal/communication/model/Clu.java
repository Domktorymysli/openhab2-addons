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

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tciunelis - Initial contribution
 */
public class Clu {

    private final Integer port;
    private final Integer callbackPort;
    private final InetAddress ip;
    private final InetAddress targetIp;

    public Clu(String ip, String targetIp, Integer port, Integer callbackPort) throws UnknownHostException {
        this.ip = InetAddress.getByName(ip);
        this.targetIp = InetAddress.getByName(targetIp);
        this.port = port;
        this.callbackPort = callbackPort;
    }

    public int getPort() {
        return port;
    }

    public Integer getCallbackPort() {
        return callbackPort;
    }

    public InetAddress getIp() {
        return ip;
    }

    public InetAddress getTargetIp() {
        return targetIp;
    }

}
