/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.bpm.custom.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkFlowDefineSaveDeserializer
extends JsonDeserializer<WorkFlowDefineSaveEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowDefineSaveDeserializer.class);

    public WorkFlowDefineSaveEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        WorkFlowDefineSaveEntity saveObj = new WorkFlowDefineSaveEntity();
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode j_define = jNode.findValue("define");
        WorkFlowDefine define = (WorkFlowDefine)mapper.readValue(j_define.traverse(p.getCodec()), WorkFlowDefine.class);
        saveObj.setDefine(define);
        saveObj.setCreat_nodes(this.getWorkFlowNodeSet(jNode, "creat_nodes", mapper, p));
        saveObj.setUpdate_nodes(this.getWorkFlowNodeSet(jNode, "update_nodes", mapper, p));
        saveObj.setDel_nodes(this.getDelString(jNode, "del_nodes", mapper, p));
        saveObj.setCreat_lines(this.getWorkFlowLine(jNode, "creat_lines", mapper, p));
        saveObj.setUpdate_lines(this.getWorkFlowLine(jNode, "update_lines", mapper, p));
        saveObj.setDel_lines(this.getDelString(jNode, "del_lines", mapper, p));
        saveObj.setCreat_actions(this.getWorkFlowAction(jNode, "creat_actions", mapper, p));
        saveObj.setUpdate_actions(this.getWorkFlowAction(jNode, "update_actions", mapper, p));
        saveObj.setDel_actions(this.getDelString(jNode, "del_actions", mapper, p));
        saveObj.setCreat_participants(this.getWorkFlowParticipant(jNode, "creat_participants", mapper, p));
        saveObj.setUpdate_participants(this.getWorkFlowParticipant(jNode, "update_participants", mapper, p));
        saveObj.setDel_participants(this.getDelString(jNode, "del_participants", mapper, p));
        return saveObj;
    }

    private List<WorkFlowNodeSet> getWorkFlowNodeSet(JsonNode jNode, String key, ObjectMapper mapper, JsonParser p) {
        ArrayList<WorkFlowNodeSet> nodes = new ArrayList<WorkFlowNodeSet>();
        JsonNode target = jNode.get(key);
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            arr.forEach(e -> {
                try {
                    WorkFlowNodeSet node = (WorkFlowNodeSet)mapper.readValue(e.traverse(p.getCodec()), WorkFlowNodeSet.class);
                    nodes.add(node);
                }
                catch (IOException e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            });
        }
        return nodes;
    }

    private List<WorkFlowLine> getWorkFlowLine(JsonNode jNode, String key, ObjectMapper mapper, JsonParser p) {
        ArrayList<WorkFlowLine> lines = new ArrayList<WorkFlowLine>();
        JsonNode target = jNode.get(key);
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            arr.forEach(e -> {
                try {
                    WorkFlowLine line = (WorkFlowLine)mapper.readValue(e.traverse(p.getCodec()), WorkFlowLine.class);
                    lines.add(line);
                }
                catch (IOException e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            });
        }
        return lines;
    }

    private List<WorkFlowAction> getWorkFlowAction(JsonNode jNode, String key, ObjectMapper mapper, JsonParser p) {
        ArrayList<WorkFlowAction> actions = new ArrayList<WorkFlowAction>();
        JsonNode target = jNode.get(key);
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            arr.forEach(e -> {
                try {
                    WorkFlowAction a = (WorkFlowAction)mapper.readValue(e.traverse(p.getCodec()), WorkFlowAction.class);
                    actions.add(a);
                }
                catch (IOException e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            });
        }
        return actions;
    }

    private List<WorkFlowParticipant> getWorkFlowParticipant(JsonNode jNode, String key, ObjectMapper mapper, JsonParser p) {
        ArrayList<WorkFlowParticipant> particis = new ArrayList<WorkFlowParticipant>();
        JsonNode target = jNode.get(key);
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            arr.forEach(e -> {
                try {
                    WorkFlowParticipant partici = (WorkFlowParticipant)mapper.readValue(e.traverse(p.getCodec()), WorkFlowParticipant.class);
                    particis.add(partici);
                }
                catch (IOException e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            });
        }
        return particis;
    }

    private List<String> getDelString(JsonNode jNode, String key, ObjectMapper mapper, JsonParser p) {
        ArrayList<String> delstr = new ArrayList<String>();
        JsonNode target = jNode.get(key);
        if (target != null && target.isArray()) {
            ArrayNode arr = (ArrayNode)target;
            arr.forEach(e -> {
                try {
                    String str = (String)mapper.readValue(e.traverse(p.getCodec()), String.class);
                    delstr.add(str);
                }
                catch (IOException e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            });
        }
        return delstr;
    }
}

