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
final public class ConfigurationDigitalIn {

    public BigDecimal pageId;

    public String dinName1 = "";

    public String dinName2 = "";

    public String dinName3 = "";

    public String dinName4 = "";

    public String dinName5 = "";

    public String dinName6 = "";

    public String dinName7 = "";

    public String dinName8 = "";

    public long pollingInterval = 10;

    @Override
    public String toString() {
        return "ConfigurationDigitalIn [pageId=" + pageId + ", dinName1=" + dinName1 + ", dinName2=" + dinName2
                + ", dinName3=" + dinName3 + ", dinName4=" + dinName4 + ", dinName5=" + dinName5 + ", dinName6="
                + dinName6 + ", dinName7=" + dinName7 + ", dinName8=" + dinName8 + ", pollingInterval="
                + pollingInterval + "]";
    }

}
