/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 */
package nr.single.client.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.map.configurations.bean.ISingleMappingConfig;

public interface IUploadJioAfterService {
    public void uploadJioAfterSuccess(JIOImportResultObject var1, ISingleMappingConfig var2, UploadParam var3, AsyncTaskMonitor var4);
}

