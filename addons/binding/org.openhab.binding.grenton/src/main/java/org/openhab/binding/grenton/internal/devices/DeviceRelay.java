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
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.command.ApiCommand;
import org.openhab.binding.grenton.internal.communication.api.command.RelayOffCommand;
import org.openhab.binding.grenton.internal.communication.api.command.RelayOnCommand;
import org.openhab.binding.grenton.internal.communication.api.command.RelaySwitchCommand;

/**
 * @author tciunelis - Initial contribution
 */
public class DeviceRelay implements Device {

    private final ConfigurationRelay config;

    private DeviceState state;

    public DeviceRelay(ConfigurationRelay config) {
        this.config = config;
        this.state = new DeviceState();

        this.setSwitchState(DOUT_1, OnOffType.OFF);
        this.setSwitchState(DOUT_2, OnOffType.OFF);
        this.setSwitchState(DOUT_3, OnOffType.OFF);
        this.setSwitchState(DOUT_4, OnOffType.OFF);
    }

    public void setSwitchState(String key, OnOffType onOff) {
        String value = (onOff.equals(OnOffType.ON) ? "1" : "0");
        String channel = changeChannelToName(key);

        // channel without name in configuration should not be used
        if (!channel.equals("")) {
            state.setValue(channel, value);
        }
    }

    public ApiCommand toggleSwitch(String key, Command command) {

        OnOffType onOff = (OnOffType) command;

        if (onOff.equals(OnOffType.ON)) {
            return new RelayOnCommand(changeChannelToName(key));
        }

        if (onOff.equals(OnOffType.OFF)) {
            return new RelayOffCommand(changeChannelToName(key));
        }

        return new RelaySwitchCommand(changeChannelToName(key));
    }

    public OnOffType getSwitchState(String key) {
        return OnOffType.valueOf(state.getValue(changeChannelToName(key)));
    }

    @Override
    public BigDecimal getPageId() {
        return config.getPageId();
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

        return result;
    }

    @Override
    public Map<String, String> getBinaryState() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put(DOUT_1, state.getValue(changeChannelToName(DOUT_1)));
        result.put(DOUT_2, state.getValue(changeChannelToName(DOUT_2)));
        result.put(DOUT_3, state.getValue(changeChannelToName(DOUT_3)));
        result.put(DOUT_4, state.getValue(changeChannelToName(DOUT_4)));

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
        if (key.equals(DOUT_1)) {
            return config.doutName1;
        }
        if (key.equals(DOUT_2)) {
            return config.doutName2;
        }
        if (key.equals(DOUT_3)) {
            return config.doutName3;
        }
        if (key.equals(DOUT_4)) {
            return config.doutName4;
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
