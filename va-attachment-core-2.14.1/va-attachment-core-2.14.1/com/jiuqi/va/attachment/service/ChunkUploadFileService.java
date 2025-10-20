/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.oss.feign.domain.VaChunkUploadObjectInfo
 *  com.jiuqi.va.oss.feign.domain.VaChunkUploadVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.oss.feign.domain.VaChunkUploadObjectInfo;
import com.jiuqi.va.oss.feign.domain.VaChunkUploadVO;
import org.springframework.web.multipart.MultipartFile;

public interface ChunkUploadFileService {
    public R init(VaChunkUploadObjectInfo var1);

    public R upload(MultipartFile var1, VaChunkUploadVO var2);

    public R finishUpload(VaChunkUploadObjectInfo var1);
}

