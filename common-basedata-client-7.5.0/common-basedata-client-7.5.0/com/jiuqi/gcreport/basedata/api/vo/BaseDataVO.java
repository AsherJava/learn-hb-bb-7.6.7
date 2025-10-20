/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.api.vo;

import java.util.ArrayList;
import java.util.List;

public class BaseDataVO {
    private String id;
    private String code;
    private String title;
    private String parentid;
    private String ordinal;
    private String[] parents;
    private boolean leaf;
    private List<BaseDataVO> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String[] getParents() {
        return this.parents;
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List<BaseDataVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<BaseDataVO>();
        }
        return this.children;
    }

    public void setChildren(List<BaseDataVO> children) {
        this.children = children;
    }

    public void addChild(BaseDataVO child) {
        if (this.children == null) {
            this.children = new ArrayList<BaseDataVO>();
        }
        this.children.add(child);
    }
}

