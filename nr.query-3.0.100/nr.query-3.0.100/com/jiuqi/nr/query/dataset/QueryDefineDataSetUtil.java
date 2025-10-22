/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QuerySelectField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryDefineDataSetUtil {
    public static FieldType getDSFieldType(FieldDefine field) {
        switch (field.getType()) {
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_LOGIC: 
            case FIELD_TYPE_DECIMAL: {
                return FieldType.MEASURE;
            }
            case FIELD_TYPE_STRING: {
                if (field.getCode().equalsIgnoreCase("default_period")) {
                    return FieldType.TIME_DIM;
                }
                return FieldType.GENERAL_DIM;
            }
            case FIELD_TYPE_GENERAL: 
            case FIELD_TYPE_UUID: 
            case FIELD_TYPE_DATE: 
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_TIME: {
                return FieldType.GENERAL_DIM;
            }
        }
        return FieldType.DESCRIPTION;
    }

    public static List<QuerySelectField> getSelectFieldsInBlock(QueryBlockDefine block) {
        Optional<QueryDimensionDefine> fieldDim;
        List<QuerySelectField> selectedFields = new ArrayList<QuerySelectField>();
        List<QueryDimensionDefine> dims = block.getQueryDimensions();
        if (dims != null && (fieldDim = dims.stream().filter(idx -> idx.getDimensionType() == QueryDimensionType.QDT_FIELD).findFirst()).isPresent()) {
            QueryDimensionDefine fd = fieldDim.get();
            selectedFields = fd.getSelectFields();
        }
        return selectedFields;
    }

    public static int fieldTypeToDataType(com.jiuqi.np.definition.common.FieldType fieldType) {
        switch (fieldType) {
            case FIELD_TYPE_GENERAL: {
                return 6;
            }
            case FIELD_TYPE_FLOAT: {
                return 3;
            }
            case FIELD_TYPE_STRING: {
                return 6;
            }
            case FIELD_TYPE_INTEGER: {
                return 5;
            }
            case FIELD_TYPE_LOGIC: {
                return 1;
            }
            case FIELD_TYPE_DATE: {
                return 2;
            }
            case FIELD_TYPE_DATE_TIME: {
                return 2;
            }
            case FIELD_TYPE_TIME: {
                return 2;
            }
            case FIELD_TYPE_DECIMAL: {
                return 10;
            }
            case FIELD_TYPE_BINARY: {
                return 9;
            }
            case FIELD_TYPE_FILE: {
                return 9;
            }
            case FIELD_TYPE_PICTURE: {
                return 9;
            }
            case FIELD_TYPE_TEXT: {
                return 9;
            }
            case FIELD_TYPE_UUID: {
                return 6;
            }
        }
        return 0;
    }

    public static TimeGranularity adaptTimeGranularity(String periodType) {
        PeriodType ptype = PeriodType.valueOf((String)periodType);
        if (ptype == PeriodType.YEAR) {
            return TimeGranularity.YEAR;
        }
        if (ptype == PeriodType.HALFYEAR) {
            return TimeGranularity.HALFYEAR;
        }
        if (ptype == PeriodType.SEASON) {
            return TimeGranularity.QUARTER;
        }
        if (ptype == PeriodType.MONTH) {
            return TimeGranularity.MONTH;
        }
        if (ptype == PeriodType.TENDAY) {
            return TimeGranularity.XUN;
        }
        if (ptype == PeriodType.DAY) {
            return TimeGranularity.DAY;
        }
        return TimeGranularity.MONTH;
    }

    public static String getDSFieldName(String tableName, String fieldName) {
        if (fieldName.length() + tableName.length() < 30) {
            return tableName + "_" + fieldName;
        }
        return fieldName;
    }

    public static String getRefTitleField(FieldDefine field, TableRunInfo info) {
        return QueryDefineDataSetUtil.getRefNamePrefix(field, info) + "_TITLE";
    }

    public static String getRefCodeField(FieldDefine field, TableRunInfo info) {
        return QueryDefineDataSetUtil.getRefNamePrefix(field, info) + "_CODE";
    }

    public static String getRefTitlePrefix(FieldDefine field, TableRunInfo refTableInfo) {
        return refTableInfo.getTableDefine().getTitle();
    }

    public static String getRefNamePrefix(FieldDefine field, TableRunInfo info) {
        return info.getDimensionName(field.getCode());
    }
}

