/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.budget.iview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class IViewTreeItem<T> {
    private String title;
    private String code;
    private String name;
    private String id;
    private String uniquekey;
    private boolean expand;
    private boolean disabled;
    private boolean disableCheckbox;
    private boolean selected;
    private boolean checked;
    private List<IViewTreeItem<T>> children;
    private String dataType;
    private T data;
    @JsonIgnore
    private IViewTreeItem<T> parent;
    private long count;

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return this.disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<IViewTreeItem<T>> getChildren() {
        return this.children;
    }

    public void setChildren(List<IViewTreeItem<T>> children) {
        this.children = children;
    }

    public String getUniquekey() {
        return this.uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public T getData() {
        return this.data;
    }

    public IViewTreeItem<T> getParent() {
        return this.parent;
    }

    public void setParent(IViewTreeItem<T> parent) {
        this.parent = parent;
    }

    public <F extends IViewTreeItem<T>> void addChild(F child) {
        if (this.children == null) {
            this.children = new ArrayList<IViewTreeItem<T>>();
        }
        this.children.add(child);
    }
}

