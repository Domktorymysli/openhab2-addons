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
package org.openhab.binding.grenton.internal.discovery;

import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.grenton.GrentonBindingConstants;
import org.openhab.binding.grenton.handler.GrentonCluHandler;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.command.DiscoveryCommand;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author tciunelis - Initial contribution
 */
public class GrentonThingsDiscoveryService extends AbstractDiscoveryService {

    private Logger logger = LoggerFactory.getLogger(GrentonThingsDiscoveryService.class);

    private static final int SEARCH_TIME = 60;

    private GrentonCluHandler bridge;

    private int pageId = GrentonBindingConstants.DEFAULT_PAGE_NUMBER;

    public GrentonThingsDiscoveryService(@NonNull GrentonCluHandler bridge) {
        super(GrentonBindingConstants.SUPPORTED_THING_TYPES_UIDS, SEARCH_TIME, true);
        this.bridge = bridge;
    }

    @Override
    protected void deactivate() {
        super.deactivate();
    }

    @Override
    protected void startBackgroundDiscovery() {
        discoverDevices();
    };

    @Override
    protected void startScan() {
        if (bridge == null) {
            logger.error("Can't start Grenton discovery because brige is null!");
            return;
        }

        if (bridge.getThing().getStatus() != ThingStatus.ONLINE) {
            logger.error("Can't start Grenton discovery because bridge is not ONLINE. Current status is {}",
                    bridge.getThing().getStatus());
            return;
        }

        discoverDevices();
    }

    /**
     * Gets information about all modules that are connected to the Clu. DiscoveryCommand is asking Lua script for list
     * of devices.
     *
     * Json result looks like this:
     *
     * {
     * "id": "mm_460000424",
     * "type": "analogInOut",
     * "items": [
     * {
     * "item": "AnalogOUT_5656",
     * "name": "x460000424_AnalogOUT1"
     * },
     * {
     * "item": "AnalogOUT_3561",
     * "name": "x460000424_AnalogOUT2"
     * },
     * {
     * "item": "AnalogOUT_0530",
     * "name": "x460000424_AnalogOUT3"
     * },
     * {
     * "item": "AnalogOUT_1503",
     * "name": "x460000424_AnalogOUT4"
     * },
     * {
     * "item": "AnalogIN_8726",
     * "name": "x460000424_AnalogIN1"
     * },
     * {
     * "item": "AnalogIN_3065",
     * "name": "x460000424_AnalogIN2"
     * },
     * {
     * "item": "AnalogIN_5868",
     * "name": "x460000424_AnalogIN3"
     * },
     * {
     * "item": "AnalogIN_6845",
     * "name": "x460000424_AnalogIN4"
     * }
     * ]
     * }
     *
     * @throws GrentonIoException
     * @throws GrentonEncoderException
     */
    private void discoverDevices() {

        if (bridge.getThing().getStatus() != ThingStatus.ONLINE) {
            logger.debug("Grenton is not online, scanning postponed");
            return;
        }

        DiscoveryCommand discoveryCommand;
        Response response;
        Integer index = 0;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            do {
                discoveryCommand = new DiscoveryCommand(index);
                response = bridge.getCluBridge().executeCommand(discoveryCommand);
                if (!response.getBody().equals("{}")) {
                    addDevice(gson.fromJson(response.getBody(), Module.class), pageId);
                    index++;
                    pageId++;
                }
            } while (!response.getBody().equals("{}"));

        } catch (GrentonIoException e) {
            logger.error("Discovery exception message: {}, exception: {}", e.getMessage(), e);

        } catch (GrentonEncoderException e) {
            logger.error("Discovery exception message: {}, exception: {}", e.getMessage(), e);
        }

        stopScan();
    }

    /**
     * Clear cache if needed
     * d:\eclipse-smarthouse\openhab2-master\git\openhab-distro\launch\home\ userdata\jsondb\
     *
     * @param module
     * @param pageId
     */
    private void addDevice(Module module, int pageId) {
        logger.debug("Adding new device with parameters: {}", module);

        DiscoveryResult result;

        try {
            ThingUID bridgeUID = bridge.getThing().getUID();

            ThingUID thingUID = new ThingUID(GrentonBindingConstants.BINDING_ID, bridgeUID, module.getId());

            Map<String, Object> properties = module.getProperties();
            properties.put(GrentonBindingConstants.PAGE_ID, pageId);

            logger.debug("New module properties: {}", properties);

            result = DiscoveryResultBuilder.create(thingUID).withBridge(bridgeUID).withProperties(properties)
                    .withThingType(module.getType()).withRepresentationProperty(module.getId())
                    .withLabel("Grenton - module: " + module.getId() + " " + module.getType().getAsString()).build();

            thingDiscovered(result);

        } catch (Exception e) {
            logger.warn("Error while adding new device: {}", e);
        }
    }
}
