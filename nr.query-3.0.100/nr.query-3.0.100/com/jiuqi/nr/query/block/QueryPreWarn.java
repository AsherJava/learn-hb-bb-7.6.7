/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.block.FilterSymbols;
import com.jiuqi.nr.query.common.PreWareType;
import com.jiuqi.nr.query.deserializer.QueryPreWarnDeserializer;
import com.jiuqi.nr.query.serializer.QueryPreWarnSerializer;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@JsonSerialize(using=QueryPreWarnSerializer.class)
@JsonDeserialize(using=QueryPreWarnDeserializer.class)
public class QueryPreWarn {
    private static final Logger log = LoggerFactory.getLogger(QueryPreWarn.class);
    public static final String PREWARN_ID = "id";
    public static final String PREWARE_TYPE = "preWareType";
    public static final String PREWARE_FIELDVALUE = "fieldValue";
    public static final String PREWARE_PREFIELDLABEL = "preFieldLabel";
    public static final String PREWARE_FIELDCODE = "fieldCode";
    public static final String PREWARE_FIELDLABEL = "fieldLabel";
    public static final String PREWARE_FIELDCOMPAREVALUE = "fieldCompareValue";
    public static final String PREWARE_FIELDCOMPAREINPUT = "fieldCompareInput";
    public static final String PREWARE_FIELDFORMULAINPUT = "fieldFormulaInput";
    public static final String PREWARE_FIELDCOMPAREVALLABEL = "fieldCompareValLabel";
    public static final String PREWARE_PREVIEWBACKGROUNDCOLOR = "previewBackgroundColor";
    public static final String PREWARE_PREVIEWTEXTCOLOR = "previewTextColor";
    public static final String PREWARE_PREVIEWTEXTFONTWEIGHT = "previewTextFontWeight";
    public static final String PREWARE_PREVIEWTEXTFONTSTYLE = "previewTextFontStyle";
    public static final String PREWARE_FIELDSETTINGCODE = "fieldSettingCode";
    public static final String PREWARE_FIELDREMARKTEXTAEA = "fieldRemarkTextAea";
    public static final String PREWARE_FIELDICONCOMPARELIST = "iconCompareModel";
    public static final String PREWARE_FIELDICONINPUTLIST = "iconInputList";
    public static final String PREWARE_ONLYICONCHECKBOX = "onlyIconCheckBox";
    public static final String PREWARE_REVERSEICONBTN = "reverseIconBtn";
    public static final String PREWARE_MINBARMODEL = "minBarModel";
    public static final String PREWARE_MINBARINPUTMODEL = "minBarInputModel";
    public static final String PREWARE_MAXBARMODEL = "maxBarModel";
    public static final String PREWARE_MAXBARINPUTMODEL = "maxBarInputModel";
    public static final String PREWARE_BARFILLCOLOR = "barFillColor";
    public static final String PREWARE_BARBORDERCOLOR = "barBorderColor";
    public static final String PREWARE_BARDIRECTION = "barDirection";
    public static final String PREWARE_ONLYSHOWBAR = "onlyShowBar";
    public static final String PREWARE_NEGATIVEFILLCOLORRADIO = "negativefillColorRadio";
    public static final String PREWARE_NEGATIVEFILLCOLORVALUE = "negativefillColorValue";
    public static final String PREWARE_NEGATIVEBORDERCOLORRADIO = "negativeBorderColorRadio";
    public static final String PREWARE_NEGATIVEBORDERCOLORVALUE = "negativeBorderColorValue";
    public static final String PREWARE_AXISSETTINGPOSRADIO = "axisSettingPosRadio";
    public static final String PREWARE_AXISCOLORVALUE = "axisColorValue";
    public static final String PREWARE_FIRSTGRADUALCHANGECOLOR = "firstGradualChangeColor";
    public static final String PREWARE_SECONDGRADUALCHANGECOLOR = "secondGradualChangeColor";
    public static final String PREWARE_THIRDGRADUALCHANGECOLOR = "thirdGradualChangeColor";
    public static final String PREWARE_MIDDLEBARMODEL = "middleBarModel";
    public static final String PREWARE_MIDDLEBARINPUTMODEL = "middleBarInputModel";
    public static final String PREWARE_FIELDFORMULAISCHECK = "fieldFormulaIsCheck";
    public static final String PREWARE_FIELDSETTINGTITLE = "fieldSettingTitle";
    public static final String PREWARE_CELLTOPORLASTSELECTVALUE = "cellTopOrLastSelectValue";
    public static final String PREWARE_CELLTOPORLASTSELECTLABEL = "cellTopOrLastSelectLabel";
    public static final String PREWARE_CELLTOPORLASTISPERCENT = "cellTopOrLastIsPercent";
    public static final String PREWARE_CELLAVERAGEVAL = "cellAverageVal";
    public static final String PREWARE_FIELDCOMPAREINPUTTWO = "fieldCompareInputTwo";
    public static final String PREWARE_ICONGROUPVALUE = "iconGroupValue";
    public static final String PREWARE_BARFILLMODEL = "barFillModel";
    public static final String PREWARE_BARBORDERMODEL = "barBorderModel";
    public static final String PREWARE_PREISENABLE = "PreIsEnable";
    public static final String PREWARE_NEGATIVEHIDDENSETTINGMODEL = "negativeHiddenSettingModel";
    private String id;
    private PreWareType preWareType;
    private String fieldValue;
    private String preFieldLabel;
    private String fieldCode;
    private String fieldLabel;
    private FilterSymbols fieldCompareValue;
    private String fieldCompareInput;
    private String fieldFormulaInput;
    private String fieldCompareValLabel;
    private String previewBackgroundColor;
    private String previewTextColor;
    private String previewTextFontWeight;
    private String previewTextFontStyle;
    private String fieldSettingCode;
    private String fieldRemarkTextAea;
    private List<String> fieldIconCompareList;
    private List<Integer> iconInputList;
    private boolean onlyIconCheckBox;
    private boolean reverseIconBtn;
    private String minBarModel;
    private String minBarInputModel;
    private String maxBarModel;
    private String maxBarInputModel;
    private String barFillColor;
    private String barBorderColor;
    private String barDirection;
    private boolean onlyShowBar;
    private String negativefillColorRadio;
    private String negativefillColorValue;
    private String negativeBorderColorRadio;
    private String negativeBorderColorValue;
    private String axisSettingPosRadio;
    private String axisColorValue;
    private String firstGradualChangeColor;
    private String secondGradualChangeColor;
    private String thirdGradualChangeColor;
    private String middleBarModel;
    private String middleBarInputModel;
    private boolean fieldFormulaIsCheck;
    private String fieldSettingTitle;
    private String cellTopOrLastSelectValue;
    private String cellTopOrLastSelectLabel;
    private boolean cellTopOrLastIsPercent;
    private String cellAverageVal;
    private String fieldCompareInputTwo;
    private String iconGroupValue;
    private String barFillModel;
    private String barBorderModel;
    private Boolean preIsEnable;
    private Boolean negativeHiddenSettingModel;

