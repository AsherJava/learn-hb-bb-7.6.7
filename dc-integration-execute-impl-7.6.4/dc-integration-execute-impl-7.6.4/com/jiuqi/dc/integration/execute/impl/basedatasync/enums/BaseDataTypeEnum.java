/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.enums;

public enum BaseDataTypeEnum {
    ORG("ORG", "\u7ec4\u7ec7\u673a\u6784"),
    BASEDATA("BASEDATA", "\u57fa\u7840\u6570\u636e");

    private String code;
    private String name;

    private BaseDataTypeEnum(String code, String name) {
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

