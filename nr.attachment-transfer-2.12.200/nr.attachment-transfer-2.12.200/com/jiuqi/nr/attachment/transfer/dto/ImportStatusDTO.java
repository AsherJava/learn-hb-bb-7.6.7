/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dto;

import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import java.util.List;

public class ImportStatusDTO {
    private long total;
    private long success;
    private long failed;
    private long running;
    private List<ImportRecordDTO> records;
    private int status;
    private long waitTing;

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSuccess() {
        return this.success;
    }

    public long getRunning() {
        return this.running;
    }

    public void setRunning(long running) {
        this.running = running;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getFailed() {
        return this.failed;
    }

    public void setFailed(long failed) {
        this.failed = failed;
    }

    public List<ImportRecordDTO> getRecords() {
        return this.records;
    }

    public void setRecords(List<ImportRecordDTO> records) {
        this.records = records;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getWaitTing() {
        return this.waitTing;
    }

    public void setWaitTing(long waitTing) {
        this.waitTing = waitTing;
    }
}

