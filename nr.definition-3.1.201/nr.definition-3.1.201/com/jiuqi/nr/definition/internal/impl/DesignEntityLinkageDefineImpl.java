/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="des_sys_entitylinkage")
public class DesignEntityLinkageDefineImpl
implements DesignEntityLinkageDefine {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String getMasterEntityKey() {
        return this.masterEntityKey;
    }

    @Override
    public String getSlaveEntityKeys() {
        return this.slaveEntityKey;
    }

    @Override
    public String getLinkageCondition() {
        return this.linkageCondition;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.order;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setMasterEntityKey(String masterEntityKey) {
        this.masterEntityKey = masterEntityKey;
    }

    @Override
    public void setSlaveEntityKey(String slaveEntityKey) {
        this.slaveEntityKey = slaveEntityKey;
    }

    @Override
    public void setLinkageCondition(String condition) {
        this.linkageCondition = condition;
    }
}

