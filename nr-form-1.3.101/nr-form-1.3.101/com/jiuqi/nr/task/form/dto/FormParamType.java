/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.face.ParamType;

public enum FormParamType implements ParamType
{
    FORM("FORM", "\u8868\u5355"),
    REGION("REGION", "\u533a\u57df"),
    LINK("LINK", "\u94fe\u63a5"),
    FORM_STYLE("FORM_STYLE", "\u8868\u6837"),
    ZB("ZB", "\u6307\u6807"),
    FIELD("FIELD", "\u5b57\u6bb5"),
    FORM_STATUS("FORM_STATUS", "\u8868\u5355\u72b6\u6001"),
    CONDITION_STYLE("CONDITION_STYLE", "\u6761\u4ef6\u6837\u5f0f"),
    DATATABLE("DATATABLE", "\u6570\u636e\u8868");

    private final String code;
    private final String title;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    private FormParamType(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

