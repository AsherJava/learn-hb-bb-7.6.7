/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.dto.Ordered;

public interface BaseData
extends Ordered {
    public String getKey();

    public String getTitle();

    public String getLevel();

    public String getUpdateTime();

    public Constants.DataStatus getStatus();
}

