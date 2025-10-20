/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcBaseExecutorContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public class GcReportSimpleExecutorContext
extends GcBaseExecutorContext {
    private DefaultTableEntity data;
    private List<DefaultTableEntity> items;
    private Double phsValue;
    private String schemeId;
    private DimensionValueSet dimensionValueSet;

    public DefaultTableEntity getData() {
        return this.data;
    }

    public void setData(DefaultTableEntity data) {
        this.data = data;
    }

    public List<DefaultTableEntity> getItems() {
        return this.items;
    }

    public void setItems(List<DefaultTableEntity> items) {
        this.items = items;
    }

    public Double getPhsValue() {
        return this.phsValue;
    }

    public void setPhsValue(Double phsValue) {
        this.phsValue = phsValue;
    }

    public void reset() {
        this.phsValue = null;
        this.data = null;
        this.items = null;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public void setDimensionValueSet(DimensionValueSet dset) {
        this.dimensionValueSet = dset;
        this.setVarDimensionValueSet(dset);
    }

    public void setDimensionValueSet(String orgCode, String periodStr, String currencyCode, String orgTypeCode, String adjustCode, String taskId) {
        DimensionValueSet dset;
        this.dimensionValueSet = dset = DimensionUtils.generateDimSet(orgCode, periodStr, currencyCode, orgTypeCode, adjustCode, taskId);
        this.setVarDimensionValueSet(dset);
    }

    public QueryContext generateQueryContext() throws ParseException {
        QueryContext queryContext = new QueryContext((ExecutorContext)this, null);
        queryContext.setCurrentMasterKey(this.dimensionValueSet);
        return queryContext;
    }
}

