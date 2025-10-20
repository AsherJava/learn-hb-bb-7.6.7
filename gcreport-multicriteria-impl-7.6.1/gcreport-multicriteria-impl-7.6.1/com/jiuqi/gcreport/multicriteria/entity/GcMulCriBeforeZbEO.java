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

@DBTable(name="GC_MULCRIBEFOREZB", title="\u591a\u51c6\u5219\u8f6c\u6362\u524d\u9879\u76ee\u8868")
public class GcMulCriBeforeZbEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MULCRIBEFOREZB";
    @DBColumn(title="\u591a\u51c6\u5219\u8868ID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String mcid;
    @DBColumn(title="\u8f6c\u6362\u524d\u6307\u6807key", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String beforeZbKey;
    @DBColumn(title="\u8f6c\u6362\u540e\u6307\u6807\u540d\u79f0", dbType=DBColumn.DBType.Text, isRequired=true)
    private String afterZbTitles;
    @DBColumn(title="\u8f6c\u6362\u524d\u62a5\u8868key", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String beforeFormKey;
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

    public String getBeforeZbKey() {
        return this.beforeZbKey;
    }

    public void setBeforeZbKey(String beforeZbKey) {
        this.beforeZbKey = beforeZbKey;
    }

    public String getAfterZbTitles() {
        return this.afterZbTitles;
    }

    public void setAfterZbTitles(String afterZbTitles) {
        this.afterZbTitles = afterZbTitles;
    }

    public String getBeforeFormKey() {
        return this.beforeFormKey;
    }

    public void setBeforeFormKey(String beforeFormKey) {
        this.beforeFormKey = beforeFormKey;
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

