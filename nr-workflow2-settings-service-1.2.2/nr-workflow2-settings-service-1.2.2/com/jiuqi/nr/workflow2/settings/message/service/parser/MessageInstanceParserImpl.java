/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfo
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfoImpl
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInstanceParser
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItem
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItemImpl
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.VariableInfo
 */
package com.jiuqi.nr.workflow2.settings.message.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfo;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfoImpl;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInstanceParser;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItem;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItemImpl;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.VariableInfo;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageInstanceVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageInstanceParserImpl
implements MessageInstanceParser {
    public MessageInfo parseToMessageInfo(String notificationEventParam, Map<String, String> variableReplaceMap) {
        MessageInstanceVO messageInstanceVO;
        ObjectMapper mapper = new ObjectMapper();
        try {
            messageInstanceVO = (MessageInstanceVO)mapper.readValue(notificationEventParam, MessageInstanceVO.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MessageInfoImpl messageInfo = new MessageInfoImpl();
        messageInfo.setTitle(messageInstanceVO.getTitle());
        messageInfo.setSubject(messageInstanceVO.getSubject());
        messageInfo.setContent(this.parse_Html(messageInstanceVO.getContent(), variableReplaceMap));
        messageInfo.setReceiver(this.transferToReceiverItems(messageInstanceVO.getReceiver()));
        return messageInfo;
    }

    private String parse_Html(String content, Map<String, String> variableReplaceMap) {
        ArrayList<VariableInfo> variables = new ArrayList<VariableInfo>();
        int index_span = 0;
        for (int index_span_end = 0; index_span_end < content.length() - 1; index_span_end += "</span>".length()) {
            index_span = content.indexOf("<span", index_span_end);
            index_span_end = content.indexOf("</span>", index_span_end);
            if (index_span == -1 || index_span_end == -1) break;
            String variableContent = content.substring(index_span + "<span".length(), index_span_end);
            Map<String, String> property = this.parseVariableProperty(variableContent);
            String classStyle = property.get("class");
            if (classStyle == null || !classStyle.equals("w-e-tag")) continue;
            VariableInfo variableInfo = new VariableInfo();
            variableInfo.setIndex_start(index_span);
            variableInfo.setIndex_end(index_span_end + "</span>".length());
            variableInfo.setProperties(property);
            variables.add(variableInfo);
        }
        StringBuilder replaceRowContent = new StringBuilder(content);
        if (!variables.isEmpty()) {
            variables.sort((v1, v2) -> v2.getIndex_start() - v1.getIndex_start());
            for (VariableInfo variableInfo : variables) {
                Map properties = variableInfo.getProperties();
                String variableCode = (String)properties.get("data-code");
                String replaceContent = variableReplaceMap.computeIfAbsent(variableCode, k -> "");
                replaceRowContent.replace(variableInfo.getIndex_start(), variableInfo.getIndex_end(), replaceContent);
            }
        }
        return replaceRowContent.toString();
    }

    private String parse(String content, Map<String, String> variableReplaceMap) {
        StringBuilder result = new StringBuilder();
        int index_p = 0;
        for (int index_p_end = 0; index_p_end < content.length() - 1; index_p_end += "</p>".length()) {
            index_p = content.indexOf("<p>", index_p_end);
            index_p_end = content.indexOf("</p>", index_p_end);
            String rowContent = content.substring(index_p + "<p>".length(), index_p_end);
            ArrayList<VariableInfo> variables = new ArrayList<VariableInfo>();
            int index_span = 0;
            for (int index_span_end = 0; rowContent.contains("<span") && rowContent.contains("</span>") && index_span_end < rowContent.length() - 1; index_span_end += "</span>".length()) {
                index_span = rowContent.indexOf("<span", index_span_end);
                index_span_end = rowContent.indexOf("</span>", index_span_end);
                if (index_span == -1 || index_span_end == -1) break;
                String variableContent = rowContent.substring(index_span + "<span".length(), index_span_end);
                VariableInfo variableInfo = new VariableInfo();
                variableInfo.setIndex_start(index_span);
                variableInfo.setIndex_end(index_span_end + "</span>".length());
                variableInfo.setProperties(this.parseVariableProperty(variableContent));
                variables.add(variableInfo);
            }
            if (!variables.isEmpty()) {
                variables.sort((v1, v2) -> v2.getIndex_start() - v1.getIndex_start());
                StringBuilder replaceRowContent = new StringBuilder(rowContent);
                for (VariableInfo variableInfo : variables) {
                    Map properties = variableInfo.getProperties();
                    String variableCode = (String)properties.get("data-code");
                    String replaceContent = variableReplaceMap.computeIfAbsent(variableCode, k -> "");
                    replaceRowContent.replace(variableInfo.getIndex_start(), variableInfo.getIndex_end(), replaceContent);
                }
                rowContent = replaceRowContent.toString();
            }
            if (result.length() != 0) {
                result.append("<br>");
            }
            result.append(rowContent);
        }
        return result.toString();
    }

    private Map<String, String> parseVariableProperty(String content) {
        String[] pairs;
        HashMap<String, String> map = new HashMap<String, String>();
        String[] parts = content.split(">");
        for (String pair : pairs = parts[0].trim().split(" ")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length != 2) continue;
            String key = keyValue[0];
            String value = keyValue[1].replace("\"", "");
            map.put(key, value);
        }
        return map;
    }

    private List<ReceiverItem> transferToReceiverItems(Object receiverParam) {
        List receiverList = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(receiverParam);
            receiverList = (List)mapper.readValue(json, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReceiverItem> items = new ArrayList<ReceiverItem>();
        for (Map receiverItem : receiverList) {
            Map userAndRoleMap;
            ReceiverItemImpl item = new ReceiverItemImpl();
            item.setStrategy(receiverItem.get("strategy").toString());
            try {
                String json = mapper.writeValueAsString(receiverItem.get("param"));
                userAndRoleMap = (Map)mapper.readValue(json, (TypeReference)new TypeReference<Map<String, List<String>>>(){});
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            item.setUsers((List)userAndRoleMap.get("user"));
            item.setRoles((List)userAndRoleMap.get("role"));
            items.add((ReceiverItem)item);
        }
        return items;
    }
}

