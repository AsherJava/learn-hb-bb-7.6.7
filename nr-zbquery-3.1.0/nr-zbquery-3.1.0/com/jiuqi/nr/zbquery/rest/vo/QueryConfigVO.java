/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;

public class QueryConfigVO {
    private String cacheId;
    private ZBQueryModel queryModel;
    private ConditionValues conditionValues;
    private PageInfo pageInfo;
    private String title;

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
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

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

