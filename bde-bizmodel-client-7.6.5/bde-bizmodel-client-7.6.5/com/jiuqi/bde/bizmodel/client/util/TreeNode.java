/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.util;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private List<TreeNode> children;
    private String name;
    private String code;
    private String title;

    public TreeNode() {
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode(String name, String code) {
        this.name = name;
        this.code = code;
        this.title = name + "  " + code;
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode(String name) {
        this.name = name;
        this.title = name;
        this.children = new ArrayList<TreeNode>();
    }

    public void addChild(TreeNode child) {
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}

