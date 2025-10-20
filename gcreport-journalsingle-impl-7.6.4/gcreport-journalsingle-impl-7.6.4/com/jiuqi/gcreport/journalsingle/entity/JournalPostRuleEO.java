/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.journalsingle.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_JOURNALPOSTRULE", inStorage=true, title="\u65e5\u8bb0\u8d26\u8fc7\u8d26\u65b9\u6848")
public class JournalPostRuleEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_JOURNALPOSTRULE";
    @DBColumn(length=36, nameInDB="TASKID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(length=36, nameInDB="SCHEMEID", dbType=DBColumn.DBType.Varchar)
    private String schemeId;
    @DBColumn(length=36, nameInDB="FORMID", dbType=DBColumn.DBType.Varchar)
    private String formId;
    @DBColumn(length=36, nameInDB="ZBID", dbType=DBColumn.DBType.Varchar)
    private String zbId;
    @DBColumn(nameInDB="CALCATERULE", dbType=DBColumn.DBType.Int)
    private Integer calcateRule;
    @DBColumn(length=100, nameInDB="FORMULA", dbType=DBColumn.DBType.NVarchar)
    private String formula;
    @DBColumn(length=16, nameInDB="ZBNAME", dbType=DBColumn.DBType.NVarchar)
    private String zbName;
    @DBColumn(length=100, nameInDB="ZBTITLE", dbType=DBColumn.DBType.NVarchar)
    private String zbTitle;
    @DBColumn(nameInDB="SUBJECTFLAG", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer subjectFlag;
    @DBColumn(length=16, nameInDB="SUBJECTCODE", dbType=DBColumn.DBType.NVarchar)
    private String subjectCode;

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

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getZbId() {
        return this.zbId;
    }

    public void setZbId(String zbId) {
        this.zbId = zbId;
    }

    public Integer getCalcateRule() {
        return this.calcateRule;
    }

    public void setCalcateRule(Integer calcateRule) {
        this.calcateRule = calcateRule;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getZbName() {
        return this.zbName;
    }

    public void setZbName(String zbName) {
        this.zbName = zbName;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public Integer getSubjectFlag() {
        return this.subjectFlag;
    }

    public void setSubjectFlag(Integer subjectFlag) {
        this.subjectFlag = subjectFlag;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}

