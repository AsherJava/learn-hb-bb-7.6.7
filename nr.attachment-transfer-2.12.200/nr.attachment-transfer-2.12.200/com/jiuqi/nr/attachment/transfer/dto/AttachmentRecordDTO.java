/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import java.util.List;

public class AttachmentRecordDTO {
    private String key;
    private String fileName;
    private int status;
    private String statusTitle;
    private List<String> entityKeys;
    private String size;
    private int downloadNum;

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

    public int getDownloadNum() {
        return this.downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public String getStatusTitle() {
        return this.statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public static AttachmentRecordDTO getInstance(AttachmentRecordDO recordDO) {
        AttachmentRecordDTO recordDTO = new AttachmentRecordDTO();
        recordDTO.setKey(recordDO.getKey());
        recordDTO.setFileName(recordDO.getFileName());
        recordDTO.setSize(Utils.getFileMBSize(recordDO.getSize()));
        recordDTO.setStatus(recordDO.getStatus());
        recordDTO.setStatusTitle(Constant.GenerateStatus.getTitle(recordDO.getStatus()));
        recordDTO.setDownloadNum(recordDO.getDownloadNum());
        recordDTO.setEntityKeys(recordDO.getEntityKey());
        return recordDTO;
    }
}

