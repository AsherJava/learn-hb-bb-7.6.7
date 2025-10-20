/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.MappedSuperclass
 */
package com.jiuqi.gcreport.definition.impl.basic.entity;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DefaultTableEntity
extends BaseEntity {
    private static final long serialVersionUID = -9064361917511124995L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true)
    private String id;
    @Column(name="RECVER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long recver;
    private String tableName;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Long getRecver() {
        return this.recver;
    }

    @Override
    public void setRecver(Long recver) {
        this.recver = recver;
    }

    @Override
    public String getTableName() {
        if (!StringUtils.isEmpty((String)this.tableName)) {
            return this.tableName;
        }
        DBTable dbTable = this.getClass().getAnnotation(DBTable.class);
        if (dbTable == null) {
            throw new UnsupportedOperationException(GcI18nUtil.getMessage((String)"ent.definetion.entity.tablename"));
        }
        this.tableName = dbTable.name().toUpperCase();
        return this.tableName;
    }
}

