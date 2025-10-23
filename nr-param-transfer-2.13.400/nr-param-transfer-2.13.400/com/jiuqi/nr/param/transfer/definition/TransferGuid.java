/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer.definition;

import com.jiuqi.nr.param.transfer.definition.TransferNodeType;

public class TransferGuid {
    private String key;
    private TransferNodeType transferNodeType;
    private boolean business;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TransferNodeType getTransferNodeType() {
        return this.transferNodeType;
    }

    public void setTransferNodeType(TransferNodeType transferNodeType) {
        this.transferNodeType = transferNodeType;
    }

    public boolean isBusiness() {
        return this.business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public String toString() {
        return "TransferGuid{key='" + this.key + '\'' + ", transferNodeType=" + (Object)((Object)this.transferNodeType) + ", business=" + this.business + '}';
    }
}

