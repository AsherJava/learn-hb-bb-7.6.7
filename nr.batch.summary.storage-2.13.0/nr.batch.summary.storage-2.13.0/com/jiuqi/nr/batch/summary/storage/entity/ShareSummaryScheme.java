/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import java.util.Date;

public interface ShareSummaryScheme {
    public static final String TYPE = "scheme";

    public String getCode();

    public String getTitle();

    public String getTask();

    public Date getShareTime();

    public String getFromUser();

    public String getToUser();
}

