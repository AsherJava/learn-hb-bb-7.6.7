/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.internal.impl;

import com.jiuqi.nr.configuration.common.ConfigContentType;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;

public class BusinessConfigurationDefineImpl
implements BusinessConfigurationDefine {
    private String key;
    private String taskKey;
    private String formSchemeKey;
    private String code;
    private String title;
    private String description;
    private String category;
    private ConfigContentType contentType;
    private String content;
    private String fileName;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public ConfigContentType getContentType() {
        return this.contentType;
    }

    @Override
    public void setContentType(ConfigContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getConfigurationContent() {
        return this.content;
    }

    @Override
    public void setConfigurationContent(String content) {
        this.content = content;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BusinessConfigurationDefineImpl() {
    }

    public BusinessConfigurationDefineImpl(String code, ConfigContentType contentType, String content) {
        this.code = code;
        this.contentType = contentType;
        this.content = content;
    }

    public BusinessConfigurationDefineImpl(String taskKey, String formSchemeKey, String code, ConfigContentType contentType, String content) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.code = code;
        this.contentType = contentType;
        this.content = content;
    }
}

