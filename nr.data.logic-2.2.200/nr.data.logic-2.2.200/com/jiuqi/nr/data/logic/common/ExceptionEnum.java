/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.common;

public enum ExceptionEnum {
    FORMULA_NODE_EXC("000", "\u516c\u5f0f\u8282\u70b9\u5904\u7406\u5f02\u5e38"),
    CHECK_ERROR_DES_SAVE_EXC("001", "\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u4fdd\u5b58\u5f02\u5e38"),
    CHECK_ERROR_DES_DEL_EXC("002", "\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u5220\u9664\u5f02\u5e38"),
    DIM_EXPAND_EXC("003", "\u6ca1\u6709\u53ef\u6267\u884c\u7684\u7ef4\u5ea6"),
    CHECK_QUERY_PARAM_EXC("004", "\u5ba1\u6838\u67e5\u8be2\u53c2\u6570\u5f02\u5e38"),
    TABLE_MODEL_EXC("005", "\u5973\u5a32\u8868\u6a21\u578b\u5f02\u5e38");

    private final String code;
    private final String desc;

    private ExceptionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}

