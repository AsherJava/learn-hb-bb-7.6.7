/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.dc.base.client.rpunitmapping.queryvo;

import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class Org2RpunitMappingQueryVO
extends TenantDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private List<String> orgs;
    private String rpUnit;
    private Integer acctYear;
    private Integer acctPeriod;
    private List<String> delIds;
    private Integer pageNum = -1;
    private Integer pageSize;

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

    public boolean isPagination() {
        return this.pageNum > 0;
    }

    public int getOffset() {
        return this.pageSize * (this.pageNum - 1);
    }

    public int getLimit() {
        return this.pageSize;
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

    public List<String> getDelIds() {
        return this.delIds;
    }

    public void setDelIds(List<String> delIds) {
        this.delIds = delIds;
    }

    public List<String> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public String getRpUnit() {
        return this.rpUnit;
    }

    public void setRpUnit(String rpUnit) {
        this.rpUnit = rpUnit;
    }
}

