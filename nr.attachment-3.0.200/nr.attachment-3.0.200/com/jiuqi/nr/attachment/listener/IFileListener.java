/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.listener;

import com.jiuqi.nr.attachment.listener.param.FileBatchDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileUpdateEvent;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;

public interface IFileListener {
    public void afterFileUpload(FileUploadEvent var1);

    public void beforeFileDelete(FileDelEvent var1);

    public void afterFileDelete(FileDelEvent var1);

    public void beforeBatchFileDelete(FileBatchDelEvent var1);

    public void afterBatchFileDelete(FileBatchDelEvent var1);

    public void afterFileUpdate(FileUpdateEvent var1);
}

