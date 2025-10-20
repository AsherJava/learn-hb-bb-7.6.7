/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.navigation.vo;

import javax.validation.constraints.NotBlank;

public class NavigationVO {
    private String id;
    private String recver;
    @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code;
    private String configValue;
    private String backImg;
    @NotBlank(message="\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a") String title;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getBackImg() {
        return this.backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecver() {
        return this.recver;
    }

    public void setRecver(String recver) {
        this.recver = recver;
    }
}

