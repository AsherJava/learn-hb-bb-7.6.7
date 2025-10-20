/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.budget.domain.ParamSerializer;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ObjectMapperParamSerializer<T>
extends ParamSerializer<T> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperParamSerializer.class);
    private ObjectMapper objectMapper = (ObjectMapper)ApplicationContextRegister.getBean(ObjectMapper.class);

    @Override
    public T unSerializeParam(String paramStr, Class<T> tClass) {
        if (!StringUtils.hasLength(paramStr)) {
            return null;
        }
        try {
            return (T)this.objectMapper.readValue(paramStr, tClass);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String serializeParam(T paramObj) {
        if (Objects.isNull(paramObj)) {
            return null;
        }
        try {
            return this.objectMapper.writeValueAsString(paramObj);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

