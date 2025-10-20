/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.provider;

public class ResourceNode {
    private String id;
    private String title;
    private boolean leaf;
    private boolean linkResource = false;
    private String extData;
    private String icon;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLinkResource() {
        return this.linkResource;
    }

    public void setLinkResource(boolean linkResource) {
        this.linkResource = linkResource;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

