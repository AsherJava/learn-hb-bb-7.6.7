/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.aidocaudit.eo;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_AIDOCAUDIT_LOG", title="\u8bc4\u5206\u4efb\u52a1\u65e5\u5fd7\u8868")
public class AidocAuditLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_AIDOCAUDIT_LOG";
    @DBColumn(nameInDB="SCORETASK", title="\u6279\u6b21ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String scoreTask;
    @DBColumn(nameInDB="MDCODE", title="\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar, length=36)
    private String mdCode;
    @DBColumn(nameInDB="DATATIME", title="\u62a5\u8868\u65f6\u671f", dbType=DBColumn.DBType.NVarchar, length=9)
    private String dataTime;
    @DBColumn(nameInDB="RULEID", title="\u8bc4\u5206\u6a21\u677fID", dbType=DBColumn.DBType.Varchar, length=36)
    private String ruleId;
    @DBColumn(nameInDB="STATUS", title="\u72b6\u6001", dbType=DBColumn.DBType.Int)
    private Integer status;
    @DBColumn(nameInDB="STARTTIME", title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date startTime;
    @DBColumn(nameInDB="ENDTIME", title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date endTime;
    @DBColumn(nameInDB="ISFEEDBACK", title="\u662f\u5426\u53cd\u9988\u7528\u6237", dbType=DBColumn.DBType.Int)
    private Integer isFeedback;
    @DBColumn(nameInDB="AUDITUSER", title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.Varchar, length=100)
    private String auditUser;

    public String getScoreTask() {
        return this.scoreTask;
    }

    public void setScoreTask(String scoreTask) {
        this.scoreTask = scoreTask;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsFeedback() {
        return this.isFeedback;
    }

    public void setIsFeedback(Integer isFeedback) {
        this.isFeedback = isFeedback;
    }

    public String getAuditUser() {
        return this.auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }
}

