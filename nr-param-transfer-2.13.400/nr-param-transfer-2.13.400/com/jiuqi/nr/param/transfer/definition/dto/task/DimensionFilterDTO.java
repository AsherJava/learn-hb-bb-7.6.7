/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.definition.common.DimensionFilterListType
 *  com.jiuqi.np.definition.common.DimensionFilterType
 *  com.jiuqi.nr.definition.facade.DesignDimensionFilter
 *  com.jiuqi.nr.definition.internal.impl.DesignDimensionFilterImpl
 */
package com.jiuqi.nr.param.transfer.definition.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.definition.common.DimensionFilterListType;
import com.jiuqi.np.definition.common.DimensionFilterType;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.internal.impl.DesignDimensionFilterImpl;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DimensionFilterDTO
extends BaseDTO {
    private String key;
    private String entityId;
    private String taskKey;
    private DimensionFilterListType listType;
    private DimensionFilterType type;
    private String value;
    @JsonIgnore
    private List<String> list;

    public void setKey(String key) {
        this.key = key;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setType(DimensionFilterType type) {
        this.type = type;
    }

    public void setListType(DimensionFilterListType listType) {
        this.listType = listType;
    }

    @JsonIgnore
    public void setList(List<String> list) {
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return null;
    }

    public DimensionFilterType getType() {
        return this.type;
    }

    public DimensionFilterListType getListType() {
        return this.listType;
    }

    @JsonIgnore
    public List<String> getList() {
        return null;
    }

    public String getValue() {
        return this.value;
    }

    public static List<DimensionFilterDTO> convertDTO(List<DesignDimensionFilter> filters) {
        ArrayList<DimensionFilterDTO> list1 = new ArrayList<DimensionFilterDTO>();
        for (DesignDimensionFilter filter : filters) {
            DimensionFilterDTO dimensionFilterDTO = new DimensionFilterDTO();
            BeanUtils.copyProperties(filter, dimensionFilterDTO);
            list1.add(dimensionFilterDTO);
        }
        return list1;
    }

    public static List<DesignDimensionFilter> convertDefinition(List<DimensionFilterDTO> filterDTOList) {
        ArrayList<DesignDimensionFilter> list1 = new ArrayList<DesignDimensionFilter>();
        for (DimensionFilterDTO filter : filterDTOList) {
            DesignDimensionFilterImpl dimensionFilter = new DesignDimensionFilterImpl();
            BeanUtils.copyProperties(filter, dimensionFilter);
            list1.add((DesignDimensionFilter)dimensionFilter);
        }
        return list1;
    }
}

