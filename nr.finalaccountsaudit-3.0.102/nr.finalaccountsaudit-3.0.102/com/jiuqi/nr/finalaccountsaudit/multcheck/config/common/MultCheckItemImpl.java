/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.common;

import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.IMultCheckItemBase;
import java.util.List;

public class MultCheckItemImpl
implements IMultCheckItemBase {
    private String id;
    private String type;
    private String label;
    private String groupId;
    private List<IMultCheckItemBase> children;

    @Override
    public List<IMultCheckItemBase> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<IMultCheckItemBase> children) {
        this.children = children;
    }

    public MultCheckItemImpl(String id, String type, String label) {
        this.id = id;
        this.type = type;
        this.label = label;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

