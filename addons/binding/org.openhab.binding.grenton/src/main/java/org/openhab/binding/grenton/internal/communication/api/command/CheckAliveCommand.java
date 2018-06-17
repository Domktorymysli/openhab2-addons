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

/**
 * @author tciunelis - Initial contribution
 */
final public class CheckAliveCommand implements ApiCommand {

    @Override
    public String getCommand() {
        return "req:[HOST]:[SESSION_ID]:checkAlive()";
    }

}
