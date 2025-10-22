/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import java.util.Date;

public interface ShareSchemeRow {
    public String getScheme();

    public String getFromUser();

    public String getToUser();

    public Date getShareTime();

    public String getTask();
}

