/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.para.paraout.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.para.paraout.bean.ParaExportParam;
import nr.single.para.paraout.bean.ParaExportResult;
import nr.single.para.paraout.bean.exception.ParaExportException;

public interface ITaskFileExportService {
    public ParaExportResult exportNetFormSchemeToSingle(String var1, String var2, ParaExportParam var3, AsyncTaskMonitor var4) throws ParaExportException;
}

