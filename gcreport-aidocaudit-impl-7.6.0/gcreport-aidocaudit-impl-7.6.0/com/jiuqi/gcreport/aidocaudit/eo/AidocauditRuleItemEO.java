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

@DBTable(name="GC_AIDOCAUDIT_RULEITEM", title="\u5ba1\u6838\u7b56\u7565\u8be6\u60c5\u8868")
public class AidocauditRuleItemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_AIDOCAUDIT_RULEITEM";
    @DBColumn(title="\u4e3b\u8868ID", nameInDB="RULEID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String ruleId;
    @DBColumn(title="\u89c4\u5219ID", nameInDB="SCOREITEMID", dbType=DBColumn.DBType.Varchar, length=36)
    private String scoreItemId;
    @DBColumn(title="\u89c4\u5219\u540d\u79f0", nameInDB="SCOREITEMNAME", dbType=DBColumn.DBType.NVarchar, length=50)
    private String scoreItemName;
    @DBColumn(title="\u5206\u503c", nameInDB="FULLSCORE", dbType=DBColumn.DBType.Numeric, precision=10, scale=1)
    private BigDecimal fullScore;
    @DBColumn(title="\u7236\u8282\u70b9", nameInDB="PARENTSCOREITEMID", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentScoreItemId;
    @DBColumn(title="\u6bb5\u843d\u6807\u9898", nameInDB="PARAGRAPHTITLE", dbType=DBColumn.DBType.NVarchar, length=100)
    private String paragraphTitle;
    @DBColumn(title="\u63d0\u793a\u8bcd", nameInDB="PROMPT", dbType=DBColumn.DBType.NVarchar, length=500)
    private String prompt;
    @DBColumn(title="\u6392\u5e8f", nameInDB="ORDINAL", dbType=DBColumn.DBType.Varchar, length=6)
    private String ordinal;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", nameInDB="CREATETIME", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(title="\u521b\u5efa\u4eba", nameInDB="CREATEUSER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", nameInDB="UPDATETIME", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;
    @DBColumn(title="\u4fee\u6539\u4eba", nameInDB="UPDATEUSER", dbType=DBColumn.DBType.NVarchar, length=100)
    private String updateUser;

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getScoreItemId() {
        return this.scoreItemId;
    }

    public void setScoreItemId(String scoreItemId) {
        this.scoreItemId = scoreItemId;
    }

    public String getScoreItemName() {
        return this.scoreItemName;
    }

    public void setScoreItemName(String scoreItemName) {
        this.scoreItemName = scoreItemName;
    }

    public BigDecimal getFullScore() {
        return this.fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public String getParentScoreItemId() {
        return this.parentScoreItemId;
    }

    public void setParentScoreItemId(String parentScoreItemId) {
        this.parentScoreItemId = parentScoreItemId;
    }

    public String getParagraphTitle() {
        return this.paragraphTitle;
    }

    public void setParagraphTitle(String paragraphTitle) {
        this.paragraphTitle = paragraphTitle;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
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

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }
}

