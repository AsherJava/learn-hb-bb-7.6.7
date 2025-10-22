/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.pojo;

import java.sql.Timestamp;

@Deprecated
public class ParticipantPO {
    private String msgId;
    private String participantId;
    private Integer participantType;
    private Timestamp validTime;
    private Timestamp invalidTime;

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Integer getParticipantType() {
        return this.participantType;
    }

    public void setParticipantType(Integer participantType) {
        this.participantType = participantType;
    }

    public Timestamp getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Timestamp validTime) {
        this.validTime = validTime;
    }

    public Timestamp getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Timestamp invalidTime) {
        this.invalidTime = invalidTime;
    }
}

