/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionDataCount", description="\u533a\u57df\u6570\u636e\u603b\u884c\u6570")
public class RegionDataCount {
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u7c7b\u578b", name="dataType")
    private int dataType;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u603b\u884c\u6570", name="totalCount")
    private int totalCount;

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

