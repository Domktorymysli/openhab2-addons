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

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.openhab.binding.grenton.internal.communication.api.Response;

/**
 * @author tciunelis - Initial contribution
 */
public class DevicePanel implements Device {

    private final ConfigurationPanel config;

    private DeviceState state;

    /**
     * Grenton panel constructor
     *
     * @param clu
     * @param config
     */
    public DevicePanel(ConfigurationPanel config) {
        this.config = config;
        this.state = new DeviceState();

        this.setButtonState(BUTTON_1, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_2, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_3, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_4, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_5, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_6, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_7, OpenClosedType.OPEN);
        this.setButtonState(BUTTON_8, OpenClosedType.OPEN);
        this.setSensorState(PANEL_SENSOR_1, DecimalType.ZERO);
        this.setSensorState(PANEL_SENSOR_2, DecimalType.ZERO);
    }

    private void setButtonState(String key, OpenClosedType openClosed) {
        String value = (openClosed.equals(OpenClosedType.CLOSED) ? "1" : "0");
        String channel = changeChannelToName(key);

        // channel without name in configuration should not be used
        if (!channel.equals("")) {
            state.setValue(channel, value);
        }
    }

    private void setSensorState(String key, DecimalType decimal) {
        String value = decimal.format("00.00");
        String channel = changeChannelToName(key);
        if (!channel.equals("")) {
            state.setValue(channel, value);
        }
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

    @Override
    public Map<String, String> getSensorsState() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put(PANEL_SENSOR_1, state.getValue(changeChannelToName(PANEL_SENSOR_1)));
        result.put(PANEL_SENSOR_2, state.getValue(changeChannelToName(PANEL_SENSOR_2)));

        return result;
    }

    @Override
    public Map<String, String> getBinaryState() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put(BUTTON_1, state.getValue(changeChannelToName(BUTTON_1)));
        result.put(BUTTON_2, state.getValue(changeChannelToName(BUTTON_2)));
        result.put(BUTTON_3, state.getValue(changeChannelToName(BUTTON_3)));
        result.put(BUTTON_4, state.getValue(changeChannelToName(BUTTON_4)));
        result.put(BUTTON_5, state.getValue(changeChannelToName(BUTTON_5)));
        result.put(BUTTON_6, state.getValue(changeChannelToName(BUTTON_6)));
        result.put(BUTTON_7, state.getValue(changeChannelToName(BUTTON_7)));
        result.put(BUTTON_8, state.getValue(changeChannelToName(BUTTON_8)));

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
        if (key.equals(BUTTON_1)) {
            return config.buttonName1;
        }
        if (key.equals(BUTTON_2)) {
            return config.buttonName2;
        }
        if (key.equals(BUTTON_3)) {
            return config.buttonName3;
        }
        if (key.equals(BUTTON_4)) {
            return config.buttonName4;
        }
        if (key.equals(BUTTON_5)) {
            return config.buttonName5;
        }
        if (key.equals(BUTTON_6)) {
            return config.buttonName6;
        }
        if (key.equals(BUTTON_7)) {
            return config.buttonName7;
        }
        if (key.equals(BUTTON_8)) {
            return config.buttonName8;
        }
        if (key.equals(PANEL_SENSOR_1)) {
            return config.sensorName1;
        }
        if (key.equals(PANEL_SENSOR_2)) {
            return config.sensorName2;
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
