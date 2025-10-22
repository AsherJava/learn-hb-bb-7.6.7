/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.nr.datawarning.defines;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.datawarning.defines.DataWarningProperties;
import java.io.IOException;

public class DataWarningPropertiesSerializer
extends JsonSerializer<DataWarningProperties> {
    public void serialize(DataWarningProperties dataWarningProperties, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("fieldValue", (Object)dataWarningProperties.getFieldValue());
        gen.writeObjectField("preFieldLabel", (Object)dataWarningProperties.getPreFieldLabel());
        gen.writeObjectField("fieldCode", (Object)dataWarningProperties.getFieldCode());
        gen.writeObjectField("fieldLabel", (Object)dataWarningProperties.getFieldLabel());
        gen.writeObjectField("fieldCompareValue", (Object)dataWarningProperties.getFieldCompareValue());
        gen.writeObjectField("fieldCompareInput", (Object)dataWarningProperties.getFieldCompareInput());
        gen.writeObjectField("fieldFormulaInput", (Object)dataWarningProperties.getFieldFormulaInput());
        gen.writeObjectField("fieldCompareValLabel", (Object)dataWarningProperties.getFieldCompareValLabel());
        gen.writeObjectField("previewBackgroundColor", (Object)dataWarningProperties.getPreviewBackgroundColor());
        gen.writeObjectField("previewTextColor", (Object)dataWarningProperties.getPreviewTextColor());
        gen.writeObjectField("previewTextFontWeight", (Object)dataWarningProperties.getPreviewTextFontWeight());
        gen.writeObjectField("previewTextFontStyle", (Object)dataWarningProperties.getPreviewTextFontStyle());
        gen.writeObjectField("fieldSettingCode", (Object)dataWarningProperties.getFieldSettingCode());
        gen.writeObjectField("fieldRemarkTextAea", (Object)dataWarningProperties.getFieldRemarkTextAea());
        gen.writeObjectField("fieldIconCompareList", dataWarningProperties.getFieldIconCompareList());
        gen.writeObjectField("iconInputList", dataWarningProperties.getIconInputList());
        gen.writeObjectField("onlyIconCheckBox", (Object)dataWarningProperties.isOnlyIconCheckBox());
        gen.writeObjectField("reverseIconBtn", (Object)dataWarningProperties.isReverseIconBtn());
        gen.writeObjectField("minBarModel", (Object)dataWarningProperties.getMinBarModel());
        gen.writeObjectField("minBarInputModel", (Object)dataWarningProperties.getMinBarInputModel());
        gen.writeObjectField("maxBarModel", (Object)dataWarningProperties.getMaxBarModel());
        gen.writeObjectField("maxBarInputModel", (Object)dataWarningProperties.getMaxBarInputModel());
        gen.writeObjectField("barFillColor", (Object)dataWarningProperties.getBarFillColor());
        gen.writeObjectField("barBorderColor", (Object)dataWarningProperties.getBarBorderColor());
        gen.writeObjectField("barDirection", (Object)dataWarningProperties.getBarDirection());
        gen.writeObjectField("onlyShowBar", (Object)dataWarningProperties.isOnlyShowBar());
        gen.writeObjectField("negativefillColorRadio", (Object)dataWarningProperties.getNegativefillColorRadio());
        gen.writeObjectField("negativefillColorValue", (Object)dataWarningProperties.getNegativefillColorValue());
        gen.writeObjectField("negativeBorderColorRadio", (Object)dataWarningProperties.getNegativeBorderColorRadio());
        gen.writeObjectField("negativeBorderColorValue", (Object)dataWarningProperties.getNegativeBorderColorValue());
        gen.writeObjectField("axisSettingPosRadio", (Object)dataWarningProperties.getAxisSettingPosRadio());
        gen.writeObjectField("axisColorValue", (Object)dataWarningProperties.getAxisColorValue());
        gen.writeObjectField("firstGradualChangeColor", (Object)dataWarningProperties.getFirstGradualChangeColor());
        gen.writeObjectField("secondGradualChangeColor", (Object)dataWarningProperties.getSecondGradualChangeColor());
        gen.writeObjectField("thirdGradualChangeColor", (Object)dataWarningProperties.getThirdGradualChangeColor());
        gen.writeObjectField("middleBarModel", (Object)dataWarningProperties.getMiddleBarModel());
        gen.writeObjectField("middleBarInputModel", (Object)dataWarningProperties.getMiddleBarInputModel());
        gen.writeObjectField("fieldFormulaIsCheck", (Object)dataWarningProperties.getIsFieldFormulaIsCheck());
        gen.writeObjectField("fieldSettingTitle", (Object)dataWarningProperties.getFieldSettingTitle());
        gen.writeObjectField("cellTopOrLastSelectValue", (Object)dataWarningProperties.getCellTopOrLastSelectValue());
        gen.writeObjectField("cellTopOrLastSelectLabel", (Object)dataWarningProperties.getCellTopOrLastSelectLabel());
        gen.writeObjectField("cellTopOrLastIsPercent", (Object)dataWarningProperties.getIsCellTopOrLastIsPercent());
        gen.writeObjectField("cellAverageVal", (Object)dataWarningProperties.getCellAverageVal());
        gen.writeObjectField("fieldCompareInputTwo", (Object)dataWarningProperties.getFieldCompareInputTwo());
        gen.writeObjectField("iconGroupValue", (Object)dataWarningProperties.getIconGroupValue());
        gen.writeObjectField("preIsEnable", (Object)dataWarningProperties.getPreIsEnable());
        gen.writeObjectField("barFillModel", (Object)dataWarningProperties.getBarFillModel());
        gen.writeObjectField("barBorderModel", (Object)dataWarningProperties.getBarBorderModel());
        gen.writeObjectField("negativeHiddenSettingModel", (Object)dataWarningProperties.getNegativeHiddenSettingModel());
        gen.writeObjectField("barStyle", (Object)dataWarningProperties.getBarStyle());
        gen.writeEndObject();
    }
}

