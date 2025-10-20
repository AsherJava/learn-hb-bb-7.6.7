/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.rate.impl.dto;

import com.jiuqi.common.base.util.StringUtils;
import java.util.List;

public class RateBatchParam {
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private String acctYear;
    private String acctPeriod;
    private List<String> subjectCode;

    public String getSourceCurrencyCode() {
        return this.sourceCurrencyCode;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public List<String> getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(List<String> subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getBizKey() {
        StringBuffer key = new StringBuffer();
        key.append(StringUtils.isEmpty((String)this.getSourceCurrencyCode()) ? "CNY" : this.getSourceCurrencyCode()).append("-");
        key.append(StringUtils.isEmpty((String)this.getTargetCurrencyCode()) ? "CNY" : this.getTargetCurrencyCode()).append("-");
        key.append(StringUtils.isEmpty((String)this.getAcctYear()) ? "0" : this.getAcctYear()).append("-");
        return key.toString();
    }
}

