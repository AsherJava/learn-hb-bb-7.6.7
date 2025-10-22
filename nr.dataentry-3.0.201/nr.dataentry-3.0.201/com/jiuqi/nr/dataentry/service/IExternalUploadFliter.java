/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.ExternalBatchUploadResult
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExternalUploadResult;
import com.jiuqi.nr.jtable.params.output.ExternalBatchUploadResult;

public interface IExternalUploadFliter {
    public ExternalBatchUploadResult batchUpload(BatchExecuteTaskParam var1);

    public ExternalUploadResult upload(ExecuteTaskParam var1);
}

