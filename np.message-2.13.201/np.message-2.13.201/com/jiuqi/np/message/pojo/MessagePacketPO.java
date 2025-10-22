/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.pojo;

import java.sql.Timestamp;

public class MessagePacketPO {
    private String id;
    private String title;
    private Timestamp sendTime;
    private Timestamp latestHandleTime;
    private Integer state;
    private Integer type;
    private String msgBody;
    private long threadId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Timestamp getLatestHandleTime() {
        return this.latestHandleTime;
    }

    public void setLatestHandleTime(Timestamp latestHandleTime) {
        this.latestHandleTime = latestHandleTime;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsgBody() {
        return this.msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public long getThreadId() {
        return this.threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String toString() {
        return "MessagePacketPO{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", sendTime=" + this.sendTime + ", latestHandleTime=" + this.latestHandleTime + ", state=" + this.state + ", type=" + this.type + ", threadId=" + this.threadId + '}';
    }
}

