/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MessageMerge {
    private String userId;
    private String unitId;
    private Set<String> careKeys;
    private List<String> userIds;

    public String getUserId() {
        return this.userId;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MessageMerge merge = (MessageMerge)o;
        return Objects.equals(this.careKeys, merge.careKeys);
    }

    public int hashCode() {
        return this.careKeys != null ? this.careKeys.hashCode() : 0;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }

    public static Collection<MessageMerge> merge(Collection<MessageMerge> values) {
        HashMap<MessageMerge, MessageMerge> map = new HashMap<MessageMerge, MessageMerge>();
        for (MessageMerge value : values) {
            MessageMerge merge = (MessageMerge)map.get(value);
            if (merge == null) {
                value.userIds = new ArrayList<String>();
                value.userIds.add(value.getUserId());
                map.put(value, value);
                continue;
            }
            merge.userIds.add(value.getUserId());
        }
        return map.values();
    }
}

