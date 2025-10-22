/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 */
package com.jiuqi.nr.unit.treeimpl.web.request;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;

public class UpdateCaptionFieldsContext {
    private boolean showCodeInfo;
    private UnitTreeContextData contextData;

    public boolean isShowCodeInfo() {
        return this.showCodeInfo;
    }

    public void setShowCodeInfo(boolean showCodeInfo) {
        this.showCodeInfo = showCodeInfo;
    }

    public UnitTreeContextData getContextData() {
        return this.contextData;
    }

    public void setContextData(UnitTreeContextData contextData) {
        this.contextData = contextData;
    }
}

