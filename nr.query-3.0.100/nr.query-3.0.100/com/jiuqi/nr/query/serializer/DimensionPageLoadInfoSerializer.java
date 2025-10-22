/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.block.DimensionPageLoadInfo;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionPageLoadInfoSerializer
extends JsonSerializer<DimensionPageLoadInfo> {
    private static final Logger log = LoggerFactory.getLogger(DimensionPageLoadInfoSerializer.class);

    public void serialize(DimensionPageLoadInfo dimPageLoadInfo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeStartObject();
            gen.writeObjectField("dimensionRows", dimPageLoadInfo.dimensionRows);
            gen.writeObjectField("recordIndex", dimPageLoadInfo.recordIndex);
            gen.writeObjectField("recordStart", dimPageLoadInfo.recordStart);
            gen.writeObjectField("firstDimensionID", (Object)dimPageLoadInfo.firstDimensionID);
            gen.writeObjectField("strucNode", (Object)dimPageLoadInfo.strucNode);
            gen.writeObjectField("dimensionName", (Object)dimPageLoadInfo.dimensionName);
            gen.writeObjectField("parentDepth", (Object)dimPageLoadInfo.parentDepth);
            gen.writeObjectField("passedRows", dimPageLoadInfo.passedRows);
            gen.writeObjectField("hasDataItems", dimPageLoadInfo.hasDataItems);
            gen.writeObjectField("isFirstPage", (Object)dimPageLoadInfo.isFirstPage);
            gen.writeObjectField("detailStart", (Object)dimPageLoadInfo.detailStart);
            gen.writeObjectField("detailEnd", (Object)dimPageLoadInfo.detailEnd);
            gen.writeObjectField("lastItem", (Object)dimPageLoadInfo.lastItem);
            gen.writeObjectField("lastDimensionName", (Object)dimPageLoadInfo.lastDimensionName);
            gen.writeObjectField("lastItemCount", (Object)dimPageLoadInfo.lastItemCount);
            gen.writeObjectField("lastDimensionRecord", dimPageLoadInfo.lastDimensionRecord);
            gen.writeObjectField("lastDimensionValueSet", (Object)dimPageLoadInfo.lastDimValueSetStr);
            gen.writeEndObject();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}

