/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodLanguage
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.bean.AsyncCalcTaskDetail;
import com.jiuqi.nr.dataentry.bean.AsyncDatasumTaskDetail;
import com.jiuqi.nr.dataentry.bean.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.ReportImportResultObject;
import com.jiuqi.nr.dataentry.paramInfo.AllCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.service.IAsyncExpService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry/async-progress"})
public class AsyncProgressController {
    private static final Logger log = LoggerFactory.getLogger(AsyncProgressController.class);
    @Autowired
    AsyncTaskManager asyncTaskManager;
    @Autowired
    AsyncTaskDao asyncTaskDao;
    @Autowired(required=false)
    private List<IAsyncExpService> iAsyncExpServices;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IFormulaRunTimeController formulaController;

    @GetMapping
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u5f55\u5165\u5f02\u6b65\u4efb\u52a1", notes="\u67e5\u8be2\u6240\u6709\u5f55\u5165\u5f02\u6b65\u4efb\u52a1")
    @NRContextBuild
    public List<AsyncTaskInfo> queryDataentryTaskToDo(String taskKey, @RequestParam(value="showAsyncTaskTypeList", required=false) List<String> showAsyncTaskTypeList, @ModelAttribute NRContext nrContext) {
        ArrayList<AsyncTaskInfo> asyncTaskInfos = new ArrayList<AsyncTaskInfo>();
        if (StringUtils.isEmpty((String)taskKey)) {
            return asyncTaskInfos;
        }
        try {
            this.checkTaskKeyLegal(taskKey);
        }
        catch (Exception e) {
            log.error("\u5f02\u6b65\u4efb\u52a1ID\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\uff0c\u6821\u9a8c\u672a\u901a\u8fc7\uff1a{}", (Object)e.getMessage(), (Object)e);
            return asyncTaskInfos;
        }
        ArrayList<String> taskPoolTypes = new ArrayList<String>();
        if (showAsyncTaskTypeList != null) {
            taskPoolTypes.addAll(showAsyncTaskTypeList);
        } else {
            if (this.iAsyncExpServices != null) {
                for (IAsyncExpService iAsyncExpService : this.iAsyncExpServices) {
                    List<AsyncTask> expAsyncTasks = iAsyncExpService.getExpAsyncTasks(taskKey);
                    if (expAsyncTasks == null || expAsyncTasks.size() <= 0) continue;
                    List expTaskPoolTypes = expAsyncTasks.stream().map(AsyncTask::getTaskPoolType).collect(Collectors.toList());
                    taskPoolTypes.addAll(expTaskPoolTypes);
                }
            }
            String[] taskPoolTypesStringList = new String[]{"ASYNCTASK_BATCHCHECK", "ASYNCTASK_BATCHCALCULATE", "ASYNCTASK_BATCHEXPORT", "ASYNCTASK_ANALYSIS_BATCHANALYSIS", "ASYNCTASK_BATCHCLEAR", "ASYNCTASK_BATCHUPLOAD", "ASYNCTASK_BATCHDATASUM", "ASYNCTASK_EFDC", "ASYNCTASK_BATCHEFDC", "ASYNCTASK_ETL", "ASYNCTASK_NODECHECK", "ASYNCTASK_UPLOADFILE", "GC_FETCH"};
            taskPoolTypes.addAll(Arrays.asList(taskPoolTypesStringList));
        }
        List queryTaskToDo = this.asyncTaskDao.queryTaskToDoWithoutClob(taskKey, taskPoolTypes);
        ArrayList<Object> dataentryAsyncTask = new ArrayList<Object>();
        HashMap<String, AsyncTask> map = new HashMap<String, AsyncTask>();
        block23: for (AsyncTask asyncTask : queryTaskToDo) {
            if (showAsyncTaskTypeList != null && !showAsyncTaskTypeList.contains(asyncTask.getTaskPoolType())) continue;
            switch (asyncTask.getTaskPoolType()) {
                case "ASYNCTASK_BATCHCHECK": 
                case "ASYNCTASK_BATCHEXPORT": {
                    dataentryAsyncTask.add(asyncTask);
                    continue block23;
                }
                case "ASYNCTASK_ANALYSIS_BATCHANALYSIS": 
                case "ASYNCTASK_BATCHCALCULATE": 
                case "ASYNCTASK_BATCHCLEAR": 
                case "ASYNCTASK_BATCHCOPY": 
                case "ASYNCTASK_BATCHUPLOAD": 
                case "ASYNCTASK_BATCHDATASUM": 
                case "ASYNCTASK_EFDC": 
                case "ASYNCTASK_BATCHEFDC": 
                case "ASYNCTASK_ETL": 
                case "ASYNCTASK_NODECHECK": 
                case "ASYNCTASK_UPLOADFILE": 
                case "GC_FETCH": {
                    if (asyncTask.getState() != TaskState.FINISHED && asyncTask.getState() != TaskState.ERROR) {
                        dataentryAsyncTask.add(asyncTask);
                        continue block23;
                    }
                    map.put(asyncTask.getTaskPoolType(), asyncTask);
                    continue block23;
                }
            }
            dataentryAsyncTask.add(asyncTask);
        }
        for (Map.Entry entry : map.entrySet()) {
            dataentryAsyncTask.add(entry.getValue());
        }
        dataentryAsyncTask.sort(Comparator.comparing(AsyncTask::getCreateTime).reversed());
        for (AsyncTask asyncTask : dataentryAsyncTask) {
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(asyncTask.getTaskId());
            asyncTaskInfo.setTaskPoolType(asyncTask.getTaskPoolType());
            asyncTaskInfo.setState(asyncTask.getState());
            asyncTaskInfo.setProcess(asyncTask.getProcess() * 100.0);
            asyncTaskInfo.setResult(asyncTask.getResult());
            asyncTaskInfo.setCreateTime(asyncTask.getCreateTime());
            asyncTaskInfo.setArgs(null);
            if (asyncTaskInfos.size() >= 20) break;
            asyncTaskInfos.add(asyncTaskInfo);
        }
        Collections.reverse(asyncTaskInfos);
        return asyncTaskInfos;
    }

