/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.TaskResultEnum
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.io.record.bean.ImportHistory
 *  com.jiuqi.nr.io.record.bean.ImportState
 *  com.jiuqi.nr.io.record.service.ImportHistoryService
 *  com.jiuqi.nr.transmission.data.intf.ImportParam
 *  com.jiuqi.nr.transmission.data.intf.TransmissionResult
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor
 *  com.jiuqi.nr.transmission.data.service.IExecuteSyncIOService
 */
package com.jiuqi.nr.nrdx.data.nrd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.TaskResultEnum;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportState;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.nrdx.data.JobUtil;
import com.jiuqi.nr.nrdx.data.nrd.ImpParVO;
import com.jiuqi.nr.nrdx.data.nrd.NrdMonitor;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import com.jiuqi.nr.transmission.data.intf.TransmissionResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.service.IExecuteSyncIOService;
import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="NRDX_IMP_NRD", groupTitle="NRDX\u6570\u636e\u5bfc\u5165(\u517c\u5bb9\u8001\u7248\u672cNRD\u53c2\u6570\u5305)")
public class NRDImpJob
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 5792188288402196579L;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(NRDImpJob.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        ImpParVO paramVO;
        IProgressMonitor jobContextMonitor = jobContext.getMonitor();
        ImportHistoryService importHistoryService = (ImportHistoryService)BeanUtil.getBean(ImportHistoryService.class);
        ImportHistory importHistory = JobUtil.getImportHistoryWhenJobExec(jobContext, importHistoryService);
        importHistory.setState(Integer.valueOf(ImportState.PROCESSING.getCode()));
        this.asyncTaskStepMonitor.startTask("\u5bfc\u5165", 2);
        String args = this.getArgs();
        try {
            paramVO = (ImpParVO)objectMapper.readValue(args, ImpParVO.class);
        }
        catch (Exception e) {
            jobContextMonitor.prompt("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u51fa\u5931\u8d25");
            importHistory.setState(Integer.valueOf(ImportState.EXCEPTION.getCode()));
            importHistory.setEndTime(new Date(System.currentTimeMillis()));
            this.asyncTaskStepMonitor.finishTask("\u5bfc\u5165", "\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u5165\u5931\u8d25", null, TaskResultEnum.FAILURE);
            throw new JobExecutionException("\u53c2\u6570\u672a\u652f\u6301\u5e8f\u5217\u5316\uff0c\u5bfc\u5165\u5931\u8d25", (Throwable)e);
        }
        importHistoryService.updateImportHistory(importHistory);
        try {
            String instanceId = jobContext.getInstanceId();
            RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(instanceId, "NRDX_IMP_NRD", jobContext);
            CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)BeanUtil.getBean(CacheObjectResourceRemote.class);
            IExecuteSyncIOService executeSyncIOService = (IExecuteSyncIOService)BeanUtil.getBean(IExecuteSyncIOService.class);
            NrdMonitor nrdMonitor = new NrdMonitor(instanceId, cacheObjectResourceRemote, (AsyncTaskMonitor)asyncTaskMonitor, null);
            ImportParam importParam = new ImportParam();
            importParam.setExecuteKey(instanceId);
            importParam.setFileKey(paramVO.getFileKey());
            importParam.setTaskKey(paramVO.getTaskKey());
            importParam.setFormSchemeKey(paramVO.getFormSchemeKey());
            importParam.setDoUpload(paramVO.isDoUpload() ? 1 : 0);
            importParam.setAllowForceUpload(paramVO.isAllowForceUpload() ? 1 : 0);
            importParam.setDescription(paramVO.getUploadDes());
            TransmissionResult transmissionResult = executeSyncIOService.executeOffLineImport(importParam, (TransmissionMonitor)nrdMonitor, 1.0);
            importHistory = importHistoryService.queryByRecKey(jobContext.getInstanceId());
            importHistory.setState(Integer.valueOf(this.getImpHisState(transmissionResult)));
            importHistory.setEndTime(new Date(System.currentTimeMillis()));
            importHistoryService.updateImportHistory(importHistory);
            this.asyncTaskStepMonitor.finishTask("\u5bfc\u5165", "\u5bfc\u5165\u5b8c\u6210", null);
        }
        catch (Exception e) {
            jobContextMonitor.prompt("\u5bfc\u5165\u5931\u8d25");
            importHistory.setState(Integer.valueOf(ImportState.EXCEPTION.getCode()));
            importHistory = importHistoryService.queryByRecKey(jobContext.getInstanceId());
            importHistory.setEndTime(new Date(System.currentTimeMillis()));
            importHistoryService.updateImportHistory(importHistory);
            log.error("\u5bfc\u5165\u5931\u8d25", e);
            this.asyncTaskStepMonitor.finishTask("\u5bfc\u5165", "\u5bfc\u5165\u5931\u8d25", null, TaskResultEnum.FAILURE);
        }
    }

    private int getImpHisState(TransmissionResult transmissionResult) {
        Boolean success = transmissionResult.getSuccess();
        if (success == null) {
            return ImportState.EXCEPTION.getCode();
        }
        if (success.booleanValue()) {
            return ImportState.FINISHED.getCode();
        }
        return ImportState.FAILED.getCode();
    }
}

