/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import java.util.Date;

public interface ModelContext {
    public String getTenantName();

    public String getUnitCode();

    public String getUserCode();

    public Date getBizDate();

    public String getTriggerOrigin();

    public boolean isPreview();
}

