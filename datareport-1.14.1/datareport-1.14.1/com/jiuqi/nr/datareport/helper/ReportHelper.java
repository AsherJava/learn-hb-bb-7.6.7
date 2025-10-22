/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.OfficeException
 *  com.jiuqi.bi.office.SpireInitializer
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.ReportTagDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.office.template.document.DocumentContext
 *  com.jiuqi.nvwa.office.template.document.DocumentTemplate
 */
package com.jiuqi.nr.datareport.helper;

import com.jiuqi.bi.office.OfficeException;
import com.jiuqi.bi.office.SpireInitializer;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.datareport.constant.ReportErrorEnum;
import com.jiuqi.nr.datareport.helper.BatchConditionMonitor;
import com.jiuqi.nr.datareport.helper.ReportUtil;
import com.jiuqi.nr.datareport.obj.DimensionCacheKey;
import com.jiuqi.nr.datareport.obj.NrDocumentParam;
import com.jiuqi.nr.datareport.obj.ReportEnv;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.office.template.document.DocumentContext;
import com.jiuqi.nvwa.office.template.document.DocumentTemplate;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ReportHelper {
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private static final Logger logger = LoggerFactory.getLogger(ReportHelper.class);

    public boolean filterCondition(String formSchemeKey, Map<String, DimensionValue> dimensionSet, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, String expression) throws JQException {
        if (StringUtils.isEmpty((String)expression)) {
            return true;
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        FormulaCallBack callback = new FormulaCallBack();
        Formula formula = new Formula();
        formula.setId(formSchemeKey);
        formula.setFormula(expression);
        formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
        callback.getFormulas().add(formula);
        IFormulaRunner runner = this.iDataAccessProvider.newFormulaRunner(callback);
        BatchConditionMonitor monitor = new BatchConditionMonitor(formSchemeKey);
        monitor.setDimensionValueMap(dimensionSet);
        try {
            runner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
            runner.run((IMonitor)monitor);
            Map<DimensionCacheKey, Set<String>> result = monitor.getConditionResultList();
            return result.isEmpty();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ReportErrorEnum.REPORT_EXCEPTION_203, (Throwable)e);
        }
    }

    public DocumentTemplate parseReport(InputStream is, ReportEnv reportEnv, String rptKey, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) throws JQException {
        SpireInitializer.initialize();
        DocumentContext documentContext = this.getDocumentContext(reportEnv, rptKey, executorContext);
        DocumentTemplate dt = new DocumentTemplate(documentContext);
        try {
            dt.loadDocument(is);
            dt.processDocument();
            return dt;
        }
        catch (OfficeException e) {
            throw new JQException((ErrorEnum)ReportErrorEnum.REPORT_EXCEPTION_204, (Throwable)e);
        }
    }

    private DocumentContext getDocumentContext(ReportEnv reportEnv, String rptKey, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) {
        DocumentContext documentContext = this.initDocumentContext(reportEnv, rptKey);
        NrDocumentParam nrDocumentParam = this.getNrDocumentParam(reportEnv, rptKey, executorContext);
        documentContext.setProperty("nrDocumentParam", (Object)nrDocumentParam);
        this.setDimEnv(documentContext, reportEnv.getDimensionSet());
        return documentContext;
    }

    private DocumentContext initDocumentContext(ReportEnv reportEnv, String rptKey) {
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        String period = reportEnv.getDimensionSet().get(periodDimensionName).getValue();
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(reportEnv.getFormSchemeKey());
        String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        String unit = reportEnv.getDimensionSet().get(dwDimName).getValue();
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        String periodTitle = periodProvider.getPeriodTitle(period);
        String unitTitle = this.getUnitTitle(formScheme, this.period2Date(periodProvider, period), unit);
        return new DocumentContext(rptKey, period, periodTitle, unit, unitTitle);
    }

    private NrDocumentParam getNrDocumentParam(ReportEnv reportEnv, String rptKey, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) {
        List tagList = this.runtimeViewController.queryAllTagsByRptKey(rptKey);
        HashMap<String, ReportTagDefine> tagMap = new HashMap<String, ReportTagDefine>();
        for (ReportTagDefine tag : tagList) {
            tagMap.put(tag.getContent(), tag);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(reportEnv.getDimensionSet());
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            dimensionCombinationBuilder.setValue(dimensionValueSet.getName(i), dimensionValueSet.getValue(i));
        }
        DimensionCombination dimensionCombination = dimensionCombinationBuilder.getCombination();
        Map<String, List<Object>> param1 = this.getQuickReportParam(reportEnv.getDimensionSet());
        Map<String, String[]> param2 = this.getChartParam(reportEnv.getDimensionSet());
        NrDocumentParam nrDocumentParam = new NrDocumentParam();
        nrDocumentParam.setTagMap(tagMap);
        nrDocumentParam.setReportEnv(reportEnv);
        nrDocumentParam.setDimensionCombination(dimensionCombination);
        nrDocumentParam.setExecutorContext(executorContext);
        nrDocumentParam.setQuickReportParam(param1);
        nrDocumentParam.setChartParam(param2);
        return nrDocumentParam;
    }

    private String getUnitTitle(FormSchemeDefine formScheme, Date entityQueryDate, String unit) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(this.entityViewRunTimeController.buildEntityView(ReportUtil.getContextMainDimId(formScheme.getDw())));
        query.setQueryVersionDate(entityQueryDate);
        query.setAuthorityOperations(AuthorityType.Read);
        try {
            IEntityTable entityTable = query.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
            IEntityRow entityRow = entityTable.quickFindByEntityKey(unit);
            if (entityRow != null) {
                return entityRow.getTitle();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private void setDimEnv(DocumentContext documentContext, Map<String, DimensionValue> dimensionSet) {
        if (!CollectionUtils.isEmpty(dimensionSet)) {
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                documentContext.setProperty("P_" + entry.getKey(), (Object)entry.getValue().getValue());
            }
        }
    }

    private Map<String, List<Object>> getQuickReportParam(Map<String, DimensionValue> dimensionSet) {
        HashMap<String, List<Object>> result = new HashMap<String, List<Object>>();
        if (!CollectionUtils.isEmpty(dimensionSet)) {
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                ArrayList<String> value = new ArrayList<String>();
                value.add(entry.getValue().getValue());
                result.put("P_" + entry.getKey(), value);
            }
        }
        return result;
    }

    private Map<String, String[]> getChartParam(Map<String, DimensionValue> dimensionSet) {
        HashMap<String, String[]> result = new HashMap<String, String[]>();
        if (!CollectionUtils.isEmpty(dimensionSet)) {
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                String[] value = new String[]{entry.getValue().getValue()};
                result.put("P_" + entry.getKey(), value);
            }
        }
        return result;
    }

    public Date period2Date(IPeriodProvider periodProvider, String period) {
        Date versionDate = null;
        try {
            versionDate = periodProvider.getPeriodDateRegion(period)[1];
        }
        catch (ParseException e) {
            logger.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
        }
        return versionDate;
    }
}

