/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache.memcache.gather.impl;

import com.jiuqi.bde.plugin.common.cache.memcache.gather.IMemoryBalanceType;

public class BuiltinMemoryBalanceType
implements IMemoryBalanceType {
    private final String symbol;
    private final String code;
    private final String name;
    private final String bizModel;
    private final Integer order;
    private final String desc;

    public BuiltinMemoryBalanceType(String symbol, String code, String name, String bizModel, Integer order, String desc) {
        this.symbol = symbol;
        this.code = code;
        this.name = name;
        this.bizModel = bizModel;
        this.order = order;
        this.desc = desc;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBizModel() {
        return this.bizModel;
    }

    @Override
    public Integer getOrder() {
        return this.order;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}

