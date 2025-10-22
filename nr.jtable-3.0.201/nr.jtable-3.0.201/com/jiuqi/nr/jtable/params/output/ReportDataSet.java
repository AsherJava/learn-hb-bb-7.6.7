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
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="ReportDataSet", description="\u62a5\u8868\u6570\u636e\u67e5\u8be2\u7ed3\u679c")
public class ReportDataSet {
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u67e5\u8be2\u7ed3\u679c\u96c6", name="queryData")
    private Map<String, RegionDataSet> queryData = new HashMap<String, RegionDataSet>();

    public Map<String, RegionDataSet> getQueryData() {
        return this.queryData;
    }

    public void setQueryData(Map<String, RegionDataSet> queryData) {
        this.queryData = queryData;
    }
}

