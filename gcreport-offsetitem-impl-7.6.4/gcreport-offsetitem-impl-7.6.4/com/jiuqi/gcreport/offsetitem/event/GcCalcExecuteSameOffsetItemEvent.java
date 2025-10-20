/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 */
package com.jiuqi.gcreport.offsetitem.event;

import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import org.springframework.context.ApplicationEvent;

public class GcCalcExecuteSameOffsetItemEvent
extends ApplicationEvent {
    private QueryParamsDTO queryParamsDTO;

    public GcCalcExecuteSameOffsetItemEvent(Object source, QueryParamsDTO queryParamsDTO) {
        super(source);
        this.queryParamsDTO = queryParamsDTO;
    }

    public QueryParamsDTO getQueryParamsDTO() {
        return this.queryParamsDTO;
    }

    public void setQueryParamsDTO(QueryParamsDTO queryParamsDTO) {
        this.queryParamsDTO = queryParamsDTO;
    }
}

