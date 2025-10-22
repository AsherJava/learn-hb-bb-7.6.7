/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.query.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.block.QueryPreWarn;
import com.jiuqi.nr.query.common.PreWareType;
import java.io.IOException;

public class QueryPreWarnDeserializer
extends JsonDeserializer<QueryPreWarn> {
    public QueryPreWarn deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        QueryPreWarn queryPreWarn = new QueryPreWarn();
        JsonNode jNode = (JsonNode)p.getCodec().readTree(p);
        JsonNode target = jNode.findValue("id");
        String id = target != null && !target.isNull() ? target.asText() : null;
        target = jNode.findValue("preWareType");
        String preWareType = target != null && !target.isNull() ? target.asText() : PreWareType.CELL.toString();
        target = jNode.findValue("fieldValue");
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
        target = jNode.findValue("iconCompareModel");
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
        target = jNode.findValue("PreIsEnable");
        boolean preIsEnable = target != null && !target.isNull() ? target.asBoolean() : false;
        target = jNode.findValue("negativeHiddenSettingModel");
        boolean negativeHiddenSettingModel = target != null && !target.isNull() ? target.asBoolean() : false;
        queryPreWarn.setId(id);
        queryPreWarn.setPreWareType(PreWareType.valueOf(preWareType));
        queryPreWarn.setFieldValue(fieldValue);
        queryPreWarn.setPreFieldLabel(preFieldLabel);
        queryPreWarn.setFieldCode(fieldCode);
        queryPreWarn.setFieldLabel(fieldLabel);
        queryPreWarn.setFieldCompareValue(FilterSymbols.valueOf(fieldCompareValue));
        queryPreWarn.setFieldCompareInput(fieldCompareInput);
        queryPreWarn.setFieldFormulaInput(fieldFormulaInput);
        queryPreWarn.setFieldCompareValLabel(fieldCompareValLabel);
        queryPreWarn.setPreviewBackgroundColor(previewBackgroundColor);
        queryPreWarn.setPreviewTextColor(previewTextColor);
        queryPreWarn.setPreviewTextFontWeight(previewTextFontWeight);
        queryPreWarn.setPreviewTextFontStyle(previewTextFontStyle);
        queryPreWarn.setFieldSettingCode(fieldSettingCode);
        queryPreWarn.setFieldRemarkTextAea(fieldRemarkTextAea);
        queryPreWarn.setBarFillModell(barFillModel);
        queryPreWarn.setBarBorderModel(barBorderModel);
        queryPreWarn.setPreIsEnable(preIsEnable);
        if (iconCompareList != null) {
            queryPreWarn.setFieldIconCompareListStr(iconCompareList);
        }
        if (iconInputList != null) {
            queryPreWarn.setIconInputListStr(iconInputList);
        }
        queryPreWarn.setOnlyIconCheckBox(onlyIconCheckBox);
        queryPreWarn.setReverseIconBtn(reverseIconBtn);
        queryPreWarn.setMinBarModel(minBarModel);
        queryPreWarn.setMinBarInputModel(minBarInputModel);
        queryPreWarn.setMaxBarModel(maxBarModel);
        queryPreWarn.setMaxBarInputModel(maxBarInputModel);
        queryPreWarn.setBarFillColor(barFillColor);
        queryPreWarn.setBarBorderColor(barBorderColor);
        queryPreWarn.setBarDirection(barDirection);
        queryPreWarn.setOnlyShowBar(onlyShowBar);
        queryPreWarn.setNegativefillColorRadio(negativefillColorRadio);
        queryPreWarn.setNegativefillColorValue(negativefillColorValue);
        queryPreWarn.setNegativeBorderColorRadio(negativeBorderColorRadio);
        queryPreWarn.setNegativeBorderColorValue(negativeBorderColorValue);
        queryPreWarn.setAxisSettingPosRadio(axisSettingPosRadio);
        queryPreWarn.setAxisColorValue(axisColorValue);
        queryPreWarn.setFirstGradualChangeColor(firstGradualChangeColor);
        queryPreWarn.setSecondGradualChangeColor(secondGradualChangeColor);
        queryPreWarn.setThirdGradualChangeColor(thirdGradualChangeColor);
        queryPreWarn.setMiddleBarModel(middleBarModel);
        queryPreWarn.setMiddleBarInputModel(middleBarInputModel);
        queryPreWarn.setFieldFormulaIsCheck(fieldFormulaIsCheck);
        queryPreWarn.setFieldSettingTitle(fieldSettingTitle);
        queryPreWarn.setCellTopOrLastSelectValue(cellTopOrLastSelectValue);
        queryPreWarn.setCellTopOrLastSelectLabel(cellTopOrLastSelectLabel);
        queryPreWarn.setCellTopOrLastIsPercent(cellTopOrLastIsPercent);
        queryPreWarn.setCellAverageVal(cellAverageVal);
        queryPreWarn.setFieldCompareInputTwo(fieldCompareInputTwo);
        queryPreWarn.setIconGroupValue(iconGroupValue);
        queryPreWarn.setNegativeHiddenSettingModel(negativeHiddenSettingModel);
        return queryPreWarn;
    }
}

