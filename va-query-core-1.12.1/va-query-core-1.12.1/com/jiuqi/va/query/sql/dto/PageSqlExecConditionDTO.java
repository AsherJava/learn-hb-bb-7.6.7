/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.FormatNumberEnum
 */
package com.jiuqi.va.query.sql.dto;

import com.jiuqi.va.query.common.FormatNumberEnum;
import com.jiuqi.va.query.sql.dto.SqlExecConditionDTO;

public class PageSqlExecConditionDTO
extends SqlExecConditionDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean pageQuery = false;
    private FormatNumberEnum formatNumber;

    public boolean isPageQuery() {
        return this.pageQuery;
    }

    public void setIsPageQuery(boolean pageQuery) {
        this.pageQuery = pageQuery;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getPageQuery() {
        return this.pageQuery;
    }

    public void setPageQuery(Boolean pageQuery) {
        this.pageQuery = pageQuery;
    }

    public FormatNumberEnum getFormatNumber() {
        return this.formatNumber;
    }

    public void setFormatNumber(FormatNumberEnum formatNumber) {
        this.formatNumber = formatNumber;
    }
}

