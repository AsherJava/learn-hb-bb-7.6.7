/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.exception.NrParamSyncException
 */
package com.jiuqi.nr.definition.deploy.service;

import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import com.jiuqi.nr.definition.exception.NrParamSyncException;
import java.util.List;

public interface IRuntimeParamChangeService {
    public void reload(List<ParamDeployItem> var1) throws NrParamSyncException;

    public void reload(ParamDeployItem var1) throws NrParamSyncException;
}

