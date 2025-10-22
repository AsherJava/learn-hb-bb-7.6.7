/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.RowState;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.ZBAuthJudger;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRowImpl
implements IDataRow {
    private static final Logger logger = LoggerFactory.getLogger(DataRowImpl.class);
    protected ReadonlyTableImpl tableImpl;
    protected DimensionValueSet rowKeys;
    protected ArrayList<Object> rowDatas;
    protected RowState rowState;
    protected Integer groupingFlag = -2;
    protected Integer groupingLevel = -2;
    protected HashMap<Integer, Object> modifiedDatas;
    protected String recKey;
    public boolean notInitialized;
    public boolean rowKeysEmpty;
    protected Integer parentLevel = -1;
    private boolean baseRow = false;
    private boolean filledRow = false;
    private boolean virtualRow = false;
    private String recordKey;
    private int groupTreeDeep = -1;

    public DataRowImpl(ReadonlyTableImpl table, DimensionValueSet rowKeys, ArrayList<Object> rowDatas) {
        this.tableImpl = table;
        this.rowKeys = rowKeys;
        this.rowDatas = rowDatas;
        this.rowState = RowState.NONE;
        this.modifiedDatas = new HashMap();
    }

    public DataRowImpl(DimensionValueSet rowKeys, ArrayList<Object> rowDatas) {
        this.rowKeys = rowKeys;
        this.rowDatas = rowDatas;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.tableImpl.getFieldsInfo();
    }

    public IFieldsInfo getSystemFields() {
        return this.tableImpl.getSystemFields();
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
    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    @Override
    public int getGroupingFlag() {
        if (this.groupingFlag < -1) {
            this.groupingFlag = -1;
        }
        return this.groupingFlag;
    }

    public void setGroupingFlag(int value) {
        this.groupingFlag = value;
    }

    public Object getLatestKeyValue(FieldDefine keyField) {
        Object keyValue = null;
        IFieldsInfo fieldsInfo = this.getFieldsInfo();
        if (keyField != null && fieldsInfo.indexOf(keyField) >= 0) {
            keyValue = this.internalGetValue(fieldsInfo.indexOf(keyField));
        }
        return keyValue;
    }

    @Override
    public Object getKeyValue(FieldDefine keyField) {
        ColumnModelDefine keyColumn;
        Object keyValue = null;
        IFieldsInfo fieldsInfo = this.getFieldsInfo();
        if (keyField != null && fieldsInfo.indexOf(keyField) >= 0) {
            keyValue = this.internalGetValue(fieldsInfo.indexOf(keyField));
        }
        if (keyValue != null) {
            return keyValue;
        }
        DataModelDefinitionsCache dataModelDefinitionsCache = this.tableImpl.cache.getDataModelDefinitionsCache();
        String dimension = dataModelDefinitionsCache.getDimensionName(keyColumn = dataModelDefinitionsCache.getColumnModel(keyField));
        if (!StringUtils.isEmpty((String)dimension)) {
            keyValue = this.getKeyValue(dimension);
            keyValue = DataEngineConsts.formatData(keyColumn, keyValue, this.tableImpl);
        }
        return keyValue;
    }

    public Object getKeyValue(Integer fieldIndex, String dimension) {
        Object keyValue = null;
        if (fieldIndex != null && fieldIndex >= 0) {
            keyValue = this.internalGetValue(fieldIndex);
        }
        if (keyValue != null) {
            return keyValue;
        }
        if (!StringUtils.isEmpty((String)dimension)) {
            keyValue = this.getKeyValue(dimension);
            if (fieldIndex >= 0) {
                keyValue = DataEngineConsts.formatData(this.getFieldsInfo().getFieldDefine(fieldIndex), keyValue, this.tableImpl);
            }
        }
        return keyValue;
    }

    public Object getKeyValue(String dimension) {
        Object keyValue = null;
        if (this.rowKeys.hasValue(dimension)) {
            keyValue = this.rowKeys.getValue(dimension);
        }
        if (keyValue == null) {
            keyValue = this.getMasterKeys().getValue(dimension);
        }
        return keyValue;
    }

    public Object getOldKeyValue(FieldDefine keyField) {
        Object keyValue = null;
        IFieldsInfo fieldsInfo = this.getFieldsInfo();
        DataModelDefinitionsCache dataModelDefinitionsCache = this.tableImpl.cache.getDataModelDefinitionsCache();
        if (keyField != null && fieldsInfo.indexOf(keyField) >= 0) {
            keyValue = this.internalGetOldValue(fieldsInfo.indexOf(keyField));
        }
        if (keyValue != null) {
            return keyValue;
        }
        ColumnModelDefine keyColumn = dataModelDefinitionsCache.getColumnModel(keyField);
        String dimension = dataModelDefinitionsCache.getDimensionName(keyColumn);
        if (!StringUtils.isEmpty((String)dimension)) {
            keyValue = this.getKeyValue(dimension);
        }
        return keyValue;
    }

    public void setKeyValue(FieldDefine keyField, Object value) {
        IFieldsInfo fieldsInfo = this.getFieldsInfo();
        DataModelDefinitionsCache dataModelDefinitionsCache = this.tableImpl.cache.getDataModelDefinitionsCache();
        if (keyField != null && fieldsInfo.indexOf(keyField) >= 0) {
            this.setValue(keyField, value);
        }
        ColumnModelDefine keyColumn = dataModelDefinitionsCache.getColumnModel(keyField);
        String dimension = dataModelDefinitionsCache.getDimensionName(keyColumn);
        if (this.rowKeys != null) {
            this.rowKeys.setValue(dimension, value);
        }
    }

    @Override
    public AbstractData getValue(int fieldIndex) throws DataTypeException {
        Object resultValue;
        AbstractData nullValue = this.checkGroupBySummarizingNull(fieldIndex);
        if (nullValue != null) {
            return nullValue;
        }
        int dataType = DataTypesConvert.fieldTypeToDataType(this.getFieldsInfo().getDataType(fieldIndex));
        Object object = this.modifiedDatas.containsKey(fieldIndex) ? this.modifiedDatas.get(fieldIndex) : (resultValue = this.rowDatas == null || this.rowDatas.size() <= fieldIndex ? null : this.rowDatas.get(fieldIndex));
        if (this.groupingFlag >= 0 && "\u2014\u2014".equals(resultValue)) {
            return new StringData("\u2014\u2014", true);
        }
        if (resultValue == null) {
            return DataTypes.getNullValue(dataType);
        }
        AbstractData value = AbstractData.valueOf(resultValue, dataType);
        return value;
    }

    @Override
    public Object getValueObject(int fieldIndex) {
        return this.rowDatas.get(fieldIndex);
    }

    @Override
    public AbstractData getValue(FieldDefine fieldDefine) throws RuntimeException {
        int fieldIndex = this.getFieldsInfo().indexOf(fieldDefine);
        if (fieldIndex < 0) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldDefine.getTitle()));
        }
        return this.getValue(fieldIndex);
    }

    @Override
    public String getAsString(int fieldIndex) throws DataTypeException {
        AbstractData valueData = this.getValue(fieldIndex);
        IFieldsInfo fieldsInfo = this.getFieldsInfo();
        FieldDefine fieldDefine = fieldsInfo.getFieldDefine(fieldIndex);
        return this.formatData(valueData, fieldDefine);
    }

    private String formatData(AbstractData valueData, FieldDefine fieldDefine) throws DataTypeException {
        if (fieldDefine == null) {
            if (valueData.dataType == 3 || valueData.dataType == 10) {
                Object numValue = valueData.getAsObject();
                if (numValue == null) {
                    return "";
                }
                BigDecimal dataValue = DataTypesConvert.toBigDecimal(numValue);
                return dataValue.toPlainString();
            }
            return valueData.getAsString();
        }
        if ((fieldDefine.getType() == FieldType.FIELD_TYPE_FLOAT || fieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL) && valueData.dataType != 6) {
            Object numValue = valueData.getAsObject();
            if (numValue == null) {
                return "";
            }
            BigDecimal dataValue = DataTypesConvert.toBigDecimal(numValue);
            String resultValue = dataValue.setScale((int)fieldDefine.getFractionDigits(), 6).toPlainString();
            return resultValue;
        }
        return valueData.getAsString();
    }

    @Override
    public String getAsString(FieldDefine fieldDefine) throws DataTypeException {
        int fieldIndex = this.getFieldsInfo().indexOf(fieldDefine);
        if (fieldIndex < 0) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldDefine.getTitle()));
        }
        return this.getAsString(fieldIndex);
    }

    private AbstractData checkGroupBySummarizingNull(int fieldIndex) {
        HashSet<Integer> noneGatherCols;
        Integer groupingFlag = this.getGroupingFlag();
        if (groupingFlag < 0) {
            return null;
        }
        HashMap<Integer, BitMaskAndNullValue> grpByColsInGroupingId = this.tableImpl.getGrpByColsEffectiveInGroupingId();
        if (groupingFlag > 0 && grpByColsInGroupingId != null && grpByColsInGroupingId.containsKey(fieldIndex)) {
            BitMaskAndNullValue nullValue = grpByColsInGroupingId.get(fieldIndex);
            if ((groupingFlag & nullValue.getBitMask()) > 0) {
                return nullValue.getNullValue();
            }
        }
        if ((noneGatherCols = this.tableImpl.getNoneGatherCols()).contains(fieldIndex)) {
            return new StringData("\u2014\u2014", true);
        }
        return null;
    }

    @Override
    public void setValue(int fieldIndex, Object value) {
        FieldDefine fieldDefine = this.getFieldsInfo().getFieldDefine(fieldIndex);
        if (fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldIndex));
        }
        this.internalSet(fieldIndex, value);
    }

    public void directSet(FieldDefine fieldDefine, Object value) {
        int fieldIndex = this.getFieldsInfo().indexOf(fieldDefine);
        if (fieldIndex < 0) {
            return;
        }
        if (this.rowDatas.size() <= fieldIndex) {
            return;
        }
        Object resultValue = DataEngineConsts.formatData(fieldDefine, value, this.tableImpl);
        this.rowDatas.set(fieldIndex, resultValue);
    }

    public void directSet(int fieldIndex, Object value) {
        if (this.rowDatas.size() <= fieldIndex) {
            return;
        }
        FieldDefine fieldDefine = this.getSystemFields().getFieldDefine(fieldIndex);
        Object resultValue = DataEngineConsts.formatData(fieldDefine, value, this.tableImpl);
        this.rowDatas.set(fieldIndex, resultValue);
    }

    public void internalSet(FieldDefine fieldDefine, Object value) {
        int fieldIndex = this.getFieldsInfo().indexOf(fieldDefine);
        if (fieldIndex < 0) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldDefine.getTitle()));
        }
        this.internalSet(fieldIndex, value);
    }

    public void internalSet(int fieldIndex, Object value) {
        Object oldValue;
        Object object = oldValue = this.rowDatas.size() <= fieldIndex ? null : this.rowDatas.get(fieldIndex);
        if (this.isEqual(oldValue, value)) {
            return;
        }
        FieldDefine fieldDefine = this.getSystemFields().getFieldDefine(fieldIndex);
        ZBAuthJudger aulthJuger = this.tableImpl.getContext().getAuthJudger();
        if (aulthJuger != null && !aulthJuger.canModify(fieldDefine.getKey())) {
            return;
        }
        Object resultValue = DataEngineConsts.formatData(fieldDefine, value, this.tableImpl);
        if (this.isEqual(oldValue, resultValue)) {
            return;
        }
        if (this.rowState == RowState.NONE) {
            this.rowState = RowState.MODIFIED;
        }
        this.modifiedDatas.put(fieldIndex, resultValue);
    }

    public boolean isEqual(Object oldValue, Object value) {
        return oldValue == null && value == null || oldValue != null && value != null && oldValue.equals(value);
    }

    @Override
    public void setValue(FieldDefine fieldDefine, Object value) {
        if (Objects.isNull(fieldDefine)) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldDefine.getTitle()));
        }
        int fieldIndex = this.getFieldsInfo().indexOf(fieldDefine);
        if (fieldIndex < 0) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldDefine.getTitle()));
        }
        this.internalSet(fieldIndex, value);
    }

    @Override
    public void setAsString(int fieldIndex, String value) {
        FieldDefine fieldDefine = this.getFieldsInfo().getFieldDefine(fieldIndex);
        if (fieldDefine == null || fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldIndex));
        }
        this.internalSetAsString(fieldIndex, fieldDefine, value);
    }

    @Override
    public void setAsString(FieldDefine fieldDefine, String value) {
        int fieldIndex = this.getFieldsInfo().indexOf(fieldDefine);
        if (fieldIndex < 0) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", fieldDefine.getTitle()));
        }
        this.internalSetAsString(fieldIndex, fieldDefine, value);
    }

    private void internalSetAsString(int fieldIndex, FieldDefine fieldDefine, String value) {
        this.internalSet(fieldIndex, (Object)value);
    }

    @Override
    public void validateAll() throws Exception {
        this.validateAll(false);
    }

    @Override
    public void validateAll(boolean ignoreLogicCheck) throws Exception {
        DataTableImpl dataTableImpl = (DataTableImpl)this.tableImpl;
        if (this.notInitialized) {
            dataTableImpl.delayInitRow(this.rowKeys, this);
            this.notInitialized = false;
        }
        if (this.rowKeysEmpty) {
            dataTableImpl.resetRowKeys(this);
        }
        switch (this.rowState) {
            case NONE: {
                break;
            }
            case ADD: 
            case MODIFIED: {
                dataTableImpl.recalculate(this);
                if (!ignoreLogicCheck) {
                    dataTableImpl.verifyDataRow(this);
                }
                dataTableImpl.dataValidate(this);
                dataTableImpl.valueValidate(this);
                break;
            }
            case DELETE: {
                break;
            }
        }
    }

    public void setRowState(RowState rowState) {
        this.rowState = rowState;
    }

    public void delete() {
        this.rowState = RowState.DELETE;
    }

    protected Date getVersionDate(int dateColIndex) {
        Object dateObj = this.internalGetValue(dateColIndex);
        if (dateObj instanceof Calendar) {
            Calendar calendar = (Calendar)dateObj;
            return calendar.getTime();
        }
        if (dateObj instanceof Date) {
            return (Date)dateObj;
        }
        return Consts.DATE_VERSION_INVALID_VALUE;
    }

    public Object internalGetValue(int fieldIndex) {
        AbstractData nullValue = this.checkGroupBySummarizingNull(fieldIndex);
        if (nullValue != null) {
            return null;
        }
        return this.directGetValue(fieldIndex);
    }

    public Object internalGetValue(List<Integer> fieldIndexs) {
        int firstIndex = fieldIndexs.get(0);
        if (fieldIndexs.size() == 1) {
            return this.directGetValue(firstIndex);
        }
        Object resultValue = null;
        boolean hasValue = false;
        for (Integer fieldIndex : fieldIndexs) {
            if (!this.modifiedDatas.containsKey(fieldIndex)) continue;
            hasValue = true;
            resultValue = this.modifiedDatas.get(fieldIndex);
            break;
        }
        if (!hasValue) {
            Object object = resultValue = this.rowDatas == null || this.rowDatas.size() <= firstIndex ? null : this.rowDatas.get(firstIndex);
        }
        if (resultValue instanceof Calendar) {
            Calendar calendar = (Calendar)resultValue;
            return calendar.getTime();
        }
        try {
            FieldDefine fieldDefine;
            QueryContext queryContext = this.tableImpl.getContext();
            IEncryptDecryptProcesser encryptDecryptProcesser = queryContext.getQueryParam().getEncryptDecryptProcesser();
            if (resultValue != null && encryptDecryptProcesser != null && (fieldDefine = this.getFieldsInfo().getFieldDefine(firstIndex)) != null && fieldDefine.getType() == FieldType.FIELD_TYPE_STRING) {
                resultValue = encryptDecryptProcesser.encrypt(queryContext, fieldDefine, resultValue.toString());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultValue;
    }

    private Object directGetValue(Integer fieldIndex) {
        Object resultValue;
        Object object = this.modifiedDatas.containsKey(fieldIndex) ? this.modifiedDatas.get(fieldIndex) : (resultValue = this.rowDatas == null || this.rowDatas.size() <= fieldIndex ? null : this.rowDatas.get(fieldIndex));
        if (resultValue instanceof Calendar) {
            Calendar calendar = (Calendar)resultValue;
            return calendar.getTime();
        }
        try {
            FieldDefine fieldDefine;
            QueryContext queryContext = this.tableImpl.getContext();
            IEncryptDecryptProcesser encryptDecryptProcesser = queryContext.getQueryParam().getEncryptDecryptProcesser();
            if (resultValue != null && encryptDecryptProcesser != null && (fieldDefine = this.getFieldsInfo().getFieldDefine(fieldIndex)) != null && fieldDefine.getType() == FieldType.FIELD_TYPE_STRING) {
                resultValue = encryptDecryptProcesser.encrypt(queryContext, fieldDefine, resultValue.toString());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultValue;
    }

    public Object internalGetOldValue(int fieldIndex) {
        AbstractData nullValue = this.checkGroupBySummarizingNull(fieldIndex);
        if (nullValue != null) {
            return null;
        }
        return this.directGetOldValue(fieldIndex);
    }

    public Object internalGetOldValue(List<Integer> fieldIndexs) {
        int firstIndex = fieldIndexs.get(0);
        return this.directGetOldValue(firstIndex);
    }

    private Object directGetOldValue(Integer fieldIndex) {
        Object resultValue;
        Object object = resultValue = this.rowDatas == null || this.rowDatas.size() <= fieldIndex ? null : this.rowDatas.get(fieldIndex);
        if (resultValue instanceof Calendar) {
            Calendar calendar = (Calendar)resultValue;
            return calendar.getTime();
        }
        return resultValue;
    }

    public ArrayList<Object> getRowDatas() {
        return this.rowDatas;
    }

    public String toString() {
        return "DataRowImpl [rowKeys=" + this.rowKeys + ", rowDatas=" + this.rowDatas + "," + this.modifiedDatas + "]";
    }

    public void buildRow() {
    }

    @Override
    public int getGroupingLevel() {
        if (this.groupingLevel < -1) {
            this.groupingLevel = this.groupingFlag;
        }
        return this.groupingLevel;
    }

    public void setGroupingLevel(int value) {
        this.groupingLevel = value;
    }

    @Override
    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public void setRowKeys(DimensionValueSet dimensionValueSet) {
        this.rowKeys = dimensionValueSet;
    }

    @Override
    public boolean validateExpression(List<IParsedExpression> expressions) throws ExpressionValidateException {
        if (expressions.isEmpty()) {
            return true;
        }
        DataTableImpl dataTableImpl = (DataTableImpl)this.tableImpl;
        return dataTableImpl.validataDataRow(this, expressions);
    }

    public void resetRowKeys() {
        DataTableImpl dataTableImpl = (DataTableImpl)this.tableImpl;
        dataTableImpl.resetRowKeys(this);
    }

    @Override
    public boolean judge(IExpression expression, QueryContext context) {
        if (expression == null || context == null) {
            return true;
        }
        DataTableImpl dataTableImpl = (DataTableImpl)this.tableImpl;
        return dataTableImpl.judge(this, expression, context);
    }

    @Override
    public int getParentLevel() {
        return this.parentLevel;
    }

    public void setParentLevel(Integer parentLevel) {
        this.parentLevel = parentLevel;
    }

    public boolean isBaseRow() {
        return this.baseRow;
    }

    public void setBaseRow(boolean baseRow) {
        this.baseRow = baseRow;
    }

    @Override
    public boolean isFilledRow() {
        return this.filledRow;
    }

    public void setFilledRow(boolean filledRow) {
        this.filledRow = filledRow;
    }

    public boolean hasKeyValue(FieldDefine keyField) {
        Object rowValue;
        ColumnModelDefine column = this.tableImpl.cache.getDataModelDefinitionsCache().getColumnModel(keyField);
        String dimension = this.tableImpl.cache.getDataModelDefinitionsCache().getDimensionName(column);
        if (this.rowKeys != null && this.rowKeys.hasValue(dimension) && (rowValue = this.rowKeys.getValue(dimension)) != null) {
            AbstractData dataValue;
            IFieldsInfo fieldsInfo = this.getFieldsInfo();
            if (keyField != null && fieldsInfo.indexOf(keyField) >= 0 && (dataValue = this.getValue(keyField)) == null) {
                this.setValue(keyField, rowValue);
            }
            return true;
        }
        return false;
    }

    public boolean isVirtualRow() {
        return this.virtualRow;
    }

    public void setVirtualRow(boolean virtualRow) {
        this.virtualRow = virtualRow;
    }

    public final HashMap<Integer, Object> getModifiedDatas() {
        return this.modifiedDatas;
    }

    @Override
    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getRecordKey() {
        return this.recordKey;
    }

    @Override
    public int getGroupTreeDeep() {
        return this.groupTreeDeep;
    }

    public void setGroupTreeDeep(int groupTreeDeep) {
        this.groupTreeDeep = groupTreeDeep;
    }

    public ReadonlyTableImpl getTableImpl() {
        return this.tableImpl;
    }
}

