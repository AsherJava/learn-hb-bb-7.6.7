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
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.common.OrderType;
import java.io.IOException;

public class QuerySelectItemDeserializer
extends JsonDeserializer<QuerySelectItem> {
    public QuerySelectItem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QuerySelectItem selectItem = new QuerySelectItem();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("demesionId");
        String demesionId = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("code");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isorder");
        boolean isOrder = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("ordertype");
        OrderType orderType = target != null && !target.isNull() ? OrderType.parse(target.asText()) : OrderType.ORDER_ASC;
        target = jNode.findValue("iscustom");
        boolean isCustom = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("customvalue");
        String customValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("sort");
        String sortinfor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("issorted");
        boolean isSorted = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("parentpath");
        String parentPath = target != null && !target.isNull() ? target.asText() : null;
        selectItem.setDemesionId(demesionId);
        selectItem.setCode(code);
        selectItem.setTitle(title);
        selectItem.setOrder(isOrder);
        selectItem.setOrderType(orderType);
        selectItem.setCustom(isCustom);
        selectItem.setCustomValue(customValue);
        selectItem.setSort(sortinfor);
        selectItem.setIsSorted(isSorted);
        selectItem.setParentPath(parentPath);
        return selectItem;
    }
}

