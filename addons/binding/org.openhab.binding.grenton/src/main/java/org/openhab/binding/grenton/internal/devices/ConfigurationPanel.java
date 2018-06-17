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

/**
 * @author tciunelis - Initial contribution
 */
public class ConfigurationPanel {

    public BigDecimal pageId;

    public long pollingInterval = 10;

    public String buttonName1 = "";

    public String buttonName2 = "";

    public String buttonName3 = "";

    public String buttonName4 = "";

    public String buttonName5 = "";

    public String buttonName6 = "";

    public String buttonName7 = "";

    public String buttonName8 = "";

    public String sensorName1 = "";

    public String sensorName2 = "";

    @Override
    public String toString() {
        return "ConfigurationPanel [pageId=" + pageId + ", pollingInterval=" + pollingInterval + ", buttonName1="
                + buttonName1 + ", buttonName2=" + buttonName2 + ", buttonName3=" + buttonName3 + ", buttonName4="
                + buttonName4 + ", buttonName5=" + buttonName5 + ", buttonName6=" + buttonName6 + ", buttonName7="
                + buttonName7 + ", buttonName8=" + buttonName8 + ", sensorName1=" + sensorName1 + ", sensorName2="
                + sensorName2 + "]";
    }

}
