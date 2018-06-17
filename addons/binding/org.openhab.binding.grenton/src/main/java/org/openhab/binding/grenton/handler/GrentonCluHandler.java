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

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.config.core.status.ConfigStatusMessage;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.ConfigStatusBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.grenton.internal.communication.api.ResponseEvent;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.openhab.binding.grenton.internal.devices.CluBridge;
import org.openhab.binding.grenton.internal.devices.CluConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class GrentonCluHandler extends ConfigStatusBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(GrentonCluHandler.class);

    /**
     * Class that is linking internal classes
     */
    private CluBridge cluBridge;

    /**
     * Job used for polling check that Clu is alive.
     */
    private ScheduledFuture<?> pollingJob;

    /**
     * Job used to create one thread that is listening on socket for udp messages from Clu when something is changed.
     */
    private ScheduledFuture<?> pollingJobServer;

    public GrentonCluHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // gdzieś w tym module będzie będzie discovery. tak samo jak w HUE
        // not needed
    }

    Runnable runnableDeviceStatus = new Runnable() {
        @Override
        public void run() {
            updateDeviceStatus();
        }
    };

    @Override
    public void initialize() {
        logger.debug("Initalizing handler for {}", getClass().getName());

        if (this.getThing().getStatusInfo().getStatusDetail().equals(ThingStatusDetail.CONFIGURATION_ERROR)) {
            return;
        }

        try {
            cluBridge = new CluBridge(getConfigAs(CluConfiguration.class));
        } catch (IOException e) {
            logger.error("Can't establish network socket connection", e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Can't establish network socket connection");
        }

        if (!cluBridge.isConfigurationValid()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_PENDING,
                    "Device keys are not set or invalid");
            return;
        }

        pollingJob = scheduler.scheduleWithFixedDelay(runnableDeviceStatus, 0,
                cluBridge.getCluConfiguration().getPoolingInterval(), TimeUnit.SECONDS);

        pollingJobServer = scheduler.schedule(cluBridge.getGrentonServer(), 10, TimeUnit.SECONDS);

    }

    private void updateDeviceStatus() {
        try {
            if (this.cluBridge.checkAlive()) {
                updateStatus(ThingStatus.ONLINE);
                return;
            }

        } catch (GrentonEncoderException e) {
            logger.error("Invalid credentials or network configuration", e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Invalid credentials or network configuration");
        } catch (GrentonIoException e) {
            logger.error("Clu is offline?", e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE, "Clu is offline?");
        }

        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE,
                "Clu checkAlive() method returned nothing.");
    }

    private void updateConnectedDevices(ResponseEvent event) {
        logger.debug("Update connected devices: {}", event);

        List<Thing> things = this.getBridge().getThings();

        for (Thing thing : things) {
            logger.debug("Thing connected to clu: {}", thing);
        }

    }

    @Override
    public void dispose() {
        if (pollingJob != null) {
            pollingJob.cancel(true);
        }

        if (pollingJobServer != null) {
            pollingJobServer.cancel(true);
        }
    }

    @Override
    public @NonNull Collection<@NonNull ConfigStatusMessage> getConfigStatus() {
        Collection<ConfigStatusMessage> configStatusMessages = Collections.emptyList();

        return configStatusMessages;
    }

    public CluBridge getCluBridge() {
        return cluBridge;
    }

}
