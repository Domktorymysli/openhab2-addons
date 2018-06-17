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
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.handler.exception.MissingBridge;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.command.ApiCommand;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.openhab.binding.grenton.internal.devices.ConfigurationAnalogInOut;
import org.openhab.binding.grenton.internal.devices.DeviceAnalogInOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonAnalogInOutHandler extends BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(GrentonAnalogInOutHandler.class);

    private DeviceAnalogInOut deviceAnalogInOut;

    private ScheduledFuture<?> pollingJob;

    public GrentonAnalogInOutHandler(Thing thing) {
        super(thing);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            logger.info("Client Register AnalogIn");
            clientRegister(deviceAnalogInOut);
        }
    };

    @Override
    public void initialize() {
        deviceAnalogInOut = new DeviceAnalogInOut(getConfigAs(ConfigurationAnalogInOut.class));

        pollingJob = scheduler.scheduleWithFixedDelay(runnable, 0, deviceAnalogInOut.getPollingInterval(),
                TimeUnit.SECONDS);

        initalizeCluObserver(deviceAnalogInOut);
    }

    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, @NonNull Command command) {
        logger.debug("handleCommand channelUID: {}, command: {}", channelUID, command);
        try {
            ApiCommand apiCommand = deviceAnalogInOut.change(channelUID.getId(), command);
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
        deviceAnalogInOut.setState(response);
        Map<String, String> sensorsState = deviceAnalogInOut.getSensorsState();

        for (String channelID : sensorsState.keySet()) {
            updateState(channelID, new StringType(sensorsState.get(channelID)));
        }
    }
}
