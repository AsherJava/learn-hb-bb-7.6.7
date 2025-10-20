/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.formulamapping.facade.Data;
import java.util.List;

public class ExtNode {
    private boolean checked = false;
    private int childCount = 0;
    private List<ExtNode> children;
    private boolean onlyChildNodes = false;
    private String code;
    private Data data;
    private boolean disabled = false;
    private boolean expanded = false;
    private String icons = null;
    private Boolean isLeaf = false;
    private String key;
    private Boolean noDrag = false;
    private Boolean noDrop = false;
    private boolean selected = false;
    private String title;

    public boolean getOnlyChildNodes() {
        return this.onlyChildNodes;
    }

    public void setOnlyChildNodes(boolean onlyChildNodes) {
        this.onlyChildNodes = onlyChildNodes;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean getExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ExtNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<ExtNode> children) {
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

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

