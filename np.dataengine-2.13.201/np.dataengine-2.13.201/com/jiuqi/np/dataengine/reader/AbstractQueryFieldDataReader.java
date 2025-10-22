/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractQueryFieldDataReader
implements IQueryFieldDataReader {
    protected Map<QueryField, QueryFieldInfo> fieldInfoSeach;
    protected boolean canRead = false;
    protected QueryContext queryContext;

    public AbstractQueryFieldDataReader() {
    }

    public AbstractQueryFieldDataReader(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    protected Map<QueryField, QueryFieldInfo> getFieldInfoSeach() {
        if (this.fieldInfoSeach == null) {
            this.fieldInfoSeach = new HashMap<QueryField, QueryFieldInfo>();
        }
        return this.fieldInfoSeach;
    }

    @Deprecated
    protected Object convertDataValue(FieldDefine fieldDefine, Object dataValue) {
        if (dataValue == null) {
            return null;
        }
        Object resultValue = dataValue;
        try {
            if (dataValue instanceof AbstractData) {
                resultValue = ((AbstractData)dataValue).getAsObject();
            }
            switch (fieldDefine.getType()) {
                case FIELD_TYPE_DECIMAL: {
                    resultValue = this.convertBigDecimalValue(fieldDefine, dataValue);
                    break;
                }
                case FIELD_TYPE_FLOAT: {
                    resultValue = this.convertFloatValue(fieldDefine, dataValue);
                    break;
                }
                case FIELD_TYPE_INTEGER: {
                    resultValue = this.convertIntValue(dataValue);
                    break;
                }
                case FIELD_TYPE_STRING: {
                    if (dataValue == null) break;
                    resultValue = this.convertStringValue(fieldDefine, dataValue);
                    break;
                }
                case FIELD_TYPE_UUID: {
                    if (dataValue == null) break;
                    if (dataValue instanceof UUID) {
                        resultValue = dataValue;
                        break;
                    }
                    if (dataValue instanceof String) {
                        resultValue = UUID.fromString((String)dataValue);
                        break;
                    }
                    if (dataValue instanceof BlobValue) {
                        resultValue = Convert.toUUID((byte[])((BlobValue)dataValue)._getBytes());
                        break;
                    }
                    resultValue = Convert.toUUID((Object)dataValue);
                    break;
                }
                case FIELD_TYPE_BINARY: {
                    if (!(dataValue instanceof BlobValue)) break;
                    resultValue = ((BlobValue)dataValue)._getBytes();
                    break;
                }
                case FIELD_TYPE_LOGIC: {
                    resultValue = Convert.toBoolean((Object)dataValue);
                    break;
                }
            }
            if (this.queryContext != null) {
                IEncryptDecryptProcesser encryptDecryptProcesser = this.queryContext.getQueryParam().getEncryptDecryptProcesser();
                if (resultValue != null && encryptDecryptProcesser != null && fieldDefine.getType() == FieldType.FIELD_TYPE_STRING) {
                    resultValue = encryptDecryptProcesser.decrypt(this.queryContext, fieldDefine, resultValue.toString());
                }
            }
        }
        catch (Exception e) {
            resultValue = dataValue;
        }
        return resultValue;
    }

    protected Object convertDataValue(QueryFieldInfo queryFieldInfo, Object dataValue) {
        if (dataValue == null) {
            return null;
        }
        Object resultValue = dataValue;
        try {
            resultValue = queryFieldInfo.dataConverter.convert(this.queryContext, dataValue);
        }
        catch (Exception e) {
            resultValue = dataValue instanceof AbstractData ? ((AbstractData)dataValue).getAsObject() : dataValue;
        }
        return resultValue;
    }

    protected Object convertIntValue(Object dataValue) {
        if (dataValue instanceof Number) {
            dataValue = Round.callFunction((Number)((Number)dataValue), (int)0);
        }
        return Convert.toInt((Object)dataValue);
    }

    protected double convertFloatValue(FieldDefine fieldDefine, Object dataValue) {
        double floatValue = Convert.toDouble((Object)dataValue);
        return Round.callFunction((Number)floatValue, (int)fieldDefine.getFractionDigits());
    }

    protected BigDecimal convertBigDecimalValue(FieldDefine fieldDefine, Object dataValue) {
        BigDecimal decimalValue = DataTypesConvert.toBigDecimal(dataValue);
        return decimalValue.setScale((int)fieldDefine.getFractionDigits(), 4);
    }

    protected String convertStringValue(FieldDefine fieldDefine, Object dataValue) {
        String formatValue = dataValue.toString();
        if (formatValue.length() > fieldDefine.getSize()) {
            formatValue = formatValue.substring(0, fieldDefine.getSize());
        }
        return formatValue;
    }

    protected double convertFloatValue(ColumnModelDefine fieldDefine, Object dataValue) {
        double floatValue = Convert.toDouble((Object)dataValue);
        return Round.callFunction((Number)floatValue, (int)fieldDefine.getDecimal());
    }

    protected BigDecimal convertBigDecimalValue(ColumnModelDefine fieldDefine, Object dataValue) {
        BigDecimal decimalValue = DataTypesConvert.toBigDecimal(dataValue);
        return decimalValue.setScale(fieldDefine.getDecimal(), 4);
    }

    protected String convertStringValue(ColumnModelDefine fieldDefine, Object dataValue) {
        String formatValue = dataValue.toString();
        if (formatValue.length() > fieldDefine.getPrecision()) {
            formatValue = formatValue.substring(0, fieldDefine.getPrecision());
        }
        return formatValue;
    }

    @Override
    public int findIndex(QueryField queryField) {
        QueryFieldInfo info = this.getFieldInfoSeach().get(queryField);
        if (info == null) {
            return -1;
        }
        return info.index;
    }

    @Override
    public QueryFieldInfo findQueryFieldInfo(QueryField queryField) {
        return this.getFieldInfoSeach().get(queryField);
    }

    @Override
    public QueryFieldInfo putIndex(DataModelDefinitionsCache cache, QueryField queryField, int index) {
        ColumnModelDefine fieldDefine = cache.findField(queryField.getUID());
        QueryFieldInfo queryFieldInfo = new QueryFieldInfo(queryField, fieldDefine, index);
        Map<QueryField, QueryFieldInfo> fieldInfoMap = this.getFieldInfoSeach();
        fieldInfoMap.put(queryField, queryFieldInfo);
        return queryFieldInfo;
    }

    @Override
    public void reset() {
        if (this.fieldInfoSeach != null) {
            this.fieldInfoSeach.clear();
        }
    }
}

