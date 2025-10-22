/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.bpm.custom.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeActionSet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorkFlowNodeActionSetDeserializer
extends JsonDeserializer<WorkFlowNodeActionSet> {
    public final String KEY_NEEDOPTDESC = "needOptDesc";
    public final String KEY_NEEDAUTOCALCULATE = "needAutoCalculate";
    public final String KEY_NEEDAUTOCHECK = "needAutoCheck";
    public final String KEY_ERROR_PASSTYPE = "error_passType";
    public final String KEY_WARNNING_PASSTYPE = "warnning_passType";
    public final String KEY_INFO_PASSTYPE = "info_passType";
    public final String KEY_COUNTERSIGN = "countersign";
    public final String KEY_COUNTERSIGN_COUNT = "countersign_count";
    public final String KEY_SIGN_USER = "sign_user";
    public final String KEY_EXSET = "exset";

    public WorkFlowNodeActionSet deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        WorkFlowNodeActionSet set = new WorkFlowNodeActionSet();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        set.setNeedOptDesc(this.getBooleanValue(jNode, this.KEY_NEEDOPTDESC));
        set.setNeedAutoCalculate(this.getBooleanValue(jNode, this.KEY_NEEDAUTOCALCULATE));
        set.setNeedAutoCheck(this.getBooleanValue(jNode, this.KEY_NEEDAUTOCHECK));
        set.setError_passType(this.getIntValue(jNode, this.KEY_ERROR_PASSTYPE));
        set.setWarnning_passType(this.getIntValue(jNode, this.KEY_WARNNING_PASSTYPE));
        set.setInfo_passType(this.getIntValue(jNode, this.KEY_INFO_PASSTYPE));
        set.setExset(this.getMapValue(jNode, this.KEY_EXSET));
        return set;
    }

    private Map<String, String> getMapValue(JsonNode jNode, String key) {
        HashMap<String, String> map = new HashMap<String, String>();
        JsonNode target = jNode.findValue(key);
        if (target != null && target.isArray()) {
            ArrayNode arrObj = (ArrayNode)target;
            for (int i = 0; i < arrObj.size(); ++i) {
                JsonNode obj = arrObj.get(i);
                Iterator fields = obj.fields();
                while (fields.hasNext()) {
                    Map.Entry next = (Map.Entry)fields.next();
                    map.put((String)next.getKey(), ((JsonNode)next.getValue()).asText(null));
                }
            }
        }
        return map;
    }

    private boolean getBooleanValue(JsonNode jNode, String key) {
        JsonNode target = jNode.findValue(key);
        if (target != null && !target.isNull()) {
            return 1 == target.asInt();
        }
        return false;
    }

    private int getIntValue(JsonNode jNode, String key) {
        JsonNode target = jNode.findValue(key);
        if (target != null && !target.isNull()) {
            return target.asInt();
        }
        return 0;
    }

    private String getStringValue(JsonNode jNode, String key) {
        JsonNode target = jNode.findValue(key);
        if (target != null && !target.isNull()) {
            return target.asText();
        }
        return "";
    }
}

