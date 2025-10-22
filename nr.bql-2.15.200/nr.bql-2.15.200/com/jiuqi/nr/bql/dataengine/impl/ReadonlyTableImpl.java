/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.dataengine.IFieldsInfo;
import com.jiuqi.nr.bql.dataengine.IReadonlyTable;
import com.jiuqi.nr.bql.dataengine.impl.DataRowImpl;
import com.jiuqi.nr.bql.dataengine.impl.FieldsInfoImpl;
import com.jiuqi.nr.bql.dataengine.query.DataQueryBuilder;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nvwa.definition.common.AggrType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ReadonlyTableImpl
implements IReadonlyTable {
    protected QueryContext qContext;
    protected ArrayList<DataRowImpl> dataRows;
    public List<QueryField> queryfields;
    public FieldsInfoImpl fieldsInfoImpl;
    protected DimensionValueSet masterKeys;
    protected DimensionSet rowDimensions;
    private DataQueryBuilder dataQueryBuilder;
    protected HashMap<String, AggrType> uidGatherTypes;
    protected HashSet<Integer> noneGatherCols;
    protected IASTNode rowFilterNode;
    protected QueryParam queryParam;
    protected HashMap<Integer, ArrayList<Object>> colFilterValues;

    public ReadonlyTableImpl(QueryContext qCntext, DimensionValueSet masterKeys, int columnCount) {
        this.fieldsInfoImpl = new FieldsInfoImpl(columnCount);
        this.queryfields = new ArrayList<QueryField>();
        this.dataRows = new ArrayList();
        this.masterKeys = masterKeys;
        this.qContext = qCntext;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.fieldsInfoImpl;
    }

    @Override
    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    @Override
    public DimensionSet getMasterDimensions() {
        return this.masterKeys.getDimensionSet();
    }

    @Override
    public DimensionSet getRowDimensions() {
        return this.rowDimensions;
    }

    public void setRowDimensions(DimensionSet dimensions) {
        this.rowDimensions = dimensions;
    }

    @Override
    public int getCount() {
        return this.dataRows.size();
    }

    @Override
    public IDataRow getItem(int index) {
        return this.dataRows.get(index);
    }

    public DataRowImpl addDataRow(DimensionValueSet rowKeys) {
        Object[] rowDatas = new Object[this.queryfields.size()];
        DataRowImpl dataRowImpl = new DataRowImpl(this, rowKeys, rowDatas);
        this.dataRows.add(dataRowImpl);
        return dataRowImpl;
    }

    public String toString() {
        return "ReadonlyTableImpl [dataRows=" + this.dataRows + "]";
    }

    public void setDataQueryBuilder(DataQueryBuilder dataQueryBuilder) {
        this.dataQueryBuilder = dataQueryBuilder;
    }

    public DataQueryBuilder getDataQueryBuilder() {
        return this.dataQueryBuilder;
    }

    public QuerySqlBuilder getSqlBuilder() {
        return this.dataQueryBuilder.getSqlBuilder(null);
    }

    public List<DataRowImpl> getAllDataRows() {
        return this.dataRows;
    }

    public void setAllDataRows(List<DataRowImpl> dataRowImpls) {
        this.dataRows = new ArrayList<DataRowImpl>(dataRowImpls);
    }

    public QueryContext getContext() {
        return this.qContext;
    }

    public void setContext(QueryContext qContext) {
        this.qContext = qContext;
    }

    public void setGatherTypes(HashMap<String, AggrType> uidGatherTypes) {
        this.uidGatherTypes = uidGatherTypes;
    }

    public HashMap<String, AggrType> getGatherTypes() {
        return this.uidGatherTypes;
    }

    public void setNoneGatherCols(ArrayList<Integer> noneGatherCols) {
        this.noneGatherCols = new HashSet<Integer>(noneGatherCols);
    }

    public HashSet<Integer> getNoneGatherCols() {
        return this.noneGatherCols;
    }

    public void setRowFilterNode(IASTNode rowFilterNode) {
        this.rowFilterNode = rowFilterNode;
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    public HashMap<Integer, ArrayList<Object>> getColFilterValues() {
        return this.colFilterValues;
    }

    public void setColFilterValues(HashMap<Integer, ArrayList<Object>> colFilterValues) {
        this.colFilterValues = colFilterValues;
    }

    public void reset() {
        this.dataRows.clear();
    }

    public void addDataRows(List<DataRowImpl> allRows) {
        if (allRows == null || allRows.size() <= 0) {
            return;
        }
        this.dataRows.addAll(allRows);
    }
}

