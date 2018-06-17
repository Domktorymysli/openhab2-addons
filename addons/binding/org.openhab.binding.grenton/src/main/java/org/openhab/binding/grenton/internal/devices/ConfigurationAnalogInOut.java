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
final public class ConfigurationAnalogInOut {

    public BigDecimal pageId;

    public long pollingInterval = 10;

    public String analogInName1 = "";

    public String analogInName2 = "";

    public String analogInName3 = "";

    public String analogInName4 = "";

    public String analogOutName1 = "";

    public String analogOutName2 = "";

    public String analogOutName3 = "";

    public String analogOutName4 = "";

    public String analogSensorName1 = "";

    public String analogSensorName2 = "";

    public String analogSensorName3 = "";

    public String analogSensorName4 = "";

    public String analogSensorName5 = "";

    public String analogSensorName6 = "";

    public String analogSensorName7 = "";

    public String analogSensorName8 = "";

    public String analogSensorName9 = "";

    public String analogSensorName10 = "";

    public String analogSensorName11 = "";

    public String analogSensorName12 = "";

    public String analogSensorName13 = "";

    public String analogSensorName14 = "";

    public String analogSensorName15 = "";

    public String analogSensorName16 = "";

    @Override
    public String toString() {
        return "ConfigurationAnalogInOut [pageId=" + pageId + ", pollingInterval=" + pollingInterval
                + ", analogInName1=" + analogInName1 + ", analogInName2=" + analogInName2 + ", analogInName3="
                + analogInName3 + ", analogInName4=" + analogInName4 + ", analogOutName1=" + analogOutName1
                + ", analogOutName2=" + analogOutName2 + ", analogOutName3=" + analogOutName3 + ", analogOutName4="
                + analogOutName4 + ", analogSensorName1=" + analogSensorName1 + ", analogSensorName2="
                + analogSensorName2 + ", analogSensorName3=" + analogSensorName3 + ", analogSensorName4="
                + analogSensorName4 + ", analogSensorName5=" + analogSensorName5 + ", analogSensorName6="
                + analogSensorName6 + ", analogSensorName7=" + analogSensorName7 + ", analogSensorName8="
                + analogSensorName8 + ", analogSensorName9=" + analogSensorName9 + ", analogSensorName10="
                + analogSensorName10 + ", analogSensorName11=" + analogSensorName11 + ", analogSensorName12="
                + analogSensorName12 + ", analogSensorName13=" + analogSensorName13 + ", analogSensorName14="
                + analogSensorName14 + ", analogSensorName15=" + analogSensorName15 + ", analogSensorName16="
                + analogSensorName16 + "]";
    }

}
