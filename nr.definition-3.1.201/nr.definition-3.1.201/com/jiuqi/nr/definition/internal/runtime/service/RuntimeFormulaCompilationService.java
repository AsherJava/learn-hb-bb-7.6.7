/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.ICmpFmlProvider;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.FmlTrackColumnModelFinder;
import com.jiuqi.nr.definition.internal.env.FmlTrackExecEnvironment;
import com.jiuqi.nr.definition.internal.env.FmlTrackQueryContext;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaCompilationService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RuntimeFormulaCompilationService
implements IRuntimeFormulaCompilationService {
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    private static final Logger logger = LoggerFactory.getLogger(RuntimeFormulaCompilationService.class);

    @Override
    public List<IParsedExpression> cmpFmlOnceAllPar(ICmpFmlProvider fmlProvider) {
        long a = System.currentTimeMillis();
        if (fmlProvider.getFmlScheme() == null || CollectionUtils.isEmpty(fmlProvider.getCmpFml())) {
            return Collections.emptyList();
        }
        FormulaSchemeDefine formulaSchemeDefine = fmlProvider.getFmlScheme();
        List<FormulaDefine> allFormulasInScheme = fmlProvider.getCmpFml();
        List<IParsedExpression> iParsedExpressions = this.compileWithAllParam(formulaSchemeDefine, allFormulasInScheme);
        long b = System.currentTimeMillis();
        logger.info("\u7f16\u8bd1\u516c\u5f0f\u65b9\u6848{}\uff0c\u603b\u8ba1{}\u6761\u516c\u5f0f\uff0c\u8017\u65f6{}\u6beb\u79d2", formulaSchemeDefine.getKey(), allFormulasInScheme.size(), b - a);
        return iParsedExpressions;
    }

    @Override
    public List<IParsedExpression> cmpFmlOnceFormPar(ICmpFmlProvider fmlProvider) {
        if (fmlProvider.getFmlScheme() == null || CollectionUtils.isEmpty(fmlProvider.getCmpFml())) {
            return Collections.emptyList();
        }
        long a = System.currentTimeMillis();
        ArrayList<IParsedExpression> iParsedExpressions = new ArrayList<IParsedExpression>();
        FormulaSchemeDefine formulaSchemeDefine = fmlProvider.getFmlScheme();
        List<FormulaDefine> allFormulasInForm = fmlProvider.getCmpFml();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaSchemeDefine.getFormSchemeKey(), true);
        context.setEnv((IFmlExecEnvironment)environment);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormulaScheme(formulaSchemeDefine);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        List<FormulaDefine> checkFormulas = allFormulasInForm.stream().filter(FormulaDefine::getUseCheck).collect(Collectors.toList());
        List<FormulaDefine> calculateFormulas = allFormulasInForm.stream().filter(FormulaDefine::getUseCalculate).collect(Collectors.toList());
        List<Formula> checkFml = this.toFormulas(checkFormulas);
        List<Formula> calFml = this.toFormulas(calculateFormulas);
        try {
            FmlEngineBaseMonitor fmlEngineBaseMonitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
            fmlEngineBaseMonitor.start();
            List check = DataEngineFormulaParser.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)context, checkFml, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK, (IMonitor)fmlEngineBaseMonitor);
            List cal = DataEngineFormulaParser.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)context, calFml, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE, (IMonitor)fmlEngineBaseMonitor);
            fmlEngineBaseMonitor.finish();
            iParsedExpressions.addAll(check);
            iParsedExpressions.addAll(cal);
        }
        catch (ParseException e) {
            logger.debug("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38:" + e.getMessage(), e);
        }
        long b = System.currentTimeMillis();
        logger.info("\u7f16\u8bd1\u516c\u5f0f\u65b9\u6848{}\uff0c\u603b\u8ba1{}\u6761\u516c\u5f0f\uff0c\u8017\u65f6{}\u6beb\u79d2", formulaSchemeDefine.getKey(), allFormulasInForm.size(), b - a);
        return iParsedExpressions;
    }

    private List<IParsedExpression> compileWithAllParam(FormulaSchemeDefine formulaSchemeDefine, List<FormulaDefine> formulaDefineList) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        FmlTrackExecEnvironment environment = new FmlTrackExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaSchemeDefine.getFormSchemeKey());
        context.setEnv((IFmlExecEnvironment)environment);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormulaScheme(formulaSchemeDefine);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        return this.parseFormulas(context, formulaDefineList);
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(FormulaSchemeDefine formulaSchemeDefine) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formulaSchemeDefine.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    private List<IParsedExpression> parseFormulas(ExecutorContext context, List<FormulaDefine> formulaDefines) {
        ArrayList<IParsedExpression> iParsedExpressions = new ArrayList<IParsedExpression>();
        List<FormulaDefine> checkFormulas = formulaDefines.stream().filter(FormulaDefine::getUseCheck).collect(Collectors.toList());
        List<FormulaDefine> calculateFormulas = formulaDefines.stream().filter(FormulaDefine::getUseCalculate).collect(Collectors.toList());
        AbstractMonitor monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        try {
            FmlTrackQueryContext qContext = new FmlTrackQueryContext((com.jiuqi.np.dataengine.executors.ExecutorContext)context, (IMonitor)monitor, new FmlTrackColumnModelFinder((com.jiuqi.np.dataengine.executors.ExecutorContext)context));
            iParsedExpressions.addAll(this.getParsedExpressions(checkFormulas, true, context, monitor, qContext));
            iParsedExpressions.addAll(this.getParsedExpressions(calculateFormulas, false, context, monitor, qContext));
        }
        catch (Exception e) {
            logger.debug("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38:" + e.getMessage(), e);
        }
        return iParsedExpressions;
    }

    private List<IParsedExpression> getParsedExpressions(List<FormulaDefine> formulaDefines, boolean isCheck, ExecutorContext context, AbstractMonitor monitor, FmlTrackQueryContext qContext) {
        List<Formula> formulas = this.toFormulas(formulaDefines);
        List<IParsedExpression> iParsedExpressions = null;
        try {
            iParsedExpressions = isCheck ? this.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)context, formulas, DataEngineConsts.FormulaType.CHECK, (IMonitor)monitor, qContext) : this.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)context, formulas, DataEngineConsts.FormulaType.CALCULATE, (IMonitor)monitor, qContext);
        }
        catch (ParseException e) {
            logger.debug("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38:" + e.getMessage(), e);
        }
        return iParsedExpressions;
    }

    private List<Formula> toFormulas(List<FormulaDefine> formulaDefines) {
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for (FormulaDefine formulaDefine : formulaDefines) {
            String formKey = formulaDefine.getFormKey();
            String formCode = null;
            if (formKey != null) {
                FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                if (formDefine == null) continue;
                formCode = formDefine.getFormCode();
            }
            Formula formula = new Formula();
            formula.setId(formulaDefine.getKey());
            formula.setAutoCalc(false);
            formula.setChecktype(Integer.valueOf(formulaDefine.getCheckType()));
            formula.setCode(formulaDefine.getCode());
            formula.setFormKey(formulaDefine.getFormKey());
            formula.setFormula(formulaDefine.getExpression());
            formula.setMeanning(formulaDefine.getDescription());
            formula.setOrder(formulaDefine.getOrder());
            formula.setReportName(formCode);
            formulas.add(formula);
        }
        return formulas;
    }

    private List<IParsedExpression> parseFormula(com.jiuqi.np.dataengine.executors.ExecutorContext context, List<Formula> formulas, DataEngineConsts.FormulaType formulaType, IMonitor monitor, FmlTrackQueryContext qContext) throws ParseException {
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList<IParsedExpression>();
        ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(qContext.getExeContext());
        for (Formula formula : formulas) {
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) continue;
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                logger.info("\u89e3\u6790\u516c\u5f0f\uff1a" + formula);
            }
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            try {
                IExpression expression = this.parseFormula(qContext, parser, formula.getFormula(), formulaType);
                if (qContext.getExeContext().isJQReportModel()) {
                    List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                    if (wildcardsExpresions != null) {
                        for (IExpression exp : wildcardsExpresions) {
                            this.addParsedExpression(qContext, parsedExpressions, formula, formulaType, exp);
                        }
                        continue;
                    }
                    this.addParsedExpression(qContext, parsedExpressions, formula, formulaType, expression);
                    continue;
                }
                this.addParsedExpression(qContext, parsedExpressions, formula, formulaType, expression);
            }
            catch (Exception e) {
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, (Throwable)e);
                se.setErrorFormua(formula);
                monitor.exception((Exception)se);
            }
        }
        monitor.finish();
        return parsedExpressions;
    }

    private void addParsedExpression(QueryContext context, List<IParsedExpression> parsedExpressions, Formula formula, DataEngineConsts.FormulaType formulaType, IExpression expression) throws ParseException {
        DataEngineFormulaParser.tryExpandLinkPlus((QueryContext)context, (IASTNode)expression);
        if (formulaType == DataEngineConsts.FormulaType.CALCULATE) {
            DynamicDataNode assignNode = ExpressionUtils.getAssignNode((IASTNode)expression);
            CalcExpression calcExpression = new CalcExpression(expression, formula, assignNode, parsedExpressions.size());
            ExpressionUtils.bindExtnedRWKey((CheckExpression)calcExpression);
            if (calcExpression.getAssignNode() == null && calcExpression.getExtendAssignKey() == null && !(expression.getChild(0) instanceof FunctionNode)) {
                if (expression.getChild(0) instanceof IfThenElse) {
                    IfThenElse ifThen = (IfThenElse)expression.getChild(0);
                    if (!(ifThen.getChild(1) instanceof FunctionNode)) {
                        throw new ParseException("\u8868\u8fbe\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u8d4b\u503c\u8868\u8fbe\u5f0f\uff01");
                    }
                } else {
                    throw new ParseException("\u8868\u8fbe\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u8d4b\u503c\u8868\u8fbe\u5f0f\uff01");
                }
            }
            for (IASTNode node : expression) {
                DataLinkColumn dataLink;
                DynamicDataNode dataNode;
                if (!(node instanceof DynamicDataNode) || (dataNode = (DynamicDataNode)node).getQueryField().getDimensionRestriction() != null || dataNode.getStatisticInfo() != null || dataNode.getDataLink() == null || (dataLink = dataNode.getDataLink()).getExpandDims() == null || dataLink.getExpandDims().size() <= 0) continue;
                calcExpression.setNeedExpand(true);
            }
            parsedExpressions.add((IParsedExpression)calcExpression);
        } else {
            CheckExpression checkExpression = new CheckExpression(expression, formula);
            ExpressionUtils.bindExtnedRWKey((CheckExpression)checkExpression);
            parsedExpressions.add((IParsedExpression)checkExpression);
        }
    }

    private IExpression parseFormula(QueryContext qContext, ReportFormulaParser parser, String formula, DataEngineConsts.FormulaType formulaType) throws ParseException {
        if (formulaType == DataEngineConsts.FormulaType.CHECK) {
            return parser.parseCond(formula, (IContext)qContext);
        }
        if (formulaType == DataEngineConsts.FormulaType.CALCULATE) {
            return parser.parseAssign(formula, (IContext)qContext);
        }
        return parser.parseEval(formula, (IContext)qContext);
    }
}

