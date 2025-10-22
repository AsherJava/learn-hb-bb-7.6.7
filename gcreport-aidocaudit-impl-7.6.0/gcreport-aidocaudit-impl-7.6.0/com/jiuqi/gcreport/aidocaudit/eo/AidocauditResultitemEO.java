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

@DBTable(name="GC_AIDOCAUDIT_RESULTITEM", title="\u5ba1\u6838\u7b56\u7565\u4e3b\u8868")
public class AidocauditResultitemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_AIDOCAUDIT_RESULTITEM";
    @DBColumn(title="\u4e3b\u8868ID", nameInDB="RESULTID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String resultId;
    @DBColumn(title="\u5f97\u5206", nameInDB="SCORE", dbType=DBColumn.DBType.Numeric, precision=10, scale=1)
    private BigDecimal score;
    @DBColumn(title="\u5206\u6570\u8bf4\u660e", nameInDB="SCOREBASIS", dbType=DBColumn.DBType.NVarchar, length=500)
    private String scoreBasis;
    @DBColumn(title="\u5206\u503c", nameInDB="FULLSCORE", dbType=DBColumn.DBType.Numeric, precision=10, scale=1)
    private BigDecimal fullScore;
    @DBColumn(title="\u7ec6\u5219id", nameInDB="RULEITEMID", dbType=DBColumn.DBType.Varchar, length=36)
    private String ruleItemId;

    public String getResultId() {
        return this.resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getScoreBasis() {
        return this.scoreBasis;
    }

    public void setScoreBasis(String scoreBasis) {
        this.scoreBasis = scoreBasis;
    }

    public BigDecimal getFullScore() {
        return this.fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public String getRuleItemId() {
        return this.ruleItemId;
    }

    public void setRuleItemId(String ruleItemId) {
        this.ruleItemId = ruleItemId;
    }
}

