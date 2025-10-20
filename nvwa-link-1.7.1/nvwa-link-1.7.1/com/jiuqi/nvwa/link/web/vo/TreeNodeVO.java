/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.web.vo;

import com.jiuqi.nvwa.link.provider.ResourceParam;
import java.util.List;

public class TreeNodeVO {
    private String group;
    private String key;
    private String code;
    private String title;
    private String icons;
    private boolean isLeaf = false;
    private boolean expanded = false;
    private boolean linkResource = false;
    private String type;
    private String extData;
    private List<ResourceParam> paramList;

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isLinkResource() {
        return this.linkResource;
    }

    public void setLinkResource(boolean linkResource) {
        this.linkResource = linkResource;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public List<ResourceParam> getParamList() {
        return this.paramList;
    }

    public void setParamList(List<ResourceParam> paramList) {
        this.paramList = paramList;
    }
}

