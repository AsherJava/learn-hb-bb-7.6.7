/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.designer.common.Grid2DataDeserializeGege;
import com.jiuqi.nr.designer.sync.IAction;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.vo.FormSaveObject;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FormSaveObjectDeserializer
extends JsonDeserializer<FormSaveObject> {
    public FormSaveObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode formulaObjsNode;
        String id;
        JsonNode progressId;
        JsonNode ifUpdateFormula;
        JsonNode syncActions;
        String activedSchemeId;
        JsonNode activedSchemeIdNode;
        String grid;
        JsonNode gridNode;
        String formObj;
        FormSaveObject formObject = new FormSaveObject();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserializeGege());
        module.setAbstractTypes(AnalysisFormParamDefineImpl.getDefaultResolver());
        mapper.registerModule((Module)module);
        JsonNode node = (JsonNode)mapper.readTree(p);
        JsonNode formDataNode = node.get("formData");
        if (formDataNode != null && StringUtils.isNotEmpty((String)(formObj = formDataNode.toString()))) {
            FormObj formData = (FormObj)mapper.readValue(formObj, FormObj.class);
            formObject.setFormData(formData);
        }
        if ((gridNode = node.get("gridMap")) != null && StringUtils.isNotEmpty((String)(grid = gridNode.toString()))) {
            Map gridMap = (Map)mapper.readValue(grid, (TypeReference)new TypeReference<Map<Integer, Grid2Data>>(){});
            formObject.setGridMap(gridMap);
        }
        if ((activedSchemeIdNode = node.get("activedSchemeId")) != null && StringUtils.isNotEmpty((String)(activedSchemeId = activedSchemeIdNode.textValue()))) {
            formObject.setActivedSchemeId(activedSchemeId);
        }
        if (null != (syncActions = node.get("syncActions"))) {
            String actions = syncActions.toString();
            formObject.setSyncActions((List)mapper.readValue(actions, (TypeReference)new TypeReference<List<IAction>>(){}));
        }
        if (null != (ifUpdateFormula = node.get("ifUpdateFormula"))) {
            boolean tag = ifUpdateFormula.booleanValue();
            formObject.setIfUpdateFormula(tag);
        }
        if (null != (progressId = node.get("progressId")) && StringUtils.isNotEmpty((String)(id = progressId.toString()))) {
            formObject.setProgressId(id);
        }
        if (null != (formulaObjsNode = node.get("formulaObjs"))) {
            String formulaObjsNodeStr = formulaObjsNode.toString();
            formObject.setFormulaObjs((List)mapper.readValue(formulaObjsNodeStr, (TypeReference)new TypeReference<List<FormulaObj>>(){}));
        }
        return formObject;
    }
}

