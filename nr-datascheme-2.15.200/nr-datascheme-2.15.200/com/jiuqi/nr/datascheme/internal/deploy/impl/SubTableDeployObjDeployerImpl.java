/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractDataTableDeployObjDeployer;
import org.springframework.stereotype.Component;

@Component
public class SubTableDeployObjDeployerImpl
extends AbstractDataTableDeployObjDeployer {
    @Override
    public DataTableType[] getDoForTableTypes() {
        return new DataTableType[]{DataTableType.SUB_TABLE};
    }

    @Override
    protected void deployDataTable(DataTableDeployObj dataTableInfo) {
        super.deployDataTable(dataTableInfo);
        this.runtimeDataSchemeManager.updateRuntimeDataTableRel(dataTableInfo.getDataTableKey());
    }
}

