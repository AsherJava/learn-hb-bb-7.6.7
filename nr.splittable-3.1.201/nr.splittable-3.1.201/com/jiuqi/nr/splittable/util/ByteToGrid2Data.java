/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.splittable.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;

public class ByteToGrid2Data
extends JsonDeserializer<Grid2Data> {
    public Grid2Data deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode jsonNode = (JsonNode)p.getCodec().readTree(p);
        byte[] grid2Data = new byte[]{};
        if (jsonNode.has("grid2Data")) {
            grid2Data = jsonNode.get("grid2Data").binaryValue();
        }
        return Grid2Data.bytesToGrid((byte[])grid2Data);
    }
}

