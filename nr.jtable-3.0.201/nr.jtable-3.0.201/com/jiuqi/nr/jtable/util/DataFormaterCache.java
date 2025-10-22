/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.data.CurrencyData
 *  com.jiuqi.np.dataengine.data.DateData
 *  com.jiuqi.np.dataengine.data.DateTimeData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.dataengine.data.IntData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.file.FileInfo
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.DateData;
import com.jiuqi.np.dataengine.data.DateTimeData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.util.BooleanUtil;
import com.jiuqi.nr.jtable.util.DateUtils;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFormaterCache {
    private static final Logger logger = LoggerFactory.getLogger(DataFormaterCache.class);
    private JtableContext jtableContext;
    private Map<String, List<FileInfo>> fileDataMap;
    private Map<String, List<byte[]>> imgDataMap;
    private Map<String, Map<String, EntityData>> entityDataInfoMap = new HashMap<String, Map<String, EntityData>>();
    private Map<String, EntityReturnInfo> entityDataMap;
    private Map<String, Set<String>> entityCaptionFields;
    private boolean jsonData = false;
    private Boolean entityMatchAll;
    private Map<String, EntityReturnInfo> searchDataMap;
    private boolean queryChildrenCount = true;
    private boolean desensitized = false;
    private final Map<String, DecimalFormat> formatMap = new HashMap<String, DecimalFormat>();

    public DataFormaterCache(JtableContext jtableContext) {
        this.jtableContext = new JtableContext(jtableContext);
        this.entityDataMap = new HashMap<String, EntityReturnInfo>();
    }

    public DataFormaterCache(JtableContext jtableContext, Map<String, List<FileInfo>> fileDataMap, Map<String, List<byte[]>> imgDataMap) {
        this.jtableContext = new JtableContext(jtableContext);
        this.entityDataMap = new HashMap<String, EntityReturnInfo>();
        this.fileDataMap = fileDataMap;
        this.imgDataMap = imgDataMap;
    }

    public void init(RegionDataSet regionDataSet) {
        this.fileDataMap = regionDataSet.getFileDataMap();
        this.imgDataMap = regionDataSet.getImgDataMap();
        if (this.entityDataMap != null && this.entityDataMap.size() > 0) {
            HashMap<String, EntityReturnInfo> defaultMap = new HashMap<String, EntityReturnInfo>(this.entityDataMap);
            this.entityDataMap = regionDataSet.getEntityDataMap();
            this.entityDataMap.putAll(defaultMap);
        } else {
            this.entityDataMap = regionDataSet.getEntityDataMap();
        }
    }

    public void init(RegionSingleDataSet regionSingleDataSet) {
        this.fileDataMap = regionSingleDataSet.getFileDataMap();
        this.imgDataMap = regionSingleDataSet.getImgDataMap();
        if (this.entityDataMap != null && this.entityDataMap.size() > 0) {
            HashMap<String, EntityReturnInfo> defaultMap = new HashMap<String, EntityReturnInfo>(this.entityDataMap);
            this.entityDataMap = regionSingleDataSet.getEntityDataMap();
            this.entityDataMap.putAll(defaultMap);
        } else {
            this.entityDataMap = regionSingleDataSet.getEntityDataMap();
        }
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public Map<String, List<FileInfo>> getFileDataMap() {
        return this.fileDataMap;
    }

    public void setFileDataMap(Map<String, List<FileInfo>> fileDataMap) {
        this.fileDataMap = fileDataMap;
    }

    public Map<String, List<byte[]>> getImgDataMap() {
        return this.imgDataMap;
    }

    public void setImgDataMap(Map<String, List<byte[]>> imgDataMap) {
        this.imgDataMap = imgDataMap;
    }

    public Map<String, Map<String, EntityData>> getEntityDataInfoMap() {
        return this.entityDataInfoMap;
    }

    public void setEntityDataInfoMap(Map<String, Map<String, EntityData>> entityDataInfoMap) {
        this.entityDataInfoMap = entityDataInfoMap;
    }

    public Map<String, EntityReturnInfo> getEntityDataMap() {
        return this.entityDataMap;
    }

    public void setEntityDataMap(Map<String, EntityReturnInfo> entityDataMap) {
        this.entityDataMap = entityDataMap;
    }

    public Map<String, Set<String>> getEntityCaptionFields() {
        return this.entityCaptionFields;
    }

    public void setEntityCaptionFields(Map<String, Set<String>> entityCaptionFields) {
        this.entityCaptionFields = entityCaptionFields;
    }

    public boolean isJsonData() {
        return this.jsonData;
    }

    public void setJsonData(boolean jsonData) {
        this.jsonData = jsonData;
    }

    public void jsonData() {
        this.jsonData = true;
    }

    public Boolean getEntityMatchAll() {
        return this.entityMatchAll;
    }

    public void setEntityMatchAll(Boolean entityMatchAll) {
        this.entityMatchAll = entityMatchAll;
    }

    public boolean isQueryChildrenCount() {
        return this.queryChildrenCount;
    }

    public void setQueryChildrenCount(boolean queryChildrenCount) {
        this.queryChildrenCount = queryChildrenCount;
    }

    public boolean isDesensitized() {
        return this.desensitized;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }

    public Object getFieldValue(AbstractData value, FieldData fieldData) {
        if (value.isNull) {
            return "";
        }
        if ("\u2014\u2014".equals(value.getAsString())) {
            return value.getAsString();
        }
        if (value instanceof StringData) {
            StringData data = (StringData)value;
            return data.getAsString();
        }
        if (value instanceof CurrencyData) {
            CurrencyData data = (CurrencyData)value;
            try {
                BigDecimal bigDecimal = data.getAsCurrency();
                int fractionDigits = fieldData.getFractionDigits();
                BigDecimal setScale = bigDecimal.setScale(fractionDigits, 4);
                return setScale.toPlainString();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        if (value instanceof FloatData) {
            FloatData data = (FloatData)value;
            try {
                return data.getAsFloat();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        if (value instanceof IntData) {
            IntData data = (IntData)value;
            try {
                return data.getAsInt();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        if (value instanceof DateData) {
            DateData data = (DateData)value;
            try {
                Date date = data.getAsDateObj();
                return DateUtils.dateToString(date);
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        if (value instanceof DateTimeData) {
            DateTimeData data = (DateTimeData)value;
            long dateTime = 0L;
            try {
                dateTime = data.getAsDateTime();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
            FieldType type = FieldType.forValue((int)fieldData.getFieldType());
            if (type == FieldType.FIELD_TYPE_DATE_TIME) {
                return DateUtils.dateToStringTime(new Date(dateTime));
            }
            if (type == FieldType.FIELD_TYPE_TIME) {
                return DateUtils.dateToTime(new Date(dateTime));
            }
            return "";
        }
        if (value instanceof BoolData) {
            BoolData data = (BoolData)value;
            try {
                return data.getAsBool();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        return value.getAsString();
    }

    public Object getData(FieldData fieldData, Object value) {
        FieldType type = FieldType.forValue((int)fieldData.getFieldType());
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            if (type == FieldType.FIELD_TYPE_LOGIC) {
                return false;
            }
            return null;
        }
        switch (type) {
            case FIELD_TYPE_LOGIC: {
                Boolean returnBoolean = BooleanUtil.parseBoolean(value.toString());
                if (null == returnBoolean) {
                    return false;
                }
                return returnBoolean;
            }
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_DECIMAL: {
                BigDecimal bigDecimal;
                int fieldSize = fieldData.getFieldSize();
                try {
                    String tempValue = value.toString();
                    if (tempValue.contains(",")) {
                        Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(tempValue);
                        bigDecimal = new BigDecimal(number.doubleValue());
                    } else {
                        bigDecimal = new BigDecimal(tempValue);
                    }
                    bigDecimal = new BigDecimal(value.toString());
                }
                catch (Exception e) {
                    return null;
                }
                if (fieldData.getFieldValueType() != FieldValueType.FIELD_VALUE_INPUT_ORDER.getValue()) {
                    if (value.toString().contains(".")) {
                        if (fieldSize < value.toString().length() - 1) {
                            return null;
                        }
                        int fractionDigits = fieldData.getFractionDigits();
                        if (fractionDigits < value.toString().length() - value.toString().indexOf(".") - 1) {
                            return null;
                        }
                    } else if (fieldSize < value.toString().length()) {
                        return null;
                    }
                }
                return bigDecimal.doubleValue();
            }
            case FIELD_TYPE_DATE: 
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_TIME: {
                String formatStr = "";
                if (type == FieldType.FIELD_TYPE_DATE) {
                    formatStr = "yyyy-MM-dd";
                } else if (type == FieldType.FIELD_TYPE_DATE_TIME) {
                    formatStr = "yyyy-MM-dd HH:mm:ss";
                } else if (type == FieldType.FIELD_TYPE_TIME) {
                    formatStr = "HH:mm:ss";
                }
                try {
                    Date date = null;
                    if (type == FieldType.FIELD_TYPE_DATE) {
                        date = DateUtils.stringToDate(value.toString());
                    } else if (type == FieldType.FIELD_TYPE_DATE_TIME) {
                        date = DateUtils.stringTimeToDate(value.toString());
                    } else if (type == FieldType.FIELD_TYPE_TIME) {
                        date = DateUtils.timeToDate(value.toString());
                    }
                    if (date != null && DateUtils.checkDate(date)) {
                        return new Time(date.getTime());
                    }
                    return null;
                }
                catch (Exception e) {
                    return null;
                }
            }
            case FIELD_TYPE_INTEGER: {
                try {
                    int intValue = Integer.parseInt(value.toString());
                    return intValue;
                }
                catch (NumberFormatException e) {
                    return null;
                }
            }
            case FIELD_TYPE_STRING: {
                String stringVaule = value.toString();
                return stringVaule;
            }
            case FIELD_TYPE_UUID: {
                String formatValue = value.toString();
                if (UUIDUtils.isUUID((String)formatValue)) {
                    return UUID.fromString(formatValue);
                }
                return null;
            }
        }
        return value.toString();
    }

    public void addEntityData(String entityViewKey, EntityReturnInfo entityReturnInfo) {
        if (this.entityDataMap == null) {
            this.entityDataMap = new HashMap<String, EntityReturnInfo>();
        }
        this.entityDataMap.put(entityViewKey, entityReturnInfo);
    }

    public Map<String, EntityReturnInfo> getSearchDataMap() {
        if (this.searchDataMap == null) {
            this.searchDataMap = new HashMap<String, EntityReturnInfo>();
        }
        return this.searchDataMap;
    }

    public Map<String, DecimalFormat> getFormatMap() {
        return this.formatMap;
    }
}

