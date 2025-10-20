/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum EtlTaskTypeEnum {
    GET_UNIT_RANGE(1, "\u83b7\u53d6\u5355\u4f4d\u8303\u56f4"),
    GET_INCREMENT_BY_UNIT(2, "\u6309\u5355\u4f4d\u83b7\u53d6\u589e\u91cf");

    private Integer code;
    private String name;

    private EtlTaskTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

