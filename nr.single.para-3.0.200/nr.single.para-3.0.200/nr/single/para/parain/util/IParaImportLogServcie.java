/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.para.parain.util;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.para.compare.bean.ParaImportResult;

public interface IParaImportLogServcie {
    public String makeDetailLogFile(ParaImportResult var1, AsyncTaskMonitor var2) throws Exception;

    public void deleteLogFile(String var1);
}

