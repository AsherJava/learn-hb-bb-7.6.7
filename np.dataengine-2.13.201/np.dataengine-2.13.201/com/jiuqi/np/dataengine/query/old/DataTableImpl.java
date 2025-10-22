/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.query.old;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DefaultExpression;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.RowState;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.old.DataRowImpl;
import com.jiuqi.np.dataengine.query.old.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.reader.DataRowReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTableImpl
extends ReadonlyTableImpl
implements IDataTable {
    private static final Logger logger = LoggerFactory.getLogger(DataTableImpl.class);
    protected ArrayList<DataRowImpl> emptyDataRows = new ArrayList();
    private final boolean useDnaSql;
    private IMonitor monitor;
    private boolean needCheckKeys;
    protected boolean onlyUpdate = false;
    private boolean ignoreEvent = false;
    private boolean onlySaveData = false;
    private HashMap<String, ColumnModelDefine> dimFields = null;
    private boolean initContext = false;
    private final List<InsertRowEvent> insertRowEvents = null;
    private final List<UpdateRowEvent> updateRowEvents = null;
    private final List<DeleteRowEvent> deleteRowEvents = null;
    private final List<DeleteAllRowEvent> deleteAllRowEvents = null;
    private final Set<String> currentPeriodSet = new HashSet<String>();
    private boolean ignoreResetCache = false;

    public DataTableImpl(QueryContext qContext, DimensionValueSet masterKeys, int columnCount) {
        super(qContext, masterKeys, columnCount);
        this.useDnaSql = qContext != null && qContext.getExeContext().isUseDnaSql();
    }

    @Override
    public boolean getIsReadOnly() {
        return false;
    }

    @Override
    public IDataRow appendRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        this.checkNewRowKeys(rowKeys);
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        DataRowImpl dataRowImpl = new DataRowImpl(this, rowKeys, rowDatas);
        dataRowImpl.setRowState(RowState.ADD);
        this.initDataRow(dataRowImpl, true);
        if (rowKeys.isAllNull()) {
            dataRowImpl.rowKeysEmpty = true;
        }
        this.dataRows.add(dataRowImpl);
        this.addedRowToCache(dataRowImpl);
        return dataRowImpl;
    }

    @Override
    public IDataRow newEmptyRow(boolean initDefaultValues) {
        DimensionSet rowDimensions = this.getRowDimensions();
        DimensionValueSet rowKeys = rowDimensions == null ? new DimensionValueSet() : new DimensionValueSet(this.getRowDimensions());
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        DataRowImpl dataRowImpl = new DataRowImpl(this, rowKeys, rowDatas);
        dataRowImpl.setRowState(RowState.ADD);
        dataRowImpl.notInitialized = true;
        dataRowImpl.rowKeysEmpty = true;
        this.emptyDataRows.add(dataRowImpl);
        this.initDataRow(dataRowImpl, initDefaultValues);
        return dataRowImpl;
    }

    @Override
    public int deleteRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        DataRowImpl dataRowImpl;
        if (this.getMasterKeys().size() == 0 && rowKeys.isAllNull()) {
            throw new IncorrectQueryException("\u4e0d\u652f\u6301\u5220\u9664\u6240\u6709\u8bb0\u5f55");
        }
        if (!rowKeys.hasAnyNull() && (dataRowImpl = (DataRowImpl)this.findRow(rowKeys)) != null) {
            this.deleteRow(dataRowImpl);
            return 1;
        }
        int deleteCount = 0;
        for (DataRowImpl dataRowImpl2 : this.dataRows) {
            if (!rowKeys.isSubsetOf(dataRowImpl2.getRowKeys())) continue;
            this.deleteRow(dataRowImpl2);
            ++deleteCount;
        }
        return deleteCount;
    }

    @Override
    public void deleteByIndex(int rowIndex) {
        DataRowImpl dataRowImpl = (DataRowImpl)this.dataRows.get(rowIndex);
        if (dataRowImpl == null) {
            return;
        }
        this.deleteRow(dataRowImpl);
    }

    protected void deleteRow(DataRowImpl deleteRow) {
        deleteRow.delete();
        String rowKeyData = deleteRow.getRowKeys().toString();
        if (this.rowKeySearch != null) {
            this.rowKeySearch.remove(rowKeyData);
        }
    }

    @Override
    public void deleteAll() {
        for (DataRowImpl dataRowImpl : this.dataRows) {
            this.deleteRow(dataRowImpl);
        }
    }

    @Override
    public boolean commitChanges(boolean abandonDataTable) throws Exception {
        return this.commitChanges(false, abandonDataTable, true);
    }

    @Override
    public boolean commitChanges(boolean ignoreLogicCheck, boolean abandonDataTable) throws Exception {
        return this.commitChanges(ignoreLogicCheck, abandonDataTable, true);
    }

    @Override
    public boolean commitChanges(boolean ignoreLogicCheck, boolean abandonDataTable, boolean cascadeDeletion) throws Exception {
        if (this.qContext != null) {
            this.setCalcRowContext(this.qContext);
        }
        ArrayList<DataRowImpl> insertRows = new ArrayList<DataRowImpl>();
        ArrayList<DataRowImpl> deleteRows = new ArrayList<DataRowImpl>();
        ArrayList<DataRowImpl> updateRows = new ArrayList<DataRowImpl>();
        ArrayList<DataRowImpl> revokeRows = new ArrayList<DataRowImpl>();
        boolean hasChange = false;
        for (DataRowImpl dataRowImpl : this.dataRows) {
            switch (dataRowImpl.rowState) {
                case NONE: {
                    break;
                }
                case ADD: {
                    hasChange = true;
                    dataRowImpl.validateAll(ignoreLogicCheck);
                    insertRows.add(dataRowImpl);
                    break;
                }
                case MODIFIED: {
                    hasChange = true;
                    dataRowImpl.validateAll(ignoreLogicCheck);
                    updateRows.add(dataRowImpl);
                    break;
                }
                case DELETE: {
                    hasChange = true;
                    deleteRows.add(dataRowImpl);
                    break;
                }
                case REVOKE: {
                    hasChange = true;
                    revokeRows.add(dataRowImpl);
                    break;
                }
            }
        }
        if (!hasChange) {
            return false;
        }
        this.emptyDataRows.clear();
        this.wholeTableCheck(insertRows, updateRows, deleteRows);
        this.commitChanges(this.getMasterKeys(), false, insertRows, updateRows, deleteRows, revokeRows, this.getQueryVersionStartDate(), this.getQueryVersionDate(), cascadeDeletion);
        if (abandonDataTable) {
            return true;
        }
        this.refreshTableRows(insertRows, updateRows, deleteRows);
        return true;
    }

    public void commitChanges(DimensionValueSet masterKeys, boolean deleteAllRows, List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows, List<DataRowImpl> revokeRows, Date queryStartVersionDate, Date queryVersionDate, boolean cascadeDeletion) throws Exception {
    }

    public void setCalcRowContext(QueryContext queryContext) throws ParseException {
        if (this.initContext) {
            return;
        }
        this.initContext = true;
        IQueryFieldDataReader reader = this.getDataReader(queryContext);
        reader.reset();
        Integer index = 0;
        while (index < this.queryfields.size()) {
            QueryField queryField = (QueryField)this.queryfields.get(index);
            if (queryField != null && reader.findIndex(queryField) < 0) {
                reader.putIndex(queryContext.getExeContext().getCache().getDataModelDefinitionsCache(), queryField, index);
            }
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
    }

    protected void refreshTableRows(List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows) {
        for (DataRowImpl dataRowImpl : updateRows) {
            for (Map.Entry<Integer, Object> valuePair : dataRowImpl.modifiedDatas.entrySet()) {
                dataRowImpl.rowDatas.set(valuePair.getKey(), valuePair.getValue());
            }
            dataRowImpl.modifiedDatas.clear();
            dataRowImpl.setRowState(RowState.NONE);
        }
        for (DataRowImpl dataRowImpl : insertRows) {
            if (dataRowImpl.rowDatas.size() <= 0) {
                for (int i = 0; i < this.queryfields.size(); ++i) {
                    dataRowImpl.rowDatas.add(null);
                }
            }
            for (Map.Entry<Integer, Object> valuePair : dataRowImpl.modifiedDatas.entrySet()) {
                dataRowImpl.rowDatas.set(valuePair.getKey(), valuePair.getValue());
            }
            dataRowImpl.modifiedDatas.clear();
            dataRowImpl.setRowState(RowState.NONE);
        }
        for (DataRowImpl dataRowImpl : deleteRows) {
            this.dataRows.remove(dataRowImpl);
        }
    }

    protected void wholeTableCheck(List<DataRowImpl> insertRows, List<DataRowImpl> updateRows, List<DataRowImpl> deleteRows) {
    }

    public void delayInitRow(DimensionValueSet rowKeys, DataRowImpl dataRowImpl) {
        this.emptyDataRows.remove(dataRowImpl);
        this.dataRows.add(dataRowImpl);
        this.addedRowToCache(dataRowImpl);
    }

    public void recalculate(DataRowImpl dataRowImpl) {
        try {
            if (this.calcExprs == null) {
                this.calcExprs = this.getCalcExprs(this.qContext);
            }
            if (this.calcExprs.size() <= 0) {
                return;
            }
            IQueryFieldDataReader dataReader = this.getDataReader(this.qContext);
            dataReader.setDataSet(dataRowImpl);
            for (Map.Entry calcExpr : this.calcExprs.entrySet()) {
                IExpression calcExp = (IExpression)calcExpr.getValue();
                QueryFields queryFields = ExpressionUtils.getAllQueryFields((IASTNode)calcExp);
                boolean rowCalc = true;
                for (QueryField queryField : queryFields) {
                    int fieldIndex = dataReader.findIndex(queryField);
                    if (fieldIndex >= 0) continue;
                    rowCalc = false;
                    break;
                }
                if (rowCalc) {
                    Object calcResult = ((IExpression)calcExpr.getValue()).evaluate((IContext)this.qContext);
                    dataRowImpl.setValue((Integer)calcExpr.getKey(), calcResult);
                    continue;
                }
                this.evalCalcExpression((String)this.calcFormulas.get(calcExpr.getKey()), (Integer)calcExpr.getKey(), this.qContext, dataRowImpl);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void evalCalcExpression(String expression, Integer fieldIndex, QueryContext qContext, DataRowImpl dataRowImpl) throws ExpressionException, DataTypeException {
        this.queryParam.setSameConnection(true);
        try {
            ExpressionEvaluatorImpl evaluatorImpl = new ExpressionEvaluatorImpl(this.queryParam);
            AbstractData calcData = evaluatorImpl.eval(expression, qContext.getExeContext(), dataRowImpl.getRowKeys());
            dataRowImpl.setValue(fieldIndex, calcData.getAsObject());
        }
        finally {
            this.queryParam.setSameConnection(false);
        }
    }

    private IQueryFieldDataReader getDataReader(QueryContext queryContext) {
        IQueryFieldDataReader dataReader = queryContext.getDataReader();
        if (dataReader == null || !(dataReader instanceof DataRowReader)) {
            dataReader = new DataRowReader(queryContext);
            queryContext.setDataReader(dataReader);
        }
        return dataReader;
    }

    public void verifyDataRow(DataRowImpl dataRowImpl) throws Exception {
        if (this.validExprs == null) {
            this.validExprs = this.getValidExprs(this.qContext);
        }
        if (this.validExprs.size() <= 0) {
            return;
        }
        this.getDataReader(this.qContext).setDataSet(dataRowImpl);
        for (Map.Entry validExpr : this.validExprs.entrySet()) {
            Object validResult = ((IExpression)validExpr.getValue()).evaluate((IContext)this.qContext);
            if (!(validResult instanceof Boolean) || ((Boolean)validResult).booleanValue()) continue;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine((Integer)validExpr.getKey());
            String filedValue = dataRowImpl.getAsString((Integer)validExpr.getKey());
            throw new Exception(String.format("\u6307\u6807%s\u7684\u503c%s\u4e0d\u7b26\u5408\u6821\u9a8c\u89c4\u5219%s", fieldDefine.getTitle(), filedValue, null));
        }
    }

    protected void initDataRow(DataRowImpl dataRowImpl, boolean initDefaultValues) {
        try {
            dataRowImpl.buildRow();
            this.initDefaultDimValues(dataRowImpl);
            if (!initDefaultValues) {
                return;
            }
            if (this.defExprs == null) {
                this.defExprs = this.getDefExprs(this.qContext);
            }
            if (this.defExprs.size() <= 0) {
                return;
            }
            this.getDataReader(this.qContext).setDataSet(dataRowImpl);
            for (Map.Entry defExpr : this.defExprs.entrySet()) {
                Object calcResult = ((IExpression)defExpr.getValue()).evaluate((IContext)this.qContext);
                dataRowImpl.directSet((Integer)defExpr.getKey(), calcResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDefaultDimValues(DataRowImpl dataRowImpl) {
        try {
            if (this.dimDefaultExprs == null) {
                this.dimDefaultExprs = this.getDimDefaultExprs(this.qContext);
            }
            if (this.dimDefaultExprs.size() <= 0) {
                return;
            }
            this.getDataReader(this.qContext).setDataSet(dataRowImpl);
            for (DefaultExpression defaultExpression : this.dimDefaultExprs) {
                boolean hasValue = dataRowImpl.hasKeyValue(defaultExpression.getFieldDefine());
                if (hasValue) continue;
                Object calcResult = defaultExpression.getExpression().evaluate((IContext)this.qContext);
                dataRowImpl.setKeyValue(defaultExpression.getFieldDefine(), calcResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void checkNewRowKeys(DimensionValueSet rowKeys) throws IncorrectQueryException {
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        if (this.rowKeySearch.containsKey(rowKeys.toString())) {
            ArrayList<DimensionValueSet> rowKeysArray = new ArrayList<DimensionValueSet>();
            rowKeysArray.add(rowKeys);
            throw new DuplicateRowKeysException(rowKeysArray);
        }
    }

    private HashMap<Integer, IExpression> getCalcExprs(QueryContext qContext) {
        if (this.calcFormulas == null) {
            this.calcFormulas = new HashMap();
        }
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<Integer, IExpression> calcExprs = new HashMap<Integer, IExpression>();
        for (int index = 0; index < this.fieldsInfoImpl.getFieldCount(); ++index) {
            ColumnModelDefine column;
            IExpression expression;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(index);
            if (fieldDefine == null || (expression = dataDefinitionsCache.getFieldCalculation(qContext, column = dataDefinitionsCache.getColumnModel(fieldDefine))) == null) continue;
            calcExprs.put(index, expression);
            this.calcFormulas.put(index, null);
        }
        return calcExprs;
    }

    private HashMap<Integer, IExpression> getValidExprs(QueryContext qContext) {
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<Integer, IExpression> validExprs = new HashMap<Integer, IExpression>();
        for (int index = 0; index < this.fieldsInfoImpl.getFieldCount(); ++index) {
            ColumnModelDefine column;
            IExpression expression;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(index);
            if (fieldDefine == null || (expression = dataDefinitionsCache.getFieldVerification(qContext, column = dataDefinitionsCache.getColumnModel(fieldDefine))) == null) continue;
            validExprs.put(index, expression);
        }
        return validExprs;
    }

    private HashMap<Integer, IExpression> getDefExprs(QueryContext qContext) {
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<Integer, IExpression> defExprs = new HashMap<Integer, IExpression>();
        for (int index = 0; index < this.fieldsInfoImpl.getFieldCount(); ++index) {
            IExpression expression;
            ColumnModelDefine field;
            TableModelDefine tableModelDefine;
            TableModelRunInfo tableRunInfo;
            boolean isKeyField;
            FieldDefine fieldDefine = this.fieldsInfoImpl.getFieldDefine(index);
            if (fieldDefine == null || StringUtils.isEmpty((String)fieldDefine.getDefaultValue()) || (isKeyField = (tableRunInfo = dataDefinitionsCache.getTableInfo((tableModelDefine = dataDefinitionsCache.getTableModel(field = dataDefinitionsCache.getColumnModel(fieldDefine))).getName())).isKeyField(field.getCode())) || (expression = dataDefinitionsCache.getFieldDefaultValue(qContext, fieldDefine)) == null) continue;
            defExprs.put(index, expression);
        }
        return defExprs;
    }

    private List<DefaultExpression> getDimDefaultExprs(QueryContext qContext) {
        ArrayList<DefaultExpression> defExprs = new ArrayList<DefaultExpression>();
        QueryField queryField = this.getQueryField();
        if (queryField == null) {
            return defExprs;
        }
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(queryField.getTableName());
        if (tableRunInfo == null) {
            return defExprs;
        }
        List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
        for (int index = 0; index < dimFields.size(); ++index) {
            IExpression expression;
            ColumnModelDefine fieldDefine = dimFields.get(index);
            FieldDefine field = dataDefinitionsCache.getFieldDefine(fieldDefine);
            if (fieldDefine == null || StringUtils.isEmpty((String)field.getDefaultValue()) || (expression = dataDefinitionsCache.getFieldDefaultValue(qContext, field)) == null) continue;
            DefaultExpression defaultExpression = new DefaultExpression(field, expression);
            defExprs.add(defaultExpression);
        }
        return defExprs;
    }

    private QueryField getQueryField() {
        if (this.queryfields == null || this.queryfields.size() <= 0) {
            return null;
        }
        for (int index = 0; index < this.queryfields.size(); ++index) {
            QueryField queryField = (QueryField)this.queryfields.get(index);
            if (queryField == null) continue;
            return queryField;
        }
        return null;
    }

    @Override
    public void setDataChangeMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    public void dataValidate(DataRowImpl dataRowImpl) throws DataValidateException {
        boolean resultValue;
        if (this.validateProvider != null && !(resultValue = this.validateProvider.checkRow(dataRowImpl))) {
            throw new DataValidateException("\u6570\u636e\u884c\u6821\u9a8c\u4e0d\u901a\u8fc7\uff0c\u65e0\u6cd5\u63d0\u4ea4\u6570\u636e\u3002");
        }
    }

    @Override
    public void needCheckDuplicateKeys(boolean needCheckKeys) {
        this.needCheckKeys = needCheckKeys;
    }

    @Override
    public IDataRow revokeRow(DimensionValueSet rowKeys) {
        DataRowImpl dataRowImpl = (DataRowImpl)this.findRow(rowKeys);
        if (dataRowImpl == null) {
            return null;
        }
        dataRowImpl.setRowState(RowState.REVOKE);
        return dataRowImpl;
    }

    @Override
    public void setIgnoreEvent(boolean value) {
        this.ignoreEvent = value;
    }

    public boolean isIgnoreEvent() {
        return this.ignoreEvent;
    }

    @Override
    public void setOnlySaveData(boolean value) {
        this.onlySaveData = value;
    }

    public void resetRowKeys(DataRowImpl dataRowImpl) {
        if (this.dimFields == null) {
            this.dimFields = this.getDimensionFields();
        }
        if (this.dimFields.isEmpty()) {
            return;
        }
        DimensionValueSet rowKeys = new DimensionValueSet();
        DimensionSet dimensionSet = this.getRowDimensions();
        int count = dimensionSet.size();
        if (count <= 0) {
            dimensionSet = new DimensionSet();
            for (Map.Entry<String, ColumnModelDefine> dimField : this.dimFields.entrySet()) {
                dimensionSet.addDimension(dimField.getKey());
            }
            count = dimensionSet.size();
        }
        boolean hasNull = false;
        for (int index = 0; index < count; ++index) {
            String dimension = dimensionSet.get(index);
            if (this.dimFields.containsKey(dimension)) {
                ColumnModelDefine fieldDefine = this.dimFields.get(dimension);
                FieldDefine column = this.cache.getDataModelDefinitionsCache().getFieldDefine(fieldDefine);
                int fieldIndex = this.fieldsInfoImpl.indexOf(column);
                if (fieldIndex < 0) {
                    if (dataRowImpl.getRowKeys().hasValue(dimension)) {
                        Object value = dataRowImpl.getRowKeys().getValue(dimension);
                        if (value == null) {
                            hasNull = true;
                            break;
                        }
                        rowKeys.setValue(dimension, value);
                        continue;
                    }
                    hasNull = true;
                    break;
                }
                Object dimValue = dataRowImpl.internalGetValue(fieldIndex);
                if (dimValue == null) {
                    hasNull = true;
                    break;
                }
                rowKeys.setValue(dimension, dimValue);
                continue;
            }
            hasNull = true;
            break;
        }
        if (hasNull) {
            return;
        }
        dataRowImpl.setRowKeys(rowKeys);
    }

    private HashMap<String, ColumnModelDefine> getDimensionFields() {
        HashMap<String, ColumnModelDefine> dimMap = new HashMap<String, ColumnModelDefine>();
        QueryField queryField = this.getQueryField();
        if (queryField == null) {
            return dimMap;
        }
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(queryField.getTableName());
        if (tableRunInfo == null) {
            return dimMap;
        }
        List<ColumnModelDefine> dimFields = tableRunInfo.getDimFields();
        for (ColumnModelDefine fieldDefine : dimFields) {
            String dimension = tableRunInfo.getDimensionName(fieldDefine.getCode());
            dimMap.put(dimension, fieldDefine);
        }
        return dimMap;
    }

    public boolean validataDataRow(DataRowImpl dataRowImpl, List<IParsedExpression> expressions) throws DataValidateException {
        if (expressions.size() <= 0) {
            return true;
        }
        try {
            this.setCalcRowContext(this.qContext);
        }
        catch (ParseException ex) {
            logger.error("\u6570\u636e\u884c\u516c\u5f0f\u6267\u884c\u73af\u5883\u521d\u59cb\u5316\u5931\u8d25", ex);
        }
        this.getDataReader(this.qContext).setDataSet(dataRowImpl);
        boolean resultValue = true;
        ArrayList<IParsedExpression> errorExps = new ArrayList<IParsedExpression>();
        for (IParsedExpression iExpression : expressions) {
            try {
                Object validResult = iExpression.getRealExpression().evaluate((IContext)this.qContext);
                if (!(validResult instanceof Boolean) || ((Boolean)validResult).booleanValue()) continue;
                resultValue = false;
                errorExps.add(iExpression);
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u884c\uff1a".concat(dataRowImpl.getRowKeys().toString()).concat("\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u51fa\u9519\uff0c\u5177\u4f53\u516c\u5f0f\u4e3a\uff1a").concat(iExpression.toString()), e);
            }
        }
        if (!resultValue) {
            DataValidateException exception = new DataValidateException("\u5f53\u524d\u6570\u636e\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u4e0d\u901a\u8fc7\u3002");
            throw exception;
        }
        return true;
    }

    public boolean judge(DataRowImpl dataRowImpl, IExpression expression, QueryContext context) {
        try {
            this.setCalcRowContext(this.qContext);
        }
        catch (ParseException ex) {
            logger.error("\u6570\u636e\u884c\u516c\u5f0f\u6267\u884c\u73af\u5883\u521d\u59cb\u5316\u5931\u8d25", ex);
            return true;
        }
        this.getDataReader(this.qContext).setDataSet(dataRowImpl);
        boolean result = true;
        try {
            result = expression.judge((IContext)this.qContext);
        }
        catch (Exception e) {
            return true;
        }
        return result;
    }

    protected void deleteRow(DataRowImpl deleteRow, boolean onlyCurrent) {
        deleteRow.delete();
        String rowKeyData = deleteRow.getRowKeys().toString();
        if (this.rowKeySearch != null) {
            this.rowKeySearch.remove(rowKeyData);
        }
        if (onlyCurrent) {
            this.currentPeriodSet.add(rowKeyData);
        }
    }

    @Override
    public void setIgnoreResetCache(boolean value) {
        this.ignoreResetCache = value;
    }

    @Override
    public void setValidExpression(List<IParsedExpression> expressions) {
    }
}

