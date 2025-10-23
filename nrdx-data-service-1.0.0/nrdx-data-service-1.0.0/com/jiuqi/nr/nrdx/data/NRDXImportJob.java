/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.ChangeInfo
 *  com.jiuqi.bi.transfer.engine.ChangeMode
 *  com.jiuqi.bi.transfer.engine.DataMode
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.TaskResultEnum
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.io.record.bean.ImportHistory
 *  com.jiuqi.nr.io.record.bean.ImportState
 *  com.jiuqi.nr.io.record.service.ImportHistoryService
 *  com.jiuqi.nr.nrdx.adapter.dto.IParamVO
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferFileRecorder
 */
package com.jiuqi.nr.nrdx.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.ChangeInfo;
import com.jiuqi.bi.transfer.engine.ChangeMode;
import com.jiuqi.bi.transfer.engine.DataMode;
import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.TaskResultEnum;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportState;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxParamNodeType;
import com.jiuqi.nr.nrdx.data.JobUtil;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferFileRecorder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="NR", groupTitle="NRDX\u6570\u636e\u5bfc\u5165")
public class NRDXImportJob
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(NRDXImportJob.class);
    private static final long serialVersionUID = 1063636352646656L;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        IParamVO paramVO;
        IProgressMonitor jobContextMonitor = jobContext.getMonitor();
        ImportHistoryService importHistoryService = (ImportHistoryService)BeanUtil.getBean(ImportHistoryService.class);
        ImportHistory importHistory = JobUtil.getImportHistoryWhenJobExec(jobContext, importHistoryService);
        importHistory.setState(Integer.valueOf(ImportState.PROCESSING.getCode()));
        this.asyncTaskStepMonitor.startTask("\u5bfc\u5165", 2);
        String args = this.getArgs();
        try {
            paramVO = (IParamVO)this.objectMapper.readValue(args, IParamVO.class);
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
            IProgressMonitor monitor = jobContext.getMonitor();
            String userId = NpContextHolder.getContext().getUserId();
            TransferContext context = new TransferContext(userId);
            context.setSkipImportModel(true);
            context.setEnableFactoryHandleFile(true);
            context.setSource("NR");
            String instanceId = jobContext.getInstanceId();
            paramVO.setRecKey(instanceId);
            args = this.objectMapper.writeValueAsString((Object)paramVO);
            context.getExtInfo().put("i_args", args);
            context.setProgressMonitor(monitor);
            TransferEngine engine = new TransferEngine();
            TransferFileRecorder recorder = new TransferFileRecorder(paramVO.getKey(), (ITransferContext)context);
            Desc desc = engine.getDescInfo((IFileRecorder)recorder);
            HashMap changeInfoMap = new HashMap();
            ArrayList<ChangeInfo> changeInfos = new ArrayList<ChangeInfo>();
            ChangeInfo changeInfo = new ChangeInfo();
            String businessId = NrdxGuidParse.toBusinessId((NrdxParamNodeType)NrdxParamNodeType.FORMSCHEME, (String)paramVO.getFormSchemeKey());
            changeInfo.setGuid(businessId);
            changeInfo.setType(NrdxParamNodeType.FORMSCHEME.name());
            changeInfo.setChangeMode(ChangeMode.MODIFIED);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(paramVO.getFormSchemeKey());
            changeInfo.setName(formScheme.getTitle());
            changeInfos.add(changeInfo);
            changeInfoMap.put("TASK_DATA", changeInfos);
            List businessNodes = desc.getBusinessNodes("TASK_DATA");
            for (BusinessNode businessNode : businessNodes) {
                businessNode.setDataMode(DataMode.DATA);
                ChangeInfo businessChangeInfo = new ChangeInfo();
                businessChangeInfo.setGuid(businessNode.getGuid());
                businessChangeInfo.setType(businessNode.getType());
                businessChangeInfo.setChangeMode(ChangeMode.MODIFIED);
                businessChangeInfo.setName(businessNode.getName());
                changeInfos.add(businessChangeInfo);
            }
            try {
                engine.importProcess((ITransferContext)context, (IFileRecorder)recorder, desc, changeInfoMap);
            }
            finally {
                recorder.destroy();
            }
            importHistory = importHistoryService.queryByRecKey(jobContext.getInstanceId());
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
}

