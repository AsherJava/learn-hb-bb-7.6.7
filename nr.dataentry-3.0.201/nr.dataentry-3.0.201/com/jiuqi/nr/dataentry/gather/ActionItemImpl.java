/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.KeyStore;

public class ActionItemImpl
implements ActionItem {
    private String code;
    private String title;
    private String desc;
    private ActionType actionType;
    private String parentCode;
    private String params;
    private String paramsDesc;
    private boolean enablePermission;
    private String icon;
    private String bgColor;
    private KeyStore accelerator;
    private String alias;

    public ActionItemImpl(String code, String title) {
        this.code = code;
        this.title = title;
        this.actionType = ActionType.BUTTON;
    }

    public ActionItemImpl(String code, String title, String desc) {
        this(code, title);
        this.desc = desc;
    }

    public ActionItemImpl(String code, String title, String desc, String icon) {
        this(code, title);
        this.desc = desc;
        this.icon = icon;
    }

    public ActionItemImpl(String code, String title, String desc, ActionType actionType) {
        this(code, title, desc);
        this.actionType = actionType;
    }

    public ActionItemImpl(String code, String title, String desc, ActionType actionType, String alias) {
        this(code, title, desc, actionType);
        this.alias = alias;
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
    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public boolean isEnablePermission() {
        return this.enablePermission;
    }

    public void setEnablePermission(boolean enablePermission) {
        this.enablePermission = enablePermission;
    }

    @Override
    public KeyStore getAccelerator() {
        return this.accelerator;
    }

    public void setAccelerator(KeyStore accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public String getParamsDesc() {
        return this.paramsDesc;
    }

    public void setParamsDesc(String paramsDesc) {
        this.paramsDesc = paramsDesc;
    }

    public String toString() {
        return "\"code\":\"" + this.code + "\", \"title\":\"" + this.title + "\", \"desc\":\"" + this.desc + "\", \"actionType\":\"" + (Object)((Object)this.actionType) + "\", \"parentCode\":\"" + this.parentCode + "\", \"params\":\"" + this.params + "\", \"enablePermission\":\"" + this.enablePermission + "\", \"icon\":\"" + this.icon + "\", \"bgColor\":\"" + this.bgColor + "\",";
    }

    @Override
    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

