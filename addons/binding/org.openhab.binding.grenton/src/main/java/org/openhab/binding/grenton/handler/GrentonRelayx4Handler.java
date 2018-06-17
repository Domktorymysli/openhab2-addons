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
package org.openhab.binding.grenton.handler;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.handler.exception.MissingBridge;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.command.ApiCommand;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.openhab.binding.grenton.internal.devices.ConfigurationRelay;
import org.openhab.binding.grenton.internal.devices.DeviceRelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonRelayx4Handler extends BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(GrentonRelayx4Handler.class);

    private DeviceRelay deviceRelay;

    private ScheduledFuture<?> pollingJob;

    public GrentonRelayx4Handler(Thing thing) {
        super(thing);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            clientRegister(deviceRelay);
        }
    };

    @Override
    public void initialize() {
        deviceRelay = new DeviceRelay(getConfigAs(ConfigurationRelay.class));
        pollingJob = scheduler.scheduleWithFixedDelay(runnable, 0, deviceRelay.getPollingInterval(), TimeUnit.SECONDS);
        initalizeCluObserver(deviceRelay);
    }

    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, @NonNull Command command) {
        try {
            logger.debug("handleCommand channelUID: {}, command: {}", channelUID, command);
            ApiCommand apiCommand = deviceRelay.toggleSwitch(channelUID.getId(), command);
            getGrentonCluHandler().getCluBridge().executeCommand(apiCommand);
        } catch (GrentonIoException | GrentonEncoderException | MissingBridge e) {
            logger.error("Msg: {}, Exception: {}", e.getMessage(), e);
        }
    }

    /**
     * Update Thing state
     *
     * @param response
     */
    @Override
    protected void updateState(Response response) {
        logger.debug("Update state: {}", response);
        deviceRelay.setState(response);
        Map<String, String> channelsState = deviceRelay.getBinaryState();

        for (String channelID : channelsState.keySet()) {
            if (channelsState.get(channelID).equals("1")) {
                updateState(channelID, OnOffType.ON);
            }

            if (channelsState.get(channelID).equals("0")) {
                updateState(channelID, OnOffType.OFF);
            }
        }
    }
}
