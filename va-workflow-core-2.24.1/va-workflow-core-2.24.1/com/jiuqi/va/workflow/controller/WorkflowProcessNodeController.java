/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.NodeView
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowQueryDTO
 *  com.jiuqi.va.domain.workflow.approval.count.ApprovalCountDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.NodeView;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowQueryDTO;
import com.jiuqi.va.domain.workflow.approval.count.ApprovalCountDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeExtendService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.controller.WorkflowDefineController;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/process"})
public class WorkflowProcessNodeController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowProcessNodeController.class);
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;
    @Autowired
    private WorkflowDefineController workflowController;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private WorkflowProcessNodeExtendService workflowProcessNodeExtendService;
    @Autowired
    private WorkflowNextNodeService workflowNextNodeService;

    @PostMapping(value={"/view"})
    public List<NodeView> processView(@RequestBody ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid())) {
            return Collections.emptyList();
        }
        return this.workflowProcessNodeService.processView(processNodeDTO);
    }

    @PostMapping(value={"/nodes/get"})
    public Set<Map<String, String>> getProcessNodes(@RequestBody TenantDO tenantDO) {
        if (tenantDO.getExtInfo("bizCode") == null || tenantDO.getExtInfo("nodeId") == null) {
            return new HashSet<Map<String, String>>();
        }
        String bizCode = String.valueOf(tenantDO.getExtInfo("bizCode"));
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(bizCode);
        List allProcessNodeDOs = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        LinkedHashSet<Map<String, String>> processNodes = new LinkedHashSet<Map<String, String>>();
        for (int i = allProcessNodeDOs.size() - 1; i >= 0; --i) {
            ProcessNodeDO node = (ProcessNodeDO)allProcessNodeDOs.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("nodeId", node.getNodecode());
            map.put("nodeName", node.getProcessnodename());
            processNodes.add(map);
        }
        return processNodes;
    }

    @PostMapping(value={"/node/query"})
    public R listProcessNode(@RequestBody ProcessDTO processDTO) {
        try {
            if (!StringUtils.hasText(processDTO.getId())) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
            }
            List<Map<String, Object>> resList = this.workflowProcessNodeService.selectProcessNode(processDTO);
            HashMap userMap = new HashMap();
            resList.forEach(map -> {
                String completeUser = String.valueOf(map.get("COMPLETEUSERID"));
                if (!userMap.containsKey(completeUser)) {
                    this.getUser(userMap, completeUser);
                }
                map.put("COMPLETEUSERTITLE", userMap.get(completeUser));
            });
            resList = this.mergeProcessNode(resList);
            R r = R.ok();
            r.put("processNodeList", resList);
            return r;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getprocessfailed"));
        }
    }

    private List<Map<String, Object>> mergeProcessNode(List<Map<String, Object>> resList) {
        ArrayList<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        HashMap<String, Map<String, Object>> processNodeMap = new HashMap<String, Map<String, Object>>();
        for (Map<String, Object> item : resList) {
            String processNodeCode = (String)item.get("NODECODE");
            if (!processNodeMap.containsKey(processNodeCode)) {
                processNodeMap.put(processNodeCode, item);
                newList.add(item);
                continue;
            }
            Map processNodeItem = (Map)processNodeMap.get(processNodeCode);
            Object subprocessbranch = item.get("SUBPROCESSBRANCH");
            if (subprocessbranch != null && !Objects.equals(subprocessbranch, processNodeItem.get("SUBPROCESSBRANCH"))) {
                processNodeMap.put(processNodeCode, item);
                newList.add(item);
                continue;
            }
            String completeUserTitle = (String)processNodeItem.get("COMPLETEUSERTITLE");
            completeUserTitle = completeUserTitle + "\uff0c" + item.get("COMPLETEUSERTITLE");
            processNodeItem.put("COMPLETEUSERTITLE", completeUserTitle);
        }
        return newList;
    }

    private void getUser(Map<String, String> userMap, String startUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(startUser);
        PageVO pageVO = this.authUserClient.list(userDTO);
        if (pageVO != null && pageVO.getRows() != null && pageVO.getRows().size() > 0) {
            UserDO userDO = (UserDO)pageVO.getRows().get(0);
            userMap.put(userDO.getId(), userDO.getName());
        }
    }

    @PostMapping(value={"batch/nodes/get"})
    public List<Map<String, Object>> bacthGetProcessNodes(@RequestBody List<Map<String, Object>> params) {
        ArrayList<Map<String, Object>> nodeMapList = new ArrayList<Map<String, Object>>();
        params.forEach(o -> {
            HashMap nodeMap = new HashMap();
            try {
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("processId", o.get("PROCESSID"));
                tenantDO.addExtInfo("nodeId", o.get("TASKDEFINEKEY"));
                tenantDO.addExtInfo("bizCode", o.get("BIZCODE"));
                Set<Map<String, String>> processNodes = this.getProcessNodes(tenantDO);
                tenantDO.addExtInfo("uniqueCode", o.get("PROCESSDEFINEKEY"));
                tenantDO.addExtInfo("version", o.get("PROCESSDEFINEVERSION"));
                R r = this.workflowController.getNodeConfig(tenantDO);
                String rejectType = (String)r.get((Object)"rejectType");
                List rejectNodeMapList = VaWorkflowUtils.getList(r.get((Object)"nodes"));
                if ("3".equals(rejectType)) {
                    List nodesList;
                    if (rejectNodeMapList.isEmpty()) {
                        nodesList = processNodes.stream().filter(item -> !((String)item.get("nodeId")).equals(o.get("TASKDEFINEKEY"))).collect(Collectors.toList());
                    } else {
                        HashSet dataNodeIdSet = new HashSet();
                        processNodes.forEach(e -> dataNodeIdSet.add(e.get("nodeId")));
                        nodesList = rejectNodeMapList.stream().filter(item -> dataNodeIdSet.contains(item.get("nodeId")) && !((String)item.get("nodeId")).equals(o.get("TASKDEFINEKEY"))).collect(Collectors.toList());
                    }
                    nodeMap.put("taskId", o.get("TASKID"));
                    if (nodesList.size() == 1) {
                        nodeMap.put("rejectNode", ((Map)nodesList.get(0)).get("nodeId"));
                        nodeMapList.add(nodeMap);
                    } else if (nodesList.isEmpty()) {
                        nodeMap.put("rejectNode", "3");
                        nodeMapList.add(nodeMap);
                    } else {
                        nodeMap.put("rejectNode", "2");
                    }
                } else {
                    nodeMap.put("taskId", o.get("TASKID"));
                    nodeMap.put("rejectNode", "1");
                }
                nodeMapList.add(nodeMap);
            }
            catch (Exception e2) {
                log.error(e2.getMessage(), e2);
                nodeMap.put("taskId", o.get("TASKID"));
                nodeMap.put("rejectNode", "");
                nodeMapList.add(nodeMap);
            }
        });
        return nodeMapList;
    }

    @PostMapping(value={"/node/get"})
    public ProcessNodeDO getProcessNode(@RequestBody ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessNodeService.get(processNodeDTO);
    }

    @PostMapping(value={"/node/list"})
    public List<ProcessNodeDO> listProcessNode(@RequestBody ProcessNodeDTO processNodeDTO) {
        if (!StringUtils.hasText(processNodeDTO.getBizcode()) && !StringUtils.hasText(processNodeDTO.getProcessid()) && !StringUtils.hasText(processNodeDTO.getNodeid()) && CollectionUtils.isEmpty(processNodeDTO.getProcessIdList())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessNodeService.listProcessNode(processNodeDTO);
    }

    @PostMapping(value={"/node/add"})
    public R addProcessNode(@RequestBody ProcessNodeDO processNode) {
        if (!StringUtils.hasText(processNode.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        this.workflowProcessNodeService.add(processNode);
        return R.ok();
    }

    @PostMapping(value={"/node/batch/add"})
    public R addBatchProcessNode(@RequestBody TenantDO tenantDO) {
        Object obj = tenantDO.getExtInfo("processNodes");
        if (ObjectUtils.isEmpty(obj)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)obj), ProcessNodeDO.class);
        this.workflowProcessNodeService.addBatch(processNodes, tenantDO.getTenantName());
        return R.ok();
    }

    @PostMapping(value={"/node/update"})
    public R updateProcessNode(@RequestBody ProcessNodeDO processNode) {
        if (!StringUtils.hasText(processNode.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        this.workflowProcessNodeService.update(processNode);
        return R.ok();
    }

    @PostMapping(value={"/node/delete/finishNode"})
    public R deleteFinishProcessNode(@RequestBody ProcessNodeDO processNode) {
        return this.workflowProcessNodeService.deleteFinishProcessNode(processNode);
    }

    @PostMapping(value={"/node/batch/complete"})
    public R completeBatchProcessNode(@RequestBody TenantDO tenantDO) {
        try {
            this.workflowProcessNodeService.completeBatch(tenantDO);
        }
        catch (Exception e) {
            log.error("completeBatchProcessNode\u53d1\u751f\u5f02\u5e38", e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/node/currNodeProperties"})
    public R currNodeProperties(@RequestBody WorkflowDTO workflowDTO) {
        return this.workflowProcessNodeService.currNodeProperties(workflowDTO);
    }

    @PostMapping(value={"/updatenode/completeuserid"})
    R updateProcessNodeCompleteUserId(@RequestBody ProcessNodeDO processNode) {
        return this.workflowProcessNodeService.updateCompleteUserAndReceiveTime(processNode);
    }

    @PostMapping(value={"/node/batch/delete"})
    public R deleteBatchProcessNode(@RequestBody TenantDO tenantDO) {
        Object obj = tenantDO.getExtInfo("processNodes");
        if (ObjectUtils.isEmpty(obj)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        List processNodes = JSONUtil.parseArray((String)JSONUtil.toJSONString((Object)obj), ProcessNodeDO.class);
        this.workflowProcessNodeService.deleteBatch(processNodes, tenantDO.getTenantName());
        return R.ok();
    }

    @PostMapping(value={"/node/reject/list"})
    public List<ProcessRejectNodeDO> listProcessRejectNode(@RequestBody ProcessRejectNodeDO processRejectNodeDO) {
        if (!StringUtils.hasText(processRejectNodeDO.getBizcode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessRejectNodeService.listRejectNodeInfo(processRejectNodeDO);
    }

    @PostMapping(value={"/next/node/config"})
    public R getNextNodeConfig(@RequestBody WorkflowDTO workflowDTO) {
        return this.workflowNextNodeService.getNextNodeConfig(workflowDTO);
    }

    @PostMapping(value={"/reject/designate/node/list"})
    public R listRejectDesignateNodeInfo(@RequestBody WorkflowDTO workflowDTO) {
        if (!StringUtils.hasText(workflowDTO.getBizCode()) && !StringUtils.hasText(workflowDTO.getNodeCode())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        return this.workflowProcessNodeService.listRejectDesignateNodeInfo(workflowDTO);
    }

    @PostMapping(value={"/getWorkflowAttr"})
    public R getWorkflowAttrOfTodoColumn(@RequestBody WorkflowQueryDTO workflowQueryDTO) {
        R ok = new R();
        List processNodeList = workflowQueryDTO.getProcessNodeList();
        if (CollectionUtils.isEmpty(processNodeList)) {
            return ok;
        }
        try {
            boolean currNodeFlag;
            Object prevNodeCommentFlagObj = workflowQueryDTO.getExtInfo("prevNodeCommentFlag");
            Object currNodeFlagObj = workflowQueryDTO.getExtInfo("currNodeFlag");
            boolean prevNodeCommentFlag = prevNodeCommentFlagObj != null && (Boolean)prevNodeCommentFlagObj != false;
            boolean bl = currNodeFlag = currNodeFlagObj != null && (Boolean)currNodeFlagObj != false;
            if (!prevNodeCommentFlag && !currNodeFlag) {
                return ok;
            }
            Map workflowAttributes = this.workflowProcessNodeService.getPreNodeCommAndCurrNode(processNodeList, prevNodeCommentFlag, currNodeFlag);
            ok.put("workflowAttributes", (Object)workflowAttributes);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return ok;
    }

    @PostMapping(value={"/query/nodecode-title/info"})
    public R queryNodeCodeTitleInfo(@RequestBody TenantDO tenantDO) {
        try {
            Object processDefinesObj = tenantDO.getExtInfo("processDefines");
            R ok = R.ok();
            if (processDefinesObj instanceof List) {
                List processDefines = (List)processDefinesObj;
                Map nodeCodeMap = this.workflowProcessNodeExtendService.getNodeCodeMapList(processDefines);
                ok.put("nodeCodeMap", (Object)nodeCodeMap);
            }
            return ok;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/node/approval-count"})
    public R getApprovalCount(@RequestBody ApprovalCountDTO approvalCountDTO) {
        try {
            R ok = R.ok();
            ok.put("data", (Object)this.workflowProcessNodeService.getApprovalCount(approvalCountDTO));
            return ok;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }
}

