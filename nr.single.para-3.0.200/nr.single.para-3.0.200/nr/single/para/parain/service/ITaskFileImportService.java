/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.para.parain.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.parain.controller.SingleParaImportOption;

public interface ITaskFileImportService {
    public String ImportSingleToTask(String var1, String var2) throws Exception;

    public String ImportSingleToFormScheme(String var1, String var2, String var3, SingleParaImportOption var4) throws Exception;

    public String ImportSingleToFormScheme(String var1, String var2, String var3, SingleParaImportOption var4, AsyncTaskMonitor var5) throws Exception;

    public String ImportSingleToFormScheme(String var1, String var2, String var3, String var4, SingleParaImportOption var5, AsyncTaskMonitor var6) throws Exception;

    public String ImportSingleToFormScheme(String var1, SingleParaImportOption var2, AsyncTaskMonitor var3, ParaImportResult var4) throws Exception;

    public String ImportSingleToFormScheme(String var1, AsyncTaskMonitor var2) throws Exception;
}

