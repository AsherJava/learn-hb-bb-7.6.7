/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.model.AllViewConfig;

public class FuncExecuteConfig {
    private String template;
    private AllViewConfig config;
    private String templateType;

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public AllViewConfig getConfig() {
        return this.config;
    }

    public void setConfig(AllViewConfig config) {
        this.config = config;
    }

    public String getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
}

