/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.gcreport.billcore.util;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLHelper {
    private StringBuffer insertSql;
    private List<String> insertColumnCodeList;
    private Map<String, ColumnModelType> columnCode2TypeMap;
    private String tableName;
    private static final Logger log = LoggerFactory.getLogger(SQLHelper.class);

    public SQLHelper(String tableName) {
        this.tableName = tableName;
    }

    private String sqlHead(FieldDefine fieldDefine) {
        String str = "@" + fieldDefine.getCode() + " ";
        switch (fieldDefine.getType()) {
            case FIELD_TYPE_DECIMAL: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_INTEGER: {
                return str + "double";
            }
            case FIELD_TYPE_LOGIC: {
                return str + "boolean";
            }
            case FIELD_TYPE_DATE_TIME: 
            case FIELD_TYPE_DATE: {
                return str + "Date";
            }
            case FIELD_TYPE_STRING: {
                return str + "string";
            }
        }
        System.out.println(fieldDefine.getType());
        return str + "string";
    }

    private List<String> initInsertSql(Map<String, Object> fields) {
        if (null != this.insertSql) {
            return this.insertColumnCodeList;
        }
        if (this.columnCode2TypeMap == null) {
            this.columnCode2TypeMap = new HashMap<String, ColumnModelType>();
        }
        Map code2FieldDefineMap = NrTool.queryAllColumnsInTable((String)this.tableName).stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity(), (v1, v2) -> v2));
        StringBuffer codePart = new StringBuffer(128);
        StringBuffer valuePart = new StringBuffer(128);
        ArrayList<String> insertColumnCodeList = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            ColumnModelDefine fieldDefine = (ColumnModelDefine)code2FieldDefineMap.get(entry.getKey());
            if (null == fieldDefine) continue;
            this.columnCode2TypeMap.put(fieldDefine.getCode(), fieldDefine.getColumnType());
            codePart.append(fieldDefine.getCode()).append(",");
            insertColumnCodeList.add(fieldDefine.getCode());
            valuePart.append("?,");
        }
        if (valuePart.length() == 0) {
            return insertColumnCodeList;
        }
        codePart.setLength(codePart.length() - 1);
        valuePart.setLength(valuePart.length() - 1);
        this.insertSql = new StringBuffer(516);
        this.insertSql.append(" insert into ").append(this.tableName).append("\n");
        this.insertSql.append(" (").append(codePart).append(") \n");
        this.insertSql.append("  values (").append(valuePart).append(") \n");
        this.insertColumnCodeList = Collections.unmodifiableList(insertColumnCodeList);
        return insertColumnCodeList;
    }

    public void saveData(Map<String, Object> fields) {
        if (null == fields.get("ID")) {
            fields.put("ID", UUIDUtils.newUUIDStr());
        }
        List<String> insertColumnCodeList = this.initInsertSql(fields);
        Object[] values = new Object[insertColumnCodeList.size()];
        for (int i = 0; i < values.length; ++i) {
            values[i] = this.getValueByColumnType(fields, insertColumnCodeList.get(i), this.columnCode2TypeMap.get(insertColumnCodeList.get(i)));
        }
        if (null != this.insertSql) {
            try {
                EntNativeSqlDefaultDao.getInstance().execute(this.insertSql.toString(), values);
            }
            catch (Exception e) {
                e.printStackTrace();
                log.error(this.insertSql.toString());
                StringBuilder logStr = new StringBuilder(64);
                for (Map.Entry<String, Object> entry : fields.entrySet()) {
                    logStr.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
                }
                log.error(logStr.toString());
                throw e;
            }
        }
    }

    private Object getValueByColumnType(Map<String, Object> fields, String field, ColumnModelType fieldType) {
        if (null == fieldType) {
            return null;
        }
        if (fields.get(field) == null) {
            return null;
        }
        switch (fieldType) {
            case BIGDECIMAL: 
            case DOUBLE: {
                return ConverterUtils.getAsDouble((Object)fields.get(field));
            }
            case INTEGER: {
                return ConverterUtils.getAsInteger((Object)fields.get(field));
            }
            case BOOLEAN: {
                return ConverterUtils.getAsBoolean((Object)fields.get(field));
            }
            case DATETIME: {
                return fields.get(field) instanceof Date ? fields.get(field) : DateUtils.parse((String)((String)fields.get(field)));
            }
            case STRING: {
                return ConverterUtils.getAsString((Object)fields.get(field));
            }
        }
        return ConverterUtils.getAsString((Object)fields.get(field));
    }
}

