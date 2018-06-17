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

import org.eclipse.smarthome.core.library.types.DecimalType;

/**
 * @author tciunelis - Initial contribution
 */
final public class AnalogOutCommand implements ApiCommand {

    private String channel;
    private String value;

    public AnalogOutCommand(String channel, DecimalType value) {
        this.channel = channel;
        this.value = value.toFullString();
    }

    @Override
    public String getCommand() {
        String command = "req:[HOST]:[SESSION_ID]:[CHANNEL]:set(0, [VALUE])";
        command = command.replace("[CHANNEL]", channel);
        command = command.replace("[VALUE]", value);

        return command;
    }

}