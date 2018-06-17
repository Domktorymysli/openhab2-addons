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
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.devices.ConfigurationDigitalIn;
import org.openhab.binding.grenton.internal.devices.DeviceDigitalIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonDigitalInHandler extends BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(GrentonDigitalInHandler.class);

    private DeviceDigitalIn deviceDigitalIn;

    private ScheduledFuture<?> pollingJob;

    public GrentonDigitalInHandler(Thing thing) {
        super(thing);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            clientRegister(deviceDigitalIn);
        }
    };

    @Override
    public void initialize() {

        deviceDigitalIn = new DeviceDigitalIn(getConfigAs(ConfigurationDigitalIn.class));

        pollingJob = scheduler.scheduleWithFixedDelay(runnable, 0, deviceDigitalIn.getPollingInterval(),
                TimeUnit.SECONDS);

        initalizeCluObserver(deviceDigitalIn);
    }

    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, @NonNull Command command) {
        logger.debug("handleCommand channelUID: {}, command: {}", channelUID, command);
        // do nothing
    }

    /**
     * Update Thing state
     *
     * @param response
     */
    @Override
    protected void updateState(Response response) {
        deviceDigitalIn.setState(response);
        Map<String, String> channelsState = deviceDigitalIn.getBinaryState();

        for (String channelID : channelsState.keySet()) {
            if (channelsState.get(channelID).equals("1")) {
                updateState(channelID, OpenClosedType.CLOSED);
            } else {
                updateState(channelID, OpenClosedType.OPEN);
            }
        }
    }
}
