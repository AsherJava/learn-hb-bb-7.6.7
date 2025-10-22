/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.domain;

import java.util.Date;
import java.util.List;

public class AttachmentRecordDO {
    private String key;
    private List<String> entityKey;
    private String fileName;
    private int status;
    private long size;
    private int downloadNum;
    private long order;
    private Date startTime;
    private Date endTime;
    private boolean history;
    private String content;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(List<String> entityKey) {
        this.entityKey = entityKey;
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

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDownloadNum() {
        return this.downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public boolean isHistory() {
        return this.history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getOrder() {
        return this.order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

