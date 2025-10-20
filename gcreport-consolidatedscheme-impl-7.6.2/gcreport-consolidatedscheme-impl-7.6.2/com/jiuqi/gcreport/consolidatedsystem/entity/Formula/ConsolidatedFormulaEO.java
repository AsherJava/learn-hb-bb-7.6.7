/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.Formula;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CONSFORMULA", title="\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u516c\u5f0f\u8868")
public class ConsolidatedFormulaEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONSFORMULA";
    @DBColumn(nameInDB="CODE", title="\u516c\u5f0f\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=36)
    private String code;
    @DBColumn(nameInDB="SYSTEMID", title="\u4f53\u7cfbid", dbType=DBColumn.DBType.Varchar, length=36)
    private String systemId;
    @DBColumn(nameInDB="FORMULA", title="\u516c\u5f0f", dbType=DBColumn.DBType.Varchar, length=500)
    private String formula;
    @DBColumn(nameInDB="INPUTFLAG", title="\u8f93\u5165\u8c03\u6574\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer inputFlag;
    @DBColumn(nameInDB="ANTOFLAG", title="\u81ea\u52a8\u62b5\u9500\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer antoFlag;
    @DBColumn(nameInDB="MANUALFLAG", title="\u624b\u52a8\u62b5\u9500\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer manualFlag;
    @DBColumn(nameInDB="RULEIDS", dbType=DBColumn.DBType.Text)
    private String ruleIds;
    @DBColumn(nameInDB="SORTORDER", dbType=DBColumn.DBType.NVarchar)
    private String sortOrder;
    @DBColumn(nameInDB="CARRYOVER", title="\u5e74\u7ed3\u6807\u8bc6", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer carryOver;

    public Integer getCarryOver() {
        return this.carryOver;
    }

    public void setCarryOver(Integer carryOver) {
        this.carryOver = carryOver;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getInputFlag() {
        return this.inputFlag;
    }

    public void setInputFlag(Integer inputFlag) {
        this.inputFlag = inputFlag;
    }

    public Integer getAntoFlag() {
        return this.antoFlag;
    }

    public void setAntoFlag(Integer antoFlag) {
        this.antoFlag = antoFlag;
    }

    public Integer getManualFlag() {
        return this.manualFlag;
    }

    public void setManualFlag(Integer manualFlag) {
        this.manualFlag = manualFlag;
    }

    public String getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(String ruleIds) {
        this.ruleIds = ruleIds;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }
}

