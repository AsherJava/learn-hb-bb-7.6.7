/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.vo;

import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import java.util.List;

public class GenerateParamVO {
    private String taskKey;
    private String period;
    private String schemeKey;
    private boolean allEntity;
    private List<String> entityKeys;
    private String mapping;
    private int unitPage;
    private String taskTitle;
    private String mappingTitle;

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

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public boolean isAllEntity() {
        return this.allEntity;
    }

    public void setAllEntity(boolean allEntity) {
        this.allEntity = allEntity;
    }

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public int getUnitPage() {
        return this.unitPage;
    }

    public void setUnitPage(int unitPage) {
        this.unitPage = unitPage;
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

    public static GenerateParamVO getInstance(GenerateParamDTO generateParamDTO) {
        GenerateParamVO vo = new GenerateParamVO();
        vo.setTaskKey(generateParamDTO.getTaskKey());
        vo.setPeriod(generateParamDTO.getPeriod());
        vo.setEntityKeys(generateParamDTO.getEntityKeys());
        vo.setMapping(generateParamDTO.getMapping());
        vo.setUnitPage(generateParamDTO.getUnitPage());
        vo.setAllEntity(generateParamDTO.isAllEntity());
        return vo;
    }
}

