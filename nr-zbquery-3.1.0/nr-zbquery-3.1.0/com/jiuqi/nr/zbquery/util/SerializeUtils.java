/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.JsonToken
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.deser.DeserializationProblemHandler
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.zbquery.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.FormulaType;
import com.jiuqi.nr.zbquery.model.NullRowDisplayMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.QueryOption;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SerializeUtils {
    public static byte[] jsonSerializeToByte(ZBQueryModel zbQueryModel) throws JsonProcessingException {
        if (null == zbQueryModel) {
            return new byte[0];
        }
        ObjectMapper objectMapper = new ObjectMapper();
        zbQueryModel.getOption().setDisplayRowCheck(false);
        return objectMapper.writeValueAsBytes((Object)zbQueryModel);
    }

    public static ZBQueryModel jsonDeserialize(byte[] byteArray) throws IOException {
        if (null == byteArray || byteArray.length == 0) {
            return null;
        }
        String strValue = new String(byteArray, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addHandler((DeserializationProblemHandler)new QueryOptionDeserializationProblemHandler());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ZBQueryModel model = (ZBQueryModel)objectMapper.readValue(byteArray, ZBQueryModel.class);
        model.getOption().setDisplayRowCheck(false);
        JsonNode jsonNode = objectMapper.readTree(strValue);
        if (!jsonNode.has("version")) {
            model.setVersion(1);
        }
        if (model.getVersion() == 1) {
            boolean hasCustomFormula = false;
            for (QueryObject field : model.getQueryObjects()) {
                if (field.getType() != QueryObjectType.FORMULA || ((FormulaField)field).getFormulaType() != FormulaType.CUSTOM) continue;
                hasCustomFormula = true;
                break;
            }
            if (!hasCustomFormula) {
                model.setVersion(2);
            }
        }
        QueryObject periodDim13 = null;
        for (QueryDimension dim : model.getDimensions()) {
            dim.setMaxFiscalMonth(-1);
            dim.setMinFiscalMonth(-1);
            if (dim.getDimensionType() == QueryDimensionType.PERIOD) {
                if (!PeriodUtil.is13Period(dim)) continue;
                PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
                IPeriodEntity periodEntity = periodEngineService.getPeriodAdapter().getPeriodEntity(BqlTimeDimUtils.getPeriodEntityId((String)dim.getName()));
                dim.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
                dim.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
                periodDim13 = dim;
                continue;
            }
            if (dim.getDimensionType() != QueryDimensionType.CHILD || periodDim13 == null || !periodDim13.getFullName().equals(dim.getParent()) || dim.getPeriodType() != PeriodType.MONTH) continue;
            dim.setMinFiscalMonth(((QueryDimension)periodDim13).getMinFiscalMonth());
            dim.setMaxFiscalMonth(((QueryDimension)periodDim13).getMaxFiscalMonth());
        }
        return model;
    }

    private static class QueryOptionDeserializationProblemHandler
    extends DeserializationProblemHandler {
        private QueryOptionDeserializationProblemHandler() {
        }

        public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
            if ("showNullRow".equals(propertyName)) {
                if (p.getCurrentValue() instanceof QueryOption) {
                    QueryOption o = (QueryOption)p.getParsingContext().getCurrentValue();
                    if (p.getCurrentToken() == JsonToken.VALUE_FALSE) {
                        o.setNullRowDisplayMode(NullRowDisplayMode.DEFAULT);
                    } else if (p.getCurrentToken() == JsonToken.VALUE_TRUE) {
                        o.setNullRowDisplayMode(NullRowDisplayMode.DISPLAY_ALLNULL);
                    }
                }
                return true;
            }
            return false;
        }
    }
}

