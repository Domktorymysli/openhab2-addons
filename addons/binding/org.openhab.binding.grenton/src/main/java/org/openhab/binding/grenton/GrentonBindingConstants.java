/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.grenton;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.ImmutableSet;

/**
 * @author tciunelis - Initial contribution
 */
@NonNullByDefault
final public class GrentonBindingConstants {

    public static final String BINDING_ID = "grenton";

    // List of device types
    public static final String CLU = "clu";
    public static final String RELAYX4 = "relayx4";
    public static final String DIGITAL_IN = "digitalIn";
    public static final String ANALOG_IN_OUT = "analogInOut";
    public static final String PANEL = "panel";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_CLU = new ThingTypeUID(BINDING_ID, CLU);
    public static final ThingTypeUID THING_TYPE_RELAYX4 = new ThingTypeUID(BINDING_ID, RELAYX4);
    public static final ThingTypeUID THING_TYPE_DIGITAL_IN = new ThingTypeUID(BINDING_ID, DIGITAL_IN);
    public static final ThingTypeUID THING_TYPE_ANALOG_IN_OUT = new ThingTypeUID(BINDING_ID, ANALOG_IN_OUT);
    public static final ThingTypeUID THING_TYPE_PANEL = new ThingTypeUID(BINDING_ID, PANEL);

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = ImmutableSet.of(THING_TYPE_CLU,
            THING_TYPE_RELAYX4, THING_TYPE_DIGITAL_IN, THING_TYPE_ANALOG_IN_OUT, THING_TYPE_PANEL);

    // default values for module discovery
    public static final int DEFAULT_PAGE_NUMBER = 200;
    public static final String PAGE_ID = "pageId";

    // relayx4
    public static final String DOUT_1 = "dout1";
    public static final String DOUT_2 = "dout2";
    public static final String DOUT_3 = "dout3";
    public static final String DOUT_4 = "dout4";

    // digitalIn
    public static final String DIN_1 = "din1";
    public static final String DIN_2 = "din2";
    public static final String DIN_3 = "din3";
    public static final String DIN_4 = "din4";
    public static final String DIN_5 = "din5";
    public static final String DIN_6 = "din6";
    public static final String DIN_7 = "din7";
    public static final String DIN_8 = "din8";

    // analogInOut
    public static final String ANALOG_IN_1 = "analog_in1";
    public static final String ANALOG_IN_2 = "analog_in2";
    public static final String ANALOG_IN_3 = "analog_in3";
    public static final String ANALOG_IN_4 = "analog_in4";

    public static final String ANALOG_OUT_1 = "analog_out1";
    public static final String ANALOG_OUT_2 = "analog_out2";
    public static final String ANALOG_OUT_3 = "analog_out3";
    public static final String ANALOG_OUT_4 = "analog_out4";

    // 1wire up to 16 devices
    public static final String ANALOG_SENSOR_1 = "sensor1";
    public static final String ANALOG_SENSOR_2 = "sensor2";
    public static final String ANALOG_SENSOR_3 = "sensor3";
    public static final String ANALOG_SENSOR_4 = "sensor4";
    public static final String ANALOG_SENSOR_5 = "sensor5";
    public static final String ANALOG_SENSOR_6 = "sensor6";
    public static final String ANALOG_SENSOR_7 = "sensor7";
    public static final String ANALOG_SENSOR_8 = "sensor8";
    public static final String ANALOG_SENSOR_9 = "sensor9";
    public static final String ANALOG_SENSOR_10 = "sensor10";
    public static final String ANALOG_SENSOR_11 = "sensor11";
    public static final String ANALOG_SENSOR_12 = "sensor12";
    public static final String ANALOG_SENSOR_13 = "sensor13";
    public static final String ANALOG_SENSOR_14 = "sensor14";
    public static final String ANALOG_SENSOR_15 = "sensor15";
    public static final String ANALOG_SENSOR_16 = "sensor16";

    // panels
    public static final String BUTTON_1 = "button1";
    public static final String BUTTON_2 = "button2";
    public static final String BUTTON_3 = "button3";
    public static final String BUTTON_4 = "button4";
    public static final String BUTTON_5 = "button5";
    public static final String BUTTON_6 = "button6";
    public static final String BUTTON_7 = "button7";
    public static final String BUTTON_8 = "button8";

    public static final String PANEL_SENSOR_1 = "panel_sensor1";
    public static final String PANEL_SENSOR_2 = "panel_sensor2";

}
