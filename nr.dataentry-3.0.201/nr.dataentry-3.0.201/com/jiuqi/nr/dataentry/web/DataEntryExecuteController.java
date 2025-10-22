/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.dataentry.bean.DataEntityReturnInfo;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.DataEntryFormulaSchemeParam;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.DataentryEntityParam;
import com.jiuqi.nr.dataentry.bean.DataentryEntityParamGet;
import com.jiuqi.nr.dataentry.bean.DataentryEntityParamGetByTaskIdList;
import com.jiuqi.nr.dataentry.bean.DataentryEntityQueryInfo;
import com.jiuqi.nr.dataentry.bean.DataentryFormSchemeParam;
import com.jiuqi.nr.dataentry.bean.DataentryRefreshParam;
import com.jiuqi.nr.dataentry.bean.FormAnalysisConfig;
import com.jiuqi.nr.dataentry.bean.FormDataStatusParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.bean.InitLinkParam;
import com.jiuqi.nr.dataentry.bean.RefreshStatusParam;
import com.jiuqi.nr.dataentry.gather.IDataentryRefreshParams;
import com.jiuqi.nr.dataentry.paramInfo.BatchExportOrImportFileType;
import com.jiuqi.nr.dataentry.paramInfo.ChangeEntitys;
import com.jiuqi.nr.dataentry.paramInfo.DataEntryRefreshParams;
import com.jiuqi.nr.dataentry.paramInfo.DesignTaskGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.FormsParam;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.paramInfo.TaskGroupParam;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessResult;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessParam;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IFormAnalysisService;
import com.jiuqi.nr.dataentry.service.IFormDataStatusService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.service.IGetEntityInfoService;
import com.jiuqi.nr.dataentry.service.IReadWriteAccessService;
import com.jiuqi.nr.dataentry.service.IRefreshStatusService;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataservice.core.diminfo.facade.service.IDimInfoService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry"})
@Api(tags={"\u6570\u636e\u5f55\u5165"})
public class DataEntryExecuteController {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryExecuteController.class);
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private IFormDataStatusService formDataStatusService;
    @Autowired
    private IFormAnalysisService formAnalysisService;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IReadWriteAccessService readWriteAccessService;
    @Autowired
    DesignTaskGroupDefineService designTaskGroupDefineService;
    @Autowired
    private MessagePublisher messageService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IRuntimeFormService runtimeFormService;
    @Autowired
    private IDataentryRefreshParams dataEntryRefreshParams;
    @Autowired
    private IRefreshStatusService refreshStatusService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private IDimInfoService iDimInfoService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController apiRunTimeViewController;
    @Autowired
    private IGetEntityInfoService iGetEntityInfoService;
    @Value(value="${jiuqi.nr.dataentry.context.analysis:false}")
    private boolean analysisContext = false;

    @GetMapping(value={"/init"})
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5f55\u5165\u7684\u73af\u5883:\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u3001\u6a21\u677f")
    @NRContextBuild
    public Object getDataEntryEnv(@Valid DataEntryInitParam param, @Valid @ModelAttribute NRContext nrContext, HttpServletResponse httpResponse) throws Exception {
        return this.funcExecuteService.getDataEntryEnv(param);
    }

    @RequestMapping(value={"/getFormulaScheme"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5f55\u5165\u7684\u73af\u5883:\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u3001\u6a21\u677f")
    @NRContextBuild
    public Object getFormulaScheme(@Valid @RequestBody DataEntryFormulaSchemeParam param) throws Exception {
        if (param.getTaskIdList() == null || param.getTaskIdList().size() != 1) {
            return null;
        }
        DataEntryInitParam dataEntryInitParam = new DataEntryInitParam();
        dataEntryInitParam.setTaskKey(param.getTaskIdList().get(0));
        dataEntryInitParam.setPeriodInfo(param.getPeriod());
        FuncExecResult funcExecResult = this.funcExecuteService.getDataEntryEnv(dataEntryInitParam);
        List<FormSchemeResult> schemeResults = funcExecResult.getSchemes();
        FormSchemeResult currentScheme = null;
        currentScheme = param.getPeriod() != null && funcExecResult.getPeriodSchemeMap().containsKey(param.getPeriod()) ? schemeResults.get(funcExecResult.getPeriodSchemeMap().get(param.getPeriod())) : schemeResults.get(schemeResults.size() - 1);
        return currentScheme.getScheme().getFormulaSchemes();
    }

    @GetMapping(value={"/queryFormScheme"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5f55\u5165\u7684\u73af\u5883:\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u3001\u6a21\u677f")
    @NRContextBuild
    public String queryFormSchemeKey(@Valid DataEntryInitParam param, @ModelAttribute NRContext nrContext) {
        return this.funcExecuteService.queryFormScheme(param.getTaskKey(), param.getPeriodInfo()) != null ? this.funcExecuteService.queryFormScheme(param.getTaskKey(), param.getPeriodInfo()).getKey() : "";
    }

    @GetMapping(value={"/have-task"})
    @ResponseBody
    @ApiOperation(value="\u5224\u65ad\u53c2\u6570\u4e2d\u662f\u5426\u6709\u4efb\u52a1")
    @NRContextBuild
    public boolean haveTask(@Valid DataEntryInitParam param, @ModelAttribute NRContext nrContext) {
        return this.funcExecuteService.haveTask(param);
    }

    @RequestMapping(value={"/tasks"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @NRContextBuild
    public List<TaskData> runtimeTaskList() {
        return this.dataEntryParamService.getRuntimeTaskList();
    }

    @RequestMapping(value={"/getTasksByGroupKey"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u5206\u7ec4\u4e0b\u7684\u4efb\u52a1\u96c6\u5408")
    public List<TaskData> getAllTasksByGroupKey(@Valid @RequestBody TaskGroupParam taskGroupParam) {
        return this.dataEntryParamService.getRuntimeTaskListByGroupKey(taskGroupParam);
    }

    @GetMapping(value={"/allTaskInGroup/{taskGroupKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5206\u7ec4\u4e0b\u7684\u4efb\u52a1")
    public List<TaskDefine> getAllTasksInGroup(@PathVariable(value="taskGroupKey") String taskGroupKey) {
        return this.runTimeViewController.getAllRunTimeTasksInGroup(taskGroupKey);
    }

    @GetMapping(value={"/getFormSchemeData"})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5f55\u5165\u7684\u73af\u5883:\u83b7\u53d6\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u521d\u59cb\u5316\u6570\u636e")
    @NRContextBuild
    public List<FormSchemeResult> getFormSchemeData(@Valid DataEntryInitParam param, @Valid @ModelAttribute NRContext nrContext) {
        return this.funcExecuteService.getFormSchemeData(param);
    }

    @RequestMapping(value={"/taskList"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c=\u671f\u4efb\u52a1,\u521b\u5efa\u6570\u636e\u5f55\u5165\u5165\u53e3\u7528")
    @NRContextBuild
    public List<TaskData> runtimeTaskList(@Valid @RequestBody DataentryFormSchemeParam param) {
        List<TaskData> taskDataList = this.dataEntryParamService.getRuntimeTaskList();
        ArrayList<TaskData> taskDataListOfReturn = new ArrayList();
        if (StringUtils.isNotEmpty((String)param.getTaskGroup())) {
            for (TaskData taskData : taskDataList) {
                String taskGroupKeys = taskData.getTaskGroupKeys();
                if (taskGroupKeys == null || taskGroupKeys.indexOf(param.getTaskGroup()) < 0) continue;
                taskDataListOfReturn.add(taskData);
            }
        } else {
            taskDataListOfReturn = taskDataList;
        }
        return taskDataListOfReturn;
    }

    @RequestMapping(value={"/entityInfo"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u5b9e\u4f53\u4fe1\u606f")
    @NRContextBuild
    public List<IEntityDefine> queryPin(@Valid @RequestBody DataentryEntityParam param) {
        List<String> taskKeyList = param.getTaskId();
        return this.iGetEntityInfoService.getEntityInfoByTaskList(taskKeyList);
    }

    @RequestMapping(value={"/entityInfoByTaskList"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u5b9e\u4f53\u4fe1\u606f")
    @NRContextBuild
    public List<IEntityDefine> queryPinByTaskList(@Valid @RequestBody DataentryEntityParamGetByTaskIdList param) {
        List<String> taskKeyList = param.getTaskIdList();
        return this.iGetEntityInfoService.getEntityInfoByTaskList(taskKeyList);
    }

    @RequestMapping(value={"/getEntityInfo"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u67e5\u8be2\u5b9e\u4f53\u4fe1\u606f")
    @NRContextBuild
    public List<IEntityDefine> getQueryPin(@Valid @RequestBody DataentryEntityParamGet param) {
        String taskId = param.getTaskid();
        ArrayList<String> taskKeyList = new ArrayList<String>();
        taskKeyList.add(taskId);
        if (StringUtils.isNotEmpty((String)taskId)) {
            return this.iGetEntityInfoService.getEntityInfoByTaskList(taskKeyList);
        }
        return new ArrayList<IEntityDefine>();
    }

    @RequestMapping(value={"/allTaskGroup"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8bbe\u8ba1\u671f\u4efb\u52a1\u5206\u7ec4")
    @NRContextBuild
    public List<DesignTaskGroupData> getAllTaskGroupDataS() {
        ArrayList<DesignTaskGroupData> designTaskGroupDataList = new ArrayList<DesignTaskGroupData>();
        try {
            List designTaskGroupDefines = this.designTaskGroupDefineService.queryAllTaskGroupDefine();
            for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
                DesignTaskGroupData designTaskGroupData = new DesignTaskGroupData();
                designTaskGroupData.init(designTaskGroupDefine);
                designTaskGroupDataList.add(designTaskGroupData);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return designTaskGroupDataList;
    }

    @RequestMapping(value={"/formschemes"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848")
    @NRContextBuild
    public List<FormSchemeData> runtimeFormSchemeList(@Valid @RequestBody DataentryFormSchemeParam param) throws Exception {
        if (StringUtils.isNotEmpty((String)param.getTaskId())) {
            return this.dataEntryParamService.runtimeFormSchemeList(param.getTaskId());
        }
        return new ArrayList<FormSchemeData>();
    }

    @PostMapping(value={"/forms"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868")
    @NRContextBuild
    public FormsQueryResult getForms(@Valid @RequestBody FormsParam param) {
        return this.funcExecuteService.getForms(param);
    }

    @PostMapping(value={"/formsWithoutAuth"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868")
    @NRContextBuild
    public FormsQueryResult getFormsWithoutAuth(@Valid @RequestBody FormsParam param) {
        return this.funcExecuteService.getFormsWithoutAuth(param);
    }

    @GetMapping(value={"/fmdm-exist/{formSchemeKey}"})
    @ApiOperation(value="\u662f\u5426\u5b58\u5728\u5c01\u9762\u4ee3\u7801\u8868")
    @NRContextBuild
    public boolean fmdmExist(@PathVariable(value="formSchemeKey") String formSchemeKey, @ModelAttribute NRContext nrContext) {
        return this.funcExecuteService.fmdmExist(formSchemeKey);
    }

    @RequestMapping(value={"/entitydatas"}, method={RequestMethod.POST})
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u89c6\u56fe\u6570\u636e")
    @ResponseBody
    @NRContextBuild
    public List<DataEntityReturnInfo> queryEntityData(@Valid @RequestBody DataentryEntityQueryInfo entityQueryInfo) {
        return this.funcExecuteService.queryEntityData(entityQueryInfo);
    }

    @PostMapping(value={"/forms-read"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868")
    @NRContextBuild
    public FormsQueryResult getFormsReadable(@Valid @RequestBody DataEntryContext context) {
        return this.funcExecuteService.getFormsReadAble(context);
    }

    @PostMapping(value={"/data-status/period"})
    @ApiOperation(value="\u901a\u8fc7\u65f6\u671f\u83b7\u5f97\u6570\u636e\u72b6\u6001")
    @NRContextBuild
    public Map<String, String> getEntryedPeriod(@Valid @RequestBody FormDataStatusParam formDataStatusParam) {
        String formSchemeKey = formDataStatusParam.getFormSchemeKey();
        List<String> dates = formDataStatusParam.getDates();
        return this.formDataStatusService.getFilledPeriodBySql(formDataStatusParam.getFormSchemeKeyList(), dates);
    }

    @GetMapping(value={"/analysisConfig/{formKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5206\u6790\u8868\u914d\u7f6e\u4fe1\u606f")
    @NRContextBuild
    public FormAnalysisConfig getFormAnalysisConfig(@PathVariable(value="formKey") String formKey, @ModelAttribute NRContext nrContext) {
        return this.formAnalysisService.getFormAnalysisConfig(formKey);
    }

    @PostMapping(value={"/refresh-status"})
    @ApiOperation(value="")
    @NRContextBuild
    public Map<String, Object> getRefreshStatus(@Valid @RequestBody RefreshStatusParam param) {
        return this.refreshStatusService.getStatus(param);
    }

    @GetMapping(value={"/all-forms/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u5f97\u6240\u6709\u62a5\u8868")
    @NRContextBuild
    public FormTree getAllForms(@PathVariable(value="formSchemeKey") String formSchemeKey, @ModelAttribute NRContext nrContext) {
        return this.funcExecuteService.getAllForms(formSchemeKey);
    }

    @PostMapping(value={"/read-write"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u7684\u8bfb\u5199\u6743\u9650")
    @NRContextBuild
    public ReadWriteAccessResult getReadWriteAceess(@Valid @RequestBody ReadWriteAccessParam param) {
        return this.readWriteAccessService.getReadWriteAccess(param);
    }

    @RequestMapping(value={"/extension-params"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u6216\u62a5\u8868\u5237\u65b0\u65f6\u6ce8\u518c\u7684\u53c2\u6570")
    @NRContextBuild
    public List<DataEntryRefreshParams> extensionParams(@Valid @RequestBody DataentryRefreshParam dataentryRefreshParam) {
        List<DataEntryRefreshParams> addDataentryRefreshParams = this.dataEntryRefreshParams.addDataentryRefreshParams(dataentryRefreshParam);
        return addDataentryRefreshParams;
    }

    @RequestMapping(value={"/form-scheme/ids"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848id\u83b7\u53d6\u62a5\u8868\u65b9\u6848(\u76ee\u524d\u53ea\u7528\u6765\u529f\u80fd\u53c2\u6570\u4ea4\u4e92)")
    @NRContextBuild
    public List<FormSchemeData> formSchemeListByIds(@Valid @RequestBody DataentryFormSchemeParam param) throws Exception {
        if (StringUtils.isNotEmpty((String)param.getTaskId()) && param.getFormschemeIds() != null && param.getFormschemeIds().size() > 0) {
            List<FormSchemeData> runtimeFormSchemeList = this.dataEntryParamService.runtimeFormSchemeList(param.getTaskId());
            ArrayList<FormSchemeData> formSchemes = new ArrayList<FormSchemeData>();
            for (FormSchemeData formSchemeData : runtimeFormSchemeList) {
                if (!param.getFormschemeIds().contains(formSchemeData.getKey())) continue;
                formSchemes.add(formSchemeData);
            }
            return formSchemes;
        }
        return new ArrayList<FormSchemeData>();
    }

    @RequestMapping(value={"/change-entity"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u662f\u5426\u5141\u8bb8\u5207\u6362\u76ee\u6807\u5355\u4f4d")
    @NRContextBuild
    public List<ChangeEntitys> changeEntity() {
        ArrayList<ChangeEntitys> result = new ArrayList<ChangeEntitys>();
        ChangeEntitys changeEntity = new ChangeEntitys();
        changeEntity.setKey("0");
        changeEntity.setTitle("\u662f");
        ChangeEntitys noChangeEntity = new ChangeEntitys();
        noChangeEntity.setKey("1");
        noChangeEntity.setTitle("\u5426");
        result.add(changeEntity);
        result.add(noChangeEntity);
        return result;
    }

    @RequestMapping(value={"/data-sum-forms"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u662f\u5426\u5141\u8bb8\u5207\u6362\u76ee\u6807\u5355\u4f4d")
    @NRContextBuild
    public List<ChangeEntitys> dataSumForms() {
        ArrayList<ChangeEntitys> result = new ArrayList<ChangeEntitys>();
        ChangeEntitys changeEntity = new ChangeEntitys();
        changeEntity.setKey("0");
        changeEntity.setTitle("\u662f");
        ChangeEntitys noChangeEntity = new ChangeEntitys();
        noChangeEntity.setKey("1");
        noChangeEntity.setTitle("\u5426");
        result.add(changeEntity);
        result.add(noChangeEntity);
        return result;
    }

    @RequestMapping(value={"/batchExport-multiplePeriod"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6279\u91cf\u5bfc\u51fa\u662f\u5426\u652f\u6301\u9009\u62e9\u591a\u65f6\u671f")
    @NRContextBuild
    public List<ChangeEntitys> batchExportMultiplePeriod() {
        ArrayList<ChangeEntitys> result = new ArrayList<ChangeEntitys>();
        ChangeEntitys changeEntity = new ChangeEntitys();
        changeEntity.setKey("0");
        changeEntity.setTitle("\u662f");
        ChangeEntitys noChangeEntity = new ChangeEntitys();
        noChangeEntity.setKey("1");
        noChangeEntity.setTitle("\u5426");
        result.add(changeEntity);
        result.add(noChangeEntity);
        return result;
    }

    @RequestMapping(value={"/batchExportFileType"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6279\u91cf\u5bfc\u51fa\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b\u5217\u8868")
    public List<BatchExportOrImportFileType> batchExportFileType() {
        return BatchExportOrImportFileType.getAllBatchExportType();
    }

    @RequestMapping(value={"/batchImportFileType"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6279\u91cf\u5bfc\u5165\u652f\u6301\u7684\u6587\u4ef6\u7c7b\u578b\u5217\u8868")
    public List<BatchExportOrImportFileType> batchImportFileType() {
        return BatchExportOrImportFileType.getAllBatchImpotyType();
    }

    @RequestMapping(value={"/getFillingExplain"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u586b\u62a5\u8bf4\u660e")
    @NRContextBuild
    public String getFillingForm(String formKey, String formSchemeKey) {
        return ((FormDefine)this.apiRunTimeViewController.getFormStream(formKey, formSchemeKey).i18n().get()).getFillingGuide();
    }

    @RequestMapping(value={"/linkMsgTransfer"}, method={RequestMethod.POST})
    @ApiOperation(value="\u89e3\u6790\u5206\u6790\u8868\u4f20\u6765\u7684\u53c2\u6570\u4e3a\u62a5\u8868\u53c2\u6570")
    public Map<String, Object> queryInitLinkData(@Valid @RequestBody InitLinkParam initLinkParam) {
        return this.funcExecuteService.queryInitLinkData(initLinkParam);
    }
}