    public Boolean getNegativeHiddenSettingModel() {
        return this.negativeHiddenSettingModel;
    }

    public void setNegativeHiddenSettingModel(boolean negativeHiddenSettingModel) {
        this.negativeHiddenSettingModel = negativeHiddenSettingModel;
    }

    public String getBarFillModel() {
        return this.barFillModel;
    }

    public void setBarFillModell(String barFillModel) {
        this.barFillModel = barFillModel;
    }

    public String getBarBorderModel() {
        return this.barBorderModel;
    }

    public void setBarBorderModel(String barBorderModel) {
        this.barBorderModel = barBorderModel;
    }

    public Boolean getPreIsEnable() {
        return this.preIsEnable;
    }

    public void setPreIsEnable(Boolean preIsEnable) {
        this.preIsEnable = preIsEnable;
    }

    public String getIconGroupValue() {
        return this.iconGroupValue;
    }

    public void setIconGroupValue(String iconGroupValue) {
        this.iconGroupValue = iconGroupValue;
    }

    public boolean getIsFieldFormulaIsCheck() {
        return this.fieldFormulaIsCheck;
    }

    public void setFieldFormulaIsCheck(boolean fieldFormulaIsCheck) {
        this.fieldFormulaIsCheck = fieldFormulaIsCheck;
    }

    public String getFieldSettingTitle() {
        return this.fieldSettingTitle;
    }

    public void setFieldSettingTitle(String fieldSettingTitle) {
        this.fieldSettingTitle = fieldSettingTitle;
    }

    public String getCellTopOrLastSelectValue() {
        return this.cellTopOrLastSelectValue;
    }

    public void setCellTopOrLastSelectValue(String cellTopOrLastSelectValue) {
        this.cellTopOrLastSelectValue = cellTopOrLastSelectValue;
    }

