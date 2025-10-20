/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.join.api.domain;

import com.jiuqi.va.join.api.domain.ReplyStatus;

public class ReplyTo {
    private ReplyStatus replyStatus;
    private String replyMessage;

    public ReplyTo() {
    }

    public ReplyTo(ReplyStatus replyStatus) {
        this.replyStatus = replyStatus;
    }

    public ReplyTo(ReplyStatus replyStatus, String replyMessage) {
        this.replyStatus = replyStatus;
        this.replyMessage = replyMessage;
    }

    public ReplyStatus getReplyStatus() {
        return this.replyStatus;
    }

    public void setReplyStatus(ReplyStatus replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getReplyMessage() {
        return this.replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }
}

