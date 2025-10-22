/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.dto;

import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ReportDataSyncParamFileDTO {
    private String syncDesAttachId;
    private CommonFileDTO syncDesAttachFile;
    private String syncParamAttachId;
    private CommonFileDTO syncParamAttachFile;

    public String getSyncDesAttachId() {
        return this.syncDesAttachId;
    }

    public void setSyncDesAttachId(String syncDesAttachId) {
        this.syncDesAttachId = syncDesAttachId;
    }

    public VaParamSyncMultipartFile getSyncDesAttachFile() {
        return this.syncDesAttachFile;
    }

    public void setSyncDesAttachFile(CommonFileDTO syncDesAttachFile) {
        this.syncDesAttachFile = syncDesAttachFile;
    }

    public String getSyncParamAttachId() {
        return this.syncParamAttachId;
    }

    public void setSyncParamAttachId(String syncParamAttachId) {
        this.syncParamAttachId = syncParamAttachId;
    }

    public MultipartFile getSyncParamAttachFile() {
        return this.syncParamAttachFile;
    }

    public void setSyncParamAttachFile(CommonFileDTO syncParamAttachFile) {
        this.syncParamAttachFile = syncParamAttachFile;
    }
}

