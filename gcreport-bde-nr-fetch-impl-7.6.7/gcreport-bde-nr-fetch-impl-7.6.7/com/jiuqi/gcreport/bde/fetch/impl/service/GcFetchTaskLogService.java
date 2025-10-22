/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.service;

import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogClobEO;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogEO;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import java.util.List;

public interface GcFetchTaskLogService {
    public void insertItemTaskLogs(List<GcFetchItemTaskLogEO> var1);

    public void updateItemTaskLog(GcFetchItemTaskLogEO var1);

    public GcFetchItemTaskLogEO getItemTask(String var1, String var2, String var3);

    public Double queryProcess(String var1);

    public String getLastFetchInfo(String var1);

    public List<GcFetchItemTaskLogEO> getErrorItemTaskList(String var1);

    public String getArgContentById(String var1);

    public void updateErrorStateByFetchId(String var1);

    public int countExecuteTask(String var1);

    public void updateFetchLog(GcFetchTaskLogEO var1, GcFetchTaskLogClobEO var2);

    public void saveGcFetchTaskLog(GcFetchTaskLogEO var1);

    public List<GcFetchTaskLogEO> getTaskByState(String var1, Integer var2);

    public List<GcFetchTaskLogClobEO> listById(List<String> var1);

    public void saveClob(GcFetchTaskLogClobEO var1);

    public String saveFetchLog(EfdcInfo var1, String var2);

    public void updateFetchLog(EfdcInfo var1, String var2, String var3, Integer var4, String var5);
}

