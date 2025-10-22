/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.FormulaSchemeTypeForFormulaManager
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.FormulaSchemeTypeForFormulaManager;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.web.facade.FormulaSchemeObj;
import com.jiuqi.nr.designer.web.facade.TaskTreeObj;
import com.jiuqi.nr.designer.web.facade.UReportTaskL;
import com.jiuqi.nr.designer.web.rest.vo.FormulaSchemeTypeVO;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.designer.web.service.ReportTaskService;
import com.jiuqi.nr.designer.web.service.TaskDeleteCondition;
import com.jiuqi.nr.designer.web.service.TaskDeleteExecutor;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api"})
public class ReportTaskController {
    private static final Logger log = LoggerFactory.getLogger(ReportTaskController.class);
    @Resource
    private ReportTaskService reportTaskService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IFormulaDesignTimeController formulaController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    @GetMapping(value={"/reportTasks/enable"})
    public boolean enable() throws JQException {
        return true;
    }

    @RequestMapping(value={"/reportTask/{task_id}"}, method={RequestMethod.GET})
    @ResponseBody
    public String getReportTaskInfo(@PathVariable(value="task_id") String taskId) throws JQException {
        return this.reportTaskService.getReportTaskInfo(taskId);
    }

    @RequestMapping(value={"/reportTask"}, method={RequestMethod.POST})
    @ResponseBody
    public String addReportTask(@RequestBody String para) {
        return this.reportTaskService.addReportTask(para);
    }

    @RequestMapping(value={"/reportTask/u"}, method={RequestMethod.POST})
    @ResponseBody
    public String editReportTask(@RequestBody String para) throws JQException {
        return this.reportTaskService.editReportTask(para);
    }

