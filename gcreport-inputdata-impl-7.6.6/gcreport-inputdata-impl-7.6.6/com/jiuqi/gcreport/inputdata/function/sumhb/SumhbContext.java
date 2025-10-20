/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.inputdata.function.sumhb;

import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class SumhbContext {
    QueryContext queryContext;
    FormSchemeDefine formSchemeDefine;
    FormDefine currFormDefine;
    String currRegionId;
    FieldDefine calcFieldDefine;
    String filter;

    public QueryContext getQueryContext() {
        return this.queryContext;
    }

    public void setQueryContext(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public FormDefine getCurrFormDefine() {
        return this.currFormDefine;
    }

    public void setCurrFormDefine(FormDefine currFormDefine) {
        this.currFormDefine = currFormDefine;
    }

    public String getCurrRegionId() {
        return this.currRegionId;
    }

    public void setCurrRegionId(String currRegionId) {
        this.currRegionId = currRegionId;
    }

    public FieldDefine getCalcFieldDefine() {
        return this.calcFieldDefine;
    }

    public void setCalcFieldDefine(FieldDefine calcFieldDefine) {
        this.calcFieldDefine = calcFieldDefine;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}

