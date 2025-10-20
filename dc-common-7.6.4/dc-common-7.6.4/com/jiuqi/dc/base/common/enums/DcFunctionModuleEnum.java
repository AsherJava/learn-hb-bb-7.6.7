/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum DcFunctionModuleEnum {
    UNITCOMBINE("\u5355\u4f4d\u7ec4\u5408"),
    ONLINEPERIOD("\u4e0a\u7ebf\u671f\u95f4\u7ba1\u7406"),
    SUBJECT("\u4e00\u672c\u8d26\u79d1\u76ee"),
    DIM("\u7ef4\u5ea6\u7ba1\u7406"),
    DATAMAPPINGSCHEME("\u6570\u636e\u6620\u5c04\u65b9\u6848"),
    BASEDATAMAPPINGDEFINE("\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49"),
    BIZDATAMAPPINGDEFINE("\u4e1a\u52a1\u6570\u636e\u6620\u5c04\u5b9a\u4e49"),
    DATAMAPPING("\u6570\u636e\u6620\u5c04"),
    DATAINTEGRATION("\u6570\u636e\u6574\u5408"),
    UNITMAPPING("\u4e00\u672c\u8d26\u5355\u4f4d\u4e0e\u62a5\u8868\u5355\u4f4d\u6620\u5c04"),
    ACCOUNTAGESCHEME("\u8d26\u9f84\u65b9\u6848"),
    RECLASSIFYSCHEME("\u91cd\u5206\u7c7b\u65b9\u6848"),
    DEDUCTIONRECLASSIFYSCHEME("\u62b5\u51cf\u91cd\u5206\u7c7b\u65b9\u6848"),
    ADJUSTVCHR("\u8c03\u6574\u51ed\u8bc1"),
    RATESCHEMESETTINGS("\u6c47\u7387\u65b9\u6848\u8bbe\u7f6e"),
    ACCOUNTCONVERSION("\u8d26\u7ea7\u6298\u7b97\u521d\u59cb"),
    HISTORYRATE("\u5386\u53f2\u6c47\u7387"),
    OPPUNITMAPPING("\u5185\u90e8\u5ba2\u5546\u6620\u5c04"),
    PENETRATEBILL("\u8054\u67e5\u5355\u636e\u65b9\u6848");

    private String name;

    private DcFunctionModuleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getFullModuleName() {
        return "\u4e00\u672c\u8d26-" + this.name;
    }
}

