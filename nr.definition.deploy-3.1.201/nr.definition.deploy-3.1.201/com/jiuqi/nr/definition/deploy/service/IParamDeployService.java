/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.service;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import java.util.List;

public interface IParamDeployService {
    public void deploy(ParamDeployContext var1, String var2);

    public void rollback(ParamDeployContext var1, String var2);

    public void deploy(ParamResourceType var1, String var2, List<String> var3);

    public void rollback(ParamResourceType var1, String var2, List<String> var3);
}

