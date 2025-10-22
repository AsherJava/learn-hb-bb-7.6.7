/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_SUMHBNOTEMP12", title="SumHb\u516c\u5f0f\u4e34\u65f6\u8868", indexs={@DBIndex(name="IDX_SUMHBNOTEMP_B12", columnsFields={"batchId", "subjectCode"})})
public class SumHbTemp12EO
extends DefaultTableEntity {
    public static final String TABLE_NAME_SUBJECT = "GC_SUMHBNOTEMP12";
    @DBColumn(nameInDB="batchId", title="batchId", dbType=DBColumn.DBType.Varchar, length=36)
    private String batchId;
    @DBColumn(nameInDB="formId", title="\u533a\u57dfID", dbType=DBColumn.DBType.Varchar, length=36)
    private String regionId;
    @DBColumn(nameInDB="subjectCode", title="\u79d1\u76eecode", dbType=DBColumn.DBType.Varchar, length=36)
    private String subjectCode;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String formId) {
        this.regionId = formId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}