    @GetMapping(value={"/getCancelableTasks"})
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @NRContextBuild
    public List<RealTimeJobBean> getCancelableTasks() {
        return this.asyncTaskManager.getCancelableTasks();
    }

    @GetMapping(value={"/completeAllAsynctasks"})
    @NRContextBuild
    public void completeAllAsynctasks(String taskKey) {
        try {
            this.checkTaskKeyLegal(taskKey);
        }
        catch (Exception e) {
            log.error("\u5f02\u6b65\u4efb\u52a1ID\u5b58\u5728\u975e\u6cd5\u5b57\u7b26\uff0c\u6821\u9a8c\u672a\u901a\u8fc7\uff1a{}", (Object)e.getMessage(), (Object)e);
            return;
        }
        ArrayList<Object> taskPoolTypes = new ArrayList<Object>();
        if (this.iAsyncExpServices != null) {
            for (IAsyncExpService iAsyncExpService : this.iAsyncExpServices) {
                List<AsyncTask> expAsyncTasks = iAsyncExpService.getExpAsyncTasks(taskKey);
                if (expAsyncTasks == null || expAsyncTasks.size() <= 0) continue;
                List expTaskPoolTypes = expAsyncTasks.stream().map(AsyncTask::getTaskPoolType).collect(Collectors.toList());
                taskPoolTypes.addAll(expTaskPoolTypes);
            }
        }
        String[] taskPoolTypesStringList = new String[]{"ASYNCTASK_BATCHCHECK", "ASYNCTASK_BATCHCALCULATE", "ASYNCTASK_BATCHEXPORT", "ASYNCTASK_ANALYSIS_BATCHANALYSIS", "ASYNCTASK_BATCHCLEAR", "ASYNCTASK_BATCHUPLOAD", "ASYNCTASK_BATCHDATASUM", "ASYNCTASK_EFDC", "ASYNCTASK_BATCHEFDC", "ASYNCTASK_ETL", "ASYNCTASK_NODECHECK", "ASYNCTASK_UPLOADFILE", "GC_FETCH"};
        taskPoolTypes.addAll(Arrays.asList(taskPoolTypesStringList));
        List asyncTaskList = this.asyncTaskDao.queryTaskToDoWithoutClob(taskKey, taskPoolTypes);
        for (AsyncTask asyncTask : asyncTaskList) {
            if (asyncTask.getState().getValue() == 0 || asyncTask.getState().getValue() == 1 || asyncTask.getState().getValue() == 3) continue;
            this.asyncTaskManager.completeTask(asyncTask.getTaskId());
        }
    }

