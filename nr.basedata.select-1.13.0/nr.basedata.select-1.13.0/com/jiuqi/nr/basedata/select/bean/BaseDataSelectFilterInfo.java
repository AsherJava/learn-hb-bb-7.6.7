/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="BaseDataSelectFilterInfo", description="\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u5668\u4fe1\u606f")
public class BaseDataSelectFilterInfo {
    @ApiModelProperty(value="\u8fc7\u6ee4\u5668\u7684\u540d\u79f0", name="filterName")
    private String filterName;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5668\u53c2\u6570", name="filterParams")
    private Object filterParams;

    public String getFilterName() {
        return this.filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Object getFilterParams() {
        return this.filterParams;
    }

    public void setFilterParams(Object filterParams) {
        this.filterParams = filterParams;
    }
}

