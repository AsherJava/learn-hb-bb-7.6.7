/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.ExportParamsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj
 *  com.jiuqi.nr.definition.formulamapping.facade.MappingParamsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj
 *  com.jiuqi.nr.definition.formulamapping.util.ExcelUtils
 *  com.jiuqi.nr.definition.formulamapping.util.ExcelUtils$ISheetRowData
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.formulamapping.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.ExportParamsObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import com.jiuqi.nr.definition.formulamapping.facade.MappingParamsObj;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import com.jiuqi.nr.definition.formulamapping.util.ExcelUtils;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.formulamapping.common.MappingKind;
import com.jiuqi.nr.formulamapping.common.MappingMode;
import com.jiuqi.nr.formulamapping.dao.FormulaMappingDao;
import com.jiuqi.nr.formulamapping.dao.FormulaMappingSchemeDaoImpl;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import com.jiuqi.nr.formulamapping.service.IFormulaMappingService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="formulaMappingServiceImpl2")
public class FormulaMappingServiceImpl
implements IFormulaMappingService {
    @Autowired
    private FormulaMappingDao formulaMappingDao;
    @Autowired
    private FormulaMappingSchemeDaoImpl formulaMappingSchemeDao;
    @Autowired
    private IFormulaRunTimeController iFormulaController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    private static final String NUMBER_FORMULAS = "number_formulas";
    private static final String NUMBER_FORMULAS_FORMCODE = "\u8868\u95f4\u516c\u5f0f";
    Logger logger = LogFactory.getLogger(FormulaMappingServiceImpl.class);
    private static final String SHEET_NAME_SEPARATE = " | ";

    protected FormInfo getFormInfo(String tFSchemeKey, String tFormKey) {
        FormInfo formInfo = new FormInfo();
        formInfo.formulaSchemeKey = tFSchemeKey;
        FormulaSchemeDefine formulaScheme = this.iFormulaController.queryFormulaSchemeDefine(formInfo.formulaSchemeKey);
        formInfo.formSchemeKey = formulaScheme.getFormSchemeKey();
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formInfo.formSchemeKey);
        formInfo.taskKey = formScheme.getTaskKey();
        if (NUMBER_FORMULAS.equals(tFormKey)) {
            formInfo.formKey = null;
        } else if (!StringUtils.isEmpty((String)tFormKey)) {
            formInfo.formKey = tFormKey;
            FormDefine targetForm = this.iRunTimeViewController.queryFormById(formInfo.formKey);
            formInfo.formCode = targetForm.getFormCode();
            formInfo.isQuote = targetForm.getQuoteType();
        }
        return formInfo;
    }

    protected FormInfo getSourceFormInfo(String sFSchemeKey, String tFormKey) throws JQException {
        FormDefine sourceForm;
        FormInfo formInfo = new FormInfo();
        formInfo.formulaSchemeKey = sFSchemeKey;
        FormulaSchemeDefine sourceFSDefine = this.iFormulaController.queryFormulaSchemeDefine(formInfo.formulaSchemeKey);
        formInfo.formSchemeKey = sourceFSDefine.getFormSchemeKey();
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formInfo.formSchemeKey);
        formInfo.taskKey = formScheme.getTaskKey();
        if (NUMBER_FORMULAS.equals(tFormKey)) {
            formInfo.formKey = null;
            return formInfo;
        }
        FormDefine targetForm = this.iRunTimeViewController.queryFormById(tFormKey);
        if (null != targetForm && null != (sourceForm = this.getSourceForm(formInfo.formSchemeKey, targetForm.getTitle()))) {
            formInfo.formKey = sourceForm.getKey();
            formInfo.formCode = sourceForm.getFormCode();
            formInfo.isQuote = sourceForm.getQuoteType();
            return formInfo;
        }
        return null;
    }

    private FormDefine getSourceForm(String formSchemeKey, String formTitle) throws JQException {
        List sourceForms = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        FormDefine sourceForm = null;
        if (null != sourceForms && !sourceForms.isEmpty()) {
            for (FormDefine f : sourceForms) {
                if (!formTitle.equals(f.getTitle())) continue;
                sourceForm = f;
                break;
            }
        }
        return sourceForm;
    }

    @Override
    public List<QueryFormulaObj> queryFormulas(String formulaSchemeKey, String groupKey, String formKey) throws JQException {
        FormInfo formInfo = this.getFormInfo(formulaSchemeKey, formKey);
        if (StringUtils.isEmpty((String)formKey)) {
            if (!StringUtils.isEmpty((String)groupKey)) {
                try {
                    List allFormDefine = this.iRunTimeViewController.getAllFormsInGroup(groupKey);
                    ArrayList<QueryFormulaObj> allFormulaObj = new ArrayList<QueryFormulaObj>();
                    for (FormDefine formDefine : allFormDefine) {
                        formInfo.formKey = formDefine.getKey();
                        formInfo.formCode = formDefine.getFormCode();
                        allFormulaObj.addAll(this.queryFormulas(formInfo));
                    }
                    return allFormulaObj;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_101, e.getMessage(), (Throwable)e);
                }
            }
            List allFormDefine = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(formInfo.formSchemeKey);
            ArrayList<QueryFormulaObj> allFormulaObj = new ArrayList<QueryFormulaObj>();
            for (FormDefine formDefine : allFormDefine) {
                formInfo = this.getFormInfo(formulaSchemeKey, formDefine.getKey());
                allFormulaObj.addAll(this.queryFormulas(formInfo));
            }
            formInfo.formKey = null;
            formInfo.formCode = null;
            allFormulaObj.addAll(this.queryFormulas(formInfo));
            return allFormulaObj;
        }
        return this.queryFormulas(formInfo);
    }

    private List<QueryFormulaObj> queryFormulas(FormInfo formInfo) throws JQException {
        List<FormulaDefine> formulaDefines = this.queryCheckFormulas(formInfo.formulaSchemeKey, formInfo.formKey, formInfo.isQuote);
        if (null == formulaDefines || formulaDefines.isEmpty()) {
            return Collections.emptyList();
        }
        List<Formula> formulas = this.buildFormula(formInfo.formKey, formInfo.formCode, formulaDefines);
        return this.parseFormula(formInfo.taskKey, formInfo.formSchemeKey, formulas);
    }

    protected List<FormulaDefine> queryCheckFormulas(String formulaSchemeKey, String formKey, boolean isQuote) throws JQException {
        List<FormulaDefine> formulaDefines = null;
        try {
            List qFormulaDefines;
            formulaDefines = this.iFormulaController.getCheckFormulasInForm(formulaSchemeKey, formKey);
            if (null == formulaDefines) {
                formulaDefines = Collections.emptyList();
            }
            if (!StringUtils.isEmpty((String)formKey) && isQuote && null != (qFormulaDefines = this.iFormulaController.queryPublicFormulaDefineByScheme(formulaSchemeKey, formKey)) && !qFormulaDefines.isEmpty()) {
                formulaDefines.addAll(qFormulaDefines.stream().filter(formula -> this.compareType((FormulaDefine)formula, DataEngineConsts.FormulaType.CHECK)).collect(Collectors.toList()));
                formulaDefines.sort(new Comparator<FormulaDefine>(){

                    @Override
                    public int compare(FormulaDefine o1, FormulaDefine o2) {
                        return o1.getOrder().compareTo(o2.getOrder());
                    }
                });
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_101, e.getMessage(), (Throwable)e);
        }
        return formulaDefines;
    }

    private List<FormulaDefine> queryCheckFormulas(String formulaSchemeKey) throws JQException {
        List<FormulaDefine> formulaDefines = null;
        try {
            List qFormulaDefines;
            formulaDefines = this.iFormulaController.getCheckFormulasInScheme(formulaSchemeKey);
            if (null == formulaDefines) {
                formulaDefines = Collections.emptyList();
            }
            if (null != (qFormulaDefines = this.iFormulaController.queryPublicFormulaDefineByScheme(formulaSchemeKey)) && !qFormulaDefines.isEmpty()) {
                formulaDefines.addAll(qFormulaDefines.stream().filter(formula -> this.compareType((FormulaDefine)formula, DataEngineConsts.FormulaType.CHECK)).collect(Collectors.toList()));
                formulaDefines.sort(new Comparator<FormulaDefine>(){

                    @Override
                    public int compare(FormulaDefine o1, FormulaDefine o2) {
                        return o1.getOrder().compareTo(o2.getOrder());
                    }
                });
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_101, e.getMessage(), (Throwable)e);
        }
        return formulaDefines;
    }

    private boolean compareType(FormulaDefine formula, DataEngineConsts.FormulaType formulaType) {
        if (null == formula) {
            return false;
        }
        if (formulaType.equals((Object)DataEngineConsts.FormulaType.CALCULATE)) {
            return formula.getUseCalculate();
        }
        if (formulaType.equals((Object)DataEngineConsts.FormulaType.CHECK)) {
            return formula.getUseCheck();
        }
        if (formulaType.equals((Object)DataEngineConsts.FormulaType.BALANCE)) {
            return formula.getUseBalance();
        }
        return false;
    }

    private List<QueryFormulaObj> parseFormula(String taskKey, String formSchemeKey, List<Formula> formulas) throws JQException {
        try {
            QueryContext qContext = this.buildQueryContext(taskKey, formSchemeKey, null);
            return this.parseFormula(qContext, formulas);
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_102, e.getMessage(), (Throwable)e);
        }
    }

    private String parseFormula(QueryContext qContext, Formula formula) throws JQException {
        ExecutorContext context = qContext.getExeContext();
        if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) {
            return null;
        }
        context.setDefaultGroupName(formula.getReportName());
        qContext.setDefaultGroupName(formula.getReportName());
        try {
            ReportFormulaParser parser = context.getCache().getFormulaParser(context);
            IExpression expression = parser.parseCond(formula.getFormula(), (IContext)qContext);
            IParsedExpression parsedExpression = this.buildParsedExpression(qContext, formula, expression);
            String meanning = parsedExpression.getMeanning(qContext);
            if (StringUtils.isNotEmpty((String)meanning)) {
                meanning = meanning.replace(" ", "");
            }
            return meanning;
        }
        catch (InterpretException | ParseException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_102, e.getMessage(), e);
        }
    }

    private List<QueryFormulaObj> parseFormula(QueryContext qContext, List<Formula> formulas) throws ParseException {
        ArrayList<QueryFormulaObj> formulaMappings = new ArrayList<QueryFormulaObj>();
        ExecutorContext context = qContext.getExeContext();
        ReportFormulaParser parser = context.getCache().getFormulaParser(context);
        for (int i = 0; i < formulas.size(); ++i) {
            Formula formula = formulas.get(i);
            if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) {
                formulaMappings.add(this.createMappingObj(formula, formula.getId(), formula.getFormula(), "", MappingKind.MAPPING, false));
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
                parsedExp = this.buildParsedExpression(qContext, formula, expression);
                List wildcardsExpresions = expression.expandWildcards((IContext)qContext);
                if (wildcardsExpresions != null) {
                    QueryFormulaObj groupMappingObj = this.createMappingObj(formula, formula.getId(), parsedExp.getFormula(qContext, context.getFormulaShowInfo()), "", MappingKind.GROUP, false);
                    formulaMappings.add(groupMappingObj);
                    for (IExpression exp : wildcardsExpresions) {
                        parsedExp = this.buildParsedExpression(qContext, formula, exp);
                        groupMappingObj.addChild(this.createMappingObj(formula, parsedExp.getKey(), parsedExp.getFormula(qContext, context.getFormulaShowInfo()), parsedExp.getMeanning(qContext), MappingKind.MAPPING, true));
                    }
                    continue;
                }
                formulaMappings.add(this.createMappingObj(formula, parsedExp.getKey(), parsedExp.getFormula(qContext, context.getFormulaShowInfo()), parsedExp.getMeanning(qContext), MappingKind.MAPPING, false));
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

    private String parseFormula(QueryContext qContext, FormInfo formInfo, FormulaDefine formulaDefine) throws ParseException, InterpretException {
        ExecutorContext context = qContext.getExeContext();
        ReportFormulaParser parser = context.getCache().getFormulaParser(context);
        Formula formula = this.extracted(formInfo.formKey, formInfo.formCode, formulaDefine);
        if (formula.getFormula() == null || formula.getFormula().trim().startsWith("//")) {
            return formula.getFormula();
        }
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            this.logger.info("\u89e3\u6790\u516c\u5f0f\uff1a{}", (Object)formula);
        }
        context.setDefaultGroupName(formula.getReportName());
        qContext.setDefaultGroupName(formula.getReportName());
        IExpression expression = parser.parseCond(formula.getFormula(), (IContext)qContext);
        IParsedExpression parsedExp = this.buildParsedExpression(qContext, formula, expression);
        return parsedExp.getFormula(qContext, context.getFormulaShowInfo());
    }

    protected QueryFormulaObj createMappingObj(Formula formula, String rowId, String expression, String meanning, MappingKind kind, boolean isChild) {
        QueryFormulaObj mappingObj = new QueryFormulaObj();
        mappingObj.setId(formula.getId());
        mappingObj.setRowId(rowId);
        mappingObj.setFormKey(StringUtils.isEmpty((String)formula.getFormKey()) ? NUMBER_FORMULAS : formula.getFormKey());
        mappingObj.setCode(formula.getCode());
        mappingObj.setCheckType(formula.getChecktype().intValue());
        mappingObj.setExpression(expression);
        mappingObj.setMeanning(meanning);
        mappingObj.setKind(kind.getValue());
        if (isChild) {
            mappingObj.setGroup(formula.getCode());
        }
        return mappingObj;
    }

    protected IParsedExpression buildParsedExpression(QueryContext context, Formula formula, IExpression expression) {
        DataEngineFormulaParser.tryExpandLinkPlus((QueryContext)context, (IASTNode)expression);
        CheckExpression checkExpression = new CheckExpression(expression, formula);
        ExpressionUtils.bindExtnedRWKey((CheckExpression)checkExpression);
        return checkExpression;
    }

    protected List<Formula> buildFormula(String formKey, String formCode, List<FormulaDefine> formulaDefines) {
        ArrayList<Formula> Formulas = new ArrayList<Formula>();
        for (FormulaDefine formulaDefine : formulaDefines) {
            Formula formula = this.extracted(formKey, formCode, formulaDefine);
            Formulas.add(formula);
        }
        return Formulas;
    }

    private Formula extracted(String formKey, String formCode, FormulaDefine formulaDefine) {
        Formula formula = new Formula();
        formula.setCode(formulaDefine.getCode());
        if (!NUMBER_FORMULAS.equals(formKey)) {
            formula.setFormKey(formKey);
            formula.setReportName(formCode);
        }
        formula.setFormula(formulaDefine.getExpression());
        formula.setId(formulaDefine.getKey());
        formula.setMeanning(formulaDefine.getDescription());
        formula.setChecktype(Integer.valueOf(formulaDefine.getCheckType()));
        formula.setAutoCalc(formulaDefine.getIsAutoExecute());
        return formula;
    }

    private Formula buildFormula(String formKey, String formCode, FormulaMappingDefine mapping) {
        Formula formula = new Formula();
        if (!NUMBER_FORMULAS.equals(formKey)) {
            formula.setFormKey(formKey);
            formula.setReportName(formCode);
        }
        formula.setId(mapping.getTargetKey());
        formula.setCode(mapping.getTargetCode());
        formula.setFormula(mapping.getTargetExpression());
        formula.setChecktype(Integer.valueOf(mapping.getTargetCheckType()));
        return formula;
    }

    protected QueryContext buildQueryContext(String taskKey, String formSchemeKey, IMonitor monitor) throws ParseException {
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionController);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.iDataDefinitionController, this.iEntityViewRunTimeController, formSchemeKey);
        context.setEnv((IFmlExecEnvironment)environment);
        if (monitor == null) {
            monitor = new AbstractMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        }
        return new QueryContext(context, monitor);
    }

    private FormulaMappingSchemeDefine getDefaultMappingScheme(String formulaSchemeKey) {
        List<FormulaMappingSchemeDefine> queryFMSDefines = this.formulaMappingSchemeDao.queryFMSDefine(formulaSchemeKey);
        if (null != queryFMSDefines && !queryFMSDefines.isEmpty()) {
            return queryFMSDefines.get(0);
        }
        return null;
    }

    @Override
    public String querySourceFormulaSchemeKey(String formulaSchemeKey) {
        FormulaMappingSchemeDefine queryFMSDefine = this.getDefaultMappingScheme(formulaSchemeKey);
        if (null != queryFMSDefine) {
            return queryFMSDefine.getSourceFSKey();
        }
        return null;
    }

    @Override
    public List<FormulaMappingDefine> queryFormulaMappings(String formulaSchemeKey) {
        FormulaMappingSchemeDefine queryFMSDefine = this.getDefaultMappingScheme(formulaSchemeKey);
        if (null != queryFMSDefine) {
            return this.formulaMappingDao.query(queryFMSDefine.getKey());
        }
        return Collections.emptyList();
    }

    @Override
    public List<FormulaMappingDefine> queryValidFormulaMappings(String targetFormulaSchemeKey, String sourceSchemeKey) {
        List<FormulaMappingSchemeDefine> queryFMSDefines = this.formulaMappingSchemeDao.queryFMSDefine(targetFormulaSchemeKey);
        if (null != queryFMSDefines && !queryFMSDefines.isEmpty()) {
            for (FormulaMappingSchemeDefine queryFMSDefine : queryFMSDefines) {
                if (!queryFMSDefine.getSourceFSKey().equals(sourceSchemeKey)) continue;
                return this.formulaMappingDao.queryValid(queryFMSDefine.getKey());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> querySourceFormulaSchemes(String targetFormulaSchemeKey) {
        List<FormulaMappingSchemeDefine> queryFMSDefines = this.formulaMappingSchemeDao.queryFMSDefine(targetFormulaSchemeKey);
        if (null != queryFMSDefines && !queryFMSDefines.isEmpty()) {
            return queryFMSDefines.stream().map(e -> e.getSourceFSKey()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public String querySourceFormulaSchemeKey(String targetFormulaSchemeKey, String sourceSchemeKey) {
        List<FormulaMappingSchemeDefine> queryFMSDefines = this.formulaMappingSchemeDao.queryFMSDefine(targetFormulaSchemeKey);
        if (null != queryFMSDefines && !queryFMSDefines.isEmpty()) {
            for (FormulaMappingSchemeDefine queryFMSDefine : queryFMSDefines) {
                if (!queryFMSDefine.getSourceFSKey().equals(sourceSchemeKey)) continue;
                return queryFMSDefine.getSourceFSKey();
            }
        }
        return null;
    }

    @Override
    public List<FormulaMappingDefine> queryValidFormulaMappings(String formulaSchemeKey) {
        FormulaMappingSchemeDefine queryFMSDefine = this.getDefaultMappingScheme(formulaSchemeKey);
        if (null != queryFMSDefine) {
            return this.formulaMappingDao.queryValid(queryFMSDefine.getKey());
        }
        return Collections.emptyList();
    }

    @Override
    public List<FormulaMappingDefine> queryFormulaMappings(String formulaSchemeKey, String formKey) {
        FormulaMappingSchemeDefine queryFMSDefine = this.getDefaultMappingScheme(formulaSchemeKey);
        if (null != queryFMSDefine) {
            return this.formulaMappingDao.query(queryFMSDefine.getKey(), formKey);
        }
        return Collections.emptyList();
    }

    @Override
    public List<FormulaMappingDefine> queryFormulaMappings(String formulaSchemeKey, String formKey, String targetCode) {
        FormulaMappingSchemeDefine queryFMSDefine = this.getDefaultMappingScheme(formulaSchemeKey);
        if (null != queryFMSDefine) {
            return this.formulaMappingDao.query(queryFMSDefine.getKey(), formKey, targetCode);
        }
        return Collections.emptyList();
    }

    @Override
    public Long queryFormulaMappingsCount(String schemekey, String groupKey, String formKey, String keyword) {
        return this.formulaMappingDao.queryCount(schemekey, groupKey, formKey, keyword);
    }

    @Override
    public List<FormulaMappingObj> queryFormulaMappingsByGroup(String schemeKey, String formKey, String groupkey) throws JQException {
        List<FormulaMappingDefine> query = this.formulaMappingDao.queryByGroup(schemeKey, formKey, groupkey);
        if (null != query && !query.isEmpty()) {
            return query.stream().map(FormulaMappingObj::toFormulaMappingObj).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public double queryMaxOrder(String schemekey, String formKey) throws JQException {
        return this.formulaMappingDao.queryMaxOrder(schemekey, formKey);
    }

    private List<FormulaMappingDefine> queryMappingByScheme(String scheme) {
        if (StringUtils.isNotEmpty((String)scheme)) {
            List<FormulaMappingDefine> query = this.formulaMappingDao.query(scheme);
            return query;
        }
        return new ArrayList<FormulaMappingDefine>();
    }

    private List<FormulaMappingDefine> queryByCondition(String schemekey, String groupKey, String formKey, String keyword, List<FormulaMappingDefine> datas) throws Exception {
        ArrayList<FormulaMappingDefine> condits = new ArrayList<FormulaMappingDefine>();
        List<Object> formkeys = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)groupKey) && StringUtils.isEmpty((String)formKey)) {
            List allFormsInGroup = this.iRunTimeViewController.getAllFormsInGroup(groupKey);
            if (null != allFormsInGroup && allFormsInGroup.size() != 0) {
                formkeys = allFormsInGroup.stream().map(e -> e.getKey()).collect(Collectors.toList());
            }
        } else if (StringUtils.isNotEmpty((String)formKey)) {
            formkeys.add(formKey);
        }
        for (FormulaMappingDefine data : datas) {
            boolean ishas = true;
            if (StringUtils.isNotEmpty((String)data.getGroup())) {
                ishas = false;
            }
            if (formkeys.size() != 0 && !formkeys.contains(data.getTargetFormKey())) {
                ishas = false;
            }
            if (StringUtils.isNotEmpty((String)keyword)) {
                boolean a = false;
                boolean b = false;
                boolean c = false;
                boolean d = false;
                if (data.getTargetCode() != null && data.getTargetCode().toUpperCase().contains(keyword.toUpperCase())) {
                    a = true;
                }
                if (data.getTargetExpression() != null && data.getTargetExpression().toUpperCase().contains(keyword.toUpperCase())) {
                    b = true;
                }
                if (data.getSourceCode() != null && data.getSourceCode().toUpperCase().contains(keyword.toUpperCase())) {
                    c = true;
                }
                if (data.getSourceExpression() != null && data.getSourceExpression().toUpperCase().contains(keyword.toUpperCase())) {
                    d = true;
                }
                if (!(a || b || c || d)) {
                    ishas = false;
                }
            }
            if (!ishas) continue;
            condits.add(data);
        }
        return condits;
    }

    private List<FormulaMappingDefine> queryByGroup(String schemekey, String formKey, String groupKey, List<FormulaMappingDefine> datas) {
        ArrayList<FormulaMappingDefine> condits = new ArrayList<FormulaMappingDefine>();
        for (FormulaMappingDefine data : datas) {
            if (!groupKey.equals(data.getGroup()) || !formKey.equals(data.getTargetFormKey()) || MappingKind.MAPPING.getValue() != data.getKind()) continue;
            condits.add(data);
        }
        return condits;
    }

    @Override
    public List<FormulaMappingObj> queryByCondition(String schemekey, String groupKey, String formKey, String keyword, int mappingType) throws Exception {
        ArrayList<FormulaMappingObj> result = new ArrayList<FormulaMappingObj>();
        List<FormulaMappingDefine> datasByScheme = this.queryMappingByScheme(schemekey);
        List<FormulaMappingDefine> formulaMappingDefines = this.queryByCondition(schemekey, groupKey, formKey, keyword, datasByScheme);
        if (null != formulaMappingDefines && !formulaMappingDefines.isEmpty()) {
            for (FormulaMappingDefine formulaMappingDefine : formulaMappingDefines) {
                List<FormulaMappingDefine> queryByGroup;
                FormulaMappingObj formulaMappingObj = FormulaMappingObj.toFormulaMappingObj((FormulaMappingDefine)formulaMappingDefine);
                if (MappingKind.GROUP.getValue() == formulaMappingObj.getKind() && null != (queryByGroup = this.queryByGroup(schemekey, formulaMappingObj.getFormKey(), formulaMappingObj.getCode(), datasByScheme)) && !queryByGroup.isEmpty()) {
                    formulaMappingObj.setChildren(queryByGroup.stream().map(FormulaMappingObj::toFormulaMappingObj).collect(Collectors.toList()));
                }
                if (mappingType != -1) {
                    if (null != formulaMappingObj.getChildren() && formulaMappingObj.getChildren().size() != 0) {
                        boolean ishav = false;
                        for (FormulaMappingObj child : formulaMappingObj.getChildren()) {
                            if (child.getMode() != mappingType) continue;
                            ishav = true;
                            break;
                        }
                        if (!ishav) continue;
                        result.add(formulaMappingObj);
                        continue;
                    }
                    if (formulaMappingObj.getMode() != mappingType) continue;
                    result.add(formulaMappingObj);
                    continue;
                }
                result.add(formulaMappingObj);
            }
        }
        return result;
    }

    @Override
    public List<FormulaMappingObj> queryFormulaMappings(String schemekey, String groupKey, String formKey, String keyword, int startRow, int endRow) throws JQException {
        List<FormulaMappingDefine> query = this.formulaMappingDao.query(schemekey, groupKey, formKey, keyword, startRow, endRow);
        if (null != query && !query.isEmpty()) {
            ArrayList<FormulaMappingObj> result = new ArrayList<FormulaMappingObj>();
            for (FormulaMappingDefine formulaMappingDefine : query) {
                List<FormulaMappingDefine> queryByGroup;
                FormulaMappingObj formulaMappingObj = FormulaMappingObj.toFormulaMappingObj((FormulaMappingDefine)formulaMappingDefine);
                if (MappingKind.GROUP.getValue() == formulaMappingObj.getKind() && null != (queryByGroup = this.formulaMappingDao.queryByGroup(schemekey, formulaMappingObj.getFormKey(), formulaMappingObj.getCode())) && !queryByGroup.isEmpty()) {
                    formulaMappingObj.setChildren(queryByGroup.stream().map(FormulaMappingObj::toFormulaMappingObj).collect(Collectors.toList()));
                }
                result.add(formulaMappingObj);
            }
            return result;
        }
        return Collections.emptyList();
    }

    private FormulaMappingDefine[] onlyToDefines(FormulaMappingObj[] objs, String schemeKey, Double order) {
        if (null == objs || 0 == objs.length) {
            return new FormulaMappingDefine[0];
        }
        ArrayList<FormulaMappingDefine> defines = new ArrayList<FormulaMappingDefine>();
        for (FormulaMappingObj obj : objs) {
            if (null != order) {
                FormulaMappingDefine group;
                if (MappingKind.GROUP.getValue() == obj.getKind() && null != (group = this.formulaMappingDao.queryGroup(schemeKey, obj.getFormKey(), obj.getCode()))) continue;
                obj.setKey(UUIDUtils.getKey());
                order = order + 1.0;
                obj.setOrder(order.doubleValue());
            }
            defines.add(FormulaMappingObj.toFormulaMappingDefine((FormulaMappingObj)obj, (String)schemeKey));
        }
        return defines.toArray(new FormulaMappingDefine[0]);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void addFormulaMappings(String schemeKey, FormulaMappingObj[] formulaMappings) throws JQException {
        double order = this.queryMaxOrder(schemeKey, null);
        Object[] defines = this.onlyToDefines(formulaMappings, schemeKey, order);
        try {
            this.formulaMappingDao.insert(defines);
        }
        catch (BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_103, e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteFormulaMappings(String[] keys) throws JQException {
        try {
            this.formulaMappingDao.delete(keys);
        }
        catch (BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_105, e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSourceMapping(String schemeKey, FormulaMappingObj[] formulaMappings) throws JQException {
        if (null == formulaMappings || 0 == formulaMappings.length) {
            return;
        }
        Object[] defines = new FormulaMappingDefine[formulaMappings.length];
        for (int i = 0; i < formulaMappings.length; ++i) {
            defines[i] = FormulaMappingObj.toDefineWithoutSource((FormulaMappingObj)formulaMappings[i], (String)schemeKey);
            defines[i].setMode(MappingMode.DEFAULT.getValue());
        }
        try {
            this.formulaMappingDao.update(defines);
        }
        catch (BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_104, e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteFormulaMappings(String schemeKey) throws JQException {
        try {
            this.formulaMappingDao.deleteBySchemeKey(schemeKey);
        }
        catch (BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_105, e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateFormulaMapping(String schemeKey, FormulaMappingObj formulaMapping) throws JQException {
        try {
            this.formulaMappingDao.update(FormulaMappingObj.toFormulaMappingDefine((FormulaMappingObj)formulaMapping, (String)schemeKey));
        }
        catch (BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_104, e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateFormulaMappings(String schemeKey, FormulaMappingObj[] formulaMappings) throws JQException {
        Object[] defines = this.onlyToDefines(formulaMappings, schemeKey, null);
        try {
            this.formulaMappingDao.update(defines);
        }
        catch (BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_104, e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void doMapping(MappingParamsObj mappingParamsObj) throws JQException {
        List<Object> mappings = null;
        mappings = null != mappingParamsObj.getFormulaMappings() && !mappingParamsObj.getFormulaMappings().isEmpty() ? mappingParamsObj.getFormulaMappings().stream().map(obj -> FormulaMappingObj.toFormulaMappingDefine((FormulaMappingObj)obj, (String)mappingParamsObj.getSchemeKey())).collect(Collectors.toList()) : this.formulaMappingDao.queryAll(mappingParamsObj.getSchemeKey(), mappingParamsObj.getFormGroupKey(), mappingParamsObj.getFormKey());
        if (null == mappings || mappings.isEmpty()) {
            return;
        }
        ArrayList<FormulaMappingDefine> updateMappings = new ArrayList<FormulaMappingDefine>();
        ArrayList<FormulaMappingDefine> addMappings = new ArrayList<FormulaMappingDefine>();
        try {
            MappingContext mappingContext = new MappingContext(mappingParamsObj.getSchemeKey(), mappingParamsObj.getTargetFormulaSchemeKey(), mappingParamsObj.getSourceFormulaSchemeKey());
            this.doMapping(mappingContext, mappings, updateMappings, addMappings);
            if (!addMappings.isEmpty()) {
                // empty if block
            }
            if (!updateMappings.isEmpty()) {
                this.formulaMappingDao.update(updateMappings.toArray());
            }
        }
        catch (ParseException | BeanParaException | DBParaException e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_103, e.getMessage(), e);
        }
    }

    private void doMapping(MappingContext context, List<FormulaMappingDefine> mappings, List<FormulaMappingDefine> updateMappings, List<FormulaMappingDefine> addMappings) throws JQException, ParseException {
        QueryContext qContext = context.getQueryContext();
        double maxOrder = this.queryMaxOrder(context.getSchemeKey(), null);
        String targetFormKey = null;
        Map<String, List<QueryFormulaObj>> sourceFormulasMap = null;
        for (int i = 0; i < mappings.size(); ++i) {
            Formula buildFormula;
            String targetMenning;
            FormulaMappingDefine mapping = mappings.get(i);
            if (MappingKind.GROUP.getValue() == mapping.getKind() || StringUtils.isEmpty((String)(targetMenning = this.parseFormula(qContext, buildFormula = this.buildFormula(targetFormKey = mapping.getTargetFormKey(), context.getFormCode(targetFormKey), mapping)))) || !(sourceFormulasMap = context.getSourceFormulas(targetFormKey)).containsKey(targetMenning)) continue;
            List<QueryFormulaObj> sourceFormulas = sourceFormulasMap.get(targetMenning);
            QueryFormulaObj sourceFormula = sourceFormulas.get(0);
            this.doMapping(mapping, sourceFormula);
            updateMappings.add(mapping);
            if (1 >= sourceFormulas.size()) continue;
            FormulaMappingDefine clone = null;
            for (int j = 1; j < sourceFormulas.size(); ++j) {
                clone = this.clone(mapping);
                sourceFormula = sourceFormulas.get(j);
                this.doMapping(clone, sourceFormula);
                clone.setKey(UUIDUtils.getKey());
                clone.setOrder(maxOrder += 1.0);
                addMappings.add(clone);
            }
        }
    }

    private FormulaMappingDefine clone(FormulaMappingDefine mapping) {
        FormulaMappingDefine clone = new FormulaMappingDefine();
        clone.setKey(mapping.getKey());
        clone.setSchemeKey(mapping.getSchemeKey());
        clone.setTargetFormKey(mapping.getTargetFormKey());
        clone.setTargetKey(mapping.getTargetKey());
        clone.setTargetCode(mapping.getTargetCode());
        clone.setTargetCheckType(mapping.getTargetCheckType());
        clone.setTargetExpression(mapping.getTargetExpression());
        clone.setGroup(mapping.getGroup());
        clone.setKind(mapping.getKind());
        clone.setOrder(mapping.getOrder());
        return clone;
    }

    private void doMapping(FormulaMappingDefine mapping, QueryFormulaObj sourceFormula) {
        mapping.setSourceKey(sourceFormula.getId());
        mapping.setSourceCode(sourceFormula.getCode());
        mapping.setSourceCheckType(sourceFormula.getCheckType());
        mapping.setSourceExpression(sourceFormula.getExpression());
        mapping.setMode(MappingMode.AUTO.getValue());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void doImport(String schemeKey, String targetFSKey, String sourceFSkey, InputStream input) throws JQException {
        try {
            Workbook workbook = ExcelUtils.create((InputStream)input);
            String formSchemeKey = null;
            if (StringUtils.isEmpty((String)targetFSKey) || StringUtils.isEmpty((String)sourceFSkey)) {
                FormulaMappingSchemeDefine mappingSchemeDefine = this.formulaMappingSchemeDao.queryFormulaMappingSchemeDefine(schemeKey);
                targetFSKey = mappingSchemeDefine.getTargetFSKey();
                sourceFSkey = mappingSchemeDefine.getSourceFSKey();
            }
            FormulaSchemeDefine formulaSchemeDefine = this.iFormulaController.queryFormulaSchemeDefine(targetFSKey);
            formSchemeKey = formulaSchemeDefine.getFormSchemeKey();
            FormInfo formInfo = this.getFormInfo(targetFSKey, null);
            QueryContext queryContext = this.buildQueryContext(formInfo.taskKey, formInfo.formSchemeKey, null);
            Map<String, FormulaDefine> sourceFormulasMap = this.queryCheckFormulas(sourceFSkey).stream().collect(Collectors.toMap(FormulaDefine::getCode, f -> f));
            double maxOrder = this.queryMaxOrder(schemeKey, null);
            ArrayList<FormulaMappingDefine> mappings = new ArrayList<FormulaMappingDefine>();
            for (Sheet sheet : workbook) {
                Object[][] values = ExcelUtils.getSheetValues((Sheet)sheet, (int)2);
                if (null == values || 0 == values.length) continue;
                String formKey = null;
                String formCode = this.getFormCode(sheet);
                Map<String, FormulaDefine> formulasMap = null;
                if (NUMBER_FORMULAS_FORMCODE.equals(formCode)) {
                    formKey = NUMBER_FORMULAS;
                    formulasMap = this.queryCheckFormulas(targetFSKey, null, false).stream().collect(Collectors.toMap(FormulaDefine::getCode, f -> f));
                } else {
                    FormDefine form = this.iRunTimeViewController.queryFormByCodeInScheme(formSchemeKey, formCode);
                    if (null == form) {
                        this.logger.info("\u516c\u5f0f\u6620\u5c04\u5bfc\u5165: \u4e0d\u5b58\u5728Code\u4e3a{}\u7684\u62a5\u8868, {}\u7684\u62a5\u8868\u516c\u5f0f\u6620\u5c04\u6570\u636e\u672a\u80fd\u6210\u529f\u5bfc\u5165!", (Object)formCode, (Object)formCode);
                        continue;
                    }
                    formKey = form.getKey();
                    formulasMap = this.queryCheckFormulas(targetFSKey, formKey, form.getQuoteType()).stream().collect(Collectors.toMap(FormulaDefine::getCode, f -> f));
                }
                this.formulaMappingDao.delete(schemeKey, formKey);
                HashMap mappingsMap = new HashMap();
                FormulaMappingDefine mapping = null;
                FormulaDefine formulaDefine = null;
                for (Object[] value : values) {
                    mapping = new FormulaMappingDefine();
                    mapping.setRowDatas(value);
                    formulaDefine = formulasMap.get(mapping.getTargetCode());
                    if (null == formulaDefine) {
                        this.logger.info("\u516c\u5f0f\u6620\u5c04\u5bfc\u5165: \u672a\u627e\u5230\u516c\u5f0fCode\u4e3a{}\u7684\u76ee\u6807\u516c\u5f0f, \u672c\u6761\u6620\u5c04\u672a\u80fd\u6210\u529f\u5bfc\u5165\uff01", (Object)mapping.getTargetCode());
                        continue;
                    }
                    if (!mappingsMap.containsKey((mapping = this.createMapping(mapping, schemeKey, formKey, formulaDefine, sourceFormulasMap, maxOrder += 1.0)).getTargetCode())) {
                        mappingsMap.put(mapping.getTargetCode(), new ArrayList());
                    }
                    ((List)mappingsMap.get(mapping.getTargetCode())).add(mapping);
                }
                formInfo.formKey = formKey;
                formInfo.formCode = formCode;
                for (Map.Entry entry : mappingsMap.entrySet()) {
                    if (((List)entry.getValue()).size() > 1) {
                        FormulaMappingDefine group = this.createGroup(schemeKey, formKey, formulasMap.get(entry.getKey()));
                        try {
                            String targetExpression = this.parseFormula(queryContext, formInfo, formulasMap.get(entry.getKey()));
                            group.setTargetExpression(targetExpression);
                        }
                        catch (Exception e) {
                            this.logger.error("\u516c\u5f0f\u6620\u5c04\u5bfc\u5165: \u83b7\u53d6\u6620\u5c04\u5206\u7ec4\u8868\u8fbe\u5f0f\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
                        }
                        group.setOrder(((FormulaMappingDefine)((List)entry.getValue()).get(0)).getOrder());
                        mappings.add(group);
                        for (FormulaMappingDefine mappingDefine : (List)entry.getValue()) {
                            mappingDefine.setGroup((String)entry.getKey());
                        }
                    }
                    mappings.addAll((Collection)entry.getValue());
                }
            }
            this.formulaMappingDao.insert(mappings.toArray());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_109);
        }
    }

    private String getSheetName(FormDefine form) {
        if (NUMBER_FORMULAS_FORMCODE.equals(form.getFormCode())) {
            return form.getFormCode();
        }
        return form.getFormCode() + SHEET_NAME_SEPARATE + form.getTitle();
    }

    private String getFormCode(Sheet sheet) {
        String sheetName = sheet.getSheetName();
        if (StringUtils.isEmpty((String)sheetName)) {
            return "";
        }
        if (NUMBER_FORMULAS_FORMCODE.equals(sheetName)) {
            return sheetName;
        }
        return sheetName.split(SHEET_NAME_SEPARATE)[0];
    }

    private FormulaMappingDefine createMapping(FormulaMappingDefine mapping, String schemeKey, String formKey, FormulaDefine formulaDefine, Map<String, FormulaDefine> sourceFormulasMap, double maxOrder) {
        mapping.setKey(UUIDUtils.getKey());
        mapping.setSchemeKey(schemeKey);
        mapping.setTargetFormKey(formKey);
        mapping.setTargetKey(formulaDefine.getKey());
        mapping.setTargetCheckType(formulaDefine.getCheckType());
        mapping.setKind(MappingKind.MAPPING.getValue());
        mapping.setOrder(maxOrder);
        FormulaDefine sourceFormulaDefine = null;
        if (!StringUtils.isEmpty((String)mapping.getSourceCode())) {
            sourceFormulaDefine = sourceFormulasMap.get(mapping.getSourceCode());
            if (null != sourceFormulaDefine) {
                mapping.setSourceKey(sourceFormulaDefine.getKey());
                mapping.setSourceCheckType(sourceFormulaDefine.getCheckType());
                mapping.setMode(MappingMode.MANUAL.getValue());
            } else {
                this.logger.info("\u516c\u5f0f\u6620\u5c04\u5bfc\u5165: \u672a\u627e\u5230\u516c\u5f0fCode\u4e3a{}\u7684\u6765\u6e90\u516c\u5f0f, \u672c\u6761\u6620\u5c04\u6ca1\u6709\u6765\u6e90\u516c\u5f0f!", (Object)mapping.getSourceCode());
                mapping.setSourceCode(null);
                mapping.setSourceExpression(null);
            }
        }
        return mapping;
    }

    private FormulaMappingDefine createGroup(String schemeKey, String formKey, FormulaDefine formula) {
        FormulaMappingDefine define = new FormulaMappingDefine();
        define.setKey(UUIDUtils.getKey());
        define.setSchemeKey(schemeKey);
        define.setTargetFormKey(formKey);
        define.setTargetKey(formula.getKey());
        define.setTargetCode(formula.getCode());
        define.setTargetExpression(formula.getExpression());
        define.setTargetCheckType(formula.getCheckType());
        define.setMode(MappingMode.DEFAULT.getValue());
        define.setKind(MappingKind.GROUP.getValue());
        return define;
    }

    @Override
    public Workbook doExport(ExportParamsObj params) throws JQException {
        Workbook workbook = ExcelUtils.create();
        this.checkParams(params);
        List<FormDefine> forms = this.getForms(params);
        String targetTitle = this.formulaMappingSchemeDao.queryFormulaMappingSchemeTitle(params.getTargetFSKey());
        String sourceTitle = this.formulaMappingSchemeDao.queryFormulaMappingSchemeTitle(params.getSourceFSKey());
        int width = 0;
        width = targetTitle.length() > sourceTitle.length() ? targetTitle.getBytes().length * 256 : sourceTitle.getBytes().length * 256;
        List<ExcelUtils.ISheetRowData> headRowDatas = this.getSheetHead(targetTitle, sourceTitle);
        List<Object> mappings = null;
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        for (FormDefine formDefine : forms) {
            mappings = this.formulaMappingDao.query(params.getSchemeKey(), formDefine.getKey());
            if (null == mappings || mappings.isEmpty()) continue;
            mappings = mappings.stream().filter(m -> MappingKind.GROUP.getValue() != m.getKind()).sorted(new Comparator<FormulaMappingDefine>(){

                @Override
                public int compare(FormulaMappingDefine m1, FormulaMappingDefine m2) {
                    return m1.getTargetCode().compareTo(m2.getTargetCode());
                }
            }).collect(Collectors.toList());
            Sheet sheet = workbook.createSheet(this.getSheetName(formDefine));
            rowDatas.clear();
            rowDatas.addAll(headRowDatas);
            rowDatas.addAll(mappings);
            ExcelUtils.setSheetValues((Sheet)sheet, rowDatas);
            this.sheetStyle(sheet, width);
        }
        return workbook;
    }

    private void sheetStyle(Sheet sheet, int width) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
        CellStyle cellStyle0 = sheet.getWorkbook().createCellStyle();
        cellStyle0.setFillForegroundColor(IndexedColors.SEA_GREEN.index);
        cellStyle0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle0.setAlignment(HorizontalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cellStyle0);
        sheet.getRow(0).getCell(1).setCellStyle(cellStyle0);
        sheet.getRow(0).getCell(2).setCellStyle(cellStyle0);
        sheet.getRow(0).getCell(3).setCellStyle(cellStyle0);
        CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
        sheet.getRow(1).getCell(0).setCellStyle(cellStyle1);
        sheet.getRow(1).getCell(1).setCellStyle(cellStyle1);
        sheet.getRow(1).getCell(2).setCellStyle(cellStyle1);
        sheet.getRow(1).getCell(3).setCellStyle(cellStyle1);
        cellStyle1.setFillForegroundColor(IndexedColors.SEA_GREEN.index);
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sheet.setColumnWidth(0, width * 4 / 10);
        sheet.setColumnWidth(1, width * 8 / 10);
        sheet.setColumnWidth(2, width * 4 / 10);
        sheet.setColumnWidth(3, width * 8 / 10);
    }

    private List<ExcelUtils.ISheetRowData> getSheetHead(final String targetTitle, final String sourceTitle) {
        ArrayList<ExcelUtils.ISheetRowData> headRowDatas = new ArrayList<ExcelUtils.ISheetRowData>();
        headRowDatas.add(new ExcelUtils.ISheetRowData(){

            public Object[] getRowDatas() {
                return new Object[]{"\u76ee\u6807\uff1a" + targetTitle, "", "\u6765\u6e90\uff1a" + sourceTitle, ""};
            }

            public void setRowDatas(Object[] datas) {
            }
        });
        headRowDatas.add(new ExcelUtils.ISheetRowData(){

            public Object[] getRowDatas() {
                return new Object[]{"\u516c\u5f0f\u7f16\u53f7", "\u516c\u5f0f\u5185\u5bb9", "\u516c\u5f0f\u7f16\u53f7", "\u516c\u5f0f\u5185\u5bb9"};
            }

            public void setRowDatas(Object[] datas) {
            }
        });
        return headRowDatas;
    }

    private void checkParams(ExportParamsObj params) {
        String targetFSKey = params.getTargetFSKey();
        String sourceFSkey = params.getSourceFSKey();
        if (StringUtils.isEmpty((String)targetFSKey) || StringUtils.isEmpty((String)sourceFSkey)) {
            FormulaMappingSchemeDefine mappingSchemeDefine = this.formulaMappingSchemeDao.queryFormulaMappingSchemeDefine(params.getSchemeKey());
            targetFSKey = mappingSchemeDefine.getTargetFSKey();
            sourceFSkey = mappingSchemeDefine.getSourceFSKey();
            params.setTargetFSKey(targetFSKey);
            params.setSourceFSKey(sourceFSkey);
        }
    }

    private List<FormDefine> getForms(ExportParamsObj params) throws JQException {
        List<FormDefine> forms = null;
        if (!StringUtils.isEmpty((String)params.getFormKey())) {
            if (NUMBER_FORMULAS.equals(params.getFormKey())) {
                RunTimeFormDefineImpl formDefine = new RunTimeFormDefineImpl();
                formDefine.setKey(NUMBER_FORMULAS);
                formDefine.setFormCode(NUMBER_FORMULAS_FORMCODE);
                forms = Collections.singletonList(formDefine);
            } else {
                FormDefine form = this.iRunTimeViewController.queryFormById(params.getFormKey());
                forms = Collections.singletonList(form);
            }
        } else {
            if (!StringUtils.isEmpty((String)params.getFormGroupKey())) {
                try {
                    forms = this.iRunTimeViewController.getAllFormsInGroup(params.getFormGroupKey());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_108);
                }
            }
            FormulaSchemeDefine formulaSchemeDefine = this.iFormulaController.queryFormulaSchemeDefine(params.getTargetFSKey());
            List groups = this.iRunTimeViewController.getAllFormGroupsInFormScheme(formulaSchemeDefine.getFormSchemeKey());
            forms = new ArrayList<FormDefine>();
            HashMap<String, String> formKeysMap = new HashMap<String, String>();
            try {
                for (int i = 0; i < groups.size(); ++i) {
                    List formsInGroup = this.iRunTimeViewController.getAllFormsInGroup(((FormGroupDefine)groups.get(i)).getKey());
                    for (int k = 0; k < formsInGroup.size(); ++k) {
                        if (formKeysMap.get(((FormDefine)formsInGroup.get(k)).getKey()) != null) continue;
                        formKeysMap.put(((FormDefine)formsInGroup.get(k)).getKey(), ((FormDefine)formsInGroup.get(k)).getKey());
                        forms.add((FormDefine)formsInGroup.get(k));
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_108);
            }
            RunTimeFormDefineImpl formDefine = new RunTimeFormDefineImpl();
            formDefine.setKey(NUMBER_FORMULAS);
            formDefine.setFormCode(NUMBER_FORMULAS_FORMCODE);
            forms.add((FormDefine)formDefine);
        }
        return forms;
    }

    @Override
    public void deleteMappings(String schemeKey, String formGroupKey, String formKey, FormulaMappingObj[] mappings) throws JQException {
        if (null != mappings && 0 != mappings.length) {
            String[] mappingKeys = new String[mappings.length];
            try {
                for (int i = 0; i < mappings.length; ++i) {
                    mappingKeys[i] = mappings[i].getKey();
                    if (MappingKind.GROUP.getValue() != mappings[i].getKind()) continue;
                    this.formulaMappingDao.deleteByGroup(schemeKey, mappings[i].getCode());
                }
            }
            catch (BeanParaException | DBParaException e) {
                e.printStackTrace();
                throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_105);
            }
            this.deleteFormulaMappings(mappingKeys);
            return;
        }
        if (StringUtils.isEmpty((String)schemeKey)) {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_105);
        }
        try {
            if (!StringUtils.isEmpty((String)formKey)) {
                this.formulaMappingDao.delete(schemeKey, formKey);
                return;
            }
            if (!StringUtils.isEmpty((String)formGroupKey)) {
                List forms = this.iRunTimeViewController.getAllFormsInGroup(formGroupKey);
                if (null != forms && !forms.isEmpty()) {
                    String[] formKeys = new String[forms.size()];
                    for (int i = 0; i < forms.size(); ++i) {
                        formKeys[i] = ((FormDefine)forms.get(i)).getKey();
                    }
                    this.formulaMappingDao.delete(schemeKey, formKeys);
                }
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_105);
        }
        this.deleteFormulaMappings(schemeKey);
    }

    @Override
    public void deleteSourceMappings(String schemeKey, String formGroupKey, String formKey, FormulaMappingObj[] mappings) throws JQException {
        if (null != mappings && 0 != mappings.length) {
            this.deleteSourceMapping(schemeKey, mappings);
            return;
        }
        if (StringUtils.isEmpty((String)schemeKey)) {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_104);
        }
        String[] formKeys = null;
        if (!StringUtils.isEmpty((String)formKey)) {
            formKeys = new String[]{formKey};
        } else if (!StringUtils.isEmpty((String)formGroupKey)) {
            try {
                List forms = this.iRunTimeViewController.getAllFormsInGroup(formGroupKey);
                if (null != forms && !forms.isEmpty()) {
                    formKeys = new String[forms.size()];
                    for (int i = 0; i < forms.size(); ++i) {
                        formKeys[i] = ((FormDefine)forms.get(i)).getKey();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_104);
            }
        }
        this.formulaMappingDao.cleanMapping(schemeKey, formKeys);
    }

    public class MappingContext {
        private String schemeKey;
        private String taskKey;
        private String formSchemeKey;
        private String targetFSKey;
        private String sourceFSKey;
        QueryContext qContext;
        Map<String, String> targetFormCodes = new HashMap<String, String>();
        Map<String, Map<String, List<QueryFormulaObj>>> sourceFormulas = new HashMap<String, Map<String, List<QueryFormulaObj>>>();

        public MappingContext(String schemeKey, String targetFSKey, String sourceFSKey) throws ParseException {
            this.schemeKey = schemeKey;
            if (StringUtils.isEmpty((String)targetFSKey) || StringUtils.isEmpty((String)sourceFSKey)) {
                FormulaMappingSchemeDefine mappingSchemeDefine = FormulaMappingServiceImpl.this.formulaMappingSchemeDao.queryFormulaMappingSchemeDefine(this.schemeKey);
                this.targetFSKey = mappingSchemeDefine.getTargetFSKey();
                this.sourceFSKey = mappingSchemeDefine.getSourceFSKey();
            } else {
                this.targetFSKey = targetFSKey;
                this.sourceFSKey = sourceFSKey;
            }
            FormInfo formInfo = FormulaMappingServiceImpl.this.getFormInfo(this.targetFSKey, null);
            this.taskKey = formInfo.taskKey;
            this.formSchemeKey = formInfo.formSchemeKey;
            this.qContext = FormulaMappingServiceImpl.this.buildQueryContext(this.taskKey, this.formSchemeKey, null);
        }

        public String getSchemeKey() {
            return this.schemeKey;
        }

        public QueryContext getQueryContext() {
            return this.qContext;
        }

        public String getFormCode(String formKey) {
            if (this.targetFormCodes.containsKey(formKey)) {
                return this.targetFormCodes.get(formKey);
            }
            if (FormulaMappingServiceImpl.NUMBER_FORMULAS.equals(formKey)) {
                this.targetFormCodes.put(formKey, FormulaMappingServiceImpl.NUMBER_FORMULAS);
                return FormulaMappingServiceImpl.NUMBER_FORMULAS;
            }
            FormDefine form = FormulaMappingServiceImpl.this.iRunTimeViewController.queryFormById(formKey);
            this.targetFormCodes.put(formKey, form.getFormCode());
            return form.getFormCode();
        }

        public Map<String, List<QueryFormulaObj>> getSourceFormulas(String formKey) throws JQException {
            if (this.sourceFormulas.containsKey(formKey)) {
                return this.sourceFormulas.get(formKey);
            }
            Map<String, List<QueryFormulaObj>> sourceFormulasMap = this.getSourceFormulasMap(this.sourceFSKey, formKey);
            this.sourceFormulas.put(formKey, sourceFormulasMap);
            return sourceFormulasMap;
        }

        private Map<String, List<QueryFormulaObj>> getSourceFormulasMap(String sFMSchemeKey, String tFormKey) throws JQException {
            FormInfo sourceFormInfo = FormulaMappingServiceImpl.this.getSourceFormInfo(sFMSchemeKey, tFormKey);
            if (null == sourceFormInfo) {
                return Collections.emptyMap();
            }
            List sourceFormulas = FormulaMappingServiceImpl.this.queryFormulas(sourceFormInfo);
            if (null == sourceFormulas || sourceFormulas.isEmpty()) {
                return Collections.emptyMap();
            }
            HashMap<String, List<QueryFormulaObj>> sourceFormulasMap = new HashMap<String, List<QueryFormulaObj>>();
            for (QueryFormulaObj formulaObj : sourceFormulas) {
                if (MappingKind.GROUP.getValue() == formulaObj.getKind() && null != formulaObj.getChildren()) {
                    for (QueryFormulaObj child : formulaObj.getChildren()) {
                        this.putSourceFormulas(sourceFormulasMap, child);
                    }
                    continue;
                }
                this.putSourceFormulas(sourceFormulasMap, formulaObj);
            }
            return sourceFormulasMap;
        }

        private void putSourceFormulas(Map<String, List<QueryFormulaObj>> sourceFormulasMap, QueryFormulaObj formulaObj) {
            String meanning = formulaObj.getMeanning();
            if (StringUtils.isEmpty((String)meanning)) {
                return;
            }
            if (!sourceFormulasMap.containsKey(meanning = meanning.replace(" ", ""))) {
                sourceFormulasMap.put(meanning, new ArrayList());
            }
            sourceFormulasMap.get(meanning).add(formulaObj);
        }
    }

    protected class FormInfo {
        String taskKey;
        String formSchemeKey;
        String formKey;
        String formCode;
        String formulaSchemeKey;
        boolean isQuote;

        protected FormInfo() {
        }
    }
}

