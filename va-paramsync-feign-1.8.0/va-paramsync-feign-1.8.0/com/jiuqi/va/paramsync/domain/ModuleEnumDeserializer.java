/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 */
package com.jiuqi.va.paramsync.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleEnum;
import java.io.IOException;

class ModuleEnumDeserializer
extends JsonDeserializer<VaParamSyncModuleEnum> {
    ModuleEnumDeserializer() {
    }

    public VaParamSyncModuleEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return VaParamSyncModuleEnum.valueOf(p.getValueAsString());
    }
}

