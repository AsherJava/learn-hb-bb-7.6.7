/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Logger LOGGER = LoggerFactory.getLogger(Response.class);
    private int code;
    private String msg;
    private String data;
    private boolean success;
    public static final ObjectMapper MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public Response() {
    }

    public Response(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Response(int code, String message, String data, boolean success) {
        this.code = code;
        this.msg = message;
        this.data = data;
        this.success = success;
    }

    public static Response ok(String data) {
        return new Response(200, "", data, true);
    }

    public static Response okWithObj(Object data) {
        try {
            return new Response(200, "", MAPPER.writeValueAsString(data), true);
        }
        catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
    }

    public static Response error(String message) {
        return new Response(500, message, null, false);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String toString() {
        return "Response{code=" + this.code + ", msg='" + this.msg + ", data=" + this.data + ", success=" + this.success + '}';
    }

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}

