/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.Node
 *  org.jsoup.nodes.TextNode
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.var.formula.helper;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.var.formula.common.ReportFormulaUtil;
import com.jiuqi.nr.var.formula.vo.ReportFormulaGroup;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaEvaluator {
    private static Logger logger = LoggerFactory.getLogger(FormulaEvaluator.class);
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityController;
    Pattern patternInt = Pattern.compile("(INT)\\(.*?\\)");
    Pattern patternDate = Pattern.compile("(YEAR|MONTH|DAY)\\(.*?\\)");
    Pattern patternRound = Pattern.compile("(ROUND)\\(.*?,(\\d+)\\)");
    Pattern patternDecimalPlaces = Pattern.compile("\\.(\\d+)");
    Pattern patternHtml = Pattern.compile("<[^>]+>");

    public void calculate(ReportFormulaGroup reportFormulaGroup, ReportVariableParseVO variableVO) {
        try {
            ExecutorContext executorContext = this.buildContext(reportFormulaGroup, variableVO);
            Map results = this.dataAccessProvider.newExpressionEvaluator().evalBatch(reportFormulaGroup.getFormulas(), executorContext, reportFormulaGroup.getFormulaDim().getDimensionValueSet());
            this.dealResult(results, reportFormulaGroup, variableVO);
        }
        catch (Exception e) {
            this.setCaculateErrorInfo(reportFormulaGroup, e);
        }
    }

    public ExecutorContext buildContext(ReportFormulaGroup reportFormulaGroup, ReportVariableParseVO variableVO) throws NoSuchFieldException, IllegalAccessException {
        String formSchemeKey = reportFormulaGroup.getFormSchemeKey();
        ReportFmlExecEnvironment fmlEnv = null;
        fmlEnv = StringUtils.isEmpty((CharSequence)formSchemeKey) ? new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController) : new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        Field entityViewDefineField = ReportFmlExecEnvironment.class.getDeclaredField("entityViewDefines");
        Field unitDimensionField = ReportFmlExecEnvironment.class.getDeclaredField("unitDimension");
        entityViewDefineField.setAccessible(true);
        unitDimensionField.setAccessible(true);
        ReportFormulaGroup.FormulaDim formulaDim = reportFormulaGroup.getFormulaDim();
        HashMap<String, EntityViewDefine> entityViewDefines = new HashMap<String, EntityViewDefine>();
        String dw = this.getTaskDw(formSchemeKey);
        for (String entityId : formulaDim.getEntityIds()) {
            entityViewDefines.put(this.entityMetaService.getDimensionName(entityId), this.entityController.buildEntityView(entityId));
        }
        String dimensionName = this.entityMetaService.getDimensionName(dw);
        if (entityViewDefines.containsKey(dimensionName)) {
            entityViewDefines.put(dimensionName, this.entityController.buildEntityView(dw));
        }
        entityViewDefineField.set(fmlEnv, entityViewDefines);
        if (StringUtils.isEmpty((CharSequence)((String)unitDimensionField.get(fmlEnv))) && StringUtils.isNotEmpty((CharSequence)dimensionName) && reportFormulaGroup.getFormulaDim().getDimensionValueSet().hasValue(dimensionName)) {
            unitDimensionField.set(fmlEnv, dimensionName);
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setEnv((IFmlExecEnvironment)fmlEnv);
        executorContext.setAutoDataMasking(true);
        executorContext.setOrgEntityId(dw);
        VariableManager variableManager = executorContext.getVariableManager();
        if (variableVO.getExt().containsKey("QCY_PROJECTID")) {
            HashMap<String, Object> varMap = new HashMap<String, Object>();
            varMap.put("QCY_PROJECTID", variableVO.getExt().get("QCY_PROJECTID"));
            ReportFormulaUtil.setFormulaVariable(variableManager, varMap);
        }
        return executorContext;
    }

    private String getTaskDw(String formSchemeKey) {
        String dw = "";
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            return "MD_ORG";
        }
        if (!StringUtils.isEmpty((CharSequence)formScheme.getDw())) {
            dw = formScheme.getDw();
        }
        if (StringUtils.isEmpty((CharSequence)dw)) {
            String taskKey = formScheme.getTaskKey();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            dw = taskDefine.getDw();
        }
        return dw;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void setCaculateErrorInfo(ReportFormulaGroup reportFormulaGroup, Exception ex) {
        ReentrantLock reentrantLock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
        try {
            String exText = new StringBuffer().append("\u516c\u5f0f\u53d8\u91cf[").append("%s").append("]\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0[").append(!StringUtil.isNullOrEmpty((String)ex.getMessage()) ? ex.getMessage() : ex.toString()).append("]").toString();
            int size = reportFormulaGroup.getFormulas().size();
            reentrantLock.lock();
            for (int i = 0; i < size; ++i) {
                String exTitle = String.format(exText, reportFormulaGroup.getFormulas().get(i));
                AnalysisReportLogHelper.log((String)(exTitle.length() > 100 ? exTitle.substring(0, 100) : exTitle), (String)(exTitle + ex.toString()), (int)AnalysisReportLogHelper.LOGLEVEL_ERROR);
                reportFormulaGroup.getElements().get(i).replaceWith((Node)new TextNode(exTitle));
            }
        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public void dealResult(Map<String, Object[]> results, ReportFormulaGroup formulaGroup, ReportVariableParseVO parseVO) {
        int count = formulaGroup.getFormulas().size();
        Boolean isScientificValue = this.getScientific(parseVO);
        for (int i = 0; i < count; ++i) {
            String formula = formulaGroup.getFormulas().get(i);
            Element element = formulaGroup.getElements().get(i);
            Object value = this.getValidValue(results, i);
            String result = this.formatResult(value, formula, isScientificValue);
            this.fillResultsToHTML(result, element);
        }
    }

    public Object getValidValue(Map<String, Object[]> results, int i) {
        Object closeToZero = null;
        for (Object[] objects : results.values()) {
            Object object = objects[i];
            if (object == null) continue;
            if (ReportFormulaUtil.isValueEmpty(object).booleanValue()) {
                closeToZero = object;
                continue;
            }
            return object;
        }
        return closeToZero;
    }

    public Boolean getScientific(ReportVariableParseVO variableParseVO) {
        Map ext = variableParseVO.getExt();
        String isScientificKey = "isScientific";
        if (ext.containsKey("isScientific")) {
            return Boolean.valueOf(ext.get(isScientificKey).toString());
        }
        return false;
    }

    public String formatResult(Object value, String formulaExpression, Boolean isScientific) {
        String resultData = ReportFormulaUtil.valueToSting(value, isScientific);
        if (StringUtils.isEmpty((CharSequence)resultData)) {
            return "";
        }
        if (this.patternInt.matcher(formulaExpression = formulaExpression.toUpperCase()).matches()) {
            resultData = resultData.replaceAll("(.*?)\\..*", "$1");
        } else if (this.patternDate.matcher(formulaExpression).find()) {
            resultData = resultData.replaceAll("(.*?)\\..*", "$1").replace(",", "");
        } else if (this.patternRound.matcher(formulaExpression).matches()) {
            resultData = this.padZero(resultData, formulaExpression);
        }
        if (StringUtils.isNotEmpty((CharSequence)resultData) && resultData.contains("\n")) {
            resultData = resultData.replace("\n", "ANALYSISREPORT_LINE_BREAKS");
        }
        return resultData == null ? "" : resultData;
    }

    public String padZero(String resultData, String formulaExpression) {
        try {
            int length;
            Matcher m = this.patternRound.matcher(formulaExpression);
            Matcher d = this.patternDecimalPlaces.matcher(resultData);
            if (m.find() && (length = Integer.parseInt(m.group(2))) > 0) {
                if (d.find()) {
                    String decimal = d.group(1);
                    length -= decimal.length();
                } else {
                    resultData = resultData + ".";
                }
                while (length-- > 0) {
                    resultData = resultData + "0";
                }
            }
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return resultData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void fillResultsToHTML(String result, Element varElement) {
        ReentrantLock lock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
        try {
            lock.lock();
            if (StringUtils.isEmpty((CharSequence)result)) {
                varElement.remove();
                return;
            }
            Matcher m = this.patternHtml.matcher(result);
            if (m.find()) {
                Elements elements = Jsoup.parseBodyFragment((String)result).body().children();
                for (Element element : elements) {
                    String style = element.attr("style");
                    String varStyle = varElement.attr("style");
                    element.attr("style", style + varStyle);
                    varElement.before((Node)element);
                }
            } else {
                varElement.text(result);
            }
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        finally {
            lock.unlock();
        }
    }
}

