/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.fmdm.internal.check;

import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="FMDMCheckResult", description="\u5c01\u9762\u6570\u636e\u68c0\u67e5\u7ed3\u679c\u96c6")
public class FMDMCheckResult {
    @ApiModelProperty(value="\u68c0\u67e5\u7ed3\u679c\u5217\u8868", name="results")
    private List<FMDMCheckFailNodeInfo> results = new ArrayList<FMDMCheckFailNodeInfo>();

    public List<FMDMCheckFailNodeInfo> getResults() {
        return this.results;
    }

    public void setResults(List<FMDMCheckFailNodeInfo> results) {
        this.results = results;
    }

    public FMDMCheckResult addResult(List<FMDMCheckFailNodeInfo> results) {
        if (this.results == null) {
            this.results = new ArrayList<FMDMCheckFailNodeInfo>();
        }
        this.results.addAll(results);
        return this;
    }

    public FMDMCheckResult addResult(FMDMCheckFailNodeInfo resultInfo) {
        if (this.results == null) {
            this.results = new ArrayList<FMDMCheckFailNodeInfo>();
        }
        this.results.add(resultInfo);
        return this;
    }
}

