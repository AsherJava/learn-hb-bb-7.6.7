/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FinancialCheckIniDataVO
implements Serializable {
    private GcOrgCacheVO localUnit;
    private Integer loginYear;
    private Integer loginMonth;
    private List<SelectOptionVO> acctYearRange;
    private List<SelectOptionVO> acctPeriodRange;
    private List<TableColumnVO> allCheckedColumns;
    private List<TableColumnVO> allUncheckedColumns;
    private List<DimensionVO> dimensionVOS;
    private FinancialCheckConfigVO config;
    private List<Map<String, String>> showWayList;

    public GcOrgCacheVO getLocalUnit() {
        return this.localUnit;
    }

    public void setLocalUnit(GcOrgCacheVO localUnit) {
        this.localUnit = localUnit;
    }

    public Integer getLoginYear() {
        return this.loginYear;
    }

    public void setLoginYear(Integer loginYear) {
        this.loginYear = loginYear;
    }

    public Integer getLoginMonth() {
        return this.loginMonth;
    }

    public void setLoginMonth(Integer loginMonth) {
        this.loginMonth = loginMonth;
    }

    public List<SelectOptionVO> getAcctYearRange() {
        return this.acctYearRange;
    }

    public void setAcctYearRange(List<SelectOptionVO> acctYearRange) {
        this.acctYearRange = acctYearRange;
    }

    public List<SelectOptionVO> getAcctPeriodRange() {
        return this.acctPeriodRange;
    }

    public void setAcctPeriodRange(List<SelectOptionVO> acctPeriodRange) {
        this.acctPeriodRange = acctPeriodRange;
    }

    public List<TableColumnVO> getAllCheckedColumns() {
        return this.allCheckedColumns;
    }

    public void setAllCheckedColumns(List<TableColumnVO> allCheckedColumns) {
        this.allCheckedColumns = allCheckedColumns;
    }

    public List<TableColumnVO> getAllUncheckedColumns() {
        return this.allUncheckedColumns;
    }

    public void setAllUncheckedColumns(List<TableColumnVO> allUncheckedColumns) {
        this.allUncheckedColumns = allUncheckedColumns;
    }

    public List<DimensionVO> getDimensionVOS() {
        return this.dimensionVOS;
    }

    public void setDimensionVOS(List<DimensionVO> dimensionVOS) {
        this.dimensionVOS = dimensionVOS;
    }

    public FinancialCheckConfigVO getConfig() {
        return this.config;
    }

    public void setConfig(FinancialCheckConfigVO config) {
        this.config = config;
    }

    public List<Map<String, String>> getShowWayList() {
        return this.showWayList;
    }

    public void setShowWayList(List<Map<String, String>> showWayList) {
        this.showWayList = showWayList;
    }
}

