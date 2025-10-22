/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import org.springframework.web.multipart.MultipartFile;

public interface IDataFillDataEnvImportExportService {
    public AsyncTaskInfo export(DataFillDataQueryInfo var1);

    public AsyncTaskInfo importData(DataFillDataQueryInfo var1, MultipartFile var2);
}

