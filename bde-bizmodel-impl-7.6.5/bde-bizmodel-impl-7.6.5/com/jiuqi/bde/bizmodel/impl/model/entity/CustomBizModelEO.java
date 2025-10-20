/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.entity;

import com.jiuqi.bde.bizmodel.impl.model.entity.BizModelEO;

public class CustomBizModelEO
extends BizModelEO {
    private static final long serialVersionUID = 3552038571438867508L;
    public static final String TABLENAME = "BDE_BIZMODEL_CUSTOM";
    private String fetchTable;
    private String fetchFields;
    private String fixedCondition;
    private String customCondition;
    private String applyScope;
    private String dataSourceCode;
    private String queryTemplateId;

    public String getFetchTable() {
        return this.fetchTable;
    }

    public void setFetchTable(String fetchTable) {
        this.fetchTable = fetchTable;
    }

    public String getFetchFields() {
        return this.fetchFields;
    }

    public void setFetchFields(String fetchFields) {
        this.fetchFields = fetchFields;
    }

    public String getFixedCondition() {
        return this.fixedCondition;
    }

    public void setFixedCondition(String fixedCondition) {
        this.fixedCondition = fixedCondition;
    }

    public String getCustomCondition() {
        return this.customCondition;
    }

    public void setCustomCondition(String customCondition) {
        this.customCondition = customCondition;
    }

    public String getApplyScope() {
        return this.applyScope;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getQueryTemplateId() {
        return this.queryTemplateId;
    }

    public void setQueryTemplateId(String queryTemplateId) {
        this.queryTemplateId = queryTemplateId;
    }

    @Override
    public String toString() {
        return "CustomBizModelEO [fetchTable=" + this.fetchTable + ", fetchFields=" + this.fetchFields + ", fixedCondition=" + this.fixedCondition + ", customCondition=" + this.customCondition + ", applyScope=" + this.applyScope + "]";
    }
}

