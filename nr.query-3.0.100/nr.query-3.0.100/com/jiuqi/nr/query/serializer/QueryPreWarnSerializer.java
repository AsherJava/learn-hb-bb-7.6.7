/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.query.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.query.block.QueryPreWarn;
import java.io.IOException;

public class QueryPreWarnSerializer
extends JsonSerializer<QueryPreWarn> {
    public void serialize(QueryPreWarn preWarn, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", (Object)preWarn.getId());
        gen.writeObjectField("preWareType", (Object)preWarn.getPreWareType());
        gen.writeObjectField("fieldValue", (Object)preWarn.getFieldValue());
        gen.writeObjectField("preFieldLabel", (Object)preWarn.getPreFieldLabel());
        gen.writeObjectField("fieldCode", (Object)preWarn.getFieldCode());
        gen.writeObjectField("fieldLabel", (Object)preWarn.getFieldLabel());
        gen.writeObjectField("fieldCompareValue", (Object)preWarn.getFieldCompareValue());
        gen.writeObjectField("fieldCompareInput", (Object)preWarn.getFieldCompareInput());
        gen.writeObjectField("fieldFormulaInput", (Object)preWarn.getFieldFormulaInput());
        gen.writeObjectField("fieldCompareValLabel", (Object)preWarn.getFieldCompareValLabel());
        gen.writeObjectField("previewBackgroundColor", (Object)preWarn.getPreviewBackgroundColor());
        gen.writeObjectField("previewTextColor", (Object)preWarn.getPreviewTextColor());
        gen.writeObjectField("previewTextFontWeight", (Object)preWarn.getPreviewTextFontWeight());
        gen.writeObjectField("previewTextFontStyle", (Object)preWarn.getPreviewTextFontStyle());
        gen.writeObjectField("fieldSettingCode", (Object)preWarn.getFieldSettingCode());
        gen.writeObjectField("fieldRemarkTextAea", (Object)preWarn.getFieldRemarkTextAea());
        gen.writeObjectField("iconCompareModel", preWarn.getFieldIconCompareList());
        gen.writeObjectField("iconInputList", preWarn.getIconInputList());
        gen.writeObjectField("onlyIconCheckBox", (Object)preWarn.isOnlyIconCheckBox());
        gen.writeObjectField("reverseIconBtn", (Object)preWarn.isReverseIconBtn());
        gen.writeObjectField("minBarModel", (Object)preWarn.getMinBarModel());
        gen.writeObjectField("minBarInputModel", (Object)preWarn.getMinBarInputModel());
        gen.writeObjectField("maxBarModel", (Object)preWarn.getMaxBarModel());
        gen.writeObjectField("maxBarInputModel", (Object)preWarn.getMaxBarInputModel());
        gen.writeObjectField("barFillColor", (Object)preWarn.getBarFillColor());
        gen.writeObjectField("barBorderColor", (Object)preWarn.getBarBorderColor());
        gen.writeObjectField("barDirection", (Object)preWarn.getBarDirection());
        gen.writeObjectField("onlyShowBar", (Object)preWarn.isOnlyShowBar());
        gen.writeObjectField("negativefillColorRadio", (Object)preWarn.getNegativefillColorRadio());
        gen.writeObjectField("negativefillColorValue", (Object)preWarn.getNegativefillColorValue());
        gen.writeObjectField("negativeBorderColorRadio", (Object)preWarn.getNegativeBorderColorRadio());
        gen.writeObjectField("negativeBorderColorValue", (Object)preWarn.getNegativeBorderColorValue());
        gen.writeObjectField("axisSettingPosRadio", (Object)preWarn.getAxisSettingPosRadio());
        gen.writeObjectField("axisColorValue", (Object)preWarn.getAxisColorValue());
        gen.writeObjectField("firstGradualChangeColor", (Object)preWarn.getFirstGradualChangeColor());
        gen.writeObjectField("secondGradualChangeColor", (Object)preWarn.getSecondGradualChangeColor());
        gen.writeObjectField("thirdGradualChangeColor", (Object)preWarn.getThirdGradualChangeColor());
        gen.writeObjectField("middleBarModel", (Object)preWarn.getMiddleBarModel());
        gen.writeObjectField("middleBarInputModel", (Object)preWarn.getMiddleBarInputModel());
        gen.writeObjectField("fieldFormulaIsCheck", (Object)preWarn.getIsFieldFormulaIsCheck());
        gen.writeObjectField("fieldSettingTitle", (Object)preWarn.getFieldSettingTitle());
        gen.writeObjectField("cellTopOrLastSelectValue", (Object)preWarn.getCellTopOrLastSelectValue());
        gen.writeObjectField("cellTopOrLastSelectLabel", (Object)preWarn.getCellTopOrLastSelectLabel());
        gen.writeObjectField("cellTopOrLastIsPercent", (Object)preWarn.getIsCellTopOrLastIsPercent());
        gen.writeObjectField("cellAverageVal", (Object)preWarn.getCellAverageVal());
        gen.writeObjectField("fieldCompareInputTwo", (Object)preWarn.getFieldCompareInputTwo());
        gen.writeObjectField("iconGroupValue", (Object)preWarn.getIconGroupValue());
        gen.writeObjectField("PreIsEnable", (Object)preWarn.getPreIsEnable());
        gen.writeObjectField("barFillModel", (Object)preWarn.getBarFillModel());
        gen.writeObjectField("barBorderModel", (Object)preWarn.getBarBorderModel());
        gen.writeObjectField("negativeHiddenSettingModel", (Object)preWarn.getNegativeHiddenSettingModel());
        gen.writeEndObject();
    }
}

