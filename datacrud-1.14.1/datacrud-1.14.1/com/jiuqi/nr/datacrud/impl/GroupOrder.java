/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl;

public class GroupOrder
implements Comparable<GroupOrder> {
    private int level = Integer.MAX_VALUE;
    private String groupKey = "";

    public GroupOrder(int level) {
        this.level = level;
    }

    public GroupOrder(int level, String groupKey) {
        this.level = level;
        this.groupKey = groupKey;
    }

    public GroupOrder() {
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    @Override
    public int compareTo(GroupOrder o) {
        int level = this.level - o.level;
        if (level == 0) {
            return this.groupKey.compareTo(o.groupKey);
        }
        return level;
    }
}

