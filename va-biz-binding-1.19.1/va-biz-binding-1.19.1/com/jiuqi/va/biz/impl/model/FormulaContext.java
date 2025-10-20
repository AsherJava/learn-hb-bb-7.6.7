/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.formula.intf.IFormulaContext
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.va.biz.intf.ref.RefDataBuffer;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.util.ObjectUtils;

public abstract class FormulaContext
implements IFormulaContext {
    private RefDataBuffer refDataBuffer;
    private Map<String, Object> dimValues;

    public RefDataBuffer getRefDataBuffer() {
        if (this.refDataBuffer == null) {
            this.refDataBuffer = new RefDataBuffer();
        }
        return this.refDataBuffer;
    }

    public String getUnitCode() {
        return Env.getUnitCode();
    }

    public Date getBizDate() {
        return Env.getBizDate();
    }

    public Map<String, Object> getDimValues() {
        if (this.dimValues == null) {
            this.dimValues = new HashMap<String, Object>();
            this.dimValues.put("UNITCODE", this.getUnitCode());
            this.dimValues.put("BIZDATE", this.getBizDate());
        }
        return this.dimValues;
    }

    public void resetDimValues() {
        this.dimValues = null;
    }

    @Deprecated
    public Object findRefFieldValue(int refTableType, String refTableName, String id, String fieldName) {
        return this.getRefTableMap(refTableType, refTableName, this.getDimValues()).findRefFieldValue(id, fieldName);
    }

    public Object findRefFieldValue(int refTableType, String refTableName, String id, String fieldName, Map<String, Object> dimValues) {
        return this.getRefTableMap(refTableType, refTableName, dimValues).findRefFieldValue(id, fieldName);
    }

    @Deprecated
    public Map<String, Map<String, Object>> getAll(int refTableType, String refTableName) {
        return this.getRefTableMap(refTableType, refTableName, this.getDimValues()).list();
    }

    @Deprecated
    public Stream<Map<String, Object>> getRefDataByExpression(int refTableType, String refTableName, String expression) {
        return this.getRefTableMap(refTableType, refTableName, this.getDimValues()).filter(expression).stream();
    }

    private RefTableDataMap getRefTableMap(int refTableType, String refTableName, Map<String, Object> dimValues) {
        return this.getRefDataBuffer().getRefTableMap(refTableType, refTableName, dimValues);
    }

    public Object valueOf(Object value, int dataType) {
        if (value instanceof List) {
            return new ArrayData(dataType, (Collection)((List)value));
        }
        switch (dataType) {
            case 10: {
                return BigDecimal.valueOf(value == null ? 0.0 : Double.valueOf(value.toString()));
            }
            case 3: {
                if (value == null) {
                    return 0;
                }
                if (value instanceof Double) {
                    return (double)Convert.cast(value, Double.class);
                }
                if (value instanceof Integer) {
                    return (int)Convert.cast(value, Integer.class);
                }
                if (value instanceof Byte) {
                    return (byte)Convert.cast(value, Byte.class);
                }
                if (value instanceof Long) {
                    return (long)Convert.cast(value, Long.class);
                }
                if (value instanceof Short) {
                    return (short)Convert.cast(value, Short.class);
                }
                if (value instanceof BigDecimal) {
                    return value;
                }
                return (int)Convert.cast(value, Integer.class);
            }
            case 2: {
                Calendar calendar = Calendar.getInstance();
                if (value == null) {
                    calendar.setTime(new Date(0L));
                } else if (value instanceof Date) {
                    calendar.setTime((Date)value);
                } else if (value instanceof Long) {
                    calendar.setTime(new Date((Long)value));
                } else if (value instanceof String) {
                    calendar.setTime(Utils.parseDateTime((String)value));
                } else {
                    calendar = (Calendar)value;
                }
                return calendar;
            }
            case 6: {
                if (value == null) {
                    return "";
                }
                return String.valueOf(value);
            }
            case 1: {
                return Convert.cast(value, Boolean.TYPE);
            }
        }
        return value;
    }

    public Object analyticalValue(Object value, int dataType) {
        switch (dataType) {
            case 2: {
                if (value == null) {
                    return null;
                }
                if (value instanceof Date) {
                    Date date = (Date)value;
                    return date.getTime() == 0L ? null : date;
                }
                Date date = ((Calendar)value).getTime();
                return date.getTime() == 0L ? null : date;
            }
            case 6: {
                return ObjectUtils.isEmpty(value) ? null : value;
            }
        }
        return value;
    }

    public int getRefTableType(String tableName) {
        return FormulaUtils.getRefTableType(tableName);
    }

    public DataModelDO findBaseDataDefine(String tableName) {
        DataModelDTO param = new DataModelDTO();
        param.setBiztype(DataModelType.BizType.BASEDATA);
        param.setName(tableName);
        param.setTenantName(Env.getTenantName());
        return ((DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class)).get(param);
    }

    public String getTenantName() {
        return Env.getTenantName();
    }
}

