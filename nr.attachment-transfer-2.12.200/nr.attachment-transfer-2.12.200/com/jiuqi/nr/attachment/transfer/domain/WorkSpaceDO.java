/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.domain;

import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;

public class WorkSpaceDO {
    private int thread;
    private int spaceSize;
    private String filePath;
    private String serverId;
    private int type;
    private String url;

    public int getThread() {
        return this.thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public int getSpaceSize() {
        return this.spaceSize;
    }

    public void setSpaceSize(int spaceSize) {
        this.spaceSize = spaceSize;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getServerId() {
        return this.serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static WorkSpaceDO getInstance(WorkSpaceDTO workSpaceDTO) {
        WorkSpaceDO workSpaceDO = new WorkSpaceDO();
        workSpaceDO.setThread(workSpaceDTO.getThread());
        workSpaceDO.setSpaceSize(workSpaceDTO.getSpaceSize());
        workSpaceDO.setFilePath(workSpaceDTO.getFilePath());
        workSpaceDO.setType(workSpaceDTO.getType());
        workSpaceDO.setUrl(workSpaceDTO.getUrl());
        return workSpaceDTO;
    }
}

