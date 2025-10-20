/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.gcreport.mobile.approval.client.MobileTodoClient
 *  com.jiuqi.gcreport.mobile.approval.vo.MobileTodoParamInfo
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.dataentry.asynctask.WorkflowAsyncTaskExecutor
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.task.dto.TaskDTO
 *  com.jiuqi.nr.task.service.ITaskService
 *  com.jiuqi.nr.todo.entity.TodoVO
 *  com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem
 *  com.jiuqi.nr.workflow2.todo.service.TodoExtendQueryService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.todocat.TodoCatInfoDO
 *  com.jiuqi.va.domain.todocat.TodoCatParamDTO
 *  com.jiuqi.va.domain.todocat.TodoCatQueryDTO
 *  com.jiuqi.va.todo.service.todocat.VaTodoCatConfigService
 *  com.jiuqi.va.todo.service.todocat.VaTodoCatInfoService
 *  com.jiuqi.va.todo.service.todocat.VaTodoCatService
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.mobile.approval.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.gcreport.mobile.approval.client.MobileTodoClient;
import com.jiuqi.gcreport.mobile.approval.service.MobileTodoService;
import com.jiuqi.gcreport.mobile.approval.vo.MobileTodoParamInfo;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.asynctask.WorkflowAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.task.dto.TaskDTO;
import com.jiuqi.nr.task.service.ITaskService;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.service.TodoExtendQueryService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.todocat.TodoCatInfoDO;
import com.jiuqi.va.domain.todocat.TodoCatParamDTO;
import com.jiuqi.va.domain.todocat.TodoCatQueryDTO;
import com.jiuqi.va.todo.service.todocat.VaTodoCatConfigService;
import com.jiuqi.va.todo.service.todocat.VaTodoCatInfoService;
import com.jiuqi.va.todo.service.todocat.VaTodoCatService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class MobileTodoController
implements MobileTodoClient {
    private static final Logger logger = LoggerFactory.getLogger(MobileTodoController.class);
    @Autowired
    private MobileTodoService mobileTodoService;
    @Autowired
    private TodoExtendQueryService todoExtendQueryService;
    @Autowired
    private VaTodoCatInfoService vaTodoCatInfoService;
    @Autowired
    private VaTodoCatConfigService vaTodoCatConfigService;
    @Autowired
    private VaTodoCatService vaTodoCatService;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private ITaskService iTaskService;

    public TodoVO queryTodoDataInfo(MobileTodoParamInfo mobileTodoParamInfo) {
        return this.mobileTodoService.queryTodoDataInfo(mobileTodoParamInfo);
    }

    public List<PeriodItem> queryTodoDataInfo(String taskKey) {
        return this.todoExtendQueryService.getPeriodsWithTodo(taskKey);
    }

    public List<Map<String, Object>> queryMobileTodoAllowTaskInfo() {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        try {
            TodoCatInfoDO todoCatInfoDO = new TodoCatInfoDO();
            R listTodoCatInfoDO = this.vaTodoCatInfoService.listTodoCatInfoDO(todoCatInfoDO);
            if (listTodoCatInfoDO.get((Object)"data") == null) {
                return returnList;
            }
            List data = (List)listTodoCatInfoDO.get((Object)"data");
            if (CollectionUtils.isEmpty(data)) {
                return returnList;
            }
            returnList = data.stream().map(infoDO -> {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", infoDO.getId());
                map.put("taskTitle", infoDO.getTitle());
                return map;
            }).collect(Collectors.toList());
            List idList = data.stream().map(infoDO -> infoDO.getId().toString()).collect(Collectors.toList());
            TodoCatQueryDTO todoCatQueryDTO = new TodoCatQueryDTO();
            todoCatQueryDTO.setIds(idList);
            Map countMap = this.vaTodoCatService.batchCountTodoData(todoCatQueryDTO);
            returnList.forEach(item -> {
                TodoCatParamDTO todoCatParamDTO = new TodoCatParamDTO();
                todoCatParamDTO.setId(item.get("id").toString());
                todoCatParamDTO.setName(item.get("taskTitle").toString());
                R getTodoCatConfigDTO = this.vaTodoCatConfigService.getTodoCatConfigDTO(todoCatParamDTO);
                Object dataObject = getTodoCatConfigDTO.get((Object)"data");
                if (dataObject != null) {
                    if (LinkedHashMap.class.equals(dataObject.getClass())) {
                        Map extInfo = (Map)dataObject;
                        item.put("taskId", extInfo.get("taskId"));
                        item.put("count", countMap.get(item.get("id").toString()));
                    } else {
                        logger.info("\u5f85\u529e\u914d\u7f6e\u6570\u636e\u683c\u5f0f\u8f93\u51fa\uff1a" + dataObject.getClass());
                    }
                }
            });
            List taskItemVOList = this.iTaskService.queryTask("00000000-0000-0000-0000-000000000000");
            List taskIdList = taskItemVOList.stream().map(TaskDTO::getKey).collect(Collectors.toList());
            returnList = returnList.stream().filter(item -> item.containsKey("taskId") && taskIdList.contains((String)item.get("taskId")) && item.containsKey("count") && (Integer)item.get("count") > 0).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f85\u529e\u4efb\u52a1\u5931\u8d25", e);
        }
        return returnList;
    }

    @NRContextBuild
    public AsyncTaskInfo executeTask(@RequestBody ExecuteTaskParam param) {
        DsContext context = DsContextHolder.getDsContext();
        DsContextImpl dsContext = (DsContextImpl)context;
        dsContext.setEntityId(param.getContextEntityId());
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new WorkflowAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

