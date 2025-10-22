/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

public class MultiPeriodRegionDataSet {
    @ApiModelProperty(value="\u533a\u57df\u4e0b\u94fe\u63a5key\u5217\u8868", name="cells")
    private List<String> cells = new ArrayList<String>();
    @ApiModelProperty(value="\u4e0a\u4e00\u671f\u533a\u57df\u6570\u636e\u7ed3\u679c\u96c6(\u6570\u636e\u7d22\u5f15\u4e0ecells\u7d22\u5f15\u4e00\u81f4)", name="prevPeriodData")
    private List<Object> prevPeriodData = new ArrayList<Object>();
    @ApiModelProperty(value="\u4e0a\u4e00\u5e74\u533a\u57df\u6570\u636e\u7ed3\u679c\u96c6(\u6570\u636e\u7d22\u5f15\u4e0ecells\u7d22\u5f15\u4e00\u81f4)", name="prevYearData")
    private List<Object> prevYearData = new ArrayList<Object>();

    public List<String> getCells() {
        return this.cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }

    public List<Object> getPrevPeriodData() {
        return this.prevPeriodData;
    }

    public void setPrevPeriodData(List<Object> prevPeriodData) {
        this.prevPeriodData = prevPeriodData;
    }

    public List<Object> getPrevYearData() {
        return this.prevYearData;
    }

    public void setPrevYearData(List<Object> prevYearData) {
        this.prevYearData = prevYearData;
    }
}

