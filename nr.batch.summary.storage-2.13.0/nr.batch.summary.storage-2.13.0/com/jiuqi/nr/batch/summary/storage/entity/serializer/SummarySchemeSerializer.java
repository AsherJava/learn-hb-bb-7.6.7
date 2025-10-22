/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.batch.summary.storage.entity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeUnit;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import java.io.IOException;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class SummarySchemeSerializer
extends JsonSerializer<SummarySchemeDefine> {
    private static final String KEY = "key";
    private static final String CODE = "code";
    private static final String TITLE = "title";
    private static final String TASK = "task";
    private static final String GROUP = "group";
    private static final String CORPORATE_ENTITY_TYPE_KEY = "corporateEntityType";
    private static final String TARGET_DIM = "targetDim";
    private static final String RANGE_UNIT = "rangeUnit";
    private static final String RANGE_FORM = "rangeForm";
    private static final String SINGLE_DIMS = "singleDims";
    private static final String TYPE_KEY = "type";
    private static final String VALUE_KEY = "value";
    private static final String ENTITY_ID = "entityId";
    private static final String CUSTOM_ROW_VALUE_KEY = "customValue";

    public void serialize(SummarySchemeDefine value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(KEY, value.getKey());
        gen.writeStringField(CODE, value.getCode());
        gen.writeStringField(TITLE, value.getTitle());
        gen.writeStringField(TASK, value.getTask());
        gen.writeStringField(GROUP, value.getGroup());
        gen.writeStringField(CORPORATE_ENTITY_TYPE_KEY, value.getCorporateEntityType());
        gen.writeStringField(ENTITY_ID, value.getEntityId());
        this.writeTargetDimInfo(value.getTargetDim(), gen);
        this.writeRangeUnitInfo(value.getRangeUnit(), gen);
        this.writeRangeFormInfo(value.getRangeForm(), gen);
        this.writeSingleDimInfo(value.getSingleDims(), gen);
        gen.writeEndObject();
    }

    private void writeTargetDimInfo(SchemeTargetDim value, JsonGenerator gen) throws IOException {
        gen.writeObjectFieldStart(TARGET_DIM);
        gen.writeNumberField(TYPE_KEY, value.getTargetDimType().value);
        switch (value.getTargetDimType()) {
            case BASE_DATA: 
            case CALIBRE: {
                gen.writeStringField(VALUE_KEY, value.getDimValue());
                break;
            }
            case CONDITION: {
                gen.writeObjectField(VALUE_KEY, (Object)TargetDimType.CONDITION.key);
                gen.writeObjectField(CUSTOM_ROW_VALUE_KEY, value.getCustomCalibreRows());
            }
        }
        gen.writeEndObject();
    }

    private void writeRangeUnitInfo(SchemeRangeUnit value, JsonGenerator gen) throws IOException {
        gen.writeObjectFieldStart(RANGE_UNIT);
        gen.writeNumberField(TYPE_KEY, value.getRangeUnitType().value);
        switch (value.getRangeUnitType()) {
            case ALL: {
                gen.writeStringField(VALUE_KEY, "");
                break;
            }
            case CHECK_UNIT: {
                gen.writeObjectField(VALUE_KEY, value.getCheckList());
                break;
            }
            case EXPRESSION: {
                gen.writeStringField(VALUE_KEY, value.getExpression());
            }
        }
        gen.writeEndObject();
    }

    private void writeRangeFormInfo(SchemeRangeForm value, JsonGenerator gen) throws IOException {
        gen.writeObjectFieldStart(RANGE_FORM);
        gen.writeNumberField(TYPE_KEY, value.getRangeFormType().value);
        gen.writeObjectField(VALUE_KEY, value.getFormList());
        gen.writeEndObject();
    }

    private void writeSingleDimInfo(List<SingleDim> value, JsonGenerator gen) throws IOException {
        gen.writeFieldName(SINGLE_DIMS);
        gen.writeStartArray();
        if (!CollectionUtils.isEmpty(value)) {
            for (SingleDim singleDim : value) {
                gen.writeStartObject();
                gen.writeStringField(ENTITY_ID, singleDim.getEntityId());
                gen.writeStringField(VALUE_KEY, singleDim.getValue());
                gen.writeEndObject();
            }
        }
        gen.writeEndArray();
    }
}

