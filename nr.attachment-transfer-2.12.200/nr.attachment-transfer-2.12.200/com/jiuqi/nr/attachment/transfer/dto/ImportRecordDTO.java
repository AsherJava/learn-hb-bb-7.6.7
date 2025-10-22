/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.domain.ImportRecordDO;

public class ImportRecordDTO {
    private String key;
    private String fileName;
    private String path;
    private int status;
    private String size;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public static ImportRecordDTO getInstance(ImportRecordDO importRecordDO) {
        ImportRecordDTO importRecordDTO = new ImportRecordDTO();
        importRecordDTO.setKey(importRecordDO.getKey());
        importRecordDTO.setFileName(importRecordDO.getFileName());
        importRecordDTO.setStatus(importRecordDO.getStatus());
        importRecordDTO.setSize(Utils.getFileMBSize(importRecordDO.getSize()));
        return importRecordDTO;
    }
}

