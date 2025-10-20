/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.delegate.DelegateDO
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.delegate.DelegateDO;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.constants.DelegateConstants;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class DelegateHandler {
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    private WorkflowProcessNodeService workflowProcessNodeService;
    private static final Logger logger = LoggerFactory.getLogger(DelegateHandler.class);

    public WorkflowProcessNodeService getWorkflowProcessNodeService() {
        if (this.workflowProcessNodeService == null) {
            this.workflowProcessNodeService = (WorkflowProcessNodeService)ApplicationContextRegister.getBean(WorkflowProcessNodeService.class);
        }
        return this.workflowProcessNodeService;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.NOT_SUPPORTED)
    public void send(List<Map<String, Object>> todotasks, DelegateDO delegatedto, Map<String, Object> sendbizmap) {
        if (sendbizmap == null) {
            sendbizmap = new HashMap<String, Object>();
        }
        block2: for (Map<String, Object> todoTask : todotasks) {
            try {
                OptionItemDTO workflowOptionDTO = new OptionItemDTO();
                workflowOptionDTO.setGroupName("outsideVisit");
                workflowOptionDTO.setSearchKey("WF1003");
                List list = this.workflowOptionService.list(workflowOptionDTO);
                ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                processNodeDTO.setNodeid((String)todoTask.get("TASKID"));
                processNodeDTO.setProcessid((String)todoTask.get("PROCESSID"));
                processNodeDTO.setCompleteuserid(delegatedto.getDelegateuser());
                ProcessNodeDO processNodeDOByTodo = this.getWorkflowProcessNodeService().get(processNodeDTO);
                if (list.isEmpty()) continue;
                List agentusers = JSONUtil.parseArray((String)delegatedto.getAgentuser(), String.class);
                for (String agentuser : agentusers) {
                    todoTask.put("PARTICIPANT", agentuser);
                    todoTask.put("TASKTYPE", 3);
                    todoTask.put("DELEGATEID", delegatedto.getId());
                    ArrayList<Map<String, Object>> addTodoTasks = new ArrayList<Map<String, Object>>();
                    addTodoTasks.add(todoTask);
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.addExtInfo("todoTasks", addTodoTasks);
                    R r = this.todoClient.addBatch(tenantDO);
                    if (r.getCode() != 0) {
                        sendbizmap.put((String)todoTask.get("BIZCODE"), "\u5931\u8d25");
                        continue block2;
                    }
                    processNodeDOByTodo.setCompleteuserid(agentuser);
                    processNodeDOByTodo.setCompleteusertype(new BigDecimal(BigInteger.valueOf(2L)));
                    processNodeDOByTodo.setDelegateuser(delegatedto.getDelegateuser());
                    this.getWorkflowProcessNodeService().add(processNodeDOByTodo);
                }
                String val = ((OptionItemVO)list.get(0)).getVal();
                if (!"0".equals(val)) continue;
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("TASKID", todoTask.get("TASKID"));
                tenantDO.addExtInfo("PARTICIPANT", (Object)delegatedto.getDelegateuser());
                tenantDO.addExtInfo("FILTERDELEGATE", (Object)true);
                this.todoClient.deleteTask(tenantDO);
                ProcessNodeDO processNodeDO = new ProcessNodeDO();
                processNodeDO.setNodeid((String)todoTask.get("TASKID"));
                processNodeDO.setProcessid((String)todoTask.get("PROCESSID"));
                processNodeDO.setCompleteuserid(delegatedto.getDelegateuser());
                this.getWorkflowProcessNodeService().deleteByNodeIdAndUserId(processNodeDO);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                sendbizmap.put((String)todoTask.get("BIZCODE"), "\u5931\u8d25");
            }
        }
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.NOT_SUPPORTED)
    public void retake(List<Map<String, Object>> todotasks, DelegateDO delegatedto, Map<String, Object> retakebizmap) {
        if (retakebizmap == null) {
            retakebizmap = new HashMap<String, Object>();
        }
        for (Map<String, Object> todotask : todotasks) {
            try {
                TaskDTO tempTaskDTO = new TaskDTO();
                tempTaskDTO.setTaskId((String)todotask.get("TASKID"));
                tempTaskDTO.setParticipant(delegatedto.getDelegateuser());
                tempTaskDTO.setBackendRequest(true);
                PageVO tempList = this.todoClient.listUnfinished(tempTaskDTO);
                if (tempList.getRs().getCode() == 0 && tempList.getRows().isEmpty()) {
                    HashMap<String, Object> todotasktemp = new HashMap<String, Object>();
                    todotasktemp.putAll(todotask);
                    todotasktemp.put("PARTICIPANT", delegatedto.getDelegateuser());
                    todotasktemp.put("TASKTYPE", 1);
                    todotasktemp.put("DELEGATEID", null);
                    ArrayList<HashMap<String, Object>> addTodoTasks = new ArrayList<HashMap<String, Object>>();
                    addTodoTasks.add(todotasktemp);
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.addExtInfo("todoTasks", addTodoTasks);
                    R r = this.todoClient.addBatch(tenantDO);
                    if (r.getCode() != 0) {
                        retakebizmap.put((String)todotask.get("BIZCODE"), "\u5931\u8d25");
                        continue;
                    }
                    ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                    processNodeDTO.setNodeid((String)todotask.get("TASKID"));
                    processNodeDTO.setProcessid((String)todotask.get("PROCESSID"));
                    processNodeDTO.setCompleteuserid((String)todotask.get("PARTICIPANT"));
                    ProcessNodeDO processNodeDO = this.getWorkflowProcessNodeService().get(processNodeDTO);
                    processNodeDO.setCompleteuserid(delegatedto.getDelegateuser());
                    processNodeDO.setCompleteusertype(null);
                    this.getWorkflowProcessNodeService().add(processNodeDO);
                }
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("TASKID", todotask.get("TASKID"));
                tenantDO.addExtInfo("PARTICIPANT", todotask.get("PARTICIPANT"));
                this.todoClient.deleteTask(tenantDO);
                ProcessNodeDO processNodeDO = new ProcessNodeDO();
                processNodeDO.setNodeid((String)todotask.get("TASKID"));
                processNodeDO.setProcessid((String)todotask.get("PROCESSID"));
                processNodeDO.setCompleteuserid((String)todotask.get("PARTICIPANT"));
                this.getWorkflowProcessNodeService().deleteByNodeIdAndUserId(processNodeDO);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                retakebizmap.put((String)todotask.get("BIZCODE"), "\u5931\u8d25");
            }
        }
    }

    /*
     * Could not resolve type clashes
     */
    public List<Map<String, Object>> filterByCondition(DelegateDTO delegatedto, List<Map<String, Object>> unfinished) {
        ArrayList<Map<String, Object>> todotasks = new ArrayList<Map<String, Object>>();
        long nowtime = System.currentTimeMillis();
        block0: for (Map<String, Object> todotask : unfinished) {
            String bizType = Optional.ofNullable(delegatedto.getBizType()).orElse("BILL");
            String bizTypeObject = (String)todotask.get(DelegateConstants.U_BIZTYPE);
            if (StringUtils.isEmpty(bizTypeObject)) {
                bizTypeObject = "BILL";
            }
            if (!bizType.equals(bizTypeObject)) continue;
            for (Object cond : delegatedto.getDeleconditions()) {
                List condvalarray;
                Map condMap = JSONUtil.parseMap((String)JSONUtil.toJSONString(cond));
                String columnName = (String)condMap.get("columnName");
                String columnType = (String)condMap.get("columnType");
                Integer mappingtype = (Integer)condMap.get("mappingType");
                String condval = Optional.ofNullable(condMap.get("value")).orElse("");
                Object todoval = todotask.get(columnName);
                if (ObjectUtils.isEmpty(condval) || condval.equals("[]") || condval.equals("[\"\",\"\"]")) continue;
                if (columnName.equals("BIZDEFINE")) {
                    List storageValueArray;
                    Map condvalMap = JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)condval));
                    if (condvalMap == null || (storageValueArray = (List)condvalMap.get("storageValue")).size() == 0) continue;
                    boolean flag = true;
                    for (Object storageValue : storageValueArray) {
                        String todovalue = "";
                        if (todoval instanceof Map) {
                            todovalue = (String)((Map)todoval).get("name");
                        }
                        if (todoval instanceof String) {
                            todovalue = (String)todoval;
                        }
                        if (!((String)((Map)storageValue).get("uniqueCode")).contains(todovalue)) continue;
                        flag = false;
                        break;
                    }
                    if (!flag) continue;
                    continue block0;
                }
                if (mappingtype != null && mappingtype.equals(1)) {
                    boolean flag = true;
                    if (todoval != null) {
                        List condvalarray2 = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)condval), Object.class);
                        block3: for (Object object : condvalarray2) {
                            if (object instanceof String) {
                                for (Object todovalItem : ((String)todoval).split(",")) {
                                    if (!object.toString().equals(todovalItem)) continue;
                                    flag = false;
                                    continue block3;
                                }
                                continue;
                            }
                            List<String> todoValList = Arrays.asList(((String)todoval).split(","));
                            Map objectMap = (Map)object;
                            if (!objectMap.containsKey("data")) continue;
                            for (Object object2 : (List)objectMap.get("data")) {
                                Map jSONObject2 = (Map)object2;
                                String objectCode = (String)jSONObject2.get("objectcode");
                                if (!todoValList.contains(objectCode)) continue;
                                flag = false;
                                continue block3;
                            }
                        }
                    }
                    if (!flag) continue;
                    continue block0;
                }
                if (mappingtype != null && (mappingtype.equals(2) || mappingtype.equals(4) || mappingtype.equals(3))) {
                    if (condval instanceof String) {
                        condvalarray = new ArrayList<String>();
                        condvalarray.add(condval);
                    } else {
                        condvalarray = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)condval), String.class);
                    }
                    boolean flag = true;
                    if (todoval != null) {
                        for (String todovalItem : ((String)todoval).split(",")) {
                            if (!condvalarray.contains(todovalItem)) continue;
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) continue;
                    continue block0;
                }
                if (columnType.equals("NVARCHAR")) {
                    if ((todoval != null && condval != null || Objects.equals(condval, todoval)) && ((String)todoval).contains(condval.toString())) continue;
                    continue block0;
                }
                if (columnType.equals("TIMESTAMP") || columnType.equals("DATE")) {
                    condvalarray = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)condval), String.class);
                    if (!StringUtils.hasText((String)condvalarray.get(0)) || !StringUtils.hasText((String)condvalarray.get(1))) continue;
                    Date datestart = DelegateHandler.dateConvert((String)condvalarray.get(0), "00:00:00");
                    Date dateend = DelegateHandler.dateConvert((String)condvalarray.get(0), "23:59:59");
                    if (datestart.getTime() <= nowtime && dateend.getTime() >= nowtime) continue;
                    continue block0;
                }
                if (columnType.equals("NUMERIC") || columnType.equals("INTEGER")) {
                    condvalarray = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)condval), BigDecimal.class);
                    BigDecimal numstart = (BigDecimal)condvalarray.get(0);
                    BigDecimal numend = (BigDecimal)condvalarray.get(1);
                    if (todoval == null) {
                        if (numstart == null && numend == null) continue;
                        continue block0;
                    }
                    BigDecimal todoValBig = new BigDecimal(todoval.toString());
                    if (!(numstart != null && numend != null ? todoval == null || numstart.compareTo(todoValBig) == 1 || numend.compareTo(todoValBig) == -1 : (numstart != null && numend == null ? numstart.compareTo(todoValBig) == 1 : numstart == null && numend != null && numend.compareTo(todoValBig) == -1))) continue;
                    continue block0;
                }
                if (Objects.equals(condval, todoval)) continue;
                continue block0;
            }
            todotasks.add(todotask);
        }
        return todotasks;
    }

    public static Date dateConvert(String datetime, String pattern1) {
        SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd " + pattern1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatOld.parse(datetime);
            return format.parse(formatNew.format(date));
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

