/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.multcheck2.provider.PluginInfo;

public class MultcheckItemVO {
    private String key;
    private String scheme;
    private String title;
    private String type;
    private String config;
    private String order;
    private String typeTitle;
    private boolean needChangeConfig;
    private String description;
    private PluginInfo pluginInfo;
    private boolean supportDimension;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTypeTitle() {
        return this.typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public boolean isNeedChangeConfig() {
        return this.needChangeConfig;
    }

    public void setNeedChangeConfig(boolean needChangeConfig) {
        this.needChangeConfig = needChangeConfig;
    }

    public PluginInfo getPluginInfo() {
        return this.pluginInfo;
    }

    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public boolean isSupportDimension() {
        return this.supportDimension;
    }

    public void setSupportDimension(boolean supportDimension) {
        this.supportDimension = supportDimension;
    }
}

