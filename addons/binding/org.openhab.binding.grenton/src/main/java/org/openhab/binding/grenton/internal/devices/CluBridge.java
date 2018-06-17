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

import java.io.IOException;

import org.openhab.binding.grenton.internal.communication.api.Api;
import org.openhab.binding.grenton.internal.communication.api.GrentonApiFactory;
import org.openhab.binding.grenton.internal.communication.api.GrentonServer;
import org.openhab.binding.grenton.internal.communication.api.GrentonServerFactory;
import org.openhab.binding.grenton.internal.communication.api.Response;
import org.openhab.binding.grenton.internal.communication.api.command.ApiCommand;
import org.openhab.binding.grenton.internal.communication.api.command.CheckAliveCommand;
import org.openhab.binding.grenton.internal.communication.api.command.ClientRegisterCommand;
import org.openhab.binding.grenton.internal.communication.exception.GrentonEncoderException;
import org.openhab.binding.grenton.internal.communication.exception.GrentonIoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that handles communication between Clu and bridge handler.
 * Clu is the main control unit of Grenton product. All modules are connected to Clu via modbus like (TF-BUS) protocol
 * or z-wave.
 *
 * @author tciunelis - Initial contribution
 *
 */
final public class CluBridge {

    private final Logger logger = LoggerFactory.getLogger(CluBridge.class);

    private final CluConfiguration config;

    private final Api api;

    private final GrentonServer server;

    public CluBridge(CluConfiguration config) throws IOException {
        this.config = config;
        this.api = (new GrentonApiFactory()).create(config);
        this.server = (new GrentonServerFactory()).create(config);
    }

    public GrentonServer getGrentonServer() {
        return this.server;
    }

    /**
     * Calling CheckAlive Lua method on Clu. The method is returning internal Clu id that it is not needed.
     *
     * @return
     * @throws GrentonIoException
     * @throws GrentonEncoderException
     */
    public boolean checkAlive() throws GrentonIoException, GrentonEncoderException {
        Response response = this.api.send(new CheckAliveCommand());
        logger.debug("checkAlive: {} ", response.getBody());
        return !response.getBody().equals("");
    }

    /**
     * Main communication method between OpenHab and Clu. It register changes on that where made in OpenHab and send it
     * to Clu.
     * Also Clu will notify OpenHab for every change on device.
     *
     * @param device
     * @return
     * @throws GrentonEncoderException
     * @throws GrentonIoException
     */
    public Response registerClient(Device device) throws GrentonIoException, GrentonEncoderException {
        ClientRegisterCommand clientRegisterCommand = new ClientRegisterCommand(device);
        return this.api.send(clientRegisterCommand);
    }

    public void messageFromClu(Response response) {
        logger.debug("Got message from clu: {}", response);

    }

    /**
     *
     * @param command
     * @return
     * @throws GrentonIoException
     * @throws GrentonEncoderException
     */
    public Response executeCommand(ApiCommand command) throws GrentonIoException, GrentonEncoderException {
        return this.api.send(command);
    }

    /**
     * Checking is Clu configuration is valid.
     *
     * @return boolean
     */
    public boolean isConfigurationValid() {
        return this.config.isValid();
    }

    /**
     * Getter for configuration
     *
     * @return
     */
    public CluConfiguration getCluConfiguration() {
        return this.config;
    }

}
