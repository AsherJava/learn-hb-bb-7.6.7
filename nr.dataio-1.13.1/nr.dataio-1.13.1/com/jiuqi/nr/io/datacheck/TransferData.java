/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.datacheck;

public class TransferData {
    private int transferType;
    private Object value;
    private String msg;

    public int getTransferType() {
        return this.transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

