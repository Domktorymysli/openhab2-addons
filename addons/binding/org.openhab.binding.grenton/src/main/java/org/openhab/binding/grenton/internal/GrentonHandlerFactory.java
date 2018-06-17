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
package org.openhab.binding.grenton.internal;

import static org.openhab.binding.grenton.GrentonBindingConstants.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.grenton.handler.GrentonAnalogInOutHandler;
import org.openhab.binding.grenton.handler.GrentonCluHandler;
import org.openhab.binding.grenton.handler.GrentonDigitalInHandler;
import org.openhab.binding.grenton.handler.GrentonPanelHandler;
import org.openhab.binding.grenton.handler.GrentonRelayx4Handler;
import org.openhab.binding.grenton.internal.discovery.GrentonThingsDiscoveryService;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, immediate = true, configurationPid = "binding.grenton")
@NonNullByDefault
final public class GrentonHandlerFactory extends BaseThingHandlerFactory {

    private final Logger logger = LoggerFactory.getLogger(GrentonCluHandler.class);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    private final Map<ThingUID, @Nullable ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        switch (thingTypeUID.getId()) {
            case CLU:
                GrentonCluHandler cluHandler = new GrentonCluHandler((Bridge) thing);
                registerGrentonThingsDiscoveryService(cluHandler);
                return cluHandler;
            case RELAYX4:
                return new GrentonRelayx4Handler(thing);
            case DIGITAL_IN:
                return new GrentonDigitalInHandler(thing);
            case ANALOG_IN_OUT:
                return new GrentonAnalogInOutHandler(thing);
            case PANEL:
                return new GrentonPanelHandler(thing);
        }

        return null;
    }

    private synchronized void registerGrentonThingsDiscoveryService(GrentonCluHandler bridgeHandler) {
        GrentonThingsDiscoveryService discoveryService = new GrentonThingsDiscoveryService(bridgeHandler);
        // discoveryService.activate();
        this.discoveryServiceRegs.put(bridgeHandler.getThing().getUID(), bundleContext
                .registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<String, Object>()));
    }

    @Override
    protected synchronized void removeHandler(ThingHandler thingHandler) {
        //
        // @todo sprawdzić - HueThingHandlerFactory

        //
        // if (thingHandler instanceof HueBridgeHandler) {
        // ServiceRegistration<?> serviceReg = this.discoveryServiceRegs.get(thingHandler.getThing().getUID());
        // if (serviceReg != null) {
        // // remove discovery service, if bridge handler is removed
        // HueLightDiscoveryService service = (HueLightDiscoveryService) bundleContext
        // .getService(serviceReg.getReference());
        // if (service != null) {
        // service.deactivate();
        // }
        // serviceReg.unregister();
        // discoveryServiceRegs.remove(thingHandler.getThing().getUID());
        // }
        // }
    }

}
