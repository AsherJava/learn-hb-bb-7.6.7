/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import java.util.List;

public class ReselectPeriodVO {
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private List<String> selectEntity;
    private List<MappingSchemeVO> mappingSchemes;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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

    public List<String> getSelectEntity() {
        return this.selectEntity;
    }

    public void setSelectEntity(List<String> selectEntity) {
        this.selectEntity = selectEntity;
    }

    public List<MappingSchemeVO> getMappingSchemes() {
        return this.mappingSchemes;
    }

    public void setMappingSchemes(List<MappingSchemeVO> mappingSchemes) {
        this.mappingSchemes = mappingSchemes;
    }
}

