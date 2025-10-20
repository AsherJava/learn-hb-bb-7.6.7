/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 */
package com.jiuqi.dc.base.common.definition;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DcDefaultTableEntity
extends BaseEntity {
    private static final long serialVersionUID = -900450935682693969L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true, order=0)
    private String id;
    private String tableName;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        if (!StringUtils.isEmpty((String)this.tableName)) {
            return this.tableName;
        }
        DBTable dbTable = ((Object)((Object)this)).getClass().getAnnotation(DBTable.class);
        if (dbTable == null) {
            throw new UnsupportedOperationException(((Object)((Object)this)).getClass() + "\u6ca1\u6709DBTable\u6ce8\u89e3,\u8bf7\u68c0\u67e5");
        }
        this.tableName = dbTable.name().toUpperCase();
        return this.tableName;
    }
}

