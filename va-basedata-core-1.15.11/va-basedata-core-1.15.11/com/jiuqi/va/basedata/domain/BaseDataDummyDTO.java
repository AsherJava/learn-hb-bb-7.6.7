/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.mapper.domain.PageDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class BaseDataDummyDTO
extends TenantDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private String sqlDefine;
    private List<BaseDataDO> basedataList;
    private Boolean pagination;
    private int offset;
    private int limit;
    private int structtype;

    public String getSqlDefine() {
        return this.sqlDefine;
    }

    public void setSqlDefine(String sqlDefine) {
        this.sqlDefine = sqlDefine;
    }

    public List<BaseDataDO> getBasedataList() {
        return this.basedataList;
    }

    public void setBasedataList(List<BaseDataDO> basedataList) {
        this.basedataList = basedataList;
    }

    public Boolean getPagination() {
        return this.pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public boolean isPagination() {
        return this.pagination == null ? Boolean.FALSE.booleanValue() : this.pagination.booleanValue();
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

    public int getStructtype() {
        return this.structtype;
    }

    public void setStructtype(int structtype) {
        this.structtype = structtype;
    }
}

