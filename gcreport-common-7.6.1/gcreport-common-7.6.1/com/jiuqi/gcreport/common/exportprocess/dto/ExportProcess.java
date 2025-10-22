/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.exportprocess.dto;

import java.util.Date;

public class ExportProcess {
    private transient Date createDate;
    private boolean finish = false;
    private Float processPercent = Float.valueOf(0.0f);
    private String message = "";

    public ExportProcess() {
        this.createDate = new Date();
    }

    public void writeInfoLog(String logs, Float percent) {
        this.processPercent = percent;
        this.message = logs;
    }

    public boolean isFinish() {
        return this.finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Float getProcessPercent() {
        return this.processPercent;
    }

    public void setProcessPercent(Float processPercent) {
        this.processPercent = processPercent;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

