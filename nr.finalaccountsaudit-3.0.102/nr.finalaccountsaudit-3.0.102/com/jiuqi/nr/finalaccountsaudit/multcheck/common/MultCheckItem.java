/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.nr.jtable.annotation.JtableLog;

public class MultCheckItem
extends JtableLog {
    private static final long serialVersionUID = 1L;
    private String id;
    private String key;
    private String name;
    private Boolean yssb;
    private String desc;
    private String actionName;
    private String checkType;
    private String checkParams;
    private int index;
    private Object itemSetting;

    public Object getItemSetting() {
        return this.itemSetting;
    }

    public void setItemSetting(Object itemSetting) {
        this.itemSetting = itemSetting;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getYssb() {
        return this.yssb;
    }

    public void setYssb(Boolean yssb) {
        this.yssb = yssb;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckParams() {
        return this.checkParams;
    }

    public void setCheckParams(String checkParams) {
        this.checkParams = checkParams;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

