/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;

public class GcConversionVO
extends GcBaseTaskStateVO {
    private String orgName;
    private String currencyName;
    private String tarCurrencyName;
    private String useTime;

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getTarCurrencyName() {
        return this.tarCurrencyName;
    }

    public void setTarCurrencyName(String tarCurrencyName) {
        this.tarCurrencyName = tarCurrencyName;
    }

    public String getUseTime() {
        return this.useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
}

