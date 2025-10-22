/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.query.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.ai.QueryNvwaFieldParam;
import com.jiuqi.nr.query.service.AiConfigDeserializer;
import com.jiuqi.nr.query.service.AiConfigSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=AiConfigSerializer.class)
@JsonDeserialize(using=AiConfigDeserializer.class)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class AiConfigs {
    private static final Logger log = LoggerFactory.getLogger(AiConfigs.class);
    public Map<String, String> dimensions;
    public List<QueryNvwaFieldParam> schemes;
    public String periodType;

    AiConfigs() {
    }

    public void SetSchemesStr(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{QueryNvwaFieldParam.class});
            this.schemes = (List)mapper.readValue(str, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

