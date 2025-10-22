/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.rewritesetting.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_REWRITE_SETTING", title="\u56de\u5199\u8bbe\u7f6e\u8868")
public class RewriteSettingEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_REWRITE_SETTING";
    @DBColumn(title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String subjectCode;
    @DBColumn(title="\u96c6\u56e2\u5185\u6d6e\u52a8\u884c", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String insideReginonKey;
    @DBColumn(title="\u96c6\u56e2\u5185\u62a5\u8868key", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String insideFormKey;
    @DBColumn(title="\u96c6\u56e2\u5185\u5b58\u50a8\u8868key", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String insideTableName;
    @DBColumn(title="\u96c6\u56e2\u5916\u6d6e\u52a8\u884c", dbType=DBColumn.DBType.NVarchar)
    private String outsideReginonKey;
    @DBColumn(title="\u96c6\u56e2\u5916\u62a5\u8868key", dbType=DBColumn.DBType.NVarchar)
    private String outsideFormKey;
    @DBColumn(title="\u96c6\u56e2\u5916\u5b58\u50a8\u8868key", dbType=DBColumn.DBType.NVarchar)
    private String outsideTableName;
    @DBColumn(title="\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String rewSetGroupId;
    @DBColumn(title="\u4e3b\u952e", dbType=DBColumn.DBType.NVarchar, length=300)
    private String masterColumnCodes;
    @DBColumn(title="\u5b57\u6bb5\u6620\u5c04", dbType=DBColumn.DBType.NVarchar, length=2000)
    private String fieldMapping;
    @DBColumn(title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric)
    private Double ordinal;

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

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getInsideReginonKey() {
        return this.insideReginonKey;
    }

    public void setInsideReginonKey(String insideReginonKey) {
        this.insideReginonKey = insideReginonKey;
    }

    public String getInsideFormKey() {
        return this.insideFormKey;
    }

    public void setInsideFormKey(String insideFormKey) {
        this.insideFormKey = insideFormKey;
    }

    public String getOutsideReginonKey() {
        return this.outsideReginonKey;
    }

    public void setOutsideReginonKey(String outsideReginonKey) {
        this.outsideReginonKey = outsideReginonKey;
    }

    public String getOutsideFormKey() {
        return this.outsideFormKey;
    }

    public void setOutsideFormKey(String outsideFormKey) {
        this.outsideFormKey = outsideFormKey;
    }

    public String getInsideTableName() {
        return this.insideTableName;
    }

    public void setInsideTableName(String insideTableName) {
        this.insideTableName = insideTableName;
    }

    public String getOutsideTableName() {
        return this.outsideTableName;
    }

    public void setOutsideTableName(String outsideTableName) {
        this.outsideTableName = outsideTableName;
    }

    public String getRewSetGroupId() {
        return this.rewSetGroupId;
    }

    public void setRewSetGroupId(String rewSetGroupId) {
        this.rewSetGroupId = rewSetGroupId;
    }

    public String getMasterColumnCodes() {
        return this.masterColumnCodes;
    }

    public void setMasterColumnCodes(String masterColumnCodes) {
        this.masterColumnCodes = masterColumnCodes;
    }

    public String getFieldMapping() {
        return this.fieldMapping;
    }

    public void setFieldMapping(String fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }
}