    @RequestMapping(value={"/reportTask/d/{task_id}"}, method={RequestMethod.POST})
    @ResponseBody
    public String deleteReportTask(@PathVariable(value="task_id") String taskId) throws JQException {
        boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskId);
        if (!taskCanEdit) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
        }
        if (!this.authorityProvider.canModeling(taskId)) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_013, "\u672a\u6388\u6743\uff01");
        }
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)new TaskDeleteCondition(taskId)));
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new TaskDeleteExecutor());
        return this.asyncTaskManager.publishTask(info, "ASYNCTASK_DELETETASK");
    }

    @RequestMapping(value={"/reportTask/d/without_data/{task_id}"}, method={RequestMethod.POST})
    @ResponseBody
    public String deleteReportTaskNoData(@PathVariable(value="task_id") String taskId) throws JQException {
        boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskId);
        if (!taskCanEdit) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
        }
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)new TaskDeleteCondition(taskId, false)));
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new TaskDeleteExecutor());
        return this.asyncTaskManager.publishTask(info, "ASYNCTASK_DELETETASK");
    }

    @GetMapping(value={"/reportTasks"})
    public List<UReportTaskL> getAllReportTasks() throws JQException {
        try {
            return this.reportTaskService.getAllReportTasks();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_034, (Throwable)e);
        }
    }

    @GetMapping(value={"/editReportTasks"})
    public List<UReportTaskL> getReportTasksOfEditor() throws JQException {
        try {
            return this.reportTaskService.getReportTasksOfEditor();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_034, (Throwable)e);
        }
    }

    @GetMapping(value={"/editReportTasksTree"})
    public List<TreeObj> getReportTasksOfEditorTree() throws JQException {
        try {
            List<TreeObj> taskTreeList = this.reportTaskService.getEditTaskTree();
            return taskTreeList;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_034, (Throwable)e);
        }
    }

    @RequestMapping(value={"/runTimeReportTasksTree"}, method={RequestMethod.GET})
    @ResponseBody
    public List<TreeObj> getRunTimeReportTasksTree() throws JQException {
        return this.reportTaskService.getRuntimeEditTaskTree();
    }

    @RequestMapping(value={"/reportTasksByFormulaType/{formulaType}"}, method={RequestMethod.POST})
    @ResponseBody
    public List<UReportTaskL> getReportTasksByFormulaType(@PathVariable(value="formulaType") Integer formulaType) throws JQException {
        try {
            return this.reportTaskService.getReportTasksByFormulaType(formulaType);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_034, (Throwable)e);
        }
    }

    @ApiOperation(value="post\u83b7\u53d6\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"form/scheme/initpost"}, method={RequestMethod.POST})
    @ResponseBody
    public String initPost(@RequestBody Map<String, String> params) throws JQException {
        List formSchemes;
        String taskKey = params.get("taskKey");
        ObjectMapper mapper = new ObjectMapper();
        if (StringUtils.isNotEmpty((String)taskKey) && (formSchemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey)) != null) {
            try {
                return mapper.writeValueAsString(formSchemes.stream().map(scheme -> {
                    HashMap<String, String> result = new HashMap<String, String>();
                    result.put("key", scheme.getKey().toString());
                    result.put("title", scheme.getTitle());
                    return result;
                }).collect(Collectors.toList()));
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"formula/scheme/querypost"}, method={RequestMethod.POST})
    @ResponseBody
    public String queryPost(@RequestBody Map<String, Object> params) {
        String formSchemeKey = (String)params.get("formschemeKey");
        int formulaSchemeType = FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT.getValue();
        if (params.get("formulaSchemeType") != null) {
            formulaSchemeType = (Integer)params.get("formulaSchemeType") - 1;
        }
        ObjectMapper mapper = new ObjectMapper();
        List formulaSchemes = new ArrayList();
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            formulaSchemes = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        }
        if (formulaSchemes != null) {
            try {
                int finalFormulaSchemeType = formulaSchemeType;
                return mapper.writeValueAsString(formulaSchemes.stream().filter(scheme -> scheme.getFormulaSchemeType().getValue() == finalFormulaSchemeType).map(FormulaSchemeObj::toFormulaSchemeObj).collect(Collectors.toList()));
            }
            catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848(\u8fc7\u6ee4EFDC\u53d6\u6570\u516c\u5f0f)")
    @RequestMapping(value={"/formula/scheme/query"}, method={RequestMethod.POST})
    @ResponseBody
    public List<FormulaSchemeDefine> queryFormula(@RequestBody Map<String, Object> params) {
        String formSchemeKey = (String)params.get("formSchemeId");
        ObjectMapper mapper = new ObjectMapper();
        List<Object> formulaSchemes = new ArrayList<FormulaSchemeDefine>();
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            formulaSchemes = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        }
        if (formulaSchemes != null) {
            return formulaSchemes;
        }
        return null;
    }

    @RequestMapping(value={"/reportFormParam"}, method={RequestMethod.GET})
    @ResponseBody
    public String getSelectFormParam() {
        StringBuffer result = new StringBuffer("");
        result.append("[{\"key\":\"all_form\",\"code\":\"JQTABDA\",\"title\":\"\u6240\u6709\u62a5\u8868\",\"canDesign\":true},{\"key\":\"choose_form\",\"code\":\"JQTABDB\",\"title\":\"\u9009\u62e9\u62a5\u8868\",\"canDesign\":true}]");
        return result.toString();
    }

    @RequestMapping(value={"/reportEntityParam"}, method={RequestMethod.GET})
    @ResponseBody
    public String getSchemeParam() {
        StringBuffer result = new StringBuffer("");
        result.append("[{\"key\":\"all_entity\",\"code\":\"JQTABDA\",\"title\":\"\u6240\u6709\u4e3b\u4f53\",\"canDesign\":true},{\"key\":\"choose_entity\",\"code\":\"JQTABDB\",\"title\":\"\u9009\u62e9\u4e3b\u4f53\",\"canDesign\":true}]");
        return result.toString();
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u7c7b\u578b")
    @RequestMapping(value={"formula/scheme/queryFormulaType"}, method={RequestMethod.POST})
    @ResponseBody
    public String queryFormulaType(@RequestBody Map<String, Object> params) {
        String taskKey = (String)params.get("taskKey");
        ObjectMapper mapper = new ObjectMapper();
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        try {
            ArrayList<FormulaSchemeTypeVO> formulaSchemeTypes = new ArrayList<FormulaSchemeTypeVO>();
            FormulaSchemeTypeVO formulaSchemeTypeReport = new FormulaSchemeTypeVO();
            formulaSchemeTypeReport.setTitle("\u8fd0\u7b97\u5ba1\u6838\u516c\u5f0f\uff08\u9ed8\u8ba4\uff09");
            formulaSchemeTypeReport.setType(FormulaSchemeTypeForFormulaManager.FORMULA_SCHEME_TYPE_REPORT.getValue());
            formulaSchemeTypes.add(formulaSchemeTypeReport);
            if (taskDefine == null || taskDefine != null && taskDefine.getEfdcSwitch()) {
                FormulaSchemeTypeVO formulaSchemeTypeEfdc = new FormulaSchemeTypeVO();
                formulaSchemeTypeEfdc.setTitle("\u8d22\u52a1\u63d0\u53d6\u516c\u5f0f");
                formulaSchemeTypeEfdc.setType(FormulaSchemeTypeForFormulaManager.FORMULA_SCHEME_TYPE_FINANCIAL.getValue());
                formulaSchemeTypes.add(formulaSchemeTypeEfdc);
            }
            return mapper.writeValueAsString(formulaSchemeTypes);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/runTimeReportTasks"}, method={RequestMethod.GET})
    @ResponseBody
    public String getRunTimeReportTasks() throws JQException {
        return this.reportTaskService.getRunTimeAllReportTasks();
    }

    @RequestMapping(value={"/reportTasksParam"}, method={RequestMethod.GET})
    @ResponseBody
    public String getAllReportTasksParam() {
        StringBuffer result = new StringBuffer("");
        result.append("[{\"key\":\"onlyTaskDesigner\",\"code\":\"JQTABDA\",\"title\":\"\u4efb\u52a1\u8bbe\u8ba1\",\"canDesign\":true},{\"key\":\"allTaskDesigner\",\"code\":\"JQTABDB\",\"title\":\"\u5168\u90e8\",\"canDesign\":true},{\"key\":\"allRunTime\",\"code\":\"JQTABDC\",\"title\":\"\u6570\u636e\u5f55\u5165\",\"canDesign\":true}]");
        return result.toString();
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u4efb\u52a1\u5206\u7ec4\u4fe1\u606f")
    @RequestMapping(value={"/allTaskGroupArr"}, method={RequestMethod.GET})
    public String getAllTaskGroupArr() throws JQException {
        return this.reportTaskService.getAllTaskGroupArr();
    }

    @ApiOperation(value="\u9012\u5f52\u83b7\u53d6\u67d0\u4e2a\u53ca\u5176\u5168\u90e8\u5b50\u5206\u7ec4\u4fe1\u606f")
    @RequestMapping(value={"/allChildTaskGroup/{taskGroupKey}"}, method={RequestMethod.GET})
    public List<ITree<TaskTreeObj>> getAllChildTaskGroup(@PathVariable(value="taskGroupKey") String taskGroupKey) throws Exception {
        return this.reportTaskService.getChildTaskTreeList(taskGroupKey);
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u6240\u5c5e\u5206\u7ec4")
    @RequestMapping(value={"getTaskGroupByTask/{taskKey}"}, method={RequestMethod.GET})
    public List<DesignTaskGroupDefine> getTaskGroupByTask(@PathVariable(value="taskKey") String taskKey) throws Exception {
        List<DesignTaskGroupDefine> taskGroupByTask = this.reportTaskService.getTaskGroupByTask(taskKey);
        if (null != taskGroupByTask && taskGroupByTask.size() != 0) {
            return taskGroupByTask;
        }
        return new ArrayList<DesignTaskGroupDefine>();
    }

    @ApiOperation(value="\u83b7\u53d6\u5168\u90e8\u4efb\u52a1\u6811\u6570\u636e")
    @RequestMapping(value={"/allReportTaskTree"}, method={RequestMethod.GET})
    public List<ITree<TaskTreeObj>> getAllTaskGroupData() {
        return this.reportTaskService.getTaskTreeList();
    }

    @ApiOperation(value="\u5237\u65b0\u4efb\u52a1\u5206\u7ec4\u6811")
    @RequestMapping(value={"taskGroupTree/reload"}, method={RequestMethod.POST})
    public ReturnObject taskGroupTreeReload(@RequestBody String taskGroup) throws IOException {
        ReturnObject ro = new ReturnObject();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(taskGroup);
            String groupKey = jsonNode.get("key").toString().replace("\"", "");
            this.reportTaskService.taskTreeListReload(groupKey, ro);
            ro.setSuccess(true);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ro.setSuccess(false);
        }
        return ro;
    }

    @ApiOperation(value="\u6784\u5efa\u4efb\u52a1\u6307\u5b9a\u5206\u7ec4\u7684\u5168\u90e8\u4efb\u52a1\u6811")
    @RequestMapping(value={"/allTaskGroupWithTaskCheck/{taskKey}"}, method={RequestMethod.GET})
    public List<ITree<TaskTreeObj>> getAllTaskGroupWithTaskCheck(@PathVariable(value="taskKey") String taskKey) {
        return this.reportTaskService.getTaskTreeListWithTaskCheck(taskKey);
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u5206\u7ec4\u83b7\u53d6\u4efb\u52a1\u5217\u8868")
    @GetMapping(value={"/taskListForGroup/{taskGroupKey}"})
    public List<UReportTaskL> getTaskListForGroup(@PathVariable(value="taskGroupKey") String taskGroupKey) throws JQException {
        try {
            return this.reportTaskService.getTaskListForGroup(taskGroupKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_037, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u5206\u7ec4\u9012\u5f52\u83b7\u53d6\u6240\u6709\u4efb\u52a1")
    @GetMapping(value={"/allTaskListForGroup/{taskGroupKey}"})
    public List<UReportTaskL> getAllTaskListForGroup(@PathVariable(value="taskGroupKey") String taskGroupKey) throws JQException {
        try {
            return this.reportTaskService.getAllTaskListForGroup(taskGroupKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_037);
        }
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u5206\u7ec4\u9012\u5f52\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @GetMapping(value={"/allRunTaskListForGroup/{taskGroupKey}"})
    public List<UReportTaskL> getAllRunTaskListForGroup(@PathVariable(value="taskGroupKey") String taskGroupKey) throws JQException {
        try {
            return this.reportTaskService.getAllRunTaskListForGroup(taskGroupKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_037);
        }
    }

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u5206\u7ec4\u6240\u6709\u76f4\u63a5\u8fd0\u884c\u671f\u4efb\u52a1")
    @GetMapping(value={"/runTaskListForGroup/{taskGroupKey}"})
    public List<UReportTaskL> getRunTaskListForGroup(@PathVariable(value="taskGroupKey") String taskGroupKey) throws JQException {
        try {
            return this.reportTaskService.getRunTaskListForGroup(taskGroupKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_037);
        }
    }

    @ApiOperation(value="\u4e3a\u4efb\u52a1\u8bbe\u7f6e\u5206\u7ec4")
    @RequestMapping(value={"/setGroupForTask"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:task_group:manage"})
    public ReturnObject setGroupForTask(@RequestBody String taskAndGroups) {
        ReturnObject ro = new ReturnObject();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(taskAndGroups);
            String taskID = jsonNode.get("taskID").toString().replace("\"", "");
            String groups = jsonNode.get("groups").toString();
            List groupsArr = JacksonUtils.toList((String)groups, String.class);
            this.reportTaskService.setGroupForTask(taskID, groupsArr);
            ro.setSuccess(true);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ro.setSuccess(false);
        }
        return ro;
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u5206\u7ec4\u4fe1\u606f")
    @RequestMapping(value={"/taskGroupData/{taskGroupKey}"}, method={RequestMethod.GET})
    public DesignTaskGroupDefine gettaskGroupData(@PathVariable(value="taskGroupKey") String taskGroupKey) {
        return this.nrDesignTimeController.queryTaskGroupDefine(taskGroupKey);
    }

    @ApiOperation(value="\u5220\u9664\u4efb\u52a1\u5206\u7ec4")
    @RequestMapping(value={"taskGroup/delete/{taskGroupKey}"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:task_group:manage"})
    public ReturnObject taskGroupDelete(@PathVariable(value="taskGroupKey") String taskGroupKey) {
        ReturnObject ro = new ReturnObject();
        try {
            this.reportTaskService.deleteTaskGroup(taskGroupKey);
            ro.setSuccess(true);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ro.setSuccess(false);
            ro.setMessage(e.getMessage());
        }
        return ro;
    }

    @ApiOperation(value="\u4ea4\u6362\u4efb\u52a1\u5206\u7ec4\u987a\u5e8f")
    @RequestMapping(value={"taskGroup/changeOrder"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:task_group:manage"})
    public ReturnObject changeTaskGroupOrder(@RequestBody Map<String, Object> param) {
        ReturnObject ro = new ReturnObject();
        String source = (String)param.get("source");
        int way = (Integer)param.get("way");
        try {
            this.reportTaskService.changeTaskGroupOrder(source, way);
            ro.setSuccess(true);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ro.setSuccess(false);
            ro.setMessage(e.getMessage());
        }
        return ro;
    }

    @ApiOperation(value="\u66f4\u65b0\u4efb\u52a1\u5206\u7ec4\u4fe1\u606f")
    @RequestMapping(value={"taskGroup/update"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:task_group:manage"})
    public ReturnObject taskGroupUpdate(@RequestBody String taskGroup) throws IOException, JQException {
        ReturnObject ro = new ReturnObject();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(taskGroup);
        String groupKey = jsonNode.get("groupKey").toString().replace("\"", "");
        String groupTitle = jsonNode.get("groupTitle").toString().replace("\"", "");
        String parentKey = jsonNode.get("parentKey").toString().replace("\"", "");
        String groupDescription = jsonNode.get("groupDescription").toString().replace("\"", "");
        return this.reportTaskService.updateTaskGroup(groupKey, groupTitle, parentKey, groupDescription, ro);
    }

    @ApiOperation(value="\u65b0\u589e\u4efb\u52a1\u5206\u7ec4")
    @RequestMapping(value={"taskGroup/insert"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:task_group:manage"})
    public ReturnObject taskGroupInsert(@RequestBody String taskGroup) throws IOException, JQException {
        ReturnObject ro = new ReturnObject();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(taskGroup);
        String groupTitle = jsonNode.get("groupTitle").toString().replace("\"", "");
        String parentKey = jsonNode.get("parentKey").toString().replace("\"", "");
        String groupDescription = jsonNode.get("groupDescription").toString().replace("\"", "");
        return this.reportTaskService.insertTaskGroup(groupTitle, parentKey, groupDescription, ro);
    }

    @RequestMapping(value={"/reportAddType"}, method={RequestMethod.GET})
    @ResponseBody
    public String getReportAddType() {
        StringBuffer result = new StringBuffer("");
        result.append("[{\"key\":\"allButton\",\"code\":\"JQTABDD\",\"title\":\"\u5168\u90e8\",\"canDesign\":true},{\"key\":\"survy\",\"code\":\"JQTABDE\",\"title\":\"\u95ee\u5377\",\"canDesign\":true},{\"key\":\"table\",\"code\":\"JQTABDF\",\"title\":\"\u8868\u683c\",\"canDesign\":true}]");
        return result.toString();
    }
}

