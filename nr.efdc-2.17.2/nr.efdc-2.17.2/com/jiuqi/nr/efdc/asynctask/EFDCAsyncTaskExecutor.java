/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.dataentry.service.ICurrencyService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.efdc.asynctask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.dataentry.service.ICurrencyService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.pojo.EfdcReturnInfo;
import com.jiuqi.nr.efdc.service.IEFDCService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@RealTimeJob(group="ASYNCTASK_EFDC", groupTitle="EFDC\u53d6\u6570")
public class EFDCAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(EFDCAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        RealTimeTaskMonitor asyncTaskMonitor = null;
        try {
            ICurrencyService currencyService = (ICurrencyService)BeanUtil.getBean(ICurrencyService.class);
            IEFDCService efdcService = (IEFDCService)BeanUtil.getBean(IEFDCService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_ETL.getName(), jobContext);
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                Map<String, EfdcReturnInfo> efdcResult;
                String retStr = "";
                ObjectMapper mapper = new ObjectMapper();
                EfdcInfo efdcInfo = (EfdcInfo)((Object)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS"))));
                if (efdcInfo.getDimensionSet().get("MD_CURRENCY") != null && (efdcInfo.getDimensionSet().get("MD_CURRENCY").getValue().equals("PROVIDER_BASECURRENCY") || efdcInfo.getDimensionSet().get("MD_CURRENCY").getValue().equals("PROVIDER_PBASECURRENCY"))) {
                    JtableContext jtableContext = new JtableContext();
                    jtableContext.setDimensionSet(efdcInfo.getDimensionSet());
                    jtableContext.setTaskKey(efdcInfo.getTaskKey());
                    jtableContext.setFormSchemeKey(efdcInfo.getFormSchemeKey());
                    List currency = currencyService.getCurrencyInfo(jtableContext, efdcInfo.getDimensionSet().get("MD_CURRENCY").getValue());
                    if (currency != null) {
                        efdcInfo.getDimensionSet().get("MD_CURRENCY").setValue((String)currency.get(0));
                    }
                }
                if (!CollectionUtils.isEmpty(efdcResult = efdcService.efdcService(efdcInfo, (AsyncTaskMonitor)asyncTaskMonitor, params))) {
                    for (String key : efdcResult.keySet()) {
                        retStr = mapper.writeValueAsString((Object)efdcResult.get(key));
                        asyncTaskMonitor.error("efdcError", null, retStr);
                    }
                } else {
                    asyncTaskMonitor.finish("efdcComplete", (Object)retStr);
                }
            }
        }
        catch (Throwable nrCommonException) {
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.error("efdcError", nrCommonException);
            }
            log.error(nrCommonException.getMessage(), nrCommonException);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_EFDC.getName();
    }
}

