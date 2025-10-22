/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.KeyStore;
import java.util.List;

public class TreeNodeItem {
    private String code;
    private String title;
    private String alias;
    private String desc;
    private ActionType actionType;
    private String parentCode;
    private String params;
    private boolean enablePermission;
    private String paramsDesc;
    private String icon;
    private String bgColor;
    private KeyStore accelerator;
    private List<TreeNodeItem> children;
    private String nodeKey;
    private boolean expand;
    private boolean selected;

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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public boolean isEnablePermission() {
        return this.enablePermission;
    }

    public void setEnablePermission(boolean enablePermission) {
        this.enablePermission = enablePermission;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public KeyStore getAccelerator() {
        return this.accelerator;
    }

    public void setAccelerator(KeyStore accelerator) {
        this.accelerator = accelerator;
    }

    public List<TreeNodeItem> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNodeItem> children) {
        this.children = children;
    }

    public String getNodeKey() {
        return this.nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getParamsDesc() {
        return this.paramsDesc;
    }

    public void setParamsDesc(String paramsDesc) {
        this.paramsDesc = paramsDesc;
    }
}

