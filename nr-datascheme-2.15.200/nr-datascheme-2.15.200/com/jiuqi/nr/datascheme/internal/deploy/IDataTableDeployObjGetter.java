/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.deploy;

import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IDataTableDeployObjGetter {
    public Collection<DataTableDeployObj> doGet(DeployContext var1);

    public Collection<DataTableDeployObj> doGet(DeployContext var1, Set<String> var2);

    default public List<RuntimeDataTableDTO> getRuntimeDataTable(String dataSchemeKey) {
        return this.getRuntimeDataTable(dataSchemeKey, 0);
    }

    public List<RuntimeDataTableDTO> getRuntimeDataTable(String var1, int var2);

    public List<RuntimeDataTableDTO> getRuntimeDataTable(List<String> var1);
}

