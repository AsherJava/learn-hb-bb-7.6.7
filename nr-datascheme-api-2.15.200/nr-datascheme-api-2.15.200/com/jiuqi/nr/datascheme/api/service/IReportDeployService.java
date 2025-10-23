/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;

public interface IReportDeployService {
    public DeployResult insertAndDeployTableModel(boolean var1, DesignTableModel ... var2);

    public DeployResult updateAndDeployTableModel(boolean var1, DesignTableModel ... var2);

    public DeployResult deleteAndDeployTableModel(boolean var1, DesignTableModel ... var2);

    public void deleteAndDeployTableModel(String ... var1) throws Exception;
}

