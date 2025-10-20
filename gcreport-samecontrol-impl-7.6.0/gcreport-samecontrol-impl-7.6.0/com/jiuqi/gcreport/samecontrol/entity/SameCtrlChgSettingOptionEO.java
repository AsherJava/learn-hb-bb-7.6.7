/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_SAMECTRLSETING_OPTION", title="\u540c\u63a7\u53d8\u52a8\u8bbe\u7f6e\u9009\u9879", indexs={@DBIndex(name="IDX_SAMECTRLCHGORG_OPTION_INDEX1", columnsFields={"taskid", "schemeId"})})
public class SameCtrlChgSettingOptionEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRLSETING_OPTION";
    @DBColumn(title="\u4efb\u52a1", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u51c0\u5229\u6da6\u79d1\u76ee", dbType=DBColumn.DBType.Varchar)
    private String netProfitSubjectCode;
    @DBColumn(title="\u672a\u5206\u914d\u5229\u6da6\u79d1\u76ee", dbType=DBColumn.DBType.Varchar)
    private String undividendProfitSubjectCode;
    @DBColumn(title="\u5904\u7f6e\u573a\u666f\u8bbe\u7f6e", dbType=DBColumn.DBType.Varchar, length=100)
    private String disposalSceneCodes;
    @DBColumn(title="\u542f\u7528\u540c\u63a7", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer enableSameCtr;
    @DBColumn(title="\u6295\u8d44\u53f0\u8d26\u5904\u7f6e\u540e\u590d\u5236\u4e0a\u671f\u5206\u5f55\u5230\u5f53\u671f", dbType=DBColumn.DBType.Numeric, length=1)
    private Integer enableInvestDisposeCopy;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getNetProfitSubjectCode() {
        return this.netProfitSubjectCode;
    }

    public void setNetProfitSubjectCode(String netProfitSubjectCode) {
        this.netProfitSubjectCode = netProfitSubjectCode;
    }

    public String getUndividendProfitSubjectCode() {
        return this.undividendProfitSubjectCode;
    }

    public void setUndividendProfitSubjectCode(String undividendProfitSubjectCode) {
        this.undividendProfitSubjectCode = undividendProfitSubjectCode;
    }

    public String getDisposalSceneCodes() {
        return this.disposalSceneCodes;
    }

    public void setDisposalSceneCodes(String disposalSceneCodes) {
        this.disposalSceneCodes = disposalSceneCodes;
    }

    public Integer getEnableSameCtr() {
        return this.enableSameCtr;
    }

    public void setEnableSameCtr(Integer enableSameCtr) {
        this.enableSameCtr = enableSameCtr;
    }

    public Integer getEnableInvestDisposeCopy() {
        return this.enableInvestDisposeCopy;
    }

    public void setEnableInvestDisposeCopy(Integer enableInvestDisposeCopy) {
        this.enableInvestDisposeCopy = enableInvestDisposeCopy;
    }
}

