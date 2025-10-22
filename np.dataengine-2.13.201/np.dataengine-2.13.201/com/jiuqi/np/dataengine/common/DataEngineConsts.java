/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.IQueryField;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataEngineConsts {
    public static final String ORDER_DIMENSION = "RECORDKEY";
    public static final String ADJUST_DIMENSION = "ADJUST";
    public static final String GROUPING_FLAG_DIM = "GroupingFlag";
    public static final String GROUPING_DEEP_DIM = "GroupingDeep";
    public static final String GROUP_KEY = "GROUP_KEY";
    public static final char GROUP_KEY_SYMBOL = '#';
    public static final String NON_GROUPING_FIELD = "\u2014\u2014";
    public static final String ADJUST_DEFAULT_VALUE = "0";
    public static final String SBID_FIELD = "SBID";
    public static final String ACCOUNT_ID_FIELD = "SBID";
    public static final String TN_ACCOUNT_RPT_SUFFIX = "_RPT";
    public static final String TN_ACCOUNT_HIS_SUFFIX = "_HIS";
    public static final boolean DATA_ENGINE_DEBUG = DataEngineUtil.logger.isDebugEnabled() || DataEngineUtil.logger.isTraceEnabled() || "true".equals(System.getProperty("np.dataengine.debug"));
    public static final boolean DATA_ENGINE_DATAQUERY_SQLLOG = DATA_ENGINE_DEBUG || "true".equals(System.getProperty("np.dataengine.out.dataquery.sqllog"));
    public static final boolean DATA_ENGINE_OUT_FMLPLAN = DATA_ENGINE_DEBUG || "true".equals(System.getProperty("np.dataengine.out.fmlplan"));
    public static final boolean DATA_ENGINE_DEBUG_SQL = DATA_ENGINE_DEBUG || "true".equals(System.getProperty("np.dataengine.debug.out.sql"));
    public static final boolean DATA_ENGINE_DEBUG_DATACHANGE = "true".equals(System.getProperty("np.dataengine.debug.out.datachange"));
    @Deprecated
    public static final String VERSION_DIMENSION = "VERSIONID";
    public static final String CALIBER_DIMENSION = "CALIBER";
    public static final String VERSION_TABLE_PREFIX = "SYS_VER_";
    public static final String VERSION_RELATION_TABLE_PREFIX = "SYS_VER_REL_";
    public static final String HISTORY_TABLE_PREFIX = "SYS_UP_HI_";
    public static final String STATE_TABLE_PREFIX = "SYS_UP_ST_";
    public static final String VAR_LINK_ALIAS = "VAR_LINK_ALIAS";
    public static final int DETAIL_GROUPING_FLAG = -1;
    public static final int LAST_GROUPING_FLAG = 0;
    public static final String EMPTY = "(\u7a7a)";
    public static final String SPLIT = "|";
    public static final String BIZKEYORDER_SEPARATOR = "#^$";
    public static final String SELF_DIMENSION = "SelfFlag";
    public static final String ORIGINALDATAPROVIDER = "originalDataProvider";
    public static final String EXPRESSION_VALID_INFO = "\u5f53\u524d\u6570\u636e\u6821\u9a8c\u516c\u5f0f\u6267\u884c\u4e0d\u901a\u8fc7\u3002";
    public static final String DATA_VALID_INFO = "\u5f53\u524d\u884c\u6570\u636e\u6821\u9a8c\u4e0d\u901a\u8fc7\u3002";
    public static final FormulaShowInfo FORMULA_SHOW_INFO_JQ = new FormulaShowInfo(FormulaShowType.JQ);
    public static final FormulaShowInfo FORMULA_SHOW_INFO_DATA = new FormulaShowInfo(FormulaShowType.DATA);
    public static int MAX_DATA_SIZE = DataEngineConsts.getMaxDataSize();

    public static Object formatData(int dataType, Object value) {
        if (value == null || value instanceof String && StringUtils.isEmpty((String)value.toString())) {
            return null;
        }
        Object resultValue = value;
        try {
            switch (dataType) {
                case 10: {
                    BigDecimal decimalValue = DataTypesConvert.toBigDecimal(value);
                    resultValue = decimalValue;
                    break;
                }
                case 3: {
                    double floatValue = Convert.toDouble((Object)value);
                    resultValue = floatValue;
                    break;
                }
                case 4: {
                    resultValue = DataEngineConsts.convertToInt(value);
                    break;
                }
                case 6: {
                    if (value == null) break;
                    String formatValue = value.toString();
                    resultValue = formatValue;
                    break;
                }
                case 5: {
                    if (value instanceof Calendar) {
                        resultValue = ((Calendar)value).getTime();
                        break;
                    }
                    if (value instanceof Date) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Date(date);
                    break;
                }
                case 2: {
                    if (value instanceof Timestamp) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Timestamp(date);
                    break;
                }
                case 33: {
                    if (value == null) break;
                    if (value instanceof UUID) {
                        resultValue = value;
                        break;
                    }
                    if (value instanceof String) {
                        resultValue = UUID.fromString((String)value);
                        break;
                    }
                    if (value instanceof BlobValue) {
                        resultValue = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
                        break;
                    }
                    resultValue = Convert.toUUID((Object)value);
                    break;
                }
            }
        }
        catch (Exception e) {
            resultValue = value;
        }
        return resultValue;
    }

    public static Object formatData(IQueryField queryField, Object value) {
        if (value == null || value instanceof String && StringUtils.isEmpty((String)value.toString())) {
            return null;
        }
        Object resultValue = value;
        try {
            switch (queryField.getDataType()) {
                case 10: {
                    BigDecimal decimalValue = DataTypesConvert.toBigDecimal(value);
                    resultValue = Round.callFunction((BigDecimal)decimalValue, (int)queryField.getFractionDigits());
                    break;
                }
                case 3: {
                    double floatValue = Convert.toDouble((Object)value);
                    resultValue = Round.callFunction((Number)floatValue, (int)queryField.getFractionDigits());
                    break;
                }
                case 4: {
                    DataEngineConsts.convertToInt(value);
                    break;
                }
                case 6: {
                    if (value == null) break;
                    String formatValue = value.toString();
                    if (formatValue.length() > queryField.getFieldSize() && queryField.getFieldSize() > 0) {
                        formatValue = formatValue.substring(0, queryField.getFieldSize());
                    }
                    resultValue = formatValue;
                    break;
                }
                case 5: {
                    if (value instanceof Calendar) {
                        resultValue = ((Calendar)value).getTime();
                        break;
                    }
                    if (value instanceof Date) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Date(date);
                    break;
                }
                case 2: {
                    if (value instanceof Timestamp) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Timestamp(date);
                    break;
                }
                case 1: {
                    if (!(value instanceof Number)) break;
                    Number num = (Number)value;
                    resultValue = DataType.compare((Number)num, (Number)0) != 0;
                    break;
                }
                case 33: {
                    if (value == null) break;
                    if (value instanceof UUID) {
                        resultValue = value;
                        break;
                    }
                    if (value instanceof String) {
                        resultValue = UUID.fromString((String)value);
                        break;
                    }
                    if (value instanceof BlobValue) {
                        resultValue = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
                        break;
                    }
                    resultValue = Convert.toUUID((Object)value);
                    break;
                }
            }
        }
        catch (Exception e) {
            resultValue = value;
        }
        return resultValue;
    }

    public static Object formatData(FieldDefine fieldDefine, Object value, ReadonlyTableImpl tableImpl) {
        if (value == null || value instanceof String && StringUtils.isEmpty((String)value.toString())) {
            return null;
        }
        Object resultValue = value;
        try {
            if (value instanceof AbstractData) {
                return ((AbstractData)value).getAsObject();
            }
            switch (fieldDefine.getType()) {
                case FIELD_TYPE_DECIMAL: {
                    BigDecimal decimalValue = DataTypesConvert.toBigDecimal(value);
                    resultValue = decimalValue.setScale((int)fieldDefine.getFractionDigits(), 4);
                    break;
                }
                case FIELD_TYPE_FLOAT: {
                    double floatValue = Convert.toDouble((Object)value);
                    resultValue = Round.callFunction((Number)floatValue, (int)fieldDefine.getFractionDigits());
                    break;
                }
                case FIELD_TYPE_INTEGER: {
                    DataEngineConsts.convertToInt(value);
                    break;
                }
                case FIELD_TYPE_STRING: {
                    if (value == null) break;
                    String formatValue = value.toString();
                    if (formatValue.length() > fieldDefine.getSize() && fieldDefine.getSize() > 0) {
                        formatValue = formatValue.substring(0, fieldDefine.getSize());
                    }
                    resultValue = formatValue;
                    break;
                }
                case FIELD_TYPE_DATE: {
                    if (value instanceof Calendar) {
                        resultValue = ((Calendar)value).getTime();
                        break;
                    }
                    if (value instanceof String) {
                        String str = (String)value;
                        if (str.length() > 0) {
                            long date = Convert.toDate((Object)value);
                            resultValue = new Date(date);
                        }
                        break;
                    }
                    if (value instanceof Date) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Date(date);
                    break;
                }
                case FIELD_TYPE_DATE_TIME: {
                    if (value instanceof String) {
                        String str = (String)value;
                        if (str.length() > 0) {
                            long date = Convert.toDate((Object)value);
                            resultValue = new Timestamp(date);
                        }
                        break;
                    }
                    if (value instanceof Timestamp) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Timestamp(date);
                    break;
                }
                case FIELD_TYPE_UUID: {
                    if (value == null) break;
                    if (value instanceof UUID) {
                        resultValue = value;
                        break;
                    }
                    if (value instanceof String) {
                        String codeValue = (String)value;
                        if (codeValue.length() != 32 && codeValue.length() != 36 && tableImpl != null) {
                            resultValue = tableImpl.getRefValue(fieldDefine, codeValue);
                            if (!(resultValue instanceof UUID)) {
                                throw new RuntimeException(String.format("\u6307\u6807%s\u4e3aUUID\u578b\uff0c\u8bd5\u56fe\u6839\u636e\u6307\u6807\u5173\u8054\u7684\u4e3b\u4f53\u9879\u4ee3\u7801%s\u627e\u5230\u5bf9\u5e94\u7684ID\uff0c\u4f46\u672a\u627e\u5230\uff01", fieldDefine.getCode(), codeValue));
                            }
                            break;
                        }
                        try {
                            resultValue = Convert.toUUID((String)((String)value));
                        }
                        catch (Exception e) {
                            if (tableImpl != null) break;
                            resultValue = codeValue;
                        }
                        break;
                    }
                    if (value instanceof BlobValue) {
                        resultValue = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
                        break;
                    }
                    resultValue = Convert.toUUID((Object)value);
                    break;
                }
                case FIELD_TYPE_LOGIC: {
                    resultValue = Convert.toBoolean((Object)value);
                    break;
                }
            }
        }
        catch (Exception e) {
            resultValue = value;
        }
        return resultValue;
    }

    public static Object formatData(ColumnModelDefine fieldDefine, Object value, ReadonlyTableImpl tableImpl) {
        if (value == null || value instanceof String && StringUtils.isEmpty((String)value.toString())) {
            return null;
        }
        Object resultValue = value;
        try {
            if (value instanceof AbstractData) {
                return ((AbstractData)value).getAsObject();
            }
            switch (fieldDefine.getColumnType()) {
                case BIGDECIMAL: {
                    BigDecimal decimalValue = DataTypesConvert.toBigDecimal(value);
                    resultValue = decimalValue.setScale(fieldDefine.getDecimal(), 4);
                    break;
                }
                case DOUBLE: {
                    double floatValue = Convert.toDouble((Object)value);
                    resultValue = Round.callFunction((Number)floatValue, (int)fieldDefine.getDecimal());
                    break;
                }
                case INTEGER: {
                    DataEngineConsts.convertToInt(value);
                    break;
                }
                case STRING: {
                    if (value == null) break;
                    String formatValue = value.toString();
                    if (formatValue.length() > fieldDefine.getPrecision() && fieldDefine.getPrecision() > 0) {
                        formatValue = formatValue.substring(0, fieldDefine.getPrecision());
                    }
                    resultValue = formatValue;
                    break;
                }
                case DATETIME: {
                    if (value instanceof String) {
                        String str = (String)value;
                        if (str.length() > 0) {
                            long date = Convert.toDate((Object)value);
                            resultValue = new Timestamp(date);
                        }
                        break;
                    }
                    if (value instanceof Timestamp) break;
                    long date = Convert.toDate((Object)value);
                    resultValue = new Timestamp(date);
                    break;
                }
                case UUID: {
                    if (value == null) break;
                    if (value instanceof UUID) {
                        resultValue = value;
                        break;
                    }
                    if (value instanceof String) {
                        String codeValue = (String)value;
                        if (codeValue.length() != 32 && codeValue.length() != 36 && tableImpl != null) {
                            resultValue = tableImpl.getRefValue(fieldDefine, codeValue);
                            if (!(resultValue instanceof UUID)) {
                                throw new RuntimeException(String.format("\u6307\u6807%s\u4e3aUUID\u578b\uff0c\u8bd5\u56fe\u6839\u636e\u6307\u6807\u5173\u8054\u7684\u4e3b\u4f53\u9879\u4ee3\u7801%s\u627e\u5230\u5bf9\u5e94\u7684ID\uff0c\u4f46\u672a\u627e\u5230\uff01", fieldDefine.getCode(), codeValue));
                            }
                            break;
                        }
                        try {
                            resultValue = Convert.toUUID((String)((String)value));
                        }
                        catch (Exception e) {
                            if (tableImpl != null) {
                                resultValue = tableImpl.getRefValue(fieldDefine, codeValue);
                                break;
                            }
                            resultValue = codeValue;
                        }
                        break;
                    }
                    if (value instanceof BlobValue) {
                        resultValue = Convert.toUUID((byte[])((BlobValue)value)._getBytes());
                        break;
                    }
                    resultValue = Convert.toUUID((Object)value);
                    break;
                }
                case BOOLEAN: {
                    resultValue = Convert.toBoolean((Object)value);
                    break;
                }
            }
        }
        catch (Exception e) {
            resultValue = value;
        }
        return resultValue;
    }

    private static int convertToInt(Object value) {
        Object resultValue = value;
        if (value instanceof Number) {
            resultValue = Round.callFunction((Number)((Number)value), (int)0);
        }
        return Convert.toInt((Object)resultValue);
    }

    public static byte[] toBytes(Object value) {
        if (value instanceof String) {
            String valueStr = (String)value;
            if (valueStr.length() == 32 || valueStr.length() == 36) {
                return Convert.toBytes((UUID)Convert.toUUID((String)valueStr));
            }
            return Convert.toBytes((Object)value);
        }
        return Convert.toBytes((Object)value);
    }

    public static List<String> getRecKeys(int size) {
        ArrayList<String> recKeys = new ArrayList<String>();
        for (int index = 0; index < size; ++index) {
            recKeys.add(UUID.randomUUID().toString());
        }
        return recKeys;
    }

    private static int getMaxDataSize() {
        String maxSize = System.getProperty("bi.maxDataSize");
        if (!StringUtils.isEmpty((String)maxSize)) {
            try {
                return Integer.parseInt(maxSize);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 1000000;
    }

    public static enum FocusType {
        FIELD("\u6307\u6807"),
        FUNCTION("\u51fd\u6570 ");

        private final String title;

        private FocusType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }
    }

    public static enum FuncReadWriteType {
        AUTO,
        CUSTOM,
        UNKNOWN;

    }

    public static enum QueryTableType {
        COMMON,
        PERIOD,
        DIMENSION,
        ACCOUNT,
        ACCOUNT_HIS,
        ACCOUNT_RPT;

    }

    public static enum DebugLogType {
        COMMON,
        SQL,
        REGION,
        FORMULA,
        DATACHANGE;

    }

    public static enum DataEngineRunType {
        UNKOWN("dataEngine", "\u6570\u636e\u5f15\u64ce", 0),
        QUERY_COMMON("dataEngine.query.common", "\u6570\u636e\u5f15\u64ce-\u666e\u901a\u67e5\u8be2", 1),
        QUERY_READONLY("dataEngine.query.readonly", "\u6570\u636e\u5f15\u64ce-\u53ea\u8bfb\u67e5\u8be2", 11),
        QUERY_FORUPDATE("dataEngine.query.forUpdate", "\u6570\u636e\u5f15\u64ce-\u76f4\u63a5\u66f4\u65b0\u63a5\u53e3", 12),
        QUERY_GROUP("dataEngine.query.group", "\u6570\u636e\u5f15\u64ce-\u5206\u7ec4\u67e5\u8be2", 2),
        QUERY_ENTITY("dataEngine.query.entity", "\u6570\u636e\u5f15\u64ce-\u5b9e\u4f53\u67e5\u8be2", 3),
        CALCULATE("dataEngine.calculate", "\u6570\u636e\u5f15\u64ce-\u8fd0\u7b97", 4),
        BATCH_CALCULATE("dataEngine.batch.calculate", "\u6570\u636e\u5f15\u64ce-\u6279\u91cf\u8fd0\u7b97", 40),
        CHECK("dataEngine.check", "\u6570\u636e\u5f15\u64ce-\u5ba1\u6838", 5),
        BATCH_CHECK("dataEngine.batch.check", "\u6570\u636e\u5f15\u64ce-\u6279\u91cf\u5ba1\u6838", 50),
        EVAL("dataEngine.eval", "\u6570\u636e\u5f15\u64ce-\u8868\u8fbe\u5f0f\u53d6\u503c", 6),
        JUDGE("dataEngine.judge", "\u6570\u636e\u5f15\u64ce-\u6761\u4ef6\u5224\u65ad", 7),
        PARSE("dataEngine.parse", "\u6570\u636e\u5f15\u64ce-\u516c\u5f0f\u89e3\u6790", 8);

        private final String type;
        private final String title;
        private final int value;

        private DataEngineRunType(String type, String title, int value) {
            this.type = type;
            this.title = title;
            this.value = value;
        }

        public FormulaType getFormulaType(int value) {
            for (FormulaType temp : FormulaType.values()) {
                if (temp.getValue() != value) continue;
                return temp;
            }
            return null;
        }

        public String getType() {
            return this.type;
        }

        public String getTitle() {
            return this.title;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum FormulaType {
        CALCULATE("\u8fd0\u7b97", 1),
        CHECK("\u5ba1\u6838", 2),
        EXPRESSION("\u8868\u8fbe\u5f0f", 3),
        BALANCE("\u5e73\u8861", 4);

        private final String title;
        private final int value;

        private FormulaType(String title, int value) {
            this.title = title;
            this.value = value;
        }

        public FormulaType getFormulaType(int value) {
            for (FormulaType temp : FormulaType.values()) {
                if (temp.getValue() != value) continue;
                return temp;
            }
            return null;
        }

        public String getTitle() {
            return this.title;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum FormulaShowType {
        DATA("\u6570\u636e\u516c\u5f0f", 1),
        JQ("\u4e45\u5176\u62a5\u8868\u5750\u6807\u516c\u5f0f ", 2),
        EXCEL("Excel\u516c\u5f0f", 3),
        DATALINK("\u636e\u94fe\u63a5\u516c\u5f0f", 4);

        private final String title;
        private final int value;

        private FormulaShowType(String title, int value) {
            this.title = title;
            this.value = value;
        }

        public FormulaShowType getFormulaShowType(int value) {
            for (FormulaShowType temp : FormulaShowType.values()) {
                if (temp.getValue() != value) continue;
                return temp;
            }
            return null;
        }

        public String getTitle() {
            return this.title;
        }

        public int getValue() {
            return this.value;
        }
    }
}

