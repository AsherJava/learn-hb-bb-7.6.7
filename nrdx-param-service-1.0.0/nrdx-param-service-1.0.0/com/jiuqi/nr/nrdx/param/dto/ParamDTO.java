/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 */
package com.jiuqi.nr.nrdx.param.dto;

import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import java.util.List;

public class ParamDTO {
    private String key;
    private List<String> paramKeys;
    private String task;
    private String formScheme;
    private NrdxParamNodeType nrdxParamNodeType;

    public ParamDTO() {
    }

    public ParamDTO(String key) {
        this.key = key;
    }

    public ParamDTO(String key, NrdxParamNodeType resourceType) {
        this.key = key;
        this.nrdxParamNodeType = resourceType;
    }

    public ParamDTO(List<String> paramKeys, NrdxParamNodeType resourceType) {
        this.paramKeys = paramKeys;
        this.nrdxParamNodeType = resourceType;
    }

    public ParamDTO(List<String> paramKeys, String formScheme, NrdxParamNodeType resourceType) {
        this.paramKeys = paramKeys;
        this.formScheme = formScheme;
        this.nrdxParamNodeType = resourceType;
    }

    public ParamDTO(String task, String formScheme) {
        this.task = task;
        this.formScheme = formScheme;
    }

    public ParamDTO(String task, String formScheme, NrdxParamNodeType resourceType) {
        this.task = task;
        this.formScheme = formScheme;
        this.nrdxParamNodeType = resourceType;
    }

    public ParamDTO(String key, String task, String formScheme, NrdxParamNodeType resourceType) {
        this.key = key;
        this.task = task;
        this.formScheme = formScheme;
        this.nrdxParamNodeType = resourceType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public List<String> getParamKeys() {
        return this.paramKeys;
    }

    public void setParamKeys(List<String> paramKeys) {
        this.paramKeys = paramKeys;
    }

    public NrdxParamNodeType getResourceType() {
        return this.nrdxParamNodeType;
    }

    public void setResourceType(NrdxParamNodeType resourceType) {
        this.nrdxParamNodeType = resourceType;
    }
}

