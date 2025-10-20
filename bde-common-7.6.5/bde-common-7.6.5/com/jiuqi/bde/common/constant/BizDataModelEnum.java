/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;

public enum BizDataModelEnum {
    BALANCEMODEL("BALANCEMODEL", "\u4f59\u989d\u6570\u636e\u6a21\u578b"),
    ASSBALANCEMODEL("ASSBALANCEMODEL", "\u5168\u7ef4\u5ea6\u4f59\u989d\u6570\u636e\u6a21\u578b"),
    VOUCHERMODEL("VOUCHERMODEL", "\u51ed\u8bc1\u6570\u636e\u6a21\u578b"),
    ASSRECLASSIFYBALANCEMODEL("ASSRECLASSIFYBALANCEMODEL", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b\u6570\u636e\u6a21\u578b"),
    XJLLMODEL("XJLLMODEL", "\u73b0\u6d41\u6570\u636e\u6a21\u578b"),
    AGINGMODEL("AGINGMODEL", "\u8d26\u9f84\u6570\u636e\u6a21\u578b"),
    CEDXMODEL("CEDXMODEL", "\u5dee\u989d\u62b5\u9500\u6570\u636e\u6a21\u578b"),
    INVESTMENTLEDGERMODEL("INVESTMENTLEDGERMODEL", "\u6295\u8d44\u53f0\u8d26\u6a21\u578b"),
    CUSTOMFETCHMODEL("CUSTOMFETCHMODEL", "\u81ea\u5b9a\u4e49\u6570\u6a21\u578b"),
    BASEDATAMODEL("BASEDATAMODEL", "\u57fa\u7840\u6570\u636e\u6a21\u578b"),
    TFVMODEL("TFVMODEL", "\u81ea\u5b9a\u4e49SQL\u6570\u6a21\u578b");

    private final String code;
    private final String name;

    private BizDataModelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static BizDataModelEnum getEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        BizDataModelEnum val = BizDataModelEnum.findEnumByCode(code);
        if (val == null) {
            throw new BusinessRuntimeException(String.format("\u6570\u636e\u6a21\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", code));
        }
        return val;
    }

    private static BizDataModelEnum findEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (BizDataModelEnum bizDataModelEnum : BizDataModelEnum.values()) {
            if (!bizDataModelEnum.code.equals(code.toUpperCase())) continue;
            return bizDataModelEnum;
        }
        return null;
    }
}

