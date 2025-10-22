/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 */
package com.jiuqi.nr.dataentity.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataEntityQueryParam
implements Serializable {
    private EntityViewDefine entityView;
    private ExecutorContext context;
    private boolean sorted;
    private String formSchemeKey;
    private boolean readAuth;
    private String entityIsolateCondition;
    private DimensionValueSet masterKeys;
    private Date queryVersionDate;
    private String rowFilter;
    private String expression;
    private boolean ignoreViewFiler;
    private Map<String, Boolean> orderAttribute = new HashMap<String, Boolean>();
    private boolean querySorted;
    private RangeQuery rangeQuery;

    public EntityViewDefine getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityViewDefine entityView) {
        this.entityView = entityView;
    }

    public boolean isReadAuth() {
        return this.readAuth;
    }

    public void setReadAuth(boolean readAuth) {
        this.readAuth = readAuth;
    }

    public String getEntityIsolateCondition() {
        return this.entityIsolateCondition;
    }

    public void setEntityIsolateCondition(String entityIsolateCondition) {
        this.entityIsolateCondition = entityIsolateCondition;
    }

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = masterKeys;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionDate;
    }

    public void setQueryVersionDate(Date queryVersionDate) {
        this.queryVersionDate = queryVersionDate;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isIgnoreViewFiler() {
        return this.ignoreViewFiler;
    }

    public void setIgnoreViewFiler(boolean ignoreViewFiler) {
        this.ignoreViewFiler = ignoreViewFiler;
    }

    public Map<String, Boolean> getOrderAttribute() {
        return this.orderAttribute;
    }

    public void setOrderAttribute(Map<String, Boolean> orderAttribute) {
        this.orderAttribute = orderAttribute;
    }

    public boolean isQuerySorted() {
        return this.querySorted;
    }

    public void setQuerySorted(boolean querySorted) {
        this.querySorted = querySorted;
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public RangeQuery getRangeQuery() {
        return this.rangeQuery;
    }

    public void setRangeQuery(RangeQuery rangeQuery) {
        this.rangeQuery = rangeQuery;
    }

    public ExecutorContext getContext() {
        return this.context;
    }

    public void setContext(ExecutorContext context) {
        this.context = context;
    }
}

