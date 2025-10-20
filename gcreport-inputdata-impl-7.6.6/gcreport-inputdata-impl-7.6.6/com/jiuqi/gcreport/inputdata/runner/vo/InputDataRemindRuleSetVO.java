/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.inputdata.runner.vo;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.List;

public class InputDataRemindRuleSetVO {
    private List<GcOrgCacheVO> units;
    private List<GcOrgCacheVO> oppUnits;
    private List<UnionRuleVO> rules;

    public List<GcOrgCacheVO> getUnits() {
        return this.units;
    }

    public void setUnits(List<GcOrgCacheVO> units) {
        this.units = units;
    }

    public List<GcOrgCacheVO> getOppUnits() {
        return this.oppUnits;
    }

    public void setOppUnits(List<GcOrgCacheVO> oppUnits) {
        this.oppUnits = oppUnits;
    }

    public List<UnionRuleVO> getRules() {
        return this.rules;
    }

    public void setRules(List<UnionRuleVO> rules) {
        this.rules = rules;
    }
}

