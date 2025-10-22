/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.tree;

import java.util.List;

public class BTree {
    private String id;
    private String name;
    private boolean leafed;
    private String parentId;
    private boolean parentFlag;
    private List<BTree> children;
    private boolean uploaded;
    private List<String> formOrGroupKeys;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLeafed() {
        return this.leafed;
    }

    public void setLeafed(boolean leafed) {
        this.leafed = leafed;
    }

    public List<BTree> getChildren() {
        return this.children;
    }

    public void setChildren(List<BTree> children) {
        this.children = children;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isParentFlag() {
        return this.parentFlag;
    }

    public void setParentFlag(boolean parentFlag) {
        this.parentFlag = parentFlag;
    }

    public boolean isUploaded() {
        return this.uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public List<String> getFormOrGroupKeys() {
        return this.formOrGroupKeys;
    }

    public void setFormOrGroupKeys(List<String> formOrGroupKeys) {
        this.formOrGroupKeys = formOrGroupKeys;
    }
}

