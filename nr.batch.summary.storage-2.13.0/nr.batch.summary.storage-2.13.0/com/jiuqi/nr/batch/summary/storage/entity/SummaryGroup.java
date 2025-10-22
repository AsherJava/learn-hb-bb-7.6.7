/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import java.util.Date;

public interface SummaryGroup {
    public static final String ROOT_GROUP = "00000000-0000-0000-0000-000000000000";
    public static final String TYPE = "Group";

    public String getKey();

    public String getTitle();

    public String getParent();

    public String getTask();

    public Date getUpdateTime();

    public String getCreator();

    public String getOrdinal();
}

