/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    public AsyncTaskInfo upload(MultipartFile var1, UploadParam var2);

    public AsyncTaskInfo upload(UploadParam var1);

    public AsyncTaskInfo uploadFile(UploadParam var1, String var2, File var3);

    public ImportResultObject importResult(String var1);

    public void uploadAsync(AsyncTaskMonitor var1, UploadParam var2, String var3, File var4);

    public ImportResultObject getMappingConfig(UploadParam var1);

    public ImportResultObject getMappingConfig(MultipartFile var1, UploadParam var2);

    public boolean singleImportCheck(UploadParam var1);
}

