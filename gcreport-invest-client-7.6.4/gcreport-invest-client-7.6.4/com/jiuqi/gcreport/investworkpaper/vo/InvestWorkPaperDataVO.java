/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class InvestWorkPaperDataVO {
    private String investBillId;
    private String ruleId;
    private String ruleTitle;
    private String investUnitId;
    private String investUnitTitle;
    private String investedUnitId;
    private String investedUnitTitle;
    private String compreEquityRatio;
    private String fewShareHolder;
    private Map<String, String> otherColumnsValue = new HashMap<String, String>();
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public String getInvestBillId() {
        return this.investBillId;
    }

    public void setInvestBillId(String investBillId) {
        this.investBillId = investBillId;
        this.otherColumnsValue.put("investBillId", investBillId);
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
        this.otherColumnsValue.put("ruleId", ruleId);
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
        this.otherColumnsValue.put("OFFSETSCENARIO", ruleTitle);
    }

    public String getInvestUnitTitle() {
        return this.investUnitTitle;
    }

    public void setInvestUnitTitle(String investUnitTitle) {
        this.investUnitTitle = investUnitTitle;
        this.otherColumnsValue.put("UNITCODE", investUnitTitle);
    }

    public String getInvestedUnitTitle() {
        return this.investedUnitTitle;
    }

    public void setInvestedUnitTitle(String investedUnitTitle) {
        this.investedUnitTitle = investedUnitTitle;
        this.otherColumnsValue.put("INVESTEDUNIT", investedUnitTitle);
    }

    public Map<String, String> getOtherColumnsValue() {
        return this.otherColumnsValue;
    }

    public void setOtherColumnsValue(Map<String, String> otherColumnsValue) {
        this.otherColumnsValue = otherColumnsValue;
    }

    public String getCompreEquityRatio() {
        return this.compreEquityRatio;
    }

    public void setCompreEquityRatio(String compreEquityRatio) {
        this.compreEquityRatio = compreEquityRatio;
        this.otherColumnsValue.put("COMPREEQUITYRATIO", this.df.format(new BigDecimal(compreEquityRatio)));
    }

    public String getFewShareHolder() {
        return this.fewShareHolder;
    }

    public void setFewShareHolder(String fewShareHolder) {
        this.fewShareHolder = fewShareHolder;
        this.otherColumnsValue.put("FEWSHAREHOLDER", this.df.format(new BigDecimal(fewShareHolder)));
    }

    public String getInvestUnitId() {
        return this.investUnitId;
    }

    public void setInvestUnitId(String investUnitId) {
        this.investUnitId = investUnitId;
        this.otherColumnsValue.put("investUnitId", investUnitId);
    }

    public String getInvestedUnitId() {
        return this.investedUnitId;
    }

    public void setInvestedUnitId(String investedUnitId) {
        this.investedUnitId = investedUnitId;
        this.otherColumnsValue.put("investedUnitId", investedUnitId);
    }
}

