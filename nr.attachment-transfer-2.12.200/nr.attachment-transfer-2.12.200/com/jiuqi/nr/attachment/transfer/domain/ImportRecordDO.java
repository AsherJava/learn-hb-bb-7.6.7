/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.domain;

import java.util.Date;

public class ImportRecordDO {
    private String key;
    private String owner;
    private String fileName;
    private long size;
    private int executeNum;
    private Date startTime;
    private Date endTime;
    private String content;
    private int status;
    private double order;

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

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getExecuteNum() {
        return this.executeNum;
    }

    public void setExecuteNum(int executeNum) {
        this.executeNum = executeNum;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

