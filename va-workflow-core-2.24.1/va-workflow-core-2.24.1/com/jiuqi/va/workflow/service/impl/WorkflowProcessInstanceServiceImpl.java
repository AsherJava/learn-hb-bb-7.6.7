/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.biz.querytable.service.QueryTableService
 *  com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.BussinessState
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.SubProcessInfoDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService
 *  com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.biz.querytable.service.QueryTableService;
import com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.BussinessState;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.SubProcessInfoDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService;
import com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice;
import com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.dao.VaWorkflowProcessDao;
import com.jiuqi.va.workflow.dao.VaWorkflowProcessHistoryDao;
import com.jiuqi.va.workflow.domain.ProcessNodeDOS;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowProcessInstanceService;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowProcessInstanceServiceImpl
implements WorkflowProcessInstanceService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowProcessInstanceServiceImpl.class);
    public static final String SYSTEM_CODE_WORKFLOW = "WORKFLOW";
    public static final String KEY_PROCESS_DO = "processDO";
    public static final String KEY_WORKFLOW_DTO = "workflowDTO";
    public static final String KEY_PROCESS_DOS = "processNodes";
    public static final String KEY_PROCESS_TERMINATE = "processTerminateFlag";
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowFormulaSevice workflowFormulaSevice;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowMetaService workflowMetaService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private VaWorkflowProcessDao processDao;
    @Autowired
    private VaWorkflowProcessHistoryDao processHistoryDao;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    @Autowired
    private QueryTableService queryTableService;
    @Autowired
    private WorkflowProcessInstanceService processInstanceService;
    @Autowired
    private WorkflowRetractLockService retractLockService;
    @Autowired
    private WorkflowProcessNodeExtendService nodeExtendService;
    @Autowired
    private JoinTemplate joinTemplate;
    private static final String KEY_IDLIST = "idList";
    private static final String KEY_REFRESHLIST = "refreshList";
    private static final String KEY_IS_PARENT = "isParent";
    private final String PRE_KEY = "processInstanceId:";
    private static final String FIELD_ID = "id";
    private static final String FIELD_MSG = "msg";
    private static final String SKIP_EMPTY_PARTICIPANT = "skipemptyparticipant";
    private static List<String> types = new ArrayList<String>();
    private WorkflowSubProcessBranchStrategyService workflowStrategyService;

    public WorkflowProcessInstanceServiceImpl() {
        types.add("ParallelGateway");
        types.add("JoinParallelGateway");
        types.add("EndNoneEvent");
        types.add("UserTask");
        types.add("StartNoneEvent");
    }

    private WorkflowSubProcessBranchStrategyService getWorkflowStrategyService() {
        if (this.workflowStrategyService == null) {
            this.workflowStrategyService = (WorkflowSubProcessBranchStrategyService)ApplicationContextRegister.getBean(WorkflowSubProcessBranchStrategyService.class);
        }
        return this.workflowStrategyService;
    }

    @Override
    public R changeProcessStatus(TenantDO tenantDO, WorkflowProcessStatus status) {
        R r = R.ok();
        try {
            List ids = (List)tenantDO.getExtInfo(KEY_IDLIST);
            ArrayList successData = new ArrayList();
            ArrayList failedData = new ArrayList();
            String tenantName = tenantDO.getTenantName();
            ids.forEach(id -> this.changeStatus(status, successData, failedData, (String)id, tenantName));
            r.put(FIELD_MSG, (Object)new StringBuffer(VaWorkFlowI18nUtils.getInfo("va.workflow.success")).append(successData.size()).append(VaWorkFlowI18nUtils.getInfo("va.workflow.stripfailed")).append(failedData.size()).append(VaWorkFlowI18nUtils.getInfo("va.workflow.strip")));
            if (!failedData.isEmpty()) {
                r.put("errItem", failedData);
            }
            return r;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void changeStatus(final WorkflowProcessStatus status, final List<String> sussessData, final List<Map<String, String>> failedData, final String processInstanceId, String tenantName) {
        Runnable refreshParticipantRunnable = new Runnable(){

            @Override
            public void run() {
                TenantDO tenant = new TenantDO();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(WorkflowProcessInstanceServiceImpl.FIELD_ID, processInstanceId);
                tenant.setExtInfo(map);
                String errorMsg = null;
                errorMsg = WorkflowProcessInstanceServiceImpl.this.handleChangeStatus(tenant, status.getValue());
                if (!StringUtils.hasText(errorMsg)) {
                    sussessData.add(processInstanceId);
                } else {
                    final String errorStr = errorMsg;
                    failedData.add(new HashMap<String, String>(){
                        private static final long serialVersionUID = 1L;
                        {
                            this.put(WorkflowProcessInstanceServiceImpl.FIELD_ID, processInstanceId);
                            this.put(WorkflowProcessInstanceServiceImpl.FIELD_MSG, errorStr);
                        }
                    });
                }
            }
        };
        R redisResult = R.ok();
        String lockKey = tenantName + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)refreshParticipantRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() != 0) {
            throw new RuntimeException(redisResult.getMsg());
        }
    }

    private String handleChangeStatus(TenantDO tenantDO, int status) {
        String id = (String)tenantDO.getExtInfo(FIELD_ID);
        if (status == WorkflowProcessStatus.PROCESS_FINSHED_RETRACT.getValue()) {
            return this.processInstanceService.doTerminate(id, status);
        }
        return this.processInstanceService.doPauseOrRecovery(id, status);
    }

    @Override
    @Transactional
    public String doTerminate(String id, int status) {
        String errorMsg = null;
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(id);
        ProcessDO processDO = this.processDao.getProcess(processDTO);
        if (processDO == null || !StringUtils.hasText(processDO.getBizcode())) {
            errorMsg = VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged");
            return errorMsg;
        }
        ProcessHistoryDO processHistoryDO = new ProcessHistoryDO();
        BeanUtils.copyProperties(processDO, processHistoryDO);
        processHistoryDO.setEndreason("\u6d41\u7a0b\u5df2\u88ab\u7ec8\u6b62");
        processHistoryDO.setEndstatus(new BigDecimal(status));
        processHistoryDO.setEndtime(new Date());
        this.processHistoryDao.insert(processHistoryDO);
        this.processDao.delete(processDO);
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo("PROCESSID", (Object)id);
        this.todoClient.retractTask(tenantDO);
        this.terminateSubProcess(processDO);
        this.workflowProcessNodeService.retractTask(tenantDO);
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        String bizCode = processDO.getBizcode();
        processNodeDTO.setBizcode(bizCode);
        try {
            this.nodeExtendService.deleteIgnoreNode(processNodeDTO);
        }
        catch (Exception e) {
            log.error("{}\u5220\u9664\u5931\u6548\u8f68\u8ff9\u5f02\u5e38{}", bizCode, e.getMessage(), e);
        }
        WorkflowRetractLockDTO retractLockDTO = new WorkflowRetractLockDTO();
        retractLockDTO.setBizcode(bizCode);
        this.deleteRetractLock(retractLockDTO);
        ProcessRejectNodeDO deleteDO = new ProcessRejectNodeDO();
        deleteDO.setBizcode(processDO.getBizcode());
        this.workflowProcessRejectNodeService.delRejectNodeInfo(deleteDO);
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
            bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
        }
        TenantDO tenant = new TenantDO();
        tenant.addExtInfo("bizCode", (Object)processDO.getBizcode());
        tenant.addExtInfo("bizType", (Object)processDO.getBiztype());
        tenant.addExtInfo("bizState", (Object)BussinessState.UNSUBMITTED);
        bussinessClient.updateBussinessState(tenant);
        return errorMsg;
    }

    private void terminateSubProcess(ProcessDO processDO) {
        ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
        String bizCode = processDO.getBizcode();
        nodeDTO.setBizcode(bizCode);
        List processNodeDOList = this.workflowProcessNodeService.listProcessNode(nodeDTO);
        if (CollectionUtils.isEmpty(processNodeDOList)) {
            return;
        }
        Set subProcessNodeCodeSet = processNodeDOList.stream().filter(o -> StringUtils.hasText(o.getSubprocessnodeid()) && o.getSubprocessnodeid().equals(o.getNodeid())).map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(subProcessNodeCodeSet)) {
            return;
        }
        String workflowDefineKey = processDO.getDefinekey();
        long workflowDefineVersion = processDO.getDefineversion().longValue();
        WorkflowModel model = this.workflowParamService.getModel(workflowDefineKey, workflowDefineVersion);
        ArrayNode arrayNode = this.workflowParamService.getWorkflowProcessNode(model);
        for (String subProcessNodeCode : subProcessNodeCodeSet) {
            List<Map<String, Object>> subProcessBranchStrategy = WorkflowProcessInstanceServiceImpl.getSubProcessBranchStrategy(subProcessNodeCode, arrayNode);
            if (CollectionUtils.isEmpty(subProcessBranchStrategy)) continue;
            for (Map<String, Object> branchStrategy : subProcessBranchStrategy) {
                String mqName = VaWorkflowUtils.getSubProcessRetractMq(branchStrategy);
                if (!StringUtils.hasText(mqName)) continue;
                HashMap<String, Object> msgContent = new HashMap<String, Object>(2);
                WorkflowDTO workflowDTO = new WorkflowDTO();
                workflowDTO.setProcessInstanceId(processDO.getId());
                workflowDTO.setUniqueCode(workflowDefineKey);
                workflowDTO.setProcessDefineVersion(Long.valueOf(workflowDefineVersion));
                workflowDTO.setBizCode(bizCode);
                workflowDTO.setBizDefine(processDO.getBiztype());
                workflowDTO.setBizType(processDO.getBizmodule());
                workflowDTO.addExtInfo(KEY_PROCESS_DO, (Object)processDO);
                workflowDTO.addExtInfo(KEY_PROCESS_DOS, (Object)processNodeDOList);
                msgContent.put(KEY_WORKFLOW_DTO, JSONUtil.toJSONString((Object)workflowDTO));
                msgContent.put(KEY_PROCESS_TERMINATE, true);
                this.joinTemplate.sendAndReceive(mqName, JSONUtil.toJSONString(msgContent));
            }
        }
    }

    private static List<Map<String, Object>> getSubProcessBranchStrategy(String subProcessNodeCode, ArrayNode arrayNode) {
        List subProcessBranchStrategy = null;
        for (JsonNode jsonNode : arrayNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (!subProcessNodeCode.equals(resourceId)) continue;
            JsonNode node = jsonNode.get("properties").get("subprocessbranchstrategy");
            subProcessBranchStrategy = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)node));
            break;
        }
        return subProcessBranchStrategy;
    }

    @Override
    @Transactional
    public String doPauseOrRecovery(String id, int status) {
        String errorMsg = null;
        int affectRows = 0;
        try {
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setId(id);
            ProcessDO processDO = this.processDao.getProcess(processDTO);
            if (processDO == null) {
                errorMsg = VaWorkFlowI18nUtils.getInfo("va.workflow.datachanged");
                return errorMsg;
            }
            if (processDO.getStatus().intValue() == status) {
                errorMsg = String.format(VaWorkFlowI18nUtils.getInfo("va.workflow.processstateis"), status == WorkflowProcessStatus.PROCESS_UNFINSHED_PAUSE.getValue() ? "\u6682\u505c" : "\u6062\u590d");
                return errorMsg;
            }
            processDO.setStatus(new BigDecimal(status));
            affectRows = this.processDao.updateByPrimaryKey(processDO);
            if (affectRows == 0) {
                errorMsg = VaWorkFlowI18nUtils.getInfo("va.workflow.datachanged");
                return errorMsg;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return errorMsg;
    }

    @Override
    public R refreshParticipant(TenantDO tenantDO) {
        R r = R.ok();
        List refreshList = (List)tenantDO.getExtInfo(KEY_REFRESHLIST);
        String tenantName = tenantDO.getTenantName();
        HashSet<String> successData = new HashSet<String>();
        ArrayList<Map<String, String>> failedData = new ArrayList<Map<String, String>>();
        if (CollectionUtils.isEmpty(refreshList)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        boolean isParent = (Boolean)tenantDO.getExtInfo(KEY_IS_PARENT);
        if (isParent) {
            for (Map map : refreshList) {
                this.refreshParticipant(successData, failedData, map, tenantName);
            }
        } else {
            Map map = (Map)refreshList.get(0);
            if (map.get("nodecode") != null) {
                this.refreshParticipant(successData, failedData, map, tenantName);
            }
        }
        r.put(FIELD_MSG, (Object)new StringBuffer(VaWorkFlowI18nUtils.getInfo("va.workflow.success")).append(successData.size()).append(VaWorkFlowI18nUtils.getInfo("va.workflow.stripfailed")).append(failedData.size()).append(VaWorkFlowI18nUtils.getInfo("va.workflow.strip")));
        if (!failedData.isEmpty()) {
            r.put("errItem", failedData);
        }
        return r;
    }

    private void refreshParticipant(Set<String> successData, List<Map<String, String>> failedData, Map<String, String> map, String tenantName) {
        String processInstanceId = map.get(FIELD_ID);
        String nodeCode = map.get("nodecode");
        String subProcessBranch = map.get("subprocessbranch");
        Runnable refreshParticipantRunnable = () -> {
            String errorMsg = null;
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setId(processInstanceId);
            ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
            if (processDO == null) {
                errorMsg = VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished");
            } else {
                WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
                WorkflowDTO workflowDTO = new WorkflowDTO();
                workflowDTO.setProcessInstanceId(processInstanceId);
                workflowDTO.setUniqueCode(processDO.getDefinekey());
                workflowDTO.setTraceId(Utils.getTraceId());
                if (nodeCode == null) {
                    HashMap<String, Map> nodeCodeMap = new HashMap<String, Map>();
                    List processNodeList = this.workflowProcessNodeService.selectProcessNode(processDTO);
                    for (Map processNodeMap : processNodeList) {
                        Object subprocessbranch;
                        String nodecode = (String)processNodeMap.get("NODECODE");
                        if (nodecode == null || nodeCodeMap.containsKey(nodecode) && ((subprocessbranch = ((Map)nodeCodeMap.get(nodecode)).get("SUBPROCESSBRANCH")) == null || Objects.equals(subprocessbranch, processNodeMap.get("SUBPROCESSBRANCH")))) continue;
                        nodeCodeMap.put(nodecode, processNodeMap);
                        workflowDTO.setTaskId((String)processNodeMap.get("NODEID"));
                        workflowDTO.setNodeCode(nodecode);
                        workflowDTO.setSubProcessBranch((String)processNodeMap.get("SUBPROCESSBRANCH"));
                        R result = workflowModel.refreshParticipant(workflowDTO);
                        if (result.getCode() == 0) continue;
                        errorMsg = result.getMsg();
                        break;
                    }
                } else {
                    workflowDTO.setNodeCode(nodeCode);
                    workflowDTO.setSubProcessBranch(subProcessBranch);
                    R result = workflowModel.refreshParticipant(workflowDTO);
                    if (result.getCode() != 0) {
                        errorMsg = result.getMsg();
                    }
                }
            }
            if (StringUtils.hasText(errorMsg)) {
                HashMap<String, String> failedMap = new HashMap<String, String>();
                failedMap.put(FIELD_ID, processInstanceId);
                failedMap.put(FIELD_MSG, errorMsg);
                failedData.add(failedMap);
            } else {
                successData.add(processInstanceId);
            }
        };
        R redisResult = R.ok();
        String lockKey = tenantName + "processInstanceId:" + processInstanceId;
        RedisLockUtil.execute((Runnable)refreshParticipantRunnable, (String)lockKey, (long)10000L, (boolean)true, (R)redisResult);
        if (redisResult.getCode() != 0) {
            throw new RuntimeException(redisResult.getMsg());
        }
    }

    @Override
    public R getProcessBizType(TenantDO tenantDO) {
        OptionItemVO optionItemVO;
        ArrayList list = new ArrayList(Optional.ofNullable(this.processDao.getProcessBizType(tenantDO)).orElse(Collections.emptyList()));
        UserLoginDTO user = ShiroUtil.getUser();
        OptionItemDTO optionParam = new OptionItemDTO();
        optionParam.setName("META001");
        List optionItemList = this.metaDataClient.listOption(optionParam);
        if (user.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = (OptionItemVO)optionItemList.get(0)).getVal().equals("1")) {
            TenantDO tenantParam = new TenantDO();
            HashMap exParam = new HashMap();
            exParam.put("TenantName", ShiroUtil.getTenantName());
            exParam.put("Groupflag", 0);
            exParam.put("MetaType", "workflow");
            tenantParam.setExtInfo((Map)exParam);
            Set userAuth = this.metaDataClient.checkUserAuth(tenantParam);
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>(16);
            for (Object name : userAuth) {
                hashMap.put((String)name, 1);
            }
            ArrayList<Map> infoTemp = new ArrayList<Map>();
            for (Map map : list) {
                if (!hashMap.containsKey(map.get("DEFINEKEY"))) continue;
                infoTemp.add(map);
            }
            list = infoTemp;
        }
        ArrayList maps = new ArrayList();
        HashSet<String> bizDefineSet = new HashSet<String>();
        for (Map info : list) {
            String bizType = String.valueOf(info.get("BIZMODULE"));
            String bizDefine = String.valueOf(info.get("BIZTYPE"));
            if (bizDefineSet.contains(bizDefine)) continue;
            bizDefineSet.add(bizDefine);
            Object title = null;
            TenantDO tenantDO2 = new TenantDO();
            tenantDO2.setTraceId(Utils.getTraceId());
            if ("BILL".equals(bizType)) {
                tenantDO2.addExtInfo("defineCode", (Object)bizDefine);
                R r2 = this.metaDataClient.findMetaInfoByDefineCode(tenantDO2);
                if (r2.getCode() == 0 && r2.get((Object)"title") != null) {
                    title = r2.get((Object)"title");
                }
            } else {
                try {
                    tenantDO2.addExtInfo("bizDefine", (Object)bizDefine);
                    BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
                    R r = bussinessClient.getBizTitle(tenantDO2);
                    if (r.getCode() == 0) {
                        title = r.get((Object)"bizDefineTitle");
                    }
                }
                catch (Exception e) {
                    title = bizDefine;
                    log.error("\u83b7\u53d6{}\u4e1a\u52a1\u540d\u79f0\u5931\u8d25", (Object)bizDefine, (Object)e);
                }
            }
            HashMap<String, Object> temp = new HashMap<String, Object>();
            temp.put("biztype", bizDefine);
            temp.put("title", title);
            maps.add(temp);
        }
        R r = R.ok();
        r.put("list", maps);
        return r;
    }

    @Override
    public R processForecast(WorkflowDTO workflowDTO) {
        ArrayList<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> startNode = new HashMap<String, Object>();
        startNode.put("nodeName", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.start"));
        VaWorkFlowI18nUtils.dealNodeMap("\u5f00\u59cb", startNode);
        startNode.put("auditState", 1);
        startNode.put("nodeType", "StartNoneEvent");
        nodes.add(startNode);
        if (StringUtils.hasText(workflowDTO.getUniqueCode())) {
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setBizcode(workflowDTO.getBizCode());
            ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
            if (processDO != null) {
                return this.forecastSubmited(workflowDTO, nodes);
            }
            return this.forecastUnSubmit(workflowDTO, nodes);
        }
        return this.forecastSubmited(workflowDTO, nodes);
    }

    @Override
    public R billProcessInfo(TenantDO tenantDO) {
        String dataSetName = (String)tenantDO.getExtInfo("dataSetName");
        HashMap<String, TenantDO> param = new HashMap<String, TenantDO>();
        param.put("tenantDO", tenantDO);
        List queryTableDataByName = this.queryTableService.getQueryTableDataByName(dataSetName, param);
        R r = R.ok();
        if (CollectionUtils.isEmpty(queryTableDataByName)) {
            return r;
        }
        Map rowData = ((QueryTableDataDTO)queryTableDataByName.get(0)).getRowData();
        if (CollectionUtils.isEmpty(r)) {
            return r;
        }
        List infoMap = JSONUtil.parseMapArray((String)((String)rowData.get("rowData")));
        r.put("info", (Object)infoMap);
        return r;
    }

    @Override
    public R getProcessInfo(TenantDO tenantDO) {
        boolean filterBizNodeFlag;
        String bizCode = (String)tenantDO.getExtInfo("bizcode");
        Object filterBizNodeFlagObj = tenantDO.getExtInfo("filterBizNodeFlag");
        boolean bl = filterBizNodeFlag = filterBizNodeFlagObj != null && (Boolean)filterBizNodeFlagObj != false;
        if (!StringUtils.hasText(bizCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        R r = R.ok();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(bizCode);
        processNodeDTO.setSyscode(SYSTEM_CODE_WORKFLOW);
        processNodeDTO.setFilterBizNodeFlag(filterBizNodeFlag);
        List info = this.workflowProcessNodeService.listProcessNodeForward(processNodeDTO);
        r.put("info", (Object)info);
        return r;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private R forecastUnSubmit(WorkflowDTO workflowDTO, List<Map<String, Object>> nodes) {
        String bizCode = workflowDTO.getBizCode();
        String uniqueCode = workflowDTO.getUniqueCode();
        String bizDefine = workflowDTO.getBizDefine();
        boolean userInfoDetail = workflowDTO.isUserInfoDetail();
        Map workflowVariables = workflowDTO.getWorkflowVariables();
        HashMap<String, UserDO> userCache = new HashMap<String, UserDO>();
        ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
        processRejectNodeDO.setBizcode(bizCode);
        List processRejectNodeDOS = this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
        try {
            String processId;
            WorkflowModel workflowModel;
            if (CollectionUtils.isEmpty(processRejectNodeDOS)) {
                workflowModel = this.workflowParamService.getModel(uniqueCode, null);
            } else {
                long processDefineVersion = ((ProcessRejectNodeDO)processRejectNodeDOS.get(0)).getProcessdefineversion().longValue();
                workflowModel = this.workflowParamService.getModel(uniqueCode, processDefineVersion);
            }
            VaWorkflowContext workflowContext = VaContext.getVaWorkflowContext();
            if (workflowContext == null) {
                workflowContext = new VaWorkflowContext();
                VaContext.setVaWorkflowContext((VaWorkflowContext)workflowContext);
            }
            this.setGlobalConfig(workflowModel, workflowDTO);
            workflowContext.setWorkflowDTO(workflowDTO);
            workflowContext.setRejectSkipNodeInfoList(processRejectNodeDOS);
            ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setBizcode(bizCode);
            List processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
            workflowContext.setProcessNodeList(processNodeDOS);
            VaWorkflowUtils.calProcessParam(workflowVariables, workflowModel);
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setBizcode(bizCode);
            ProcessDO process = this.vaWorkflowProcessService.get(processDTO);
            if (process != null) {
                processId = process.getId();
            } else {
                List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
                if (CollectionUtils.isEmpty(processHistoryDOS)) {
                    processId = null;
                } else {
                    ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
                    processId = processHistoryDO.getId();
                }
            }
            ProcessDO processDO = new ProcessDO();
            processDO.setId(processId);
            processDO.setBizcode(bizCode);
            processDO.setBiztype(bizDefine);
            processDO.setDefinekey(uniqueCode);
            ArrayNode jsonNodes = this.workflowParamService.getWorkflowProcessNode(workflowModel);
            String resourceId = null;
            for (JsonNode jsonNode : jsonNodes) {
                String nodeType = jsonNode.get("stencil").get(FIELD_ID).asText();
                if (!"StartNoneEvent".equals(nodeType)) continue;
                ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
                String outgoingId = outgoings.get(0).get("resourceId").asText();
                for (JsonNode node : jsonNodes) {
                    if (!outgoingId.equals(node.get("resourceId").asText())) continue;
                    resourceId = node.get("target").get("resourceId").asText();
                }
            }
            HashMap<String, Object> submitNode = new HashMap<String, Object>();
            submitNode.put("nodeName", VaWorkFlowI18nUtils.getInfo("va.workflow.submit"));
            VaWorkFlowI18nUtils.dealNodeMap("\u63d0\u4ea4", submitNode);
            submitNode.put("auditState", 2);
            submitNode.put("nodeType", "UserTask");
            submitNode.put("counterSignFlag", 0);
            submitNode.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.waitsubmit"));
            submitNode.put("stencilId", resourceId);
            ArrayList commitUser = new ArrayList();
            HashMap<String, String> commitNodeMap = new HashMap<String, String>();
            commitUser.add(commitNodeMap);
            commitNodeMap.put("auditUser", "");
            submitNode.put("auditInfo", commitUser);
            nodes.add(submitNode);
            HashMap<String, WorkflowModel> customParam = new HashMap<String, WorkflowModel>();
            customParam.put("workflowModel", workflowModel);
            workflowContext.setCustomParam(customParam);
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("metaVersion", (Object)workflowDTO.getProcessDefineVersion());
            tenantDO.addExtInfo("workflowDefineKey", (Object)uniqueCode);
            Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
            R upcomingNodess = this.getUpcomingNodess(resourceId, jsonNodes, nodes, (Map<String, Object>)workflowVariables, uniqueCode, workflowDefineVer, processDO, false, userCache, false, userInfoDetail);
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                VaWorkFlowI18nUtils.convertNodeLanguage(nodes, uniqueCode, bizCode, workflowDTO.getProcessDefineVersion());
            }
            R r = upcomingNodess;
            return r;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            R r = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfailed"));
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private R forecastSubmited(WorkflowDTO workflowDTO, List<Map<String, Object>> nodes) {
        String processId;
        String definekey;
        BigDecimal version;
        String bizDefine;
        String bizType;
        String bizCode = workflowDTO.getBizCode();
        boolean userInfoDetail = workflowDTO.isUserInfoDetail();
        HashMap<String, UserDO> userCache = new HashMap<String, UserDO>();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO != null) {
            bizType = processDO.getBizmodule();
            bizDefine = processDO.getBiztype();
            version = processDO.getDefineversion();
            definekey = processDO.getDefinekey();
            processId = processDO.getId();
        } else {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                return R.ok().put("data", new ArrayList());
            }
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
            bizType = processHistoryDO.getBizmodule();
            bizDefine = processHistoryDO.getBiztype();
            version = processHistoryDO.getDefineversion();
            processId = processHistoryDO.getId();
            definekey = processHistoryDO.getDefinekey();
        }
        WorkflowModel workflowModel = this.workflowParamService.getModel(definekey, version.longValue());
        VaWorkflowContext workflowContext = VaContext.getVaWorkflowContext();
        if (workflowContext == null) {
            workflowContext = new VaWorkflowContext();
            VaContext.setVaWorkflowContext((VaWorkflowContext)workflowContext);
        }
        this.setGlobalConfig(workflowModel, workflowDTO);
        workflowContext.setWorkflowDTO(workflowDTO);
        workflowContext.setProcessDO(processDO);
        ArrayNode arrayNodes = this.workflowParamService.getWorkflowProcessNode(workflowModel);
        HashMap<String, String> nodeTypeMap = new HashMap<String, String>(arrayNodes.size());
        for (JsonNode arrayNode : arrayNodes) {
            String nodeType;
            String resourceId = arrayNode.get("resourceId").asText();
            String string = arrayNode.get("stencil") == null ? null : (nodeType = arrayNode.get("stencil").get(FIELD_ID) == null ? null : arrayNode.get("stencil").get(FIELD_ID).asText());
            if ("SubProcess".equals(nodeType)) {
                JsonNode childShapes = arrayNode.get("childShapes");
                for (JsonNode childShape : childShapes) {
                    String subResourceId = childShape.get("resourceId").asText();
                    String subNodeType = childShape.get("stencil") == null ? null : (childShape.get("stencil").get(FIELD_ID) == null ? null : childShape.get("stencil").get(FIELD_ID).asText());
                    nodeTypeMap.put(subResourceId, subNodeType);
                }
            }
            nodeTypeMap.put(resourceId, nodeType);
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(bizCode);
        List processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        workflowContext.setProcessNodeList(processNodeDOS);
        List nodeDOS = processNodeDOS.stream().filter(o -> processId.equals(o.getProcessid())).collect(Collectors.toList());
        if (processDO == null) {
            nodeDOS = nodeDOS.stream().filter(processNodeDO -> processNodeDO.getCompleteuserid() != null || processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid()) || processNodeDO.getSubprocessnodeid() != null).collect(Collectors.toList());
        }
        ArrayList<ProcessNodeDOS> processNodeDOSList = new ArrayList<ProcessNodeDOS>();
        try {
            ArrayList<String> subProcessNodeIds = new ArrayList<String>();
            for (int i = 0; i < nodeDOS.size(); ++i) {
                ProcessNodeDO processNodeDO2 = (ProcessNodeDO)nodeDOS.get(i);
                if (processNodeDO2.getSubprocessnodeid() != null && !subProcessNodeIds.contains(processNodeDO2.getSubprocessnodeid()) && processNodeDO2.getNodeid().equals(processNodeDO2.getSubprocessnodeid())) {
                    subProcessNodeIds.add(processNodeDO2.getSubprocessnodeid());
                    ProcessNodeDOS subNode = new ProcessNodeDOS();
                    BeanUtils.copyProperties(nodeDOS.get(i), (Object)subNode);
                    ArrayList<List<ProcessNodeDOS>> children = new ArrayList<List<ProcessNodeDOS>>();
                    ArrayList<String> subBranch = new ArrayList<String>();
                    for (ProcessNodeDO nodeDO : nodeDOS) {
                        if (nodeDO.getSubprocessnodeid() == null || !nodeDO.getSubprocessnodeid().equals(processNodeDO2.getSubprocessnodeid()) || subBranch.contains(nodeDO.getSubprocessbranch()) || nodeDO.getNodeid().equals(nodeDO.getSubprocessnodeid())) continue;
                        subBranch.add(nodeDO.getSubprocessbranch());
                        ArrayList<ProcessNodeDOS> processBranch = new ArrayList<ProcessNodeDOS>();
                        children.add(processBranch);
                        for (ProcessNodeDO aDo : nodeDOS) {
                            if (nodeDO.getSubprocessnodeid() == null || !nodeDO.getSubprocessnodeid().equals(processNodeDO2.getSubprocessnodeid()) || !nodeDO.getSubprocessbranch().equals(aDo.getSubprocessbranch())) continue;
                            ProcessNodeDOS branchNode = new ProcessNodeDOS();
                            BeanUtils.copyProperties(aDo, (Object)branchNode);
                            processBranch.add(branchNode);
                        }
                    }
                    subNode.setChildren(children);
                    processNodeDOSList.add(subNode);
                    continue;
                }
                if (processNodeDO2.getPgwnodeid() == null && processNodeDO2.getHiddenflag() == null && processNodeDO2.getSubprocessnodeid() == null) {
                    ProcessNodeDOS processNodeDOS1 = new ProcessNodeDOS();
                    BeanUtils.copyProperties(nodeDOS.get(i), (Object)processNodeDOS1);
                    processNodeDOSList.add(processNodeDOS1);
                    continue;
                }
                if (!processNodeDO2.getNodeid().equals(processNodeDO2.getPgwnodeid())) continue;
                String pgwnodeid = processNodeDO2.getPgwnodeid();
                ProcessNodeDOS pgw = new ProcessNodeDOS();
                BeanUtils.copyProperties(nodeDOS.get(i), (Object)pgw);
                ArrayList<List<ProcessNodeDOS>> children = new ArrayList<List<ProcessNodeDOS>>();
                LinkedList<String> branchIds = new LinkedList<String>();
                for (int j = 0; j < nodeDOS.size(); ++j) {
                    if (branchIds.contains(((ProcessNodeDO)nodeDOS.get(j)).getPgwbranch()) || !pgwnodeid.equals(((ProcessNodeDO)nodeDOS.get(j)).getPgwnodeid())) continue;
                    branchIds.add(((ProcessNodeDO)nodeDOS.get(j)).getPgwbranch());
                    ArrayList<ProcessNodeDOS> collect = new ArrayList<ProcessNodeDOS>();
                    for (int i1 = j; i1 < nodeDOS.size(); ++i1) {
                        if (!((ProcessNodeDO)nodeDOS.get(j)).getPgwbranch().equals(((ProcessNodeDO)nodeDOS.get(i1)).getPgwbranch()) || ((ProcessNodeDO)nodeDOS.get(i1)).getNodeid().equals(((ProcessNodeDO)nodeDOS.get(i1)).getPgwnodeid()) || !pgwnodeid.equals(((ProcessNodeDO)nodeDOS.get(i1)).getPgwnodeid())) continue;
                        ProcessNodeDOS processNodeDOS1 = new ProcessNodeDOS();
                        BeanUtils.copyProperties(nodeDOS.get(i1), (Object)processNodeDOS1);
                        collect.add(processNodeDOS1);
                    }
                    children.add(collect);
                }
                pgw.setChildren(children);
                processNodeDOSList.add(pgw);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        try {
            List<Map<String, Object>> completeNodes = this.calculateCompletedNodes(processNodeDOSList, userCache, nodeTypeMap, userInfoDetail);
            nodes.addAll(completeNodes);
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("metaVersion", (Object)version);
            tenantDO.addExtInfo("workflowDefineKey", (Object)definekey);
            Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
            HashMap<String, WorkflowModel> customParam = new HashMap<String, WorkflowModel>();
            customParam.put("workflowModel", workflowModel);
            workflowContext.setCustomParam(customParam);
            List todoAuditNodes = nodeDOS.stream().filter(processNodeDO -> processNodeDO.getCompletetime() == null && !processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid()) && !processNodeDO.getNodeid().equals(processNodeDO.getSubprocessnodeid())).collect(Collectors.toList());
            if (todoAuditNodes.size() != 0) {
                R r;
                Map<String, Object> workflowVariables = workflowDTO.getWorkflowVariables();
                if (CollectionUtils.isEmpty(workflowVariables)) {
                    workflowVariables = this.workflowParamService.loadWorkflowVariables(bizCode, bizDefine, version, bizType, definekey);
                }
                workflowDTO.setWorkflowVariables((Map)workflowVariables);
                VaWorkflowUtils.calProcessParam(workflowVariables, workflowModel);
                ArrayList<String> subprocessbranchs = new ArrayList<String>();
                ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
                processRejectNodeDO.setBizcode(workflowDTO.getBizCode());
                List rejectSkipNodeInfoList = this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
                workflowContext.setRejectSkipNodeInfoList(rejectSkipNodeInfoList);
                for (int i = 0; i < todoAuditNodes.size(); ++i) {
                    ProcessNodeDO todoProcessNodeDO = (ProcessNodeDO)todoAuditNodes.get(i);
                    String nodecode = todoProcessNodeDO.getNodecode();
                    if (todoProcessNodeDO.getPgwbranch() != null) {
                        String stencilId = todoProcessNodeDO.getNodecode();
                        this.getUpcomingNodess(nodecode, arrayNodes, nodes, workflowVariables, definekey, workflowDefineVer, processDO, false, userCache, stencilId, userInfoDetail);
                    }
                    if (todoProcessNodeDO.getSubprocessnodeid() == null || subprocessbranchs.contains(todoProcessNodeDO.getSubprocessbranch())) continue;
                    subprocessbranchs.add(todoProcessNodeDO.getSubprocessbranch());
                    String subStencilId = completeNodes.get(completeNodes.size() - 1).get("stencilId").toString();
                    for (JsonNode arrayNode : arrayNodes) {
                        if (!arrayNode.get("resourceId").asText().equals(subStencilId)) continue;
                        List childrens = (List)completeNodes.get(completeNodes.size() - 1).get("children");
                        for (int i1 = childrens.size() - 1; i1 >= 0; --i1) {
                            List children = (List)childrens.get(i1);
                            String subProcessBranch = ((Map)children.get(0)).get("subProcessBranch").toString();
                            SubProcessInfoDTO subProcessInfoDTO = new SubProcessInfoDTO();
                            subProcessInfoDTO.setSubProcessBranch(subProcessBranch);
                            if (!todoProcessNodeDO.getSubprocessbranch().equals(subProcessBranch)) continue;
                            String subProcessBranchName = "";
                            String nodeName = todoProcessNodeDO.getProcessnodename();
                            if (nodeName.contains("\u2014")) {
                                subProcessBranchName = nodeName.substring(nodeName.indexOf(8212) + 2);
                            }
                            subProcessInfoDTO.setSubProcessBranchName(subProcessBranchName);
                            JsonNode childShapeNode = VaWorkflowNodeUtils.adjustChildShapeOrder(arrayNode.get("childShapes"), arrayNodes);
                            boolean childShapes = this.calculSubProcessBranch(children, subProcessInfoDTO, childShapeNode, userCache, nodecode, completeNodes, workflowVariables, processDO, null, userInfoDetail);
                            if (childShapes) continue;
                            childrens.remove(i1);
                        }
                    }
                }
                if (((ProcessNodeDO)todoAuditNodes.get(0)).getPgwbranch() != null || ((ProcessNodeDO)todoAuditNodes.get(0)).getSubprocessnodeid() != null) {
                    String nodecodePgw = nodes.get(nodes.size() - 1).get("stencilId").toString();
                    r = this.getUpcomingNodess(nodecodePgw, arrayNodes, nodes, workflowVariables, definekey, workflowDefineVer, processDO, false, userCache, true, userInfoDetail);
                } else {
                    String nodecode = ((ProcessNodeDO)todoAuditNodes.get(0)).getNodecode();
                    r = this.getUpcomingNodess(nodecode, arrayNodes, nodes, workflowVariables, definekey, workflowDefineVer, processDO, false, userCache, false, userInfoDetail);
                }
                if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                    VaWorkFlowI18nUtils.convertNodeLanguage(nodes, bizDefine, bizCode, workflowDTO.getProcessDefineVersion());
                }
                R r2 = r;
                return r2;
            }
            this.autoNodeFilter(nodes, definekey, workflowDefineVer);
            HashMap<String, Object> endNode = new HashMap<String, Object>();
            endNode.put("nodeName", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.end"));
            VaWorkFlowI18nUtils.dealNodeMap("\u7ed3\u675f", endNode);
            endNode.put("auditState", 1);
            endNode.put("nodeType", "EndNoneEvent");
            nodes.add(endNode);
            R r = new R();
            r.put("data", nodes);
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                VaWorkFlowI18nUtils.convertNodeLanguage(nodes, bizDefine, bizCode, workflowDTO.getProcessDefineVersion());
            }
            R r3 = r;
            return r3;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private void setGlobalConfig(WorkflowModel workflowModel, WorkflowDTO workflowDTO) {
        JsonNode jsonNode = ((ProcessDesignPluginDefine)((ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class)).getDefine()).getData();
        JsonNode globalSubmitterCannotApprove = jsonNode.get("globalSubmitterCannotApprove");
        workflowDTO.addExtInfo("globalSubmitterCannotApprove", (Object)(globalSubmitterCannotApprove != null && globalSubmitterCannotApprove.asBoolean() ? 1 : 0));
        JsonNode globalChooseApprover = jsonNode.get("globleChooseApprover");
        workflowDTO.addExtInfo("globalChooseApprover", (Object)(globalChooseApprover == null ? "disable" : globalChooseApprover.asText()));
    }

    private List<Map<String, Object>> calculateCompletedNodes(List<ProcessNodeDOS> processNodeDOs, Map<String, UserDO> userCache, Map<String, String> nodeTypeMap, boolean userInfoDetail) {
        ArrayList<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        this.removeDuplicateNodes(processNodeDOs);
        LinkedHashMap<String, List> listMap = new LinkedHashMap<String, List>();
        for (ProcessNodeDOS processNodeDO : processNodeDOs) {
            if (processNodeDO == null || BigDecimal.ONE.equals(processNodeDO.getRejectstatus()) && "\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) continue;
            String nodeDONodeCode = processNodeDO.getNodecode();
            BigDecimal counterSignFlag = processNodeDO.getCountersignflag();
            if (listMap.containsKey(nodeDONodeCode)) {
                List<ProcessNodeDOS> processNodeDOS2;
                String nodeId = processNodeDO.getNodeid();
                if (!new BigDecimal("1").equals(counterSignFlag) && !((ProcessNodeDOS)((Object)((List)listMap.get(nodeDONodeCode)).get(0))).getNodeid().equals(nodeId)) {
                    if (listMap.containsKey(nodeId)) {
                        processNodeDOS2 = (List)listMap.get(nodeId);
                        processNodeDOS2.add(processNodeDO);
                        continue;
                    }
                    processNodeDOS2 = new ArrayList<ProcessNodeDOS>();
                    processNodeDOS2.add(processNodeDO);
                    listMap.put(nodeId, processNodeDOS2);
                    continue;
                }
                processNodeDOS2 = (ArrayList<ProcessNodeDOS>)listMap.get(nodeDONodeCode);
                processNodeDOS2.add(processNodeDO);
                continue;
            }
            ArrayList<ProcessNodeDOS> processNodeDOS3 = new ArrayList<ProcessNodeDOS>();
            processNodeDOS3.add(processNodeDO);
            listMap.put(nodeDONodeCode, processNodeDOS3);
        }
        listMap.forEach((nodeCode, processNodeDOS) -> {
            HashMap<String, Object> nodeMap = new HashMap<String, Object>();
            nodes.add(nodeMap);
            LinkedList mapList = new LinkedList();
            String processnodename = ((ProcessNodeDOS)((Object)((Object)processNodeDOS.get(0)))).getProcessnodename();
            nodeMap.put("nodeName", processnodename);
            nodeMap.put("auditInfo", mapList);
            String nodecode = ((ProcessNodeDOS)((Object)((Object)processNodeDOS.get(0)))).getNodecode();
            nodeMap.put("stencilId", nodecode);
            nodeMap.put("counterSignFlag", ((ProcessNodeDOS)((Object)((Object)processNodeDOS.get(0)))).getCountersignflag());
            nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.havebeenapproval"));
            nodeMap.put("auditState", 1);
            nodeMap.put("nodeType", nodeTypeMap.get(nodecode));
            if (((ProcessNodeDOS)((Object)((Object)processNodeDOS.get(0)))).getSubprocessbranch() != null) {
                nodeMap.put("subProcessBranch", ((ProcessNodeDOS)((Object)((Object)processNodeDOS.get(0)))).getSubprocessbranch());
            }
            processNodeDOS.forEach(processNodeDO -> {
                Date completetime;
                if (processNodeDO.getChildren() != null) {
                    ArrayList<List<Map<String, Object>>> children = new ArrayList<List<Map<String, Object>>>();
                    for (int i = 0; i < processNodeDO.getChildren().size(); ++i) {
                        List<Map<String, Object>> maps = this.calculateCompletedNodes(processNodeDO.getChildren().get(i), userCache, nodeTypeMap, userInfoDetail);
                        if (maps.isEmpty()) continue;
                        children.add(maps);
                    }
                    nodeMap.put("children", children);
                }
                if ((completetime = processNodeDO.getCompletetime()) == null) {
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.pending"));
                    nodeMap.put("auditState", 2);
                }
                LinkedHashMap<String, Object> auditMap = new LinkedHashMap<String, Object>();
                String completeuserid = processNodeDO.getCompleteuserid();
                if (StringUtils.hasText(completeuserid)) {
                    BigDecimal completeusertype = processNodeDO.getCompleteusertype();
                    String delegateuserId = processNodeDO.getDelegateuser();
                    String auditUser = this.getUser(completeuserid, userCache).getName();
                    if (completeusertype != null && completeusertype.intValue() == 1) {
                        auditUser = auditUser + "(" + VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.signatureperson") + ")";
                        auditMap.put("completeusertype", 1);
                    } else if (completetime == null) {
                        List nodeDOS = processNodeDOS.stream().filter(processNode -> completeuserid.equals(processNode.getDelegateuser())).collect(Collectors.toList());
                        if (nodeDOS.size() != 0) {
                            return;
                        }
                        if (delegateuserId != null) {
                            List deleNodes = processNodeDOS.stream().filter(processNode -> delegateuserId.equals(processNode.getCompleteuserid())).collect(Collectors.toList());
                            if (deleNodes.size() == 0) {
                                auditUser = auditUser + "(" + VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.agent") + ")";
                                auditMap.put("completeusertype", 2);
                            } else {
                                String delegateUser = this.getUser(delegateuserId, userCache).getName();
                                auditUser = delegateUser + "(" + VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.agent") + auditUser + ")";
                                auditMap.put("completeusertype", 2);
                            }
                        }
                    } else if (completeusertype != null && completeusertype.intValue() == 2) {
                        auditUser = auditUser + "(" + VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.agent") + ")";
                        auditMap.put("completeusertype", 2);
                    }
                    auditMap.put("auditUser", auditUser);
                } else {
                    auditMap.put("auditUser", "");
                }
                if (userInfoDetail) {
                    auditMap.put("auditUserInfoDetail", this.getUser(completeuserid, userCache));
                }
                auditMap.put("completeTime", processNodeDO.getCompletetime());
                if ("\u63d0\u4ea4".equals(processNodeDO.getCompleteresult())) {
                    auditMap.put("comment", VaWorkFlowI18nUtils.getInfo("va.workflow.submit"));
                } else {
                    auditMap.put("comment", processNodeDO.getCompletecomment());
                }
                auditMap.put("auditResult", processNodeDO.getCompleteresult());
                if (processNodeDO.getCompletetime() == null) {
                    mapList.add(auditMap);
                } else {
                    mapList.addLast(auditMap);
                }
            });
        });
        return nodes;
    }

    private void removeDuplicateNodes(List<ProcessNodeDOS> processNodeDOs) {
        ArrayList<ProcessNodeDOS> tempList = new ArrayList<ProcessNodeDOS>(processNodeDOs);
        HashMap<String, Integer> minIndex = new HashMap<String, Integer>();
        ArrayList<String> nodeCodes = new ArrayList<String>();
        String lastNodeCode = null;
        for (int i = 0; i < processNodeDOs.size(); ++i) {
            ProcessNodeDO currentNode = processNodeDOs.get(i);
            String currentNodeCode = currentNode.getNodecode();
            if (!nodeCodes.contains(currentNodeCode)) {
                nodeCodes.add(currentNodeCode);
                minIndex.put(currentNodeCode, i);
                lastNodeCode = currentNodeCode;
                continue;
            }
            if (!Objects.equals(currentNodeCode, lastNodeCode)) {
                Integer index = (Integer)minIndex.get(currentNodeCode);
                minIndex.put(currentNodeCode, i);
                for (int z = index.intValue(); z < i; ++z) {
                    nodeCodes.remove(((ProcessNodeDO)tempList.get(z)).getNodecode());
                    nodeCodes.add(currentNodeCode);
                    if (currentNode.getSubprocessbranch() != null && processNodeDOs.get(z) != null && "\u5ba1\u6279\u9a73\u56de".equals(processNodeDOs.get(z).getCompleteresult())) {
                        processNodeDOs.set(z, null);
                        break;
                    }
                    processNodeDOs.set(z, null);
                }
            }
            lastNodeCode = currentNodeCode;
        }
    }

    private R getUpcomingNodess(String nodeId, ArrayNode arrayNode, List<Map<String, Object>> nodes, Map<String, Object> params, String defineKey, Integer version, ProcessDO processDO, boolean calparticipaint, Map<String, UserDO> userCache, String stencilId, boolean userInfoDetail) {
        HashMap<String, Object> nodeMap = new HashMap<String, Object>();
        for (JsonNode jsonNode : arrayNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (!nodeId.equals(resourceId)) continue;
            boolean skipUser = false;
            String nodeCodeName = jsonNode.get("properties").get("name").asText();
            if (calparticipaint) {
                JsonNode multiinstanceTypeNode = jsonNode.get("properties").get("multiinstance_type");
                if (multiinstanceTypeNode != null) {
                    ArrayNode usertaskassignment;
                    int counterSignFlag = 0;
                    String multiinstanceType = multiinstanceTypeNode.asText();
                    if (!"None".equals(multiinstanceType)) {
                        counterSignFlag = 1;
                        String multiinstance = jsonNode.get("properties").get("multiinstance_collection").asText();
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            usertaskassignment = (ArrayNode)objectMapper.readTree(multiinstance);
                        }
                        catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);
                            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.failedgetsignature"));
                        }
                    } else {
                        usertaskassignment = (ArrayNode)jsonNode.get("properties").get("usertaskassignment");
                    }
                    WorkflowStrategySevice workflowStrategySevice = (WorkflowStrategySevice)ApplicationContextRegister.getBean(WorkflowStrategySevice.class);
                    Set<String> allUserIdSet = new LinkedHashSet<String>();
                    for (JsonNode assignmentNode : usertaskassignment) {
                        try {
                            String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                            String strategyName = assignmentNode.get("strategyName").asText();
                            HashMap<String, Object> calParams = new HashMap<String, Object>();
                            calParams.put("variables", params);
                            calParams.put("assignParam", assignmentNode.get("items"));
                            WorkflowDTO workflowDTO = new WorkflowDTO();
                            workflowDTO.setBizType(processDO.getBizmodule());
                            workflowDTO.setBizDefine(processDO.getBiztype());
                            workflowDTO.setBizCode(processDO.getBizcode());
                            calParams.put(KEY_WORKFLOW_DTO, workflowDTO);
                            calParams.put(KEY_PROCESS_DO, processDO);
                            calParams.put("currentNodeCode", nodeId);
                            allUserIdSet.addAll(workflowStrategySevice.execute(strategyModuleName, strategyName, calParams));
                        }
                        catch (Exception e) {
                            log.error("\u9884\u6d4b\u6267\u884c\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
                        }
                    }
                    if ((allUserIdSet = this.filterApprover(allUserIdSet, jsonNode, null)).isEmpty() && jsonNode.get("properties").get(SKIP_EMPTY_PARTICIPANT).asBoolean()) {
                        skipUser = true;
                    }
                    LinkedHashSet<String> allUsers = new LinkedHashSet<String>();
                    ArrayList allUserList = new ArrayList();
                    for (String userId : allUserIdSet) {
                        UserDO user = this.getUser(userId, userCache);
                        String userName = user.getName();
                        allUsers.add(userName);
                        HashMap<String, String> userInfoMap = new HashMap<String, String>(2);
                        userInfoMap.put("auditUser", userName);
                        userInfoMap.put("auditUserInfoDetail", (String)user);
                        allUserList.add(userInfoMap);
                    }
                    if (userInfoDetail) {
                        nodeMap.put("auditInfo", allUserList);
                    } else {
                        nodeMap.put("auditInfo", allUsers);
                    }
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("counterSignFlag", counterSignFlag);
                    nodeMap.put("stencilId", jsonNode.get("resourceId").asText());
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                } else if (jsonNode.get("stencil").get(FIELD_ID) != null && !types.contains(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                    nodeMap.put("stencilId", jsonNode.get("resourceId").asText());
                } else {
                    R r = new R();
                    r.put("data", nodes);
                    return r;
                }
            }
            calparticipaint = true;
            if (processDO != null) {
                String processDefineKey = processDO.getDefinekey();
                BigDecimal processDefineVersion = processDO.getDefineversion();
                ProcessRejectNodeDO rejectNodeInfo = VaContext.getVaWorkflowContext().getRejectSkipNodeInfoList().stream().filter(x -> !StringUtils.hasText(processDefineKey) || processDefineKey.equals(x.getProcessdefinekey())).filter(x -> processDefineVersion == null || processDefineVersion.equals(x.getProcessdefineversion())).filter(x -> !StringUtils.hasText(nodeId) || nodeId.equals(x.getBerejectednodecode())).findFirst().orElse(null);
                if (rejectNodeInfo != null) {
                    if (this.checkInEqual(rejectNodeInfo.getRejectnodecode(), rejectNodeInfo.getBerejectednodecode(), processDO)) {
                        return this.getUpcomingNodess(rejectNodeInfo.getRejectnodecode(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, stencilId, userInfoDetail);
                    }
                    String nodecodePgw = nodes.get(nodes.size() - 1).get("stencilId").toString();
                    rejectNodeInfo.setBerejectednodecode(nodecodePgw);
                    if (nodeMap.isEmpty()) {
                        return null;
                    }
                }
            }
            ArrayList outgoings = new ArrayList();
            if (jsonNode.get("properties").get("sequencefloworder") != null && jsonNode.get("properties").get("sequencefloworder").get("sequenceFlowOrder") != null) {
                jsonNode.get("properties").get("sequencefloworder").get("sequenceFlowOrder").forEach(o -> outgoings.add(o.asText()));
            } else {
                jsonNode.get("outgoing").forEach(o -> outgoings.add(o.get("resourceId").asText()));
            }
            boolean executeResult = false;
            if (outgoings.size() > 1 || "ParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                for (int i = 0; i < outgoings.size(); ++i) {
                    String outgoingId = (String)outgoings.get(i);
                    for (JsonNode flowNode : arrayNode) {
                        if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                        JsonNode properties = flowNode.get("properties");
                        JsonNode conditionNode = properties.get("conditionsequenceflow");
                        JsonNode conditionViewNode = properties.get("conditionview");
                        executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                        if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                        if (nodeMap.size() > 0 && jsonNode.get("properties").get("multiinstance_type") != null) {
                            List children = (List)nodes.get(nodes.size() - 1).get("children");
                            for (int j = 0; j < children.size(); ++j) {
                                if (!((Map)((List)children.get(j)).get(((List)children.get(j)).size() - 1)).get("stencilId").equals(stencilId)) continue;
                                if (!skipUser) {
                                    ((List)children.get(j)).add(nodeMap);
                                }
                                if (((Map)((List)children.get(j)).get(((List)children.get(j)).size() - 1)).get("resourceId") != null) {
                                    ((Map)((List)children.get(j)).get(((List)children.get(j)).size() - 1)).put("stencilId", ((Map)((List)children.get(j)).get(((List)children.get(j)).size() - 1)).get("resourceId"));
                                }
                                stencilId = ((Map)((List)children.get(j)).get(((List)children.get(j)).size() - 1)).get("stencilId").toString();
                                break;
                            }
                        }
                        R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, stencilId, userInfoDetail);
                        return upcomingNodess;
                    }
                }
            } else {
                String outgoingId = (String)outgoings.get(0);
                for (JsonNode flowNode : arrayNode) {
                    if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                    JsonNode properties = flowNode.get("properties");
                    JsonNode conditionNode = properties.get("conditionsequenceflow");
                    JsonNode conditionViewNode = properties.get("conditionview");
                    executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                    if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                    if (nodeMap.size() > 0 && jsonNode.get("properties").get("multiinstance_type") != null || nodeMap.size() > 0 && !types.contains(jsonNode.get("stencil").get(FIELD_ID).asText()) && !jsonNode.get("stencil").get("nodeType").asText().equals("AUTO")) {
                        List children = (List)nodes.get(nodes.size() - 1).get("children");
                        for (int i = 0; i < children.size(); ++i) {
                            if (((List)children.get(i)).isEmpty() || !((Map)((List)children.get(i)).get(((List)children.get(i)).size() - 1)).get("stencilId").equals(stencilId)) continue;
                            if (!skipUser) {
                                ((List)children.get(i)).add(nodeMap);
                            }
                            stencilId = ((Map)((List)children.get(i)).get(((List)children.get(i)).size() - 1)).get("stencilId").toString();
                            break;
                        }
                    }
                    R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, stencilId, userInfoDetail);
                    return upcomingNodess;
                }
            }
            return R.error((String)(nodeCodeName + VaWorkFlowI18nUtils.getInfo("va.workflow.checkconfigurationinfo")));
        }
        return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfailed"));
    }

    private boolean checkInEqual(String rejectnodecode, String berejectednodecode, ProcessDO processDO) {
        String processId = processDO.getId();
        Map<String, ProcessNodeDO> processNodeDOMap = VaContext.getVaWorkflowContext().getProcessNodeList().stream().filter(x -> SYSTEM_CODE_WORKFLOW.equals(x.getSyscode())).collect(Collectors.toMap(ProcessNodeDO::getNodeid, o -> o, (o1, o2) -> o1));
        ProcessNodeDO rejectNode = processNodeDOMap.values().stream().filter(o -> o.getNodecode().equals(rejectnodecode)).findFirst().orElse(null);
        ProcessNodeDO beRejectedNode = processNodeDOMap.values().stream().filter(o -> o.getNodecode().equals(berejectednodecode)).findFirst().orElse(null);
        if (rejectNode == null || beRejectedNode == null) {
            return false;
        }
        ProcessNodeDO rejectPgw = processNodeDOMap.get(rejectNode.getPgwnodeid());
        ProcessNodeDO beRejectedPgw = processNodeDOMap.get(beRejectedNode.getPgwnodeid());
        if (rejectPgw == null || beRejectedPgw == null) {
            return false;
        }
        return Objects.equals(rejectPgw.getNodecode(), beRejectedPgw.getNodecode());
    }

    private R getUpcomingNodess(String nodeId, ArrayNode arrayNode, List<Map<String, Object>> nodes, Map<String, Object> params, String defineKey, Integer version, ProcessDO processDO, boolean calparticipaint, Map<String, UserDO> userCache, boolean isParent, boolean userInfoDetail) {
        HashMap<String, Object> nodeMap = new HashMap<String, Object>();
        for (JsonNode jsonNode : arrayNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (!nodeId.equals(resourceId)) continue;
            boolean skipUser = false;
            String nodeCodeName = jsonNode.get("properties").get("name").asText();
            if (calparticipaint) {
                if ("EndNoneEvent".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    this.autoNodeFilter(nodes, defineKey, version);
                    HashMap<String, Object> endNode = new HashMap<String, Object>();
                    endNode.put("nodeName", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.end"));
                    VaWorkFlowI18nUtils.dealNodeMap("\u7ed3\u675f", endNode);
                    endNode.put("auditState", 3);
                    endNode.put("nodeType", "EndNoneEvent");
                    nodes.add(endNode);
                    R r = new R();
                    r.put("data", nodes);
                    return r;
                }
                JsonNode multiinstanceTypeNode = jsonNode.get("properties").get("multiinstance_type");
                if ("SubProcess".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    nodeMap.put("auditInfo", new LinkedHashSet());
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("nodeType", "SubProcess");
                    nodeMap.put("stencilId", resourceId);
                    ArrayList<ArrayList<Map<String, Object>>> children = new ArrayList<ArrayList<Map<String, Object>>>();
                    nodeMap.put("children", children);
                    JsonNode properties = jsonNode.get("properties");
                    String multiinstanceType = properties.get("multiinstance_type").asText();
                    JsonNode subprocessbranchstrategy = properties.get("subprocessbranchstrategy").get(0);
                    if (!"Parallel".equals(multiinstanceType)) {
                        ArrayList<Map<String, Object>> subNodes = new ArrayList<Map<String, Object>>();
                        children.add(subNodes);
                        JsonNode childShapeNode = VaWorkflowNodeUtils.adjustChildShapeOrder(jsonNode.get("childShapes"), arrayNode);
                        this.calculSubProcessBranch(subNodes, new SubProcessInfoDTO(), childShapeNode, userCache, null, null, params, processDO, null, userInfoDetail);
                    } else {
                        if (subprocessbranchstrategy == null) {
                            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.subprocessbranchempty"));
                        }
                        JsonNode skipemptybranchstrategyJson = properties.get("skipemptybranchstrategy");
                        boolean skipEmptyBranchStrategy = skipemptybranchstrategyJson != null && skipemptybranchstrategyJson.asBoolean();
                        String strategyModuleName = subprocessbranchstrategy.get("subProcessBranchStrategyModuleName").asText();
                        String strategyName = subprocessbranchstrategy.get("subProcessBranchStrategyName").asText();
                        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
                        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
                        HashMap<String, Object> params1 = new HashMap<String, Object>();
                        params1.put("assignParam", subprocessbranchstrategy.get("items"));
                        params1.put(KEY_WORKFLOW_DTO, workflowDTO);
                        params1.put(KEY_PROCESS_DO, vaWorkflowContext.getProcessDO());
                        params1.put("processForecast", true);
                        Map branchMap = this.getWorkflowStrategyService().execute(strategyModuleName, strategyName, params1);
                        ArrayList<String> branches = new ArrayList<String>();
                        for (String branchKey : branchMap.keySet()) {
                            branches.add(branchKey);
                        }
                        if (branches.isEmpty()) {
                            if (!skipEmptyBranchStrategy) {
                                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.subprocessfailsplit"));
                            }
                            nodeMap = new HashMap();
                        } else {
                            for (String branch : branches) {
                                ArrayList<Map<String, Object>> subNodes = new ArrayList<Map<String, Object>>();
                                children.add(subNodes);
                                SubProcessInfoDTO subProcessInfoDTO = new SubProcessInfoDTO();
                                subProcessInfoDTO.setSubProcessBranch(branch);
                                JsonNode childShapeNode = VaWorkflowNodeUtils.adjustChildShapeOrder(jsonNode.get("childShapes"), arrayNode);
                                this.calculSubProcessBranch(subNodes, subProcessInfoDTO, childShapeNode, userCache, null, null, params, processDO, branchMap, userInfoDetail);
                            }
                        }
                    }
                } else if (multiinstanceTypeNode != null) {
                    ArrayNode usertaskassignment;
                    String multiinstanceType = multiinstanceTypeNode.asText();
                    int counterSignFlag = 0;
                    if (!"None".equals(multiinstanceType)) {
                        counterSignFlag = 1;
                        String multiinstance = jsonNode.get("properties").get("multiinstance_collection").asText();
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            usertaskassignment = (ArrayNode)objectMapper.readTree(multiinstance);
                        }
                        catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);
                            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.failedgetsignature"));
                        }
                    } else {
                        usertaskassignment = (ArrayNode)jsonNode.get("properties").get("usertaskassignment");
                    }
                    WorkflowStrategySevice workflowStrategySevice = (WorkflowStrategySevice)ApplicationContextRegister.getBean(WorkflowStrategySevice.class);
                    Set<String> allUserIdSet = new LinkedHashSet<String>();
                    for (JsonNode assignmentNode : usertaskassignment) {
                        try {
                            String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                            String strategyName = assignmentNode.get("strategyName").asText();
                            HashMap<String, Object> calParams = new HashMap<String, Object>();
                            calParams.put("variables", params);
                            calParams.put("assignParam", assignmentNode.get("items"));
                            WorkflowDTO workflowDTO = new WorkflowDTO();
                            workflowDTO.setBizType(processDO.getBizmodule());
                            workflowDTO.setBizDefine(processDO.getBiztype());
                            workflowDTO.setBizCode(processDO.getBizcode());
                            calParams.put(KEY_WORKFLOW_DTO, workflowDTO);
                            calParams.put(KEY_PROCESS_DO, processDO);
                            calParams.put("currentNodeCode", nodeId);
                            allUserIdSet.addAll(workflowStrategySevice.execute(strategyModuleName, strategyName, calParams));
                        }
                        catch (Exception e) {
                            log.error("\u9884\u6d4b\u6267\u884c\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
                        }
                    }
                    if ((allUserIdSet = this.filterApprover(allUserIdSet, jsonNode, null)).isEmpty() && jsonNode.get("properties").get(SKIP_EMPTY_PARTICIPANT).asBoolean()) {
                        skipUser = true;
                    }
                    LinkedHashSet<String> allUsers = new LinkedHashSet<String>();
                    ArrayList allUserList = new ArrayList();
                    for (String userId : allUserIdSet) {
                        UserDO user = this.getUser(userId, userCache);
                        String userName = user.getName();
                        allUsers.add(userName);
                        HashMap<String, String> userInfoMap = new HashMap<String, String>(2);
                        userInfoMap.put("auditUser", userName);
                        userInfoMap.put("auditUserInfoDetail", (String)user);
                        allUserList.add(userInfoMap);
                    }
                    if (userInfoDetail) {
                        nodeMap.put("auditInfo", allUserList);
                    } else {
                        nodeMap.put("auditInfo", allUsers);
                    }
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("stencilId", jsonNode.get("resourceId").asText());
                    nodeMap.put("counterSignFlag", counterSignFlag);
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                } else if (jsonNode.get("stencil").get(FIELD_ID) != null && !types.contains(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("stencilId", jsonNode.get("resourceId").asText());
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                } else {
                    if ("JoinParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText()) && !isParent) {
                        R r = new R();
                        r.put("data", nodes);
                        r.put("stencilId", (Object)jsonNode.get("resourceId").asText());
                        r.put("nodeType", (Object)"JoinParallelGateway");
                        return r;
                    }
                    nodeMap.put("auditInfo", new LinkedHashSet());
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                    nodeMap.put("stencilId", resourceId);
                }
            }
            calparticipaint = true;
            if (processDO != null) {
                String processDefineKey = processDO.getDefinekey();
                BigDecimal processDefineVersion = processDO.getDefineversion();
                ProcessRejectNodeDO rejectNodeInfo = VaContext.getVaWorkflowContext().getRejectSkipNodeInfoList().stream().filter(x -> !StringUtils.hasText(processDefineKey) || processDefineKey.equals(x.getProcessdefinekey())).filter(x -> processDefineVersion == null || processDefineVersion.equals(x.getProcessdefineversion())).filter(x -> !StringUtils.hasText(nodeId) || nodeId.equals(x.getBerejectednodecode())).findFirst().orElse(null);
                if (rejectNodeInfo != null) {
                    String code;
                    if (nodeMap.size() != 0) {
                        nodes.add(nodeMap);
                    }
                    String berejectednodecode = rejectNodeInfo.getBerejectednodecode();
                    try {
                        code = this.checkInPgw(berejectednodecode, rejectNodeInfo.getRejectnodecode(), processDO, nodes, userCache, arrayNode, params, defineKey, version, calparticipaint, userInfoDetail);
                    }
                    catch (Exception e) {
                        this.autoNodeFilter(nodes, defineKey, version);
                        HashMap<String, Object> endNode = new HashMap<String, Object>();
                        endNode.put("nodeName", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.end"));
                        VaWorkFlowI18nUtils.dealNodeMap("\u7ed3\u675f", endNode);
                        endNode.put("auditState", 3);
                        endNode.put("nodeType", "EndNoneEvent");
                        nodes.add(endNode);
                        Iterator r = new R();
                        r.put("data", nodes);
                        return r;
                    }
                    if (code == null) {
                        return this.getUpcomingNodess(rejectNodeInfo.getRejectnodecode(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, false, userInfoDetail);
                    }
                    return this.getUpcomingNodess(code, arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, true, userInfoDetail);
                }
            }
            ArrayList outgoings = new ArrayList();
            if (jsonNode.get("properties").get("sequencefloworder") != null && jsonNode.get("properties").get("sequencefloworder").get("sequenceFlowOrder") != null) {
                jsonNode.get("properties").get("sequencefloworder").get("sequenceFlowOrder").forEach(o -> outgoings.add(o.asText()));
            } else {
                jsonNode.get("outgoing").forEach(o -> outgoings.add(o.get("resourceId").asText()));
            }
            boolean executeResult = false;
            if (outgoings.size() > 1 || "ParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                if ("ParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    ArrayList<List> children = new ArrayList<List>(outgoings.size());
                    String resourceIdChildren = null;
                    for (int i = 0; i < outgoings.size(); ++i) {
                        ArrayList<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>(outgoings.size());
                        String outgoingId = (String)outgoings.get(i);
                        for (JsonNode flowNode : arrayNode) {
                            if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                            JsonNode properties = flowNode.get("properties");
                            JsonNode conditionNode = properties.get("conditionsequenceflow");
                            JsonNode conditionViewNode = properties.get("conditionview");
                            executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                            if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                            R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, childrenList, params, defineKey, version, processDO, calparticipaint, userCache, false, userInfoDetail);
                            if (upcomingNodess.getCode() == 1) {
                                return upcomingNodess;
                            }
                            if (((List)upcomingNodess.get((Object)"data")).isEmpty()) continue;
                            children.add((List)upcomingNodess.get((Object)"data"));
                            if (upcomingNodess.get((Object)"stencilId") == null) continue;
                            resourceIdChildren = (String)upcomingNodess.get((Object)"stencilId");
                        }
                    }
                    if (!isParent && !skipUser) {
                        nodes.add(nodeMap);
                        nodeMap.put("children", children);
                    }
                    if (CollectionUtils.isEmpty((List)nodeMap.get("children")) && !CollectionUtils.isEmpty(nodeMap) && !isParent) {
                        return R.error((String)"\u63d0\u4ea4\u8282\u70b9\u6ca1\u6709\u53ef\u8fbe\u540e\u7ee7\u8282\u70b9");
                    }
                    if (resourceIdChildren == null) {
                        return R.ok().put("data", nodes);
                    }
                    R upcomingNodess = this.getUpcomingNodess(resourceIdChildren, arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, true, userInfoDetail);
                    upcomingNodess.put("data", nodes);
                    return upcomingNodess;
                }
                block13: for (int i = 0; i < outgoings.size(); ++i) {
                    String outgoingId = (String)outgoings.get(i);
                    for (JsonNode flowNode : arrayNode) {
                        if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                        if (resourceId.equals(flowNode.get("target").get("resourceId").asText())) continue block13;
                        JsonNode properties = flowNode.get("properties");
                        JsonNode conditionNode = properties.get("conditionsequenceflow");
                        JsonNode conditionViewNode = properties.get("conditionview");
                        executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                        if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                        if (!(nodeMap.size() <= 0 || skipUser || jsonNode.get("properties").get("multiinstance_type") == null && (types.contains(jsonNode.get("stencil").get(FIELD_ID).asText()) || "AUTO".equals(jsonNode.get("stencil").get("nodeType").asText())))) {
                            nodes.add(nodeMap);
                        }
                        R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, false, userInfoDetail);
                        return upcomingNodess;
                    }
                }
            } else {
                String outgoingId = (String)outgoings.get(0);
                for (JsonNode flowNode : arrayNode) {
                    if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                    JsonNode properties = flowNode.get("properties");
                    JsonNode conditionNode = properties.get("conditionsequenceflow");
                    JsonNode conditionViewNode = properties.get("conditionview");
                    executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                    if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                    if (!(nodeMap.size() <= 0 || skipUser || jsonNode.get("properties").get("multiinstance_type") == null && (types.contains(jsonNode.get("stencil").get(FIELD_ID).asText()) || "AUTO".equals(jsonNode.get("stencil").get("nodeType").asText())))) {
                        nodes.add(nodeMap);
                    }
                    R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, false, userInfoDetail);
                    return upcomingNodess;
                }
            }
            return R.error((String)(nodeCodeName + VaWorkFlowI18nUtils.getInfo("va.workflow.checkconfigurationinfo")));
        }
        return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfailed"));
    }

    private String checkInPgw(String berejectednodecode, String rejectnode, ProcessDO processDO, List<Map<String, Object>> nodes, Map<String, UserDO> userCache, ArrayNode arrayNode, Map<String, Object> params, String defineKey, Integer version, boolean calparticipaint, boolean userInfoDetail) {
        List processNodeDOS = VaContext.getVaWorkflowContext().getProcessNodeList().stream().filter(x -> Objects.equals(x.getSyscode(), SYSTEM_CODE_WORKFLOW)).sorted(Comparator.comparing(ProcessNodeDO::getCompletetime, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
        for (int i = 0; i < processNodeDOS.size(); ++i) {
            ProcessNodeDO processNodeDO = (ProcessNodeDO)processNodeDOS.get(i);
            if (!processNodeDO.getNodecode().equals(rejectnode)) continue;
            Optional<ProcessNodeDO> first = processNodeDOS.stream().filter(o -> o.getNodecode().equals(berejectednodecode)).findFirst();
            if (processNodeDO.getPgwbranch() != null && !processNodeDO.getPgwbranch().equals(first.get().getPgwbranch())) continue;
            return null;
        }
        List collect = processNodeDOS.stream().filter(o -> o.getNodecode().equals(rejectnode)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            for (JsonNode jsonNode : arrayNode) {
                String s = jsonNode.get("stencil").get(FIELD_ID).asText();
                if (!"EndNoneEvent".equals(s)) continue;
                if (!rejectnode.equals(jsonNode.get("resourceId").asText())) break;
                throw new RuntimeException();
            }
            log.error("\u4e1a\u52a1\u7f16\u53f7\uff1a" + processDO.getBizcode() + "\uff0c\u672a\u627e\u5230\u9a73\u56de\u4e0d\u91cd\u8d70\u8282\u70b9\uff0c\u9884\u6d4b\u7ed3\u675f\uff1a" + rejectnode);
            throw new RuntimeException();
        }
        ProcessNodeDO rejectnodeInfo = (ProcessNodeDO)collect.get(0);
        try {
            String pgwId = rejectnodeInfo.getPgwnodeid();
            ProcessNodeDO pgwInfo = processNodeDOS.stream().filter(o -> o.getNodeid().equals(pgwId)).findFirst().get();
            List nodeDOS = processNodeDOS.stream().filter(o -> pgwId.equals(o.getPgwnodeid()) && !o.getNodeid().equals(o.getPgwnodeid())).collect(Collectors.toList());
            ArrayList<String> completeIds = new ArrayList<String>();
            for (int i = 0; i < nodeDOS.size(); ++i) {
                if (((ProcessNodeDO)nodeDOS.get(i)).getNodecode().equals(rejectnode)) continue;
                completeIds.add(((ProcessNodeDO)nodeDOS.get(i)).getNodecode());
            }
            Set<String> branchIds = this.getCompleteIds(rejectnode);
            Set<String> orderCompleteNodes = processNodeDOS.stream().filter(o -> rejectnodeInfo.getPgwnodeid().equals(o.getPgwnodeid()) && !rejectnodeInfo.getPgwbranch().equals(o.getPgwbranch())).map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
            orderCompleteNodes.addAll(branchIds);
            this.getUpcomingNodess(pgwInfo.getNodecode(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, true, orderCompleteNodes, userInfoDetail);
            return pgwInfo.getNodecode();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private Set<String> getCompleteIds(String rejectnode) {
        List<ProcessNodeDO> processNodeDOS = VaContext.getVaWorkflowContext().getProcessNodeList().stream().filter(x -> Objects.equals(x.getSyscode(), SYSTEM_CODE_WORKFLOW)).sorted(Comparator.comparing(ProcessNodeDO::getCompletetime, Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
        ProcessNodeDO processNodeDO = processNodeDOS.stream().filter(o -> o.getNodecode().equals(rejectnode)).findFirst().get();
        String pgwbranch = processNodeDO.getPgwbranch();
        ArrayList<ProcessNodeDO> culcPgwNode = new ArrayList<ProcessNodeDO>();
        this.culcPgwNode(culcPgwNode, pgwbranch, rejectnode, processNodeDOS);
        Set<String> strings = culcPgwNode.stream().collect(Collectors.groupingBy(o -> o.getNodecode())).keySet();
        strings.remove(rejectnode);
        return strings;
    }

    private void culcPgwNode(List<ProcessNodeDO> culcPgwNode, String pgwbranch, String rejectnode, List<ProcessNodeDO> processNodeDOS) {
        Optional<ProcessNodeDO> oldNodes;
        List branchNodes = processNodeDOS.stream().filter(o -> pgwbranch.equals(o.getPgwbranch())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(branchNodes)) {
            return;
        }
        culcPgwNode.addAll(branchNodes);
        processNodeDOS.removeAll(branchNodes);
        Optional<ProcessNodeDO> processNodeDO = branchNodes.stream().filter(o -> !o.getNodecode().equals(rejectnode)).findFirst();
        if (processNodeDO.isPresent()) {
            oldNodes = processNodeDOS.stream().filter(o -> o.getNodecode().equals(((ProcessNodeDO)processNodeDO.get()).getNodecode())).findFirst();
            if (!oldNodes.isPresent()) {
                return;
            }
        } else {
            return;
        }
        ProcessNodeDO oldNode = oldNodes.get();
        this.culcPgwNode(culcPgwNode, oldNode.getPgwbranch(), oldNode.getNodecode(), processNodeDOS);
    }

    private R getUpcomingNodess(String nodeId, ArrayNode arrayNode, List<Map<String, Object>> nodes, Map<String, Object> params, String defineKey, Integer version, ProcessDO processDO, boolean calparticipaint, Map<String, UserDO> userCache, boolean isParent, Set<String> completeIds, boolean userInfoDetail) {
        HashMap<String, Object> nodeMap = new HashMap<String, Object>();
        for (JsonNode jsonNode : arrayNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (!nodeId.equals(resourceId)) continue;
            boolean skipUser = false;
            String nodeCodeName = jsonNode.get("properties").get("name").asText();
            if (calparticipaint) {
                JsonNode multiinstanceTypeNode = jsonNode.get("properties").get("multiinstance_type");
                if (multiinstanceTypeNode != null) {
                    ArrayNode usertaskassignment;
                    String multiinstanceType = multiinstanceTypeNode.asText();
                    if (!"None".equals(multiinstanceType)) {
                        String multiinstance = jsonNode.get("properties").get("multiinstance_collection").asText();
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            usertaskassignment = (ArrayNode)objectMapper.readTree(multiinstance);
                        }
                        catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);
                            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.failedgetsignature"));
                        }
                    } else {
                        usertaskassignment = (ArrayNode)jsonNode.get("properties").get("usertaskassignment");
                    }
                    WorkflowStrategySevice workflowStrategySevice = (WorkflowStrategySevice)ApplicationContextRegister.getBean(WorkflowStrategySevice.class);
                    Set<String> allUserIdSet = new LinkedHashSet<String>();
                    for (JsonNode assignmentNode : usertaskassignment) {
                        try {
                            String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                            String strategyName = assignmentNode.get("strategyName").asText();
                            HashMap<String, Object> calParams = new HashMap<String, Object>();
                            calParams.put("variables", params);
                            calParams.put("assignParam", assignmentNode.get("items"));
                            WorkflowDTO workflowDTO = new WorkflowDTO();
                            workflowDTO.setBizType(processDO.getBizmodule());
                            workflowDTO.setBizDefine(processDO.getBiztype());
                            workflowDTO.setBizCode(processDO.getBizcode());
                            calParams.put(KEY_WORKFLOW_DTO, workflowDTO);
                            calParams.put(KEY_PROCESS_DO, processDO);
                            calParams.put("currentNodeCode", nodeId);
                            allUserIdSet.addAll(workflowStrategySevice.execute(strategyModuleName, strategyName, calParams));
                        }
                        catch (Exception e) {
                            log.error("\u9884\u6d4b\u6267\u884c\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
                        }
                    }
                    if ((allUserIdSet = this.filterApprover(allUserIdSet, jsonNode, null)).isEmpty() && jsonNode.get("properties").get(SKIP_EMPTY_PARTICIPANT).asBoolean()) {
                        skipUser = true;
                    }
                    LinkedHashSet<String> allUsers = new LinkedHashSet<String>();
                    ArrayList allUserList = new ArrayList();
                    for (String userId : allUserIdSet) {
                        UserDO user = this.getUser(userId, userCache);
                        String userName = user.getName();
                        allUsers.add(userName);
                        HashMap<String, String> userInfoMap = new HashMap<String, String>(2);
                        userInfoMap.put("auditUser", userName);
                        userInfoMap.put("auditUserInfoDetail", (String)user);
                        allUserList.add(userInfoMap);
                    }
                    if (userInfoDetail) {
                        nodeMap.put("auditInfo", allUserList);
                    } else {
                        nodeMap.put("auditInfo", allUsers);
                    }
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                    nodeMap.put("stencilId", jsonNode.get("resourceId").asText());
                } else if (jsonNode.get("stencil").get(FIELD_ID) != null && !types.contains(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("stencilId", jsonNode.get("resourceId").asText());
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                } else {
                    if ("JoinParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText()) && !isParent) {
                        R r = new R();
                        r.put("data", nodes);
                        r.put("stencilId", (Object)jsonNode.get("resourceId").asText());
                        r.put("nodeType", (Object)jsonNode.get("stencil").get(FIELD_ID).asText());
                        return r;
                    }
                    nodeMap.put("auditInfo", new LinkedHashSet());
                    nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                    nodeMap.put("auditState", 3);
                    nodeMap.put("nodeName", nodeCodeName);
                    nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
                    nodeMap.put("stencilId", resourceId);
                }
            }
            calparticipaint = true;
            ArrayList outgoings = new ArrayList();
            if (jsonNode.get("properties").get("sequencefloworder") != null && jsonNode.get("properties").get("sequencefloworder").get("sequenceFlowOrder") != null) {
                jsonNode.get("properties").get("sequencefloworder").get("sequenceFlowOrder").forEach(o -> outgoings.add(o.asText()));
            } else {
                jsonNode.get("outgoing").forEach(o -> outgoings.add(o.get("resourceId").asText()));
            }
            boolean executeResult = false;
            if (outgoings.size() > 1 || "ParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                if ("ParallelGateway".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) {
                    ArrayList<List> children = new ArrayList<List>(outgoings.size());
                    String resourceIdChildren = null;
                    for (int i = 0; i < outgoings.size(); ++i) {
                        ArrayList<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>(outgoings.size());
                        String outgoingId = (String)outgoings.get(i);
                        for (JsonNode flowNode : arrayNode) {
                            if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                            JsonNode properties = flowNode.get("properties");
                            JsonNode conditionNode = properties.get("conditionsequenceflow");
                            JsonNode conditionViewNode = properties.get("conditionview");
                            executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                            if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                            R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, childrenList, params, defineKey, version, processDO, calparticipaint, userCache, false, completeIds, userInfoDetail);
                            if (upcomingNodess.getCode() == 1) {
                                return upcomingNodess;
                            }
                            if (!((List)upcomingNodess.get((Object)"data")).isEmpty()) {
                                children.add((List)upcomingNodess.get((Object)"data"));
                            }
                            resourceIdChildren = (String)upcomingNodess.get((Object)"stencilId");
                        }
                    }
                    if (nodes.get(nodes.size() - 1).get("children") == null && !skipUser) {
                        nodeMap.put("children", children);
                        nodes.add(nodeMap);
                    }
                    if (CollectionUtils.isEmpty((List)nodeMap.get("children")) && !CollectionUtils.isEmpty(nodeMap)) {
                        return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.submitnodenotnextnode"));
                    }
                    if (resourceIdChildren == null) {
                        return R.ok().put("data", nodes);
                    }
                    return R.ok();
                }
                for (int i = 0; i < outgoings.size(); ++i) {
                    String outgoingId = (String)outgoings.get(i);
                    for (JsonNode flowNode : arrayNode) {
                        if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                        JsonNode properties = flowNode.get("properties");
                        JsonNode conditionNode = properties.get("conditionsequenceflow");
                        JsonNode conditionViewNode = properties.get("conditionview");
                        executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                        if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                        if (nodeMap.size() > 0 && jsonNode.get("properties").get("multiinstance_type") != null && !skipUser) {
                            nodes.add(nodeMap);
                        }
                        R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, false, completeIds, userInfoDetail);
                        return upcomingNodess;
                    }
                }
            } else {
                String outgoingId = (String)outgoings.get(0);
                for (JsonNode flowNode : arrayNode) {
                    if (!outgoingId.equals(flowNode.get("resourceId").asText())) continue;
                    JsonNode properties = flowNode.get("properties");
                    JsonNode conditionNode = properties.get("conditionsequenceflow");
                    JsonNode conditionViewNode = properties.get("conditionview");
                    executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(defineKey, version, params, conditionNode.toString());
                    if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) continue;
                    if ((nodeMap.size() > 0 && jsonNode.get("properties").get("multiinstance_type") != null || nodeMap.size() > 0 && !types.contains(jsonNode.get("stencil").get(FIELD_ID).asText()) && !jsonNode.get("stencil").get("nodeType").asText().equals("AUTO")) && !completeIds.contains(nodeMap.get("stencilId")) && !skipUser) {
                        nodes.add(nodeMap);
                    }
                    R upcomingNodess = this.getUpcomingNodess(flowNode.get("target").get("resourceId").asText(), arrayNode, nodes, params, defineKey, version, processDO, calparticipaint, userCache, false, completeIds, userInfoDetail);
                    return upcomingNodess;
                }
            }
            return R.error((String)(nodeCodeName + VaWorkFlowI18nUtils.getInfo("va.workflow.checkconfigurationinfo")));
        }
        return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfailed"));
    }

    private void autoNodeFilter(List<Map<String, Object>> nodes, String defineKey, int version) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        WorkflowModel workflowModel = (WorkflowModel)customParam.get("workflowModel");
        HashSet<String> resourceIds = new HashSet<String>();
        for (Map<String, Object> node : nodes) {
            if (VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.start").equals(node.get("nodeName"))) continue;
            resourceIds.add((String)node.get("stencilId"));
        }
        Map<String, Map<String, Object>> properties = workflowModel.getNodesProperty(defineKey, version, resourceIds);
        ArrayList<Map<String, Object>> removeNodes = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> node : nodes) {
            if (VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.start").equals(node.get("nodeName")) || !"AutoManualTask".equals(properties.get(node.get("stencilId")).get("nodeType"))) continue;
            removeNodes.add(node);
        }
        nodes.removeAll(removeNodes);
    }

    private UserDO getUser(String userId, Map<String, UserDO> userCache) {
        UserDO userDO;
        if (userCache.containsKey(userId)) {
            userDO = userCache.get(userId);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);
            userDO = this.authUserClient.get(userDTO);
            if (userDO != null) {
                userCache.put(userId, userDO);
            } else {
                userDO = new UserDO();
            }
        }
        return userDO;
    }

    private boolean calculSubProcessBranch(List<Map<String, Object>> subNodes, SubProcessInfoDTO subProcessInfoDTO, JsonNode childShapes, Map<String, UserDO> userCache, String stencilId, List<Map<String, Object>> completeNodes, Map<String, Object> params, ProcessDO processDO, Map<String, Object> branchMap, boolean userInfoDetail) {
        Object auditInfo;
        boolean showCurBranch = false;
        String curUserId = ShiroUtil.getUser().getId();
        String curUserUser = this.getUser(curUserId, userCache).getName();
        if (processDO.getStartuser() == null || processDO.getStartuser().equals(curUserId)) {
            showCurBranch = true;
        } else {
            OptionItemDTO optionItemDTO = new OptionItemDTO();
            optionItemDTO.setName("WF1014");
            OptionItemVO optionItemVO = (OptionItemVO)this.workflowOptionService.list(optionItemDTO).get(0);
            String val = optionItemVO.getVal();
            if (val.equals("1")) {
                showCurBranch = true;
            }
        }
        ArrayList<String> childShapesIds = new ArrayList<String>();
        HashMap<String, String> nodeTypeMap = new HashMap<String, String>(childShapes.size());
        for (JsonNode childShape : childShapes) {
            String resourceId = childShape.get("resourceId").asText();
            childShapesIds.add(resourceId);
            nodeTypeMap.put(resourceId, childShape.get("stencil").get(FIELD_ID).asText());
        }
        boolean rejectStatus = false;
        if (!childShapesIds.contains(stencilId)) {
            rejectStatus = true;
            if (!CollectionUtils.isEmpty(completeNodes)) {
                int i;
                for (i = subNodes.size() - 1; i >= 0; --i) {
                    if (!subNodes.get(i).get("auditState").equals(1) || !childShapesIds.contains(subNodes.get(i).get("stencilId"))) continue;
                    subNodes.remove(i);
                }
                for (i = 0; i < completeNodes.size(); ++i) {
                    if (!completeNodes.get(i).get("stencilId").equals(stencilId)) continue;
                    for (int j = i + 1; j < completeNodes.size() - 1; ++j) {
                        Map map = (Map)((HashMap)completeNodes.get(j)).clone();
                        auditInfo = (List)completeNodes.get(j).get("auditInfo");
                        LinkedHashSet<String> userNames = new LinkedHashSet<String>();
                        ArrayList<Map> allUserList = new ArrayList<Map>();
                        Iterator iterator = auditInfo.iterator();
                        while (iterator.hasNext()) {
                            Map stringObjectMap = (Map)iterator.next();
                            if (stringObjectMap.get("auditUser") == null) continue;
                            userNames.add((String)stringObjectMap.get("auditUser"));
                            allUserList.add((Map)stringObjectMap.get("auditUserInfoDetail"));
                        }
                        if (StringUtils.hasText(subProcessInfoDTO.getSubProcessBranchName())) {
                            map.put("nodeName", map.get("nodeName") + " \u2014 " + subProcessInfoDTO.getSubProcessBranchName());
                        }
                        map.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
                        map.put("auditState", 3);
                        map.put("nodeType", nodeTypeMap.get(stencilId));
                        if (userInfoDetail) {
                            map.put("auditInfo", allUserList);
                        } else {
                            map.put("auditInfo", userNames);
                        }
                        subNodes.add(map);
                    }
                    break;
                }
            }
        }
        boolean skip = true;
        for (int i = 0; i < childShapes.size(); ++i) {
            String stencilId1;
            int j;
            JsonNode jsonNode = childShapes.get(i);
            if ("StartNoneEvent".equals(jsonNode.get("stencil").get(FIELD_ID).asText()) || "EndNoneEvent".equals(jsonNode.get("stencil").get(FIELD_ID).asText()) || "SequenceFlow".equals(jsonNode.get("stencil").get(FIELD_ID).asText())) continue;
            String nodeCode = jsonNode.get("resourceId").asText();
            if (stencilId != null && !rejectStatus) {
                if (nodeCode.equals(stencilId)) {
                    skip = false;
                    continue;
                }
                if (skip) continue;
            }
            JsonNode multiinstanceTypeNode = jsonNode.get("properties").get("multiinstance_type");
            ArrayNode usertaskassignment = null;
            int counterSignFlag = 0;
            if (multiinstanceTypeNode != null) {
                String multiinstanceType = multiinstanceTypeNode.asText();
                if (!"None".equals(multiinstanceType)) {
                    counterSignFlag = 1;
                    String multiinstance = jsonNode.get("properties").get("multiinstance_collection").asText();
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        usertaskassignment = (ArrayNode)objectMapper.readTree(multiinstance);
                    }
                    catch (JsonProcessingException e) {
                        log.error(e.getMessage(), e);
                    }
                } else {
                    usertaskassignment = (ArrayNode)jsonNode.get("properties").get("usertaskassignment");
                }
            }
            HashMap<String, Object> nodeMap = new HashMap<String, Object>();
            WorkflowStrategySevice workflowStrategySevice = (WorkflowStrategySevice)ApplicationContextRegister.getBean(WorkflowStrategySevice.class);
            Set<String> allUserIdSet = new LinkedHashSet<String>();
            VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
            if (usertaskassignment != null) {
                for (JsonNode assignmentNode : usertaskassignment) {
                    try {
                        String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                        String strategyName = assignmentNode.get("strategyName").asText();
                        HashMap<String, Object> calParams = new HashMap<String, Object>();
                        calParams.put("variables", params);
                        calParams.put("assignParam", assignmentNode.get("items"));
                        calParams.put("subProcessInfoDTO", subProcessInfoDTO);
                        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
                        calParams.put(KEY_WORKFLOW_DTO, workflowDTO);
                        calParams.put("currentNodeCode", stencilId);
                        calParams.put(KEY_PROCESS_DO, processDO);
                        allUserIdSet.addAll(workflowStrategySevice.execute(strategyModuleName, strategyName, calParams));
                    }
                    catch (Exception e) {
                        log.error("\u5b50\u6d41\u7a0b\u9884\u6d4b\u6267\u884c\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
                    }
                }
            }
            allUserIdSet = this.filterApprover(allUserIdSet, jsonNode, subProcessInfoDTO);
            LinkedHashSet<String> allUsers = new LinkedHashSet<String>();
            ArrayList allUserList = new ArrayList();
            for (String userId : allUserIdSet) {
                UserDO user = this.getUser(userId, userCache);
                String userName = user.getName();
                allUsers.add(userName);
                HashMap<String, String> userInfoMap = new HashMap<String, String>(2);
                userInfoMap.put("auditUser", userName);
                userInfoMap.put("auditUserInfoDetail", (String)user);
                allUserList.add(userInfoMap);
            }
            if (userInfoDetail) {
                nodeMap.put("auditInfo", allUserList);
            } else {
                nodeMap.put("auditInfo", allUsers);
            }
            nodeMap.put("auditStatus", VaWorkFlowI18nUtils.getInfo("va.workflow.predictprocess.noapproval"));
            nodeMap.put("auditState", 3);
            nodeMap.put("counterSignFlag", counterSignFlag);
            Object nodeName = jsonNode.get("properties").get("name");
            String subProcessBranch = subProcessInfoDTO.getSubProcessBranch();
            if (branchMap != null) {
                nodeName = jsonNode.get("properties").get("name").asText() + " \u2014 " + branchMap.get(subProcessBranch);
            } else if (StringUtils.hasText(subProcessInfoDTO.getSubProcessBranchName())) {
                nodeName = jsonNode.get("properties").get("name").asText() + " \u2014 " + subProcessInfoDTO.getSubProcessBranchName();
            }
            nodeMap.put("nodeName", nodeName);
            nodeMap.put("stencilId", nodeCode);
            nodeMap.put("nodeType", jsonNode.get("stencil").get(FIELD_ID).asText());
            subNodes.add(nodeMap);
            String processDefineKey = processDO.getDefinekey();
            BigDecimal processDefineVersion = processDO.getDefineversion();
            List rejectSkipNodeInfoList = vaWorkflowContext.getRejectSkipNodeInfoList();
            rejectSkipNodeInfoList = rejectSkipNodeInfoList.stream().filter(x -> !StringUtils.hasText(processDefineKey) || processDefineKey.equals(x.getProcessdefinekey())).filter(x -> processDefineVersion == null || processDefineVersion.equals(x.getProcessdefineversion())).filter(x -> !StringUtils.hasText(nodeCode) || nodeCode.equals(x.getRejectnodecode())).filter(x -> !StringUtils.hasText(subProcessBranch) || subProcessBranch.equals(x.getSubprocessbranch())).collect(Collectors.toList());
            if (rejectSkipNodeInfoList.isEmpty()) continue;
            ArrayList<String> stencilId1s = new ArrayList<String>();
            String beRejectedNodeCode = ((ProcessRejectNodeDO)rejectSkipNodeInfoList.get(0)).getBerejectednodecode();
            for (j = subNodes.size() - 2; j >= 0 && !beRejectedNodeCode.equals(stencilId1 = subNodes.get(j).get("stencilId").toString()); --j) {
                stencilId1s.add(stencilId1);
                subNodes.remove(j);
            }
            for (j = subNodes.size() - 1; j >= 0; --j) {
                if (!stencilId1s.contains(subNodes.get(j).get("stencilId").toString())) continue;
                subNodes.remove(j);
            }
        }
        if (!showCurBranch) {
            for (Map<String, Object> subNode : subNodes) {
                auditInfo = subNode.get("auditInfo");
                if (auditInfo instanceof List) {
                    for (Map map : (List)auditInfo) {
                        if (!curUserUser.equals(map.get("auditUser"))) continue;
                        showCurBranch = true;
                    }
                }
                if (!(auditInfo instanceof Set)) continue;
                for (String s : (Set)auditInfo) {
                    if (!curUserUser.equals(s)) continue;
                    showCurBranch = true;
                }
            }
        }
        return showCurBranch;
    }

    private Set<String> filterApprover(Set<String> allUserIdSet, JsonNode jsonNode, SubProcessInfoDTO subProcessInfoDTO) {
        VaWorkflowContext workflowContext = VaContext.getVaWorkflowContext();
        WorkflowDTO workflowDTO = workflowContext.getWorkflowDTO();
        ProcessDO processDO = workflowContext.getProcessDO();
        JsonNode properties = jsonNode.get("properties");
        boolean globalSubmitterCannotApprove = (Boolean)workflowDTO.getExtInfo("globalSubmitterCannotApprove");
        if (VaWorkflowUtils.isSubmitterCannotApprove(globalSubmitterCannotApprove, properties)) {
            String submitUserId = processDO == null ? ShiroUtil.getUser().getId() : processDO.getStartuser();
            allUserIdSet.remove(submitUserId);
        }
        if (allUserIdSet.isEmpty()) {
            return allUserIdSet;
        }
        String nodeCode = jsonNode.get("resourceId").asText();
        String subProcessBranch = subProcessInfoDTO == null ? null : subProcessInfoDTO.getSubProcessBranch();
        Set<Object> approverBeforeLastRejection = new LinkedHashSet();
        JsonNode chooseApprover = properties.get("chooseapprover");
        String globalChooseApprover = (String)workflowContext.getWorkflowDTO().getExtInfo("globalChooseApprover");
        if (this.isChooseApprover(globalChooseApprover, chooseApprover)) {
            if ("single".equals(chooseApprover.asText())) {
                approverBeforeLastRejection = this.getApproverBeforeLastRejection(nodeCode, subProcessBranch);
            }
        } else if ("None".equals(properties.get("multiinstance_type").asText()) && this.isLimitApprover(jsonNode)) {
            approverBeforeLastRejection = VaWorkflowUtils.getApproverBeforeLastRejection(workflowDTO.getBizCode(), nodeCode, subProcessBranch);
        }
        approverBeforeLastRejection.retainAll(allUserIdSet);
        return approverBeforeLastRejection.isEmpty() ? allUserIdSet : approverBeforeLastRejection;
    }

    private boolean isChooseApprover(String globalChooseApprover, JsonNode chooseApprover) {
        if (chooseApprover == null || !StringUtils.hasText(chooseApprover.asText())) {
            return false;
        }
        String value = chooseApprover.asText();
        if ("inherit".equals(value)) {
            return !"disable".equals(globalChooseApprover);
        }
        return !"disable".equals(value);
    }

    private Set<String> getApproverBeforeLastRejection(String nodeCode, String subProcessBranch) {
        LinkedHashSet<String> resultSet = new LinkedHashSet<String>();
        VaWorkflowContext workflowContext = VaContext.getVaWorkflowContext();
        List rejectSkipNodeInfoList = workflowContext.getRejectSkipNodeInfoList().stream().filter(x -> !StringUtils.hasText(nodeCode) || nodeCode.equals(x.getRejectnodecode())).filter(x -> !StringUtils.hasText(subProcessBranch) || nodeCode.equals(x.getSubprocessbranch())).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(rejectSkipNodeInfoList)) {
            ArrayList<ProcessNodeDO> latestProcessNodes = new ArrayList<ProcessNodeDO>(workflowContext.getProcessNodeList());
            for (int i = latestProcessNodes.size() - 1; i >= 0; --i) {
                ProcessNodeDO processNodeDO = (ProcessNodeDO)latestProcessNodes.get(i);
                if (!nodeCode.equals(processNodeDO.getNodecode()) || !"\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) continue;
                BigDecimal completeUserType = processNodeDO.getCompleteusertype();
                String completeUserId = processNodeDO.getCompleteuserid();
                if (BigDecimal.ONE.equals(completeUserType)) {
                    completeUserId = VaWorkflowUtils.getOriginalApproverByPlus(processNodeDO, latestProcessNodes);
                }
                resultSet.add(completeUserId);
            }
        }
        return resultSet;
    }

    private boolean isLimitApprover(JsonNode jsonNode) {
        JsonNode chooseApprover = jsonNode.get("properties").get("chooseapprover");
        String globalChooseApprover = (String)VaContext.getVaWorkflowContext().getWorkflowDTO().getExtInfo("globalChooseApprover");
        if (this.isChooseApprover(globalChooseApprover, chooseApprover)) {
            return false;
        }
        JsonNode limitApprover = jsonNode.get("properties").get("limitapprover");
        return limitApprover != null && limitApprover.asBoolean();
    }

    private void deleteRetractLock(WorkflowRetractLockDTO retractLockDTO) {
        try {
            this.retractLockService.delete(retractLockDTO);
        }
        catch (Exception e) {
            log.error("\u5220\u9664\u53d6\u56de\u9501\u5b9a\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va_activiti_deleteretractlockfailed"));
        }
    }
}

