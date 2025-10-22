/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.FieldGatherType
 */
package com.jiuqi.np.dataengine.query.old;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.DefaultExpression;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.old.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.old.DataRowImpl;
import com.jiuqi.np.dataengine.query.old.QuerySqlBuilder;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.setting.IDataValidateProvider;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldGatherType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ReadonlyTableImpl
implements IReadonlyTable {
    protected QueryContext qContext;
    protected DefinitionsCache cache;
    public ArrayList<DataRowImpl> dataRows;
    public List<QueryField> queryfields;
    public FieldsInfoImpl fieldsInfoImpl;
    public FieldsInfoImpl systemFieldsInfo;
    protected DimensionValueSet masterKeys;
    protected DimensionSet rowDimensions;
    protected HashMap<String, DataRowImpl> rowKeySearch;
    private Date queryVersionStartDate;
    private Date queryVersionDate;
    private DataQueryBuilder dataQueryBuilder;
    private int totalCount;
    protected HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId;
    public int groupingFlagColIndex = -1;
    protected boolean designTimeData;
    protected HashMap<Integer, IExpression> calcExprs;
    protected HashMap<Integer, IExpression> defExprs;
    protected HashMap<Integer, IExpression> validExprs;
    protected List<DefaultExpression> dimDefaultExprs;
    protected HashMap<Integer, String> calcFormulas;
    protected int recKeyIndex = -1;
    protected int recType = -1;
    protected HashMap<String, FieldGatherType> uidGatherTypes;
    protected HashSet<Integer> noneGatherCols;
    protected IDataValidateProvider validateProvider;
    protected IASTNode rowFilterNode;
    protected QueryParam queryParam;
    protected HashMap<String, IEntityTable> refTables;
    protected HashMap<Integer, ArrayList<Object>> colFilterValues;
    private HashMap<Integer, HashMap<String, List<IDataRow>>> fuzzyMaps;

    public ReadonlyTableImpl(QueryContext qCntext, DimensionValueSet masterKeys, int columnCount) {
        this.fieldsInfoImpl = new FieldsInfoImpl(columnCount);
        this.systemFieldsInfo = new FieldsInfoImpl(columnCount);
        this.queryfields = new ArrayList<QueryField>();
        this.dataRows = new ArrayList();
        this.fuzzyMaps = new HashMap(4);
        this.masterKeys = masterKeys;
        this.qContext = qCntext;
        this.queryVersionStartDate = Consts.DATE_VERSION_INVALID_VALUE;
        this.queryVersionDate = Consts.DATE_VERSION_FOR_ALL;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.fieldsInfoImpl;
    }

    public IFieldsInfo getSystemFields() {
        return this.systemFieldsInfo;
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
    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public IDataRow getItem(int index) {
        return this.dataRows.get(index);
    }

    @Override
    public IDataRow findRow(DimensionValueSet rowKeys) {
        IDataRow dataRow;
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        if ((dataRow = (IDataRow)this.rowKeySearch.get(rowKeys.toString())) != null) {
            return dataRow;
        }
        DimensionValueSet copyValue = new DimensionValueSet(rowKeys);
        if (copyValue.hasValue("VERSIONID")) {
            copyValue.clearValue("VERSIONID");
        } else {
            copyValue.setValue("VERSIONID", "00000000-0000-0000-0000-000000000000");
        }
        return this.rowKeySearch.get(copyValue.toString());
    }

    public boolean getIsReadOnly() {
        return true;
    }

    public DataRowImpl createDataRowInstance(DimensionValueSet rowKeys, ResultSet resultSet, int rowIndex) throws DataTypeException {
        ArrayList<Object> rowDatas = null;
        return new DataRowImpl(this, rowKeys, rowDatas);
    }

    public DataRowImpl addDataRow(DimensionValueSet rowKeys) {
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        DataRowImpl dataRowImpl = new DataRowImpl(this, rowKeys, rowDatas);
        this.dataRows.add(dataRowImpl);
        this.rowKeySearch.put(rowKeys.toString(), dataRowImpl);
        return dataRowImpl;
    }

    public void saveTableRow(DimensionValueSet currentKeys) {
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

    protected void addedRowToCache(DataRowImpl dataRow) {
        if (this.rowKeySearch != null) {
            this.rowKeySearch.put(dataRow.getRowKeys().toString(), dataRow);
        }
    }

    protected void buildRowKeySearch() {
        HashMap<String, DataRowImpl> rowKeyMap = new HashMap<String, DataRowImpl>();
        for (DataRowImpl dataRowImpl : this.dataRows) {
            if (dataRowImpl.groupingFlag >= 0) continue;
            rowKeyMap.put(dataRowImpl.getRowKeys().toString(), dataRowImpl);
        }
        this.rowKeySearch = rowKeyMap;
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

    public void setDefinitionsCache(DefinitionsCache cache) {
        this.cache = cache;
    }

    public void setGrpByColsEffectiveInGroupingId(HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId) {
        this.grpByColsEffectiveInGroupingId = grpByColsEffectiveInGroupingId;
    }

    public HashMap<Integer, BitMaskAndNullValue> getGrpByColsEffectiveInGroupingId() {
        return this.grpByColsEffectiveInGroupingId;
    }

    public List<DataRowImpl> getAllDataRows() {
        return this.dataRows;
    }

    public void setAllDataRows(List<DataRowImpl> dataRowImpls) {
        this.dataRows = new ArrayList<DataRowImpl>(dataRowImpls);
    }

    public boolean getDesignTimeData() {
        return this.designTimeData;
    }

    public void setDesignTimeData(boolean designTimeData) {
        this.designTimeData = designTimeData;
    }

    public int getRecType() {
        return this.recType;
    }

    public void setRecType(int recType) {
        this.recType = recType;
    }

    public int getRecKeyIndex() {
        return this.recKeyIndex;
    }

    public void setRecKeyIndex(int recKeyIndex) {
        this.recKeyIndex = recKeyIndex;
    }

    public QueryContext getContext() {
        return this.qContext;
    }

    public void setContext(QueryContext qContext) {
        this.qContext = qContext;
    }

    public void setGatherTypes(HashMap<String, FieldGatherType> uidGatherTypes) {
        this.uidGatherTypes = uidGatherTypes;
    }

    public HashMap<String, FieldGatherType> getGatherTypes() {
        return this.uidGatherTypes;
    }

    public void setNoneGatherCols(ArrayList<Integer> noneGatherCols) {
        this.noneGatherCols = new HashSet<Integer>(noneGatherCols);
    }

    public HashSet<Integer> getNoneGatherCols() {
        return this.noneGatherCols;
    }

    public void setDataValidateProvider(IDataValidateProvider dataValidateProvider) {
        this.validateProvider = dataValidateProvider;
    }

    public void setRowFilterNode(IASTNode rowFilterNode) {
        this.rowFilterNode = rowFilterNode;
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public List<IDataRow> findFuzzyRows(DimensionValueSet rowKeys) {
        List<IDataRow> resultRows = new ArrayList<IDataRow>();
        if (rowKeys == null || rowKeys.size() <= 0 || this.dataRows.isEmpty()) {
            return resultRows;
        }
        int keySize = rowKeys.size();
        if (!this.fuzzyMaps.containsKey(keySize)) {
            this.initFuzzyMap(keySize, rowKeys);
        }
        String rowKeyData = rowKeys.toString();
        HashMap<String, List<IDataRow>> rowMap = this.fuzzyMaps.get(keySize);
        if (rowMap.containsKey(rowKeyData)) {
            resultRows = rowMap.get(rowKeyData);
        }
        return resultRows;
    }

    private void initFuzzyMap(int keySize, DimensionValueSet rowKeys) {
        HashMap rowMap = new HashMap();
        DimensionSet dimensionSet = rowKeys.getDimensionSet();
        for (IDataRow iDataRow : this.dataRows) {
            List<IDataRow> subRows;
            if (iDataRow.getGroupingFlag() >= 0) continue;
            DimensionValueSet subKeys = new DimensionValueSet();
            for (int index = 0; index < dimensionSet.size(); ++index) {
                String dimName = dimensionSet.get(index);
                Object dimValue = iDataRow.getRowKeys().getValue(dimName);
                subKeys.setValue(dimName, dimValue);
            }
            String subKeyData = subKeys.toString();
            if (!rowMap.containsKey(subKeyData)) {
                subRows = new ArrayList();
                rowMap.put(subKeyData, subRows);
            } else {
                subRows = (List)rowMap.get(subKeyData);
            }
            subRows.add(iDataRow);
        }
        this.fuzzyMaps.put(keySize, rowMap);
    }

    public HashMap<Integer, ArrayList<Object>> getColFilterValues() {
        return this.colFilterValues;
    }

    public void setColFilterValues(HashMap<Integer, ArrayList<Object>> colFilterValues) {
        this.colFilterValues = colFilterValues;
    }

    public void reset() {
        this.dataRows.clear();
        this.rowKeySearch = null;
    }

    public void addDataRows(List<DataRowImpl> allRows) {
        if (allRows == null || allRows.size() <= 0) {
            return;
        }
        this.rowKeySearch = null;
        this.dataRows.addAll(allRows);
    }
}

