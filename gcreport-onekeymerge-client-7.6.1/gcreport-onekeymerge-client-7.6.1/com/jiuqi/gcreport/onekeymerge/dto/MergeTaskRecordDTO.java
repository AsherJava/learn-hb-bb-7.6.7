/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.onekeymerge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class MergeTaskRecordDTO {
    private String id;
    private String taskCodes;
    private String userName;
    @JsonFormat(timezone="GMT+8", pattern="yyyyMMdd HH:mm:ss")
    private Date createTime;
    private String taskState;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskCodes() {
        return this.taskCodes;
    }

    public void setTaskCodes(String taskCodes) {
        this.taskCodes = taskCodes;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String toString() {
        return "MergeTaskRecordDTO{id='" + this.id + '\'' + ", taskCodes='" + this.taskCodes + '\'' + ", userName='" + this.userName + '\'' + ", createTime=" + this.createTime + ", taskState='" + this.taskState + '\'' + '}';
    }
}

