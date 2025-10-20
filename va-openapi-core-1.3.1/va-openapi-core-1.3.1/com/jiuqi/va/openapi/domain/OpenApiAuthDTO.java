/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.openapi.domain;

import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;

public class OpenApiAuthDTO
extends OpenApiAuthDO
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
}

