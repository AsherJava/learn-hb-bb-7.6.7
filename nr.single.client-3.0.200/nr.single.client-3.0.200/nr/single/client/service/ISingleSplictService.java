/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo;
import nr.single.map.data.exception.SingleDataException;

public interface ISingleSplictService {
    public void splitSingleFile(String var1, String var2, String var3, AsyncTaskMonitor var4) throws SingleDataException;

    public void splitSingleFileByOption(SingleDataSplictInfo var1, AsyncTaskMonitor var2) throws SingleDataException;
}

