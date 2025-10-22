/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 */
package com.jiuqi.nr.definition.deploy.service;

import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;

public interface IParamDeployStatusService {
    public ParamDeployStatus getDeployStatus(String var1);

    public void insertDeployStatus(ParamDeployStatus var1);

    public void updateDeployStatus(ParamDeployStatus var1);

    public void deleteDeployStatus(String var1);

    public void fixDeployStatus();

    public String getUpdateStatusSqlByDDLBit(String var1, int var2);
}

