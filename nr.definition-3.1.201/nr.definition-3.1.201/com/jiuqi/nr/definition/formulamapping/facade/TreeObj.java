/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.formulamapping.facade;

import com.jiuqi.nr.definition.formulamapping.facade.Data;
import java.util.List;

public class TreeObj {
    private List<TreeObj> children;
    private Data data;
    private String title;
    private String code;
    private String parentid;
    private String id;
    private Boolean isLeaf = false;
    private String icons = null;
    private Boolean noDrag = false;
    private Boolean noDrop = false;
    private boolean selected = false;
    private Boolean expended = false;
    private String nodeType = "";
    private String type = "ALL";
    private boolean onlyChildNodes = false;

    public boolean isOnlyChildNodes() {
        return this.onlyChildNodes;
    }

    public void setOnlyChildNodes(boolean onlyChildNodes) {
        this.onlyChildNodes = onlyChildNodes;
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

    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public Boolean getNoDrag() {
        return this.noDrag;
    }

    public void setNoDrag(Boolean noDrag) {
        this.noDrag = noDrag;
    }

    public Boolean getNoDrop() {
        return this.noDrop;
    }

    public void setNoDrop(Boolean noDrop) {
        this.noDrop = noDrop;
    }

    public Boolean getExpended() {
        return this.expended;
    }

    public void setExpended(Boolean expended) {
        this.expended = expended;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

