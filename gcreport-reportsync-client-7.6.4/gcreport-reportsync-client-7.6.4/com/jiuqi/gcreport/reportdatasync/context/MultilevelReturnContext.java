/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.context;

import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;

public class MultilevelReturnContext {
    protected SyncTypeEnums type;
    protected String infoJson;

    public SyncTypeEnums getType() {
        return this.type;
    }

    public void setType(SyncTypeEnums type) {
        this.type = type;
    }

    public String getInfoJson() {
        return this.infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }
}

