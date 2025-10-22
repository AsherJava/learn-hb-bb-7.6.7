/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service;

import com.jiuqi.nr.fileupload.FileChunkParamInfo;
import com.jiuqi.nr.fileupload.FileChunkReturnInfo;
import com.jiuqi.nr.fileupload.FileMergeReturnInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileChunkUploadService {
    public FileChunkReturnInfo uploadChunk(MultipartFile var1, FileChunkParamInfo var2);

    public FileChunkReturnInfo checkChunkKey(FileChunkParamInfo var1);

    public FileMergeReturnInfo createMergeTask(FileChunkParamInfo var1);

    public FileMergeReturnInfo queryMergeTask(String var1);
}

