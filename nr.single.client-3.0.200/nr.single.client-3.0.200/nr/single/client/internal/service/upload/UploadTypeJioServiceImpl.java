/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.service.IUploadTypeJioService
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.service.IUploadTypeJioService;
import nr.single.client.service.upload.IUploadJioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadTypeJioServiceImpl
implements IUploadTypeJioService {
    @Autowired
    private IUploadJioFileService uploadJioFileService;

    public ImportResultObject upload(String fileName, String path, UploadParam param, AsyncTaskMonitor asyncTaskMonitor, double begin, double span) {
        return this.uploadJioFileService.upload(fileName, path, param, asyncTaskMonitor, begin, span);
    }
}

