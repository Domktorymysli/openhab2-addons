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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openhab.binding.grenton.internal.communication.api.Response;

/**
 * @author tciunelis - Initial contribution
 */
final public class DeviceState {

    private Map<String, String> state;

    public DeviceState() {
        state = new LinkedHashMap<String, String>();
    }

    public DeviceState(Map<String, String> state) {
        this.state = state;
    }

    public String getValue(String key) {
        if (state.containsKey(key)) {
            return state.get(key);
        }
        return "";
    }

    public void setValue(String key, String value) {
        state.put(key, value);
    }

    /**
     * Return data in Grenton format. It is almost like Json but different ...
     * Zeros are important. This is not state.
     *
     * @return {{DOUT_2485,0},{DOUT_6848,0}}
     */
    public String getChannels() {
        ArrayList<String> tmp = new ArrayList<String>();
        for (String key : state.keySet()) {
            if (!key.equals("")) {
                tmp.add("{" + key + ",0}");
            }
        }
        return "{" + String.join(",", tmp) + "}";
    }

    /**
     * Apply ClientRegister response to device state
     *
     * @param response
     */
    public void setAllValues(Response response) {
        if (response.getValues().isEmpty()) {
            return;
        }
        int i = 0;
        for (String key : state.keySet()) {
            i++;
            if (!response.getValues().containsKey(i)) {
                continue;
            }
            state.put(key, response.getValues().get(i));
        }
    }

    @Override
    public String toString() {
        return "DeviceState [state=" + state + "]";
    }
}
