/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.item.dto;

import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.Date;

public class GcFcRuleUnOffsetDataDTO
extends GcRelatedItemEO {
    private String systemId;
    private String dataTime;
    private String offsetCurrency;
    private String unionRuleId;
    private String offsetState;
    private String offsetPerson;
    private Date offsetTime;
    private Integer dc;
    private Double amt;
    private String offsetGroupId;
    private Double conversionRate;

    public String getOffsetCurrency() {
        return this.offsetCurrency;
    }

    public void setOffsetCurrency(String offsetCurrency) {
        this.offsetCurrency = offsetCurrency;
    }

    @Override
    public Integer getDc() {
        return this.dc;
    }

    @Override
    public void setDc(Integer dc) {
        this.addFieldValue("DC", dc);
        this.dc = dc;
    }

    public String getOffsetState() {
        return this.offsetState;
    }

    public void setOffsetState(String offsetState) {
        this.offsetState = offsetState;
    }

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Date getOffsetTime() {
        return this.offsetTime;
    }

    public void setOffsetTime(Date offsetTime) {
        this.offsetTime = offsetTime;
    }

    public String getOffsetPerson() {
        return this.offsetPerson;
    }

    public void setOffsetPerson(String offsetPerson) {
        this.offsetPerson = offsetPerson;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    @Override
    public Double getAmt() {
        return this.amt;
    }

    @Override
    public void setAmt(Double amt) {
        this.addFieldValue("AMT", amt);
        this.amt = amt;
    }

    public Double getConversionRate() {
        return this.conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}

