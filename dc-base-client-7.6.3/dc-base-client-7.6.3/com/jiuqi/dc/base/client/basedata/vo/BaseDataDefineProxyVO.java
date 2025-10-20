/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 */
package com.jiuqi.dc.base.client.basedata.vo;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;

public class BaseDataDefineProxyVO
extends BaseDataDefineDO {
    private static final long serialVersionUID = 7484825524522702914L;
    private Boolean lazyLoad;
    private Integer limit;

    public Boolean getLazyLoad() {
        return this.lazyLoad;
    }

    public void setLazyLoad(Boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

