/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.common.Constant;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryInfo;
import com.jiuqi.nr.entity.engine.intf.IReadonlyTable;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadonlyTableImpl
implements IReadonlyTable {
    protected List<EntityRowImpl> dataRows = new ArrayList<EntityRowImpl>();
    protected DimensionValueSet masterKeys;
    protected DimensionSet rowDimensions;
    private Date queryVersionStartDate;
    private Date queryVersionDate;
    private EntityResultSet resultSet;
    protected final QueryInfo queryInfo;
    protected final QueryContext queryContext;

    public ReadonlyTableImpl(QueryContext queryContext, QueryInfo queryInfo) {
        this.masterKeys = queryInfo.getMasterKeys();
        this.queryVersionStartDate = Constant.DATE_VERSION_INVALID_VALUE;
        this.queryVersionDate = Constant.DATE_VERSION_FORALL;
        this.queryContext = queryContext;
        this.queryInfo = queryInfo;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.queryInfo.getTableRunInfo().getFieldsInfo();
    }

    public DimensionSet getMasterDimensions() {
        return this.masterKeys.getDimensionSet();
    }

    public DimensionSet getRowDimensions() {
        return this.rowDimensions;
    }

    public void setRowDimensions(DimensionSet dimensions) {
        this.rowDimensions = dimensions;
    }

    @Override
    public TableModelDefine getEntityTableDefine() {
        return this.queryInfo.getTableRunInfo().getTableModelDefine();
    }

    @Override
    public IEntityModel getEntityModel() {
        return this.queryInfo.getTableRunInfo().getEntityModel();
    }

    @Override
    public boolean isI18nAttribute(String attributeCode) {
        return false;
    }

    public EntityRowImpl addDataRow(DimensionValueSet rowKeys) {
        EntityRowImpl dataRowImpl = new EntityRowImpl(this, rowKeys, 0);
        this.dataRows.add(dataRowImpl);
        return dataRowImpl;
    }

    public Date getQueryVersionStartDate() {
        return this.queryVersionStartDate;
    }

    public void setQueryVersionStartDate(Date queryVersionStartDate) {
        this.queryVersionStartDate = queryVersionStartDate;
    }

    public Date getQueryVersionDate() {
        return this.queryVersionDate;
    }

    public void setQueryVersionDate(Date queryVersionDate) {
        this.queryVersionDate = queryVersionDate;
    }

    public String toString() {
        return "ReadonlyTableImpl [dataRows=" + this.dataRows + "]";
    }

    public List<EntityRowImpl> getAllDataRows() {
        return this.dataRows;
    }

    public void setAllDataRows(List<EntityRowImpl> dataRowImpls) {
        this.dataRows = new ArrayList<EntityRowImpl>(dataRowImpls);
    }

    public void reset() {
        this.dataRows.clear();
    }

    public void addDataRows(List<EntityRowImpl> allRows) {
        if (allRows == null || allRows.size() <= 0) {
            return;
        }
        this.dataRows.addAll(allRows);
    }

    public void setResultSet(EntityResultSet rs) {
        this.resultSet = rs;
    }

    public EntityResultSet getResultSet() {
        return this.resultSet;
    }
}

