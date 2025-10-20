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

@DBTable(name="GC_AIDOCAUDIT_RESULT", title="\u5ba1\u6838\u7b56\u7565\u4e3b\u8868")
public class AidocauditResultEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_AIDOCAUDIT_RESULT";
    @DBColumn(title="\u5355\u4f4d", nameInDB="MDCODE", dbType=DBColumn.DBType.NVarchar, length=36)
    private String mdCode;
    @DBColumn(title="\u62a5\u8868\u65f6\u671f", nameInDB="DATATIME", dbType=DBColumn.DBType.NVarchar, length=9)
    private String dataTime;
    @DBColumn(title="\u62a5\u8868\u4efb\u52a1ID", nameInDB="TASKID", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(title="\u9644\u4ef6\u6307\u6807\u4ee3\u7801", nameInDB="ZBCODE", dbType=DBColumn.DBType.NVarchar, length=100)
    private String zbCode;
    @DBColumn(title="\u9644\u4ef6ID", nameInDB="ATTACHMENTID", dbType=DBColumn.DBType.Varchar, length=36)
    private String attachmentId;
    @DBColumn(title="\u8bc4\u5206\u6a21\u677fID", nameInDB="RULEID", dbType=DBColumn.DBType.Varchar, length=36)
    private String ruleId;
    @DBColumn(title="\u5f97\u5206", nameInDB="SCORE", dbType=DBColumn.DBType.Numeric, precision=10, scale=1)
    private BigDecimal score;
    @DBColumn(title="\u89c4\u5219\u4e2a\u6570", nameInDB="RULENUM", dbType=DBColumn.DBType.Int)
    private Integer ruleNum;
    @DBColumn(title="\u901a\u8fc7\u89c4\u5219\u4e2a\u6570", nameInDB="RULEMATCHNUM", dbType=DBColumn.DBType.Int)
    private Integer ruleMatchNum;
    @DBColumn(title="\u4e0d\u901a\u8fc7\u89c4\u5219\u4e2a\u6570", nameInDB="RULEUNMATCHNUM", dbType=DBColumn.DBType.Int)
    private Integer ruleUnmatchNum;
    @DBColumn(title="\u7591\u4f3c\u901a\u8fc7\u89c4\u5219\u4e2a\u6570", nameInDB="RULESUSPECTMATCHNUM", dbType=DBColumn.DBType.Int)
    private Integer ruleSuspectMatchNum;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", nameInDB="CREATETIME", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(title="\u521b\u5efa\u4eba", nameInDB="CREATEUSER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;

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

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getRuleNum() {
        return this.ruleNum;
    }

    public void setRuleNum(Integer ruleNum) {
        this.ruleNum = ruleNum;
    }

    public Integer getRuleMatchNum() {
        return this.ruleMatchNum;
    }

    public void setRuleMatchNum(Integer ruleMatchNum) {
        this.ruleMatchNum = ruleMatchNum;
    }

    public Integer getRuleUnmatchNum() {
        return this.ruleUnmatchNum;
    }

    public void setRuleUnmatchNum(Integer ruleUnmatchNum) {
        this.ruleUnmatchNum = ruleUnmatchNum;
    }

    public Integer getRuleSuspectMatchNum() {
        return this.ruleSuspectMatchNum;
    }

    public void setRuleSuspectMatchNum(Integer ruleSuspectMatchNum) {
        this.ruleSuspectMatchNum = ruleSuspectMatchNum;
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
}

