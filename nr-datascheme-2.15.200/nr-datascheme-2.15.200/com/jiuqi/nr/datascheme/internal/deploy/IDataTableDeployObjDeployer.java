/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import java.util.Collection;

public interface IDataTableDeployObjDeployer {
    public DataTableType[] getDoForTableTypes();

    public void doDeploy(DeployContext var1, DataTableDeployObj var2, Collection<TableModelDeployObj> var3);
}

