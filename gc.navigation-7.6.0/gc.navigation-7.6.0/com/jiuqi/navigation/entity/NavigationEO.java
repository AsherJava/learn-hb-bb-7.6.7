/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.navigation.entity;

public class NavigationEO {
    public static final String TABLENAME = "GC_NAVIGATIONCONFIG";
    private String id;
    private String recver;
    private String code;
    private String configValue;
    private String backImg;
    private String title;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfigValue() {
        return this.configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackImg() {
        return this.backImg;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecver() {
        return this.recver;
    }

    public void setRecver(String recver) {
        this.recver = recver;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }
}

