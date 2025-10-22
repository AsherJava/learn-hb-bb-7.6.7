/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcDynamicMemoryDataNode;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcFormulaUtils {
    private static final Logger logger = LoggerFactory.getLogger(GcFormulaUtils.class);
    private static ReportFormulaParseUtil reportFormulaParseUtil = (ReportFormulaParseUtil)SpringContextUtils.getBean(ReportFormulaParseUtil.class);

    public static final void execute(GcTaskBaseArguments args, DefaultTableEntity entity, String formula) {
        try {
            GcReportSimpleExecutorContext context = new GcReportSimpleExecutorContext();
            context.setDimensionValueSet(args.getOrgId(), args.getPeriodStr(), args.getCurrency(), args.getOrgType(), args.getSelectAdjustCode(), args.getTaskId());
            context.setData(entity);
            context.registerFunctionProvider(new GcReportFunctionProvider());
            IExpression expression = reportFormulaParseUtil.parseFormula((ExecutorContext)context, formula);
            GcFormulaUtils.replaceDataNode((IASTNode)expression);
            GcFormulaUtils.execute(context, expression);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static final void execute(GcTaskBaseArguments args, DefaultTableEntity entity, String formula, List<DefaultTableEntity> entityList) {
        try {
            GcReportSimpleExecutorContext context = new GcReportSimpleExecutorContext();
            context.setDimensionValueSet(args.getOrgId(), args.getPeriodStr(), args.getCurrency(), args.getOrgType(), args.getSelectAdjustCode(), args.getTaskId());
            context.setData(entity);
            context.setItems(entityList);
            context.registerFunctionProvider(new GcReportFunctionProvider());
            IExpression expression = reportFormulaParseUtil.parseFormula((ExecutorContext)context, formula);
            GcFormulaUtils.replaceDataNode((IASTNode)expression);
            GcFormulaUtils.execute(context, expression);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static final void execute(GcReportSimpleExecutorContext context, IExpression expression) throws SyntaxException {
        QueryContext queryContext = context.generateQueryContext();
        expression.execute((IContext)queryContext);
    }

    private static void replaceDataNode(IASTNode node) {
        for (int i = 0; i < node.childrenSize(); ++i) {
            IASTNode child = node.getChild(i);
            if (child instanceof DynamicDataNode) {
                GcDynamicMemoryDataNode newNode = new GcDynamicMemoryDataNode((DynamicDataNode)child);
                node.setChild(i, (IASTNode)newNode);
                continue;
            }
            if (child.childrenSize() <= 0) continue;
            GcFormulaUtils.replaceDataNode(child);
        }
    }

    public static IExpression getExpression(GcTaskBaseArguments args, String formula) {
        IExpression expression = null;
        if (StringUtils.isEmpty((String)formula)) {
            return null;
        }
        try {
            GcReportSimpleExecutorContext context = new GcReportSimpleExecutorContext();
            context.registerFunctionProvider(new GcReportFunctionProvider());
            context.setDimensionValueSet(args.getOrgId(), args.getPeriodStr(), args.getCurrency(), args.getOrgType(), args.getSelectAdjustCode(), args.getTaskId());
            expression = reportFormulaParseUtil.parseFormula((ExecutorContext)context, formula);
            GcFormulaUtils.replaceDataNode((IASTNode)expression);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return expression;
    }

    public static boolean checkByExpression(IExpression expression, GcTaskBaseArguments args, DefaultTableEntity entity) {
        if (expression == null) {
            return true;
        }
        try {
            GcReportSimpleExecutorContext context = new GcReportSimpleExecutorContext();
            context.setData(entity);
            context.registerFunctionProvider(new GcReportFunctionProvider());
            context.setDimensionValueSet(args.getOrgId(), args.getPeriodStr(), args.getCurrency(), args.getOrgType(), args.getSelectAdjustCode(), args.getTaskId());
            return GcFormulaUtils.check(context, expression);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static final boolean check(GcTaskBaseArguments args, DefaultTableEntity entity, String formula) {
        if (StringUtils.isEmpty((String)formula)) {
            return true;
        }
        try {
            GcReportSimpleExecutorContext context = new GcReportSimpleExecutorContext();
            context.setData(entity);
            context.registerFunctionProvider(new GcReportFunctionProvider());
            context.setDimensionValueSet(args.getOrgId(), args.getPeriodStr(), args.getCurrency(), args.getOrgType(), args.getSelectAdjustCode(), args.getTaskId());
            IExpression expression = reportFormulaParseUtil.parseFormula((ExecutorContext)context, formula);
            GcFormulaUtils.replaceDataNode((IASTNode)expression);
            return GcFormulaUtils.check(context, expression);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static final boolean check(GcReportSimpleExecutorContext context, IExpression expression) throws SyntaxException {
        QueryContext queryContext = context.generateQueryContext();
        return expression.judge((IContext)queryContext);
    }
}

