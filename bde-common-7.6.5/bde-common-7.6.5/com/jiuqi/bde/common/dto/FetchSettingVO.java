/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchSettingVO {
    private String id;
    private String regionId;
    private String dataLinkId;
    private String dataLinkName;
    private String fieldDefineId;
    private Integer fieldDefineType;
    private String regionType;
    private String fetchSourceCode;
    private String bizModelCode;
    private String optimizeRuleGroup;
    private String sign;
    private String fetchType;
    private String subjectCode;
    private String excludeSubjectCode;
    private String dimType;
    private String sumType;
    private String reclassSubjCode;
    private String reclassSrcSubjCode;
    private String agingRangeType;
    private Integer agingRangeStart;
    private Integer agingRangeEnd;
    private String cashCode;
    private String dimensionSetting;
    private String formula;
    private String currencyCode;
    private String acctYear;
    private String acctPeriod;
    private String orgCode;
    private List<String> dimComb;
    private String dataSourceCode;
    private String ageGroup;
    private Map<String, String> extParam;

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDataLinkId() {
        return this.dataLinkId;
    }

    public void setDataLinkId(String dataLinkId) {
        this.dataLinkId = dataLinkId;
    }

    public String getDataLinkName() {
        return this.dataLinkName;
    }

    public void setDataLinkName(String dataLinkName) {
        this.dataLinkName = dataLinkName;
    }

    public String getFieldDefineId() {
        return this.fieldDefineId;
    }

    public void setFieldDefineId(String fieldDefineId) {
        this.fieldDefineId = fieldDefineId;
    }

    public Integer getFieldDefineType() {
        return this.fieldDefineType;
    }

    public void setFieldDefineType(Integer fieldDefineType) {
        this.fieldDefineType = fieldDefineType;
    }

    public String getRegionType() {
        return this.regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getFetchSourceCode() {
        return this.fetchSourceCode;
    }

    public void setFetchSourceCode(String fetchSourceCode) {
        this.fetchSourceCode = fetchSourceCode;
    }

    public String getBizModelCode() {
        return this.bizModelCode;
    }

    public void setBizModelCode(String bizModelCode) {
        this.bizModelCode = bizModelCode;
    }

    public String getOptimizeRuleGroup() {
        return this.optimizeRuleGroup;
    }

    public void setOptimizeRuleGroup(String optimizeRuleGroup) {
        this.optimizeRuleGroup = optimizeRuleGroup;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFetchType() {
        return this.fetchType;
    }

    public void setFetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getExcludeSubjectCode() {
        return this.excludeSubjectCode;
    }

    public void setExcludeSubjectCode(String excludeSubjectCode) {
        this.excludeSubjectCode = excludeSubjectCode;
    }

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getSumType() {
        return this.sumType;
    }

    public void setSumType(String sumType) {
        this.sumType = sumType;
    }

    public String getReclassSubjCode() {
        return this.reclassSubjCode;
    }

    public void setReclassSubjCode(String reclassSubjCode) {
        this.reclassSubjCode = reclassSubjCode;
    }

    public String getReclassSrcSubjCode() {
        return this.reclassSrcSubjCode;
    }

    public void setReclassSrcSubjCode(String reclassSrcSubjCode) {
        this.reclassSrcSubjCode = reclassSrcSubjCode;
    }

    public String getAgingRangeType() {
        return this.agingRangeType;
    }

    public void setAgingRangeType(String agingRangeType) {
        this.agingRangeType = agingRangeType;
    }

    public Integer getAgingRangeStart() {
        return this.agingRangeStart;
    }

    public void setAgingRangeStart(Integer agingRangeStart) {
        this.agingRangeStart = agingRangeStart;
    }

    public Integer getAgingRangeEnd() {
        return this.agingRangeEnd;
    }

    public void setAgingRangeEnd(Integer agingRangeEnd) {
        this.agingRangeEnd = agingRangeEnd;
    }

    public String getCashCode() {
        return this.cashCode;
    }

    public void setCashCode(String cashCode) {
        this.cashCode = cashCode;
    }

    public String getDimensionSetting() {
        return this.dimensionSetting;
    }

    public void setDimensionSetting(String dimensionSetting) {
        this.dimensionSetting = dimensionSetting;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getDimComb() {
        return this.dimComb;
    }

    public void setDimComb(List<String> dimComb) {
        this.dimComb = dimComb;
    }

    public String getAgeGroup() {
        return this.ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam) {
        this.extParam = extParam;
    }

    public String toString() {
        return "FetchSettingVO [id=" + this.id + ", regionId=" + this.regionId + ", dataLinkId=" + this.dataLinkId + ", dataLinkName=" + this.dataLinkName + ", fieldDefineId=" + this.fieldDefineId + ", fieldDefineType=" + this.fieldDefineType + ", regionType=" + this.regionType + ", fetchSourceCode=" + this.fetchSourceCode + ", bizModelCode=" + this.bizModelCode + ", optimizeRuleGroup=" + this.optimizeRuleGroup + ", sign=" + this.sign + ", fetchType=" + this.fetchType + ", subjectCode=" + this.subjectCode + ", excludeSubjectCode=" + this.excludeSubjectCode + ", dimType=" + this.dimType + ", sumType=" + this.sumType + ", reclassSubjCode=" + this.reclassSubjCode + ", reclassSrcSubjCode=" + this.reclassSrcSubjCode + ", agingRangeType=" + this.agingRangeType + ", agingRangeStart=" + this.agingRangeStart + ", agingRangeEnd=" + this.agingRangeEnd + ", cashCode=" + this.cashCode + ", dimensionSetting=" + this.dimensionSetting + ", formula=" + this.formula + ", currencyCode=" + this.currencyCode + ", acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", orgCode=" + this.orgCode + ", dimComb=" + this.dimComb + ", dataSourceCode=" + this.dataSourceCode + ", ageGroup=" + this.ageGroup + ", extParam=" + this.extParam + "]";
    }
}

