/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.summary;

import com.jiuqi.nr.common.summary.SummaryConfig;

public class SummaryScheme {
    private String key;
    private String title;
    private String viewKey;
    private String formSchemeKey;
    private String period;
    private String order;
    private SummaryConfig config;

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

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public SummaryConfig getConfig() {
        return this.config;
    }

    public void setConfig(SummaryConfig config) {
        this.config = config;
    }
}

