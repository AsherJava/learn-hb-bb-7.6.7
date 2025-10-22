/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import java.util.List;
import java.util.Map;

public class FinancialCheckQueryConditionVO {
    private Integer pageNum;
    private Integer pageSize;
    private String unitId;
    private String oppUnitId;
    private Integer acctYear;
    private Integer acctPeriod;
    private List<String> schemeIds;
    private String checkType;
    private List<String> subjectCodes;
    private String origCurrencyCode;
    private Double debitOrigMin;
    private Double debitOrigMax;
    private Double creditOrigMin;
    private Double creditOrigMax;
    private Double debitMin;
    private Double debitMax;
    private Double creditMin;
    private Double creditMax;
    private String gcNumber;
    private String vchrType;
    private String vchrNum;
    private String digest;
    private String billCode;
    private Integer checkYear;
    private Integer checkPeriod;
    private Boolean periodTotalEnable;
    private Boolean checkPeriodTotalEnable;
    private Map<String, Object> dimensions;
    private String inputWay;
    private Integer businessRole;
    private String checkAttribute;
    private String checkLevel;
    private String showType;
    private String showLocation;
    private List<String> oppSubjectCodes;
    private String oppGcNumber;
    private String oppVchrType;
    private String oppVchrNum;
    private Double oppDebitOrigMin;
    private Double oppDebitOrigMax;
    private Double oppCreditOrigMin;
    private Double oppCreditOrigMax;
    private String oppDigest;
    private Map<String, Object> oppDimensions;
    private String oppInputWay;
    private List<TableColumnVO> sortColumns;
    private String sn;
    private List<String> checkIds;

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String getOrigCurrencyCode() {
        return this.origCurrencyCode;
    }

    public void setOrigCurrencyCode(String origCurrencyCode) {
        this.origCurrencyCode = origCurrencyCode;
    }

    public Double getDebitOrigMin() {
        return this.debitOrigMin;
    }

    public void setDebitOrigMin(Double debitOrigMin) {
        this.debitOrigMin = debitOrigMin;
    }

    public Double getDebitOrigMax() {
        return this.debitOrigMax;
    }

    public void setDebitOrigMax(Double debitOrigMax) {
        this.debitOrigMax = debitOrigMax;
    }

    public Double getCreditOrigMin() {
        return this.creditOrigMin;
    }

    public void setCreditOrigMin(Double creditOrigMin) {
        this.creditOrigMin = creditOrigMin;
    }

    public Double getCreditOrigMax() {
        return this.creditOrigMax;
    }

    public void setCreditOrigMax(Double creditOrigMax) {
        this.creditOrigMax = creditOrigMax;
    }

    public Double getDebitMin() {
        return this.debitMin;
    }

    public void setDebitMin(Double debitMin) {
        this.debitMin = debitMin;
    }

    public Double getDebitMax() {
        return this.debitMax;
    }

    public void setDebitMax(Double debitMax) {
        this.debitMax = debitMax;
    }

    public Double getCreditMin() {
        return this.creditMin;
    }

    public void setCreditMin(Double creditMin) {
        this.creditMin = creditMin;
    }

    public Double getCreditMax() {
        return this.creditMax;
    }

