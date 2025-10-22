/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.web.app.context;

public interface BatchSummaryContextData {
    public String getTaskId();

    public String getEntityId();

    public String getFormSchemeId();

    public String getPeriod();

    public String getProductName();

    public boolean isValidContext();
}

