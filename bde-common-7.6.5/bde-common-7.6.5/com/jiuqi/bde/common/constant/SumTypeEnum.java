/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.BusinessRuntimeException;

public enum SumTypeEnum {
    MX("MX", "\u660e\u7ec6"),
    FMX("FMX", "\u975e\u660e\u7ec6");

    private final String code;
    private final String name;

    private SumTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static SumTypeEnum fromCode(String code) {
        for (SumTypeEnum type : SumTypeEnum.values()) {
            if (!type.code.equals(code)) continue;
            return type;
        }
        throw new BusinessRuntimeException(String.format("\u6c47\u603b\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879", code));
    }

    public static SumTypeEnum getSumTypeEnumByCode(String code) {
        for (SumTypeEnum type : SumTypeEnum.values()) {
            if (!type.code.equals(code)) continue;
            return type;
        }
        throw new BdeRuntimeException(String.format("\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u679a\u4e3e\u9879", code));
    }

    public static SumTypeEnum getSumTypeEnumByName(String name) {
        for (SumTypeEnum type : SumTypeEnum.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }
}

