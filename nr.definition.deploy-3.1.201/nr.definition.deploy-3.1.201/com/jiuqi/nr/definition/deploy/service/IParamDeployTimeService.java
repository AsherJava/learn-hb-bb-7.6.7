/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.service;

import com.jiuqi.nr.definition.common.ParamResourceType;
import java.util.Date;

public interface IParamDeployTimeService {
    public Date getDeployTime(ParamResourceType var1, String var2);

    public void refreshDeployTime();
}

