/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class QueryConfigInfo {
    List<FloatZbMappingVO> zbMapping;
    List<FloatQueryFieldVO> queryFields;
    List<String> usedFields;
    String pluginData;

    public String getPluginData() {
        return this.pluginData;
    }

    public void setPluginData(String pluginData) {
        this.pluginData = pluginData;
    }

    public List<FloatZbMappingVO> getZbMapping() {
        return this.zbMapping;
    }

    public void setZbMapping(List<FloatZbMappingVO> zbMapping) {
        this.zbMapping = zbMapping;
    }

    public List<FloatQueryFieldVO> getQueryFields() {
        return this.queryFields;
    }

    public void setQueryFields(List<FloatQueryFieldVO> queryFields) {
        this.queryFields = queryFields;
    }

    public List<String> getUsedFields() {
        return this.usedFields;
    }

    public void setUsedFields(List<String> usedFields) {
        this.usedFields = usedFields;
    }
}

