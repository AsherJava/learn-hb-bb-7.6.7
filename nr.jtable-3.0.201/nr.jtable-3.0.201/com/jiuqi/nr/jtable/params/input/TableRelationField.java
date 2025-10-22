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

@ApiModel(value="TableRelationField", description="\u7236\u5b50\u8868\u5173\u8054\u5b57\u6bb5\u4fe1\u606f")
public class TableRelationField {
    @ApiModelProperty(value="\u7236\u8868\u540d", name="parentTableName")
    private String parentTableName;
    @ApiModelProperty(value="\u5b50\u8868\u540d", name="childTableName")
    private String childTableName;
    @ApiModelProperty(value="\u5173\u8054\u5b57\u6bb5\u540d", name="relationFieldName")
    private String relationFieldName;

    public String getParentTableName() {
        return this.parentTableName;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }

    public String getChildTableName() {
        return this.childTableName;
    }

    public void setChildTableName(String childTableName) {
        this.childTableName = childTableName;
    }

    public String getRelationFieldName() {
        return this.relationFieldName;
    }

    public void setRelationFieldName(String relationFieldName) {
        this.relationFieldName = relationFieldName;
    }
}

