/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;

public class ZbGroupNode
implements INode {
    private Boolean first;
    private Boolean last;
    private String key;
    private String parentKey;
    private String title;

    public ZbGroupNode() {
    }

    public ZbGroupNode(ZbGroup zbGroup) {
        if (zbGroup != null) {
            this.key = zbGroup.getKey();
            this.parentKey = zbGroup.getParentKey();
            this.title = zbGroup.getTitle();
        }
    }

    @Override
    public String getCode() {
        return null;
    }

    public Boolean isFirst() {
        return this.first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean isLast() {
        return this.last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ZB_GROUP;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

