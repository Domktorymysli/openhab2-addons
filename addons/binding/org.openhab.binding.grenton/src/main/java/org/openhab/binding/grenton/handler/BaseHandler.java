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

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.openhab.binding.grenton.handler.exception.MissingBridge;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.ResponseEvent;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.openhab.binding.grenton.internal.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
abstract public class BaseHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    public BaseHandler(Thing thing) {
        super(thing);
    }

    protected void initializeThing(@Nullable ThingStatus bridgeStatus) {
        logger.debug("initializeThing thing {} bridge status {}", getThing().getUID(), bridgeStatus);
        if (bridgeStatus == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR);
            return;
        }
        if (bridgeStatus != ThingStatus.ONLINE) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
            return;
        }
        if (bridgeStatus == ThingStatus.ONLINE) {
            updateStatus(ThingStatus.ONLINE);
            return;
        }
    }

    protected void clientRegister(Device device) {
        try {
            logger.debug("Client register device: {}", device.getName());
            Response response = getGrentonCluHandler().getCluBridge().registerClient(device);
            updateState(response);
        } catch (GrentonIoException | GrentonEncoderException | MissingBridge e) {
            logger.error("Msg: {}, Exception: {}", e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws MissingBridge
     */
    protected GrentonCluHandler getGrentonCluHandler() throws MissingBridge {
        Bridge bridge = getBridge();
        initializeThing((bridge == null) ? null : bridge.getStatus());

        if (bridge == null) {
            throw new MissingBridge("Missing GrentonCluHandler!");
        }

        return (GrentonCluHandler) bridge.getHandler();
    }

    /**
     * Handles also should observe messages from Clu. When state is changed outside OpenHab should be notified right
     * away.
     *
     * @param device
     */
    protected void initalizeCluObserver(Device device) {
        try {
            getGrentonCluHandler().getCluBridge().getGrentonServer().addObserver(new Observer() {
                @Override
                public void update(Observable obj, Object arg) {
                    ResponseEvent responseEvent = (ResponseEvent) arg;
                    if (canHandleResponse(responseEvent.getResponse(), device)) {
                        updateState(responseEvent.getResponse());
                    }
                }
            });
            logger.debug("Observers count: {}",
                    getGrentonCluHandler().getCluBridge().getGrentonServer().countObservers());
        } catch (MissingBridge e) {
            logger.error("Exception", e);
        }
    }

    /**
     * Every should get dedicated message. Response has "pageId" that should be equal to pageId configured in things
     * configuration.
     *
     * @todo move to device
     *
     * @param response
     * @param device
     *
     * @return
     */
    private boolean canHandleResponse(Response response, Device device) {
        if (response.getPageId().equals(device.getPageId())) {
            return true;
        }
        return false;
    }

    protected abstract void updateState(Response response);

}
