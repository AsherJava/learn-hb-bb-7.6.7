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

public interface IResourceDeployService {
    public ParamResourceType getType();

    public void deploy(ParamDeployContext var1, String var2);

    public void deploy(String var1, List<String> var2);

    public void rollback(ParamDeployContext var1, String var2);

    public void rollback(String var1, List<String> var2);
}

