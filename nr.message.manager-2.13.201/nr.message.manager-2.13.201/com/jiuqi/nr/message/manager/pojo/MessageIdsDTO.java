/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.pojo;

import java.util.List;

@Deprecated
public class MessageIdsDTO {
    private String userId;
    private List<String> messageIds;

    public MessageIdsDTO() {
    }

    public MessageIdsDTO(String userId, List<String> messageIds) {
        this.userId = userId;
        this.messageIds = messageIds;
    }

    public List<String> getMessageIds() {
        return this.messageIds;
    }

    public void setMessageIds(List<String> messageIds) {
        this.messageIds = messageIds;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

