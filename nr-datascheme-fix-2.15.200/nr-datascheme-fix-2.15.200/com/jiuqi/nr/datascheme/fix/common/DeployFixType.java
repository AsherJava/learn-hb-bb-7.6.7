/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.common;

public enum DeployFixType {
    DEFAULT(0, "\u81ea\u52a8\u4fee\u590d"),
    DELETE_PT(301, "\u5220\u9664\u7269\u7406\u8868\uff0c\u91cd\u65b0\u751f\u6210"),
    DELETE_PT_ONLINE(302, "\u5220\u9664\u7269\u7406\u8868\uff0c\u91cd\u65b0\u751f\u6210"),
    RENAME_PT(303, "\u5907\u4efd\u7269\u7406\u8868\u5e76\u5c1d\u8bd5\u540c\u6b65\u6570\u636e"),
    DELETE_PARAMS(304, "\u4fee\u590d\u6570\u636e\u65b9\u6848\u53c2\u6570"),
    DELETE_PT_NRT(305, "\u5220\u9664\u7269\u7406\u8868\u53ca\u5973\u5a32\u8fd0\u884c\u53c2\u6570"),
    DELETE_PT_NRT_ONLINE(306, "\u5220\u9664\u7269\u7406\u8868\u53ca\u5973\u5a32\u8fd0\u884c\u53c2\u6570"),
    RENAME_PT_DELETE_NRT(307, "\u5907\u4efd\u7269\u7406\u8868\uff0c\u5220\u9664\u5973\u5a32\u8fd0\u884c\u53c2\u6570"),
    DELETE_NVWA_PARAMS(308, "\u4fee\u590d\u5973\u5a32\u53c2\u6570"),
    DDT_COVER_DRT(309, "\u6570\u636e\u65b9\u6848\u53c2\u6570\u8986\u76d6"),
    DO_NOT_FIX(310, "\u4e0d\u4fee\u590d"),
    CLEAN_GARBAGE_DATA(311, "\u6e05\u9664\u5783\u573e\u53c2\u6570");

    private final int value;
    private final String title;

    private DeployFixType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

