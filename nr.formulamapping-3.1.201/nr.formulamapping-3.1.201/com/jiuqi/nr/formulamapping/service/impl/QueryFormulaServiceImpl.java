/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj
 */
package com.jiuqi.nr.formulamapping.service.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import com.jiuqi.nr.formulamapping.common.MappingKind;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import com.jiuqi.nr.formulamapping.service.IQueryFormulaService;
import com.jiuqi.nr.formulamapping.service.impl.FormulaMappingServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryFormulaServiceImpl
implements IQueryFormulaService {
    @Autowired
    private FormulaMappingServiceImpl formulaMappingService;
    Logger logger = LogFactory.getLogger(QueryFormulaServiceImpl.class);

    @Override
    public List<QueryFormulaObj> queryFormulas(String formulaSchemeKey, String formKey) throws JQException {
        FormulaMappingServiceImpl.FormInfo formInfo = this.formulaMappingService.getFormInfo(formulaSchemeKey, formKey);
        List<FormulaDefine> formulaDefines = this.formulaMappingService.queryCheckFormulas(formInfo.formulaSchemeKey, formInfo.formKey, formInfo.isQuote);
        if (null == formulaDefines || formulaDefines.isEmpty()) {
            return Collections.emptyList();
        }
        List<Formula> formulas = this.formulaMappingService.buildFormula(formInfo.formKey, formInfo.formCode, formulaDefines);
        try {
            QueryContext qContext = this.formulaMappingService.buildQueryContext(formInfo.taskKey, formInfo.formSchemeKey, null);
            return this.parseFormula(qContext, formulas);
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_102, e.getMessage(), (Throwable)e);
        }
    }

    private List<QueryFormulaObj> parseFormula(QueryContext qContext, List<Formula> formulas) throws ParseException {
        ArrayList<QueryFormulaObj> formulaMappings = new ArrayList<QueryFormulaObj>();
        ExecutorContext context = qContext.getExeContext();
        ReportFormulaParser parser = context.getCache().getFormulaParser(context);
        for (int i = 0; i < formulas.size(); ++i) {
            Formula formula = formulas.get(i);
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) {
                formulaMappings.add(this.formulaMappingService.createMappingObj(formula, formula.getId(), formula.getFormula(), "", MappingKind.MAPPING, false));
                continue;
            }
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                this.logger.info("\u89e3\u6790\u516c\u5f0f\uff1a{}", (Object)formula);
            }
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            IParsedExpression parsedExp = null;
            try {
                IExpression expression = parser.parseCond(formula.getFormula(), (IContext)qContext);
                parsedExp = this.formulaMappingService.buildParsedExpression(qContext, formula, expression);
                List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                if (wildcardsExpresions != null) {
                    QueryFormulaObj groupMappingObj = this.formulaMappingService.createMappingObj(formula, formula.getId(), parsedExp.getFormula(qContext, context.getFormulaShowInfo()), "", MappingKind.GROUP, false);
                    groupMappingObj.setChildren(new ArrayList());
                    formulaMappings.add(groupMappingObj);
                    continue;
                }
                formulaMappings.add(this.formulaMappingService.createMappingObj(formula, parsedExp.getKey(), parsedExp.getFormula(qContext, context.getFormulaShowInfo()), parsedExp.getMeanning(qContext), MappingKind.MAPPING, false));
                continue;
            }
            catch (Exception e) {
                String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
                FormulaParseException se = new FormulaParseException(msg, (Throwable)e);
                se.setErrorFormua(formula);
                qContext.getMonitor().exception((Exception)se);
                this.logger.error(msg);
            }
        }
        return formulaMappings;
    }

    @Override
    public List<QueryFormulaObj> queryChildrenFormulas(String formulaSchemeKey, QueryFormulaObj groupFormula) throws JQException {
        if (MappingKind.GROUP.getValue() != groupFormula.getKind()) {
            return null;
        }
        FormulaMappingServiceImpl.FormInfo formInfo = this.formulaMappingService.getFormInfo(formulaSchemeKey, groupFormula.getFormKey());
        Formula formula = this.buildFormula(formInfo, groupFormula);
        try {
            QueryContext qContext = this.formulaMappingService.buildQueryContext(formInfo.taskKey, formInfo.formSchemeKey, null);
            ArrayList<QueryFormulaObj> formulaMappings = new ArrayList<QueryFormulaObj>();
            ExecutorContext context = qContext.getExeContext();
            ReportFormulaParser parser = context.getCache().getFormulaParser(context);
            context.setDefaultGroupName(formula.getReportName());
            qContext.setDefaultGroupName(formula.getReportName());
            IParsedExpression parsedExp = null;
            IExpression expression = parser.parseCond(formula.getFormula(), (IContext)qContext);
            List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
            if (wildcardsExpresions != null) {
                for (IExpression exp : wildcardsExpresions) {
                    parsedExp = this.formulaMappingService.buildParsedExpression(qContext, formula, exp);
                    formulaMappings.add(this.formulaMappingService.createMappingObj(formula, parsedExp.getKey(), parsedExp.getFormula(qContext, context.getFormulaShowInfo()), parsedExp.getMeanning(qContext), MappingKind.MAPPING, true));
                }
            }
            return formulaMappings;
        }
        catch (Exception e) {
            String msg = "\u516c\u5f0f  " + formula + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage();
            this.logger.error(msg);
            return null;
        }
    }

    private Formula buildFormula(FormulaMappingServiceImpl.FormInfo formInfo, QueryFormulaObj formulaObj) {
        Formula formula = new Formula();
        formula.setFormKey(formInfo.formKey);
        formula.setReportName(formInfo.formCode);
        formula.setId(formulaObj.getId());
        formula.setCode(formulaObj.getCode());
        formula.setFormula(formulaObj.getExpression());
        formula.setMeanning(formulaObj.getMeanning());
        formula.setChecktype(Integer.valueOf(formulaObj.getCheckType()));
        return formula;
    }
}

