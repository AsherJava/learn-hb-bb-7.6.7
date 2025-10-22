/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.batch.gather.gzw.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="\u6279\u91cf\u6c47\u603b\u5bfc\u51fa,\u524d\u7aef\u6240\u4f20\u53c2\u6570")
public class BatchGatherExportParam {
    @ApiModelProperty(value="\u6240\u5c5e\u6c47\u603b\u65b9\u6848key")
    @JsonProperty(value="schemeKey")
    private String schemeKey;
    @JsonProperty(value="scheme")
    @ApiModelProperty(value="\u6c47\u603b\u65b9\u6848\u4fe1\u606f")
    private SummaryScheme scheme;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public SummaryScheme getScheme() {
        return this.scheme;
    }

    public void setScheme(SummaryScheme scheme) {
        this.scheme = scheme;
    }

    public BatchGatherExportParam() {
    }

    public BatchGatherExportParam(String schemeKey, SummaryScheme scheme) {
        this.schemeKey = schemeKey;
        this.scheme = scheme;
    }
}

