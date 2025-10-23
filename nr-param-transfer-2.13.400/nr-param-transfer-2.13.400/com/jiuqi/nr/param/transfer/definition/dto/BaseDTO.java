/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.definition.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public abstract class BaseDTO {
    @JsonIgnore
    public byte[] toBytes(ObjectMapper objectMapper) throws IOException {
        return objectMapper.writeValueAsBytes((Object)this);
    }
}

