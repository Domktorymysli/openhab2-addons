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
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.command.AnalogOutCommand;
import org.openhab.binding.grenton.internal.communication.api.command.ApiCommand;

/**
 * @author tciunelis - Initial contribution
 */
public class DeviceAnalogInOut implements Device {

    private final ConfigurationAnalogInOut config;

    private DeviceState state;

    public DeviceAnalogInOut(ConfigurationAnalogInOut analogInOutConfigiration) {

        this.config = analogInOutConfigiration;
        this.state = new DeviceState();

        this.setAnalogInOutState(ANALOG_IN_1, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_IN_2, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_IN_3, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_IN_4, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_OUT_1, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_OUT_2, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_OUT_3, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_OUT_4, DecimalType.ZERO);

        this.setAnalogInOutState(ANALOG_SENSOR_1, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_2, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_3, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_4, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_5, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_6, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_7, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_8, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_9, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_10, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_11, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_12, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_13, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_14, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_15, DecimalType.ZERO);
        this.setAnalogInOutState(ANALOG_SENSOR_16, DecimalType.ZERO);

    }

    private void setAnalogInOutState(String key, DecimalType decimal) {
        String value = decimal.toFullString();
        String channel = changeChannelToName(key);

        if (!channel.equals("")) {
            state.setValue(channel, value);
        }
    }

    public ApiCommand change(String key, Command changeValue) {
        return new AnalogOutCommand(changeChannelToName(key), DecimalType.valueOf(changeValue.toFullString()));
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
    public Map<String, String> getBinaryState() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        return result;
    }

    @Override
    public Map<String, String> getSensorsState() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put(ANALOG_IN_1, state.getValue(changeChannelToName(ANALOG_IN_1)));
        result.put(ANALOG_IN_2, state.getValue(changeChannelToName(ANALOG_IN_2)));
        result.put(ANALOG_IN_3, state.getValue(changeChannelToName(ANALOG_IN_3)));
        result.put(ANALOG_IN_4, state.getValue(changeChannelToName(ANALOG_IN_4)));
        result.put(ANALOG_OUT_1, state.getValue(changeChannelToName(ANALOG_OUT_1)));
        result.put(ANALOG_OUT_2, state.getValue(changeChannelToName(ANALOG_OUT_2)));
        result.put(ANALOG_OUT_3, state.getValue(changeChannelToName(ANALOG_OUT_3)));
        result.put(ANALOG_OUT_4, state.getValue(changeChannelToName(ANALOG_OUT_4)));
        result.put(ANALOG_SENSOR_1, state.getValue(changeChannelToName(ANALOG_SENSOR_1)));
        result.put(ANALOG_SENSOR_2, state.getValue(changeChannelToName(ANALOG_SENSOR_2)));
        result.put(ANALOG_SENSOR_3, state.getValue(changeChannelToName(ANALOG_SENSOR_3)));
        result.put(ANALOG_SENSOR_4, state.getValue(changeChannelToName(ANALOG_SENSOR_4)));
        result.put(ANALOG_SENSOR_5, state.getValue(changeChannelToName(ANALOG_SENSOR_5)));
        result.put(ANALOG_SENSOR_6, state.getValue(changeChannelToName(ANALOG_SENSOR_6)));
        result.put(ANALOG_SENSOR_7, state.getValue(changeChannelToName(ANALOG_SENSOR_7)));
        result.put(ANALOG_SENSOR_8, state.getValue(changeChannelToName(ANALOG_SENSOR_8)));
        result.put(ANALOG_SENSOR_9, state.getValue(changeChannelToName(ANALOG_SENSOR_9)));
        result.put(ANALOG_SENSOR_10, state.getValue(changeChannelToName(ANALOG_SENSOR_10)));
        result.put(ANALOG_SENSOR_11, state.getValue(changeChannelToName(ANALOG_SENSOR_11)));
        result.put(ANALOG_SENSOR_12, state.getValue(changeChannelToName(ANALOG_SENSOR_12)));
        result.put(ANALOG_SENSOR_13, state.getValue(changeChannelToName(ANALOG_SENSOR_13)));
        result.put(ANALOG_SENSOR_14, state.getValue(changeChannelToName(ANALOG_SENSOR_14)));
        result.put(ANALOG_SENSOR_15, state.getValue(changeChannelToName(ANALOG_SENSOR_15)));
        result.put(ANALOG_SENSOR_16, state.getValue(changeChannelToName(ANALOG_SENSOR_16)));

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
        if (key.equals(ANALOG_IN_1)) {
            return config.analogInName1;
        }
        if (key.equals(ANALOG_IN_2)) {
            return config.analogInName2;
        }
        if (key.equals(ANALOG_IN_3)) {
            return config.analogInName3;
        }
        if (key.equals(ANALOG_IN_4)) {
            return config.analogInName4;
        }
        if (key.equals(ANALOG_OUT_1)) {
            return config.analogOutName1;
        }
        if (key.equals(ANALOG_OUT_2)) {
            return config.analogOutName2;
        }
        if (key.equals(ANALOG_OUT_3)) {
            return config.analogOutName3;
        }
        if (key.equals(ANALOG_OUT_4)) {
            return config.analogOutName4;
        }
        if (key.equals(ANALOG_SENSOR_1)) {
            return config.analogSensorName1;
        }
        if (key.equals(ANALOG_SENSOR_2)) {
            return config.analogSensorName2;
        }
        if (key.equals(ANALOG_SENSOR_3)) {
            return config.analogSensorName3;
        }
        if (key.equals(ANALOG_SENSOR_4)) {
            return config.analogSensorName4;
        }
        if (key.equals(ANALOG_SENSOR_5)) {
            return config.analogSensorName5;
        }
        if (key.equals(ANALOG_SENSOR_6)) {
            return config.analogSensorName6;
        }
        if (key.equals(ANALOG_SENSOR_7)) {
            return config.analogSensorName7;
        }
        if (key.equals(ANALOG_SENSOR_8)) {
            return config.analogSensorName8;
        }
        if (key.equals(ANALOG_SENSOR_9)) {
            return config.analogSensorName9;
        }
        if (key.equals(ANALOG_SENSOR_10)) {
            return config.analogSensorName10;
        }
        if (key.equals(ANALOG_SENSOR_11)) {
            return config.analogSensorName11;
        }
        if (key.equals(ANALOG_SENSOR_12)) {
            return config.analogSensorName12;
        }
        if (key.equals(ANALOG_SENSOR_13)) {
            return config.analogSensorName13;
        }
        if (key.equals(ANALOG_SENSOR_14)) {
            return config.analogSensorName14;
        }
        if (key.equals(ANALOG_SENSOR_15)) {
            return config.analogSensorName15;
        }
        if (key.equals(ANALOG_SENSOR_16)) {
            return config.analogSensorName16;
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
