/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemObject;
import com.jiuiqi.nr.unit.treebase.menu.IMenuItemType;
import com.jiuiqi.nr.unit.treebase.menu.MenuItemObjectSerializer;
import java.util.List;
import java.util.Map;

@JsonSerialize(using=MenuItemObjectSerializer.class)
public class MenuItemObject
implements IMenuItemObject {
    private String key;
    private String code;
    private String title;
    private String icon;
    private IMenuItemType type = IMenuItemType.SELECTED;
    private Boolean checked = null;
    private Boolean disabled = null;
    private Boolean divider;
    private int order;
    private Map<String, Object> data;
    private List<IMenuItemObject> children;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public IMenuItemType getType() {
        return this.type;
    }

    public void setType(IMenuItemType type) {
        this.type = type;
    }

    @Override
    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public Boolean getDivider() {
        return this.divider;
    }

    public void setDivider(Boolean divider) {
        this.divider = divider;
    }

    @Override
    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public List<IMenuItemObject> getChildren() {
        return this.children;
    }

    public void setChildren(List<IMenuItemObject> children) {
        this.children = children;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

