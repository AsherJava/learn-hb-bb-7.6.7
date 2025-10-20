/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bill.domain.option;

import com.jiuqi.va.domain.option.OptionItemVO;

public class BillRuleOptionVO
extends OptionItemVO {
    private static final long serialVersionUID = 1L;
    private Integer contronflag;
    private String unitcode;
    private String storageValue;

    public String getStorageValue() {
        return this.storageValue;
    }

    public void setStorageValue(String storageValue) {
        this.storageValue = storageValue;
    }

    public Integer getContronflag() {
        return this.contronflag;
    }

    public void setContronflag(Integer contronflag) {
        this.contronflag = contronflag;
    }

    public String getUnitcode() {
        return this.unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }
}

