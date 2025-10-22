/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

import com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum;

public enum SameCtrlExtractOperateEnum {
    VIRTUALPARENT_EXTRACT("VIRTUALPARENT_EXTRACT", "\u5904\u7f6e\u65b9\u63d0\u53d6"),
    CHANGEDPARENT_EXTRACT("CHANGEDPARENT_EXTRACT", "\u6536\u8d2d\u65b9\u63d0\u53d6"),
    DATAENTRY_EXTRACT("DATAENTRY_EXTRACT", "\u62a5\u8868\u63d0\u53d6"),
    GO_BACK("GO_BACK", "\u51b2\u56de");

    private String code;
    private String title;

    private SameCtrlExtractOperateEnum(String code, String title) {
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
        for (SameCtrlZbAttrEnum type : SameCtrlZbAttrEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return code;
    }
}

