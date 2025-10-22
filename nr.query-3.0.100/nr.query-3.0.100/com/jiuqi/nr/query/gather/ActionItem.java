/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.gather;

import com.jiuqi.nr.query.gather.ActionType;
import com.jiuqi.nr.query.gather.KeyStore;

public class ActionItem {
    private String code;
    private String title;
    private String desc;
    private ActionType actionType;
    private String parentCode;
    private String params;
    private boolean enablePermission;
    private String icon;
    private String bgColor;
    private KeyStore accelerator;

    public ActionItem(String code, String title) {
        this.code = code;
        this.title = title;
        this.actionType = ActionType.BUTTON;
    }

    public ActionItem(String code, String title, String desc) {
        this(code, title);
        this.desc = desc;
    }

    public ActionItem(String code, String title, String desc, String icon) {
        this(code, title);
        this.desc = desc;
        this.icon = icon;
    }

    public ActionItem(String code, String title, String desc, ActionType actionType) {
        this(code, title, desc);
        this.actionType = actionType;
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

    public boolean isEnablePermission() {
        return this.enablePermission;
    }

    public void setEnablePermission(boolean enablePermission) {
        this.enablePermission = enablePermission;
    }

    public KeyStore getAccelerator() {
        return this.accelerator;
    }

    public void setAccelerator(KeyStore accelerator) {
        this.accelerator = accelerator;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String toString() {
        return "\"code\":\"" + this.code + "\", \"title\":\"" + this.title + "\", \"desc\":\"" + this.desc + "\", \"actionType\":\"" + (Object)((Object)this.actionType) + "\", \"parentCode\":\"" + this.parentCode + "\", \"params\":\"" + this.params + "\", \"enablePermission\":\"" + this.enablePermission + "\", \"icon\":\"" + this.icon + "\", \"bgColor\":\"" + this.bgColor + "\",";
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

