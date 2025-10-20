/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.SelectOptionVO;
import java.util.List;

public class ColumnDefineVO {
    private String code;
    private String name;
    private Integer width;
    private boolean required;
    private String dataRange;
    private String type;
    private String defaultValue;
    private List<SelectOptionVO> data;
    private String baseDataTableName;

    public ColumnDefineVO(String code, String name, boolean required, String dataRange, String type, String defaultValue, List<SelectOptionVO> data) {
        this.code = code;
        this.name = name;
        this.required = required;
        this.dataRange = dataRange;
        this.type = type;
        this.defaultValue = defaultValue;
        this.data = data;
    }

    public ColumnDefineVO() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDataRange() {
        return this.dataRange;
    }

    public void setDataRange(String dataRange) {
        this.dataRange = dataRange;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<SelectOptionVO> getData() {
        return this.data;
    }

    public void setData(List<SelectOptionVO> data) {
        this.data = data;
    }

    public String getBaseDataTableName() {
        return this.baseDataTableName;
    }

    public void setBaseDataTableName(String baseDataTableName) {
        this.baseDataTableName = baseDataTableName;
    }
}

