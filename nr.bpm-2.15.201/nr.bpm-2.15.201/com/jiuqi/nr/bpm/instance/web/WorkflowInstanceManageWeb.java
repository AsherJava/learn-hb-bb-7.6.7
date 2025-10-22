/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.bpm.instance.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.instance.async.OperateWorkflowTaskExecutor;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.instance.bean.GridDataResult;
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataParam;
import com.jiuqi.nr.bpm.instance.bean.ReportDataResult;
import com.jiuqi.nr.bpm.instance.bean.StartStateParam;
import com.jiuqi.nr.bpm.instance.bean.TaskNode;
import com.jiuqi.nr.bpm.instance.bean.WorkflowBaseInfoResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowBaseOtherInfo;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefine;
import com.jiuqi.nr.bpm.instance.bean.WorkflowDefineResult;
import com.jiuqi.nr.bpm.instance.bean.WorkflowRelation;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.bpm.setting.constant.SettingError;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value={"/api/workflow/instacne"})
@JQRestController
public class WorkflowInstanceManageWeb {
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @NRContextBuild
    @GetMapping(value={"/queryBaseInfo"})
    public WorkflowBaseInfoResult queryBaseInfo(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period) {
        return this.workflowInstanceService.queryBaseInfo(taskKey, period);
    }

    @NRContextBuild
    @GetMapping(value={"/getTasks"})
    public List<TaskNode> getTasks() {
        return this.workflowInstanceService.queryTasks();
    }

    @NRContextBuild
    @GetMapping(value={"/queryWorkflowDefines"})
    public List<WorkflowDefine> queryWorkflowDefines() {
        return this.workflowInstanceService.queryWorkflowDefines();
    }

    @NRContextBuild
    @GetMapping(value={"/queryWorkflowRelatedInfo"})
    public WorkflowBaseOtherInfo queryWorkflowRelatedInfo(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period) {
        return this.workflowInstanceService.queryBaseOtherInfo(taskKey, period);
    }

    @NRContextBuild
    @PostMapping(value={"/queryGridData"})
    public GridDataResult queryGridData(@RequestBody QueryGridDataParam queryGridDataParam) {
        return this.workflowInstanceService.queryGridDatas(queryGridDataParam);
    }

    @NRContextBuild
    @PostMapping(value={"/operateWorkflowInstance"})
    public AsyncTaskInfo operateWorkflowInstance(@RequestBody StartStateParam startStateParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)startStateParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new OperateWorkflowTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @NRContextBuild
    @PostMapping(value={"/queryWorkflowRelation"})
    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5173\u8054\u7684\u6d41\u7a0b\u5b9a\u4e49")
    public String queryWorkflowRelation(@RequestBody WorkflowRelation workflowRelation) {
        return this.workflowInstanceService.queryWorkflowKey(workflowRelation);
    }

    @NRContextBuild
    @PostMapping(value={"/savaWorkflowRelation"})
    @ApiOperation(value="\u5173\u8054\u6d41\u7a0b\u5b9a\u4e49")
    public boolean savaWorkflowRelation(@RequestBody WorkflowRelation workflowRelation) {
        return this.workflowInstanceService.savaWorkflowRelation(workflowRelation);
    }

    @NRContextBuild
    @PostMapping(value={"/queryReportData"})
    @ApiOperation(value="\u62a5\u8868\u53ca\u62a5\u8868\u5206\u7ec4")
    public List<ReportDataResult> queryReportData(@RequestBody ReportDataParam reportParam) throws JQException {
        return this.workflowInstanceService.queryReportDataResult(reportParam);
    }

    @NRContextBuild
    @GetMapping(value={"/queryWorkflows"})
    @ApiOperation(value="\u67e5\u8be2\u6d41\u7a0b\u5b9a\u4e49")
    public List<WorkflowDefineResult> queryWorkflow(@RequestParam String taskId) {
        return this.workflowInstanceService.queryWorkflows(taskId);
    }

    @NRContextBuild
    @GetMapping(value={"/refreshActors"})
    @ApiOperation(value="\u5237\u65b0\u53c2\u4e0e\u8005")
    public void refreshStrategicPartici(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period) throws JQException {
        try {
            this.workflowInstanceService.refreshStrategicPartici(taskKey, period);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRPROCESS.getTitle(), (String)"\u5237\u65b0\u53c2\u4e0e\u8005", (String)"\u5237\u65b0\u53c2\u4e0e\u8005\u5931\u8d25");
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u5237\u65b0\u53c2\u4e0e\u8005\u5931\u8d25");
        }
    }

    @NRContextBuild
    @GetMapping(value={"/queryCorporateValueList"})
    @ApiOperation(value="\u83b7\u53d6\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u7684\u503c")
    @RequiresPermissions(value={"nr:overview:query"})
    public List<CorporateData> queryCorporateValueList(@RequestParam(value="taskKey") String taskKey) throws JQException {
        try {
            return this.workflowInstanceService.queryCorporateList(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SettingError.S_ERROR, "\u83b7\u53d6\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u7684\u503c\u5931\u8d25");
        }
    }
}

