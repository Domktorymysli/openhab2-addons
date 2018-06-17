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

import static org.openhab.binding.grenton.GrentonBindingConstants.*;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.openhab.binding.grenton.internal.communication.api.Response;

/**
 * @author tciunelis - Initial contribution
 */
public class DeviceDigitalIn implements Device {

    private final ConfigurationDigitalIn config;

    private DeviceState state;

    public DeviceDigitalIn(ConfigurationDigitalIn config) {
        this.config = config;
        this.state = new DeviceState();

        this.setDigitalInState(DIN_1, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_2, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_3, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_4, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_5, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_6, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_7, OpenClosedType.OPEN);
        this.setDigitalInState(DIN_8, OpenClosedType.OPEN);
    }

    public void setDigitalInState(String key, OpenClosedType openClosed) {
        String value = (openClosed.equals(OpenClosedType.CLOSED) ? "1" : "0");
        String channel = changeChannelToName(key);

        // channel without name in configuration should not be used
        if (!channel.equals("")) {
            state.setValue(channel, value);
        }
    }

    public OnOffType getDigitalInState(String key) {
        return OnOffType.valueOf(state.getValue(changeChannelToName(key)));
    }

    @Override
    public BigDecimal getPageId() {
        return config.pageId;
    }

    @Override
    public DeviceState getState() {
        return state;
    }

    @Override
    public void setState(Response response) {
        state.setAllValues(response);
    }

    /**
     * Get sensors state
     *
     * @return
     */
    @Override
    public Map<String, String> getSensorsState() {
        Map<String, String> result = new LinkedHashMap<String, String>();

        return result;
    }

    @Override
    public Map<String, String> getBinaryState() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put(DIN_1, state.getValue(changeChannelToName(DIN_1)));
        result.put(DIN_2, state.getValue(changeChannelToName(DIN_2)));
        result.put(DIN_3, state.getValue(changeChannelToName(DIN_3)));
        result.put(DIN_4, state.getValue(changeChannelToName(DIN_4)));
        result.put(DIN_5, state.getValue(changeChannelToName(DIN_5)));
        result.put(DIN_6, state.getValue(changeChannelToName(DIN_6)));
        result.put(DIN_7, state.getValue(changeChannelToName(DIN_7)));
        result.put(DIN_8, state.getValue(changeChannelToName(DIN_8)));

        return result;
    }

    /**
     * It allow to translate channel name into name that is recognized by Grenton command.
     * In DeviceState object it operates on names that are used on Grenton central unit (CLU).
     * And those names are protected inside this class. Handler should not know about those names.
     *
     * @param key
     * @return Grenton name of channel
     */
    private String changeChannelToName(String key) {
        if (key.equals(DIN_1)) {
            return config.dinName1;
        }
        if (key.equals(DIN_2)) {
            return config.dinName2;
        }
        if (key.equals(DIN_3)) {
            return config.dinName3;
        }
        if (key.equals(DIN_4)) {
            return config.dinName4;
        }
        if (key.equals(DIN_5)) {
            return config.dinName5;
        }
        if (key.equals(DIN_6)) {
            return config.dinName6;
        }
        if (key.equals(DIN_7)) {
            return config.dinName7;
        }
        if (key.equals(DIN_8)) {
            return config.dinName8;
        }
        return "";
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public long getPollingInterval() {
        return config.pollingInterval;
    }

}
