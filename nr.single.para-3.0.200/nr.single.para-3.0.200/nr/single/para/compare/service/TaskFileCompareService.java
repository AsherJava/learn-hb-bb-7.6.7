/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.para.compare.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.List;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.exception.SingleCompareException;

public interface TaskFileCompareService {
    public ParaCompareResult findNetTaskBySingle(String var1, byte[] var2, AsyncTaskMonitor var3) throws Exception;

    public ParaCompareResult findNetTaskBySingle(String var1, byte[] var2, boolean var3, AsyncTaskMonitor var4) throws Exception;

    public ParaCompareResult findNetFormShemesByTaskAndYear(String var1, String var2, String var3, AsyncTaskMonitor var4) throws Exception;

    public ParaCompareResult compareSingleToTasK(String var1, String var2, String var3, String var4, ParaCompareOption var5, AsyncTaskMonitor var6) throws Exception;

    public ParaCompareResult compareSingleToTasKByType(CompareDataType var1, String var2, String var3, ParaCompareOption var4, AsyncTaskMonitor var5) throws Exception;

    public ParaCompareResult batchCompareSingleToTasKByType(CompareDataType var1, List<String> var2, String var3, ParaCompareOption var4, AsyncTaskMonitor var5) throws Exception;

    public ParaCompareResult importSingleToTask(String var1, ParaCompareOption var2, AsyncTaskMonitor var3) throws Exception;

    public ParaCompareResult batchDelete(String var1, AsyncTaskMonitor var2) throws Exception;

    public List<ParaCompareResult> batchDeleteByKeys(List<String> var1, AsyncTaskMonitor var2) throws Exception;

    public List<String> getSingleEnumCodeInFmdm(String var1) throws SingleCompareException;
}

