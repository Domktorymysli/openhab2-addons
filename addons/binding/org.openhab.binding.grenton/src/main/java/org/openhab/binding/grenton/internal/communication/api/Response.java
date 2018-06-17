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
package org.openhab.binding.grenton.internal.communication.api;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openhab.binding.grenton.internal.communication.model.MessageDecoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tciunelis - Initial contribution
 */
final public class Response {

    final static String COMMAND_PATTERN = "(req|resp)\\:([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})\\:([a-z0-9]{6,8})\\:(.*)";

    final static String CLIENT_REGISTER_PATTERN = "clientReport:([0-9]+):\\{(.*)\\}";

    private final Logger logger = LoggerFactory.getLogger(Response.class);
    String type = "";

    String ip = "";

    String body = "";

    String sessionId = "";

    String pageId = "";

    Map<Integer, String> values = new LinkedHashMap<Integer, String>();

    private final MessageDecoded messageDecoded;

    public Response(MessageDecoded messageDecoded) {

        logger.debug("MessageDecoded: {}", messageDecoded);

        this.messageDecoded = messageDecoded;

        Pattern commnadPattern = Pattern.compile(Response.COMMAND_PATTERN);
        Matcher commandMatcher = commnadPattern.matcher(messageDecoded.toString().trim());

        if (commandMatcher.matches()) {
            type = commandMatcher.group(1);
            ip = commandMatcher.group(2);
            sessionId = commandMatcher.group(3);
            body = commandMatcher.group(4);
        }

        Pattern clientRegisterPattern = Pattern.compile(Response.CLIENT_REGISTER_PATTERN);
        Matcher clientRegisterMatcher = clientRegisterPattern.matcher(body);

        if (clientRegisterMatcher.matches()) {
            pageId = clientRegisterMatcher.group(1);
            String[] tmp = clientRegisterMatcher.group(2).split(",");

            int i = 0;
            for (String s : tmp) {
                values.put(++i, s);
            }
        }

    }

    public String getType() {
        return type;
    }

    public String getIp() {
        return ip;
    }

    public String getBody() {
        return body;
    }

    public String getSessionId() {
        return sessionId;
    }

    public BigDecimal getPageId() {
        return new BigDecimal(pageId);
    }

    public Map<Integer, String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "Response [type=" + type + ", ip=" + ip + ", body=" + body + ", sessionId=" + sessionId
                + ", messageDecoded=" + messageDecoded + ", values=" + values + "]";
    }

}
