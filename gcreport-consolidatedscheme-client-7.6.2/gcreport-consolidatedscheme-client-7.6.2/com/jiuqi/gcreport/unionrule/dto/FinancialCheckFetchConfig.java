/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.jiuqi.gcreport.unionrule.dto.FinancialCheckDimSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import java.util.List;

public class FinancialCheckFetchConfig {
    private String fetchSetGroupId;
    private String businessTypeCode;
    private String filterFormula;
    private String manualFilterFormula;
    private String description;
    private List<Item> debitConfigList;
    private List<Item> creditConfigList;

    public String getFetchSetGroupId() {
        return this.fetchSetGroupId;
    }

    public void setFetchSetGroupId(String fetchSetGroupId) {
        this.fetchSetGroupId = fetchSetGroupId;
    }

    public String getBusinessTypeCode() {
        return this.businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public String getManualFilterFormula() {
        return this.manualFilterFormula;
    }

    public void setManualFilterFormula(String manualFilterFormula) {
        this.manualFilterFormula = manualFilterFormula;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getDebitConfigList() {
        return this.debitConfigList;
    }

    public void setDebitConfigList(List<Item> debitConfigList) {
        this.debitConfigList = debitConfigList;
    }

    public List<Item> getCreditConfigList() {
        return this.creditConfigList;
    }

    public void setCreditConfigList(List<Item> creditConfigList) {
        this.creditConfigList = creditConfigList;
    }

    public static class Item {
        private FetchTypeEnum fetchType;
        private String subjectCode;
        private String fetchFormula;
        private FinancialCheckDimSrcTypeEnum dimSrcType;

        public FetchTypeEnum getFetchType() {
            return this.fetchType;
        }

        public void setFetchType(FetchTypeEnum fetchType) {
            this.fetchType = fetchType;
        }

        public String getSubjectCode() {
            return this.subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        public String getFetchFormula() {
            return this.fetchFormula;
        }

        public void setFetchFormula(String fetchFormula) {
            this.fetchFormula = fetchFormula;
        }

        public FinancialCheckDimSrcTypeEnum getDimSrcType() {
            return this.dimSrcType;
        }

        public void setDimSrcType(FinancialCheckDimSrcTypeEnum dimSrcType) {
            this.dimSrcType = dimSrcType;
        }
    }
}

