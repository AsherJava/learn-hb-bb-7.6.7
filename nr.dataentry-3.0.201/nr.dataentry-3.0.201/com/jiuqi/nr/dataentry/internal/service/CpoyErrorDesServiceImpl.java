/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.exception.NotFoundTaskException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.copydes.CheckDesCopyParam;
import com.jiuqi.nr.dataentry.copydes.ICopyDesService;
import com.jiuqi.nr.dataentry.internal.service.CopyErrorDesDimMappingProvider;
import com.jiuqi.nr.dataentry.internal.service.CopyErrorDesFmlMappingProvider;
import com.jiuqi.nr.dataentry.internal.service.CurrencyService;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.ICpoyErrorDesService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.exception.NotFoundTaskException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpoyErrorDesServiceImpl
implements ICpoyErrorDesService {
    private static final Logger logger = LoggerFactory.getLogger(CpoyErrorDesServiceImpl.class);
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private ICopyDesService copyDesService;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private CurrencyService currencyService;

    @Override
    public String queryLastPeriod(JtableContext context) {
        TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(context.getTaskKey());
        if (taskData == null) {
            throw new NotFoundTaskException(new String[]{context.getTaskKey()});
        }
        IPeriodProvider periodProvider = null;
        for (EntityViewData view : taskData.getEntitys()) {
            if (!this.periodEntityAdapter.isPeriodEntity(view.getKey())) continue;
            periodProvider = this.periodEntityAdapter.getPeriodProvider(view.getKey());
        }
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setPeriodModifier(-1);
        if (periodProvider != null && context.getDimensionSet() != null && context.getDimensionSet().get("DATATIME") != null) {
            return periodProvider.modify(((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue(), periodModifier);
        }
        return null;
    }

    @Override
    public void copyErrorDesfromLastPeriod(JtableContext context, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        EntityViewData masterEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        String MD_ORG = masterEntity.getDimensionName();
        CheckDesCopyParam checkDesCopyParam = new CheckDesCopyParam();
        String lastPeriod = this.queryLastPeriod(context);
        Map dimensionSet = context.getDimensionSet();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_READ);
        accessFormParam.setFormKeys(new ArrayList());
        accessFormParam.setTaskKey(context.getTaskKey());
        accessFormParam.setFormSchemeKey(context.getFormSchemeKey());
        accessFormParam.setCollectionMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)context.getFormSchemeKey()));
        DimensionAccessFormInfo dimensionAccessFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List acessFormInfos = dimensionAccessFormInfo.getAccessForms();
        if (acessFormInfos.size() == 0) {
            logger.error("\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e\uff1a\u62a5\u8868\u65e0\u6743\u9650");
            asyncTaskMonitor.finish("copyPeriodDesSuccess", (Object)"\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
            return;
        }
        double process = 1 / acessFormInfos.size();
        for (int i = 0; i < acessFormInfos.size(); ++i) {
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"");
                LogHelper.info((String)"\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
            DimensionAccessFormInfo.AccessFormInfo accessFormInfo = (DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(i);
            Map dimensionValue = accessFormInfo.getDimensions();
            List forms = accessFormInfo.getFormKeys();
            DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection((Map)dimensionValue, (String)context.getFormSchemeKey());
            CopyErrorDesDimMappingProvider copyErrorDesDimMappingProvider = new CopyErrorDesDimMappingProvider();
            copyErrorDesDimMappingProvider.setMDCODE(MD_ORG);
            copyErrorDesDimMappingProvider.setTargetDim(dimCollection.getDimensionCombinations());
            HashMap<String, DimensionValue> sourceDimensionSet = new HashMap<String, DimensionValue>();
            for (String key : dimensionValue.keySet()) {
                DimensionValue dimension = new DimensionValue((DimensionValue)dimensionValue.get(key));
                sourceDimensionSet.put(key, dimension);
            }
            ((DimensionValue)sourceDimensionSet.get("DATATIME")).setValue(lastPeriod);
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(lastPeriod, context.getTaskKey());
            copyErrorDesDimMappingProvider.setSourceDim(DimensionValueSetUtil.buildDimensionCollection(sourceDimensionSet, (String)schemePeriodLinkDefine.getSchemeKey()));
            CopyErrorDesFmlMappingProvider copyErrorDesFmlMappingProvider = new CopyErrorDesFmlMappingProvider();
            forms.add("00000000-0000-0000-0000-000000000000");
            ArrayList<FormulaMappingDefine> formulaMappingDefines = new ArrayList<FormulaMappingDefine>();
            FormulaShowInfo jqStyleShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
            ExecutorContext executorContext = this.createExecutorContext(context.getFormSchemeKey());
            QueryContext queryContext = new QueryContext(executorContext, null);
            List curCheckExpressions = this.formulaRunTimeController.getParsedExpressionByForms(context.getFormulaSchemeKey(), forms, DataEngineConsts.FormulaType.CHECK);
            if (schemePeriodLinkDefine.getSchemeKey().equals(context.getFormSchemeKey())) {
                copyErrorDesFmlMappingProvider.setSourceFmlScheme(context.getFormulaSchemeKey());
                for (IParsedExpression parsedExpression : curCheckExpressions) {
                    FormulaMappingDefine formulaMappingDefine = new FormulaMappingDefine();
                    formulaMappingDefine.setTargetFormKey(parsedExpression.getSource().getFormKey());
                    formulaMappingDefine.setTargetKey(parsedExpression.getSource().getId());
                    formulaMappingDefine.setTargetCode(parsedExpression.getSource().getCode());
                    formulaMappingDefine.setTargetExpression(parsedExpression.getFormula(queryContext, jqStyleShowInfo));
                    formulaMappingDefine.setSourceKey(parsedExpression.getSource().getId());
                    formulaMappingDefine.setSourceCode(parsedExpression.getSource().getCode());
                    formulaMappingDefine.setSourceExpression(parsedExpression.getFormula(queryContext, jqStyleShowInfo));
                    formulaMappingDefines.add(formulaMappingDefine);
                }
            } else {
                Object formDefine2;
                FormulaSchemeDefine curformulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(context.getFormulaSchemeKey());
                List formulaSchemeDefines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(schemePeriodLinkDefine.getSchemeKey());
                String formulaSchemeKey = null;
                for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
                    if (!formulaSchemeDefine.getTitle().equals(curformulaSchemeDefine.getTitle())) continue;
                    formulaSchemeKey = formulaSchemeDefine.getKey();
                }
                if (formulaSchemeKey == null) {
                    return;
                }
                copyErrorDesFmlMappingProvider.setSourceFmlScheme(formulaSchemeKey);
                HashMap<String, IParsedExpression> sourceFormula = new HashMap<String, IParsedExpression>();
                List formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(schemePeriodLinkDefine.getSchemeKey());
                ArrayList<String> lastPeriodforms = new ArrayList<String>();
                for (Object formDefine2 : formDefines) {
                    lastPeriodforms.add(formDefine2.getKey());
                }
                List lastCheckExpressions = this.formulaRunTimeController.getParsedExpressionByForms(formulaSchemeKey, lastPeriodforms, DataEngineConsts.FormulaType.CHECK);
                formDefine2 = lastCheckExpressions.iterator();
                while (formDefine2.hasNext()) {
                    IParsedExpression parsedExpression = (IParsedExpression)formDefine2.next();
                    sourceFormula.put(parsedExpression.getSource().getCode(), parsedExpression);
                }
                ExecutorContext executorContext1 = this.createExecutorContext(schemePeriodLinkDefine.getSchemeKey());
                QueryContext queryContext1 = new QueryContext(executorContext1, null);
                for (IParsedExpression parsedExpression : curCheckExpressions) {
                    if (!sourceFormula.containsKey(parsedExpression.getSource().getCode())) continue;
                    FormulaMappingDefine formulaMappingDefine = new FormulaMappingDefine();
                    formulaMappingDefine.setTargetFormKey(parsedExpression.getSource().getFormKey());
                    formulaMappingDefine.setTargetKey(parsedExpression.getSource().getId());
                    formulaMappingDefine.setTargetCode(parsedExpression.getSource().getCode());
                    formulaMappingDefine.setTargetExpression(parsedExpression.getFormula(queryContext, jqStyleShowInfo));
                    IParsedExpression source = (IParsedExpression)sourceFormula.get(parsedExpression.getSource().getCode());
                    formulaMappingDefine.setSourceKey(source.getSource().getId());
                    formulaMappingDefine.setSourceCode(source.getSource().getCode());
                    formulaMappingDefine.setSourceExpression(source.getFormula(queryContext1, jqStyleShowInfo));
                    formulaMappingDefines.add(formulaMappingDefine);
                }
            }
            copyErrorDesFmlMappingProvider.setFormulaMapping(formulaMappingDefines);
            checkDesCopyParam.setTargetFormSchemeKey(context.getFormSchemeKey());
            checkDesCopyParam.setTargetFormulaSchemeKey(context.getFormulaSchemeKey());
            checkDesCopyParam.setDimMappingProvider(copyErrorDesDimMappingProvider);
            checkDesCopyParam.setFmlMappingProvider(copyErrorDesFmlMappingProvider);
            checkDesCopyParam.setUpdateUserTime(true);
            this.copyDesService.copy(checkDesCopyParam);
            asyncTaskMonitor.progressAndMessage(process * (double)(1 + i), "\u5b8c\u6210\u8fdb\u5ea6\uff1a" + process * (double)(1 + i) * 100.0 + "%");
        }
        asyncTaskMonitor.progressAndMessage(1.0, "\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish("copyPeriodDesSuccess", (Object)"\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        }
    }

    private ExecutorContext createExecutorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionController, this.entityViewController, formSchemeKey, true);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    @Override
    public void copyErrorDesToOtherCurrency(JtableContext context, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        ((DimensionValue)context.getDimensionSet().get("MD_CURRENCY")).setValue("");
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        EntityViewData masterEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
        String MD_ORG = masterEntity.getDimensionName();
        CheckDesCopyParam checkDesCopyParam = new CheckDesCopyParam();
        Map dimensionSet = context.getDimensionSet();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_READ);
        accessFormParam.setFormKeys(new ArrayList());
        accessFormParam.setTaskKey(context.getTaskKey());
        accessFormParam.setFormSchemeKey(context.getFormSchemeKey());
        accessFormParam.setCollectionMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)context.getFormSchemeKey()));
        DimensionAccessFormInfo dimensionAccessFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List acessFormInfos = dimensionAccessFormInfo.getAccessForms();
        if (acessFormInfos.size() == 0) {
            logger.error("\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e\uff1a\u62a5\u8868\u65e0\u6743\u9650");
            asyncTaskMonitor.finish("copyCurrencyDesSuccess", (Object)"\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
            return;
        }
        double process = 1 / acessFormInfos.size();
        for (int i = 0; i < acessFormInfos.size(); ++i) {
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"");
                LogHelper.info((String)"\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
            DimensionAccessFormInfo.AccessFormInfo accessFormInfo = (DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(i);
            Map dimensionValue = accessFormInfo.getDimensions();
            List forms = accessFormInfo.getFormKeys();
            CopyErrorDesDimMappingProvider copyErrorDesDimMappingProvider = new CopyErrorDesDimMappingProvider();
            copyErrorDesDimMappingProvider.setMDCODE(MD_ORG);
            HashMap<String, DimensionValue> sourceDimensionSet = new HashMap<String, DimensionValue>();
            HashMap<String, DimensionValue> targetDimensionSet = new HashMap<String, DimensionValue>();
            for (String key : dimensionValue.keySet()) {
                DimensionValue dimension = new DimensionValue((DimensionValue)dimensionValue.get(key));
                sourceDimensionSet.put(key, dimension);
                DimensionValue dimension1 = new DimensionValue((DimensionValue)dimensionValue.get(key));
                targetDimensionSet.put(key, dimension1);
            }
            JtableContext sourceContext = new JtableContext(context);
            sourceContext.setDimensionSet(sourceDimensionSet);
            String[] dwList = ((DimensionValue)sourceDimensionSet.get(MD_ORG)).getValue().split(";");
            HashMap<String, String> curDwList = new HashMap<String, String>();
            for (String dw : dwList) {
                ((DimensionValue)sourceDimensionSet.get(MD_ORG)).setValue(dw);
                List<String> currencyInfo = this.currencyService.getCurrencyInfo(sourceContext, "PROVIDER_BASECURRENCY");
                if (currencyInfo == null || currencyInfo.isEmpty()) continue;
                String curr = currencyInfo.get(0);
                if (curDwList.containsKey(curr)) {
                    curDwList.put(curr, (String)curDwList.get(curr) + ";" + dw);
                    continue;
                }
                curDwList.put(curr, dw);
            }
            for (String curr : curDwList.keySet()) {
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled("stop_execute", (Object)"");
                    break;
                }
                if (curr.equals(((DimensionValue)targetDimensionSet.get("MD_CURRENCY")).getValue())) continue;
                ((DimensionValue)sourceDimensionSet.get("MD_CURRENCY")).setValue(curr);
                ((DimensionValue)sourceDimensionSet.get(MD_ORG)).setValue((String)curDwList.get(curr));
                copyErrorDesDimMappingProvider.setSourceDim(DimensionValueSetUtil.buildDimensionCollection(sourceDimensionSet, (String)context.getFormSchemeKey()));
                ((DimensionValue)targetDimensionSet.get(MD_ORG)).setValue((String)curDwList.get(curr));
                DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection(targetDimensionSet, (String)context.getFormSchemeKey());
                copyErrorDesDimMappingProvider.setTargetDim(dimCollection.getDimensionCombinations());
                CopyErrorDesFmlMappingProvider copyErrorDesFmlMappingProvider = new CopyErrorDesFmlMappingProvider();
                forms.add("00000000-0000-0000-0000-000000000000");
                copyErrorDesFmlMappingProvider.setSourceFmlScheme(context.getFormulaSchemeKey());
                ArrayList<FormulaMappingDefine> formulaMappingDefines = new ArrayList<FormulaMappingDefine>();
                FormulaShowInfo jqStyleShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
                ExecutorContext executorContext = this.createExecutorContext(context.getFormSchemeKey());
                QueryContext queryContext = new QueryContext(executorContext, null);
                List curCheckExpressions = this.formulaRunTimeController.getParsedExpressionByForms(context.getFormulaSchemeKey(), forms, DataEngineConsts.FormulaType.CHECK);
                for (IParsedExpression parsedExpression : curCheckExpressions) {
                    FormulaMappingDefine formulaMappingDefine = new FormulaMappingDefine();
                    formulaMappingDefine.setTargetFormKey(parsedExpression.getSource().getFormKey());
                    formulaMappingDefine.setTargetKey(parsedExpression.getSource().getId());
                    formulaMappingDefine.setTargetCode(parsedExpression.getSource().getCode());
                    formulaMappingDefine.setTargetExpression(parsedExpression.getFormula(queryContext, jqStyleShowInfo));
                    formulaMappingDefine.setSourceKey(parsedExpression.getSource().getId());
                    formulaMappingDefine.setSourceCode(parsedExpression.getSource().getCode());
                    formulaMappingDefine.setSourceExpression(parsedExpression.getFormula(queryContext, jqStyleShowInfo));
                    formulaMappingDefines.add(formulaMappingDefine);
                }
                copyErrorDesFmlMappingProvider.setFormulaMapping(formulaMappingDefines);
                checkDesCopyParam.setTargetFormSchemeKey(context.getFormSchemeKey());
                checkDesCopyParam.setTargetFormulaSchemeKey(context.getFormulaSchemeKey());
                checkDesCopyParam.setDimMappingProvider(copyErrorDesDimMappingProvider);
                checkDesCopyParam.setFmlMappingProvider(copyErrorDesFmlMappingProvider);
                checkDesCopyParam.setUpdateUserTime(true);
                this.copyDesService.copy(checkDesCopyParam);
            }
            asyncTaskMonitor.progressAndMessage(process * (double)(1 + i), "\u5b8c\u6210\u8fdb\u5ea6\uff1a" + process * (double)(1 + i) * 100.0 + "%");
        }
        asyncTaskMonitor.progressAndMessage(1.0, "\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish("copyCurrencyDesSuccess", (Object)"\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        }
    }
}

