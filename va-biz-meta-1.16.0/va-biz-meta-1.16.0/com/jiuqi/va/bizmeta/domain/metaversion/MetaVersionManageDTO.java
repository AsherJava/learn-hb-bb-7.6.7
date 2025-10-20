/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.va.bizmeta.domain.metaversion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaVersionManageDO;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class MetaVersionManageDTO
extends MetaVersionManageDO {
    private static final long serialVersionUID = 1L;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date beginDate;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    public Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

