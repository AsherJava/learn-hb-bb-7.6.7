/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractTableModelDeployObjBuilder;
import org.springframework.stereotype.Component;

@Component
public class DefaultTableModelDeployObjBuilderImpl
extends AbstractTableModelDeployObjBuilder {
    @Override
    public DataTableType[] getDoForTableTypes() {
        return new DataTableType[]{DataTableType.TABLE, DataTableType.MD_INFO, DataTableType.DETAIL, DataTableType.MULTI_DIM, DataTableType.SUB_TABLE};
    }
}

