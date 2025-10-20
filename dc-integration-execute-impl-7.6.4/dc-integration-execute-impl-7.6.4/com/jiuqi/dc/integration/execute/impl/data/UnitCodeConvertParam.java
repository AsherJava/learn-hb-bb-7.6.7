/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UnitCodeConvertParam
implements Serializable {
    private static final long serialVersionUID = 439995828852205880L;
    private List<String> unitCodes;
    private String unitType;
    private Date createDate;
    private int acctYear;

    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }
}

