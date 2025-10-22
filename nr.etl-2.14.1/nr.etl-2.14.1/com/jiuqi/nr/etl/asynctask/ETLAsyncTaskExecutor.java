/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.service.ICurrencyService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.etl.asynctask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.service.ICurrencyService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlReturnInfo;
import com.jiuqi.nr.etl.service.IEtlOrNrdlService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_ETL", groupTitle="ETL")
public class ETLAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(ETLAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        RealTimeTaskMonitor asyncTaskMonitor = null;
        try {
            IEtlOrNrdlService etlOrNrdlService = (IEtlOrNrdlService)BeanUtil.getBean(IEtlOrNrdlService.class);
            ICurrencyService currencyService = (ICurrencyService)BeanUtil.getBean(ICurrencyService.class);
            IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_ETL.getName(), jobContext);
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                Map<String, EtlReturnInfo> etlExecute;
                String retStr = "";
                ObjectMapper mapper = new ObjectMapper();
                EtlInfo etlInfo = (EtlInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                FormSchemeDefine formScheme = runtimeView.getFormScheme(etlInfo.getFormSchemeKey());
                EntityViewData unitViewData = jtableParamService.getEntity(formScheme.getDw());
                if (etlInfo.getDimensionSet().get("MD_CURRENCY") != null && (etlInfo.getDimensionSet().get("MD_CURRENCY").getValue().equals("PROVIDER_BASECURRENCY") || etlInfo.getDimensionSet().get("MD_CURRENCY").getValue().equals("PROVIDER_PBASECURRENCY"))) {
                    JtableContext jtableContext = new JtableContext();
                    HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
                    for (String key : etlInfo.getDimensionSet().keySet()) {
                        if (key.equals(unitViewData.getDimensionName())) {
                            String[] units = etlInfo.getDimensionSet().get(key).getValue().split(";");
                            DimensionValue dimensionValue = new DimensionValue();
                            dimensionValue.setName(key);
                            dimensionValue.setValue(units[0]);
                            dimensionValue.setType(etlInfo.getDimensionSet().get(key).getType());
                            dimensionValueMap.put(key, dimensionValue);
                            continue;
                        }
                        dimensionValueMap.put(key, etlInfo.getDimensionSet().get(key));
                    }
                    jtableContext.setDimensionSet(dimensionValueMap);
                    jtableContext.setTaskKey(etlInfo.getTaskKey());
                    jtableContext.setFormSchemeKey(etlInfo.getFormSchemeKey());
                    List currency = currencyService.getCurrencyInfo(jtableContext, etlInfo.getDimensionSet().get("MD_CURRENCY").getValue());
                    if (currency != null) {
                        etlInfo.getDimensionSet().get("MD_CURRENCY").setValue((String)currency.get(0));
                    }
                }
                if (null != (etlExecute = etlOrNrdlService.executeTask(etlInfo, (AsyncTaskMonitor)asyncTaskMonitor))) {
                    for (String key : etlExecute.keySet()) {
                        try {
                            retStr = mapper.writeValueAsString((Object)etlExecute.get(key));
                        }
                        catch (JsonProcessingException jsone) {
                            log.error(jsone.getMessage(), jsone);
                        }
                    }
                }
                asyncTaskMonitor.finish("etlComplete", (Object)retStr);
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error("etlError", (Throwable)nrCommonException);
            log.error(nrCommonException.getDatas()[0], nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error("etlError", (Throwable)e);
            log.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_ETL.getName();
    }
}

