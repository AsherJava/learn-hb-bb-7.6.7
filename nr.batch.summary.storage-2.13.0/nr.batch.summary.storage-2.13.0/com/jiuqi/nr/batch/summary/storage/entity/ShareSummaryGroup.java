/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

public interface ShareSummaryGroup {
    public static final String ROOT_GROUP = "00000000-0000-0000-0000-000000000000";
    public static final String TYPE = "Group";

    public String getCode();

    public String getTitle();

    public String getParent();

    public String getTask();
}

