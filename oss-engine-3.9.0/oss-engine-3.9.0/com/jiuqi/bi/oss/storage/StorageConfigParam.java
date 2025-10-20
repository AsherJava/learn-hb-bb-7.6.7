/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import java.util.ArrayList;
import java.util.List;

public class StorageConfigParam {
    public static final String WIDGET_TYPE_INPUT = "input";
    public static final String WIDGET_TYPE_CHECKBOX = "checkbox";
    public static final String WIDGET_TYPE_SELECT = "select";
    public static final String WIDGET_TYPE_PASSWORD = "password";
    private String name;
    private String title;
    private String defaultValue;
    private String widgetType = "input";
    private boolean nullable = true;
    private List<String> selectItems = new ArrayList<String>();
    private String desc;

    public StorageConfigParam() {
    }

    public StorageConfigParam(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public StorageConfigParam(String name, String title, boolean nullable) {
        this.name = name;
        this.title = title;
        this.nullable = nullable;
    }

    public StorageConfigParam(String name, String title, String defaultValue, boolean nullable) {
        this.name = name;
        this.title = title;
        this.defaultValue = defaultValue;
        this.nullable = nullable;
    }

    public StorageConfigParam(String name, String title, String defaultValue, boolean nullable, String desc) {
        this.name = name;
        this.title = title;
        this.defaultValue = defaultValue;
        this.nullable = nullable;
        this.desc = desc;
    }

    public List<String> getSelectItems() {
        return this.selectItems;
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

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getWidgetType() {
        return this.widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString() {
        return "StorageConfigParam{name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", widgetType='" + this.widgetType + '\'' + ", nullable=" + this.nullable + ", selectItems=" + this.selectItems + ", desc='" + this.desc + '\'' + '}';
    }
}

