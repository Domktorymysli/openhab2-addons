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

import java.math.BigDecimal;
import java.util.Map;

import org.openhab.binding.grenton.internal.communication.api.Response;

/**
 * @author tciunelis - Initial contribution
 */
public interface Device {

    /**
     * Get device name
     *
     * @return
     */
    public String getName();

    /**
     * Every module has own "Page". It is used for clientRegister method in Clu.
     *
     * @return
     */
    public BigDecimal getPageId();

    /**
     * Allow to get DeviceState. And this object can be easy converted to Grenton format.
     *
     * @return
     */
    public DeviceState getState();

    /**
     *
     * @param response
     */
    public void setState(Response response);

    /**
     * Get binary state of modules connected to device. Open/Closed On/Off
     *
     * @return
     */
    public Map<String, String> getBinaryState();

    /**
     * Get sensors state
     *
     * @return
     */
    public Map<String, String> getSensorsState();

    /**
     * Get device polling interval
     */
    public long getPollingInterval();

}
