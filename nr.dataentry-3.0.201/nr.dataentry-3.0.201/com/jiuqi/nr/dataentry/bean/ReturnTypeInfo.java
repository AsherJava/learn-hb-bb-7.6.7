/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.ReturnType;
import java.util.List;

public class ReturnTypeInfo {
    private boolean returnTypeEnable;
    private List<ReturnType> returnTypeSource;

    public boolean isReturnTypeEnable() {
        return this.returnTypeEnable;
    }

    public void setReturnTypeEnable(boolean returnTypeEnable) {
        this.returnTypeEnable = returnTypeEnable;
    }

    public List<ReturnType> getReturnTypeSource() {
        return this.returnTypeSource;
    }

    public void setReturnTypeSource(List<ReturnType> returnTypeSource) {
        this.returnTypeSource = returnTypeSource;
    }
}

