/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.calibersum.model;

public enum CaliberSumDSFieldType {
    CALIBER_CODE("\u53e3\u5f84\u4ee3\u7801"),
    CALIBER_TITLE("\u53e3\u5f84\u6807\u9898"),
    CALIBER_PARENT("\u53e3\u5f84\u7236\u8282\u70b9"),
    MDCODE("\u4e3b\u7ef4\u5ea6\u4ee3\u7801"),
    FIELD("\u6307\u6807\u5b57\u6bb5"),
    EXPRESSION("\u8868\u8fbe\u5f0f\u5b57\u6bb5"),
    CHECK_ERROR("\u5ba1\u6838\u9519\u8bef"),
    CHECK_WARNNING("\u5ba1\u6838\u8b66\u544a"),
    CHECK_HINT("\u5ba1\u6838\u63d0\u793a"),
    UNITSTATE("\u5355\u4f4d\u72b6\u6001");

    private String title;

    private CaliberSumDSFieldType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

