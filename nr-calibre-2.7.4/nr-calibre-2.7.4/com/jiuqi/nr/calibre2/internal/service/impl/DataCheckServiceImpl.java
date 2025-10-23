/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 */
package com.jiuqi.nr.calibre2.internal.service.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.calibre2.internal.service.IDataCheckService;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DataCheckServiceImpl
implements IDataCheckService {
    private static final Logger log = LoggerFactory.getLogger(DataCheckServiceImpl.class);
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataDefinitionDesignTimeController designTimeController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IDesignTimeViewController controller;

    @Override
    public void formulaCheck(CalibreDataVO dataVO) {
        Object value = dataVO.getValue().getExpression();
        if (value == null) {
            return;
        }
        ArrayList<Formula> formulaList = new ArrayList<Formula>();
        Formula formula = new Formula();
        formula.setFormula(value.toString());
        List<IParsedExpression> iParsedExpressions = this.executeCheck(dataVO.getFormSchemeKey(), formulaList);
        for (IParsedExpression iParsedExpression : iParsedExpressions) {
            Formula formula2 = iParsedExpression.getSource();
        }
    }

    private List<IParsedExpression> executeCheck(String formSchemeKey, List<Formula> formulaList) {
        List<Object> parsedExpressions = new ArrayList<IParsedExpression>();
        try {
            ExecutorContext context = new ExecutorContext(this.runtimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormulaScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.designTimeController);
            List formulaVariables = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.controller, this.designTimeController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE, null);
        }
        catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return parsedExpressions;
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(String schemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.controller.queryFormSchemeDefine(schemeKey);
        DesignTaskDefine taskDefine = this.controller.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }
}

