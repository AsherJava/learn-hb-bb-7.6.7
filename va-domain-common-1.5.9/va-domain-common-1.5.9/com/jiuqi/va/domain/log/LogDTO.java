/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.domain.log.LogDO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class LogDTO
extends LogDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date opttimeStart;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date opttimeEnd;

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

    public Date getOpttimeStart() {
        return this.opttimeStart;
    }

    public void setOpttimeStart(Date opttimeStart) {
        this.opttimeStart = opttimeStart;
    }

    public Date getOpttimeEnd() {
        return this.opttimeEnd;
    }

    public void setOpttimeEnd(Date opttimeEnd) {
        this.opttimeEnd = opttimeEnd;
    }
}

