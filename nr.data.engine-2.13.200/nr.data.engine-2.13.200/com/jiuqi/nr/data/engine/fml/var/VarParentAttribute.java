/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.util.StringUtils;

public class VarParentAttribute
extends AbstractContextVar {
    private static final long serialVersionUID = -6169689034148637570L;
    public static final String VAR_NAME = "PARENT_ATTR";
    private final String attributeName;

    public VarParentAttribute(String varName, String attributeName) {
        super(varName, varName, 0);
        this.attributeName = attributeName;
    }

    public Object getVarValue(IContext context) throws Exception {
        IEntityRow dimRow = this.getUnitEntityRow(context);
        if (dimRow == null) {
            return null;
        }
        String parentEntityKey = dimRow.getParentEntityKey();
        if (StringUtils.isEmpty((String)parentEntityKey)) {
            return null;
        }
        IEntityRow parentRow = this.getCurrentDimensionEntityRow((QueryContext)context, parentEntityKey);
        if (parentRow == null) {
            return null;
        }
        AbstractData value = parentRow.getValue(this.attributeName);
        return value == null ? null : value.getAsObject();
    }

    private IEntityRow getCurrentDimensionEntityRow(QueryContext qContext, String parentEntityKey) throws Exception {
        String dimensionName = qContext.getExeContext().getUnitDimension();
        String versionPeriod = qContext.getVersionPeriod();
        EntityQueryHelper entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class);
        IEntityTable dimTable = entityQueryHelper.queryEntityTreeByDimensionName(qContext, dimensionName, parentEntityKey, versionPeriod);
        return dimTable.findByEntityKey(parentEntityKey);
    }
}

