/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping2.util.NvMappingInsertRule
 */
package com.jiuqi.nr.param.transfer.nvwaMapping;

import com.jiuqi.nr.mapping2.util.NvMappingInsertRule;

public class NvdataMappingMatchContext {
    String mappingSchemeKey;
    String taskKey;
    String taskTitle;
    String formSchemeKey;
    String formSchemeTitle;
    String dataSchemeKey;
    NvMappingInsertRule nvMappingInsertRule;

    public NvdataMappingMatchContext() {
    }

    public NvdataMappingMatchContext(String mappingSchemeKey, String taskKey, String taskTitle, String formSchemeKey, String formSchemeTitle, String dataSchemeKey, NvMappingInsertRule nvMappingInsertRule) {
        this.mappingSchemeKey = mappingSchemeKey;
        this.taskKey = taskKey;
        this.taskTitle = taskTitle;
        this.formSchemeKey = formSchemeKey;
        this.dataSchemeKey = dataSchemeKey;
        this.formSchemeTitle = formSchemeTitle;
        this.nvMappingInsertRule = nvMappingInsertRule;
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public NvMappingInsertRule getNvMappingInsertRule() {
        return this.nvMappingInsertRule;
    }

    public void setNvMappingInsertRule(NvMappingInsertRule nvMappingInsertRule) {
        this.nvMappingInsertRule = nvMappingInsertRule;
    }
}

