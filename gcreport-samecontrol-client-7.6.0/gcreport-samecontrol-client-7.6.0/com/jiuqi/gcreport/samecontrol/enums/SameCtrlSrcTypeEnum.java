/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum SameCtrlSrcTypeEnum {
    END_EXTRACT("endExtract", "\u671f\u672b\u62b5\u9500\u63d0\u53d6"),
    END_INPUT("endInput", "\u671f\u672b\u8f93\u5165"),
    END_INVEST_CALC("endInvest", "\u671f\u672b\u6295\u8d44"),
    END_INVEST_CALC_INIT("endInvestInit", "\u671f\u672b\u6295\u8d44"),
    BEGIN_ASSET("beginAsset", "\u8d44\u4ea7\u8d1f\u503a\u671f\u521d"),
    BEGIN_LAST_YEAR("beginLastYear", "\u4e0a\u5e74\u540c\u671f"),
    BEGIN_INVEST("beginInvest", "\u671f\u521d\u6295\u8d44"),
    BEGIN_INPUT("beginInput", "\u671f\u521d\u8f93\u5165"),
    DISPOSER_PROFITLOSS("DISPOSER_PROFITLOSS", "\u5904\u7f6e\u65b9-\u635f\u76ca\u63d0\u53d6"),
    DISPOSER_INVEST("DISPOSER_INVEST", "\u5904\u7f6e\u65b9-\u6295\u8d44\u751f\u6210"),
    DISPOSER_INPUT_ADJUST("DISPOSER_INPUT_ADJUST", "\u5904\u7f6e\u65b9-\u8f93\u5165\u8c03\u6574"),
    DISPOSER_LAST_DATE_INVEST("DISPOSER_LAST_DATE_INVEST", "\u5904\u7f6e\u65b9-\u4e0a\u671f\u6295\u8d44\u63d0\u53d6"),
    DISPOSER_PARENT_PROFITLOSS("DISPOSER_PARENT_PROFITLOSS", "\u5904\u7f6e\u65b9\u4e0a\u7ea7-\u635f\u76ca\u63d0\u53d6"),
    DISPOSER_PARENT_INPUT_ADJUST("DISPOSER_PARENT_INPUT_ADJUST", "\u5904\u7f6e\u65b9\u4e0a\u7ea7-\u8f93\u5165\u8c03\u6574"),
    ACQUIRER_BEGIN_EXTRACT("ACQUIRER_BEGIN_EXTRACT", "\u6536\u8d2d\u65b9-\u671f\u521d\u63d0\u53d6"),
    ACQUIRER_BEFORE_EXTRACT("ACQUIRER_BEFORE_EXTRACT", "\u6536\u8d2d\u65b9-\u4e0a\u5e74\u540c\u671f\u63d0\u53d6"),
    ACQUIRER_INVEST("ACQUIRER_INVEST", "\u6536\u8d2d\u65b9-\u6295\u8d44\u751f\u6210"),
    ACQUIRER_INPUT_ADJUST("ACQUIRER_INPUT_ADJUST", "\u6536\u8d2d\u65b9-\u8f93\u5165\u8c03\u6574"),
    ACQUIRER_LAST_DATE_EXTRACT("ACQUIRER_LAST_DATE_EXTRACT", "\u6536\u8d2d\u65b9-\u4e0a\u671f\u63d0\u53d6"),
    ACQUIRER_PARENT_BEGIN_EXTRACT("ACQUIRER_PARENT_BEGIN_EXTRACT", "\u6536\u8d2d\u65b9\u4e0a\u7ea7-\u671f\u521d\u63d0\u53d6"),
    ACQUIRER_PARENT_BEFORE_EXTRACT("ACQUIRER_PARENT_BEFORE_EXTRACT", "\u6536\u8d2d\u65b9\u4e0a\u7ea7-\u4e0a\u5e74\u540c\u671f\u63d0\u53d6"),
    ACQUIRER_PARENT_INPUT_ADJUST("ACQUIRER_PARENT_INPUT_ADJUST", "\u6536\u8d2d\u65b9\u4e0a\u7ea7-\u8f93\u5165\u8c03\u6574"),
    ACQUIRER_PARENT_DATE_EXTRACT("ACQUIRER_PARENT_DATE_EXTRACT", "\u6536\u8d2d\u65b9\u4e0a\u7ea7-\u540c\u671f\u63d0\u53d6"),
    MERGE_UNIT_PARENT_INPUT_ADJUST("MERGE_UNIT_PARENT_INPUT_ADJUST", "\u5171\u540c\u4e0a\u7ea7-\u8f93\u5165\u8c03\u6574");

    private String code;
    private String title;

    private SameCtrlSrcTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getTitleByCode(String srcTypeCode) {
        for (SameCtrlSrcTypeEnum srcTypeEnum : SameCtrlSrcTypeEnum.values()) {
            if (!srcTypeEnum.getCode().equals(srcTypeCode)) continue;
            return srcTypeEnum.getTitle();
        }
        return null;
    }

    public static String getSourceMethodTtile(String sameCtrlSrcType) {
        String sourceMethodTtile = "";
        if (BEGIN_INPUT.getCode().equals(sameCtrlSrcType) || END_INPUT.getCode().equals(sameCtrlSrcType) || DISPOSER_INPUT_ADJUST.getCode().equals(sameCtrlSrcType) || DISPOSER_PARENT_INPUT_ADJUST.getCode().equals(sameCtrlSrcType) || ACQUIRER_INPUT_ADJUST.getCode().equals(sameCtrlSrcType) || ACQUIRER_PARENT_INPUT_ADJUST.getCode().equals(sameCtrlSrcType) || MERGE_UNIT_PARENT_INPUT_ADJUST.getCode().equals(sameCtrlSrcType)) {
            sourceMethodTtile = "\u8f93\u5165";
        } else if (END_EXTRACT.getCode().equals(sameCtrlSrcType) || BEGIN_ASSET.getCode().equals(sameCtrlSrcType) || BEGIN_LAST_YEAR.getCode().equals(sameCtrlSrcType) || ACQUIRER_BEGIN_EXTRACT.getCode().equals(sameCtrlSrcType) || ACQUIRER_BEFORE_EXTRACT.getCode().equals(sameCtrlSrcType) || ACQUIRER_PARENT_BEFORE_EXTRACT.getCode().equals(sameCtrlSrcType) || ACQUIRER_PARENT_BEGIN_EXTRACT.getCode().equals(sameCtrlSrcType) || ACQUIRER_PARENT_DATE_EXTRACT.getCode().equals(sameCtrlSrcType) || ACQUIRER_LAST_DATE_EXTRACT.getCode().equals(sameCtrlSrcType) || DISPOSER_LAST_DATE_INVEST.getCode().equals(sameCtrlSrcType) || DISPOSER_PROFITLOSS.getCode().equals(sameCtrlSrcType) || DISPOSER_PARENT_PROFITLOSS.getCode().equals(sameCtrlSrcType)) {
            sourceMethodTtile = "\u63d0\u53d6";
        } else if (BEGIN_INVEST.getCode().equals(sameCtrlSrcType) || END_INVEST_CALC.getCode().equals(sameCtrlSrcType) || END_INVEST_CALC_INIT.getCode().equals(sameCtrlSrcType) || DISPOSER_INVEST.getCode().equals(sameCtrlSrcType) || ACQUIRER_INVEST.getCode().equals(sameCtrlSrcType)) {
            sourceMethodTtile = "\u89c4\u5219";
        }
        return sourceMethodTtile;
    }
}

