/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ReminderMessage {
    private List<String> userIds;
    private String content;
    private Set<String> sendChannels = new LinkedHashSet<String>();
    private String unitId;
    private Set<String> careKeys;

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Set<String> getCareKeys() {
        return this.careKeys;
    }

    public void setCareKeys(Set<String> careKeys) {
        this.careKeys = careKeys;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getSendChannels() {
        return this.sendChannels;
    }

    public void setSendChannels(Set<String> sendChannels) {
        this.sendChannels = sendChannels;
    }
}

