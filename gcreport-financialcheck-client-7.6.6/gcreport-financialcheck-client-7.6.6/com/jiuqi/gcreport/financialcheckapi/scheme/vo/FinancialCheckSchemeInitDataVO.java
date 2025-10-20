/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.common.SelectOptionVO
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.vo;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.common.SelectOptionVO;
import java.util.List;

public class FinancialCheckSchemeInitDataVO {
    private Integer acctYear;
    private String orgVer;
    private String orgType;
    private List<SelectOptionVO> checkDimensions;
    private List<GcBaseData> checkAttributes;

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getOrgVer() {
        return this.orgVer;
    }

    public void setOrgVer(String orgVer) {
        this.orgVer = orgVer;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<SelectOptionVO> getCheckDimensions() {
        return this.checkDimensions;
    }

    public void setCheckDimensions(List<SelectOptionVO> checkDimensions) {
        this.checkDimensions = checkDimensions;
    }

    public List<GcBaseData> getCheckAttributes() {
        return this.checkAttributes;
    }

    public void setCheckAttributes(List<GcBaseData> checkAttributes) {
        this.checkAttributes = checkAttributes;
    }
}

