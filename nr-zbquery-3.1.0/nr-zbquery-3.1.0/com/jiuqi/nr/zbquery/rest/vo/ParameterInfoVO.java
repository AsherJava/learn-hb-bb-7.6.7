/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryObject;

public class ParameterInfoVO {
    private ConditionField conditionField;
    private QueryObject queryObject;
    private QueryDimension queryDimension;
    private String reportScheme;

    public ConditionField getConditionField() {
        return this.conditionField;
    }

    public void setConditionField(ConditionField conditionField) {
        this.conditionField = conditionField;
    }

    public QueryObject getQueryObject() {
        return this.queryObject;
    }

    public void setQueryObject(QueryObject queryObject) {
        this.queryObject = queryObject;
    }

    public QueryDimension getQueryDimension() {
        return this.queryDimension;
    }

    public void setQueryDimension(QueryDimension queryDimension) {
        this.queryDimension = queryDimension;
    }

    public String getReportScheme() {
        return this.reportScheme;
    }

    public void setReportScheme(String reportScheme) {
        this.reportScheme = reportScheme;
    }
}

