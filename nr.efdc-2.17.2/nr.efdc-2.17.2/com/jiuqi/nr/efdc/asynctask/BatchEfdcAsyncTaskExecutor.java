/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.paramInfo.FormGroupData
 *  com.jiuqi.nr.dataentry.provider.DimensionValueProvider
 *  com.jiuqi.nr.dataentry.service.ICurrencyService
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.MouldDefineImpl
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.efdc.asynctask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.service.ICurrencyService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.MouldDefineImpl;
import com.jiuqi.nr.efdc.monitor.EfdcMonitor;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.pojo.EfdcReturnInfo;
import com.jiuqi.nr.efdc.service.IEFDCService;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@RealTimeJob(group="ASYNCTASK_BATCHEFDC", groupTitle="\u6279\u91cfEFDC\u53d6\u6570")
public class BatchEfdcAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(BatchEfdcAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        block34: {
            RealTimeTaskMonitor asyncTaskMonitor = null;
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            log.info("\u5f00\u59cb\u6279\u91cf\u6267\u884cEFDC {}", (Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))).toString());
            try {
                IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
                ICurrencyService currencyService = (ICurrencyService)BeanUtil.getBean(ICurrencyService.class);
                IDataEntryParamService dataEntryParamService = (IDataEntryParamService)BeanUtil.getBean(IDataEntryParamService.class);
                AsyncTaskManager asyncTaskManager = (AsyncTaskManager)BeanUtil.getBean(AsyncTaskManager.class);
                IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                RunTimeAuthViewController runTimeAuthViewController = (RunTimeAuthViewController)BeanUtil.getBean(RunTimeAuthViewController.class);
                DimensionValueProvider dimensionValueProvider = (DimensionValueProvider)BeanUtil.getBean(DimensionValueProvider.class);
                IEFDCService efdcService = (IEFDCService)BeanUtil.getBean(IEFDCService.class);
                asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHEFDC.getName(), jobContext);
                if (!Objects.nonNull(params) || !Objects.nonNull(params.get("NR_ARGS"))) break block34;
                String retStr = "";
                ObjectMapper mapper = new ObjectMapper();
                EfdcInfo efdcInfo = (EfdcInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                Map<Object, Object> mergeResult = new HashMap();
                HashMap<String, EfdcReturnInfo> warnList = new HashMap<String, EfdcReturnInfo>();
                String formKey = efdcInfo.getFormKey();
                String periodRange = efdcInfo.getPeriodRange();
                String taskKey = efdcInfo.getTaskKey();
                String formSchemeKey = efdcInfo.getFormSchemeKey();
                if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)periodRange)) {
                    log.info("\u6279\u91cf\u6267\u884cEFDC,\u65f6\u671f\u8303\u56f4 {}", (Object)periodRange);
                    String[] startEnd = periodRange.split("-");
                    if (startEnd.length == 2) {
                        TaskDefine taskDefine = runTimeAuthViewController.queryTaskDefine(taskKey);
                        List periodList = periodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), startEnd[0], startEnd[1]);
                        Map<String, DimensionValue> dimensionSet = efdcInfo.getDimensionSet();
                        DimensionValue dimensionValue = dimensionSet.get("DATATIME");
                        if (dimensionValue == null) {
                            dimensionValue = new DimensionValue();
                            dimensionValue.setName("DATATIME");
                            dimensionSet.put("DATATIME", dimensionValue);
                        }
                        dimensionValue.setValue(String.join((CharSequence)";", periodList));
                        log.info("\u6279\u91cf\u6267\u884cEFDC,\u65f6\u671f\u8303\u56f4 {}", (Object)dimensionValue.getValue());
                    }
                }
                List linkDefines = runtimeView.querySchemePeriodLinkByTask(taskKey);
                HashMap<String, String> periodMap = new HashMap<String, String>();
                for (SchemePeriodLinkDefine linkDefine : linkDefines) {
                    periodMap.put(linkDefine.getPeriodKey(), linkDefine.getSchemeKey());
                }
                DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
                DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(efdcInfo.getDimensionSet(), efdcInfo.getFormSchemeKey());
                ArrayList allSplitDimensionValueList = new ArrayList();
                for (DimensionCombination dimensionCombination : dimensionCollection.getDimensionCombinations()) {
                    HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
                    for (String string : efdcInfo.getDimensionSet().keySet()) {
                        DimensionValue dimensionValue = new DimensionValue(efdcInfo.getDimensionSet().get(string));
                        dimensionValue.setValue(dimensionCombination.getValue(string).toString());
                        dimensionValueMap.put(string, dimensionValue);
                    }
                    allSplitDimensionValueList.add(dimensionValueMap);
                }
                double weight = 1.0 / ((double)allSplitDimensionValueList.size() + 1.0);
                EfdcMonitor efdcMonitor = new EfdcMonitor((AsyncTaskMonitor)asyncTaskMonitor, weight, asyncTaskManager);
                if (allSplitDimensionValueList.size() == 0) {
                    EfdcReturnInfo res = new EfdcReturnInfo();
                    res.setMessage("\u6240\u9009\u5355\u4f4d\u4e0e\u5176\u4ed6\u7ef4\u5ea6\u65e0\u5173\u8054\u5173\u7cfb");
                    try {
                        retStr = mapper.writeValueAsString((Object)res);
                    }
                    catch (JsonProcessingException jsonProcessingException) {
                        log.error(jsonProcessingException.getMessage(), jsonProcessingException);
                    }
                    asyncTaskMonitor.error("batchEfdcError", null, retStr);
                    return;
                }
                for (String string : efdcInfo.getDimensionSet().keySet()) {
                    if (!string.equals("ADJUST")) continue;
                    for (Map map : allSplitDimensionValueList) {
                        map.put("ADJUST", efdcInfo.getDimensionSet().get("ADJUST"));
                    }
                }
                List formDefines = null;
                if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formKey)) {
                    formDefines = runtimeView.queryFormsById(Arrays.stream(formKey.split(";")).collect(Collectors.toList()));
                }
                for (Map map : allSplitDimensionValueList) {
                    if (map.get("MD_CURRENCY") != null && (((DimensionValue)map.get("MD_CURRENCY")).getValue().equals("PROVIDER_BASECURRENCY") || ((DimensionValue)map.get("MD_CURRENCY")).getValue().equals("PROVIDER_PBASECURRENCY"))) {
                        JtableContext jtableContext = new JtableContext();
                        jtableContext.setDimensionSet(map);
                        jtableContext.setTaskKey(taskKey);
                        jtableContext.setFormSchemeKey(formSchemeKey);
                        List currency = currencyService.getCurrencyInfo(jtableContext, ((DimensionValue)map.get("MD_CURRENCY")).getValue());
                        if (currency != null && currency.size() > 0) {
                            ((DimensionValue)map.get("MD_CURRENCY")).setValue((String)currency.get(0));
                        }
                    }
                    if (com.jiuqi.bi.util.StringUtils.isEmpty((String)formKey)) {
                        JtableContext jtableContext = new JtableContext();
                        jtableContext.setDimensionSet(map);
                        jtableContext.setFormSchemeKey(efdcInfo.getFormSchemeKey());
                        jtableContext.setTaskKey(efdcInfo.getTaskKey());
                        List runtimeFormList = dataEntryParamService.getRuntimeFormList(jtableContext);
                        for (FormGroupData formGroup : runtimeFormList) {
                            if (formGroup.getReports().size() <= 0) continue;
                            for (FormData form : formGroup.getReports()) {
                                formKey = formKey + form.getKey() + ";";
                            }
                        }
                        formKey = formKey.substring(0, formKey.length() - 1);
                        efdcInfo.setFormKey(formKey);
                    }
                    efdcInfo.setDimensionSet(map);
                    DimensionValue dimensionValue = (DimensionValue)map.get("DATATIME");
                    if (dimensionValue == null) {
                        log.info("\u65f6\u671f\u4e3a\u7a7a\u8df3\u8fc7\u53d6\u6570");
                        efdcMonitor.progressAndMessage(1.0, "\u65f6\u671f\u4e3a\u7a7a\u8df3\u8fc7\u53d6\u6570");
                        continue;
                    }
                    String value = dimensionValue.getValue();
                    String periodFormSchemeKey = periodMap.getOrDefault(value, formSchemeKey);
                    efdcInfo.setFormSchemeKey(periodFormSchemeKey);
                    if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formKey) && formDefines != null) {
                        ArrayList<String> formKeys = new ArrayList<String>();
                        for (FormDefine formDefine : formDefines) {
                            FormDefine periodFormDefine = runtimeView.queryFormByCodeInScheme(periodFormSchemeKey, formDefine.getFormCode());
                            if (periodFormDefine == null) continue;
                            formKeys.add(periodFormDefine.getKey());
                        }
                        if (formKeys.isEmpty()) {
                            efdcMonitor.progressAndMessage(1.0, value + " \u8df3\u8fc7\u53d6\u6570");
                            continue;
                        }
                        efdcInfo.setFormKey(String.join((CharSequence)";", formKeys));
                    }
                    if (efdcInfo.isAppUsePayment()) {
                        JtableContext jtableContext = efdcInfo.getContext();
                        jtableContext.setDimensionSet(map);
                        FormulaSchemeDefine efdcFormula = efdcService.getEfdcFormula(jtableContext);
                        List data = new ArrayList();
                        if (efdcFormula != null && efdcFormula.getEfdcPeriodSettingDefineImpl() != null) {
                            EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl = efdcFormula.getEfdcPeriodSettingDefineImpl();
                            data = efdcPeriodSettingDefineImpl.getMouldDataDefine().getData();
                        }
                        ArrayList resData = new ArrayList(data);
                        Iterator iterator = resData.iterator();
                        while (iterator.hasNext()) {
                            MouldDefineImpl curr = (MouldDefineImpl)iterator.next();
                            if (!StringUtils.hasText(curr.getNewCode())) continue;
                            String[] spStr = curr.getNewCode().split(",");
                            if (spStr.length == 1) {
                                if (spStr[0].equals(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue())) continue;
                                iterator.remove();
                                continue;
                            }
                            if (spStr[0].equals(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue()) && spStr[1].equals(((DimensionValue)jtableContext.getDimensionSet().get("ADJUST")).getValue())) continue;
                            iterator.remove();
                        }
                        if (resData.size() > 0 && StringUtils.hasText(((MouldDefineImpl)resData.get(0)).getNewCode())) {
                            efdcInfo.setStartPay(((MouldDefineImpl)resData.get(0)).getStart());
                            efdcInfo.setEndPay(((MouldDefineImpl)resData.get(0)).getEnd());
                        }
                    }
                    Map<String, EfdcReturnInfo> efdcResult = efdcService.efdcService(efdcInfo, efdcMonitor, params);
                    efdcInfo.setStartPay("");
                    efdcInfo.setEndPay("");
                    if (null == efdcResult) continue;
                    mergeResult = this.getMergeResult(efdcResult, warnList);
                }
                if (mergeResult.size() > 0) {
                    try {
                        retStr = mapper.writeValueAsString(mergeResult);
                        asyncTaskMonitor.error("batchEfdcError", null, retStr);
                    }
                    catch (JsonProcessingException jsonProcessingException) {
                        log.error(jsonProcessingException.getMessage(), jsonProcessingException);
                    }
                } else {
                    asyncTaskMonitor.finish("batchEfdcComplete", (Object)retStr);
                }
            }
            catch (NrCommonException nrCommonException) {
                asyncTaskMonitor.error("batchEfdcError", (Throwable)nrCommonException);
                log.error(nrCommonException.getMessage(), nrCommonException);
            }
            catch (Exception e) {
                asyncTaskMonitor.error("batchEfdcError", (Throwable)e);
                log.error("\u6279\u91cfEFDC\u53d6\u6570\u5931\u8d25", e);
            }
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHEFDC.getName();
    }

    private Map<String, EfdcReturnInfo> getMergeResult(Map<String, EfdcReturnInfo> efdcResult, Map<String, EfdcReturnInfo> warnList) {
        for (String key : efdcResult.keySet()) {
            EfdcReturnInfo efdcReturnInfo = efdcResult.get(key);
            if (efdcReturnInfo.getStatus() == 2) {
                return efdcResult;
            }
            if (warnList.containsKey(key)) {
                warnList.get(key).getFormMessage().putAll(efdcResult.get(key).getFormMessage());
                continue;
            }
            warnList.putAll(efdcResult);
        }
        return warnList;
    }
}

