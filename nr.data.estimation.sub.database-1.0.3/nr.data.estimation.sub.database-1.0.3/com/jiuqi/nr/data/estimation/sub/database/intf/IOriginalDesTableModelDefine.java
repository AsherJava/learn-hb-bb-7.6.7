/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.intf;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;

public interface IOriginalDesTableModelDefine {
    public DesignTableModelDefine getDesTableModelDefine();

    public DataTableType getDataTableType();
}

