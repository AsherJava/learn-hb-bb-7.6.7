/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 */
package com.jiuqi.nr.definition.deploy.dto;

import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.deploy.entity.ParamDeployStatusDO;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import java.util.Date;
import org.springframework.beans.BeanUtils;

public class ParamDeployStatusDTO
implements ParamDeployStatus {
    private String schemeKey;
    private ParamDeployEnum.ParamStatus paramStatus = ParamDeployEnum.ParamStatus.DEFAULT;
    private ParamDeployEnum.DeployStatus deployStatus = ParamDeployEnum.DeployStatus.NOT_DEPLOYED;
    private String deployDetail;
    private Date deployTime;
    private Date lastDeployTime;
    private String userKey;
    private String userName;
    private int ddlStatus;

    public ParamDeployStatusDTO() {
    }

    public ParamDeployStatusDTO(ParamDeployStatus status) {
        BeanUtils.copyProperties(status, this);
    }

    public ParamDeployStatusDTO(ParamDeployStatusDO status) {
        BeanUtils.copyProperties(status, this);
    }

    public static ParamDeployStatusDO toDO(ParamDeployStatus status) {
        ParamDeployStatusDO statusDO = new ParamDeployStatusDO();
        BeanUtils.copyProperties(status, statusDO);
        return statusDO;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public ParamDeployEnum.ParamStatus getParamStatus() {
        return this.paramStatus;
    }

    public void setParamStatus(ParamDeployEnum.ParamStatus paramStatus) {
        this.paramStatus = paramStatus;
    }

    public ParamDeployEnum.DeployStatus getDeployStatus() {
        return this.deployStatus;
    }

    public void setDeployStatus(ParamDeployEnum.DeployStatus deployStatus) {
        this.deployStatus = deployStatus;
    }

    public Date getDeployTime() {
        return this.deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }

    public Date getLastDeployTime() {
        return this.lastDeployTime;
    }

    public void setLastDeployTime(Date lastDeployTime) {
        this.lastDeployTime = lastDeployTime;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeployDetail() {
        return this.deployDetail;
    }

    public void setDeployDetail(String deployDetail) {
        this.deployDetail = deployDetail;
    }

    public int getDdlStatus() {
        return this.ddlStatus;
    }

    public void setDdlStatus(int ddlStatus) {
        this.ddlStatus = ddlStatus;
    }
}

