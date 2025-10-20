/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.domain.datamodel;

import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.math.BigDecimal;

public class DataModelDTO
extends DataModelDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    private String searchKey;
    private BigDecimal ver;
    private String[] groupCodes;
    private Boolean deepClone;

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

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public String[] getGroupCodes() {
        return this.groupCodes;
    }

    public void setGroupCodes(String[] groupCodes) {
        this.groupCodes = groupCodes;
    }

    public void setDeepClone(Boolean deepClone) {
        this.deepClone = deepClone;
    }

    public Boolean isDeepClone() {
        return this.deepClone == null ? Boolean.TRUE : this.deepClone;
    }
}

