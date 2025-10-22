/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import java.util.List;

public class GenerateStatusDTO {
    private long total;
    private long enableDownLoad;
    private long finish;
    private List<AttachmentRecordDTO> records;
    private int taskStatus;
    private long totalUnit;

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getEnableDownLoad() {
        return this.enableDownLoad;
    }

    public void setEnableDownLoad(long enableDownLoad) {
        this.enableDownLoad = enableDownLoad;
    }

    public long getFinish() {
        return this.finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public List<AttachmentRecordDTO> getRecords() {
        return this.records;
    }

    public void setRecords(List<AttachmentRecordDTO> records) {
        this.records = records;
    }

    public int getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public long getTotalUnit() {
        return this.totalUnit;
    }

    public void setTotalUnit(long totalUnit) {
        this.totalUnit = totalUnit;
    }
}

