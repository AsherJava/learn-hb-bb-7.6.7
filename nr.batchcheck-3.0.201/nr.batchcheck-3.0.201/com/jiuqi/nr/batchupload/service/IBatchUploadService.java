/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batchupload.service;

import com.jiuqi.nr.batchupload.bean.TaskDefineData;
import com.jiuqi.nr.batchupload.bean.UploadResult;
import com.jiuqi.nr.batchupload.bean.UploadloadParam;
import java.util.List;

public interface IBatchUploadService {
    public List<TaskDefineData> getUploadTask();

    public List<UploadResult> getUploadEntities(UploadloadParam var1);

    public List<TaskDefineData> getApprovalTask();
}

