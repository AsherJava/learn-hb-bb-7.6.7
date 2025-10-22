/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.service;

import com.jiuqi.nr.attachment.input.FileRelQueryParam;
import com.jiuqi.nr.attachment.input.FileUploadByFileKeyCtx;
import com.jiuqi.nr.attachment.input.FileUploadCtx;
import com.jiuqi.nr.attachment.output.FileRelInfo;
import com.jiuqi.nr.attachment.output.ResultInfo;
import java.util.List;

public interface FileService {
    public ResultInfo uploadFiles(FileUploadCtx var1);

    public ResultInfo uploadFileByFileKey(FileUploadByFileKeyCtx var1);

    public List<FileRelInfo> queryFileRelInfo(FileRelQueryParam var1);
}

