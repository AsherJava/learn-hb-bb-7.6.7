/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.task;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CONSTASK", title="\u5408\u5e76\u4f53\u7cfb\u5173\u8054\u4efb\u52a1\u8868", inStorage=true)
public class ConsolidatedTaskEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONSTASK";
    @DBColumn(nameInDB="TASKKEY", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskKey;
    @DBColumn(nameInDB="MANAGETASKKEYS", dbType=DBColumn.DBType.Varchar, length=500)
    private String manageTaskKeys;
    @DBColumn(nameInDB="FROMPERIOD", title="\u5f00\u59cb\u65f6\u671f", dbType=DBColumn.DBType.Varchar, length=36)
    private String fromPeriod;
    @DBColumn(nameInDB="TOPERIOD", title="\u7ed3\u675f\u65f6\u671f", dbType=DBColumn.DBType.Varchar, length=36)
    private String toPeriod;
    @DBColumn(nameInDB="MOREINFO", title="\u66f4\u591a\u8bbe\u7f6e \u5141\u8bb8\u5b8c\u6210\u5408\u5e76\u3001\u7ed3\u8f6c\u635f\u76ca\u3001\u624b\u5de5\u62b5\u9500\u3001\u8f93\u5165\u8c03\u6574\u3001\u5408\u5e76\u8ba1\u7b97", dbType=DBColumn.DBType.Varchar, length=36)
    private String moreInfo;
    @DBColumn(dbType=DBColumn.DBType.Varchar, title="\u5dee\u989d\u56de\u5199\u65b9\u5f0f", length=20)
    private String diffRewriteWay;
    @DBColumn(nameInDB="SYSTEMID", dbType=DBColumn.DBType.Varchar, length=36)
    private String systemId;
    @DBColumn(nameInDB="MODIFIEDTIME", dbType=DBColumn.DBType.Date)
    private Date modifiedTime;
    @DBColumn(nameInDB="MODIFIEDUSER", dbType=DBColumn.DBType.Varchar, length=36)
    private String modifiedUser;
    @DBColumn(nameInDB="SORTORDER", dbType=DBColumn.DBType.NVarchar)
    private String sortOrder;
    @DBColumn(nameInDB="CORPORATEENTITY", title="\u6cd5\u4eba\u67b6\u6784\u53e3\u5f84\u7f16\u7801", dbType=DBColumn.DBType.Varchar)
    private String corporateEntity;
    @DBColumn(nameInDB="MANAGEENTITY", title="\u7ba1\u7406\u67b6\u6784\u53e3\u5f84\u7f16\u7801", dbType=DBColumn.DBType.Varchar, length=500)
    private String manageEntity;

    public String getCorporateEntity() {
        return this.corporateEntity;
    }

    public void setCorporateEntity(String corporateEntity) {
        this.corporateEntity = corporateEntity;
    }

    public String getManageEntity() {
        return this.manageEntity;
    }

    public void setManageEntity(String manageEntity) {
        this.manageEntity = manageEntity;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Date getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedUser() {
        return this.modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getMoreInfo() {
        return this.moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getDiffRewriteWay() {
        return this.diffRewriteWay;
    }

    public void setDiffRewriteWay(String diffRewriteWay) {
        this.diffRewriteWay = diffRewriteWay;
    }

    public String getManageTaskKeys() {
        return this.manageTaskKeys;
    }

    public void setManageTaskKeys(String manageTaskKeys) {
        this.manageTaskKeys = manageTaskKeys;
    }
}

