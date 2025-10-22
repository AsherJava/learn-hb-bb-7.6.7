/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.designer.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.definition.common.DataRegionKind;
import java.io.IOException;

public class DataRegionKindSerialize
extends JsonSerializer<DataRegionKind> {
    public void serialize(DataRegionKind value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }
}

