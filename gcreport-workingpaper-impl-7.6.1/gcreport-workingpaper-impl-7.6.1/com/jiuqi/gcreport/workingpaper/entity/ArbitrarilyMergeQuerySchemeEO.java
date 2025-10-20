/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  javax.persistence.Transient
 */
package com.jiuqi.gcreport.workingpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgFilterSettingEO;
import java.util.List;
import javax.persistence.Transient;

@DBTable(name="GC_ORGFILTER_SCHEME", title="\u4efb\u610f\u5408\u5e76\u5355\u4f4d\u7b5b\u9009\u5e38\u7528\u67e5\u8be2\u5b58\u50a8\u8868", inStorage=true)
public class ArbitrarilyMergeQuerySchemeEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ORGFILTER_SCHEME";
    @DBColumn(nameInDB="RESOURCEID", title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, length=60)
    private String resourceId;
    @DBColumn(nameInDB="SCHEMENAME", title="\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.Varchar)
    private String schemeName;
    @DBColumn(nameInDB="USERID", title="\u7528\u6237\u4e3b\u952e", dbType=DBColumn.DBType.Varchar)
    private String userId;
    @DBColumn(nameInDB="SETTINGJSON", title="json\u6587\u672c", dbType=DBColumn.DBType.Text)
    private String settingJson;
    @DBColumn(nameInDB="EXTERNALFIELD", title="\u62d3\u5c55\u6587\u672c\u5b57\u6bb5", dbType=DBColumn.DBType.Text)
    private String externalField;
    @DBColumn(nameInDB="FINALFORMULA", title="\u53ef\u6267\u884c\u516c\u5f0f", dbType=DBColumn.DBType.Text)
    private String finalFormula;
    @DBColumn(nameInDB="ORDERNUM", title="\u6392\u5e8f\u53f7", dbType=DBColumn.DBType.Numeric)
    private Double orderNum;
    @Transient
    private List<ArbitrarilyMergeOrgFilterSettingEO> settingList;

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSettingJson() {
        return this.settingJson;
    }

    public void setSettingJson(String settingJson) {
        this.settingJson = settingJson;
    }

    public String getExternalField() {
        return this.externalField;
    }

    public void setExternalField(String externalField) {
        this.externalField = externalField;
    }

    public String getFinalFormula() {
        return this.finalFormula;
    }

    public void setFinalFormula(String finalFormula) {
        this.finalFormula = finalFormula;
    }

    public Double getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Double orderNum) {
        this.orderNum = orderNum;
    }

    public List<ArbitrarilyMergeOrgFilterSettingEO> getSettingList() {
        return this.settingList;
    }

    public void setSettingList(List<ArbitrarilyMergeOrgFilterSettingEO> settingList) {
        this.settingList = settingList;
    }
}

