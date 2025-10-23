/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;

public class ParamGuid {
    private String key;
    private NrdxParamNodeType nrdxParamNodeType;
    private boolean business;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public NrdxParamNodeType getNrdxParamNodeType() {
        return this.nrdxParamNodeType;
    }

    public void setNrdxParamNodeType(NrdxParamNodeType nrdxParamNodeType) {
        this.nrdxParamNodeType = nrdxParamNodeType;
    }

    public boolean isBusiness() {
        return this.business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public String toString() {
        return "ParamGuid{key='" + this.key + '\'' + ", nrdxParamNodeType=" + (Object)((Object)this.nrdxParamNodeType) + ", business=" + this.business + '}';
    }
}

