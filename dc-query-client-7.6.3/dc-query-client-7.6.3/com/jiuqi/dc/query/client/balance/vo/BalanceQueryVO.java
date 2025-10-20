/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.balance.vo;

import com.jiuqi.dc.query.client.balance.vo.BalanceQueryDimVO;
import java.util.List;

public class BalanceQueryVO {
    private List<String> unitCodes;
    private Boolean reportFlag;
    private Integer acctYear;
    private Integer startPeriod;
    private Integer endPeriod;
    private String currencyCode;
    private String repCurrCode;
    private String startSubjectCode;
    private String endSubjectCode;
    private Boolean isExportFile;
    private Boolean showCurrColumnEnable;
    private Boolean showRepCurrColumnEnable;
    private Integer page;
    private Integer pageSize;
    private List<BalanceQueryDimVO> queryDimList;
    private Boolean containAdjustVchr;
    private String originUnitCode;
    private String showUnitName;
    private String showCurrency;
    private String excludeSubj;
    private List<String> excludeSubjList;
    private String excludeStartSubj;
    private String excludeEndSubj;
    private String multiSubj;
    private List<String> multiSubjList;
    private Boolean orgn;
    private Boolean filterZero;

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Boolean getReportFlag() {
        return this.reportFlag;
    }

    public void setReportFlag(Boolean reportFlag) {
        this.reportFlag = reportFlag;
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

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRepCurrCode() {
        return this.repCurrCode;
    }

    public void setRepCurrCode(String repCurrCode) {
        this.repCurrCode = repCurrCode;
    }

    public String getStartSubjectCode() {
        return this.startSubjectCode;
    }

    public void setStartSubjectCode(String startSubjectCode) {
        this.startSubjectCode = startSubjectCode;
    }

    public String getEndSubjectCode() {
        return this.endSubjectCode;
    }

    public void setEndSubjectCode(String endSubjectCode) {
        this.endSubjectCode = endSubjectCode;
    }

    public List<BalanceQueryDimVO> getQueryDimList() {
        return this.queryDimList;
    }

    public void setQueryDimList(List<BalanceQueryDimVO> queryDimList) {
        this.queryDimList = queryDimList;
    }

    public Boolean getExportFile() {
        return this.isExportFile;
    }

    public void setExportFile(Boolean exportFile) {
        this.isExportFile = exportFile;
    }

    public Boolean getShowCurrColumnEnable() {
        return this.showCurrColumnEnable;
    }

    public void setShowCurrColumnEnable(Boolean showCurrColumnEnable) {
        this.showCurrColumnEnable = showCurrColumnEnable;
    }

    public Boolean getShowRepCurrColumnEnable() {
        return this.showRepCurrColumnEnable;
    }

    public void setShowRepCurrColumnEnable(Boolean showRepCurrColumnEnable) {
        this.showRepCurrColumnEnable = showRepCurrColumnEnable;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getContainAdjustVchr() {
        return this.containAdjustVchr;
    }

    public void setContainAdjustVchr(Boolean containAdjustVchr) {
        this.containAdjustVchr = containAdjustVchr;
    }

    public String getOriginUnitCode() {
        return this.originUnitCode;
    }

    public void setOriginUnitCode(String originUnitCode) {
        this.originUnitCode = originUnitCode;
    }

    public String getShowUnitName() {
        return this.showUnitName;
    }

    public void setShowUnitName(String showUnitName) {
        this.showUnitName = showUnitName;
    }

    public String getShowCurrency() {
        return this.showCurrency;
    }

    public void setShowCurrency(String showCurrency) {
        this.showCurrency = showCurrency;
    }

    public String getExcludeSubj() {
        return this.excludeSubj;
    }

    public void setExcludeSubj(String excludeSubj) {
        this.excludeSubj = excludeSubj;
    }

    public List<String> getExcludeSubjList() {
        return this.excludeSubjList;
    }

    public void setExcludeSubjList(List<String> excludeSubjList) {
        this.excludeSubjList = excludeSubjList;
    }

    public String getExcludeStartSubj() {
        return this.excludeStartSubj;
    }

    public void setExcludeStartSubj(String excludeStartSubj) {
        this.excludeStartSubj = excludeStartSubj;
    }

    public String getExcludeEndSubj() {
        return this.excludeEndSubj;
    }

    public void setExcludeEndSubj(String excludeEndSubj) {
        this.excludeEndSubj = excludeEndSubj;
    }

    public String getMultiSubj() {
        return this.multiSubj;
    }

    public void setMultiSubj(String multiSubj) {
        this.multiSubj = multiSubj;
    }

    public List<String> getMultiSubjList() {
        return this.multiSubjList;
    }

    public void setMultiSubjList(List<String> multiSubjList) {
        this.multiSubjList = multiSubjList;
    }

    public Boolean isOrgn() {
        return this.orgn;
    }

    public void setOrgn(Boolean orgn) {
        this.orgn = orgn;
    }

    public Boolean getFilterZero() {
        return this.filterZero;
    }

    public void setFilterZero(Boolean filterZero) {
        this.filterZero = filterZero;
    }
}

