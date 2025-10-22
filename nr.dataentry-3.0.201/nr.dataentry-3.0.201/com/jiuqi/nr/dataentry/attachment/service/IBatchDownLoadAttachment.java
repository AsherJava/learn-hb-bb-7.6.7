/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.attachment.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;

public interface IBatchDownLoadAttachment {
    public void batchDownloadAttachments(BatchDownLoadEnclosureInfo var1, AsyncTaskMonitor var2) throws Exception;
}

