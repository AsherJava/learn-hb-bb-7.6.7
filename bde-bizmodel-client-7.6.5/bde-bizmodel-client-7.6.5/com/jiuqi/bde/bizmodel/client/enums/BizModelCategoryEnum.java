/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.enums;

public enum BizModelCategoryEnum {
    BIZMODEL_TFV("BIZMODEL_TFV", "\u81ea\u5b9a\u4e49SQL"),
    BIZMODEL_BASEDATA("BIZMODEL_BASEDATA", "\u57fa\u7840\u6570\u636e"),
    BIZMODEL_CUSTOM("BIZMODEL_CUSTOM", "\u81ea\u5b9a\u4e49\u53d6\u6570"),
    BIZMODEL_FINDATA("BIZMODEL_FINDATA", "\u8d26\u52a1\u6a21\u578b");

    private final String code;
    private final String name;

    private BizModelCategoryEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

