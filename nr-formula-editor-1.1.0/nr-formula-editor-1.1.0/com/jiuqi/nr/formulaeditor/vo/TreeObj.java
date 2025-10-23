/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import java.util.List;

public class TreeObj {
    private List<TreeObj> children;
    private EditorNodeData data;
    private String title;
    private String code;
    private String parentid;
    private String id;
    private Boolean isLeaf = false;
    private String icons = null;
    private boolean selected = false;
    private Boolean expended = false;
    private boolean onlyChildNodes = false;

    public List<TreeObj> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeObj> children) {
        this.children = children;
    }

    public EditorNodeData getData() {
        return this.data;
    }

    public void setData(EditorNodeData data) {
        this.data = data;
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

    public void setIsLeaf(Boolean leaf) {
        this.isLeaf = leaf;
    }

    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Boolean getExpended() {
        return this.expended;
    }

    public void setExpended(Boolean expended) {
        this.expended = expended;
    }

    public boolean isOnlyChildNodes() {
        return this.onlyChildNodes;
    }

    public void setOnlyChildNodes(boolean onlyChildNodes) {
        this.onlyChildNodes = onlyChildNodes;
    }
}

