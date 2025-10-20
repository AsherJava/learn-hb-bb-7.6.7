/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.util;

import java.util.ArrayList;
import java.util.List;

public class BizModelTreeNode {
    private List<BizModelTreeNode> children;
    private String name;
    private String code;

    public BizModelTreeNode() {
        this.children = new ArrayList<BizModelTreeNode>();
    }

    public BizModelTreeNode(String code, String name) {
        this.code = code;
        this.name = name;
        this.children = new ArrayList<BizModelTreeNode>();
    }

    public void addChild(BizModelTreeNode child) {
        this.children.add(child);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BizModelTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<BizModelTreeNode> children) {
        this.children = children;
    }
}