    public String getCellTopOrLastSelectLabel() {
        return this.cellTopOrLastSelectLabel;
    }

    public void setCellTopOrLastSelectLabel(String cellTopOrLastSelectLabel) {
        this.cellTopOrLastSelectLabel = cellTopOrLastSelectLabel;
    }

    public boolean getIsCellTopOrLastIsPercent() {
        return this.cellTopOrLastIsPercent;
    }

    public void setCellTopOrLastIsPercent(boolean cellTopOrLastIsPercent) {
        this.cellTopOrLastIsPercent = cellTopOrLastIsPercent;
    }

    public String getCellAverageVal() {
        return this.cellAverageVal;
    }

    public void setCellAverageVal(String cellAverageVal) {
        this.cellAverageVal = cellAverageVal;
    }

    public String getFieldCompareInputTwo() {
        return this.fieldCompareInputTwo;
    }

    public void setFieldCompareInputTwo(String fieldCompareInputTwo) {
        this.fieldCompareInputTwo = fieldCompareInputTwo;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PreWareType getPreWareType() {
        return this.preWareType;
    }

    public void setPreWareType(PreWareType preWareType) {
        this.preWareType = preWareType;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getPreFieldLabel() {
        return this.preFieldLabel;
    }

    public void setPreFieldLabel(String preFieldLabel) {
        this.preFieldLabel = preFieldLabel;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldLabel() {
        return this.fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public FilterSymbols getFieldCompareValue() {
        return this.fieldCompareValue;
    }

    public void setFieldCompareValue(FilterSymbols fieldCompareValue) {
        this.fieldCompareValue = fieldCompareValue;
    }

    public String getFieldCompareInput() {
        return this.fieldCompareInput;
    }

    public void setFieldCompareInput(String fieldCompareInput) {
        this.fieldCompareInput = fieldCompareInput;
    }

    public String getFieldFormulaInput() {
        return this.fieldFormulaInput;
    }

    public void setFieldFormulaInput(String fieldFormulaInput) {
        this.fieldFormulaInput = fieldFormulaInput;
    }

    public String getFieldCompareValLabel() {
        return this.fieldCompareValLabel;
    }

    public void setFieldCompareValLabel(String fieldCompareValLabel) {
        this.fieldCompareValLabel = fieldCompareValLabel;
    }

    public String getPreviewBackgroundColor() {
        return this.previewBackgroundColor;
    }

    public void setPreviewBackgroundColor(String previewBackgroundColor) {
        this.previewBackgroundColor = previewBackgroundColor;
    }

    public String getPreviewTextColor() {
        return this.previewTextColor;
    }

    public void setPreviewTextColor(String previewTextColor) {
        this.previewTextColor = previewTextColor;
    }

    public String getPreviewTextFontWeight() {
        return this.previewTextFontWeight;
    }

    public void setPreviewTextFontWeight(String previewTextFontWeight) {
        this.previewTextFontWeight = previewTextFontWeight;
    }

    public String getPreviewTextFontStyle() {
        return this.previewTextFontStyle;
    }

    public void setPreviewTextFontStyle(String previewTextFontStyle) {
        this.previewTextFontStyle = previewTextFontStyle;
    }

    public String getFieldSettingCode() {
        return this.fieldSettingCode;
    }

    public void setFieldSettingCode(String fieldSettingCode) {
        this.fieldSettingCode = fieldSettingCode;
    }

    public String getFieldRemarkTextAea() {
        return this.fieldRemarkTextAea;
    }

    public void setFieldRemarkTextAea(String fieldRemarkTextAea) {
        this.fieldRemarkTextAea = fieldRemarkTextAea;
    }

    public List<String> getFieldIconCompareList() {
        return this.fieldIconCompareList;
    }

    public void setFieldIconCompareList(List<String> fieldIconCompareList) {
        this.fieldIconCompareList = fieldIconCompareList;
    }

    public String getFieldIconCompareListStr() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.fieldIconCompareList);
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }

    public void setFieldIconCompareListStr(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.setFieldIconCompareList((List)mapper.readValue(str, List.class));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<Integer> getIconInputList() {
        return this.iconInputList;
    }

    public void setIconInputList(List<Integer> iconInputList) {
        this.iconInputList = iconInputList;
    }

    public String getIconInputListStr() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.iconInputList);
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }

    public void setIconInputListStr(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.setIconInputList((List)mapper.readValue(str, List.class));
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public boolean isOnlyIconCheckBox() {
        return this.onlyIconCheckBox;
    }

    public void setOnlyIconCheckBox(boolean onlyIconCheckBox) {
        this.onlyIconCheckBox = onlyIconCheckBox;
    }

    public boolean isReverseIconBtn() {
        return this.reverseIconBtn;
    }

    public void setReverseIconBtn(boolean reverseIconBtn) {
        this.reverseIconBtn = reverseIconBtn;
    }

    public String getMinBarModel() {
        return this.minBarModel;
    }

    public void setMinBarModel(String minBarModel) {
        this.minBarModel = minBarModel;
    }

    public String getMinBarInputModel() {
        return this.minBarInputModel;
    }

    public void setMinBarInputModel(String minBarInputModel) {
        this.minBarInputModel = minBarInputModel;
    }

    public String getMaxBarModel() {
        return this.maxBarModel;
    }

    public void setMaxBarModel(String maxBarModel) {
        this.maxBarModel = maxBarModel;
    }

    public String getMaxBarInputModel() {
        return this.maxBarInputModel;
    }

    public void setMaxBarInputModel(String maxBarInputModel) {
        this.maxBarInputModel = maxBarInputModel;
    }

    public String getBarFillColor() {
        return this.barFillColor;
    }

    public void setBarFillColor(String barFillColor) {
        this.barFillColor = barFillColor;
    }

    public String getBarBorderColor() {
        return this.barBorderColor;
    }

    public void setBarBorderColor(String barBorderColor) {
        this.barBorderColor = barBorderColor;
    }

    public String getBarDirection() {
        return this.barDirection;
    }

    public void setBarDirection(String barDirection) {
        this.barDirection = barDirection;
    }

    public boolean isOnlyShowBar() {
        return this.onlyShowBar;
    }

    public void setOnlyShowBar(boolean onlyShowBar) {
        this.onlyShowBar = onlyShowBar;
    }

    public String getNegativefillColorRadio() {
        return this.negativefillColorRadio;
    }

    public void setNegativefillColorRadio(String negativefillColorRadio) {
        this.negativefillColorRadio = negativefillColorRadio;
    }

    public String getNegativefillColorValue() {
        return this.negativefillColorValue;
    }

    public void setNegativefillColorValue(String negativefillColorValue) {
        this.negativefillColorValue = negativefillColorValue;
    }

    public String getNegativeBorderColorRadio() {
        return this.negativeBorderColorRadio;
    }

    public void setNegativeBorderColorRadio(String negativeBorderColorRadio) {
        this.negativeBorderColorRadio = negativeBorderColorRadio;
    }

    public String getNegativeBorderColorValue() {
        return this.negativeBorderColorValue;
    }

    public void setNegativeBorderColorValue(String negativeBorderColorValue) {
        this.negativeBorderColorValue = negativeBorderColorValue;
    }

    public String getAxisSettingPosRadio() {
        return this.axisSettingPosRadio;
    }

    public void setAxisSettingPosRadio(String axisSettingPosRadio) {
        this.axisSettingPosRadio = axisSettingPosRadio;
    }

    public String getAxisColorValue() {
        return this.axisColorValue;
    }

    public void setAxisColorValue(String axisColorValue) {
        this.axisColorValue = axisColorValue;
    }

    public String getFirstGradualChangeColor() {
        return this.firstGradualChangeColor;
    }

    public void setFirstGradualChangeColor(String firstGradualChangeColor) {
        this.firstGradualChangeColor = firstGradualChangeColor;
    }

    public String getSecondGradualChangeColor() {
        return this.secondGradualChangeColor;
    }

    public void setSecondGradualChangeColor(String secondGradualChangeColor) {
        this.secondGradualChangeColor = secondGradualChangeColor;
    }

    public String getThirdGradualChangeColor() {
        return this.thirdGradualChangeColor;
    }

    public void setThirdGradualChangeColor(String thirdGradualChangeColor) {
        this.thirdGradualChangeColor = thirdGradualChangeColor;
    }

    public String getMiddleBarModel() {
        return this.middleBarModel;
    }

    public void setMiddleBarModel(String middleBarModel) {
        this.middleBarModel = middleBarModel;
    }

    public String getMiddleBarInputModel() {
        return this.middleBarInputModel;
    }

    public void setMiddleBarInputModel(String middleBarInputModel) {
        this.middleBarInputModel = middleBarInputModel;
    }
}

