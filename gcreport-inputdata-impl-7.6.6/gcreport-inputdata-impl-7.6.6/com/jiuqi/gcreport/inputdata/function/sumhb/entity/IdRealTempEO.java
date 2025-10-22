/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_IDREALNOTEMP", title="id\u4e34\u65f6\u8868\u5b9e\u4f53")
public class IdRealTempEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_IDREALTEMP";
    public static final String TABLE_NAME_ORG = "GC_IDREALNOTEMP";
    @DBColumn(nameInDB="batchId", title="batchId", dbType=DBColumn.DBType.Varchar, length=36)
    private String batchId;
    @DBColumn(nameInDB="orgCode", title="\u5355\u4f4dcode", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgCode;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

