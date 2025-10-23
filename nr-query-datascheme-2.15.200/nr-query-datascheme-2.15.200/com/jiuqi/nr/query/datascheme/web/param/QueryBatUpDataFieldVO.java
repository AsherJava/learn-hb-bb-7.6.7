/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.web.facade.BatUpDataFieldVO
 */
package com.jiuqi.nr.query.datascheme.web.param;

import com.jiuqi.nr.datascheme.web.facade.BatUpDataFieldVO;

public class QueryBatUpDataFieldVO
extends BatUpDataFieldVO {
    private Boolean onlyLeaf;
    private String refParameter;

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

