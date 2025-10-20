/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.analysisreport.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.analysisreport.vo.ReportExportVO;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletResponse;

public interface IAnalysisReport2WordService {
    public int getOrder();

    public void report2Word(HttpServletResponse var1, ReportExportVO var2, AsyncTaskMonitor var3) throws Exception;

    default public ByteArrayOutputStream report2Word(ReportExportVO reportExportVO, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        return null;
    }
}

