/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator$LogItem
 */
package com.jiuqi.common.plantask.extend.dao;

import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import java.util.List;

public interface PlanTaskLogDetailTempDao {
    public List<ILogGenerator.LogItem> getLogItemByInstanceId(String var1);

    public String getDetailById(long var1);
}

