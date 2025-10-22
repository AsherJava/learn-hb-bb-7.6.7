/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.dataentry.paramInfo.FormSchemeData
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.service.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.asynctask.EntityCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.AssTaskFormSchemeInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckContext;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EnumStructure;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.PeriodSchemeInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckController;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u6237\u6570\u6838\u5bf9"})
@RequestMapping(value={"FinalAccountsAudit/EntityCheck"})
@Service
public class EntityCheckService {
    private static final Logger logger = LoggerFactory.getLogger(EntityCheckService.class);
    @Autowired
    private IEntityCheckController controller;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IDataEntryParamService dataEntryParamService;

    @RequestMapping(value={"/tasks"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    public List<TaskData> runtimeTaskList() {
        return this.dataEntryParamService.getRuntimeTaskList();
    }

    @RequestMapping(value={"/formschemes"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848")
    public List<FormSchemeData> runtimeFormSchemeList(@Valid @RequestBody EntityCheckInfo entityCheckInfo) {
        if (StringUtils.isNotEmpty((String)entityCheckInfo.getTaskKey())) {
            try {
                return this.dataEntryParamService.runtimeFormSchemeList(entityCheckInfo.getTaskKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            return new ArrayList<FormSchemeData>();
        }
        return new ArrayList<FormSchemeData>();
    }

    @RequestMapping(value={"/associatedformschemes"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848")
    public List<FormSchemeData> runtimeAssociatedFormSchemeList(@Valid @RequestBody EntityCheckInfo entityCheckInfo) {
        if (StringUtils.isNotEmpty((String)entityCheckInfo.getAssociatedTaskKey())) {
            try {
                return this.dataEntryParamService.runtimeFormSchemeList(entityCheckInfo.getAssociatedTaskKey());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            return new ArrayList<FormSchemeData>();
        }
        return new ArrayList<FormSchemeData>();
    }

    @JLoggable(value="\u6267\u884c\u5f02\u6b65\u6237\u6570\u6838\u5bf9")
    @PostMapping(value={"/entity-check"})
    @ApiOperation(value="\u6237\u6570\u6838\u5bf9", notes="\u6237\u6570\u6838\u5bf9")
    public AsyncTaskInfo entityCheck(@Valid @RequestBody EntityCheckInfo entityCheckInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(entityCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(entityCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)entityCheckInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new EntityCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_ENTITYCHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/GetAuditResult"})
    @ApiOperation(value="\u83b7\u53d6\u6237\u6570\u6838\u5bf9\u5ba1\u6838\u7ed3\u679c\u4fe1\u606f")
    public String GetAuditResult(@Valid @RequestBody EntityCheckInfo entityCheckInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        String taskKey = entityCheckInfo.getTaskKey();
        String formSchemeKey = entityCheckInfo.getFormSchemeKey();
        String period = entityCheckInfo.getPeriod();
        String scop = entityCheckInfo.getScop();
        String associatedTaskKey = entityCheckInfo.getAssociatedTaskKey();
        String associatedFormSchemeKey = entityCheckInfo.getAssociatedFormSchemeKey();
        String associatedperiod = entityCheckInfo.getAssociatedperiod();
        JtableContext context = entityCheckInfo.getContext();
        return this.controller.GetAuditResult(asyncTaskMonitor, taskKey, formSchemeKey, period, scop, associatedTaskKey, associatedFormSchemeKey, associatedperiod, context);
    }

    @PostMapping(value={"/EntityCheckUp"})
    @ApiOperation(value="\u6267\u884c\u6237\u6570\u6838\u5bf9\u5904\u7406\u903b\u8f91")
    public String EntityCheckUp(@Valid @RequestBody EntityCheckInfo entityCheckInfo) throws Exception {
        String taskKey = entityCheckInfo.getTaskKey();
        String formSchemeKey = entityCheckInfo.getFormSchemeKey();
        String period = entityCheckInfo.getPeriod();
        String scop = entityCheckInfo.getScop();
        String associatedTaskKey = entityCheckInfo.getAssociatedTaskKey();
        String associatedFormSchemeKey = entityCheckInfo.getAssociatedFormSchemeKey();
        String associatedperiod = entityCheckInfo.getAssociatedperiod();
        String webTabName = entityCheckInfo.getWebTabName();
        boolean isDetailed = Boolean.valueOf(entityCheckInfo.getIsDetailed());
        JtableContext context = entityCheckInfo.getContext();
        String filterValue = entityCheckInfo.getFilterValue();
        return this.controller.EntityCheckUp(taskKey, formSchemeKey, period, scop, webTabName, isDetailed, associatedTaskKey, associatedFormSchemeKey, associatedperiod, context, filterValue);
    }

    @RequestMapping(value={"/insertEntityCheckUp"}, method={RequestMethod.POST})
    @ApiOperation(value="\u589e\u52a0\u6bd4\u5bf9\u7ed3\u679c\u6570\u636e")
    @ResponseBody
    public boolean insertEntityCheckUp(@Valid @RequestBody EntityCheckContext entityCheckContext) throws Exception {
        return this.controller.insertEntityCheckUp(entityCheckContext);
    }

    @RequestMapping(value={"/updateBatchToFMDM"}, method={RequestMethod.POST})
    @ApiOperation(value="\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u76f8\u5173\u5355\u4f4d\u7684\u65b0\u62a5\u56e0\u7d20")
    @ResponseBody
    public boolean updateBatchToFMDM(@Valid @RequestBody EntityCheckContext entityCheckContext) {
        return this.controller.updateBatchToFMDM(entityCheckContext);
    }

    @GetMapping(value={"/getRelationTaskCount"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u8bbe\u8ba1\u8bbe\u7f6e\u7684\u5173\u8054\u4efb\u52a1\u4e2a\u6570")
    public int getRelationTaskCount(String formSchemeKey) {
        return this.controller.getRelationTaskCount(formSchemeKey);
    }

    @GetMapping(value={"/getRelationTaskTitle"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u8bbe\u8ba1\u8bbe\u7f6e\u7684\u5173\u8054\u4efb\u52a1\u7684Title")
    public String getRelationTaskTitle(String taskKey, String formSchemeKey, String period) throws Exception {
        return this.controller.getRelationTaskTitle(taskKey, formSchemeKey, period);
    }

    @GetMapping(value={"/getRelationTaskJsonStr"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u65b9\u6848\u5173\u8054\u7684\u4efb\u52a1\u5217\u8868\u4fe1\u606f")
    public String getRelationTaskJsonStr(String formSchemeKey) throws Exception {
        return this.controller.getRelationTaskJsonStr(formSchemeKey);
    }

    @GetMapping(value={"/getRelationTaskToFromSchemeJsonStr"})
    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u5bf9\u5e94\u7684\u4efb\u52a1\u65b9\u6848")
    public String getRelationTaskToFromSchemeJsonStr(String taskKey, String formSchemeKey, String period) throws Exception {
        return this.controller.getRelationTaskToFromSchemeJsonStr(taskKey, formSchemeKey, period);
    }

    @GetMapping(value={"/getMasterEntityKey"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u5b9e\u4f53key")
    public String getMasterEntityKey(String formSchemeKey) {
        return this.controller.getMasterEntityKey(formSchemeKey);
    }

    @GetMapping(value={"/getCurrentUnitCount"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u671f\u4efb\u52a1\u7684\u5355\u4f4d\u6570\u91cf")
    public int getCurrentUnitCount(String formSchemeKey, String period) {
        return this.controller.getCurrentUnitCount(formSchemeKey, period);
    }

    @GetMapping(value={"/querySchemePeriodMapByTask"})
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1key\u83b7\u53d6\u6240\u6709\u65f6\u95f4\u5bf9\u5e94\u65b9\u6848\u548cdimensions\u4fe1\u606f")
    public List<PeriodSchemeInfo> querySchemePeriodMapByTask(String taskKey) throws Exception {
        return this.controller.querySchemePeriodMapByTask(taskKey);
    }

    @GetMapping(value={"/querySchemePeriodMapByTaskAndFormSchemePeriod"})
    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u65b9\u6848\u65f6\u671f\u4fe1\u606f")
    public AssTaskFormSchemeInfo querySchemePeriodMapByTaskAndFormSchemePeriod(String taskKey, String formSchemeKey, String period) throws Exception {
        return this.controller.querySchemePeriodMapByTaskAndFormSchemePeriod(taskKey, formSchemeKey, period);
    }

    @GetMapping(value={"/getJSYYSlectData"})
    @ApiOperation(value="\u83b7\u53d6\u51cf\u5c11\u539f\u56e0\u4e0b\u62c9\u9879")
    public List<EnumStructure> getJSYYSlectData(String taskKey, String formSchemeKey, String associatedTaskKey, String associatedFormSchemeKey) {
        return this.controller.getJSYYSlectData(taskKey, formSchemeKey, associatedTaskKey, associatedFormSchemeKey);
    }
}

