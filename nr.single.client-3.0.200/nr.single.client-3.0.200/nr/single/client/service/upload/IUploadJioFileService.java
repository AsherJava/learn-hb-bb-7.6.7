/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 */
package nr.single.client.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;

public interface IUploadJioFileService {
    public ImportResultObject upload(String var1, String var2, UploadParam var3, AsyncTaskMonitor var4, double var5, double var7);

    public ImportResultObject upload(String var1, String var2, String var3, UploadParam var4, AsyncTaskMonitor var5, double var6, double var8);
}

