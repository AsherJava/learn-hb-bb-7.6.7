/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.carryover.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CARRYOVER_LOG_EXTEND", title="\u5e74\u7ed3\u65e5\u5fd7\u62d3\u5c55\u8868")
public class CarryOverLogExtendEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CARRYOVER_LOG_EXTEND";
    @DBColumn(nameInDB="LOGID", title="\u5e74\u7ed3\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, length=80, isRequired=true)
    private String logId;
    @DBColumn(nameInDB="EXTENDNAME", title="\u62d3\u5c55\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=50)
    private String extendName;
    @DBColumn(nameInDB="EXTENDVALUE", title="\u62d3\u5c55\u503c", dbType=DBColumn.DBType.Varchar, length=100)
    private String extendValue;

    public String getExtendName() {
        return this.extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

    public String getExtendValue() {
        return this.extendValue;
    }

    public void setExtendValue(String extendValue) {
        this.extendValue = extendValue;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}

