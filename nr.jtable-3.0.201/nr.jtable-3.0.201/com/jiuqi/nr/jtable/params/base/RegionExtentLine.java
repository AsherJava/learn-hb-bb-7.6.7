/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.definition.util.LineProp
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.util.LineProp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionExtentLine", description="\u6d6e\u52a8\u884c\u679a\u4e3e\u586b\u5145\u884c\u6570\u636e")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionExtentLine {
    @ApiModelProperty(value="\u679a\u4e3e\u9879key", name="ekey")
    private String ekey;
    @ApiModelProperty(value="\u6d6e\u52a8\u533a\u57df\u586b\u5145\u884c\u5e8f\u53f7", name="row")
    private int row;
    @ApiModelProperty(value="\u662f\u5426\u5141\u8bb8\u4fee\u6539", name="write")
    private boolean write;
    @ApiModelProperty(value="\u662f\u5426\u5141\u8bb8\u65b0\u589e(\u5f53\u524d\u884c\u7684\u4e0b\u4e00\u884c)", name="add")
    private boolean add;

    public RegionExtentLine(LineProp line) {
        this.ekey = line.getDataBaseKey();
        this.row = line.getRowNumber();
        this.write = line.isDropDown();
        this.add = line.isInsertRow();
    }

    public String getEkey() {
        return this.ekey;
    }

    public void setEkey(String ekey) {
        this.ekey = ekey;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isWrite() {
        return this.write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isAdd() {
        return this.add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }
}

