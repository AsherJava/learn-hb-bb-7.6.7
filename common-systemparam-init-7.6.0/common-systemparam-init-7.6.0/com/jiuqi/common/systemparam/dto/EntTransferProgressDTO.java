/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.systemparam.dto;

import com.jiuqi.common.systemparam.enums.EntInitMsgType;

public class EntTransferProgressDTO {
    private String progressId;
    private String msg;
    private double position;
    private EntInitMsgType status;

    public EntTransferProgressDTO() {
    }

    public EntTransferProgressDTO(String progressId, String msg, double position, EntInitMsgType status) {
        this.progressId = progressId;
        this.msg = msg;
        this.position = position;
        this.status = status;
    }

    public String getProgressId() {
        return this.progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getPosition() {
        return this.position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public EntInitMsgType getMsgType() {
        return this.status;
    }

    public void setStatus(EntInitMsgType status) {
        this.status = status;
    }
}

