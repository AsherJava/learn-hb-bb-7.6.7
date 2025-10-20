/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_DATACHECKCONFIG", title="\u6570\u636e\u7a3d\u6838\u65b9\u6848\u914d\u7f6e\u8868", inStorage=true, indexs={})
public class DataCheckConfigEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DATACHECKCONFIG";
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="FORMS_INFO", title="\u5df2\u9009\u62a5\u8868\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String formsInfo;
    @DBColumn(nameInDB="ORG_MAX_LENGTH", title="\u5355\u4f4d\u6570\u91cf\u53ef\u9009\u9600\u503c", dbType=DBColumn.DBType.Int, length=5)
    private Integer orgMaxLength;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getFormsInfo() {
        return this.formsInfo;
    }

    public void setFormsInfo(String formsInfo) {
        this.formsInfo = formsInfo;
    }

    public Integer getOrgMaxLength() {
        return this.orgMaxLength;
    }

    public void setOrgMaxLength(Integer orgMaxLength) {
        this.orgMaxLength = orgMaxLength;
    }
}

