/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.onekeymerge.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ONEKEYMERGECONFIG", title="\u4e00\u952e\u5408\u5e76\u4efb\u52a1\u914d\u7f6e", inStorage=true)
public class GcOnekeyMergeConfigEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ONEKEYMERGECONFIG";
    @DBColumn(nameInDB="configName", title="\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.Varchar)
    private String configName;
    @DBColumn(nameInDB="mergeType", title="\u5408\u5e76\u65b9\u5f0f", dbType=DBColumn.DBType.Varchar)
    private String mergeType;
    @DBColumn(nameInDB="ruleIds", title="\u5408\u5e76\u89c4\u5219", dbType=DBColumn.DBType.Text)
    private String ruleIds;
    @DBColumn(nameInDB="orgIds", title="\u5355\u4f4d", dbType=DBColumn.DBType.Text)
    private String orgIds;
    @DBColumn(nameInDB="userId", title="\u7528\u6237ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String userId;
    @DBColumn(nameInDB="configNodes", title="\u65b9\u6848(;\u5206\u9694)", dbType=DBColumn.DBType.Varchar, length=200)
    private String configNodes;
    @DBColumn(nameInDB="ordinal", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric, precision=3, scale=3)
    private Double ordinal;
    @DBColumn(nameInDB="finishCalConfig", title="\u5b8c\u6210\u5408\u5e76\u652f\u6301\u9009\u9879", dbType=DBColumn.DBType.Varchar, length=200)
    private String finishCalConfig;

    public String getConfigName() {
        return this.configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getFinishCalConfig() {
        return this.finishCalConfig;
    }

    public void setFinishCalConfig(String finishCalConfig) {
        this.finishCalConfig = finishCalConfig;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConfigNodes() {
        return this.configNodes;
    }

    public void setConfigNodes(String configNodes) {
        this.configNodes = configNodes;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }

    public String getMergeType() {
        return this.mergeType;
    }

    public void setMergeType(String mergeType) {
        this.mergeType = mergeType;
    }

    public String getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(String ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }
}

