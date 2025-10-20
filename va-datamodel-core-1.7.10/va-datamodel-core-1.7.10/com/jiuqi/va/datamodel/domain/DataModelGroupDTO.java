/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.datamodel.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.mapper.domain.PageDTO;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelGroupDTO
extends DataModelGroupDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    private String searchKey;

    public boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public static long getSerialversionuid() {
        return 1L;
    }
}

