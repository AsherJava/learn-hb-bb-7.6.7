/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.tree;

import com.jiuqi.nr.period.common.tree.Data;
import java.util.List;

public class TreeObj {
    private Boolean expand = false;
    private List<TreeObj> children;
    private Data data;
    private String title;
    private String code;
    private String parentid;
    private String id;
    private Boolean isLeaf = false;
    private String icons = null;
    private Boolean expended = false;
    private String ordinal;
    private String type = "";
    private Boolean selected = false;

    public Boolean getSelected() {
        return this.selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getExpended() {
        return this.expended;
    }

    public void setExpended(Boolean expended) {
        this.expended = expended;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public List<TreeObj> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeObj> children) {
        this.children = children;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }
}

