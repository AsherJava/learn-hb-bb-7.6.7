/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.multicriteria.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_MULTICRITERIA", title="\u591a\u51c6\u5219\u8f6c\u6362\u8868")
public class GcMultiCriteriaEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MULTICRITERIA";
    @DBColumn(title="\u8f6c\u6362\u524d\u9879\u76ee\u4ee3\u7801/\u516c\u5f0f", dbType=DBColumn.DBType.Text, isRequired=true)
    private String beforeZbCodes;
    @DBColumn(title="\u8f6c\u6362\u524d\u9879\u76ee\u6307\u6807\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String beforeZbJson;
    @DBColumn(title="\u8f6c\u6362\u524d\u9879\u76ee\u540d\u79f0", dbType=DBColumn.DBType.Text, isRequired=true)
    private String beforeZbTitles;
    @DBColumn(title="\u8f6c\u6362\u540e\u9879\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.Text, isRequired=true)
    private String afterZbCodes;
    @DBColumn(title="\u8f6c\u6362\u540e\u9879\u76ee\u6307\u6807\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String afterZbJson;
    @DBColumn(title="\u8f6c\u6362\u540e\u9879\u76ee\u540d\u79f0", dbType=DBColumn.DBType.Text, isRequired=true)
    private String afterZbTitles;
    @DBColumn(title="\u8f6c\u6362\u524d\u662f\u5426\u516c\u5f0f", dbType=DBColumn.DBType.Numeric, precision=1, scale=0, isRequired=true)
    private Integer hasFormula;
    @DBColumn(title="\u8f6c\u6362\u7c7b\u578b(manual\u3001auto)", dbType=DBColumn.DBType.Varchar, length=10, isRequired=true)
    private String kind;
    @DBColumn(title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date modifyTime;

    public String getBeforeZbCodes() {
        return this.beforeZbCodes;
    }

    public void setBeforeZbCodes(String beforeZbCodes) {
        this.beforeZbCodes = beforeZbCodes;
    }

    public String getBeforeZbTitles() {
        return this.beforeZbTitles;
    }

    public void setBeforeZbTitles(String beforeZbTitles) {
        this.beforeZbTitles = beforeZbTitles;
    }

    public String getAfterZbCodes() {
        return this.afterZbCodes;
    }

    public void setAfterZbCodes(String afterZbCodes) {
        this.afterZbCodes = afterZbCodes;
    }

    public String getAfterZbTitles() {
        return this.afterZbTitles;
    }

    public void setAfterZbTitles(String afterZbTitles) {
        this.afterZbTitles = afterZbTitles;
    }

    public Integer getHasFormula() {
        return this.hasFormula;
    }

    public void setHasFormula(Integer hasFormula) {
        this.hasFormula = hasFormula;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBeforeZbJson() {
        return this.beforeZbJson;
    }

    public void setBeforeZbJson(String beforeZbJson) {
        this.beforeZbJson = beforeZbJson;
    }

    public String getAfterZbJson() {
        return this.afterZbJson;
    }

    public void setAfterZbJson(String afterZbJson) {
        this.afterZbJson = afterZbJson;
    }
}

