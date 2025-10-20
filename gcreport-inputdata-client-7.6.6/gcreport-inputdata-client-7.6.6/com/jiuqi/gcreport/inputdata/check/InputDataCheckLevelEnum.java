/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.check;

public enum InputDataCheckLevelEnum {
    ALL_MERGE("ALL_MERGE", "\u6240\u6709\u5408\u5e76\u5c42\u7ea7"),
    CURRENT_MERGE("CURRENT_MERGE", "\u5f53\u524d\u5408\u5e76\u5c42\u7ea7"),
    PARENT_MERGE("PARENT_MERGE", "\u4e0a\u7ea7\u5408\u5e76\u5c42\u7ea7"),
    CHILDREN_MERGE("CHILDREN_MERGE", "\u4e0b\u7ea7\u5408\u5e76\u5c42\u7ea7"),
    CUSTOM("CUSTOM", "\u81ea\u5b9a\u4e49");

    private String code;
    private String title;

    private InputDataCheckLevelEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

