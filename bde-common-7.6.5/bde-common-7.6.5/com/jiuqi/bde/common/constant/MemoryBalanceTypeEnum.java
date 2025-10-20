/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.intf.IMoudle
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.intf.IMoudle;

public enum MemoryBalanceTypeEnum implements IMoudle
{
    BALANCE("BDE_BALANCE", "\u79d1\u76ee\u4f59\u989d\u8868", "\u79d1\u76ee\u4f59\u989d\u6570\u636e\u6a21\u578b"),
    ASSBALANCE("BDE_ASSBALANCE", "\u5168\u7ef4\u5ea6\u4f59\u989d\u8868", "\u8f85\u52a9\u4f59\u989d\u6570\u636e\u6a21\u578b"),
    XJLLBALANCE("BDE_XJLLBALANCE", "\u73b0\u6d41\u4f59\u989d\u8868", "\u73b0\u91d1\u6d41\u91cf\u4f59\u989d\u6570\u636e\u6a21\u578b"),
    ASSAGINGBALANCE("BDE_ASSAGINGBALANCE", "\u8d26\u9f84\u4f59\u989d\u8868", "\u8d26\u9f84\u4f59\u989d\u6570\u636e\u6a21\u578b"),
    CEDXBALANCE("BDE_CEDXBALANCE", "\u5dee\u989d\u62b5\u9500\u8868", "\u5dee\u989d\u62b5\u9500\u6570\u636e\u6a21\u578b"),
    VOUCHER("BDE_VOUCHER", "\u51ed\u8bc1\u4f59\u989d\u8868", "\u51ed\u8bc1\u6570\u636e\u6a21\u578b");

    private final String code;
    private final String name;
    private final String bizModel;

    private MemoryBalanceTypeEnum(String code, String name, String bizModel) {
        this.code = code;
        this.name = name;
        this.bizModel = bizModel;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getBizModel() {
        return this.bizModel;
    }

    public static MemoryBalanceTypeEnum fromCode(String code) {
        Assert.isNotEmpty((String)code);
        for (MemoryBalanceTypeEnum balanceType : MemoryBalanceTypeEnum.values()) {
            if (!balanceType.getCode().equals(code)) continue;
            return balanceType;
        }
        throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", code));
    }

    public int getOrder() {
        return this.ordinal();
    }

    public String getDesc() {
        return this.bizModel;
    }
}

