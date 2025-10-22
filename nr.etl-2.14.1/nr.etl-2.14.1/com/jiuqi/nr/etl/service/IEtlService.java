/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.etl.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.etl.common.ETLTask;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlExecuteParam;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlReturnInfo;
import com.jiuqi.nr.etl.common.TreeNodeImpl;
import java.util.List;
import java.util.Map;

public interface IEtlService {
    public boolean testLink(String var1, String var2, String var3);

    public List<ETLTask> getAllTask();

    public ETLTask findTaskByName(String var1);

    public EtlExecuteInfo execute(EtlExecuteParam var1);

    public List<TreeNodeImpl> getReportTask() throws Exception;

    public Map<String, EtlReturnInfo> ETLExecute(EtlInfo var1, AsyncTaskMonitor var2);
}

