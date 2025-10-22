/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.inputdata.inputdata.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_UNITSCENESNOTEMP", title="\u6302\u8d26\u7c7b\u578b\u7b5b\u9009\u4e34\u65f6\u8868\u5b9e\u4f53")
public class UnitScenesTempEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_UNITSCENESTEMP";
    public static final String TABLE_NAME_NO_TEMP = "GC_UNITSCENESNOTEMP";
    @DBColumn(nameInDB="batchId", title="batchId", dbType=DBColumn.DBType.Varchar)
    private String batchId;
    @DBColumn(title="\u672c\u65b9\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar)
    private String mdCode;
    @DBColumn(title="\u5bf9\u65b9\u65b9\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar)
    private String oppUnitCode;
    @DBColumn(nameInDB="GCBUSINESSTYPE", title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String gcBusinessType;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getGcBusinessType() {
        return this.gcBusinessType;
    }

    public void setGcBusinessType(String gcBusinessType) {
        this.gcBusinessType = gcBusinessType;
    }
}

