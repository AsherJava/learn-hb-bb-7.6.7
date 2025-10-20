/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.formulaschemeconfig.vo;

import com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.io.Serializable;

public class BillFormulaSchemeConfigTableVO
extends FormulaSchemeConfigTableVO
implements Serializable {
    private String billId;

    public BillFormulaSchemeConfigTableVO(OrgDO org) {
        this.setOrgId(org.getCode());
        this.setOrgUnit(org);
        this.setOrgTitle(org.getName());
        this.setFetchSchemeId(new String());
        this.setFetchScheme(new String());
    }

    public BillFormulaSchemeConfigTableVO() {
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}

