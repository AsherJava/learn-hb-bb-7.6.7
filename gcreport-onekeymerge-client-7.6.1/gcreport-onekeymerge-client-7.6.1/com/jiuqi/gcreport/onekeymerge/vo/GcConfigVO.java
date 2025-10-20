/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import java.util.List;
import javax.validation.constraints.NotBlank;

public class GcConfigVO {
    private String id;
    @NotBlank(message="\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotBlank(message="\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") String configName;
    private String mergeType;
    private List<String> ruleIds;
    private List<String> orgIds;
    private String configNodes;
    private double ordinal;
    private String finishCalConfig;

    public String getFinishCalConfig() {
        return this.finishCalConfig;
    }

    public void setFinishCalConfig(String finishCalConfig) {
        this.finishCalConfig = finishCalConfig;
    }

    public String getConfigName() {
        return this.configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigNodes() {
        return this.configNodes;
    }

    public void setConfigNodes(String configNodes) {
        this.configNodes = configNodes;
    }

    public double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(double ordinal) {
        this.ordinal = ordinal;
    }

    public String getMergeType() {
        return this.mergeType;
    }

    public void setMergeType(String mergeType) {
        this.mergeType = mergeType;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }
}

