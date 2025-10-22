/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import java.util.List;

public class parentZBValue
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 2721319941883225081L;

    public parentZBValue() {
        this.parameters().add(new Parameter("DIMVER", 6, "\u4e3b\u7ef4\u5ea6\u7248\u672c\u7684\u6807\u8bc6"));
        this.parameters().add(new Parameter("ZbExp", 6, "\u6307\u6807\u8868\u8fbe\u5f0f"));
    }

    public String name() {
        return "parentZBValue";
    }

    public String title() {
        return "\u83b7\u5f97\u7236\u8282\u70b9\u7684\u6307\u6807(\u4ec5\u7528\u4e8e\u517c\u5bb9JQR)";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            try {
                ExecutorContext exeContext = qContext.getExeContext();
                String unitDim = exeContext.getUnitDimension();
                IEntityRow unitEntityRow = this.getDimensionEntityRow(qContext, unitDim);
                DimensionValueSet dim = new DimensionValueSet(qContext.getCurrentMasterKey());
                dim.setValue(unitDim, (Object)unitEntityRow.getEntityKeyData());
                String zbExp = FuncExpressionParseUtil.getEvalExp(context, parameters.get(1));
                IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
                IExpressionEvaluator expressionEvaluator = dataAccessProvider.newExpressionEvaluator();
                return expressionEvaluator.evalValue(zbExp, exeContext, dim);
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
        }
        return null;
    }

    private IEntityRow getDimensionEntityRow(QueryContext qContext, String dimensionName) throws Exception {
        Object dimValue = qContext.getDimensionValue(dimensionName);
        String versionPeriod = qContext.getVersionPeriod();
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        EntityQueryHelper entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class);
        IEntityTable dimTable = entityQueryHelper.queryEntityTreeByDimensionName(qContext, dimensionName, masterKeys == null ? dimValue : masterKeys.getValue(dimensionName), versionPeriod);
        return dimTable.findByEntityKey(dimValue.toString());
    }

    public boolean isDeprecated() {
        return true;
    }
}

