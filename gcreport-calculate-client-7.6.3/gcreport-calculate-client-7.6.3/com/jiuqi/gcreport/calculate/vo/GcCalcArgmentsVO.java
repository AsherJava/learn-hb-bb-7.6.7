/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 */
package com.jiuqi.gcreport.calculate.vo;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import java.util.List;

public class GcCalcArgmentsVO
extends GcTaskBaseArguments {
    private String sn;
    private List<String> ruleIds;
    private String orgTypeId;
    private String adjtypeId;

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOrgTypeId() {
        return this.orgTypeId;
    }

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getAdjtypeId() {
        return this.adjtypeId;
    }

    public void setAdjtypeId(String adjtypeId) {
        this.adjtypeId = adjtypeId;
    }
}

