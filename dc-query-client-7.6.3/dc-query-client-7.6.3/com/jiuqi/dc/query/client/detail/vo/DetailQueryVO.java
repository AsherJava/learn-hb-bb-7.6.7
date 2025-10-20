/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.vo;

import com.jiuqi.dc.query.client.balance.vo.BalanceQueryDimVO;
import java.util.List;

public class DetailQueryVO {
    private List<String> unitCodes;
    private Boolean reportFlag;
    private Integer acctYear;
    private Integer startPeriod;
    private Integer endPeriod;
    private String currencyCode;
    private String startSubjectCode;
    private String endSubjectCode;
    private Boolean pagination;
    private Boolean showCurrColumnEnable;
    private Integer page;
    private Integer pageSize;
    private Boolean containAdjustVchr;
    private boolean containAdjustVchrOnly;
    private String originUnitCode;
    private String showUnitName;
    private String showCurrency;
    private String excludeSubj;
    private List<String> excludeSubjList;
    private String excludeStartSubj;
    private String excludeEndSubj;
    private Integer srcTypeFlag;
    private List<BalanceQueryDimVO> queryDimList;
    private String multiSubj;
    private List<String> multiSubjList;
    private Boolean orgn;
    private String selectCf;
    private String startCfCode;
    private String endCfCode;
    private List<String> selectCfList;
    private String excludeCf;
    private List<String> excludeCfList;
    private String excludeStartCf;
    private String excludeEndCf;
    private boolean cfFlag;

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

    public Boolean getPagination() {
        return this.pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public Boolean getShowCurrColumnEnable() {
        return this.showCurrColumnEnable;
    }

    public void setShowCurrColumnEnable(Boolean showCurrColumnEnable) {
        this.showCurrColumnEnable = showCurrColumnEnable;
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

    public List<BalanceQueryDimVO> getQueryDimList() {
        return this.queryDimList;
    }

    public void setQueryDimList(List<BalanceQueryDimVO> queryDimList) {
        this.queryDimList = queryDimList;
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

    public Integer getSrcTypeFlag() {
        return this.srcTypeFlag;
    }

    public void setSrcTypeFlag(Integer srcTypeFlag) {
        this.srcTypeFlag = srcTypeFlag;
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

    public Boolean getOrgn() {
        return this.orgn;
    }

    public void setOrgn(Boolean orgn) {
        this.orgn = orgn;
    }

    public String getSelectCf() {
        return this.selectCf;
    }

    public void setSelectCf(String selectCf) {
        this.selectCf = selectCf;
    }

    public String getStartCfCode() {
        return this.startCfCode;
    }

    public void setStartCfCode(String startCfCode) {
        this.startCfCode = startCfCode;
    }

    public String getEndCfCode() {
        return this.endCfCode;
    }

    public void setEndCfCode(String endCfCode) {
        this.endCfCode = endCfCode;
    }

    public List<String> getSelectCfList() {
        return this.selectCfList;
    }

    public void setSelectCfList(List<String> selectCfList) {
        this.selectCfList = selectCfList;
    }

    public String getExcludeCf() {
        return this.excludeCf;
    }

    public void setExcludeCf(String excludeCf) {
        this.excludeCf = excludeCf;
    }

    public List<String> getExcludeCfList() {
        return this.excludeCfList;
    }

    public void setExcludeCfList(List<String> excludeCfList) {
        this.excludeCfList = excludeCfList;
    }

    public String getExcludeStartCf() {
        return this.excludeStartCf;
    }

    public void setExcludeStartCf(String excludeStartCf) {
        this.excludeStartCf = excludeStartCf;
    }

    public String getExcludeEndCf() {
        return this.excludeEndCf;
    }

    public void setExcludeEndCf(String excludeEndCf) {
        this.excludeEndCf = excludeEndCf;
    }

    public boolean getCfFlag() {
        return this.cfFlag;
    }

    public void setCfFlag(boolean cfFlag) {
        this.cfFlag = cfFlag;
    }

    public boolean getContainAdjustVchrOnly() {
        return this.containAdjustVchrOnly;
    }

    public void setContainAdjustVchrOnly(boolean containAdjustVchrOnly) {
        this.containAdjustVchrOnly = containAdjustVchrOnly;
    }
}

