/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.function.util;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;

public class FuncExpressionParseUtil {
    public static String getOffSetPeriod(QueryContext qContext, IASTNode expression) {
        String offSetPeriod = null;
        if (expression instanceof DynamicDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)expression;
            QueryTable queryTable = dataNode.getQueryField().getTable();
            PeriodModifier periodModifier = queryTable.getPeriodModifier();
            if (periodModifier != null) {
                offSetPeriod = qContext.getExeContext().getPeriodAdapter().modify(qContext.getPeriodWrapper().toString(), periodModifier);
            } else if (queryTable.getDimensionRestriction() != null) {
                offSetPeriod = (String)queryTable.getDimensionRestriction().getValue("DATATIME");
            }
        }
        return offSetPeriod;
    }

    public static String getEvalExp(IContext context, IASTNode param) throws SyntaxException, InterpretException {
        String exp = null;
        exp = param instanceof DataNode && param.getType(context) == 6 ? (String)((DataNode)param).evaluate(context) : param.interpret(context, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA));
        return exp;
    }

    public static IRunTimeViewController getRunTimeViewController(ReportFmlExecEnvironment env) {
        IRunTimeViewController controller = env.getController();
        if (controller == null) {
            controller = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        }
        return controller;
    }

    public static IEntityViewRunTimeController getEntityViewRunTimeController(ReportFmlExecEnvironment env) {
        IEntityViewRunTimeController entityViewRunTimeController = env.getEntityViewRunTimeController();
        if (entityViewRunTimeController == null) {
            entityViewRunTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
        }
        return entityViewRunTimeController;
    }

    public static IDataDefinitionRuntimeController getRuntimeController(ReportFmlExecEnvironment env) {
        IDataDefinitionRuntimeController runtimeController = env.getRuntimeController();
        if (runtimeController == null) {
            runtimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        }
        return runtimeController;
    }

    public static ReportFmlExecEnvironment createNewReportFmlEnv(ExecutorContext exeContext, String linkedAlias) {
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)exeContext.getEnv();
        IRunTimeViewController controller = FuncExpressionParseUtil.getRunTimeViewController(env);
        TaskLinkDefine linkDefine = controller.queryTaskLinkByCurrentFormSchemeAndNumber(env.getFormSchemeKey(), linkedAlias);
        ReportFmlExecEnvironment newEnv = new ReportFmlExecEnvironment(controller, FuncExpressionParseUtil.getRuntimeController(env), FuncExpressionParseUtil.getEntityViewRunTimeController(env), linkDefine.getRelatedFormSchemeKey());
        return newEnv;
    }

    public static QueryContext createSrcQueryContext(QueryContext qContext, String reportName, String linkedAlias) throws ParseException {
        ExecutorContext destExeContext;
        ExecutorContext srcExeContext = destExeContext = qContext.getExeContext();
        if (linkedAlias != null) {
            srcExeContext = new ExecutorContext(destExeContext.getRuntimeController());
            if (destExeContext.isDesignTimeData()) {
                srcExeContext.setDesignTimeData(true, (IDataDefinitionDesignTimeController)SpringBeanUtils.getBean(IDataDefinitionDesignTimeController.class));
            }
            ReportFmlExecEnvironment srcEnv = FuncExpressionParseUtil.createNewReportFmlEnv(destExeContext, linkedAlias);
            srcExeContext.setEnv((IFmlExecEnvironment)srcEnv);
        }
        QueryContext srcQueryContext = new QueryContext(srcExeContext, qContext.getMonitor());
        srcQueryContext.setDefaultGroupName(reportName);
        return srcQueryContext;
    }
}

