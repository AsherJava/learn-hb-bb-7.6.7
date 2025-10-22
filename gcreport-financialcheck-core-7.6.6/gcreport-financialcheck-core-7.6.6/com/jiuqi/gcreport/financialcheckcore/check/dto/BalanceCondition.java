/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.financialcheckcore.check.dto;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Set;

public class BalanceCondition
extends GcTaskBaseArguments {
    private boolean checked;
    private Set<String> boundSubjects;
    private String checkWay;
    private String systemId;

    public String getMergeOrg() {
        return this.getOrgId();
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Set<String> getBoundSubjects() {
        return this.boundSubjects;
    }

    public void setBoundSubjects(Set<String> boundSubjects) {
        this.boundSubjects = boundSubjects;
    }

    public String getPeriodStr() {
        String periodStr = super.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr)) {
            periodStr = PeriodWrapper.makePeriodString((int)this.getAcctYear(), (int)this.getPeriodType(), (int)this.getAcctPeriod());
            this.setPeriodStr(periodStr);
        }
        return periodStr;
    }

    public String toString() {
        return "BalanceCondition{mergeOrg='" + this.getOrgId() + '\'' + ", acctYear=" + this.getAcctYear() + ", acctPeriod=" + this.getAcctPeriod() + ", currency='" + this.getCurrency() + '\'' + ", checked=" + this.checked + '}';
    }

    public String getCheckWay() {
        return this.checkWay;
    }

    public void setCheckWay(String checkWay) {
        this.checkWay = checkWay;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}

