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
package org.openhab.binding.grenton.internal.communication.api.command;

import java.math.BigDecimal;

import org.openhab.binding.grenton.internal.devices.Device;

/**
 * @author tciunelis - Initial contribution
 */
final public class ClientRegisterCommand implements ApiCommand {

    private Device device;

    public ClientRegisterCommand(Device device) {
        this.device = device;
    }

    @Override
    public String getCommand() {

        String state = device.getState().getChannels();
        BigDecimal pageId = device.getPageId();

        String command = "req:[HOST]:[SESSION_ID]:SYSTEM:clientRegister(\"[TARGET]\",[PORT],[PAGE_ID],[STATE])";
        command = command.replace("[PAGE_ID]", pageId.toPlainString());
        command = command.replace("[STATE]", state);

        return command;
    }
}
