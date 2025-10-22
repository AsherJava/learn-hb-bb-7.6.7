/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JacksonException
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nr.batch.summary.storage.entity.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeUnit;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreRowDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SchemeRangeFormInfo;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SchemeRangeUnitInfo;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SchemeTargetDimInfo;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SingleDimDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.entity.serializer.CustomCalibreRowDeserializer;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeFormType;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeUnitType;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SummarySchemeDeserializer
extends JsonDeserializer<SummarySchemeDefine> {
    private static final String KEY = "key";
    private static final String CODE = "code";
    private static final String TITLE = "title";
    private static final String TASK = "task";
    private static final String GROUP = "group";
    private static final String CORPORATE_ENTITY_TYPE_KEY = "corporateEntityType";
    private static final String TARGET_DIM = "targetDim";
    private static final String RANGE_UNIT = "rangeUnit";
    private static final String RANGE_FORM = "rangeForm";
    private static final String ENTITY_ID = "entityId";
    private static final String TYPE_KEY = "type";
    private static final String VALUE_KEY = "value";
    private static final String CUSTOM_ROW_VALUE_KEY = "customValue";
    private static final String SINGLE_DIMS = "singleDims";

    public SummarySchemeDefine deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        if (!jNode.isNull()) {
            SummarySchemeDefine impl = new SummarySchemeDefine();
            JsonNode target = jNode.findValue(KEY);
            impl.setKey(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(CODE);
            impl.setCode(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(TITLE);
            impl.setTitle(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(TASK);
            impl.setTask(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(GROUP);
            impl.setGroup(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(CORPORATE_ENTITY_TYPE_KEY);
            impl.setCorporateEntityType(target != null && !target.isNull() ? target.asText() : null);
            target = jNode.findValue(ENTITY_ID);
            impl.setEntityId(target != null && !target.isNull() ? target.asText() : null);
            impl.setTargetDim(this.getTargetDim(impl, jNode.findValue(TARGET_DIM)));
            impl.setRangeUnit(this.getRangeUnit(impl, jNode.findValue(RANGE_UNIT)));
            impl.setRangeForm(this.getRangeForm(impl, jNode.findValue(RANGE_FORM)));
            impl.setSingleDims(this.getSingleDims(jNode.findValue(SINGLE_DIMS)));
            return impl;
        }
        return null;
    }

    private List<SingleDim> getSingleDims(JsonNode value) {
        if (value != null && value.isArray()) {
            ArrayList<SingleDim> singleDims = new ArrayList<SingleDim>();
            for (JsonNode node : value) {
                SingleDim dim = this.parseSingleDimManually(node);
                if (dim == null) continue;
                singleDims.add(dim);
            }
            return singleDims;
        }
        return null;
    }

    private SingleDim parseSingleDimManually(JsonNode node) {
        if (node == null || !node.isObject()) {
            return null;
        }
        SingleDimDefine dim = new SingleDimDefine();
        if (node.has(ENTITY_ID)) {
            dim.setEntityId(node.get(ENTITY_ID).asText());
        }
        if (node.has(VALUE_KEY)) {
            dim.setValue(node.get(VALUE_KEY).asText());
        }
        return dim;
    }

    private SchemeTargetDim getTargetDim(SummarySchemeDefine impl, JsonNode jNode) throws IOException {
        SchemeTargetDimInfo targetDim = new SchemeTargetDimInfo();
        JsonNode target = jNode.findValue(TYPE_KEY);
        Integer type = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        TargetDimType targetDimType = TargetDimType.valueOf(type);
        targetDim.setTargetDimType(targetDimType);
        if (targetDimType != null) {
            target = jNode.findValue(VALUE_KEY);
            switch (targetDimType) {
                case BASE_DATA: 
                case CALIBRE: {
                    targetDim.setDimValue(target.asText());
                    break;
                }
                case CONDITION: {
                    targetDim.setDimValue(target.asText());
                    targetDim.setCustomCalibreRows(this.getCustomCalibreRows(jNode.findValue(CUSTOM_ROW_VALUE_KEY)));
                }
            }
        }
        return targetDim;
    }

    private List<CustomCalibreRow> getCustomCalibreRows(JsonNode target) {
        ArrayList<CustomCalibreRowDefine> rows = null;
        if (target.isArray()) {
            rows = new ArrayList<CustomCalibreRowDefine>();
            ArrayNode arrNode = (ArrayNode)target;
            for (int i = 0; i < arrNode.size(); ++i) {
                CustomCalibreRowDefine customCalibreRowDefine = BatchSummaryUtils.toJavaBean(arrNode.get(i).toString(), CustomCalibreRowDefine.class, new CustomCalibreRowDeserializer());
                rows.add(customCalibreRowDefine);
            }
        }
        return rows;
    }

    private SchemeRangeUnit getRangeUnit(SummarySchemeDefine impl, JsonNode jNode) {
        SchemeRangeUnitInfo rangeUnitInfo = new SchemeRangeUnitInfo();
        JsonNode target = jNode.findValue(TYPE_KEY);
        Integer type = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        RangeUnitType rangeUnitType = RangeUnitType.valueOf(type);
        if (rangeUnitType != null) {
            rangeUnitInfo.setRangeUnitType(rangeUnitType);
            target = jNode.findValue(VALUE_KEY);
            if (target != null && !target.isNull()) {
                if (RangeUnitType.CHECK_UNIT.equals((Object)rangeUnitType) && target.isArray()) {
                    ArrayList<String> checkList = new ArrayList<String>();
                    ArrayNode arrNode = (ArrayNode)target;
                    for (int i = 0; i < arrNode.size(); ++i) {
                        JsonNode node = arrNode.get(i);
                        if (node == null || node.isNull()) continue;
                        checkList.add(node.asText());
                    }
                    rangeUnitInfo.setCheckList(checkList);
                } else if (RangeUnitType.EXPRESSION.equals((Object)rangeUnitType)) {
                    rangeUnitInfo.setExpression(target.asText());
                }
            }
        }
        return rangeUnitInfo;
    }

    private SchemeRangeForm getRangeForm(SummarySchemeDefine impl, JsonNode jNode) {
        SchemeRangeFormInfo rangeForm = new SchemeRangeFormInfo();
        JsonNode target = jNode.findValue(TYPE_KEY);
        Integer type = target != null && !target.isNull() ? Integer.valueOf(target.asInt()) : null;
        RangeFormType rangeFormType = RangeFormType.valueOf(type);
        if (rangeFormType != null) {
            rangeForm.setRangeFormType(rangeFormType);
            target = jNode.findValue(VALUE_KEY);
            if (target != null && !target.isNull() && RangeFormType.CUSTOM.equals((Object)rangeFormType) && target.isArray()) {
                ArrayList<String> formList = new ArrayList<String>();
                ArrayNode arrNode = (ArrayNode)target;
                for (int i = 0; i < arrNode.size(); ++i) {
                    JsonNode node = arrNode.get(i);
                    if (node == null || node.isNull()) continue;
                    formList.add(node.asText());
                }
                rangeForm.setFormList(formList);
            }
        }
        return rangeForm;
    }
}

