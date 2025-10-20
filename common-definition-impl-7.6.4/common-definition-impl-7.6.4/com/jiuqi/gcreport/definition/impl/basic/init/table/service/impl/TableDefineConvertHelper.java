/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  javax.persistence.EntityNotFoundException
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBIndexs;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import javax.persistence.EntityNotFoundException;
import org.springframework.util.ReflectionUtils;

public class TableDefineConvertHelper {
    private BaseEntity entity;
    private DBTable dbTable;
    private DefinitionTableV tableDefine;
    private DefinitionFieldV recidField = null;
    private DefinitionFieldV recverField = null;

    public static TableDefineConvertHelper newInstance(BaseEntity entity, DBTable dbTable) {
        return new TableDefineConvertHelper(entity, dbTable);
    }

    private TableDefineConvertHelper(BaseEntity entity, DBTable dbTable) {
        this.entity = entity;
        this.dbTable = dbTable;
    }

    public DefinitionTableV convert() {
        this.tableDefine = new DefinitionTableV(this.dbTable, this.entity.getTableName());
        this.convertFields();
        this.convertIndexs();
        return this.tableDefine;
    }

    private void convertFields() {
        PropertyDescriptor[] fields;
        try {
            fields = Introspector.getBeanInfo(this.entity.getClass(), this.dbTable.stopSuper()).getPropertyDescriptors();
        }
        catch (IntrospectionException e) {
            throw new EntityNotFoundException("\u8868" + this.entity.getTableName() + "\u5bf9\u5e94\u7684\u5b9e\u4f53\u7c7b\u201c" + this.entity.getClass().getName() + "\u201d\u5c5e\u6027\u4fe1\u606f\u52a0\u8f7d\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        Arrays.stream(fields).forEach(this::convertField);
        if (this.recidField == null && this.dbTable.primaryRequired()) {
            throw new IllegalArgumentException("\u8868" + this.entity.getTableName() + "\u5bf9\u5e94\u7684\u5b9e\u4f53\u7c7b\u201crecid\u5b57\u6bb5\u662f\u5fc5\u987b\u7684");
        }
    }

    private void convertField(PropertyDescriptor propertyDescriptor) {
        Field field = this.getField(this.entity.getClass(), propertyDescriptor, this.dbTable.stopSuper());
        if (field == null) {
            return;
        }
        String fieldNameDb = propertyDescriptor.getName().toUpperCase();
        DBColumn column = this.getAnnotation(field, propertyDescriptor);
        if (column == null) {
            return;
        }
        if (!StringUtils.isEmpty((String)column.nameInDB())) {
            fieldNameDb = column.nameInDB().toUpperCase();
        }
        if (column.isRecid()) {
            if (this.recidField != null) {
                throw new IllegalArgumentException("\u91cd\u590d\u5b9a\u4e49ID\u5b57\u6bb5");
            }
            this.recidField = this.tableDefine.addField(column, fieldNameDb);
        } else if (column.isRecver()) {
            if (this.recverField != null) {
                throw new IllegalArgumentException("\u91cd\u590d\u5b9a\u4e49recver\u5b57\u6bb5");
            }
            this.recverField = this.tableDefine.addField(column, fieldNameDb);
        } else {
            this.tableDefine.addField(column, fieldNameDb);
        }
    }

    private void convertIndexs() {
        DBIndexs indexs;
        if (!this.dbTable.inStorage()) {
            return;
        }
        DBIndex index = this.entity.getClass().getAnnotation(DBIndex.class);
        if (index != null) {
            this.tableDefine.addIndex(index);
        }
        if ((indexs = this.entity.getClass().getAnnotation(DBIndexs.class)) != null && indexs.value().length > 0) {
            for (DBIndex dbIndex : indexs.value()) {
                this.tableDefine.addIndex(dbIndex);
            }
        }
        if (this.dbTable.indexs().length > 0) {
            for (DBIndex dbIndex : this.dbTable.indexs()) {
                this.tableDefine.addIndex(dbIndex);
            }
        }
    }

    private Field getField(Class<?> entityClass, PropertyDescriptor pd, Class<?> stopClass) {
        Field f = ReflectionUtils.findField(entityClass, pd.getName());
        if (f == null || f.getDeclaringClass().isAssignableFrom(stopClass)) {
            return null;
        }
        return f;
    }

    private DBColumn getAnnotation(Field f, PropertyDescriptor pd) {
        DBColumn t = null;
        if (f != null && (t = f.getAnnotation(DBColumn.class)) == null) {
            t = pd.getReadMethod().getAnnotation(DBColumn.class);
        }
        return t;
    }
}

