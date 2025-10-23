/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.subdatabase.facade.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_SUBDATABASE_SERVICE")
public class SubDataBaseImpl
implements SubDataBase {
    @DBAnno.DBField(dbField="SD_TITLE")
    private String title;
    @DBAnno.DBField(dbField="SD_CODE", isPk=true, isOrder=true)
    private String code;
    @DBAnno.DBField(dbField="SD_DS_KEY", isPk=true)
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="SD_CREATETIME", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date createTime;
    @DBAnno.DBField(dbField="ORG_CATEGORY_NAME")
    private String orgCateGoryName;
    @DBAnno.DBField(dbField="DEFAULT_ORG_CATEGORY_NAME")
    private String defaultDBOrgCateGoryName;
    @DBAnno.DBField(dbField="DS_STATUS", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean dsDeployStatus;
    @DBAnno.DBField(dbField="SD_SYNC_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date sdSyncTime;
    @DBAnno.DBField(dbField="SD_SYNC_ORDER")
    private Integer syncOrder;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setDataScheme(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public String getDataScheme() {
        return this.dataSchemeKey;
    }

    @Override
    public Date getCreateTime() {
        if (this.createTime == null) {
            this.createTime = new Date();
            return this.createTime;
        }
        return this.createTime;
    }

    @Override
    public String getOrgCateGoryName() {
        return this.orgCateGoryName;
    }

    @Override
    public void serOrgCateGoryName(String orgCateGoryName) {
        this.orgCateGoryName = orgCateGoryName;
    }

    @Override
    public void setDefaultDBOrgCateGoryName(String defaultDBOrgCateGoryName) {
        this.defaultDBOrgCateGoryName = defaultDBOrgCateGoryName;
    }

    @Override
    public String getDefaultDBOrgCateGoryName() {
        return this.defaultDBOrgCateGoryName;
    }

    @Override
    public Boolean getDSDeployStatus() {
        return this.dsDeployStatus;
    }

    @Override
    public void setDSDeployStatus(Boolean dsDeployStatus) {
        this.dsDeployStatus = dsDeployStatus;
    }

    @Override
    public Date getSDSyncTime() {
        return this.sdSyncTime;
    }

    @Override
    public void setSDSyncTime(Date date) {
        this.sdSyncTime = date;
    }

    @Override
    public Integer getSyncOrder() {
        return this.syncOrder;
    }

    @Override
    public void setSyncOrder(Integer syncOrder) {
        this.syncOrder = syncOrder;
    }
}

