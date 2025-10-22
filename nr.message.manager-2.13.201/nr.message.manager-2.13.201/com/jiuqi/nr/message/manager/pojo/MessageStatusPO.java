/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.pojo;

@Deprecated
public class MessageStatusPO {
    private String msgId;
    private String userId;
    private int status;

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

