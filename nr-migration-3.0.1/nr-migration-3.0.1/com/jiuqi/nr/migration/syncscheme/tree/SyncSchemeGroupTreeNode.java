/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.migration.syncscheme.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.migration.syncscheme.tree.ITreeNodeData;

public class SyncSchemeGroupTreeNode
implements ITreeNodeData {
    private String key;
    private String title;
    private String parent;
    private String parentTitle;
    private String order;
    private boolean isFirst;
    private boolean isLast;

    public SyncSchemeGroupTreeNode() {
    }

    public SyncSchemeGroupTreeNode(String key, String title, String parent, String parentTitle, String order) {
        this.key = key;
        this.title = title;
        this.parent = parent;
        this.parentTitle = parentTitle;
        this.order = order;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentTitle() {
        return this.parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    @JsonProperty(value="isFirst")
    public void setFirst(boolean first) {
        this.isFirst = first;
    }

    public boolean isLast() {
        return this.isLast;
    }

    @JsonProperty(value="isLast")
    public void setLast(boolean last) {
        this.isLast = last;
    }
}

