/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.ActionReturnObject
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.biz.intf.action.ActionReturnObject;

public class SublistActionReturnObject<T>
implements ActionReturnObject {
    private boolean success = false;
    private T result;

    public String getType() {
        return "sublistAction";
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

