/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package nr.single.client.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IUploadFileService {
    public void completeDeleteMarkFile(String var1, AsyncTaskMonitor var2);

    public void incrementDeleteMarkFile(String var1, DimensionCombination var2, String var3, AsyncTaskMonitor var4);
}

