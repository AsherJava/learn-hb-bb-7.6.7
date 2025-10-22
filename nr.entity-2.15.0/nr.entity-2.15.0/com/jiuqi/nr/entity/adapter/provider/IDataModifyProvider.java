/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.provider;

import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;

public interface IDataModifyProvider {
    public EntityUpdateResult insertRows(IEntityUpdateParam var1) throws EntityUpdateException;

    public EntityUpdateResult deleteRows(IEntityDeleteParam var1) throws EntityUpdateException;

    public EntityUpdateResult updateRows(IEntityUpdateParam var1) throws EntityUpdateException;

    public EntityCheckResult rowsCheck(IEntityUpdateParam var1, boolean var2);
}

