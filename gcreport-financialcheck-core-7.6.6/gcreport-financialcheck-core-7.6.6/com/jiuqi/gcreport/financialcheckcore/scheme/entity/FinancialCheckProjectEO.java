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

@DBTable(name="GC_FCPROJECT", title="\u5bf9\u8d26\u9879\u76ee\u8868")
public class FinancialCheckProjectEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FCPROJECT";
    @DBColumn(nameInDB="CHECKPROJECT", title="\u5bf9\u8d26\u9879\u76ee", dbType=DBColumn.DBType.Varchar, length=36)
    private String checkProject;
    @DBColumn(nameInDB="SCHEMEID", title="\u65b9\u6848Id", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="CHECkPROJECTDIRECTION", title="\u5bf9\u8d26\u9879\u76ee\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer checkProjectDirection;
    @DBColumn(nameInDB="BUSINESSROLE", title="\u4e1a\u52a1\u89d2\u8272", dbType=DBColumn.DBType.Int)
    private Integer businessRole;
    @DBColumn(nameInDB="SORTORDER", title="\u5e8f\u53f7", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double sortOrder;
    @DBColumn(nameInDB="OPPSUBJECT", title="\u5bf9\u65b9\u79d1\u76ee", dbType=DBColumn.DBType.Varchar, length=36)
    private String oppSubject;

    public String getCheckProject() {
        return this.checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public Integer getCheckProjectDirection() {
        return this.checkProjectDirection;
    }

    public void setCheckProjectDirection(Integer checkProjectDirection) {
        this.checkProjectDirection = checkProjectDirection;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.businessRole = businessRole;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOppSubject() {
        return this.oppSubject;
    }

    public void setOppSubject(String oppSubject) {
        this.oppSubject = oppSubject;
    }
}

