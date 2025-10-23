/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.impl.DefaultConnectionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.util.CalcExpressionSortUtil
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 */
package com.jiuqi.nr.formula.internal;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.impl.DefaultConnectionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.CalcExpressionSortUtil;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.formula.dto.FormulaCheckObj;
import com.jiuqi.nr.formula.exception.FormulaException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaCycleCheckService {
    private static final Logger log = LoggerFactory.getLogger(FormulaCycleCheckService.class);
    @Autowired
    private com.jiuqi.nr.definition.api.IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Autowired
    private IDesignTimeFormulaController formulaDesignTimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController1;

    public List<IParsedExpression> checkCycle(String formSchemeKey, List<Formula> formulaList, IMonitor formulaMonitor) {
        List parsedExpressions;
        try {
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormulaScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.dataDefinitionDesignTimeController);
            List formulaVariables = this.formulaDesignTimeController.listFormulaVariByFormScheme(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.designTimeViewController1, this.dataDefinitionDesignTimeController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE, (IMonitor)formulaMonitor);
            ArrayList<CalcExpression> allCalc = new ArrayList<CalcExpression>();
            for (IParsedExpression parsedExpression : parsedExpressions) {
                if (!(parsedExpression instanceof CalcExpression)) continue;
                allCalc.add((CalcExpression)parsedExpression);
            }
            ArrayList noCycle = new ArrayList();
            ArrayList cycle = new ArrayList();
            QueryParam queryParam = new QueryParam((IConnectionProvider)new DefaultConnectionProvider(), this.dataDefinitionRuntimeController);
            QueryContext queryContext = new QueryContext(context, queryParam, formulaMonitor);
            CalcExpressionSortUtil.analysisCycles_new((QueryContext)queryContext, allCalc, noCycle, cycle);
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new FormulaException("\u5faa\u73af\u516c\u5f0f\u68c0\u6d4b\u65f6\u53d1\u751f\u5f02\u5e38");
        }
        return parsedExpressions;
    }

    public List<Formula> getCalcFormulaByScheme(DesignFormulaSchemeDefine formulaScheme) {
        List allFormula = this.formulaDesignTimeController.listFormulaByScheme(formulaScheme.getKey());
        List formDefines = this.designTimeViewController.listFormByFormScheme(formulaScheme.getFormSchemeKey());
        Map<String, String> formKeyToCode = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, FormDefine::getFormCode));
        List calcFormulas = allFormula.stream().filter(FormulaDefine::getUseCalculate).collect(Collectors.toList());
        ArrayList<Formula> useCalculateLists = new ArrayList<Formula>();
        for (DesignFormulaDefine formulaDefine : calcFormulas) {
            FormulaCheckObj obj = new FormulaCheckObj();
            obj.setSchemeKey(formulaScheme.getKey());
            obj.setUseCalculate(true);
            obj.setUseCheck(false);
            obj.setUseBalance(false);
            obj.setCode(formulaDefine.getCode());
            obj.setFormula(formulaDefine.getExpression());
            if (null != formKeyToCode.get(formulaDefine.getFormKey())) {
                obj.setReportName(formKeyToCode.get(formulaDefine.getFormKey()));
                useCalculateLists.add(obj);
                continue;
            }
            useCalculateLists.add(obj);
        }
        return useCalculateLists;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(String schemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(schemeKey);
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }
}

