/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.splittable.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;

public class Grid2DataToByte
extends JsonSerializer<Grid2Data> {
    public void serialize(Grid2Data value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeBinaryField("grid2Data", Grid2Data.gridToBytes((Grid2Data)value));
        gen.writeEndObject();
    }
}

