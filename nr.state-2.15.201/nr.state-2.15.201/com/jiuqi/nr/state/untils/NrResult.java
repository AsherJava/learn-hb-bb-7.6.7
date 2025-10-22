/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.state.untils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NrResult {
    private static final Logger logger = LoggerFactory.getLogger(NrResult.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Integer status;
    private String msg;
    private Object data;

    public static NrResult build(Integer status, String msg, Object data) {
        return new NrResult(status, msg, data);
    }

    public static NrResult ok(Object data) {
        return new NrResult(data);
    }

    public static NrResult ok() {
        return new NrResult(null);
    }

    public NrResult() {
    }

    public static NrResult build(Integer status, String msg) {
        return new NrResult(status, msg, null);
    }

    public NrResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public NrResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static NrResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return (NrResult)MAPPER.readValue(jsonData, NrResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return NrResult.build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static NrResult format(String json) {
        try {
            return (NrResult)MAPPER.readValue(json, NrResult.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static NrResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(), (JavaType)MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return NrResult.build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        }
        catch (Exception e) {
            return null;
        }
    }
}

