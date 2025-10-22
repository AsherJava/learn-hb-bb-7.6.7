/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.datareport.service;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.datareport.obj.ReportDataParam;
import com.jiuqi.nr.datareport.obj.ReportQueryParam;
import com.jiuqi.nr.datareport.obj.ReportTemplateObj;
import java.util.List;

public interface IReportService {
    public List<ReportTemplateObj> list(ReportQueryParam var1, ExecutorContext var2) throws Exception;

    public byte[] export(ReportDataParam var1, ExecutorContext var2) throws Exception;
}

