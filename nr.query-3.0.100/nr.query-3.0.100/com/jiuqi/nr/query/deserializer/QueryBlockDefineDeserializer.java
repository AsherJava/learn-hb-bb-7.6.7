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
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.common.QueryBlockType;
import java.io.IOException;

public class QueryBlockDefineDeserializer
extends JsonDeserializer<QueryBlockDefine> {
    public QueryBlockDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryBlockDefine define = new QueryBlockDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String blockid = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("modalid");
        String code = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("title");
        String title = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("griddata");
        String griddata = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("formdata");
        String formdata = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("extension");
        String extension = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("printdata");
        String printdata = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("blockinfo");
        String blockinfo = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("masterinfo");
        String masterinfostr = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("isuserform");
        boolean hasUserForm = target != null && !target.isNull() ? Boolean.valueOf(target.asBoolean()) : null;
        target = jNode.findValue("page");
        String page = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("taskdefstartperiod");
        String taskDefStartPeriod = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("taskdefendperiod");
        String taskDefEndPeriod = target != null && !target.isNull() ? target.asText() : null;
        define.setBlockInfoStr(blockinfo);
        target = jNode.findValue("x");
        Integer posx = target != null && !target.isNull() ? target.asInt() : define.getBlockInfo().getPosX().intValue();
        target = jNode.findValue("y");
        Integer posy = target != null && !target.isNull() ? target.asInt() : define.getBlockInfo().getPosY().intValue();
        target = jNode.findValue("w");
        Integer width = target != null && !target.isNull() ? target.asInt() : define.getBlockInfo().getWidth().intValue();
        target = jNode.findValue("h");
        Integer height = target != null && !target.isNull() ? target.asInt() : define.getBlockInfo().getHeight().intValue();
        target = jNode.findValue("blocktype");
        String blockType = target != null && !target.isNull() ? target.asText() : define.getBlockType().getName();
        define.setHeight(height);
        define.setWidth(width);
        define.setPosY(posy);
        define.setPosX(posx);
        define.setId(blockid);
        define.setModelID(code);
        define.setTitle(title);
        define.setGridData(griddata);
        define.setFormdata(formdata);
        define.setPrintData(printdata);
        define.setQueryMastersStr(masterinfostr);
        define.setBlockExtension(extension);
        define.setHasUserForm(hasUserForm);
        define.setPageData(page);
        define.setTaskDefStartPeriod(taskDefStartPeriod);
        define.setTaskDefEndPeriod(taskDefEndPeriod);
        define.setBlockType(QueryBlockType.getType(blockType));
        return define;
    }
}

