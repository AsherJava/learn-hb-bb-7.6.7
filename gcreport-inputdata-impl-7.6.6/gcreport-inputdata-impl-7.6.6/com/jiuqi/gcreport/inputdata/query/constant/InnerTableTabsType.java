/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.query.constant;

import com.jiuqi.common.base.util.StringUtils;

public enum InnerTableTabsType {
    DETAILDATA(1, "\u660e\u7ec6\u6570\u636e"),
    DIFFERENCE(2, "\u5dee\u989d"),
    NOTOFFSET(3, "\u672a\u62b5\u9500"),
    INPUTADJUSTMENT(4, "\u8f93\u5165\u8c03\u6574"),
    JOURNALADJUSTMENT(5, "\u65e5\u8bb0\u8d26\u8c03\u6574"),
    PARENTUNION(6, "\u4e0a\u7ea7\u5408\u5e76"),
    OFFSET(7, "\u5df2\u62b5\u9500"),
    ALLDATA(8, "\u6240\u6709\u8bb0\u5f55");

    private String id;
    private int code;
    private String title;

    private InnerTableTabsType(int code, String title) {
        this.id = StringUtils.toViewString((Object)code);
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public static InnerTableTabsType findTabsType(String type) {
        if (StringUtils.isEmpty((String)type)) {
            return null;
        }
        for (InnerTableTabsType itt : InnerTableTabsType.values()) {
            if (itt.name().equalsIgnoreCase(type)) {
                return itt;
            }
            if (type.equals(String.valueOf(itt.getCode()))) {
                return itt;
            }
            if (!type.equals(itt.getTitle())) continue;
            return itt;
        }
        return null;
    }
}

