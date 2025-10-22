/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.datareport.obj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionNumber", description="\u533a\u57df\u5e8f\u53f7\u8bbe\u7f6e")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionNumber {
    @ApiModelProperty(value="\u533a\u57df\u5e8f\u53f7\u7eb5\u5750\u6807", name="row")
    private int row;
    @ApiModelProperty(value="\u533a\u57df\u5e8f\u53f7\u6a2a\u5750\u6807", name="column")
    private int column;
    @ApiModelProperty(value="\u533a\u57df\u5e8f\u53f7\u8d77\u59cb\u503c", name="start")
    private int start;
    @ApiModelProperty(value="\u533a\u57df\u5e8f\u53f7\u589e\u52a0\u503c", name="increment")
    private int increment;

    public RegionNumber(RowNumberSetting rowNumberSetting) {
        this.row = rowNumberSetting.getPosY();
        this.column = rowNumberSetting.getPosX();
        this.start = rowNumberSetting.getStartNumber();
        this.increment = rowNumberSetting.getIncrement();
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getIncrement() {
        return this.increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }
}

