/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.vo;

import java.util.Set;

public class DelLinkVO {
    private String groupKey;
    private Set<String> filedKeys;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Set<String> getFiledKeys() {
        return this.filedKeys;
    }

    public String[] getFiledArray() {
        if (this.filedKeys == null) {
            return null;
        }
        return this.filedKeys.toArray(new String[0]);
    }

    public void setFiledKeys(Set<String> filedKeys) {
        this.filedKeys = filedKeys;
    }

    public String toString() {
        return "DelLinkVO{groupKey='" + this.groupKey + '\'' + ", filedKeys=" + this.filedKeys + '}';
    }
}

