/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser$Feature
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 */
package com.jiuqi.nr.bpm.upload.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.upload.utils.DefaultButton;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionAlias
implements IActionAlias {
    private static final Logger logger = LoggerFactory.getLogger(ActionAlias.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public Map<String, String> actionCodeAndStateName(String formSchemeKey) {
        HashedMap<String, String> actionMap = null;
        try {
            String taskKey;
            TaskDefine taskDefine;
            TaskFlowsDefine flowsSetting;
            boolean defaultButton;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null && (defaultButton = (flowsSetting = (taskDefine = this.runTimeViewController.queryTaskDefine(taskKey = formScheme.getTaskKey())).getFlowsSetting()).getDefaultButtonName())) {
                DefaultButton[] values;
                actionMap = new HashedMap<String, String>();
                String defaultButtonName = flowsSetting.getDefaultButtonNameConfig();
                ObjectMapper objectMapper = new ObjectMapper();
                Map parseObject = (Map)objectMapper.readValue(defaultButtonName, (TypeReference)new TypeReference<Map<String, Map<String, String>>>(){});
                for (DefaultButton name : values = DefaultButton.values()) {
                    Map jsonObject;
                    String actionCode = name.getActionCode();
                    if (DefaultButton.NOSHANGBAO.getActionCode().equals(actionCode) || DefaultButton.NOSONGSHEN.getActionCode().equals(actionCode)) {
                        if (!formScheme.getFlowsSetting().isUnitSubmitForCensorship()) {
                            jsonObject = (Map)parseObject.get("act_upload");
                            if (jsonObject == null) continue;
                            actionMap.put("start", "\u672a" + (String)jsonObject.get("rename"));
                            continue;
                        }
                        jsonObject = (Map)parseObject.get("act_submit");
                        if (jsonObject == null) continue;
                        actionMap.put("start", "\u672a" + (String)jsonObject.get("rename"));
                        continue;
                    }
                    jsonObject = (Map)parseObject.get(actionCode);
                    if (jsonObject == null) continue;
                    actionMap.put(actionCode, (String)jsonObject.get("statename"));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    @Override
    public Map<String, String> actionStateCodeAndStateName(String formSchemeKey) {
        HashedMap<String, String> actionMap = null;
        try {
            String taskKey;
            TaskDefine taskDefine;
            TaskFlowsDefine flowsSetting;
            boolean defaultButton;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null && (defaultButton = (flowsSetting = (taskDefine = this.runTimeViewController.queryTaskDefine(taskKey = formScheme.getTaskKey())).getFlowsSetting()).getDefaultButtonName())) {
                DefaultButton[] values;
                actionMap = new HashedMap<String, String>();
                String defaultButtonName = flowsSetting.getDefaultButtonNameConfig();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                Map parseObject = (Map)objectMapper.readValue(defaultButtonName, (TypeReference)new TypeReference<Map<String, Map<String, String>>>(){});
                for (DefaultButton name : values = DefaultButton.values()) {
                    Map jsonObject;
                    String actionCode = name.getActionCode();
                    String stateCode = name.getStateCode();
                    if (DefaultButton.NOSHANGBAO.getActionCode().equals(actionCode)) {
                        jsonObject = (Map)parseObject.get("act_upload");
                        if (jsonObject == null) continue;
                        actionMap.put(stateCode, "\u672a" + (String)jsonObject.get("rename"));
                        continue;
                    }
                    if (DefaultButton.NOSONGSHEN.getActionCode().equals(actionCode)) {
                        if (!formScheme.getFlowsSetting().isUnitSubmitForCensorship() || (jsonObject = (Map)parseObject.get("act_submit")) == null) continue;
                        actionMap.put(stateCode, "\u672a" + (String)jsonObject.get("rename"));
                        continue;
                    }
                    jsonObject = (Map)parseObject.get(actionCode);
                    if (jsonObject == null) continue;
                    actionMap.put(stateCode, (String)jsonObject.get("statename"));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    @Override
    public Map<String, String> actionCodeAndActionName(String formSchemeKey) {
        HashedMap actionMap = null;
        try {
            String taskKey;
            TaskDefine taskDefine;
            TaskFlowsDefine flowsSetting;
            boolean defaultButton;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null && (defaultButton = (flowsSetting = (taskDefine = this.runTimeViewController.queryTaskDefine(taskKey = formScheme.getTaskKey())).getFlowsSetting()).getDefaultButtonName())) {
                DefaultButton[] values;
                actionMap = new HashedMap();
                String defaultButtonName = flowsSetting.getDefaultButtonNameConfig();
                ObjectMapper objectMapper = new ObjectMapper();
                Map parseObject = (Map)objectMapper.readValue(defaultButtonName, (TypeReference)new TypeReference<Map<String, Map<String, String>>>(){});
                for (DefaultButton name : values = DefaultButton.values()) {
                    Map jsonObject;
                    String actionCode = name.getActionCode();
                    if (DefaultButton.NOSHANGBAO.getActionCode().equals(actionCode) || DefaultButton.NOSONGSHEN.getActionCode().equals(actionCode) || (jsonObject = (Map)parseObject.get(actionCode)) == null) continue;
                    actionMap.put(actionCode, jsonObject.get("rename"));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    @Override
    public Map<String, String> nodeAndNodeName(String formSchemeKey) {
        HashedMap<String, String> nodeMap = null;
        try {
            String taskKey;
            TaskDefine taskDefine;
            TaskFlowsDefine flowsSetting;
            boolean defaultNodeName;
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null && (defaultNodeName = (flowsSetting = (taskDefine = this.runTimeViewController.queryTaskDefine(taskKey = formScheme.getTaskKey())).getFlowsSetting()).getDefaultNodeName())) {
                nodeMap = new HashedMap<String, String>();
                ObjectMapper objectMapper = new ObjectMapper();
                String defaultNodeNameConfig = flowsSetting.getDefaultNodeNameConfig();
                Map parseObject = (Map)objectMapper.readValue(defaultNodeNameConfig, (TypeReference)new TypeReference<Map<String, Map<String, String>>>(){});
                if (!parseObject.isEmpty()) {
                    for (Map.Entry obj : parseObject.entrySet()) {
                        String key = (String)obj.getKey();
                        Map value = (Map)obj.getValue();
                        if (value.isEmpty()) continue;
                        String rename = (String)value.get("rename");
                        nodeMap.put(key, rename);
                        if (!"tsk_audit".equals(key)) continue;
                        nodeMap.put("tsk_audit_after_confirm", rename);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return nodeMap;
    }
}

