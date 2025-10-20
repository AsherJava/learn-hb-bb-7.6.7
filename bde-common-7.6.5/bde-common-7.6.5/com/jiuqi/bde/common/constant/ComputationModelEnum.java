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

public enum ComputationModelEnum {
    BALANCE("BALANCE", "\u79d1\u76ee\u4f59\u989d"),
    ASSBALANCE("ASSBALANCE", "\u8f85\u52a9\u4f59\u989d"),
    CFLBALANCE("CFLBALANCE", "\u91cd\u5206\u7c7b\u4f59\u989d"),
    ASSCFLBALANCE("ASSCFLBALANCE", "\u8f85\u52a9\u4f59\u989d\u91cd\u5206\u7c7b"),
    DJYEBALANCE("DJYEBALANCE", "\u62b5\u51cf\u4f59\u989d"),
    ASSRECLASSIFYBALANCE("ASSRECLASSIFYBALANCE", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b\u4f59\u989d"),
    AGINGBALANCE("AGINGBALANCE", "\u8d26\u9f84\u4f59\u989d"),
    ASSAGINGBALANCE("ASSAGINGBALANCE", "\u8f85\u52a9\u8d26\u9f84\u4f59\u989d"),
    XJLLBALANCE("XJLLBALANCE", "\u73b0\u91d1\u6d41\u91cf\u4f59\u989d"),
    TFV("TFV", "\u81ea\u5b9a\u4e49SQL"),
    BASEDATA("BASEDATA", "\u57fa\u7840\u6570\u636e"),
    CEDXBALANCE("CEDXBALANCE", "\u5dee\u989d\u62b5\u9500"),
    VOUCHER("VOUCHER", "\u51ed\u8bc1"),
    FORMULA("FORMULA", "\u53d6\u6570\u516c\u5f0f\u6a21\u578b"),
    CUSTOMFETCH("CUSTOMFETCH", "\u81ea\u5b9a\u4e49\u53d6\u6570"),
    FINANCIALASSAGINGBALANCE("FINANCIALASSAGINGBALANCE", "\u591a\u7ef4\u8f85\u52a9\u8d26\u9f84\u4f59\u989d"),
    INVEST_BILL("INVEST_BILL", "\u6295\u8d44\u53f0\u8d26"),
    BWASSBALANCE("BWASSBALANCE", "BW\u8f85\u52a9\u4f59\u989d");

    private String code;
    private String name;

    private ComputationModelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ComputationModelEnum getEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            throw new BusinessRuntimeException(String.format("\u8ba1\u7b97\u6a21\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", code));
        }
        ComputationModelEnum val = ComputationModelEnum.findEnumByCode(code);
        if (val == null) {
            throw new BusinessRuntimeException(String.format("\u8ba1\u7b97\u6a21\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", code));
        }
        return val;
    }

    public static ComputationModelEnum findEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (ComputationModelEnum bizModelEnum : ComputationModelEnum.values()) {
            if (!bizModelEnum.code.equals(code.toUpperCase())) continue;
            return bizModelEnum;
        }
        return null;
    }
}

