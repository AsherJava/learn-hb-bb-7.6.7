/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import java.util.Date;

@DBAnno.DBTable(dbTable="sys_analyversion")
public class AnalyVersionDefineImpl
implements AnalyVersionDefine {
    private static final long serialVersionUID = 536461854042388038L;
    @DBAnno.DBField(dbField="av_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="av_analytemplate_key")
    private String analytemplateKey;
    @DBAnno.DBField(dbField="av_entity_key")
    private String entityKey;
    @DBAnno.DBField(dbField="av_period")
    private String dateKey;
    @DBAnno.DBField(dbField="av_version_name")
    private String versionName;
    @DBAnno.DBField(dbField="av_version_key")
    private String bigDataKey;
    @DBAnno.DBField(dbField="createuser")
    private String createUser;
    @DBAnno.DBField(dbField="updateuser")
    private String updateUser;
    @DBAnno.DBField(dbField="createtime", isOrder=true, dbType=Date.class, appType=Date.class)
    private Date createTime;
    @DBAnno.DBField(dbField="updatetime", autoDate=true, dbType=Date.class, appType=Date.class)
    private Date updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getAnalytemplateKey() {
        return this.analytemplateKey;
    }

    @Override
    public void setAnalytemplateKey(String analytemplateKey) {
        this.analytemplateKey = analytemplateKey;
    }

    @Override
    public String getEntityKey() {
        return this.entityKey;
    }

    @Override
    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    @Override
    public String getDateKey() {
        return this.dateKey;
    }

    @Override
    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    @Override
    public String getVersionName() {
        return this.versionName;
    }

    @Override
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public String getBigDataKey() {
        return this.bigDataKey;
    }

    @Override
    public void setBigDataKey(String bigDataKey) {
        this.bigDataKey = bigDataKey;
    }

    @Override
    public String getCreateUser() {
        return this.createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String getUpdateUser() {
        return this.updateUser;
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

