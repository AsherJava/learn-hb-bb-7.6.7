/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.intf.ICommonQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;
import com.jiuqi.nr.entity.engine.intf.impl.CommonQueryImpl;
import com.jiuqi.nr.entity.engine.intf.impl.EntityQueryBuilder;
import com.jiuqi.nr.entity.engine.intf.impl.ModifyTableImpl;

public class EntityModifyImpl
extends CommonQueryImpl
implements IEntityModify,
ICommonQuery {
    boolean batchUpdateModel = false;
    boolean ignoreCheckErrorData = false;

    public EntityModifyImpl(QueryContext environment) {
        super(environment);
    }

    @Override
    public IModifyTable executeUpdate(IContext context) throws JQException {
        this.getQueryContext().getLogger().trace("executeUpdate, batchModel:{}, ignoreCheckErrorData:{}", this.batchUpdateModel, this.ignoreCheckErrorData);
        this.setContext(context);
        this.resolutionVersion(false);
        ModifyTableImpl entityUpdate = new EntityQueryBuilder(this.getQueryContext()).buildRuntimeEnv(this.getEntityView().getEntityId(), this.getMasterKeys()).buildUpdateParam(this).getUpdateTable();
        entityUpdate.setBatchUpdateModel(this.batchUpdateModel);
        entityUpdate.setIgnoreCheckErrorData(this.ignoreCheckErrorData);
        return entityUpdate;
    }

    @Override
    public void batchUpdateModel(boolean batchUpdateModel) {
        this.batchUpdateModel = batchUpdateModel;
    }

    @Override
    public void ignoreCheckErrorData(boolean ignore) {
        this.ignoreCheckErrorData = ignore;
    }
}

