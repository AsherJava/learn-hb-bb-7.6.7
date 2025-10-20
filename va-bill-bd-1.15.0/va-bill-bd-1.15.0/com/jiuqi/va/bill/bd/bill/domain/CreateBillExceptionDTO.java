/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.bill.bd.bill.domain;

import com.jiuqi.va.bill.bd.bill.domain.CreateBillExceptionDO;
import com.jiuqi.va.mapper.domain.PageDTO;

public class CreateBillExceptionDTO
extends CreateBillExceptionDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    private String sql;
    private String srcverifycode;

    public String getSrcverifycode() {
        return this.srcverifycode;
    }

    public void setSrcverifycode(String srcverifycode) {
        this.srcverifycode = srcverifycode;
    }

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

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}

