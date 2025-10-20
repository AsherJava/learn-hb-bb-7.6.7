/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public class ReportSyncFileDTO {
    private MultipartFile mainFile;
    private String mainFileId;
    private MultipartFile desAttachFile;
    private String desAttachFileId;
    private Map<String, MultipartFile> fileMap;

    public String getMainFileId() {
        return this.mainFileId;
    }

    public void setMainFileId(String mainFileId) {
        this.mainFileId = mainFileId;
    }

    public String getDesAttachFileId() {
        return this.desAttachFileId;
    }

    public void setDesAttachFileId(String desAttachFileId) {
        this.desAttachFileId = desAttachFileId;
    }

    public MultipartFile getMainFile() {
        return this.mainFile;
    }

    public void setMainFile(MultipartFile mainFile) {
        this.mainFile = mainFile;
    }

    public MultipartFile getDesAttachFile() {
        return this.desAttachFile;
    }

    public void setDesAttachFile(MultipartFile desAttachFile) {
        this.desAttachFile = desAttachFile;
    }

    public Map<String, MultipartFile> getFileMap() {
        return this.fileMap == null ? new HashMap() : this.fileMap;
    }

    public void setFileMap(Map<String, MultipartFile> fileMap) {
        this.fileMap = fileMap;
    }
}

