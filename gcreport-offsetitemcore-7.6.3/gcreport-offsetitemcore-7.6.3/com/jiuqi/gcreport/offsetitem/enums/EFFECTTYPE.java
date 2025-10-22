/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.offsetitem.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum EFFECTTYPE {
    MONTH("effectCurrMonth", "\u5f53\u671f"),
    YEAR("effectCurrYear", "\u5f53\u5e74"),
    LONGTERM("effectLongTerm", "\u8de8\u5e74\u6eda\u8c03"),
    ALL("all", "\u5168\u90e8");

    private String code;
    private String title;

    private EFFECTTYPE(String code, String title) {
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
        for (EFFECTTYPE type : EFFECTTYPE.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return code;
    }

    public static EFFECTTYPE getEnumByTitle(String title) {
        if (StringUtils.isEmpty((String)title)) {
            return null;
        }
        title = title.trim();
        for (EFFECTTYPE type : EFFECTTYPE.values()) {
            if (!type.getTitle().equals(title)) continue;
            return type;
        }
        return null;
    }
}

