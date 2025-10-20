/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_PRIMARY_WORKPAPER_SETTING", title="\u4e3b\u8868\u5de5\u4f5c\u5e95\u7a3f\u8bbe\u7f6e")
public class PrimaryWorkPaperSettingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PRIMARY_WORKPAPER_SETTING";
    @DBColumn(title="\u4efb\u52a1", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar)
    private String schemeId;
    @DBColumn(title="\u5408\u5e76\u62a5\u8868\u4f53\u7cfb", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String reportSystem;
    @DBColumn(title="\u4e3b\u8868\u5de5\u4f5c\u5e95\u7a3f\u7c7b\u578bId", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String primaryTypeId;
    @DBColumn(title="\u62a5\u8868\u9879\u76ee\u540d\u79f0", length=100, dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String boundZbTitle;
    @DBColumn(title="\u5173\u8054\u6307\u6807Id", dbType=DBColumn.DBType.Varchar)
    private String boundZbId;
    @DBColumn(title="\u5173\u8054\u62b5\u9500\u79d1\u76ee", dbType=DBColumn.DBType.Text, isRequired=true)
    private String boundSubjectCodes;
    @DBColumn(title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer orient;
    @DBColumn(title="\u79d1\u76ee\u4ee3\u7801", length=50, dbType=DBColumn.DBType.Varchar)
    private String subjectCode;
    @DBColumn(title="\u62a5\u8868\u9879\u76ee\u5bf9\u8c61", dbType=DBColumn.DBType.Text)
    private String boundZbJson;
    @DBColumn(title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric, isRequired=true)
    private Double ordinal;
    @DBColumn(title="\u662f\u5426\u5141\u8bb8\u7f16\u8f91", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer edit = 0;

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

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public String getPrimaryTypeId() {
        return this.primaryTypeId;
    }

    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }

    public String getBoundZbId() {
        return this.boundZbId;
    }

    public void setBoundZbId(String boundZbId) {
        this.boundZbId = boundZbId;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getBoundSubjectCodes() {
        return this.boundSubjectCodes;
    }

    public void setBoundSubjectCodes(String boundSubjectCodes) {
        this.boundSubjectCodes = boundSubjectCodes;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getEdit() {
        return this.edit;
    }

    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    public String getBoundZbTitle() {
        return this.boundZbTitle;
    }

    public void setBoundZbTitle(String boundZbTitle) {
        this.boundZbTitle = boundZbTitle;
    }

    public String getBoundZbJson() {
        return this.boundZbJson;
    }

    public void setBoundZbJson(String boundZbJson) {
        this.boundZbJson = boundZbJson;
    }
}

