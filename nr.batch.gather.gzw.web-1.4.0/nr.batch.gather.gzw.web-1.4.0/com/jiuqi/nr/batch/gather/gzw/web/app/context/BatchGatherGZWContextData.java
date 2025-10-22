/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.context;

public interface BatchGatherGZWContextData {
    public String getTaskId();

    public String getEntityId();

    public String getFormSchemeId();

    public String getPeriod();

    public String getProductName();

    public boolean isValidContext();

    public String getBatchGatherSchemeKey();
}

