/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.dc.base.common.intf.impl.PageDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.bde.penetrate.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.dc.base.common.intf.impl.PageDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.List;
import java.util.Map;

public class PenetrateBaseDTO
implements PageDTO {
    private static final long serialVersionUID = -8999446679622237990L;
    private String requestTaskId;
    private String bizModelKey;
    private String fetchSourceCode;
    private String bblx;
    private Boolean includeUncharged = true;
    private Boolean includeAdjustVchr;
    private Boolean showBalanceOrient;
    private Integer acctYear;
    private Integer startPeriod;
    private Integer endPeriod;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    private String unitCode;
    private String acctOrgCode;
    private String acctOrgName;
    private String assistCode;
    private String assistName;
    private String subjectCode;
    private String excludeSubjectCode;
    private String cashCode;
    private String sumType;
    private String optimizeRuleGroup;
    private Integer fieldDefineFractionDigits;
    private String agingRangeType;
    private String agingRangeStart;
    private String agingRangeEnd;
    private Boolean pagination;
    private Integer offset;
    private Integer limit;
    private List<Dimension> assTypeList;
    private List<String> dimType;
    private Boolean showOrgnAmnt;
    @JsonIgnore
    private DataSchemeDTO dataScheme;
    @JsonIgnore
    private OrgMappingDTO orgMapping;
    private String currencyCode;
    private String unitType;
    private String fetchType;
    private String vchrSrcType;
    private String unitVer;
    private String formula;
    private String startDate;
    private String endDate;
    private Map<String, Map<String, Object>> dimensionSet;

    public String getUnitVer() {
        return this.unitVer;
    }

    public void setUnitVer(String unitVer) {
        this.unitVer = unitVer;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getBizModelKey() {
        return this.bizModelKey;
    }

    public void setBizModelKey(String bizModelKey) {
        this.bizModelKey = bizModelKey;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public Boolean getIncludeUncharged() {
        if (this.includeUncharged == null) {
            return true;
        }
        return Boolean.TRUE.equals(this.includeUncharged);
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public Boolean getShowBalanceOrient() {
        return this.showBalanceOrient;
    }

    public void setShowBalanceOrient(Boolean showBalanceOrient) {
        this.showBalanceOrient = showBalanceOrient;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Integer getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Integer endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getStartAdjustPeriod() {
        return this.startAdjustPeriod;
    }

    public void setStartAdjustPeriod(String startAdjustPeriod) {
        this.startAdjustPeriod = startAdjustPeriod;
    }

    public String getEndAdjustPeriod() {
        return this.endAdjustPeriod;
    }

    public void setEndAdjustPeriod(String endAdjustPeriod) {
        this.endAdjustPeriod = endAdjustPeriod;
    }

    public Boolean getPagination() {
        return this.pagination;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getAcctOrgCode() {
        return this.acctOrgCode;
    }

    public void setAcctOrgCode(String acctOrgCode) {
        this.acctOrgCode = acctOrgCode;
    }

    public String getAcctOrgName() {
        return this.acctOrgName;
    }

    public void setAcctOrgName(String acctOrgName) {
        this.acctOrgName = acctOrgName;
    }

    public String getAssistCode() {
        return this.assistCode;
    }

    public void setAssistCode(String assistCode) {
        this.assistCode = assistCode;
    }

    public String getAssistName() {
        return this.assistName;
    }

    public void setAssistName(String assistName) {
        this.assistName = assistName;
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

    public String getCashCode() {
        return this.cashCode;
    }

    public void setCashCode(String cashCode) {
        this.cashCode = cashCode;
    }

    public String getSumType() {
        return this.sumType;
    }

    public void setSumType(String sumType) {
        this.sumType = sumType;
    }

    public String getOptimizeRuleGroup() {
        return this.optimizeRuleGroup;
    }

    public void setOptimizeRuleGroup(String optimizeRuleGroup) {
        this.optimizeRuleGroup = optimizeRuleGroup;
    }

    public String getAgingRangeType() {
        return this.agingRangeType;
    }

    public void setAgingRangeType(String agingRangeType) {
        this.agingRangeType = agingRangeType;
    }

    public String getAgingRangeStart() {
        return this.agingRangeStart;
    }

    public void setAgingRangeStart(String agingRangeStart) {
        this.agingRangeStart = agingRangeStart;
    }

    public String getAgingRangeEnd() {
        return this.agingRangeEnd;
    }

    public void setAgingRangeEnd(String agingRangeEnd) {
        this.agingRangeEnd = agingRangeEnd;
    }

    public Boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<Dimension> getAssTypeList() {
        return this.assTypeList;
    }

    public void setAssTypeList(List<Dimension> assTypeList) {
        this.assTypeList = assTypeList;
    }

    public List<String> getDimType() {
        return this.dimType;
    }

    public void setDimType(List<String> dimType) {
        this.dimType = dimType;
    }

    public Boolean getShowOrgnAmnt() {
        return this.showOrgnAmnt;
    }

    public void setShowOrgnAmnt(Boolean showOrgnAmnt) {
        this.showOrgnAmnt = showOrgnAmnt;
    }

    public DataSchemeDTO getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataSchemeDTO dataScheme) {
        this.dataScheme = dataScheme;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMaping) {
        this.orgMapping = orgMaping;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getFetchType() {
        return this.fetchType;
    }

    public void setFetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    public String getVchrSrcType() {
        return this.vchrSrcType;
    }

    public void setVchrSrcType(String vchrSrcType) {
        this.vchrSrcType = vchrSrcType;
    }

    public Map<String, Map<String, Object>> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, Map<String, Object>> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFetchSourceCode() {
        return this.fetchSourceCode;
    }

    public void setFetchSourceCode(String fetchSourceCode) {
        this.fetchSourceCode = fetchSourceCode;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getFieldDefineFractionDigits() {
        return this.fieldDefineFractionDigits;
    }

    public void setFieldDefineFractionDigits(Integer fieldDefineFractionDigits) {
        this.fieldDefineFractionDigits = fieldDefineFractionDigits;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        return "PenetrateBaseDTO{requestTaskId='" + this.requestTaskId + '\'' + ", bizModelKey='" + this.bizModelKey + '\'' + ", fetchSourceCode='" + this.fetchSourceCode + '\'' + ", bblx='" + this.bblx + '\'' + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", showBalanceOrient=" + this.showBalanceOrient + ", acctYear=" + this.acctYear + ", startPeriod=" + this.startPeriod + ", endPeriod=" + this.endPeriod + ", startAdjustPeriod='" + this.startAdjustPeriod + '\'' + ", endAdjustPeriod='" + this.endAdjustPeriod + '\'' + ", unitCode='" + this.unitCode + '\'' + ", acctOrgCode='" + this.acctOrgCode + '\'' + ", acctOrgName='" + this.acctOrgName + '\'' + ", assistCode='" + this.assistCode + '\'' + ", assistName='" + this.assistName + '\'' + ", subjectCode='" + this.subjectCode + '\'' + ", excludeSubjectCode='" + this.excludeSubjectCode + '\'' + ", cashCode='" + this.cashCode + '\'' + ", sumType='" + this.sumType + '\'' + ", optimizeRuleGroup='" + this.optimizeRuleGroup + '\'' + ", fieldDefineFractionDigits=" + this.fieldDefineFractionDigits + ", agingRangeType='" + this.agingRangeType + '\'' + ", agingRangeStart='" + this.agingRangeStart + '\'' + ", agingRangeEnd='" + this.agingRangeEnd + '\'' + ", pagination=" + this.pagination + ", offset=" + this.offset + ", limit=" + this.limit + ", assTypeList=" + this.assTypeList + ", dimType=" + this.dimType + ", showOrgnAmnt=" + this.showOrgnAmnt + ", dataScheme=" + this.dataScheme + ", orgMapping=" + this.orgMapping + ", currencyCode='" + this.currencyCode + '\'' + ", unitType='" + this.unitType + '\'' + ", fetchType='" + this.fetchType + '\'' + ", vchrSrcType='" + this.vchrSrcType + '\'' + ", unitVer='" + this.unitVer + '\'' + ", formula='" + this.formula + '\'' + ", startDate='" + this.startDate + '\'' + ", endDate='" + this.endDate + '\'' + ", dimensionSet=" + this.dimensionSet + '}';
    }
}

