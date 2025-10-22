/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryParam
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FieldGrowthDataQueryParam;

public class FieldGrowthDataQueryDTO
extends FieldGrowthDataQueryParam {
    public FieldGrowthDataQueryDTO(FieldGrowthDataQueryParam fieldGrowthDataQueryParam) {
        this.setFormSchemeKey(fieldGrowthDataQueryParam.getFormSchemeKey());
        this.setMdCode(fieldGrowthDataQueryParam.getMdCode());
        this.setDatatime(fieldGrowthDataQueryParam.getDatatime());
        this.setQueryFields(fieldGrowthDataQueryParam.getQueryFields());
        this.setEntityID(fieldGrowthDataQueryParam.getEntityID());
    }
}

