/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.BizModelExtFieldInfo
 */
package com.jiuqi.bde.bizmodel.client.dto;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.Dimension;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinBizModelDTO
extends BizModelDTO {
    private List<String> fetchTypes = new ArrayList<String>();
    private String fetchTypeNames;
    private List<Dimension> dimensions = new ArrayList<Dimension>();
    private String dimensionNames;
    private Map<String, String> dimensionMap;
    private Integer selectAll;
    private BizModelExtFieldInfo bizModelExtFieldInfo;

    public List<String> getFetchTypes() {
        return this.fetchTypes;
    }

    public void setFetchTypes(List<String> fetchTypes) {
        this.fetchTypes = fetchTypes;
    }

    public List<Dimension> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Map<String, String> getDimensionMap() {
        return this.dimensionMap;
    }

    public void setDimensionMap(Map<String, String> dimensionMap) {
        this.dimensionMap = dimensionMap;
    }

    public String getDimensionNames() {
        return this.dimensionNames;
    }

    public void setDimensionNames(String dimensionNames) {
        this.dimensionNames = dimensionNames;
    }

    public String getFetchTypeNames() {
        return this.fetchTypeNames;
    }

    public void setFetchTypeNames(String fetchTypeNames) {
        this.fetchTypeNames = fetchTypeNames;
    }

    public Integer getSelectAll() {
        return this.selectAll;
    }

    public void setSelectAll(Integer selectAll) {
        this.selectAll = selectAll;
    }

    public BizModelExtFieldInfo getBizModelExtFieldInfo() {
        return this.bizModelExtFieldInfo;
    }

    public void setBizModelExtFieldInfo(BizModelExtFieldInfo bizModelExtFieldInfo) {
        this.bizModelExtFieldInfo = bizModelExtFieldInfo;
    }
}

