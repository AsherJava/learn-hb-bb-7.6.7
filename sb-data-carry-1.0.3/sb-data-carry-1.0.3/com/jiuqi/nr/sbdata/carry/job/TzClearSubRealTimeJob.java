/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 */
package com.jiuqi.nr.sbdata.carry.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.sbdata.carry.bean.DataTableCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.FormCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.TzClearDataParam;
import com.jiuqi.nr.sbdata.carry.service.TzCarryDownService;
import com.jiuqi.nr.sbdata.carry.util.TzCarryUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

@RealTimeJob(group="ASYNCTASK_TZ_CLEAR_SUB", groupTitle="\u53f0\u8d26\u6570\u636e\u6e05\u9664-\u5b50\u4efb\u52a1", subject="\u62a5\u8868")
public class TzClearSubRealTimeJob
extends AbstractRealTimeJob {
    public void execute(JobContext jobContext) throws JobExecutionException {
        RuntimeViewController runtimeViewController = (RuntimeViewController)BeanUtil.getBean(RuntimeViewController.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        TzCarryDownService tzCarryDownService = (TzCarryDownService)BeanUtil.getBean(TzCarryDownService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, "ASYNCTASK_TZ_CLEAR_SUB", jobContext);
        Logger defaultLogger = jobContext.getDefaultLogger();
        if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
            TzClearDataParam param = (TzClearDataParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            List allRegionsInForm = runtimeViewController.getAllRegionsInForm(param.getFormKey());
            FormDefine formDefine = runtimeViewController.queryFormById(param.getFormKey());
            ArrayList<DataTableCarryResult> results = new ArrayList<DataTableCarryResult>();
            asyncTaskMonitor.progressAndMessage(0.01, "\u5f00\u59cb\u6e05\u9664\u62a5\u8868" + formDefine.getFormCode());
            defaultLogger.info("\u5f00\u59cb\u6e05\u9664\u62a5\u8868" + formDefine.getFormCode());
            double splitSize = 0.9 / (double)allRegionsInForm.size();
            double currentProgress = 0.1;
            block0: for (DataRegionDefine region : allRegionsInForm) {
                asyncTaskMonitor.progressAndMessage(currentProgress, "\u5f00\u59cb\u6e05\u9664\u533a\u57df" + region.getCode() + "\u6570\u636e");
                Map<String, List<DataField>> dataTable2DataField = TzCarryUtils.getDataTable2DataField(runtimeViewController, runtimeDataSchemeService, region.getKey());
                if (CollectionUtils.isEmpty(dataTable2DataField)) {
                    DataTableCarryResult result = new DataTableCarryResult();
                    results.add(result);
                    continue;
                }
                for (Map.Entry<String, List<DataField>> entry : dataTable2DataField.entrySet()) {
                    param.setDataTableKey(entry.getKey());
                    param.setFields(entry.getValue());
                    DataTableCarryResult result = tzCarryDownService.clearData(param);
                    results.add(result);
                    if (result.isSuccess()) continue;
                    break block0;
                }
                asyncTaskMonitor.progressAndMessage(currentProgress += splitSize, "\u533a\u57df" + region.getCode() + "\u6570\u636e\u6e05\u9664\u5b8c\u6210");
            }
            DataTableCarryResult result = TzCarryUtils.mergeResult(results);
            FormCarryResult formCarryResult = new FormCarryResult(result);
            formCarryResult.setFormCode(formDefine.getFormCode());
            String detail = JsonUtil.objectToJson((Object)formCarryResult);
            if (formCarryResult.isSuccess()) {
                defaultLogger.info("\u62a5\u8868" + formDefine.getFormCode() + "\u6570\u636e\u6e05\u9664\u5b8c\u6210\uff01");
                asyncTaskMonitor.finish("\u62a5\u8868" + formDefine.getFormCode() + "\u6570\u636e\u6e05\u9664\u5b8c\u6210\uff01", (Object)detail);
            } else {
                defaultLogger.error("\u62a5\u8868" + formDefine.getFormCode() + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01");
                asyncTaskMonitor.error("\u62a5\u8868" + formDefine.getFormCode() + "\u6570\u636e\u6e05\u9664\u5931\u8d25\uff01", null, detail);
            }
        }
    }
}

