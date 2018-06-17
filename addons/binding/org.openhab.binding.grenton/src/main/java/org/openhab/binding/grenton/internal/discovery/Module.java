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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.openhab.binding.grenton.GrentonBindingConstants;

/**
 * @author tciunelis - Initial contribution
 */
public class Module {

    private String id;

    private String type;

    private List<Item> items = new ArrayList<Item>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ThingTypeUID getType() throws Exception {

        if (type.equals(GrentonBindingConstants.DIGITAL_IN)) {
            return GrentonBindingConstants.THING_TYPE_DIGITAL_IN;
        }

        if (type.equals(GrentonBindingConstants.PANEL)) {
            return GrentonBindingConstants.THING_TYPE_PANEL;
        }

        if (type.equals(GrentonBindingConstants.RELAYX4)) {
            return GrentonBindingConstants.THING_TYPE_RELAYX4;
        }

        if (type.equals(GrentonBindingConstants.ANALOG_IN_OUT)) {
            return GrentonBindingConstants.THING_TYPE_ANALOG_IN_OUT;
        }

        throw new Exception("Not supported module type: " + type + "!");
    }

    public Map<String, Object> getProperties() throws Exception {
        Map<String, Object> properties = new HashMap<>();

        if (getType().equals(GrentonBindingConstants.THING_TYPE_DIGITAL_IN)) {
            properties.put("dinName1", getItemByIndex(0).getItem());
            properties.put("dinName2", getItemByIndex(1).getItem());
            properties.put("dinName3", getItemByIndex(2).getItem());
            properties.put("dinName4", getItemByIndex(3).getItem());
            properties.put("dinName5", getItemByIndex(4).getItem());
            properties.put("dinName6", getItemByIndex(5).getItem());
            properties.put("dinName7", getItemByIndex(6).getItem());
            properties.put("dinName8", getItemByIndex(7).getItem());

            return properties;
        }

        if (getType().equals(GrentonBindingConstants.THING_TYPE_RELAYX4)) {
            properties.put("doutName1", getItemByIndex(0).getItem());
            properties.put("doutName2", getItemByIndex(1).getItem());
            properties.put("doutName3", getItemByIndex(2).getItem());
            properties.put("doutName4", getItemByIndex(3).getItem());
        }

        // @todo kolejność może być zła
        if (getType().equals(GrentonBindingConstants.THING_TYPE_ANALOG_IN_OUT)) {
            properties.put("analogOutName1", getItemByIndex(0).getItem());
            properties.put("analogOutName2", getItemByIndex(1).getItem());
            properties.put("analogOutName3", getItemByIndex(2).getItem());
            properties.put("analogOutName4", getItemByIndex(3).getItem());

            properties.put("analogInName1", getItemByIndex(4).getItem());
            properties.put("analogInName2", getItemByIndex(5).getItem());
            properties.put("analogInName3", getItemByIndex(6).getItem());
            properties.put("analogInName4", getItemByIndex(7).getItem());

            properties.put("analogSensorName1", getItemByIndex(8).getItem());
            properties.put("analogSensorName2", getItemByIndex(9).getItem());
            properties.put("analogSensorName3", getItemByIndex(10).getItem());
            properties.put("analogSensorName4", getItemByIndex(11).getItem());
            properties.put("analogSensorName5", getItemByIndex(12).getItem());
            properties.put("analogSensorName6", getItemByIndex(13).getItem());
            properties.put("analogSensorName7", getItemByIndex(14).getItem());
            properties.put("analogSensorName8", getItemByIndex(15).getItem());
            properties.put("analogSensorName9", getItemByIndex(16).getItem());
            properties.put("analogSensorName10", getItemByIndex(17).getItem());
            properties.put("analogSensorName11", getItemByIndex(18).getItem());
            properties.put("analogSensorName12", getItemByIndex(19).getItem());
            properties.put("analogSensorName13", getItemByIndex(20).getItem());
            properties.put("analogSensorName14", getItemByIndex(21).getItem());
            properties.put("analogSensorName15", getItemByIndex(22).getItem());
            properties.put("analogSensorName16", getItemByIndex(23).getItem());
        }

        if (getType().equals(GrentonBindingConstants.THING_TYPE_PANEL)) {
            properties.put("buttonName1", getItemByIndex(0).getItem());
            properties.put("buttonName2", getItemByIndex(1).getItem());
            properties.put("buttonName3", getItemByIndex(2).getItem());
            properties.put("buttonName4", getItemByIndex(3).getItem());
            properties.put("buttonName5", getItemByIndex(4).getItem());
            properties.put("buttonName6", getItemByIndex(5).getItem());
            properties.put("buttonName7", getItemByIndex(6).getItem());
            properties.put("buttonName8", getItemByIndex(7).getItem());
            properties.put("sensorName1", getItemByIndex(8).getItem());
            properties.put("sensorName2", getItemByIndex(9).getItem());
        }

        return properties;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemByIndex(int index) {
        try {
            return items.get(index);
        } catch (IndexOutOfBoundsException e) {
            Item item = new Item();
            return item;
        }
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Module [id=" + id + ", type=" + type + ", items=" + items + "]";
    }
}
