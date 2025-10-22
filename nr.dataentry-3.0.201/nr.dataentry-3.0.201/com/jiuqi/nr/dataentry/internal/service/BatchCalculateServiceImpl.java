/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.common.BatchSummaryConst
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.dataentry.monitor.LogicProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.common.BatchSummaryConst;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchCalculateServiceImpl
implements IBatchCalculateService {
    private static final Logger logger = LoggerFactory.getLogger(BatchCalculateServiceImpl.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private ICalculateService calculateService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;

    @Override
    public void batchCalculateForm(BatchCalculateInfo batchCalculateInfo) {
        this.batchCalculateForm(batchCalculateInfo, null);
    }

    @Override
    public void batchCalculateForm(BatchCalculateInfo batchCalculateInfo, AsyncTaskMonitor asyncTaskMonitor) {
        List formulaSchemes = FormulaUtil.getFormulaSchemeList((String)batchCalculateInfo.getFormSchemeKey(), (String)batchCalculateInfo.getFormulaSchemeKey());
        DefinitionAuthorityProvider definitionAuthorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
        formulaSchemes.stream().filter(formulaScheme -> definitionAuthorityProvider.canReadFormulaScheme(formulaScheme.getFormSchemeKey()));
        if (formulaSchemes.size() == 0) {
            asyncTaskMonitor.error("calculate_execute_exception", (Throwable)new Exception("\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\u6743\u9650"));
            return;
        }
        LogicProgressMonitor progressMonitor = null;
        if (StringUtils.isEmpty((String)batchCalculateInfo.getPeriodRegionInfo())) {
            if (asyncTaskMonitor != null && batchCalculateInfo.isChangeMonitorState()) {
                double coefficient = 0.9199999999999999 / (double)formulaSchemes.size();
                String calculating = "calculateing";
                progressMonitor = new LogicProgressMonitor(asyncTaskMonitor, 0.07, calculating, coefficient);
            }
            this.executeCalc(formulaSchemes, batchCalculateInfo, progressMonitor);
            if (progressMonitor != null && asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                progressMonitor.canceled("stop_execute", "");
                LogHelper.info((String)"\u6279\u91cf\u8fd0\u7b97", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
        } else {
            HashMap<String, Integer> formulaSchemeTitles = new HashMap<String, Integer>();
            for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemes) {
                formulaSchemeTitles.put(formulaSchemeDefine.getTitle(), 1);
            }
            List<SchemePeriodLinkDefine> schemePeriodList = this.getSchemePeriodMap(asyncTaskMonitor, batchCalculateInfo);
            if (asyncTaskMonitor != null && batchCalculateInfo.isChangeMonitorState()) {
                double coefficient = 0.9199999999999999 / (double)(schemePeriodList.size() * formulaSchemes.size());
                String calculating = "calculateing";
                progressMonitor = new LogicProgressMonitor(asyncTaskMonitor, 0.07, calculating, coefficient);
            }
            for (SchemePeriodLinkDefine define : schemePeriodList) {
                if (asyncTaskMonitor.isCancel()) {
                    progressMonitor.canceled("stop_execute", "");
                    LogHelper.info((String)"\u6279\u91cf\u8fd0\u7b97", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                    return;
                }
                String period = define.getPeriodKey();
                String formSchemeKey = define.getSchemeKey();
                ArrayList<FormulaSchemeDefine> curFormulaSchemes = new ArrayList<FormulaSchemeDefine>();
                List allFormulaSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
                for (FormulaSchemeDefine formulaSchemeDefine : allFormulaSchemes) {
                    if (!formulaSchemeTitles.containsKey(formulaSchemeDefine.getTitle())) continue;
                    curFormulaSchemes.add(formulaSchemeDefine);
                }
                HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
                Set keySet = batchCalculateInfo.getContext().getDimensionSet().keySet();
                for (String set : keySet) {
                    DimensionValue dimensionValue = new DimensionValue();
                    dimensionValue.setName(((DimensionValue)batchCalculateInfo.getContext().getDimensionSet().get(set)).getName());
                    dimensionValue.setType(((DimensionValue)batchCalculateInfo.getContext().getDimensionSet().get(set)).getType());
                    dimensionValue.setValue(((DimensionValue)batchCalculateInfo.getContext().getDimensionSet().get(set)).getValue());
                    dimensionValueMap.put(set, dimensionValue);
                }
                ((DimensionValue)dimensionValueMap.get("DATATIME")).setValue(period);
                batchCalculateInfo.getContext().setDimensionSet(dimensionValueMap);
                batchCalculateInfo.setDimensionSet(dimensionValueMap);
                batchCalculateInfo.setFormSchemeKey(formSchemeKey);
                batchCalculateInfo.getContext().setFormSchemeKey(formSchemeKey);
                this.executeCalc(curFormulaSchemes, batchCalculateInfo, progressMonitor);
            }
        }
        if (progressMonitor != null && asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
            progressMonitor.canceled("stop_execute", "");
            LogHelper.info((String)"\u6279\u91cf\u8fd0\u7b97", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        if (asyncTaskMonitor != null && batchCalculateInfo.isChangeMonitorState()) {
            String batchCalculate = "batch_calculate_success_info";
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish(batchCalculate, (Object)(batchCalculate + "\u3002"));
            }
        }
    }

    private void executeCalc(List<FormulaSchemeDefine> formulaSchemes, BatchCalculateInfo batchCalculateInfo, LogicProgressMonitor progressMonitor) {
        CalculateParam calculateParam = new CalculateParam();
        Map<String, List<String>> formulas = batchCalculateInfo.getFormulas();
        if (formulas.isEmpty()) {
            calculateParam.setMode(Mode.FORM);
            calculateParam.setRangeKeys(new ArrayList());
        } else {
            Iterator<Map.Entry<String, List<String>>> iterator = formulas.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                if (entry.getValue() == null || entry.getValue().isEmpty()) {
                    calculateParam.setMode(Mode.FORM);
                    calculateParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                } else {
                    calculateParam.setMode(Mode.FORMULA);
                    ArrayList<String> formulaKeys = new ArrayList<String>();
                    for (List<String> value : formulas.values()) {
                        formulaKeys.addAll(value);
                    }
                    calculateParam.setRangeKeys(formulaKeys);
                }
            }
        }
        calculateParam.setVariableMap(batchCalculateInfo.getVariableMap());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(batchCalculateInfo.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(batchCalculateInfo.getFormulaSchemeKey());
        jtableContext.setTaskKey(batchCalculateInfo.getTaskKey());
        jtableContext.setVariableMap(batchCalculateInfo.getVariableMap());
        jtableContext.setDimensionSet(batchCalculateInfo.getDimensionSet());
        DimensionValueSetUtil.fillDw((JtableContext)jtableContext, (String)batchCalculateInfo.getDwScope());
        calculateParam.setDimensionCollection(this.getDimensionCollection(jtableContext));
        calculateParam.setIgnoreItems(this.getIgnoreItems(jtableContext, batchCalculateInfo));
        for (FormulaSchemeDefine s : formulaSchemes) {
            if (progressMonitor != null && progressMonitor.isCancel()) {
                progressMonitor.canceled("stop_execute", "");
                return;
            }
            calculateParam.setFormulaSchemeKey(s.getKey());
            this.calculateService.batchCalculate(calculateParam, (IFmlMonitor)progressMonitor);
            if (progressMonitor == null) continue;
            progressMonitor.setProgressStart(progressMonitor.getProgressEnd());
        }
    }

    private DimensionCollection getDimensionCollection(JtableContext jtableContext) {
        if (BatchSummaryConst.isBatchSummaryEntry((Map)jtableContext.getVariableMap())) {
            DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
            for (Map.Entry entry : jtableContext.getDimensionSet().entrySet()) {
                builder.setValue((String)entry.getKey(), new Object[]{((DimensionValue)entry.getValue()).getValue()});
            }
            return builder.getCollection();
        }
        return this.dimensionBuildUtil.getDimensionCollection(DimensionUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), jtableContext.getFormSchemeKey(), (SpecificDimBuilder)DWLeafNodeBuilder.getInstance());
    }

    private Set<String> getIgnoreItems(JtableContext jtableContext, BatchCalculateInfo batchCalculateInfo) {
        HashSet<String> ignoreItems = new HashSet<String>();
        if (BatchSummaryConst.isBatchSummaryEntry((Map)jtableContext.getVariableMap())) {
            ignoreItems.add("ALL");
        } else if (batchCalculateInfo.isIgnoreWorkFlow()) {
            ignoreItems.add("upload");
        }
        return ignoreItems;
    }

    private List<SchemePeriodLinkDefine> getSchemePeriodMap(AsyncTaskMonitor asyncTaskMonitor, BatchCalculateInfo batchCalculateInfo) {
        ArrayList<SchemePeriodLinkDefine> periodList;
        block6: {
            String endDate;
            String startDate;
            periodList = new ArrayList<SchemePeriodLinkDefine>();
            String[] dateRange = batchCalculateInfo.getPeriodRegionInfo().split("-");
            if (dateRange.length == 1) {
                startDate = dateRange[0];
                endDate = dateRange[0];
            } else {
                startDate = dateRange[0];
                endDate = dateRange[1];
            }
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(batchCalculateInfo.getTaskKey());
            List periodListOfRegion = this.periodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), startDate, endDate);
            try {
                HashMap<String, SchemePeriodLinkDefine> schemePeriodLinkDefineMap = new HashMap<String, SchemePeriodLinkDefine>();
                List schemePeriodLinkDefineList = this.runTimeViewController.querySchemePeriodLinkByTask(batchCalculateInfo.getTaskKey());
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                    schemePeriodLinkDefineMap.put(schemePeriodLinkDefine.getPeriodKey(), schemePeriodLinkDefine);
                }
                for (String periodKey : periodListOfRegion) {
                    if (!schemePeriodLinkDefineMap.containsKey(periodKey)) continue;
                    periodList.add((SchemePeriodLinkDefine)schemePeriodLinkDefineMap.get(periodKey));
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u65b9\u6848\u65f6\u671f\u6620\u5c04\u5f02\u5e38\uff01\uff01\uff01");
                String schemePeriod = "scheme_period_mapping_warn_info";
                if (asyncTaskMonitor == null || !batchCalculateInfo.isChangeMonitorState()) break block6;
                asyncTaskMonitor.error(schemePeriod, (Throwable)e);
            }
        }
        return periodList;
    }
}

