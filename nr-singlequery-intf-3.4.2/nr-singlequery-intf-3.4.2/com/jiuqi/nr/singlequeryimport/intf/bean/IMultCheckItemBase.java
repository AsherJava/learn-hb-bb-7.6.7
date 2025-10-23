/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.intf.bean;

import java.util.List;

public class IMultCheckItemBase {
    private String id;
    private String type;
    private String label;
    private String groupId;
    private List<IMultCheckItemBase> children;

    public List<IMultCheckItemBase> getChildren() {
        return this.children;
    }

    public void setChildren(List<IMultCheckItemBase> children) {
        this.children = children;
    }

    public IMultCheckItemBase(String id, String type, String label) {
        this.id = id;
        this.type = type;
        this.label = label;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

