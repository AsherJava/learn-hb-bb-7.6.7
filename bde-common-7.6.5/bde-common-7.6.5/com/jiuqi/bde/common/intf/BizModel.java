/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.intf;

import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import java.util.List;

public class BizModel {
    private String bizModelCode;
    private String name;
    private List<SelectOptionVO> fetchTypes;
    private List<SelectOptionVO> dimensions;
    private List<ColumnDefineVO> fixedFields;
    private List<SelectOptionVO> optionItems;
    private Integer sortOrder;

    public String getBizModelCode() {
        return this.bizModelCode;
    }

    public void setBizModelCode(String bizModelCode) {
        this.bizModelCode = bizModelCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectOptionVO> getFetchTypes() {
        return this.fetchTypes;
    }

    public void setFetchTypes(List<SelectOptionVO> fetchTypes) {
        this.fetchTypes = fetchTypes;
    }

    public List<SelectOptionVO> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<SelectOptionVO> dimensions) {
        this.dimensions = dimensions;
    }

    public List<ColumnDefineVO> getFixedFields() {
        return this.fixedFields;
    }

    public void setFixedFields(List<ColumnDefineVO> fixedFields) {
        this.fixedFields = fixedFields;
    }

    public List<SelectOptionVO> getOptionItems() {
        return this.optionItems;
    }

    public void setOptionItems(List<SelectOptionVO> optionItems) {
        this.optionItems = optionItems;
    }

    public int getSortOrder() {
        return this.sortOrder == null ? 0 : this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}

