/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.TableModelType
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.ValueValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.nvwa.definition.common.TableModelType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class DataUpdatorImpl
implements IDataUpdator {
    private final DataTableImpl tableImpl;
    private boolean deleteAllData;
    private final List<DataRowImpl> insertRows = new ArrayList<DataRowImpl>();
    private final List<DataRowImpl> deleteRows = new ArrayList<DataRowImpl>();
    private final List<DataRowImpl> updateRows = new ArrayList<DataRowImpl>();
    private boolean onlySaveData = false;
    private final List<IParsedExpression> validExpressions = new ArrayList<IParsedExpression>();

    public DataUpdatorImpl(DataTableImpl tableImpl, boolean deleteAllExistingData) {
        this.tableImpl = tableImpl;
        this.deleteAllData = deleteAllExistingData;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.tableImpl.getFieldsInfo();
    }

    @Override
    public DimensionValueSet getMasterKeys() {
        return this.tableImpl.getMasterKeys();
    }

    @Override
    public DimensionSet getMasterDimensions() {
        return this.tableImpl.getMasterDimensions();
    }

    @Override
    public DimensionSet getRowDimensions() {
        return this.tableImpl.getRowDimensions();
    }

    @Override
    public IDataRow addInsertedRow() throws DataTypeException {
        DataRowImpl dataRow = (DataRowImpl)this.tableImpl.newEmptyRow(true);
        this.insertRows.add(dataRow);
        return dataRow;
    }

    @Override
    public IDataRow addInsertedRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        DataRowImpl dataRow = (DataRowImpl)this.tableImpl.appendRow(rowKeys);
        this.insertRows.add(dataRow);
        return dataRow;
    }

    @Override
    public IDataRow addDeletedRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        DataRowImpl dataRow = (DataRowImpl)this.tableImpl.appendRow(rowKeys);
        if (!this.deleteAllData) {
            this.deleteRows.add(dataRow);
        }
        return dataRow;
    }

    @Override
    public IDataRow addModifiedRow(DimensionValueSet rowKeys) throws IncorrectQueryException {
        DataRowImpl dataRow = (DataRowImpl)this.tableImpl.appendRow(rowKeys);
        this.updateRows.add(dataRow);
        return dataRow;
    }

    @Override
    public void deleteAll() throws Exception {
        this.deleteAll(false);
    }

    @Override
    public void deleteAll(boolean canDeleteEntity) throws Exception {
        if (!this.deleteAllData) {
            throw new Exception("\u5168\u90e8\u5220\u9664\u6570\u636e\uff0c\u9700\u8981\u5728DataUpdator\u6253\u5f00\u65f6\u9884\u5148\u6307\u5b9a");
        }
        if (!canDeleteEntity) {
            HashSet<String> tableNames = this.getTableNames(this.tableImpl.queryfields);
            DataModelDefinitionsCache dataDefinitionsCache = this.tableImpl.cache.getDataModelDefinitionsCache();
            for (String tableName : tableNames) {
                TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(tableName);
                if (tableRunInfo.getTableModelDefine().getType() != TableModelType.ENUM && tableRunInfo.getTableModelDefine().getType() != TableModelType.ENUM_NOSUMMARY) continue;
                throw new Exception("\u4e0d\u80fd\u7528\u8be5\u65b9\u6cd5\u5168\u90e8\u5220\u9664\u201c" + tableRunInfo.getTableModelDefine().getTitle() + "\u201d\u6570\u636e");
            }
        }
        this.deleteAllData = true;
    }

    private HashSet<String> getTableNames(List<QueryField> queryfields) {
        HashSet<String> tableNames = new HashSet<String>();
        int fieldCount = queryfields.size();
        for (int index = 0; index < fieldCount; ++index) {
            QueryField queryField = queryfields.get(index);
            tableNames.add(queryField.getTableName());
        }
        return tableNames;
    }

    @Override
    public boolean commitChanges() throws Exception {
        return this.commitChanges(false, true);
    }

    @Override
    public boolean commitChanges(boolean ignoreLogicCheck) throws Exception {
        return this.commitChanges(ignoreLogicCheck, true);
    }

    @Override
    public boolean commitChanges(boolean ignoreLogicCheck, boolean cascadeDeletion) throws Exception {
        this.tableImpl.setOnlySaveData(this.onlySaveData);
        if (this.tableImpl.qContext != null) {
            this.tableImpl.setCalcRowContext(this.tableImpl.qContext);
        }
        if (this.deleteAllData) {
            this.updateRows.clear();
        }
        ArrayList<DataRowImpl> checkRows = new ArrayList<DataRowImpl>();
        checkRows.addAll(this.updateRows);
        checkRows.addAll(this.insertRows);
        this.checkRowValid(ignoreLogicCheck, checkRows);
        this.tableImpl.commitChanges(this.getMasterKeys(), this.deleteAllData, this.insertRows, this.updateRows, this.deleteRows, Collections.emptyList(), this.tableImpl.getQueryVersionStartDate(), this.tableImpl.getQueryVersionDate(), cascadeDeletion);
        return true;
    }

    private void checkRowValid(boolean ignoreLogicCheck, List<DataRowImpl> checkRows) throws Exception {
        ValueValidateException valueMergeResult = new ValueValidateException("\u5f53\u524d\u884c\u6570\u636e\u6821\u9a8c\u4e0d\u901a\u8fc7\u3002");
        ExpressionValidateException expMergeResult = new ExpressionValidateException("\u5f53\u524d\u6570\u636e\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u4e0d\u901a\u8fc7\u3002");
        boolean validError = false;
        boolean expValidError = false;
        for (DataRowImpl dataRow : checkRows) {
            try {
                dataRow.validateExpression(this.validExpressions);
                dataRow.validateAll(ignoreLogicCheck);
            }
            catch (ValueValidateException e) {
                valueMergeResult.merge(e);
                validError = true;
            }
            catch (ExpressionValidateException ex) {
                expMergeResult.merge(ex);
                expValidError = true;
            }
            catch (Exception e) {
                throw e;
            }
        }
        if (expValidError) {
            throw expMergeResult;
        }
        if (validError) {
            throw valueMergeResult;
        }
    }

    @Override
    public void setDataChangeMonitor(IMonitor monitor) {
        this.tableImpl.setDataChangeMonitor(monitor);
    }

    @Override
    public void needCheckDuplicateKeys(boolean needCheckKeys) {
        this.tableImpl.needCheckDuplicateKeys(needCheckKeys);
    }

    @Override
    public void setOnlySaveData(boolean value) {
        this.onlySaveData = value;
    }

    @Override
    public void resetDeleteAllExistingData() {
        this.deleteAllData = false;
    }

    @Override
    public void resetRows() {
        this.insertRows.clear();
        this.updateRows.clear();
        this.deleteRows.clear();
    }

    @Override
    public int getInsertCount() {
        return this.insertRows.size();
    }

    @Override
    public void setValidExpression(List<IParsedExpression> expressions) {
        if (expressions == null) {
            return;
        }
        this.validExpressions.addAll(expressions);
    }
}

