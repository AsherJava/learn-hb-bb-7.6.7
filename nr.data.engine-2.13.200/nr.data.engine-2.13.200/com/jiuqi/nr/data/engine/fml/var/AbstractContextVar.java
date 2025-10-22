/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;

public abstract class AbstractContextVar
extends Variable {
    private static final long serialVersionUID = -8159405036757629376L;

    public AbstractContextVar(String varName, String varTitle, int dataType) {
        super(varName, varTitle, dataType);
    }

    public AbstractContextVar(String varName, int dataType) {
        this(varName, null, dataType);
    }

    public PeriodWrapper getPeriod(IContext context) {
        QueryContext qContext = (QueryContext)context;
        return qContext.getPeriodWrapper();
    }

    public IEntityRow getUnitEntityRow(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        return this.getCurrentDimensionEntityRow(qContext, qContext.getExeContext().getUnitDimension());
    }

    public IEntityRow getEntityRow(IContext context, String dimensionName) throws Exception {
        QueryContext qContext = (QueryContext)context;
        return this.getCurrentDimensionEntityRow(qContext, dimensionName);
    }

    public ContextUser getUser() {
        NpContext context = NpContextHolder.getContext();
        return context.getUser();
    }

    private IEntityRow getCurrentDimensionEntityRow(QueryContext qContext, String dimensionName) throws Exception {
        Object dimValue = qContext.getDimensionValue(dimensionName);
        if (dimValue == null) {
            return null;
        }
        String versionPeriod = qContext.getVersionPeriod();
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        EntityQueryHelper entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class);
        IEntityTable dimTable = entityQueryHelper.queryEntityTreeByDimensionName(qContext, dimensionName, masterKeys == null ? dimValue : masterKeys.getValue(dimensionName), versionPeriod);
        return dimTable.findByEntityKey(dimValue.toString());
    }
}

