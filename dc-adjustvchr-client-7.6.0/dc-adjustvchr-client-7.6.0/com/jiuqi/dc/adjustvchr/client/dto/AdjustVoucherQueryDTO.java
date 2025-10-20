/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.DcTenantDTO
 */
package com.jiuqi.dc.adjustvchr.client.dto;

import com.jiuqi.dc.base.common.intf.impl.DcTenantDTO;
import java.util.List;

public class AdjustVoucherQueryDTO
extends DcTenantDTO {
    private static final long serialVersionUID = -3610400425148059044L;
    private List<String> unitCodes;
    private Integer acctYear;
    private List<String> adjustTypeCode;
    private Integer affectPeriodStart;
    private Integer affectPeriodEnd;
    private Integer affectPeriod;
    private String vchrNum;
    private Integer page;
    private Integer pageSize;
    private String periodType;
    private Boolean pagination;
    private List<String> groupIdList;
    private String orgType;
    private List<String> exportIdList;

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public List<String> getAdjustTypeCode() {
        return this.adjustTypeCode;
    }

    public void setAdjustTypeCode(List<String> adjustTypeCode) {
        this.adjustTypeCode = adjustTypeCode;
    }

    public Integer getAffectPeriodStart() {
        return this.affectPeriodStart;
    }

    public void setAffectPeriodStart(Integer affectPeriodStart) {
        this.affectPeriodStart = affectPeriodStart;
    }

    public Integer getAffectPeriodEnd() {
        return this.affectPeriodEnd;
    }

    public void setAffectPeriodEnd(Integer affectPeriodEnd) {
        this.affectPeriodEnd = affectPeriodEnd;
    }

    public Integer getAffectPeriod() {
        return this.affectPeriod;
    }

    public void setAffectPeriod(Integer affectPeriod) {
        this.affectPeriod = affectPeriod;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
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

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Boolean getPagination() {
        return this.pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public List<String> getGroupIdList() {
        return this.groupIdList;
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<String> getExportIdList() {
        return this.exportIdList;
    }

    public void setExportIdList(List<String> exportIdList) {
        this.exportIdList = exportIdList;
    }
}

