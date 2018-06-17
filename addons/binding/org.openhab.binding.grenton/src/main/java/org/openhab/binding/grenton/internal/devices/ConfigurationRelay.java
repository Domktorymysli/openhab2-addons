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
final public class ConfigurationRelay {

    public BigDecimal pageId;

    public String doutName1;

    public String doutName2;

    public String doutName3;

    public String doutName4;

    public long pollingInterval = 10;

    public BigDecimal getPageId() {
        return pageId;
    }

    @Override
    public String toString() {
        return "ConfigurationRelay [pageId=" + pageId + ", doutName1=" + doutName1 + ", doutName2=" + doutName2
                + ", doutName3=" + doutName3 + ", doutName4=" + doutName4 + ", pollingInterval=" + pollingInterval
                + "]";
    }

}
