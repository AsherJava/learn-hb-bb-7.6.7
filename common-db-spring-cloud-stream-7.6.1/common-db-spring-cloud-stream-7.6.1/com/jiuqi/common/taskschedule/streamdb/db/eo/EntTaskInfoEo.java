/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb.db.eo;

import java.util.Date;

public class EntTaskInfoEo {
    public static final String TABLE_NAME = "ENT_TASK_INFO";
    public static final String TABLE_HISTORY_NAME = "ENT_TASK_INFO_HISTORY";
    public static final String CLOB_TABLE_NAME = "ENT_TASK_INFO_CLOB";
    public static final String CLOB_ERRORR_TABLE_NAME = "ENT_TASK_MESSAGE_ERRORRESULT";
    private String id;
    private Long ver;
    private String queueName;
    private String messageBody;
    private Integer status;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String serveId;
    private String serveName;
    private Integer storeType = 1;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getStoreType() {
        return this.storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
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

    public String getServeId() {
        return this.serveId;
    }

    public void setServeId(String serveId) {
        this.serveId = serveId;
    }

    public String getServeName() {
        return this.serveName;
    }

    public void setServeName(String serveName) {
        this.serveName = serveName;
    }
}

