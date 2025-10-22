/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public class GroupDefine {
    private String GroupId;
    private String GroupName;
    private String ParentGroupId;
    private int GroupOrder;

    public String getGroupId() {
        return this.GroupId;
    }

    public void setGroupId(String groupId) {
        this.GroupId = groupId;
    }

    public String getGroupName() {
        return this.GroupName;
    }

    public void setGroupName(String groupName) {
        this.GroupName = groupName;
    }

    public String getParentGroupId() {
        return this.ParentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.ParentGroupId = parentGroupId;
    }

    public int getGroupOrder() {
        return this.GroupOrder;
    }

    public void setGroupOrder(int groupOrder) {
        this.GroupOrder = groupOrder;
    }
}

