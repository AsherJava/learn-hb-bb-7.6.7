/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.reportdatasync.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_PARAMSYNC_XF_LOG_ITEM", title="\u56fd\u8d44\u59d4\u53c2\u6570\u540c\u6b65\u65e5\u5fd7\u660e\u7ec6\u8868")
public class ReportDataSyncIssuedLogItemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PARAMSYNC_XF_LOG_ITEM";
    @DBColumn(title="\u540c\u6b65\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String xfTaskId;
    @DBColumn(title="\u540c\u6b65\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgTitle;
    @DBColumn(title="\u540c\u6b65\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgCode;
    @DBColumn(title="\u540c\u6b65\u7ed3\u679c\u72b6\u6001", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer status;
    @DBColumn(title="\u662f\u5426\u4e3a\u672a\u540c\u6b65\u72b6\u6001", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer notSynchronized;
    @DBColumn(title="\u540c\u6b65\u8be6\u60c5\u4fe1\u606f", dbType=DBColumn.DBType.Varchar, length=2000)
    private String msg;
    @DBColumn(title="\u540c\u6b65\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date syncTime;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7528\u6237ID", dbType=DBColumn.DBType.Varchar)
    private String syncUserId;
    @DBColumn(title="\u540c\u6b65\u6267\u884c\u7528\u6237\u540d", dbType=DBColumn.DBType.Varchar, length=100)
    private String syncUserName;

    public String getXfTaskId() {
        return this.xfTaskId;
    }

    public void setXfTaskId(String xfTaskId) {
        this.xfTaskId = xfTaskId;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        if (msg != null && msg.length() > 2000) {
            msg = msg.substring(0, 2000);
        }
        this.msg = msg;
    }

    public Date getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getSyncUserName() {
        return this.syncUserName;
    }

    public void setSyncUserName(String syncUserName) {
        this.syncUserName = syncUserName;
    }

    public String getSyncUserId() {
        return this.syncUserId;
    }

    public void setSyncUserId(String syncUserId) {
        this.syncUserId = syncUserId;
    }

    public Integer getNotSynchronized() {
        return this.notSynchronized;
    }

    public void setNotSynchronized(Integer notSynchronized) {
        this.notSynchronized = notSynchronized;
    }
}

