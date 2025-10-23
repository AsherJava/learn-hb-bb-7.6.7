/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import java.util.function.Predicate;

public class TreeNodeQueryParam {
    private String schemeKey;
    private String versionKey;
    @Deprecated
    private String period;
    private String location;
    private String keyword;
    private NodeType type;
    private boolean checkChildren;
    private Predicate<INode> nodeDisabled;
    private Predicate<INode> nodeFilter;
    private boolean removeEmptyGroup;

    public boolean isRemoveEmptyGroup() {
        return this.removeEmptyGroup;
    }

    public void setRemoveEmptyGroup(boolean removeEmptyGroup) {
        this.removeEmptyGroup = removeEmptyGroup;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public Predicate<INode> getNodeDisabled() {
        if (this.nodeDisabled == null) {
            this.nodeDisabled = n -> false;
        }
        return this.nodeDisabled;
    }

    public void setNodeDisabled(Predicate<INode> nodeDisabled) {
        this.nodeDisabled = nodeDisabled;
    }

    public Predicate<INode> getNodeFilter() {
        if (this.nodeFilter == null) {
            this.nodeFilter = n -> true;
        }
        return this.nodeFilter;
    }

    public void setNodeFilter(Predicate<INode> nodeFilter) {
        this.nodeFilter = nodeFilter;
    }

    @Deprecated
    public String getPeriod() {
        return this.period;
    }

    @Deprecated
    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean isCheckChildren() {
        return this.checkChildren;
    }

    public void setCheckChildren(boolean checkChildren) {
        this.checkChildren = checkChildren;
    }
}

