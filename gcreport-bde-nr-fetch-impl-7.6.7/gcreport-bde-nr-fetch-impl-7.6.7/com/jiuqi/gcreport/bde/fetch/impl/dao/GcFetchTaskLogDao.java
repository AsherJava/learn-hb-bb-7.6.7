/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.dao;

import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogEO;
import java.util.List;

public interface GcFetchTaskLogDao {
    public void save(GcFetchTaskLogEO var1);

    public GcFetchTaskLogEO get(String var1);

    public int countExecuteTask(String var1);

    public void update(GcFetchTaskLogEO var1);

    public void bindAsyncTaskID(String var1, String var2);

    public List<GcFetchTaskLogEO> getTaskByState(String var1, Integer var2);
}

