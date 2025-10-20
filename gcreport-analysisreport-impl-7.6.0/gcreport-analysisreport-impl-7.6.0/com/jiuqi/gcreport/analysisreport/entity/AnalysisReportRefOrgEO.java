/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.analysisreport.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ANALYSISREPORT_REFORG", title="\u5206\u6790\u62a5\u544a\u5173\u8054\u5355\u4f4d\u8bbe\u7f6e", inStorage=true)
public class AnalysisReportRefOrgEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ANALYSISREPORT_REFORG";
    @DBColumn(nameInDB="MRECID", title="\u591a\u7ae0\u8282\u6a21\u677fID", dbType=DBColumn.DBType.Varchar, length=36)
    private String mrecid;
    @DBColumn(nameInDB="TEMPLATEID", title="\u6a21\u677fID", dbType=DBColumn.DBType.NVarchar, length=36)
    private String templateId;
    @DBColumn(nameInDB="TEMPLATETITLE", title="\u6a21\u677f\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=200)
    private String templateTitle;
    @DBColumn(nameInDB="ORGID", title="\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgId;
    @DBColumn(nameInDB="ORGTITLE", title="\u5355\u4f4d\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=100)
    private String orgTitle;
    @DBColumn(nameInDB="SORTORDER", title="\u6392\u5e8f", dbType=DBColumn.DBType.Long)
    private Long sortOrder;

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateTitle() {
        return this.templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}

