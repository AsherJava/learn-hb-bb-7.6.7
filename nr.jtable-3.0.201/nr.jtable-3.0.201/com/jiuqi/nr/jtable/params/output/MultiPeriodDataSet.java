/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.MultiPeriodRegionDataSet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="MultiPeriodDataSet", description="\u591a\u65f6\u671f\u6570\u636e\u67e5\u8be2\u7ed3\u679c")
public class MultiPeriodDataSet {
    @ApiModelProperty(value="\u6309\u533a\u57df\u5212\u5206\u591a\u65f6\u671f\u6570\u636e", name="regionMap")
    private Map<String, MultiPeriodRegionDataSet> regionMap = new HashMap<String, MultiPeriodRegionDataSet>();
    @ApiModelProperty(value="\u65e0\u4e0a\u4e00\u671f\u65f6\u671f", name="noPrevPeriod")
    private boolean noPrevPeriod;
    @ApiModelProperty(value="\u65e0\u4e0a\u4e00\u5e74\u65f6\u671f", name="noPrevYear")
    private boolean noPrevYear;

    public Map<String, MultiPeriodRegionDataSet> getRegionMap() {
        return this.regionMap;
    }

    public void setRegionMap(Map<String, MultiPeriodRegionDataSet> regionMap) {
        this.regionMap = regionMap;
    }

    public boolean isNoPrevPeriod() {
        return this.noPrevPeriod;
    }

    public void setNoPrevPeriod(boolean noPrevPeriod) {
        this.noPrevPeriod = noPrevPeriod;
    }

    public boolean isNoPrevYear() {
        return this.noPrevYear;
    }

    public void setNoPrevYear(boolean noPrevYear) {
        this.noPrevYear = noPrevYear;
    }
}

