/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.checkconfig.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_FCCONFIG", title="\u5bf9\u8d26\u914d\u7f6e\u8868")
public class FinancialCheckConfigEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FCCONFIG";
    @DBColumn(nameInDB="CHECKWAY", title="\u5bf9\u8d26\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar, length=36)
    private String checkWay;
    @DBColumn(nameInDB="DATASOURCE", title="\u6570\u636e\u6765\u6e90", dbType=DBColumn.DBType.Varchar, length=36)
    private String dataSource;
    @DBColumn(nameInDB="ORGTYPE", title="\u5173\u8054\u673a\u6784\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgType;
    @DBColumn(nameInDB="ORGRANGE", title="\u5355\u4f4d\u8303\u56f4", dbType=DBColumn.DBType.Text)
    private String orgRange;
    @DBColumn(nameInDB="SUBJECTRANGE", title="\u79d1\u76ee\u8303\u56f4", dbType=DBColumn.DBType.Text)
    private String subjectRange;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;
    @DBColumn(nameInDB="options", title="\u9009\u9879", dbType=DBColumn.DBType.Text)
    private String options;

    public String getCheckWay() {
        return this.checkWay;
    }

    public void setCheckWay(String checkWay) {
        this.checkWay = checkWay;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgRange() {
        return this.orgRange;
    }

    public void setOrgRange(String orgRange) {
        this.orgRange = orgRange;
    }

    public String getSubjectRange() {
        return this.subjectRange;
    }

    public void setSubjectRange(String subjectRange) {
        this.subjectRange = subjectRange;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}

