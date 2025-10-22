/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_DEPLOY_STATUS")
public class DataSchemeDeployStatusDO
implements DataSchemeDeployStatus,
Serializable {
    private static final long serialVersionUID = -1529349795219898404L;
    @DBAnno.DBField(dbField="DS_DS_KEY", isPk=true)
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="DS_DEPLOY_STATUS", tranWith="transDeployStatusEnum", appType=DeployStatusEnum.class, dbType=Integer.class)
    private DeployStatusEnum deployStatus;
    @DBAnno.DBField(dbField="DS_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    private Instant updateTime;
    @DBAnno.DBField(dbField="DS_LAST_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=false)
    private Instant lastUpdateTime;
    @DBAnno.DBField(dbField="DS_DEPLOY_DETAILS", tranWith="transDeployResult", appType=DeployResult.class, dbType=Clob.class)
    private DeployResult deployResult;
    @DBAnno.DBField(dbField="DS_DDL_STATUS_BIT", dbType=Integer.class)
    private int ddlStatusBit;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DeployStatusEnum getDeployStatus() {
        return this.deployStatus;
    }

    public void setDeployStatus(DeployStatusEnum deployStatus) {
        this.deployStatus = deployStatus;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Instant getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Instant lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public DeployResult getDeployResult() {
        return this.deployResult;
    }

    public void setDeployResult(DeployResult deployResult) {
        this.deployResult = deployResult;
    }

    public int getDdlStatusBit() {
        return this.ddlStatusBit;
    }

    public void setDdlStatusBit(int ddlStatusBit) {
        this.ddlStatusBit = ddlStatusBit;
    }
}

