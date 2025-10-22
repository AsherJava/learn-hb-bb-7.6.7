/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.para.parain.util;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.parain.bean.exception.SingleParaImportException;
import nr.single.para.parain.controller.SingleParaImportOption;

public interface IParaImportEventServcie {
    public void beforeImport(String var1, SingleParaImportOption var2) throws SingleParaImportException;

    public void afterImport(String var1, ParaImportResult var2, AsyncTaskMonitor var3);
}

