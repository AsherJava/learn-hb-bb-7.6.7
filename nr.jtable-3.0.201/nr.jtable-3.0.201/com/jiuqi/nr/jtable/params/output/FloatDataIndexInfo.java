/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="FloatDataIndexInfo", description="\u6d6e\u52a8\u6570\u636e\u5b9a\u4f4d\u7ed3\u679c")
public class FloatDataIndexInfo {
    @ApiModelProperty(value="\u5b9a\u4f4d\u7ed3\u679c\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u5b9a\u4f4d\u7d22\u5f15", name="index")
    private int index;
    @ApiModelProperty(value="\u6570\u636e\u603b\u6570", name="total")
    private int total;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u67e5\u8be2\u7ed3\u679c", name="regionDataSet")
    private RegionDataSet regionDataSet;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public RegionDataSet getRegionDataSet() {
        return this.regionDataSet;
    }

    public void setRegionDataSet(RegionDataSet regionDataSet) {
        this.regionDataSet = regionDataSet;
    }
}

