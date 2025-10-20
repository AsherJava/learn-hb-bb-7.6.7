/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.intf;

public class QueryTableParamItem {
    private String name;
    private String title;
    private Object defaultValue;
    private boolean nullAble = true;

    public QueryTableParamItem() {
    }

    public QueryTableParamItem(String name, String title, Object defaultValue) {
        this.name = name;
        this.title = title;
        this.defaultValue = defaultValue;
    }

    public QueryTableParamItem(String name, String title, Object defaultValue, boolean nullAble) {
        this.name = name;
        this.title = title;
        this.defaultValue = defaultValue;
        this.nullAble = nullAble;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNullAble() {
        return this.nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }
}

