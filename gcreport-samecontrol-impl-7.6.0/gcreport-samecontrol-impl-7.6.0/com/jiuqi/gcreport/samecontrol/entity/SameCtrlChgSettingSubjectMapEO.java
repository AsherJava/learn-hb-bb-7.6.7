/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_SAMECTRL_SUBJECTMAP", title="\u79d1\u76ee\u6620\u5c04")
public class SameCtrlChgSettingSubjectMapEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRL_SUBJECTMAP";
    @DBColumn(title="\u62a5\u8868\u65b9\u6848\u6620\u5c04Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeMappingId;
    @DBColumn(title="\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u5f53\u5e74\u5408\u5e76\u79d1\u76ee", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String currSubjectCode;
    @DBColumn(title="\u4e0a\u5e74\u5408\u5e76\u79d1\u76ee", dbType=DBColumn.DBType.Text, isRequired=true)
    private String historySubjectCodes;

    public String getSchemeMappingId() {
        return this.schemeMappingId;
    }

    public void setSchemeMappingId(String schemeMappingId) {
        this.schemeMappingId = schemeMappingId;
    }

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

    public String getCurrSubjectCode() {
        return this.currSubjectCode;
    }

    public void setCurrSubjectCode(String currSubjectCode) {
        this.currSubjectCode = currSubjectCode;
    }

    public String getHistorySubjectCodes() {
        return this.historySubjectCodes;
    }

    public void setHistorySubjectCodes(String historySubjectCodes) {
        this.historySubjectCodes = historySubjectCodes;
    }
}

