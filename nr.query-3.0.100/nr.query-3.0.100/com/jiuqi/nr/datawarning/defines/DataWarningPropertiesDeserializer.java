/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.datawarning.defines;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.datawarning.defines.DataWarningProperties;
import com.jiuqi.nr.query.block.FilterSymbols;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

public class DataWarningPropertiesDeserializer
extends JsonDeserializer<DataWarningProperties> {
    public DataWarningProperties deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DataWarningProperties dataWarningProperties = new DataWarningProperties();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("fieldValue");
        String fieldValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("preFieldLabel");
        String preFieldLabel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldCode");
        String fieldCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldLabel");
        String fieldLabel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldCompareValue");
        String fieldCompareValue = target != null && !target.isNull() ? target.asText() : FilterSymbols.MORETHAN.toString();
        target = jNode.findValue("fieldCompareInput");
        String fieldCompareInput = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldFormulaInput");
        String fieldFormulaInput = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldCompareValLabel");
        String fieldCompareValLabel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("previewBackgroundColor");
        String previewBackgroundColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("previewTextColor");
        String previewTextColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("previewTextFontWeight");
        String previewTextFontWeight = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("previewTextFontStyle");
        String previewTextFontStyle = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldSettingCode");
        String fieldSettingCode = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldRemarkTextAea");
        String fieldRemarkTextAea = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldIconCompareList");
        String iconCompareList = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("iconInputList");
        String iconInputList = target != null && !target.isNull() ? target.toString() : null;
        target = jNode.findValue("onlyIconCheckBox");
        boolean onlyIconCheckBox = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("reverseIconBtn");
        boolean reverseIconBtn = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("minBarModel");
        String minBarModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("minBarInputModel");
        String minBarInputModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("maxBarModel");
        String maxBarModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("maxBarInputModel");
        String maxBarInputModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("barFillColor");
        String barFillColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("barBorderColor");
        String barBorderColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("barDirection");
        String barDirection = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("onlyShowBar");
        boolean onlyShowBar = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("negativefillColorRadio");
        String negativefillColorRadio = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("negativefillColorValue");
        String negativefillColorValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("negativeBorderColorRadio");
        String negativeBorderColorRadio = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("negativeBorderColorValue");
        String negativeBorderColorValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("axisSettingPosRadio");
        String axisSettingPosRadio = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("axisColorValue");
        String axisColorValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("firstGradualChangeColor");
        String firstGradualChangeColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("secondGradualChangeColor");
        String secondGradualChangeColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("thirdGradualChangeColor");
        String thirdGradualChangeColor = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("middleBarModel");
        String middleBarModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("middleBarInputModel");
        String middleBarInputModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldFormulaIsCheck");
        boolean fieldFormulaIsCheck = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("fieldSettingTitle");
        String fieldSettingTitle = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("cellTopOrLastSelectValue");
        String cellTopOrLastSelectValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("cellTopOrLastSelectLabel");
        String cellTopOrLastSelectLabel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("cellTopOrLastIsPercent");
        boolean cellTopOrLastIsPercent = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("cellAverageVal");
        String cellAverageVal = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("fieldCompareInputTwo");
        String fieldCompareInputTwo = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("iconGroupValue");
        String iconGroupValue = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("barFillModel");
        String barFillModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("barBorderModel");
        String barBorderModel = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("preIsEnable");
        boolean preIsEnable = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("negativeHiddenSettingModel");
        boolean negativeHiddenSettingModel = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("barStyle");
        String barStyle = target != null && !target.isNull() ? target.asText() : null;
        dataWarningProperties.setFieldValue(fieldValue);
        dataWarningProperties.setPreFieldLabel(preFieldLabel);
        dataWarningProperties.setFieldCode(fieldCode);
        dataWarningProperties.setFieldLabel(fieldLabel);
        if (!StringUtil.isNullOrEmpty((String)fieldCompareValue)) {
            dataWarningProperties.setFieldCompareValue(FilterSymbols.valueOf(fieldCompareValue));
        }
        dataWarningProperties.setFieldCompareInput(fieldCompareInput);
        dataWarningProperties.setFieldFormulaInput(fieldFormulaInput);
        dataWarningProperties.setFieldCompareValLabel(fieldCompareValLabel);
        dataWarningProperties.setPreviewBackgroundColor(previewBackgroundColor);
        dataWarningProperties.setPreviewTextColor(previewTextColor);
        dataWarningProperties.setPreviewTextFontWeight(previewTextFontWeight);
        dataWarningProperties.setPreviewTextFontStyle(previewTextFontStyle);
        dataWarningProperties.setFieldSettingCode(fieldSettingCode);
        dataWarningProperties.setFieldRemarkTextAea(fieldRemarkTextAea);
        dataWarningProperties.setBarFillModell(barFillModel);
        dataWarningProperties.setBarBorderModel(barBorderModel);
        dataWarningProperties.setPreIsEnable(preIsEnable);
        if (iconCompareList != null) {
            dataWarningProperties.setFieldIconCompareListStr(iconCompareList);
        }
        if (iconInputList != null) {
            dataWarningProperties.setIconInputListStr(iconInputList);
        }
        dataWarningProperties.setOnlyIconCheckBox(onlyIconCheckBox);
        dataWarningProperties.setReverseIconBtn(reverseIconBtn);
        dataWarningProperties.setMinBarModel(minBarModel);
        dataWarningProperties.setMinBarInputModel(minBarInputModel);
        dataWarningProperties.setMaxBarModel(maxBarModel);
        dataWarningProperties.setMaxBarInputModel(maxBarInputModel);
        dataWarningProperties.setBarFillColor(barFillColor);
        dataWarningProperties.setBarBorderColor(barBorderColor);
        dataWarningProperties.setBarDirection(barDirection);
        dataWarningProperties.setOnlyShowBar(onlyShowBar);
        dataWarningProperties.setNegativefillColorRadio(negativefillColorRadio);
        dataWarningProperties.setNegativefillColorValue(negativefillColorValue);
        dataWarningProperties.setNegativeBorderColorRadio(negativeBorderColorRadio);
        dataWarningProperties.setNegativeBorderColorValue(negativeBorderColorValue);
        dataWarningProperties.setAxisSettingPosRadio(axisSettingPosRadio);
        dataWarningProperties.setAxisColorValue(axisColorValue);
        dataWarningProperties.setFirstGradualChangeColor(firstGradualChangeColor);
        dataWarningProperties.setSecondGradualChangeColor(secondGradualChangeColor);
        dataWarningProperties.setThirdGradualChangeColor(thirdGradualChangeColor);
        dataWarningProperties.setMiddleBarModel(middleBarModel);
        dataWarningProperties.setMiddleBarInputModel(middleBarInputModel);
        dataWarningProperties.setFieldFormulaIsCheck(fieldFormulaIsCheck);
        dataWarningProperties.setFieldSettingTitle(fieldSettingTitle);
        dataWarningProperties.setCellTopOrLastSelectValue(cellTopOrLastSelectValue);
        dataWarningProperties.setCellTopOrLastSelectLabel(cellTopOrLastSelectLabel);
        dataWarningProperties.setCellTopOrLastIsPercent(cellTopOrLastIsPercent);
        dataWarningProperties.setCellAverageVal(cellAverageVal);
        dataWarningProperties.setFieldCompareInputTwo(fieldCompareInputTwo);
        dataWarningProperties.setIconGroupValue(iconGroupValue);
        dataWarningProperties.setNegativeHiddenSettingModel(negativeHiddenSettingModel);
        dataWarningProperties.setBarStyle(barStyle);
        return dataWarningProperties;
    }
}

