/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

public class SearchFieldItem {
    private String key;
    private String code;
    private String title;
    private String dataLinkKey;
    private String fieldKey;
    private String fieldCode;
    private String fieldName;
    private String fieldTitle;
    private String regionKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public SearchFieldItem(String key, String code, String title, String dataLinkKey, String fieldKey, String fieldCode, String fieldName, String fieldTitle, String regionKey) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.dataLinkKey = dataLinkKey;
        this.fieldKey = fieldKey;
        this.fieldCode = fieldCode;
        this.fieldName = fieldName;
        this.fieldTitle = fieldTitle;
        this.regionKey = regionKey;
    }
}

