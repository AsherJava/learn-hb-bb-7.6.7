/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.BizModelExtFieldInfo
 */
package com.jiuqi.bde.bizmodel.impl.model.entity;

import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelEO;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;

public class FinBizModelEO
extends BizModelEO {
    private static final long serialVersionUID = 1672536034071418737L;
    public static final String TABLENAME = "BDE_BIZMODEL_FINDATA";
    private String fetchTypes;
    private String dimensions;
    private Integer selectAll;
    private BizModelExtFieldInfo bizModelExtFieldInfo;

    public String getFetchTypes() {
        return this.fetchTypes;
    }

    public void setFetchTypes(String fetchTypes) {
        this.fetchTypes = fetchTypes;
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getSelectAll() {
        return this.selectAll;
    }

    public void setSelectAll(Integer selectAll) {
        this.selectAll = selectAll;
    }

    public BizModelExtFieldInfo getBizModelExtFieldInfo() {
        return this.bizModelExtFieldInfo;
    }

    public void setBizModelExtFieldInfo(BizModelExtFieldInfo bizModelExtFieldInfo) {
        this.bizModelExtFieldInfo = bizModelExtFieldInfo;
    }

    @Override
    public String toString() {
        return "FinBizModelEO{fetchTypes='" + this.fetchTypes + '\'' + ", dimensions='" + this.dimensions + '\'' + ", selectAll=" + this.selectAll + ", bizModelExtFields=" + this.bizModelExtFieldInfo + '}';
    }
}

