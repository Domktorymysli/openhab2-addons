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
package org.openhab.binding.grenton.internal.devices;

/**
 * @author tciunelis - Initial contribution
 */
final public class CluConfiguration {

    public String cluIp;

    public String openHabIp;

    public int cluPort;

    public String cluKey;

    public String cluIv;

    public int callbackPort;

    public int timeout;

    public int poolingInterval = 10;

    public boolean isValid() {

        if (this.cluIp.equals("")) {
            return false;
        }

        if (this.openHabIp.equals("")) {
            return false;
        }

        if (this.cluPort == 0) {
            return false;
        }

        if (this.cluKey.equals("")) {
            return false;
        }

        if (this.cluIv.equals("")) {
            return false;
        }

        return true;
    }

    public int getPoolingInterval() {
        return poolingInterval;
    }

    @Override
    public String toString() {
        return "CluConfiguration [cluIp=" + cluIp + ", cluPort=" + cluPort + ", cluKey=" + cluKey + ", cluIv=" + cluIv
                + ", callbackPort=" + callbackPort + ", timeout=" + timeout + ", pollingInterval=" + poolingInterval
                + ", isValid()=" + isValid() + "]";
    }

}