    @CrossOrigin(value={"*"})
    @PostMapping(value={"/{taskId}"})
    @ApiOperation(value="\u5b8c\u6210\u5f55\u5165\u5f02\u6b65\u4efb\u52a1", notes="\u5b8c\u6210\u5f55\u5165\u5f02\u6b65\u4efb\u52a1")
    @NRContextBuild
    public void completeAsyncTask(@PathVariable(value="taskId") String taskId, @ModelAttribute NRContext nrContext) {
        this.asyncTaskManager.completeTask(taskId);
    }

    @JLoggable(value="\u83b7\u53d6\u5f02\u6b65\u4efb\u52a1\u53c2\u6570")
    @PostMapping(value={"/getTaskArgs"})
    @ApiOperation(value="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1args", notes="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1args")
    @NRContextBuild
    public Object getTaskArgs(@Valid @RequestBody com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo asyncTaskInfo) {
        String args = this.asyncTaskManager.queryArgs(asyncTaskInfo.getId());
        Object deserialize = SimpleParamConverter.SerializationUtils.deserialize((String)args);
        return deserialize;
    }

    @JLoggable(value="\u83b7\u53d6\u5f02\u6b65\u4efb\u52a1detail")
    @PostMapping(value={"/getTaskDetail"})
    @ApiOperation(value="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1detail", notes="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1detail")
    @NRContextBuild
    public Object getTaskDetail(@Valid @RequestBody com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo asyncTaskInfo) {
        return this.asyncTaskManager.queryDetail(asyncTaskInfo.getId());
    }

