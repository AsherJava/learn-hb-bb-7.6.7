/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.web.facade.DataFieldVO
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.query.datascheme.web.param;

import com.jiuqi.nr.datascheme.web.facade.DataFieldVO;
import com.jiuqi.nr.entity.model.IEntityAttribute;

public class QueryDataFieldVO
extends DataFieldVO {
    private Boolean onlyLeaf;
    private String refParameter;

    public QueryDataFieldVO() {
    }

    public QueryDataFieldVO(IEntityAttribute next, String tableName) {
        super(next, tableName);
    }

    public Boolean getOnlyLeaf() {
        return this.onlyLeaf;
    }

    public void setOnlyLeaf(Boolean onlyLeaf) {
        this.onlyLeaf = onlyLeaf;
    }

    public String getRefParameter() {
        return this.refParameter;
    }

    public void setRefParameter(String refParameter) {
        this.refParameter = refParameter;
    }
}

