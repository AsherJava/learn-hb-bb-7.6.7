/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.gather.exception.DataGatherException
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.formtype.common.EntityUnitNatureGetter
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.monitor.DataGatherMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.service.IBatchCaclSingleService;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHDATASUM", cancellable=true, rollback=false, groupTitle="\u8282\u70b9\u6c47\u603b", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchDataSumAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchDataSumAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        block26: {
            String errorInfo = "task_error_info";
            String cancelInfo = "task_cancel_info";
            String mapping = "scheme_period_mapping_warn_info";
            IBatchDataSumService batchDataSumService = (IBatchDataSumService)BeanUtil.getBean(IBatchDataSumService.class);
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            IPeriodEntityAdapter iPeriodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
            IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            DimensionProviderFactory dimensionProviderFactory = (DimensionProviderFactory)BeanUtil.getBean(DimensionProviderFactory.class);
            ITaskOptionController iTaskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName(), jobContext);
            try {
                if (!Objects.nonNull(params) || !Objects.nonNull(params.get("NR_ARGS"))) break block26;
                BatchDataSumInfo batchDataSumInfo = (BatchDataSumInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                String calAfterDataSumSettings = iTaskOptionController.getValue(batchDataSumInfo.getContext().getTaskKey(), "AUTOCALCULATE_AFTER_DATASUM");
                boolean calAfterDataSum = false;
                if (!StringUtils.isEmpty((String)calAfterDataSumSettings)) {
                    calAfterDataSum = calAfterDataSumSettings.equals("1");
                }
                if (StringUtils.isEmpty((String)batchDataSumInfo.getPeriodRegionInfo())) {
                    if (calAfterDataSum) {
                        DataGatherMonitor dataGatherMonitorDataSum = new DataGatherMonitor((AsyncTaskMonitor)asyncTaskMonitor, 0.8, 0.0);
                        batchDataSumService.batchDataSumForm(batchDataSumInfo, dataGatherMonitorDataSum, 1.0f);
                        AbstractBaseJobContext abstractBaseJobContext = (AbstractBaseJobContext)jobContext;
                        int realTimeTaskResult = abstractBaseJobContext.getResult();
                        if (realTimeTaskResult == 4 || realTimeTaskResult == -100) {
                            return;
                        }
                        DataGatherMonitor dataGatherMonitorCalc = new DataGatherMonitor((AsyncTaskMonitor)asyncTaskMonitor, 0.2, 0.8);
                        this.ExecuteCal(batchDataSumInfo, dataGatherMonitorCalc);
                        if (!asyncTaskMonitor.isFinish()) {
                            asyncTaskMonitor.finish("summary_success_info", null);
                        }
                    } else {
                        batchDataSumService.batchDataSumForm(batchDataSumInfo, (AsyncTaskMonitor)asyncTaskMonitor, 1.0f);
                    }
                    break block26;
                }
                HashMap<String, String> schemeKeyFormKeyMap = new HashMap<String, String>();
                ArrayList periodList = new ArrayList();
                HashMap<String, String> periodFormSchemeKeyMap = new HashMap<String, String>();
                String startDate = batchDataSumInfo.getPeriodRegionInfo().split("-")[0];
                String endDate = batchDataSumInfo.getPeriodRegionInfo().split("-")[1];
                TaskDefine taskDefine = iRunTimeViewController.queryTaskDefine(batchDataSumInfo.getContext().getTaskKey());
                List periodListOfRegion = iPeriodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), startDate, endDate);
                try {
                    List schemePeriodLinkDefineList = iRunTimeViewController.querySchemePeriodLinkByTask(batchDataSumInfo.getContext().getTaskKey());
                    for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                        if (!periodListOfRegion.contains(schemePeriodLinkDefine.getPeriodKey())) continue;
                        periodFormSchemeKeyMap.put(schemePeriodLinkDefine.getPeriodKey(), schemePeriodLinkDefine.getSchemeKey());
                    }
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u65b9\u6848\u65f6\u671f\u6620\u5c04\u5f02\u5e38\uff01\uff01\uff01");
                    asyncTaskMonitor.error(mapping, (Throwable)e);
                }
                Set periodSet = periodFormSchemeKeyMap.keySet();
                periodList.addAll(periodSet);
                ArrayList<String> formKeyCodeList = new ArrayList<String>();
                String formKeys = batchDataSumInfo.getFormKeys();
                if (StringUtils.isNotEmpty((String)formKeys)) {
                    String[] formKeyArray = formKeys.split(";");
                    List<String> formKeyList = Arrays.asList(formKeyArray);
                    List formDefineList = iRunTimeViewController.queryFormsById(formKeyList);
                    for (FormDefine formDefine : formDefineList) {
                        formKeyCodeList.add(formDefine.getFormCode());
                    }
                }
                Set keySet = batchDataSumInfo.getContext().getDimensionSet().keySet();
                DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
                String dwDimensionName = "";
                if (taskDefine != null) {
                    dwDimensionName = entityMetaService.getDimensionName(taskDefine.getDw());
                }
                for (String set : keySet) {
                    if (((DimensionValue)batchDataSumInfo.getContext().getDimensionSet().get(set)).getName().equals("DATATIME")) continue;
                    if (((DimensionValue)batchDataSumInfo.getContext().getDimensionSet().get(set)).getName().equals(dwDimensionName)) {
                        DimensionProviderData providerData = new DimensionProviderData();
                        providerData.setDataSchemeKey(taskDefine.getDataScheme());
                        ArrayList<String> dwList = new ArrayList<String>();
                        dwList.add(((DimensionValue)batchDataSumInfo.getContext().getDimensionSet().get(set)).getValue());
                        providerData.setChoosedValues(dwList);
                        VariableDimensionValueProvider dimensionProvider = dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                        builder.addVariableDW(dwDimensionName, taskDefine.getDw(), dimensionProvider);
                        continue;
                    }
                    builder.setEntityValue(((DimensionValue)batchDataSumInfo.getContext().getDimensionSet().get(set)).getName(), "", new Object[]{((DimensionValue)batchDataSumInfo.getContext().getDimensionSet().get(set)).getValue()});
                }
                builder.setEntityValue("DATATIME", taskDefine.getDateTime(), (Object[])periodList.toArray(new String[0]));
                DimensionCollection collection = builder.getCollection();
                Iterator iterator = collection.iterator();
                int i = 0;
                ArrayList<String> exePeriodList = new ArrayList<String>();
                while (iterator.hasNext()) {
                    DimensionValueSet dimensionValueSet = (DimensionValueSet)iterator.next();
                    String formSchemeKey = (String)periodFormSchemeKeyMap.get((String)dimensionValueSet.getValue("DATATIME"));
                    String formKeysInfo = "";
                    if (StringUtils.isEmpty((String)((String)schemeKeyFormKeyMap.get(formSchemeKey))) && formKeyCodeList != null && formKeyCodeList.size() > 0) {
                        List formDefineList = iRunTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                        for (FormDefine formDefine : formDefineList) {
                            if (!formKeyCodeList.contains(formDefine.getFormCode())) continue;
                            formKeysInfo = formKeysInfo + formDefine.getKey() + ";";
                        }
                    } else {
                        formKeysInfo = schemeKeyFormKeyMap.get(formSchemeKey) != null ? (String)schemeKeyFormKeyMap.get(formSchemeKey) : "";
                    }
                    schemeKeyFormKeyMap.put(formSchemeKey, formKeysInfo);
                    batchDataSumInfo.setFormKeys(formKeysInfo);
                    HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
                    for (int s = 0; s < dimensionValueSet.size(); ++s) {
                        DimensionValue dimensionValue = new DimensionValue();
                        dimensionValue.setName(dimensionValueSet.getName(s));
                        dimensionValue.setValue((String)dimensionValueSet.getValue(s));
                        dimensionSet.put(dimensionValueSet.getName(s), dimensionValue);
                    }
                    batchDataSumInfo.getContext().setDimensionSet(dimensionSet);
                    float progressPer = (float)(i + 1) * 1.0f / (float)periodList.size();
                    DataEntryAsyncProgressMonitor asyncProgressMonitor = new DataEntryAsyncProgressMonitor((AsyncTaskMonitor)asyncTaskMonitor, progressPer, 0.0);
                    batchDataSumService.batchDataSumForm(batchDataSumInfo, asyncProgressMonitor, progressPer);
                    if (asyncTaskMonitor.isCancel()) {
                        if (exePeriodList.size() > 0) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", (Object)"datasumPeriodCancel");
                            JSONObject param = new JSONObject();
                            param.put("num", exePeriodList.size());
                            jsonObject.put("param", (Object)param);
                            asyncTaskMonitor.canceled(jsonObject.toString(), (Object)"");
                        }
                        LogHelper.info((String)"\u6279\u91cf\u6c47\u603b", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                        return;
                    }
                    ++i;
                    if (calAfterDataSum) {
                        this.ExecuteCal(batchDataSumInfo);
                    }
                    exePeriodList.add(dimensionValueSet.getValue("DATATIME").toString());
                }
                if (!asyncTaskMonitor.isFinish()) {
                    asyncTaskMonitor.finish("summary_success_info", null);
                }
            }
            catch (NrCommonException nrCommonException) {
                asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
            }
            catch (Exception e) {
                asyncTaskMonitor.error(errorInfo, (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    private void ExecuteCal(BatchDataSumInfo batchDataSumInfo, AsyncTaskMonitor asyncTaskMonitor) {
        IBatchCaclSingleService iBatchCaclSingleService = null;
        try {
            iBatchCaclSingleService = (IBatchCaclSingleService)BeanUtil.getBean(IBatchCaclSingleService.class);
        }
        catch (Exception e) {
            logger.info("\u4f7f\u7528\u9ed8\u8ba4\u53c2\u6570\u516c\u5f0f\u65b9\u6848");
        }
        IBatchCalculateService batchCalculateService = (IBatchCalculateService)BeanUtil.getBean(IBatchCalculateService.class);
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        JtableContext jtableContext = batchDataSumInfo.getContext();
        if (batchDataSumInfo.isRecursive()) {
            IEntityTable entityTable;
            ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, jtableContext.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            String periodCode = String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
            FormSchemeDefine formScheme = null;
            try {
                formScheme = iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
            }
            String entityId = formScheme.getDw();
            EntityViewDefine entityViewDefine = iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
            try {
                entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            EntityViewData masterEntity = jtableParamService.getDwEntity(formScheme.getKey());
            String MD_ORG = masterEntity.getDimensionName();
            List iEntityRowList = entityTable.getAllChildRows(String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()));
            Iterator it = iEntityRowList.iterator();
            StringBuffer mergeDimension = new StringBuffer();
            mergeDimension.append(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()).append(";");
            LinkedHashSet<String> dimensionSet = new LinkedHashSet<String>();
            if (batchDataSumInfo.isDifference()) {
                dimensionSet.clear();
                IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IFormTypeApplyService.class);
                EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(masterEntity.getKey());
                while (it.hasNext()) {
                    IEntityRow iEntityRow = (IEntityRow)it.next();
                    if (!this.isMinus(entityFormGather, iEntityRow)) continue;
                    dimensionSet.add(iEntityRow.getEntityKeyData());
                }
            } else {
                while (it.hasNext()) {
                    IEntityRow iEntityRow = (IEntityRow)it.next();
                    if (entityTable.getChildRows(iEntityRow.getEntityKeyData()).size() <= 0) continue;
                    dimensionSet.add(iEntityRow.getEntityKeyData());
                }
            }
            for (String dimensionString : dimensionSet) {
                mergeDimension.append(dimensionString + ";");
            }
            if (!mergeDimension.toString().equals("")) {
                mergeDimension.deleteCharAt(mergeDimension.length() - 1);
                ((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).setValue(mergeDimension.toString());
            }
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
            batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
            if (iBatchCaclSingleService == null || StringUtils.isEmpty((String)iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()))) {
                batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
            } else {
                batchCalculateInfo.setFormulaSchemeKey(iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()));
            }
            batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
            batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
            batchCalculateService.batchCalculateForm(batchCalculateInfo, asyncTaskMonitor);
        } else {
            FormSchemeDefine formScheme = null;
            try {
                formScheme = iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
            }
            EntityViewData masterEntity = jtableParamService.getDwEntity(formScheme.getKey());
            String MD_ORG = masterEntity.getDimensionName();
            if (batchDataSumInfo.isDifference()) {
                IEntityTable entityTable;
                ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, jtableContext.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                String periodCode = String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
                EntityViewDefine entityViewDefine = iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
                try {
                    entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, jtableContext.getFormSchemeKey());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                List iEntityRowList = entityTable.getChildRows(String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()));
                IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IFormTypeApplyService.class);
                EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(masterEntity.getKey());
                Iterator it = iEntityRowList.iterator();
                LinkedHashSet<String> dimensionSet = new LinkedHashSet<String>();
                while (it.hasNext()) {
                    IEntityRow iEntityRow = (IEntityRow)it.next();
                    if (!this.isMinus(entityFormGather, iEntityRow)) continue;
                    dimensionSet.add(iEntityRow.getEntityKeyData());
                }
                StringBuffer mergeDimension = new StringBuffer();
                for (String dimensionString : dimensionSet) {
                    mergeDimension.append(dimensionString + ";");
                }
                if (!mergeDimension.toString().equals("")) {
                    mergeDimension.deleteCharAt(mergeDimension.length() - 1);
                    ((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).setValue(mergeDimension.toString());
                }
            }
            if (((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue().split(";").length == 1 && batchDataSumInfo.getFormKeys().split(";").length == 1 && !batchDataSumInfo.getFormKeys().equals("")) {
                IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
                jtableDataEngineService.calculateByCondition(jtableContext, batchDataSumInfo.getFormKeys());
            } else {
                BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
                batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                if (iBatchCaclSingleService == null || StringUtils.isEmpty((String)iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()))) {
                    batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                } else {
                    batchCalculateInfo.setFormulaSchemeKey(iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()));
                }
                batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
                batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
                batchCalculateService.batchCalculateForm(batchCalculateInfo, asyncTaskMonitor);
            }
        }
    }

    private void ExecuteCal(BatchDataSumInfo batchDataSumInfo) {
        IBatchCaclSingleService iBatchCaclSingleService = null;
        try {
            iBatchCaclSingleService = (IBatchCaclSingleService)BeanUtil.getBean(IBatchCaclSingleService.class);
        }
        catch (Exception e) {
            logger.info("\u4f7f\u7528\u9ed8\u8ba4\u53c2\u6570\u516c\u5f0f\u65b9\u6848");
        }
        JtableContext jtableContext = batchDataSumInfo.getContext();
        IBatchCalculateService batchCalculateService = (IBatchCalculateService)BeanUtil.getBean(IBatchCalculateService.class);
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        if (batchDataSumInfo.isRecursive()) {
            IEntityTable entityTable;
            ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, jtableContext.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            String periodCode = String.valueOf(jtableContext.getDimensionSet().get("DATATIME"));
            FormSchemeDefine formScheme = null;
            try {
                formScheme = iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
            }
            String entityId = formScheme.getDw();
            EntityViewDefine entityViewDefine = iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
            try {
                entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            EntityViewData masterEntity = jtableParamService.getDwEntity(formScheme.getKey());
            String MD_ORG = masterEntity.getDimensionName();
            List iEntityRowList = entityTable.getAllChildRows(entityId);
            Iterator it = iEntityRowList.iterator();
            StringBuffer mergeDimension = new StringBuffer();
            mergeDimension.append(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()).append(";");
            LinkedHashSet<String> dimensionSet = new LinkedHashSet<String>();
            if (batchDataSumInfo.isDifference()) {
                dimensionSet.clear();
                IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IFormTypeApplyService.class);
                EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(masterEntity.getKey());
                while (it.hasNext()) {
                    IEntityRow iEntityRow = (IEntityRow)it.next();
                    if (!this.isMinus(entityFormGather, iEntityRow)) continue;
                    dimensionSet.add(iEntityRow.getEntityKeyData());
                }
            } else {
                while (it.hasNext()) {
                    IEntityRow iEntityRow = (IEntityRow)it.next();
                    if (entityTable.getChildRows(iEntityRow.getEntityKeyData()).size() <= 0) continue;
                    dimensionSet.add(iEntityRow.getEntityKeyData());
                }
            }
            for (String dimensionString : dimensionSet) {
                mergeDimension.append(dimensionString + ";");
            }
            if (!mergeDimension.toString().equals("")) {
                mergeDimension.deleteCharAt(mergeDimension.length() - 1);
                ((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).setValue(mergeDimension.toString());
            }
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
            batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
            if (iBatchCaclSingleService == null || StringUtils.isEmpty((String)iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()))) {
                batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
            } else {
                batchCalculateInfo.setFormulaSchemeKey(iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()));
            }
            batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
            batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
            batchCalculateService.batchCalculateForm(batchCalculateInfo, null);
        } else {
            FormSchemeDefine formScheme = null;
            try {
                formScheme = iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
            }
            EntityViewData masterEntity = jtableParamService.getDwEntity(formScheme.getKey());
            String MD_ORG = masterEntity.getDimensionName();
            if (batchDataSumInfo.isDifference()) {
                IEntityTable entityTable;
                ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, jtableContext.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                String periodCode = String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
                EntityViewDefine entityViewDefine = iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
                try {
                    entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, jtableContext.getFormSchemeKey());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                List iEntityRowList = entityTable.getChildRows(String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()));
                IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IFormTypeApplyService.class);
                EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(masterEntity.getKey());
                Iterator it = iEntityRowList.iterator();
                LinkedHashSet<String> dimensionSet = new LinkedHashSet<String>();
                while (it.hasNext()) {
                    IEntityRow iEntityRow = (IEntityRow)it.next();
                    if (!this.isMinus(entityFormGather, iEntityRow)) continue;
                    dimensionSet.add(iEntityRow.getEntityKeyData());
                }
                StringBuffer mergeDimension = new StringBuffer();
                for (String dimensionString : dimensionSet) {
                    mergeDimension.append(dimensionString + ";");
                }
                if (!mergeDimension.toString().equals("")) {
                    mergeDimension.deleteCharAt(mergeDimension.length() - 1);
                    ((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).setValue(mergeDimension.toString());
                }
            }
            if (((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue().split(";").length == 1 && batchDataSumInfo.getFormKeys().split(";").length == 1) {
                IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
                jtableDataEngineService.calculateByCondition(jtableContext, batchDataSumInfo.getFormKeys());
            } else {
                BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
                batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                if (iBatchCaclSingleService == null || StringUtils.isEmpty((String)iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()))) {
                    batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                } else {
                    batchCalculateInfo.setFormulaSchemeKey(iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()));
                }
                batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
                batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
                batchCalculateService.batchCalculateForm(batchCalculateInfo, null);
            }
        }
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String periodCode, String formSchemeKey) throws Exception {
        DataEntityFullService dataEntityFullService = (DataEntityFullService)BeanUtil.getBean(DataEntityFullService.class);
        IEntityDataService entityDataService = (IEntityDataService)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(unitView);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        entityQuery.setMasterKeys(masterKeys);
        executorContext.setVarDimensionValueSet(masterKeys);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        executorContext.setPeriodView(formScheme.getDateTime());
        ExecutorContext executorContextInfo = new ExecutorContext(executorContext.getRuntimeController());
        executorContext.setDefaultGroupName(executorContext.getDefaultGroupName());
        executorContext.setJQReportModel(executorContext.isJQReportModel());
        executorContext.setVarDimensionValueSet(executorContext.getVarDimensionValueSet());
        executorContext.setEnv(executorContext.getEnv());
        IEntityTable entityTable = dataEntityFullService.executeEntityReader(entityQuery, executorContextInfo, unitView, formSchemeKey).getEntityTable();
        return entityTable;
    }

    private boolean isMinus(EntityUnitNatureGetter gather, IEntityRow row) {
        UnitNature unitNature = gather.getUnitNature(row);
        if (null != unitNature) {
            return unitNature.equals((Object)UnitNature.JTCEB);
        }
        return false;
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName();
    }
}

