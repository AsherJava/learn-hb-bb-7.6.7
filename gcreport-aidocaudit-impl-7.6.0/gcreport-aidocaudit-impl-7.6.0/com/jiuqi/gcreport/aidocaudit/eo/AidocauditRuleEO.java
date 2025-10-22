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
import java.math.BigDecimal;
import java.util.Date;

@DBTable(name="GC_AIDOCAUDIT_RULE", title="\u5ba1\u6838\u7b56\u7565\u4e3b\u8868")
public class AidocauditRuleEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_AIDOCAUDIT_RULE";
    @DBColumn(title="\u5ba1\u6838\u7b56\u7565\u540d\u79f0", nameInDB="RULENAME", dbType=DBColumn.DBType.NVarchar, length=100)
    private String ruleName;
    @DBColumn(title="\u89c4\u5219\u7c7b\u578b", nameInDB="RULETYPE", dbType=DBColumn.DBType.Int)
    private Integer ruleType;
    @DBColumn(title="\u89c4\u5219\u72b6\u6001", nameInDB="RULESTATUS", dbType=DBColumn.DBType.Int)
    private Integer ruleStatus;
    @DBColumn(title="\u62a5\u544a\u6a21\u677f-\u6587\u6863\u8d44\u6e90ID", nameInDB="REPORTTEMPLATE", dbType=DBColumn.DBType.Varchar, length=36)
    private String reportTemplate;
    @DBColumn(title="\u62a5\u544a\u6a21\u677f-\u6587\u6863\u8d44\u6e90ID", nameInDB="RULEATTACHMENTID", dbType=DBColumn.DBType.Varchar, length=36)
    private String ruleAttachmentId;
    @DBColumn(title="\u62a5\u544a\u6a21\u677f-\u6587\u6863\u8d44\u6e90\u540d\u79f0", nameInDB="RULEATTACHMENTNAME", dbType=DBColumn.DBType.NVarchar, length=100)
    private String ruleAttachmentName;
    @DBColumn(title="\u9644\u4ef6\u6307\u6807", nameInDB="ACHMENTZBCODE", dbType=DBColumn.DBType.NVarchar, length=36)
    private String achmentZbCode;
    @DBColumn(title="\u8bc4\u5206\u6a21\u677fID", nameInDB="SCORETMPLID", dbType=DBColumn.DBType.Varchar, length=36)
    private String scoreTmplId;
    @DBColumn(title="\u89c4\u5219\u603b\u6570", nameInDB="RULECOUNT", dbType=DBColumn.DBType.Numeric, precision=10, scale=1)
    private Integer ruleCount;
    @DBColumn(title="\u603b\u5206\u503c", nameInDB="TOTALSCORE", dbType=DBColumn.DBType.Numeric, precision=10, scale=1)
    private BigDecimal totalScore;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", nameInDB="CREATETIME", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(title="\u521b\u5efa\u4eba", nameInDB="CREATEUSER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", nameInDB="UPDATETIME", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;
    @DBColumn(title="\u4fee\u6539\u4eba", nameInDB="UPDATEUSER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updateUser;

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleAttachmentId() {
        return this.ruleAttachmentId;
    }

    public void setRuleAttachmentId(String ruleAttachmentId) {
        this.ruleAttachmentId = ruleAttachmentId;
    }

    public String getRuleAttachmentName() {
        return this.ruleAttachmentName;
    }

    public void setRuleAttachmentName(String ruleAttachmentName) {
        this.ruleAttachmentName = ruleAttachmentName;
    }

    public String getAchmentZbCode() {
        return this.achmentZbCode;
    }

    public void setAchmentZbCode(String achmentZbCode) {
        this.achmentZbCode = achmentZbCode;
    }

    public String getScoreTmplId() {
        return this.scoreTmplId;
    }

    public void setScoreTmplId(String scoreTmplId) {
        this.scoreTmplId = scoreTmplId;
    }

    public Integer getRuleCount() {
        return this.ruleCount;
    }

    public void setRuleCount(Integer ruleCount) {
        this.ruleCount = ruleCount;
    }

    public BigDecimal getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getRuleStatus() {
        return this.ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public String getReportTemplate() {
        return this.reportTemplate;
    }

    public void setReportTemplate(String reportTemplate) {
        this.reportTemplate = reportTemplate;
    }
}

