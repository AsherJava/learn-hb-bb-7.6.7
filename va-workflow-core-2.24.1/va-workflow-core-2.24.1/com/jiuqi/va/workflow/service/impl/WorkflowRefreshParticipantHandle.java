/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.workflow.BizType
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowOperation
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.workflow.BizType;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowOperation;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowRefreshParticipantHandle {
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private WorkflowSevice workflowSevice;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private BizTypeConfig bizTypeConfig;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String doRefreshParticipant(String processInstanceId) {
        String errorMsg = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProcessId(processInstanceId);
        taskDTO.setBackendRequest(true);
        List tasks = this.todoClient.listUnfinished(taskDTO).getRows();
        if (tasks.size() == 0) {
            errorMsg = VaWorkFlowI18nUtils.getInfo("va.workflow.querytodofaileddatachanged");
            return errorMsg;
        }
        map.put("processInstanceId", processInstanceId);
        taskDTO = new TaskDTO();
        taskDTO.setTraceId(Utils.getTraceId());
        taskDTO.setTaskDefineKey((String)((Map)tasks.get(0)).get("TASKDEFINEKEY"));
        taskDTO.setProcessId(processInstanceId);
        taskDTO.setProcessStatus(Integer.valueOf(0));
        PageVO pageVO = this.todoClient.listHistoryTask(taskDTO);
        if (pageVO != null && pageVO.getRows() != null && pageVO.getRows().size() > 0) {
            ArrayList<String> completeUsers = new ArrayList<String>();
            for (Map taskHistory : pageVO.getRows()) {
                completeUsers.add((String)taskHistory.get("COMPLETEUSER"));
            }
            map.put("completeUsers", completeUsers);
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(processInstanceId);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        String bizType = processDO.getBizmodule();
        String bizDefine = processDO.getBiztype();
        BussinessClient bussinessClient = null;
        if ("BILL".equals(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType("BILL");
            R r = this.metaDataClient.getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            BizType bizTypeConfig = this.getBizTypeConfig(bizType);
            if (bizTypeConfig == null) {
                return VaWorkFlowI18nUtils.getInfo("va.workflow.checkworkflowbiztypeinfo");
            }
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)bizTypeConfig.getAppName(), (String)bizTypeConfig.getAppPath());
        }
        TenantDO tenant = new TenantDO();
        tenant.setTraceId(Utils.getTraceId());
        tenant.addExtInfo("bizCode", (Object)processDO.getBizcode());
        tenant.addExtInfo("bizType", (Object)bizDefine);
        tenant.addExtInfo("workflowdefinekey", (Object)processDO.getDefinekey());
        tenant.addExtInfo("workflowdefineversion", (Object)processDO.getDefineversion());
        Map bussinessParamVariables = bussinessClient.getBussinessParamVariables(tenant);
        try {
            WorkflowDTO workflowDTO = new WorkflowDTO();
            workflowDTO.setBizType(bizType);
            workflowDTO.setBizDefine(bizDefine);
            workflowDTO.setBizCode(processDO.getBizcode());
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setOperation(WorkflowOperation.REFRESH);
            vaWorkflowContext.setCurrentTask((Map)tasks.get(0));
            vaWorkflowContext.setCustomParam(map);
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setProcessDO(processDO);
            vaWorkflowContext.setVariables(bussinessParamVariables);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            this.workflowSevice.refreshParticipant(processInstanceId, null);
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
        return errorMsg;
    }

    private BizType getBizTypeConfig(String bizType) {
        List<BizType> bizTypes = this.bizTypeConfig.getTypes();
        for (BizType bizTypeconfig : bizTypes) {
            if (!bizType.equalsIgnoreCase(bizTypeconfig.getName())) continue;
            return bizTypeconfig;
        }
        return null;
    }
}

