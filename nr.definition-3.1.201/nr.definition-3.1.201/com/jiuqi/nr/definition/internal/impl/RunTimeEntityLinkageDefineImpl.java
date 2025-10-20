/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.EntityLinkageDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="sys_entitylinkage")
public class RunTimeEntityLinkageDefineImpl
implements EntityLinkageDefine {
    private static final long serialVersionUID = -8271355685151045463L;
    @DBAnno.DBField(dbField="el_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="el_order")
    private String order;
    @DBAnno.DBField(dbField="el_version")
    private String version;
    @DBAnno.DBField(dbField="el_title")
    private String title;
    @DBAnno.DBField(dbField="el_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="el_master_entity")
    private String masterEntityKey;
    @DBAnno.DBField(dbField="el_slave_entity")
    private String slaveEntityKey;
    @DBAnno.DBField(dbField="el_condition")
    private String linkageCondition;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getMasterEntityKey() {
        return this.masterEntityKey;
    }

    public void setMasterEntityKey(String masterEntityKey) {
        this.masterEntityKey = masterEntityKey;
    }

    @Override
    public String getSlaveEntityKeys() {
        return this.slaveEntityKey;
    }

    public void setSlaveEntityKey(String slaveEntityKey) {
        this.slaveEntityKey = slaveEntityKey;
    }

    @Override
    public String getLinkageCondition() {
        return this.linkageCondition;
    }

    public void setLinkageCondition(String linkageCondition) {
        this.linkageCondition = linkageCondition;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

