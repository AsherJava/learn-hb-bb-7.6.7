/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

import com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum;

public enum SameCtrlPentrateTypeEnum {
    VIRTUAL_PARENT_NAME("virtualParentName", "\u5904\u7f6e\u65b9\u62b5\u9500\u5206\u5f55\u8c03\u6574"),
    CHANGED_PARENT_NAME("changedParentName", "\u6536\u8d2d\u62b5\u9500\u5206\u5f55\u8c03\u6574"),
    SAME_PARENT_NAME("sameParentName", "\u5904\u7f6e\u6536\u8d2d\u5171\u540c\u4e0a\u7ea7\u67e5\u770b");

    private String code;
    private String title;

    private SameCtrlPentrateTypeEnum(String code, String title) {
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

