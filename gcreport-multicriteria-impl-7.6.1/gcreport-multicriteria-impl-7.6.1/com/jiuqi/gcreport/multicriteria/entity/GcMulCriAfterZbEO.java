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

@DBTable(name="GC_MULCRIAFTERZB", title="\u591a\u51c6\u5219\u8f6c\u6362\u540e\u9879\u76ee\u8868")
public class GcMulCriAfterZbEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MULCRIAFTERZB";
    @DBColumn(title="\u591a\u51c6\u5219\u8868ID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String mcid;
    @DBColumn(title="\u8f6c\u6362\u540e\u6307\u6807key", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String afterZbKey;
    @DBColumn(title="\u8f6c\u6362\u524d\u6307\u6807\u540d\u79f0", dbType=DBColumn.DBType.Text, isRequired=true)
    private String beforeZbTitles;
    @DBColumn(title="\u8f6c\u6362\u540e\u62a5\u8868key", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String afterFormKey;
    @DBColumn(title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u6392\u5e8f", dbType=DBColumn.DBType.Numeric, isRequired=true)
    private Double ordinal;

    public String getMcid() {
        return this.mcid;
    }

    public void setMcid(String mcid) {
        this.mcid = mcid;
    }

    public String getAfterZbKey() {
        return this.afterZbKey;
    }

    public void setAfterZbKey(String afterZbKey) {
        this.afterZbKey = afterZbKey;
    }

    public String getBeforeZbTitles() {
        return this.beforeZbTitles;
    }

    public void setBeforeZbTitles(String beforeZbTitles) {
        this.beforeZbTitles = beforeZbTitles;
    }

    public String getAfterFormKey() {
        return this.afterFormKey;
    }

    public void setAfterFormKey(String afterFormKey) {
        this.afterFormKey = afterFormKey;
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

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }
}

