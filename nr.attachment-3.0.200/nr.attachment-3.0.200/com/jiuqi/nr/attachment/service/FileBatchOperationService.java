/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.nr.attachment.input.BatchDownLoadFileInfo;
import com.jiuqi.nr.attachment.input.FileBatchUploadContext;
import java.io.OutputStream;

public interface FileBatchOperationService {
    @Deprecated
    public void uploadFile(FileBatchUploadContext var1);

    @Deprecated
    public void downloadFile(BatchDownLoadFileInfo var1, OutputStream var2);
}

