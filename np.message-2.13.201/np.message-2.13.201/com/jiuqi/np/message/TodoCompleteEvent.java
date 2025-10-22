/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message;

import java.util.List;

public class TodoCompleteEvent {
    private String messageId;
    private String userId;
    private List<String> formSchemeKey;
    private String actionCode;
    private boolean isSign;
    private String signType;
    private String signTag;

    public TodoCompleteEvent() {
    }

    public TodoCompleteEvent(String messageId, String userId) {
        this.messageId = messageId;
        this.userId = userId;
    }

    public TodoCompleteEvent(String messageId, String userId, String actionCode) {
        this.messageId = messageId;
        this.userId = userId;
        this.actionCode = actionCode;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(List<String> formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public boolean isSign() {
        return this.isSign;
    }

    public void setSign(boolean sign) {
        this.isSign = sign;
    }

    public String getSignType() {
        return this.signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignTag() {
        return this.signTag;
    }

    public void setSignTag(String signTag) {
        this.signTag = signTag;
    }
}

