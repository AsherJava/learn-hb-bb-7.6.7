/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;

public interface IModifyTable
extends IEntityTable {
    public IModifyRow appendNewRow();

    public IModifyRow appendModifyRow(DimensionValueSet var1);

    public void deleteAll();

    public EntityCheckResult checkData();

    public EntityUpdateResult commitChange() throws EntityUpdateException;

    public EntityUpdateResult commitChangeWithOutCheck() throws EntityUpdateException;
}

