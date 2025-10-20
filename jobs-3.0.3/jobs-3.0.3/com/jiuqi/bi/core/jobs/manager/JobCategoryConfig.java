/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.manager;

import java.util.ArrayList;
import java.util.List;

public class JobCategoryConfig {
    private String applicationName;
    private ConfigType configType;
    private final List<String> categoryList = new ArrayList<String>();

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<String> getCategoryList() {
        return this.categoryList;
    }

    public ConfigType getConfigType() {
        return this.configType;
    }

    public void setConfigType(ConfigType configType) {
        this.configType = configType;
    }

    public static enum ConfigType {
        BLACKLIST,
        WHITELIST;

    }
}

