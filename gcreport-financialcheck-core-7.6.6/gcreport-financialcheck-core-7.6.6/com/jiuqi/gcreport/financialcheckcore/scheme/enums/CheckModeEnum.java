/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum CheckModeEnum {
    UNILATERAL("UNILATERAL", "\u5355\u8fb9\u5bf9\u8d26"),
    BILATERAL("BILATERAL", "\u53cc\u8fb9\u5bf9\u8d26"),
    OFFSETVCHR("OFFSETVCHR", "\u534f\u540c\u51ed\u8bc1\u5bf9\u8d26"),
    WRITEOFFCHECK("WRITEOFFCHECK", "\u51b2\u9500"),
    SPECIALCHECK("SPACIALCHECK", "\u7279\u6b8a\u5bf9\u8d26"),
    GCNUMBER("GCNUMBER", "\u534f\u540c\u5bf9\u8d26"),
    GCNUMBERSPECIAL("GCNUMBER", "\u534f\u540c\u7279\u6b8a\u5bf9\u8d26");

    private String code;
    private String title;

    private CheckModeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static boolean selfCheck(String checkMode) {
        if (StringUtils.isEmpty((String)checkMode)) {
            return false;
        }
        return WRITEOFFCHECK.getCode().equals(checkMode);
    }

    public static boolean unilateralCheck(String checkMode) {
        if (StringUtils.isEmpty((String)checkMode)) {
            return false;
        }
        return UNILATERAL.getCode().equals(checkMode);
    }

    public static CheckModeEnum getEnumByCode(String checkTypeCode) {
        for (CheckModeEnum checkModeEnum : CheckModeEnum.values()) {
            if (!checkModeEnum.getCode().equals(checkTypeCode)) continue;
            return checkModeEnum;
        }
        return null;
    }
}

