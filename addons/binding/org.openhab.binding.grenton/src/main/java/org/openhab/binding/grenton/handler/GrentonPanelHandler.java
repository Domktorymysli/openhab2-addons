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
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.devices.ConfigurationPanel;
import org.openhab.binding.grenton.internal.devices.DevicePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonPanelHandler extends BaseHandler {

    private final Logger logger = LoggerFactory.getLogger(GrentonDigitalInHandler.class);

    private DevicePanel devicePanel;

    private ScheduledFuture<?> pollingJob;

    public GrentonPanelHandler(Thing thing) {
        super(thing);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            clientRegister(devicePanel);
        }
    };

    @Override
    public void initialize() {
        devicePanel = new DevicePanel(getConfigAs(ConfigurationPanel.class));
        pollingJob = scheduler.scheduleWithFixedDelay(runnable, 0, devicePanel.getPollingInterval(), TimeUnit.SECONDS);
        initalizeCluObserver(devicePanel);
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
        logger.debug("Update state: {}", response);
        devicePanel.setState(response);
        Map<String, String> channelsState = devicePanel.getBinaryState();
        Map<String, String> sensorsState = devicePanel.getSensorsState();

        for (String channelID : channelsState.keySet()) {
            if (channelsState.get(channelID).equals("1")) {
                updateState(channelID, OpenClosedType.CLOSED);
            } else {
                updateState(channelID, OpenClosedType.OPEN);
            }
        }

        logger.debug("Grenton Panel sensorsState: {}", sensorsState);
        for (String channelId : sensorsState.keySet()) {
            logger.debug("Sensor state: {}, value: {}", channelId, sensorsState.get(channelId));

            updateState(channelId, new StringType(sensorsState.get(channelId)));
        }
    }

}
