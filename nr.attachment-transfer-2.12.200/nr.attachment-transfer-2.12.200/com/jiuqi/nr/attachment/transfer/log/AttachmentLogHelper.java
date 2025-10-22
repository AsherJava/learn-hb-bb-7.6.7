/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.attachment.transfer.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.log.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentLogHelper {
    private static final Logger log = LoggerFactory.getLogger(AttachmentLogHelper.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static void info(String message) {
        log.info(message);
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void debug(String message, Object ... args) {
        log.debug(message, args);
    }

    public static void info(String message, Object object) {
        String paramValue = null;
        if (object instanceof String) {
            paramValue = object.toString();
        } else {
            try {
                paramValue = mapper.writeValueAsString(object);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        log.info(message + "\u53c2\u6570\u4e3a\uff1a" + paramValue);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void importInfo(String message) {
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u5165\u9644\u4ef6", (String)message);
    }

    public static void importInfo(String message, Object object) {
        String paramValue;
        if (object instanceof String) {
            paramValue = object.toString();
        } else {
            try {
                paramValue = mapper.writeValueAsString(object);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u5165\u9644\u4ef6", (String)(message + "\u53c2\u6570\u4e3a\uff1a" + paramValue));
    }

    public static void importError(String message) {
        LogHelper.error((String)"\u5bfc\u5165\u9644\u4ef6", (String)message);
    }

    public static void exportInfo(String message) {
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u51fa\u9644\u4ef6", (String)message);
    }

    public static void exportInfo(String message, Object object) {
        String paramValue;
        if (object instanceof String) {
            paramValue = object.toString();
        } else {
            try {
                paramValue = mapper.writeValueAsString(object);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u5bfc\u51fa\u9644\u4ef6", (String)(message + "\u53c2\u6570\u4e3a\uff1a" + paramValue));
    }

    public static void exportError(String message) {
        LogHelper.error((String)"\u5bfc\u51fa\u9644\u4ef6", (String)message);
    }
}

