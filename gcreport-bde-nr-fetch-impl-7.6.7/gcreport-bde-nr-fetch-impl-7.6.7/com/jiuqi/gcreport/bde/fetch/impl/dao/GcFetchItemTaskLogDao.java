/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.dao;

import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import java.util.List;

public interface GcFetchItemTaskLogDao {
    public GcFetchItemTaskLogEO getItemTask(String var1, String var2, String var3);

    public Double queryProcess(String var1);

    public String getLastFetchInfo(String var1);

    public List<GcFetchItemTaskLogEO> getErrorItemTaskList(String var1);

    public void update(GcFetchItemTaskLogEO var1);

    public void saveAll(List<GcFetchItemTaskLogEO> var1);

    public void updateErrorStateByFetchId(String var1);

    public int updateFetchStatus(String var1, List<String> var2);
}

