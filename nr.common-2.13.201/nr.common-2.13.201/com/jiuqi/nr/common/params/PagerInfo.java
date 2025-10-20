/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.common.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionQueryInfo", description="\u5206\u9875\u53c2\u6570")
public class PagerInfo {
    @ApiModelProperty(value="\u9875\u6570,\u4ece0\u5f00\u59cb", name="offset")
    private int offset;
    @ApiModelProperty(value="\u6bcf\u9875\u6570\u91cf", name="limit")
    private int limit;
    @ApiModelProperty(value="\u603b\u6570", name="total")
    private int total;

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

