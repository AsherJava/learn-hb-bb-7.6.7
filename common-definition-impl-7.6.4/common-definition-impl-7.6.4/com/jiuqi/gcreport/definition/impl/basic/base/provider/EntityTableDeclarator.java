/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.math.NumberUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.base.provider;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.util.EntityFieldInfoUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.util.UUIDTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class EntityTableDeclarator<Entity extends BaseEntity> {
    private Class<Entity> entityClass;
    private Map<String, Method> fieldReadMethods;
    private Map<String, Method> fieldWriteMethods;
    private String tableName;

    public EntityTableDeclarator(Class<Entity> entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName.toUpperCase();
        this.cacheFieldMethods();
    }

    private void cacheFieldMethods() {
        List<PropertyDescriptor> fields = EntityFieldInfoUtils.getFields(this.entityClass);
        this.fieldReadMethods = new HashMap<String, Method>(fields.size());
        this.fieldWriteMethods = new HashMap<String, Method>(fields.size());
        fields.forEach(this::cacheFieldMethod);
    }

    private void cacheFieldMethod(PropertyDescriptor propertyDescriptor) {
        String fieldNameDb = propertyDescriptor.getName().toUpperCase();
        DBColumn column = this.getAnnotation(ReflectionUtils.findField(this.entityClass, propertyDescriptor.getName()), propertyDescriptor);
        if (column != null && StringUtils.hasText(column.nameInDB())) {
            fieldNameDb = column.nameInDB().toUpperCase();
        }
        this.fieldReadMethods.put(fieldNameDb, propertyDescriptor.getReadMethod());
        this.fieldWriteMethods.put(fieldNameDb, propertyDescriptor.getWriteMethod());
    }

    private DBColumn getAnnotation(Field f, PropertyDescriptor pd) {
        DBColumn t = null;
        if (f != null && (t = f.getAnnotation(DBColumn.class)) == null) {
            t = pd.getReadMethod().getAnnotation(DBColumn.class);
        }
        return t;
    }

    public Class<Entity> getEntityClass() {
        return this.entityClass;
    }

    public Object getValue(Object data, String field) {
        field = field.toUpperCase();
        try {
            if (this.fieldReadMethods.containsKey(field)) {
                return this.fieldReadMethods.get(field).invoke(data, new Object[0]);
            }
            if (data instanceof AbstractFieldDynamicDeclarator) {
                return ((AbstractFieldDynamicDeclarator)data).getFieldValue(field);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object setValue(Object data, String field, Object value) {
        field = field.toUpperCase();
        try {
            if (this.fieldWriteMethods.containsKey(field)) {
                value = this.cast(value, field);
                this.fieldWriteMethods.get(field).invoke(data, value);
            }
            if (data instanceof AbstractFieldDynamicDeclarator) {
                ((AbstractFieldDynamicDeclarator)data).addFieldValue(field, value);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private Object cast(Object value, String field) throws SQLException, ParseException {
        Class<?> type = this.getFieldType(field);
        if (type == null) {
            return value;
        }
        if (value == null) {
            return null;
        }
        if (value.getClass().equals(type)) {
            return value;
        }
        if (type.equals(String.class)) {
            return value.toString();
        }
        if (type.equals(BigDecimal.class)) {
            return NumberUtils.createBigDecimal((String)value.toString());
        }
        if (type.equals(Number.class)) {
            return NumberUtils.createNumber((String)value.toString());
        }
        if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
            return BooleanUtils.toBoolean((String)value.toString());
        }
        if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
            return NumberUtils.toInt((String)value.toString());
        }
        if (type.equals(Long.class) || type.equals(Long.TYPE)) {
            if (value instanceof Date) {
                return ((Date)value).getTime();
            }
            return NumberUtils.toLong((String)value.toString());
        }
        if (type.equals(Float.class) || type.equals(Float.TYPE)) {
            return Float.valueOf(NumberUtils.toFloat((String)value.toString()));
        }
        if (type.equals(Double.class) || type.equals(Double.TYPE)) {
            return NumberUtils.toDouble((String)value.toString());
        }
        if (type.equals(byte[].class)) {
            return value.toString().getBytes();
        }
        if (type.equals(Date.class)) {
            if (value instanceof Long) {
                return new Date((Long)value);
            }
            return new SimpleDateFormat().parse(value.toString());
        }
        if (type.equals(LocalDate.class)) {
            if (value instanceof Date) {
                return ((Date)value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            if (value instanceof Long) {
                return new Date((Long)value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            return new SimpleDateFormat().parse(value.toString()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (type.equals(Instant.class)) {
            if (value instanceof Date) {
                return Instant.ofEpochMilli(((Date)value).getTime());
            }
            if (value instanceof Long) {
                return Instant.ofEpochMilli((Long)value);
            }
            return Instant.ofEpochMilli(new SimpleDateFormat().parse(value.toString()).getTime());
        }
        if (type.equals(UUID.class)) {
            return UUIDTool.fromObject(value);
        }
        try {
            return type.cast(value);
        }
        catch (ClassCastException cce) {
            throw new SQLException("Conversion not supported for type " + type.getName(), cce);
        }
    }

    public Class<?> getFieldType(String field) {
        if (this.fieldReadMethods.containsKey(field = field.toUpperCase())) {
            return this.fieldReadMethods.get(field).getReturnType();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}

