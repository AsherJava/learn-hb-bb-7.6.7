/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.invest.investbill.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum InvestInfoEnum {
    DIRECT("DIRECT", "\u76f4\u63a5\u6295\u8d44"),
    INDIRECT("INDIRECT", "\u95f4\u63a5\u6295\u8d44"),
    DISPOSE_DONE("1", "\u5df2\u5b8c\u6210"),
    DISPOSE_UNDO("0", "");

    private String code;
    private String title;

    private InvestInfoEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getTitleByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return "";
        }
        for (InvestInfoEnum investInfoEnum : InvestInfoEnum.values()) {
            if (!investInfoEnum.getCode().equals(code)) continue;
            return investInfoEnum.getTitle();
        }
        return "";
    }
}