    @JLoggable(value="\u83b7\u53d6JIO\u5bfc\u5165\u5f02\u6b65\u4efb\u52a1detail")
    @PostMapping(value={"/getJioImportTaskDetail"})
    @ApiOperation(value="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1detail", notes="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1detail")
    @NRContextBuild
    public Object getJioImportTaskDetail(@Valid @RequestBody AllCheckInfo allCheckInfo) {
        Object object = this.asyncTaskManager.queryDetail(allCheckInfo.getAsyncTaskKey());
        if (object instanceof String) {
            try {
                JSONObject jsonObject = new JSONObject((String)object);
                if (allCheckInfo.getPagerInfo().getOffset() == 0) {
                    JSONArray messageType = null;
                    try {
                        messageType = jsonObject.getJSONArray("errorUnits");
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<JSONObject> list = new ArrayList<JSONObject>();
                    for (int i = 0; i < messageType.length(); ++i) {
                        try {
                            list.add(messageType.getJSONObject(i));
                            continue;
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Object> messages = new ArrayList();
                    if (list.size() > 0) {
                        messages = list.stream().map(json -> json.optString("message", null)).filter(type -> type != null && StringUtils.isNotEmpty((String)type)).distinct().collect(Collectors.toList());
                    }
                    messages = messages.stream().sorted((o1, o2) -> o1.compareTo((String)o2)).collect(Collectors.toList());
                    HashMap<String, Long> map = new HashMap<String, Long>();
                    for (String string : messages) {
                        map.put(string, list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals(message)).count());
                    }
                    long errUnitsOfFormCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString() == null || errUnitsOfForm.get("message").toString().equals("") || errUnitsOfForm.get("message").toString().equals("null")).count();
                    if (errUnitsOfFormCount > 0L) {
                        map.put("\u5176\u4ed6\u9519\u8bef", errUnitsOfFormCount);
                        messages.add("\u5176\u4ed6\u9519\u8bef");
                    }
                    long unitsOfNotFoundCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("\u5355\u4f4d\u5339\u914d\u5931\u8d25")).count();
                    long unitsIsUploadCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("\u5355\u4f4d\u5df2\u7ecf\u4e0a\u62a5")).count();
                    long unitsNoPermissionCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("\u5355\u4f4d\u6ca1\u6709\u7f16\u8f91\u6743\u9650")).count();
                    long unitsActionStateErrorCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("\u5f53\u524d\u5355\u4f4d\u72b6\u6001\u4e0d\u5141\u8bb8\u5bfc\u5165")).count();
                    long unitsNotExitCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("\u7cfb\u7edf\u4e0d\u5b58\u5728\u7684\u5355\u4f4d")).count();
                    long jio1ErrorCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("JIO\u6587\u4ef6\u4e2d\u7236\u8282\u70b9\u4e0e\u7cfb\u7edf\u7236\u8282\u70b9\u4e0d\u4e00\u81f4\u7684\u5355\u4f4d")).count();
                    long jio2ErrorCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("JIO\u6587\u4ef6\u4e2d\u4e0d\u5b58\u5728\u7684\u5355\u4f4d")).count();
                    long jio3ErrorCount = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("JIO\u6587\u4ef6\u4e2d\u4e3b\u4ee3\u7801\u4e0e\u6784\u6210\u5b57\u6bb5\u4e0d\u4e00\u81f4\u7684\u5355\u4f4d")).count();
                    long endlessChain = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u73af\u94fe")).count();
                    long repeatCode = list.stream().filter(errUnitsOfForm -> errUnitsOfForm.get("message").toString().equals("JIO\u6587\u4ef6\u4e2d\u5b58\u5728\u91cd\u7801\u5355\u4f4d")).count();
                    jsonObject.put("errUnitsOfFormCount", errUnitsOfFormCount);
                    jsonObject.put("unitsOfNotFoundCount", unitsOfNotFoundCount);
                    jsonObject.put("unitsIsUploadCount", unitsIsUploadCount);
                    jsonObject.put("unitsNoPermissionCount", unitsNoPermissionCount);
                    jsonObject.put("unitsActionStateErrorCount", unitsActionStateErrorCount);
                    jsonObject.put("unitsNotExitCount", unitsNotExitCount);
                    jsonObject.put("jio1ErrorCount", jio1ErrorCount);
                    jsonObject.put("jio2ErrorCount", jio2ErrorCount);
                    jsonObject.put("jio3ErrorCount", jio3ErrorCount);
                    jsonObject.put("endlessChainCount", endlessChain);
                    jsonObject.put("repeatCodeCount", repeatCode);
                    jsonObject.put("errorConut", map);
                    jsonObject.put("errorMessage", messages);
                }
                JSONArray errorUnits = jsonObject.getJSONArray("errorUnits");
                JSONArray subArray = new JSONArray();
                PagerInfo pagerInfo = allCheckInfo.getPagerInfo();
                for (int i = pagerInfo.getOffset() * pagerInfo.getLimit(); i < (pagerInfo.getOffset() + 1) * pagerInfo.getLimit() && i < errorUnits.length(); ++i) {
                    try {
                        subArray.put(errorUnits.get(i));
                        continue;
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonObject.put("errorUnits", (Object)subArray);
                return jsonObject.toMap();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (object != null) {
            ReportImportResultObject reportImportResultObject = (ReportImportResultObject)object;
            if (reportImportResultObject.getErrorUnits().size() > 1000) {
                reportImportResultObject.setErrorUnits(reportImportResultObject.getErrorUnits().subList(0, 1000));
            }
            return reportImportResultObject;
        }
        return object;
    }

    @JLoggable(value="\u83b7\u53d6\u8fd0\u7b97\u53c2\u6570\u8be6\u60c5")
    @PostMapping(value={"/getCalcAsyncTaskArgDetail"})
    @ApiOperation(value="\u83b7\u53d6\u8fd0\u7b97\u53c2\u6570\u8be6\u60c5")
    public AsyncCalcTaskDetail getCalcAsyncTaskArgDetail(@RequestBody @Valid BatchCalculateInfo batchCalculateInfo) {
        AsyncCalcTaskDetail asyncTaskDetail = new AsyncCalcTaskDetail();
        JtableContext context = batchCalculateInfo.getContext();
        if (!context.getFormKey().equals("all") && batchCalculateInfo.getFormulas().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : batchCalculateInfo.getFormulas().entrySet()) {
                stringBuilder.append(entry.getKey() + ";");
            }
            stringBuilder = stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            context.setFormKey(stringBuilder.toString());
        }
        List formulaSchemeDefines = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(context.getFormSchemeKey());
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(context.getFormSchemeKey());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        PeriodWrapper periodWrapper = new PeriodWrapper(((DimensionValue)batchCalculateInfo.getContext().getDimensionSet().get(dataTimeEntity.getDimensionName())).getValue());
        if (DataEntryUtil.isChinese()) {
            periodWrapper.setLanguage(PeriodLanguage.Chinese);
        } else {
            periodWrapper.setLanguage(PeriodLanguage.English);
        }
        asyncTaskDetail.setDataTimeTitle(periodProvider.getPeriodTitle(periodWrapper));
        if (batchCalculateInfo.getFormulaSchemeKey() != null && !batchCalculateInfo.getFormulaSchemeKey().equals("")) {
            ArrayList<String> fromMulaTitles = new ArrayList<String>();
            List<String> formMulaKeys = Arrays.asList(batchCalculateInfo.getFormulaSchemeKey().split(";"));
            for (Object formulaSchemeDefine : formulaSchemeDefines) {
                if (!formMulaKeys.contains(formulaSchemeDefine.getKey())) continue;
                fromMulaTitles.add(formulaSchemeDefine.getTitle());
            }
            asyncTaskDetail.setFormulaNames(fromMulaTitles);
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        asyncTaskDetail.setDwName(dwEntity.getTitle());
        ArrayList<Map<String, String>> dwList = new ArrayList<Map<String, String>>();
        if (!((DimensionValue)context.getDimensionSet().get(dwEntity.getDimensionName())).getValue().equals("")) {
            List<IEntityRow> entityDataList = this.getEntityDataList(context.getFormSchemeKey(), context.getDimensionSet(), dwEntity.getEntityViewDefine().getEntityId());
            for (IEntityRow iEntityRow : entityDataList) {
                HashMap<String, String> dwMap = new HashMap<String, String>();
                dwMap.put("code", iEntityRow.getCode());
                dwMap.put("name", iEntityRow.getTitle());
                dwList.add(dwMap);
            }
        }
        asyncTaskDetail.setDwList(dwList);
        ArrayList formDefines = new ArrayList();
        List queryRootGroupsByFormScheme = this.iRunTimeViewController.queryRootGroupsByFormScheme(context.getFormSchemeKey());
        for (FormGroupDefine formGroupDefine : queryRootGroupsByFormScheme) {
            Object allFormsInGroup = null;
            try {
                allFormsInGroup = this.iRunTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (allFormsInGroup == null) continue;
            formDefines.addAll(allFormsInGroup);
        }
        ArrayList<FormDefine> formList = new ArrayList<FormDefine>();
        if (context.getFormKey() != null && !context.getFormKey().equals("") && !context.getFormKey().equals("all")) {
            List<String> formKeys = Arrays.asList(context.getFormKey().split(";"));
            for (FormDefine formDefine : formDefines) {
                if (!formKeys.contains(formDefine.getKey())) continue;
                formList.add(formDefine);
            }
        }
        asyncTaskDetail.setFormList(formList);
        List dimEntityList = this.jtableParamService.getDimEntityList(context.getFormSchemeKey());
        ArrayList<Map<String, Object>> dimensionNames = new ArrayList<Map<String, Object>>();
        asyncTaskDetail.setDimensionNames(dimensionNames);
        for (Map.Entry entry : context.getDimensionSet().entrySet()) {
            HashMap<String, String> ientityMap;
            if (((String)entry.getKey()).equals(dataTimeEntity.getDimensionName()) || ((String)entry.getKey()).equals("ADJUST")) continue;
            DimensionValue dimensionValue = (DimensionValue)entry.getValue();
            String dimensionName = (String)entry.getKey();
            List dimEntityListFilter = dimEntityList.stream().filter(item -> item.getDimensionName().equals(dimensionName)).collect(Collectors.toList());
            if (dimEntityListFilter.size() <= 0 || !((EntityViewData)dimEntityListFilter.get(0)).isShowDimEntity()) continue;
            HashMap<String, Object> dimensionLoopMap = new HashMap<String, Object>();
            dimensionLoopMap.put("name", ((EntityViewData)dimEntityListFilter.get(0)).getTitle());
            dimensionNames.add(dimensionLoopMap);
            ArrayList list = new ArrayList();
            dimensionLoopMap.put("value", list);
            if (dimensionValue.getValue() == "") continue;
            if (dimensionValue.getValue().equals("PROVIDER_PBASECURRENCY")) {
                ientityMap = new HashMap<String, String>();
                ientityMap.put("code", "PROVIDER_PBASECURRENCY");
                ientityMap.put("name", "\u4e0a\u7ea7\u672c\u4f4d\u5e01");
                list.add(ientityMap);
                continue;
            }
            if (dimensionValue.getValue().equals("PROVIDER_BASECURRENCY")) {
                ientityMap = new HashMap();
                ientityMap.put("code", "PROVIDER_BASECURRENCY");
                ientityMap.put("name", "\u672c\u4f4d\u5e01");
                list.add(ientityMap);
                continue;
            }
            List<IEntityRow> entityDataList = this.getEntityDataList(context.getFormSchemeKey(), context.getDimensionSet(), ((EntityViewData)dimEntityListFilter.get(0)).getEntityViewDefine().getEntityId());
            for (IEntityRow iEntityRow : entityDataList) {
                HashMap<String, String> ientityMap2 = new HashMap<String, String>();
                ientityMap2.put("code", iEntityRow.getCode());
                ientityMap2.put("name", iEntityRow.getTitle());
                list.add(ientityMap2);
            }
        }
        return asyncTaskDetail;
    }

    @JLoggable(value="\u83b7\u53d6\u6c47\u603b\u53c2\u6570\u8be6\u60c5")
    @PostMapping(value={"/getDatasumAsyncTaskArgDetail"})
    @ApiOperation(value="\u83b7\u53d6\u6c47\u603b\u53c2\u6570\u8be6\u60c5")
    public AsyncDatasumTaskDetail getDatasumAsyncTaskArgDetail(@RequestBody @Valid BatchDataSumInfo batchDataSumInfo) {
        AsyncDatasumTaskDetail asyncDatasumTaskDetail = new AsyncDatasumTaskDetail();
        JtableContext context = batchDataSumInfo.getContext();
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(context.getFormSchemeKey());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        PeriodWrapper periodWrapper = new PeriodWrapper(((DimensionValue)batchDataSumInfo.getContext().getDimensionSet().get(dataTimeEntity.getDimensionName())).getValue());
        if (DataEntryUtil.isChinese()) {
            periodWrapper.setLanguage(PeriodLanguage.Chinese);
        } else {
            periodWrapper.setLanguage(PeriodLanguage.English);
        }
        asyncDatasumTaskDetail.setDataTimeTitle(periodProvider.getPeriodTitle(periodWrapper));
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        asyncDatasumTaskDetail.setDwName(dwEntity.getTitle());
        List<IEntityRow> dwEntityList = this.getEntityDataList(context.getFormSchemeKey(), context.getDimensionSet(), dwEntity.getEntityViewDefine().getEntityId());
        asyncDatasumTaskDetail.setTargetDwName(dwEntityList.get(0).getTitle());
        if (batchDataSumInfo.getSourceKeys().size() > 0) {
            ((DimensionValue)context.getDimensionSet().get(dwEntity.getDimensionName())).setValue(String.join((CharSequence)";", batchDataSumInfo.getSourceKeys()));
            ArrayList<Map<String, String>> dwList = new ArrayList<Map<String, String>>();
            if (!((DimensionValue)context.getDimensionSet().get(dwEntity.getDimensionName())).getValue().equals("")) {
                List<IEntityRow> entityDataList = this.getEntityDataList(context.getFormSchemeKey(), context.getDimensionSet(), dwEntity.getEntityViewDefine().getEntityId());
                for (IEntityRow iEntityRow : entityDataList) {
                    HashMap<String, String> dwMap = new HashMap<String, String>();
                    dwMap.put("code", iEntityRow.getCode());
                    dwMap.put("name", iEntityRow.getTitle());
                    dwList.add(dwMap);
                }
            }
            asyncDatasumTaskDetail.setDwList(dwList);
        } else if (batchDataSumInfo.isRecursive()) {
            asyncDatasumTaskDetail.setSumRange("\u6240\u6709\u4e0b\u7ea7");
        } else {
            asyncDatasumTaskDetail.setSumRange("\u76f4\u63a5\u4e0b\u7ea7");
        }
        ArrayList formDefines = new ArrayList();
        List queryRootGroupsByFormScheme = this.iRunTimeViewController.queryRootGroupsByFormScheme(context.getFormSchemeKey());
        for (FormGroupDefine formGroupDefine : queryRootGroupsByFormScheme) {
            Object allFormsInGroup = null;
            try {
                allFormsInGroup = this.iRunTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (allFormsInGroup == null) continue;
            formDefines.addAll(allFormsInGroup);
        }
        ArrayList<FormDefine> formList = new ArrayList<FormDefine>();
        if (batchDataSumInfo.getFormKeys() != null && !batchDataSumInfo.getFormKeys().equals("") && !batchDataSumInfo.getFormKeys().equals("all")) {
            List<String> formKeys = Arrays.asList(batchDataSumInfo.getFormKeys().split(";"));
            for (FormDefine formDefine : formDefines) {
                if (!formKeys.contains(formDefine.getKey())) continue;
                formList.add(formDefine);
            }
        }
        asyncDatasumTaskDetail.setFormList(formList);
        List dimEntityList = this.jtableParamService.getDimEntityList(context.getFormSchemeKey());
        ArrayList<Map<String, Object>> dimensionNames = new ArrayList<Map<String, Object>>();
        asyncDatasumTaskDetail.setDimensionNames(dimensionNames);
        for (Map.Entry entry : context.getDimensionSet().entrySet()) {
            HashMap<String, String> ientityMap;
            if (((String)entry.getKey()).equals(dataTimeEntity.getDimensionName()) || ((String)entry.getKey()).equals("ADJUST")) continue;
            DimensionValue dimensionValue = (DimensionValue)entry.getValue();
            String dimensionName = (String)entry.getKey();
            List dimEntityListFilter = dimEntityList.stream().filter(item -> item.getDimensionName().equals(dimensionName)).collect(Collectors.toList());
            if (dimEntityListFilter.size() <= 0 || !((EntityViewData)dimEntityListFilter.get(0)).isShowDimEntity()) continue;
            HashMap<String, Object> dimensionLoopMap = new HashMap<String, Object>();
            dimensionLoopMap.put("name", ((EntityViewData)dimEntityListFilter.get(0)).getTitle());
            dimensionNames.add(dimensionLoopMap);
            ArrayList list = new ArrayList();
            dimensionLoopMap.put("value", list);
            if (dimensionValue.getValue() == "") continue;
            if (dimensionValue.getValue().equals("PROVIDER_PBASECURRENCY")) {
                ientityMap = new HashMap<String, String>();
                ientityMap.put("code", "PROVIDER_PBASECURRENCY");
                ientityMap.put("name", "\u4e0a\u7ea7\u672c\u4f4d\u5e01");
                list.add(ientityMap);
                continue;
            }
            if (dimensionValue.getValue().equals("PROVIDER_BASECURRENCY")) {
                ientityMap = new HashMap();
                ientityMap.put("code", "PROVIDER_BASECURRENCY");
                ientityMap.put("name", "\u672c\u4f4d\u5e01");
                list.add(ientityMap);
                continue;
            }
            List<IEntityRow> entityDataList = this.getEntityDataList(context.getFormSchemeKey(), context.getDimensionSet(), ((EntityViewData)dimEntityListFilter.get(0)).getEntityViewDefine().getEntityId());
            for (IEntityRow iEntityRow : entityDataList) {
                HashMap<String, String> dwMap = new HashMap<String, String>();
                dwMap.put("code", iEntityRow.getCode());
                dwMap.put("name", iEntityRow.getTitle());
                list.add(dwMap);
            }
        }
        return asyncDatasumTaskDetail;
    }

    private List<IEntityRow> getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet, String entityId) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
            EntityViewData entity = this.jtableParamService.getEntity(entityId);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueSet));
            EntityViewDefine entityViewDefine = entity.getEntityViewDefine();
            iEntityQuery.setEntityView(entityViewDefine);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            log.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (iEntityTable != null) {
            return iEntityTable.getAllRows();
        }
        return new ArrayList<IEntityRow>();
    }

    private IEntityTable getEntityTable(String entityId) {
        IEntityTable entityTable = null;
        try {
            EntityViewData entity = this.jtableParamService.getEntity(entityId);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            EntityViewDefine entityViewDefine = entity.getEntityViewDefine();
            iEntityQuery.setEntityView(entityViewDefine);
            entityTable = iEntityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
        }
        catch (Exception e) {
            log.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return entityTable;
    }

    private void checkTaskKeyLegal(String taskKey) {
        if (taskKey == null || taskKey.isEmpty()) {
            throw new IllegalArgumentException("taskKey\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String pattern = "^[0-9A-Za-z_-]*$";
        Pattern compiledPattern = Pattern.compile("^[0-9A-Za-z_-]*$");
        Matcher matcher = compiledPattern.matcher(taskKey);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("taskKey\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u5305\u542b\u975e\u6cd5\u5b57\u7b26\uff01");
        }
    }
}

