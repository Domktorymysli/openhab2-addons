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
final public class GrentonModule {

    private String type;
    private String key;
    private String name;
    private String moduleId;

    public GrentonModule(String type, String key, String name, String moduleId) {
        super();
        this.type = type;
        this.key = key;
        this.name = name;
        this.moduleId = moduleId;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getModuleId() {
        return moduleId;
    }

}
