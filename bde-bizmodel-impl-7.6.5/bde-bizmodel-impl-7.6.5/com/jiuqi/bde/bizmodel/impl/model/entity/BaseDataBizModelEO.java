/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.entity;

import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelEO;

public class BaseDataBizModelEO
extends BizModelEO {
    private static final long serialVersionUID = -5279452240081992520L;
    public static final String TABLENAME = "BDE_BIZMODEL_BASEDATA";
    private String baseDataDefine;
    private String fetchFields;

    public String getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(String baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public String getFetchFields() {
        return this.fetchFields;
    }

    public void setFetchFields(String fetchFields) {
        this.fetchFields = fetchFields;
    }

    @Override
    public String toString() {
        return "BaseDataBizModelDTO{baseDataDefine='" + this.baseDataDefine + '\'' + ", fetchFields='" + this.fetchFields + '\'' + '}';
    }
}