    public void setCreditMax(Double creditMax) {
        this.creditMax = creditMax;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getVchrType() {
        return this.vchrType;
    }

    public void setVchrType(String vchrType) {
        this.vchrType = vchrType;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Integer getCheckPeriod() {
        return this.checkPeriod;
    }

    public void setCheckPeriod(Integer checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    public Boolean getPeriodTotalEnable() {
        return this.periodTotalEnable;
    }

    public void setPeriodTotalEnable(Boolean periodTotalEnable) {
        this.periodTotalEnable = periodTotalEnable;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public List<String> getSchemeIds() {
        return this.schemeIds;
    }

    public void setSchemeIds(List<String> schemeIds) {
        this.schemeIds = schemeIds;
    }

    public List<String> getOppSubjectCodes() {
        return this.oppSubjectCodes;
    }

    public void setOppSubjectCodes(List<String> oppSubjectCodes) {
        this.oppSubjectCodes = oppSubjectCodes;
    }

    public String getOppGcNumber() {
        return this.oppGcNumber;
    }

    public void setOppGcNumber(String oppGcNumber) {
        this.oppGcNumber = oppGcNumber;
    }

    public String getOppVchrType() {
        return this.oppVchrType;
    }

    public void setOppVchrType(String oppVchrType) {
        this.oppVchrType = oppVchrType;
    }

    public String getOppVchrNum() {
        return this.oppVchrNum;
    }

    public void setOppVchrNum(String oppVchrNum) {
        this.oppVchrNum = oppVchrNum;
    }

    public Double getOppDebitOrigMin() {
        return this.oppDebitOrigMin;
    }

    public void setOppDebitOrigMin(Double oppDebitOrigMin) {
        this.oppDebitOrigMin = oppDebitOrigMin;
    }

    public Double getOppDebitOrigMax() {
        return this.oppDebitOrigMax;
    }

    public void setOppDebitOrigMax(Double oppDebitOrigMax) {
        this.oppDebitOrigMax = oppDebitOrigMax;
    }

    public Double getOppCreditOrigMin() {
        return this.oppCreditOrigMin;
    }

    public void setOppCreditOrigMin(Double oppCreditOrigMin) {
        this.oppCreditOrigMin = oppCreditOrigMin;
    }

    public Double getOppCreditOrigMax() {
        return this.oppCreditOrigMax;
    }

    public void setOppCreditOrigMax(Double oppCreditOrigMax) {
        this.oppCreditOrigMax = oppCreditOrigMax;
    }

    public String getOppDigest() {
        return this.oppDigest;
    }

    public void setOppDigest(String oppDigest) {
        this.oppDigest = oppDigest;
    }

    public List<TableColumnVO> getSortColumns() {
        return this.sortColumns;
    }

    public void setSortColumns(List<TableColumnVO> sortColumns) {
        this.sortColumns = sortColumns;
    }

    public String getShowLocation() {
        return this.showLocation;
    }

    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }

    public Map<String, Object> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(Map<String, Object> dimensions) {
        this.dimensions = dimensions;
    }

    public Map<String, Object> getOppDimensions() {
        return this.oppDimensions;
    }

    public void setOppDimensions(Map<String, Object> oppDimensions) {
        this.oppDimensions = oppDimensions;
    }

    public Integer getCheckYear() {
        return this.checkYear;
    }

    public void setCheckYear(Integer checkYear) {
        this.checkYear = checkYear;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOrgVer() {
        String dateStr = String.format("%04d", this.acctYear) + String.format("%02d", this.acctPeriod == 0 ? 1 : this.acctPeriod) + "15";
        return dateStr;
    }

    public String getInputWay() {
        return this.inputWay;
    }

    public void setInputWay(String inputWay) {
        this.inputWay = inputWay;
    }

    public String getOppInputWay() {
        return this.oppInputWay;
    }

    public void setOppInputWay(String oppInputWay) {
        this.oppInputWay = oppInputWay;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.businessRole = businessRole;
    }

    public String getCheckLevel() {
        return this.checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public Boolean getCheckPeriodTotalEnable() {
        return this.checkPeriodTotalEnable;
    }

    public void setCheckPeriodTotalEnable(Boolean checkPeriodTotalEnable) {
        this.checkPeriodTotalEnable = checkPeriodTotalEnable;
    }

    public List<String> getCheckIds() {
        return this.checkIds;
    }

    public void setCheckId(List<String> checkIds) {
        this.checkIds = checkIds;
    }

    public String getCheckAttribute() {
        return this.checkAttribute;
    }

    public void setCheckAttribute(String checkAttribute) {
        this.checkAttribute = checkAttribute;
    }
}

