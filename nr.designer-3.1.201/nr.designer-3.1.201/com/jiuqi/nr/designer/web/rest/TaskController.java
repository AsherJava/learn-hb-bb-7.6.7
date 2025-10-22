/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.service.IDesignFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityDefineAssist
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.service.IDesignFormSchemeService;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.ProgressLoadingService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.service.impl.QuoteFormByOtherTaskServiceImpl;
import com.jiuqi.nr.designer.util.JQExceptionWrapper;
import com.jiuqi.nr.designer.web.facade.CurrencyData;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.rest.resultBean.SaveTaskResult;
import com.jiuqi.nr.designer.web.rest.vo.CheckTaskTitleAvailable;
import com.jiuqi.nr.designer.web.rest.vo.TaskSchemeGroupTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.TaskTreeNode;
import com.jiuqi.nr.designer.web.treebean.TaskObject;
import com.jiuqi.nr.designer.web.treebean.TaskOrderObject;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class TaskController {
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private IDesignTimeViewController designTimeViewService;
    @Autowired
    private IViewDeployController iViewDeployController;
    @Autowired
    private ProgressLoadingService progressLoadingService;
    @Autowired
    private QuoteFormByOtherTaskServiceImpl quoteFormByOtherTaskServiceImpl;
    @Autowired
    private IDesignRestService restService;
    @Autowired
    private IDesignFormSchemeService iFormSchemeService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private IEntityDefineAssist iEntityDefineAssist;

    @PostMapping(value={"stepSaveTaskObj"})
    @ApiOperation(value="\u4fdd\u5b58\u4efb\u52a1")
    public SaveTaskResult saveTask(@RequestBody @SFDecrypt TaskObject taskObjectFin) throws JQException {
        String logTitle = "\u4fdd\u5b58\u4efb\u52a1";
        String taskTitle = "\u672a\u77e5";
        boolean taskCanEdit = false;
        try {
            taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskObjectFin.getID());
        }
        catch (Exception e) {
            throw JQExceptionWrapper.wrapper(e);
        }
        if (!taskCanEdit) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
        }
        try {
            taskTitle = taskObjectFin.getTitle();
            boolean titleAvailable = this.nrDesignTimeController.checkTaskNameAvailable(taskObjectFin.getID(), taskTitle);
            if (!titleAvailable) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_151);
            }
            FlowsObj flowsObj = taskObjectFin.getFlowsObject();
            if (flowsObj.getFlowType() == FlowsType.DEFAULT.getValue()) {
                this.commonHelper.checkSubmitEntity(flowsObj);
            }
            TaskObject resTaskObject = this.stepSaveService.saveTask(taskObjectFin);
            SaveTaskResult saveTaskResult = new SaveTaskResult();
            saveTaskResult.setTaskObject(taskObjectFin);
            saveTaskResult.setEntityList(new ArrayList<EntityTables>(resTaskObject.getEntityTablesList().values()));
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return saveTaskResult;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_117, (Throwable)e);
        }
    }

    @PostMapping(value={"editTaskOrder"})
    @ApiOperation(value="\u4fee\u6539\u4efb\u52a1\u6392\u5e8f")
    @RequiresPermissions(value={"nr:task:manage"})
    public void editTaskOrder(@RequestBody TaskOrderObject taskOrderObject) throws JQException {
        if (StringUtils.isEmpty((String)taskOrderObject.getPreTaskKey()) || StringUtils.isEmpty((String)taskOrderObject.getSufTaskKey())) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_212);
        }
        try {
            this.stepSaveService.editTaskOrder(taskOrderObject);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_212, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5224\u65ad\u4efb\u52a1\u662f\u5426\u5b58\u5728")
    @RequestMapping(value={"/{taskID}/task-exist"}, method={RequestMethod.GET})
    public boolean taskIsExist(@PathVariable(value="taskID") String taskID) {
        return this.nrDesignTimeController.queryTaskDefine(taskID) != null;
    }

    @ApiOperation(value="\u5224\u65ad\u4efb\u52a1\u540d\u79f0\u662f\u5426\u5b58\u5728")
    @RequestMapping(value={"/task-title-exist"}, method={RequestMethod.POST})
    public boolean isExistTaskByTitle(@RequestBody CheckTaskTitleAvailable checkTaskTitleAvailable) throws Exception {
        return this.restService.isExistTaskByTitle(checkTaskTitleAvailable);
    }

    @ApiOperation(value="\u8bbe\u8ba1\u671f\u53c2\u6570\u56de\u6eda")
    @RequestMapping(value={"taskParaRollBack/{taskKey}"}, method={RequestMethod.GET})
    public void taskParaRollBackByKey(@PathVariable(value="taskKey") String taskKey) throws JQException {
        String logTitle = "\u8bbe\u8ba1\u671f\u53c2\u6570\u56de\u6eda";
        String taskTitle = "\u672a\u77e5";
        try {
            taskTitle = this.designTimeViewService.queryTaskDefine(taskKey).getTitle();
            if (!this.taskPlanPublishExternalService.taskCanEdit(taskKey)) {
                throw new Exception("\u4efb\u52a1\u6b63\u5728\u53d1\u5e03");
            }
            this.iViewDeployController.deployTaskToDes(taskKey);
            this.taskPlanPublishExternalService.createMsgRollBackSuccess(taskKey);
            this.progressLoadingService.publishSuccess(taskKey, NpContextHolder.getContext().getUserId());
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_016, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5173\u8054\u4efb\u52a1-\u83b7\u53d6\u4efb\u52a1\u65b9\u6848\u6811")
    @RequestMapping(value={"/task-scheme-tree"}, method={RequestMethod.GET})
    public List<ITree<TaskTreeNode>> getTaskSchemes() throws JQException {
        return this.restService.getTaskFormSchemes();
    }

    @ApiOperation(value="\u62a5\u8868\u5f15\u7528-\u83b7\u53d6\u6240\u6709\u4efb\u52a1\u65b9\u6848\u4fe1\u606f")
    @RequestMapping(value={"getAllTaskAndFormScheme/{schemeKey}"}, method={RequestMethod.GET})
    public List<ITree<TaskSchemeGroupTreeNode>> getAllTaskAndFormScheme(@PathVariable String schemeKey) throws JQException {
        try {
            return this.quoteFormByOtherTaskServiceImpl.getSchemeTree(schemeKey);
        }
        catch (Exception e2) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u4efb\u52a1\u7684codes\uff0c\u5305\u542b\u6240\u6709\u4efb\u52a1\u7c7b\u578b")
    @RequestMapping(value={"report-task-codes"}, method={RequestMethod.GET})
    public List<String> getAllReportTaskCodes() throws JQException {
        try {
            List taskList = this.designTimeViewService.getAllTaskDefines();
            List<String> allTaskCodes = taskList.stream().map(TaskDefine::getTaskCode).collect(Collectors.toList());
            return allTaskCodes;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_034, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6240\u6709\u666e\u901a\u4efb\u52a1\u65b9\u6848\u6811\u5f62")
    @GetMapping(value={"/all-default-task-scheme-itree"})
    public List<ITree<TaskTreeNode>> getAllDefaultTaskSchemeItree() throws JQException {
        try {
            return this.restService.getAllDefaultTaskSchemeItree();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_172, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u4e00\u4e2a\u4efb\u52a1\u5bf9\u8c61")
    @GetMapping(value={"/queryTask/{taskKey}"})
    public DesignTaskDefine getTask(@PathVariable(value="taskKey") String taskKey) {
        DesignTaskDefine task = this.nrDesignTimeController.queryTaskDefine(taskKey);
        return task;
    }

    @ApiOperation(value="\u5224\u65ad\u662f\u5426\u5f00\u542f\u4e0a\u62a5\u60c5\u666f")
    @GetMapping(value={"/task/existprocess/{formscheme}"})
    @ResponseBody
    public boolean existProcess(@PathVariable(value="formscheme") String formscheme) throws Exception {
        Boolean reportDimension;
        Boolean aBoolean;
        DesignTaskDefine designTaskDefine;
        boolean openCurrency;
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)formscheme)) {
            return false;
        }
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(formscheme);
        return null != formSchemeDefine && (openCurrency = this.iEntityDefineAssist.existCurrencyAttributes((designTaskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey())).getDw())) && (aBoolean = this.iFormSchemeService.existCurrencyDim(formSchemeDefine.getKey())) != false && (reportDimension = this.iFormSchemeService.isReportDimension(formSchemeDefine.getKey(), "MD_CURRENCY@BASE")) == false;
    }

    @ApiOperation(value="\u67e5\u8be2\u5e01\u79cd\u6570\u636e")
    @GetMapping(value={"/task/currencydata"})
    @ResponseBody
    public List<CurrencyData> queryCurrencyData() throws Exception {
        ArrayList<CurrencyData> currencyDatas = new ArrayList<CurrencyData>();
        RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
        viewDefine.setEntityId("MD_CURRENCY@BASE");
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
        iEntityQuery.sorted(true);
        ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
        context.setPeriodView("MD_CURRENCY@BASE");
        IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)context);
        List allRows = iEntityTable.getAllRows();
        if (null != allRows && allRows.size() != 0) {
            for (IEntityRow allRow : allRows) {
                currencyDatas.add(new CurrencyData(allRow.getCode(), allRow.getTitle()));
            }
        }
        return currencyDatas;
    }

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4\u548c\u4efb\u52a1\u7684\u6811\u5f62")
    @GetMapping(value={"/query_task_tree"})
    public List<ITree<TaskTreeNode>> getTaskGroupAndTaskTree() throws JQException {
        return this.restService.getTaskGroupAndTaskTree();
    }
}

