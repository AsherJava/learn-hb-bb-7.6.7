/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean.impl;

import com.jiuqi.nr.dataentry.bean.FTemplateConfig;
import java.util.Date;

public class TemplateConfigImpl
implements FTemplateConfig {
    private String templateId;
    private String code;
    private String title;
    private String template;
    private String templateConfig;
    private Date updateTime;

    @Override
    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String getTemplateConfig() {
        return this.templateConfig;
    }

    public void setTemplateConfig(String templateConfig) {
        this.templateConfig = templateConfig;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

