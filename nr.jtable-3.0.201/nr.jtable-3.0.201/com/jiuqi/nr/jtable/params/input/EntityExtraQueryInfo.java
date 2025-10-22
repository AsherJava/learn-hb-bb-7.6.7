/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="EntityExtraQueryInfo", description="\u4e3b\u4f53\u6570\u636e\u6269\u5c55\u67e5\u8be2\u53c2\u6570")
public class EntityExtraQueryInfo {
    @ApiModelProperty(value="\u8868\u540d", name="tableName")
    private String tableName;
    @ApiModelProperty(value="\u6570\u636ekey", name="dataKey")
    private String dataKey;
    @ApiModelProperty(value="\u7236\u8868\u540d", name="parentTableName")
    private String parentTableName;
    @ApiModelProperty(value="\u7236key", name="parentKey")
    private String parentKey;
    @ApiModelProperty(value="\u5173\u8054\u6307\u6807", name="relField")
    private String relField;
    @ApiModelProperty(value="\u8fc7\u6ee4\u516c\u5f0f", name="filterFormula")
    private String filterFormula;
    @ApiModelProperty(value="\u67e5\u8be2\u7ed3\u679c\u6307\u6807\u5217\u8868", name="fields")
    private List<String> fieldNames = new ArrayList<String>();

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataKey() {
        return this.dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getParentTableName() {
        return this.parentTableName;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getRelField() {
        return this.relField;
    }

    public void setRelField(String relField) {
        this.relField = relField;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public List<String> getFieldNames() {
        return this.fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }
}

