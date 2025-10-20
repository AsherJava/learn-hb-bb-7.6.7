/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.data;

import java.io.Serializable;

public class VchrChangeDim
implements Serializable {
    private static final long serialVersionUID = -3525561561063797579L;
    private String dataSchemeCode;
    private String odsUnitCode;
    private String unitCode;
    private Integer acctYear;

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getOdsUnitCode() {
        return this.odsUnitCode;
    }

    public void setOdsUnitCode(String odsUnitCode) {
        this.odsUnitCode = odsUnitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }
}

