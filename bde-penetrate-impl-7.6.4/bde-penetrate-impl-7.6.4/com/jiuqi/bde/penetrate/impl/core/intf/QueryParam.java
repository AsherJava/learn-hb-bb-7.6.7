/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.intf;

import java.util.Arrays;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;

public class QueryParam<E> {
    private boolean pagination;
    private int offset;
    private int limit;
    private String sql;
    private Object[] args;
    private ResultSetExtractor<List<E>> rse;

    public QueryParam() {
    }

    public QueryParam(String sql, Object[] args, ResultSetExtractor<List<E>> rse) {
        this.sql = sql;
        this.args = args;
        this.rse = rse;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getArgs() {
        return this.args == null ? new Object[]{} : this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public ResultSetExtractor<List<E>> getRse() {
        return this.rse;
    }

    public void setRse(ResultSetExtractor<List<E>> rse) {
        this.rse = rse;
    }

    public boolean isPagination() {
        return this.pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setPagination(int offset, int limit) {
        this.pagination = true;
        this.offset = offset;
        this.limit = limit;
    }

    public String toString() {
        return "QueryParam [pagination=" + this.pagination + ", offset=" + this.offset + ", limit=" + this.limit + ", sql=" + this.sql + ", args=" + Arrays.toString(this.args) + ", rse=" + this.rse + "]";
    }
}

