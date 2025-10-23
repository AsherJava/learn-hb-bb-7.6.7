/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;

public class ExportConfigVO {
    private String cacheId;
    private String title;
    private ZBQueryModel queryModel;
    private ConditionValues conditionValues;

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZBQueryModel getQueryModel() {
        return this.queryModel;
    }

    public void setQueryModel(ZBQueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public ConditionValues getConditionValues() {
        return this.conditionValues;
    }

    public void setConditionValues(ConditionValues conditionValues) {
        this.conditionValues = conditionValues;
    }
}

