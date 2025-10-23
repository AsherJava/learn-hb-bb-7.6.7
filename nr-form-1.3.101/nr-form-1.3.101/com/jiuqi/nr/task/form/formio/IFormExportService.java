/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.form.formio;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.task.form.dto.FormExportDTO;
import javax.servlet.http.HttpServletResponse;

public interface IFormExportService {
    public void exportFormAsync(FormExportDTO var1, AsyncTaskMonitor var2) throws Exception;

    public void download(FormExportDTO var1, HttpServletResponse var2) throws JQException;
}

