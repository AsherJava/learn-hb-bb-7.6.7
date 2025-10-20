/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_FCPROJECTMAPPING", title="\u5bf9\u8d26\u9879\u76ee\u6620\u5c04\u8868")
public class FinancialCheckMappingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FCPROJECTMAPPING";
    @DBColumn(nameInDB="SUBJECT", title="\u79d1\u76ee", dbType=DBColumn.DBType.Varchar, length=36)
    private String subject;
    @DBColumn(nameInDB="DIMENSIONS", title="\u7ef4\u5ea6", dbType=DBColumn.DBType.NVarchar, length=500)
    private String dimensions;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848Id", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="CHECKPROJECT", title="\u5bf9\u8d26\u9879\u76ee", dbType=DBColumn.DBType.Varchar, length=36)
    private String checkProject;
    @DBColumn(nameInDB="SORTORDER", title="\u5e8f\u53f7", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double sortOrder;

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getCheckProject() {
        return this.checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }
}

