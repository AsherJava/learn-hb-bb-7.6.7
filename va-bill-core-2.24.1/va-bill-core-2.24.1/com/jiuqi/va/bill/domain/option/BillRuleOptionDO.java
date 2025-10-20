/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.option.OptionItemDO
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.domain.option;

import com.jiuqi.va.domain.option.OptionItemDO;
import javax.persistence.Table;

@Table(name="OPTION_BILLRULE")
public class BillRuleOptionDO
extends OptionItemDO {
    private static final long serialVersionUID = 1L;
    private Integer contronflag;
    private String unitcode;

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

