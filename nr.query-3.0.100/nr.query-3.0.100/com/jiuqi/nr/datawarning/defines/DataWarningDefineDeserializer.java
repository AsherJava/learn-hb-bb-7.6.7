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
package com.jiuqi.nr.datawarning.defines;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.datawarning.defines.DataWarnigScop;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import java.io.IOException;

public class DataWarningDefineDeserializer
extends JsonDeserializer<DataWarningDefine> {
    public DataWarningDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DataWarningDefine dataWarningDefine = new DataWarningDefine();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("warnType");
        String warnType = target != null && !target.isNull() ? target.asText() : DataWarningType.CELL.toString();
        target = jNode.findValue("properties");
        String properties = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("Key");
        String key = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("identify");
        String identify = target != null && !target.isNull() ? target.asText() : DataWarningIdentify.IDENTIFY_QUERY.toString();
        target = jNode.findValue("scop");
        String scop = target != null && !target.isNull() ? target.asText() : DataWarnigScop.COL.toString();
        target = jNode.findValue("order");
        String order = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldCode");
        String fieldCode = target != null && !target.isNull() ? target.asText() : "";
        target = jNode.findValue("fieldSettingCode");
        String fieldSettingCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("isSave");
        Boolean isSave = target != null && !target.isNull() ? target.asBoolean() : false;
        dataWarningDefine.setId(id);
        dataWarningDefine.setWarnType(DataWarningType.valueOf(warnType));
        dataWarningDefine.setProperties(properties);
        dataWarningDefine.setPropertyStr(properties);
        dataWarningDefine.setKey(key);
        dataWarningDefine.setIdentify(DataWarningIdentify.valueOf(identify));
        dataWarningDefine.setScop(DataWarnigScop.valueOf(scop));
        dataWarningDefine.setOrder(order);
        dataWarningDefine.setFieldCode(fieldCode);
        dataWarningDefine.setFieldSettingCode(fieldSettingCode);
        dataWarningDefine.setIsSave(isSave);
        return dataWarningDefine;
    }
}

