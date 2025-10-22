/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.block.DimensionPageLoadInfo;
import java.io.IOException;

public class DimensionPageLoadInfoDeserializer
extends JsonDeserializer<DimensionPageLoadInfo> {
    public DimensionPageLoadInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DimensionPageLoadInfo dimPageLoadInfo = new DimensionPageLoadInfo();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("dimensionRows");
        String dimensionRowsStr = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("recordIndex");
        String recordIndexStr = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("recordStart");
        String recordStartStr = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("firstDimensionID");
        String firstDimensionID = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("strucNode");
        String strucNode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("dimensionName");
        String dimensionName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("parentDepth");
        Integer parentDepth = target != null && !target.isNull() ? target.asInt() : -1;
        target = jNode.findValue("passedRows");
        String passedRowsStr = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("hasDataItems");
        String hasDataItemsStr = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("isFirstPage");
        boolean isFirstPage = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        target = jNode.findValue("detailStart");
        Integer detailStart = target != null && !target.isNull() ? target.asInt() : -1;
        target = jNode.findValue("detailEnd");
        Integer detailEnd = target != null && !target.isNull() ? target.asInt() : -1;
        target = jNode.findValue("lastItem");
        String lastItem = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("lastDimensionName");
        String lastDimName = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("lastItemCount");
        Integer lastItemCount = target != null && !target.isNull() ? target.asInt() : -1;
        target = jNode.findValue("lastDimensionRecord");
        String lastDimensionRecordStr = target != null && !target.isNull() ? target.toString() : null;
        dimPageLoadInfo.dimensionRows = dimPageLoadInfo.getDimensionRows(dimensionRowsStr);
        dimPageLoadInfo.recordIndex = dimPageLoadInfo.getRecordIndex(recordIndexStr);
        dimPageLoadInfo.recordStart = dimPageLoadInfo.getRecordStart(recordStartStr);
        dimPageLoadInfo.firstDimensionID = firstDimensionID;
        dimPageLoadInfo.dimensionName = dimensionName;
        dimPageLoadInfo.strucNode = strucNode;
        dimPageLoadInfo.parentDepth = parentDepth;
        dimPageLoadInfo.passedRows = dimPageLoadInfo.getPassedRows(passedRowsStr);
        dimPageLoadInfo.hasDataItems = dimPageLoadInfo.gethasDataItems(hasDataItemsStr);
        dimPageLoadInfo.isFirstPage = isFirstPage;
        dimPageLoadInfo.detailStart = detailStart;
        dimPageLoadInfo.detailEnd = detailEnd;
        dimPageLoadInfo.lastItem = lastItem;
        dimPageLoadInfo.lastDimensionName = lastDimName;
        dimPageLoadInfo.lastItemCount = lastItemCount;
        dimPageLoadInfo.lastDimensionRecord = dimPageLoadInfo.getLastDimensionRecord(lastDimensionRecordStr);
        dimPageLoadInfo.lastDimValueSetStr = lastDimensionRecordStr;
        return dimPageLoadInfo;
    }
}

