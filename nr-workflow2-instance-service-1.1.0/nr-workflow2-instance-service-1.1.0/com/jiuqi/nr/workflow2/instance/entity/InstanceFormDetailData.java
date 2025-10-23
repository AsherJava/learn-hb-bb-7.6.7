/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.entity;

import java.util.Calendar;
import java.util.List;

public interface InstanceFormDetailData {
    public String getKey();

    public String getCode();

    public String getTitle();

    public int getState();

    public Calendar getStartTime();

    public List<InstanceFormDetailData> getChildren();
}

