/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.vo;

import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;

public class ImportParamVO {
    private String key;
    private String taskKey;
    private String period;
    private String mapping;
    private String taskTitle;
    private String mappingTitle;
    private String schemeKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getMappingTitle() {
        return this.mappingTitle;
    }

    public void setMappingTitle(String mappingTitle) {
        this.mappingTitle = mappingTitle;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public static ImportParamVO getInstance(ImportParamDTO importParamDTO) {
        ImportParamVO vo = new ImportParamVO();
        vo.setKey(importParamDTO.getHex());
        vo.setTaskKey(importParamDTO.getTaskKey());
        vo.setPeriod(importParamDTO.getPeriod());
        vo.setMapping(importParamDTO.getMapping());
        return vo;
    }
}

