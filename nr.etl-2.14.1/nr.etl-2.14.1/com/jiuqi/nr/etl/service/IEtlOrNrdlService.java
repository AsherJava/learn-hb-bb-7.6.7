/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.etl.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlExecuteParam;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlReturnInfo;
import com.jiuqi.nr.etl.common.TreeNodeImpl;
import com.jiuqi.nr.etl.common.UniversalTask;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

public interface IEtlOrNrdlService {
    public int testLink(String var1, String var2, String var3);

    public List<? extends UniversalTask> getAllTask() throws JQException;

    public UniversalTask findTaskByName(String var1);

    public EtlExecuteInfo execute(EtlExecuteParam var1) throws JQException;

    public EtlExecuteInfo executePlanTask(String var1, String var2, String var3, String var4, String var5, Logger var6) throws JQException;

    public List<TreeNodeImpl> getReportTask() throws Exception;

    public Map<String, EtlReturnInfo> ETLExecute(EtlInfo var1, AsyncTaskMonitor var2);

    public Map<String, EtlReturnInfo> executeTask(EtlInfo var1, AsyncTaskMonitor var2) throws JQException;
}

