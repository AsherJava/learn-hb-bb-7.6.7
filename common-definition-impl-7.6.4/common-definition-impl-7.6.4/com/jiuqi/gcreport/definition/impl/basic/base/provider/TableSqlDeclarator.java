/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  javax.persistence.Transient
 */
package com.jiuqi.gcreport.definition.impl.basic.base.provider;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.util.EntityFieldInfoUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.BatchDeleteSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.BatchInsertSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.BatchUpdateSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.DeleteSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.InsertSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.UpdateSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.SelectCountSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.SelectSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.gcreport.definition.impl.exception.DefinitionSqlException;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Transient;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class TableSqlDeclarator<Entity extends BaseEntity> {
    private String tableName;
    private Class<Entity> entityType;
    private EntTableDefine entTableDefine;
    private String keyName;
    private Map<String, Method> fieldReadMethods;

    public TableSqlDeclarator(Class<Entity> entityClass, String tableName) {
        this.entityType = entityClass;
        this.tableName = tableName;
        this.cacheFieldMethods();
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        assert (entTableDefineProvider != null);
        this.entTableDefine = entTableDefineProvider.getTableDefine(tableName);
    }

    private void cacheFieldMethods() {
        List<PropertyDescriptor> fields = EntityFieldInfoUtils.getFields(this.entityType);
        this.fieldReadMethods = new HashMap<String, Method>(fields.size());
        fields.forEach(this::cacheFieldMethod);
    }

    private void cacheFieldMethod(PropertyDescriptor propertyDescriptor) {
        Field field = ReflectionUtils.findField(this.entityType, propertyDescriptor.getName());
        Transient t = TableSqlDeclarator.getAnnotation(field, propertyDescriptor, Transient.class);
        if (t != null) {
            return;
        }
        String fieldNameDb = propertyDescriptor.getName().toUpperCase();
        DBColumn dbc = TableSqlDeclarator.getAnnotation(field, propertyDescriptor, DBColumn.class);
        if (dbc != null) {
            if (StringUtils.hasText(dbc.nameInDB())) {
                fieldNameDb = dbc.nameInDB().toUpperCase();
            }
            if (dbc.isRecid()) {
                this.keyName = fieldNameDb;
            }
        }
        this.fieldReadMethods.put(fieldNameDb, propertyDescriptor.getReadMethod());
    }

    private static <T extends Annotation> T getAnnotation(Field f, PropertyDescriptor pd, Class<T> clz) {
        T t = null;
        if (f != null) {
            t = f.getAnnotation(clz);
            try {
                if (t == null) {
                    t = pd.getReadMethod().getAnnotation(clz);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return t;
    }

    public String getTableName() {
        return this.tableName;
    }

    public EntTableDefine getEntTableDefine() {
        return this.entTableDefine;
    }

    public void setEntTableDefine(EntTableDefine entTableDefine) {
        this.entTableDefine = entTableDefine;
    }

    private Object getFieldValue(Entity entity, EntFieldDefine fDefine) {
        Object value = null;
        Method readMethod = this.fieldReadMethods.get(fDefine.getCode());
        if (readMethod != null) {
            try {
                value = readMethod.invoke(entity, new Object[0]);
            }
            catch (Exception e) {
                Object[] i18Args = new String[]{entity.getClass().getCanonicalName(), readMethod.getName()};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"ent.definition.class.method.read.error", (Object[])i18Args), (Throwable)e);
            }
        }
        if (value == null) {
            if (((AbstractFieldDynamicDeclarator)entity).hasField(fDefine.getCode())) {
                value = ((AbstractFieldDynamicDeclarator)entity).getFieldValue(fDefine.getCode());
            } else if (((AbstractFieldDynamicDeclarator)entity).hasField(fDefine.getName())) {
                value = ((AbstractFieldDynamicDeclarator)entity).getFieldValue(fDefine.getName());
            }
        }
        if (fDefine.getType() == 1 && value instanceof String) {
            value = ((String)value).getBytes();
        }
        return value;
    }

    public BatchInsertSql getSqlBatchInsert(List<Entity> entities) {
        BatchInsertSql sql = BatchInsertSql.newInstance(this.tableName, entities.size());
        List<String> fields = this.getEntTableDefine().getAllFields().stream().map(EntFieldDefine::getName).collect(Collectors.toList());
        sql.setColumns(fields);
        entities.forEach(entity -> {
            List<Object> row = this.getRowValue(entity);
            sql.addRowValues(row);
        });
        return sql;
    }

    private List<Object> getRowValue(Entity entity) {
        ArrayList<Object> values = new ArrayList<Object>();
        for (EntFieldDefine c : this.getEntTableDefine().getAllFields()) {
            values.add(this.getFieldValue(entity, c));
        }
        return values;
    }

    public BatchUpdateSql getSqlBatchUpdate(List<Entity> entities) {
        BatchUpdateSql sql = BatchUpdateSql.newInstance(this.tableName, entities.size());
        List<String> fields = this.getEntTableDefine().getAllFields().stream().map(EntFieldDefine::getName).collect(Collectors.toList());
        sql.setColumns(fields);
        ArrayList<String> filterFields = new ArrayList<String>();
        filterFields.add(this.keyName);
        sql.setConditionFields(filterFields);
        entities.forEach(entity -> {
            Object keyValue = this.getFieldValue(entity, this.getEntTableDefine().getField(this.keyName));
            if (keyValue == null) {
                throw new DefinitionSqlException("\u6570\u636e\u4e3b\u952e\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u751f\u6210\u6709\u6548\u66f4\u65b0SQL\uff0c\u8bf7\u81ea\u5b9a\u4e49SQL\u3002");
            }
            List<Object> row = this.getRowValue(entity);
            row.add(keyValue);
            sql.addRowValues(row);
        });
        return sql;
    }

    public BatchDeleteSql getSqlBatchDelete(List<Entity> entities) {
        BatchDeleteSql sql = BatchDeleteSql.newInstance(this.tableName, entities.size());
        ArrayList<String> filterFields = new ArrayList<String>();
        filterFields.add(this.keyName);
        sql.setConditionFields(filterFields);
        entities.forEach(entity -> {
            Object keyValue = this.getFieldValue(entity, this.getEntTableDefine().getField(this.keyName));
            if (keyValue == null) {
                throw new DefinitionSqlException(GcI18nUtil.getMessage((String)"ent.definetion.orm.sql.error"));
            }
            ArrayList<Object> row = new ArrayList<Object>();
            row.add(keyValue);
            sql.addRowValues(row);
        });
        return sql;
    }

    public InsertSql getSqlInsert(Entity entity, boolean selective) {
        InsertSql sql = InsertSql.newInstance(this.getTableName());
        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<Object> values = new ArrayList<Object>();
        for (EntFieldDefine c : this.getEntTableDefine().getAllFields()) {
            Object fieldValue = this.getFieldValue(entity, c);
            boolean needAdd = !selective || fieldValue != null;
            if (!needAdd) continue;
            fields.add(c.getName());
            values.add(fieldValue);
        }
        sql.setColumns(fields);
        sql.addRowValues(values);
        return sql;
    }

    public UpdateSql getSqlUpdate(Entity entity, boolean selective) {
        Object keyValue = this.getFieldValue(entity, this.getEntTableDefine().getField(this.keyName));
        if (keyValue == null) {
            throw new DefinitionSqlException(GcI18nUtil.getMessage((String)"ent.definetion.orm.sql.error"));
        }
        UpdateSql sql = UpdateSql.newInstance(this.getTableName());
        ArrayList<Object> values = new ArrayList<Object>();
        ArrayList<String> fields = new ArrayList<String>();
        for (EntFieldDefine c : this.getEntTableDefine().getAllFields()) {
            Object fieldValue = this.getFieldValue(entity, c);
            if (selective && fieldValue == null || this.keyName.equals(c.getCode())) continue;
            fields.add(c.getName());
            values.add(fieldValue);
        }
        sql.setColumns(fields);
        ArrayList<String> filterFields = new ArrayList<String>();
        filterFields.add(this.keyName);
        sql.setConditionFields(filterFields);
        values.add(keyValue);
        sql.addRowValues(values);
        return sql;
    }

    public DeleteSql getSqlDelete(Entity entity) {
        DeleteSql sql = DeleteSql.newInstance(this.getTableName());
        Object keyValue = this.getFieldValue(entity, this.getEntTableDefine().getField(this.keyName));
        if (keyValue == null) {
            throw new DefinitionSqlException(GcI18nUtil.getMessage((String)"ent.definetion.orm.sql.error"));
        }
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(this.keyName);
        sql.setConditionFields(fields);
        ArrayList<Object> row = new ArrayList<Object>();
        row.add(keyValue);
        sql.addRowValues(row);
        return sql;
    }

    public <S> SelectSql<S> getSqlSelectWithKey(Entity entity, EntResultSetExtractor<S> rsExtractor) {
        SelectSql<S> sql = new SelectSql<S>(this.tableName, rsExtractor);
        for (EntFieldDefine fieldDefine : this.getEntTableDefine().getAllFields()) {
            sql.addColumn(fieldDefine.getName(), fieldDefine.getName());
        }
        Object keyValue = this.getFieldValue(entity, this.getEntTableDefine().getField(this.keyName));
        if (keyValue == null) {
            throw new DefinitionSqlException(GcI18nUtil.getMessage((String)"ent.definetion.orm.sql.error"));
        }
        sql.addCondition(this.keyName, keyValue);
        return sql;
    }

    public <S> SelectSql<S> getSqlSelect(Entity object, EntResultSetExtractor<S> rsExtractor) {
        SelectSql<S> sql = new SelectSql<S>(this.tableName, rsExtractor);
        for (EntFieldDefine fieldDefine : this.getEntTableDefine().getAllFields()) {
            sql.addColumn(fieldDefine.getName(), fieldDefine.getName());
            Object fieldValue = this.getFieldValue(object, fieldDefine);
            if (fieldValue == null) continue;
            sql.addCondition(fieldDefine.getName(), fieldValue);
        }
        return sql;
    }

    public SelectCountSql getSqlSelectCount(Entity object) {
        SelectCountSql sql = new SelectCountSql(this.tableName);
        for (EntFieldDefine fieldDefine : this.getEntTableDefine().getAllFields()) {
            Object fieldValue = this.getFieldValue(object, fieldDefine);
            if (fieldValue == null) continue;
            sql.addCondition(fieldDefine.getName(), fieldValue);
        }
        return sql;
    }
}

