/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.penetrate.impl.common;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;

public enum PenetrateTypeEnum {
    BALANCE("BALANCE", "\u4f59\u989d\u8868"),
    DETAILLEDGER("DETAILLEDGER", "\u660e\u7ec6\u8d26"),
    VOUCHER("VOUCHER", "\u51ed\u8bc1");

    private final String code;
    private final String name;

    private PenetrateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static PenetrateTypeEnum fromCode(String code) {
        Assert.isNotEmpty((String)code);
        String upperCaseCode = code.toUpperCase();
        for (PenetrateTypeEnum type : PenetrateTypeEnum.values()) {
            if (!type.getCode().equals(upperCaseCode)) continue;
            return type;
        }
        throw new BusinessRuntimeException(String.format("\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u900f\u89c6\u7c7b\u578b", code));
    }
}

