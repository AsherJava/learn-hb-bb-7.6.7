/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;

public enum SourceTypeEnum {
    DC("DC", "\u4e00\u672c\u8d26"),
    BDE("BDE", "\u4e1a\u52a1\u53d6\u6570\u5f15\u64ce");

    private final String code;
    private final String name;

    private SourceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static SourceTypeEnum fromCode(String code) {
        Assert.isNotEmpty((String)code);
        for (SourceTypeEnum sourceType : SourceTypeEnum.values()) {
            if (!sourceType.getCode().equals(code)) continue;
            return sourceType;
        }
        throw new BusinessRuntimeException(String.format("\u53d6\u6570\u6765\u6e90\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", code));
    }

    public static SourceTypeEnum getDefault() {
        return DC;
    }
}

