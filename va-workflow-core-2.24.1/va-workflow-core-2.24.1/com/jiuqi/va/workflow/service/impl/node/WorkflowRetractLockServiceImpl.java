/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDO
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.va.workflow.service.impl.node;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDO;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowRetractLockService;
import com.jiuqi.va.workflow.dao.node.WorkflowRetractLockDao;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkflowRetractLockServiceImpl
implements WorkflowRetractLockService {
    @Autowired
    private WorkflowRetractLockDao workflowRetractLockDao;
    @Autowired
    private WorkflowProcessNodeService nodeService;

    public WorkflowRetractLockDO selectOne(WorkflowRetractLockDTO dto) {
        WorkflowRetractLockDO retractLockDO = this.buildRetractQueryDTO(dto);
        return (WorkflowRetractLockDO)this.workflowRetractLockDao.selectOne(retractLockDO);
    }

    public List<WorkflowRetractLockDO> list(WorkflowRetractLockDTO dto) {
        String bizCode = dto.getBizcode();
        Assert.hasText((String)bizCode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        return this.workflowRetractLockDao.select(dto);
    }

    public void add(WorkflowRetractLockDTO dto) {
        String bizCode = dto.getBizcode();
        Assert.hasText((String)bizCode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        List<String> targetNodes = this.getTargetNodes(dto);
        if (targetNodes.isEmpty()) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.currapprovalnodenotfound"));
        }
        for (String targetNode : targetNodes) {
            String describe;
            dto.setLocknode(targetNode);
            WorkflowRetractLockDO workflowRetractLockDO = this.selectOne(dto);
            if (workflowRetractLockDO == null) {
                String id;
                dto.setLocktime(new Date());
                String userId = dto.getUserid();
                if (!StringUtils.hasText(userId)) {
                    dto.setUserid(ShiroUtil.getUser().getId());
                }
                if (!StringUtils.hasText(id = dto.getId())) {
                    dto.setId(UUID.randomUUID().toString());
                }
                dto.setLockcount(Integer.valueOf(1));
                this.workflowRetractLockDao.insert(dto);
                continue;
            }
            Integer count = workflowRetractLockDO.getLockcount();
            if (count == null || count == 0) {
                workflowRetractLockDO.setLockcount(Integer.valueOf(1));
            }
            if (StringUtils.hasText(describe = dto.getLockdescribe())) {
                workflowRetractLockDO.setLockdescribe(describe);
            }
            workflowRetractLockDO.setLocktime(new Date());
            this.workflowRetractLockDao.updateByPrimaryKey(workflowRetractLockDO);
        }
    }

    private List<String> getTargetNodes(WorkflowRetractLockDTO dto) {
        ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
        nodeDTO.setBizcode(dto.getBizcode());
        List processNodes = this.nodeService.listProcessNode(nodeDTO);
        List unCompleteNodes = processNodes.stream().filter(node -> node.getCompletetime() == null).filter(node -> !BigDecimal.ONE.equals(node.getHiddenflag())).filter(node -> !Objects.equals(node.getNodeid(), node.getPgwnodeid())).filter(node -> !Objects.equals(node.getNodeid(), node.getSubprocessnodeid())).collect(Collectors.toList());
        if (unCompleteNodes.isEmpty()) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.currapprovalnodenotfound"));
        }
        String subProcessBranch = dto.getSubprocessbranch();
        ArrayList<String> targetNodes = new ArrayList<String>();
        ProcessNodeDO anyUnCompleteNode = (ProcessNodeDO)unCompleteNodes.get(0);
        String unCompleteSubNodeId = anyUnCompleteNode.getSubprocessnodeid();
        String unCompletePgwNodeId = anyUnCompleteNode.getPgwnodeid();
        if (StringUtils.hasText(unCompleteSubNodeId)) {
            if (!StringUtils.hasText(subProcessBranch)) {
                Assert.hasText((String)subProcessBranch, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "subProcessBranch"));
            }
            ProcessNodeDO targetNode = processNodes.stream().filter(node -> Objects.equals(node.getSubprocessbranch(), subProcessBranch)).filter(node -> !Objects.equals(node.getNodeid(), node.getSubprocessnodeid())).filter(node -> node.getCompletetime() == null).findFirst().orElseThrow(() -> new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.currapprovalnodenotfound")));
            targetNodes.add(targetNode.getNodecode());
        } else if (StringUtils.hasText(unCompletePgwNodeId)) {
            this.getPgwBranchTargetNodes(processNodes, unCompletePgwNodeId, targetNodes);
        } else {
            targetNodes.add(((ProcessNodeDO)unCompleteNodes.get(0)).getNodecode());
        }
        return targetNodes;
    }

    private void getPgwBranchTargetNodes(List<ProcessNodeDO> processNodes, String unCompletePgwNodeId, List<String> targetNodes) {
        Map<String, List<ProcessNodeDO>> pgwBranchMap = processNodes.stream().filter(node -> Objects.equals(node.getPgwnodeid(), unCompletePgwNodeId)).filter(node -> !Objects.equals(node.getNodeid(), node.getPgwnodeid())).collect(Collectors.groupingBy(ProcessNodeDO::getPgwbranch));
        block0: for (Map.Entry<String, List<ProcessNodeDO>> entry : pgwBranchMap.entrySet()) {
            ProcessNodeDO nodeDO;
            List<ProcessNodeDO> nodeDOList = entry.getValue();
            ArrayList<ProcessNodeDO> branchCompleteNodes = new ArrayList<ProcessNodeDO>();
            ArrayList<ProcessNodeDO> branchUnCompleteNodes = new ArrayList<ProcessNodeDO>();
            for (ProcessNodeDO nodeDO2 : nodeDOList) {
                if (nodeDO2.getCompletetime() != null) {
                    branchCompleteNodes.add(nodeDO2);
                    continue;
                }
                branchUnCompleteNodes.add(nodeDO2);
            }
            if (branchCompleteNodes.isEmpty() || branchUnCompleteNodes.isEmpty()) continue;
            String lastNodeCode = ((ProcessNodeDO)branchCompleteNodes.get(branchCompleteNodes.size() - 1)).getNodecode();
            for (int i = branchCompleteNodes.size() - 1; i >= 0 && Objects.equals(lastNodeCode, (nodeDO = (ProcessNodeDO)branchCompleteNodes.get(i)).getNodecode()); --i) {
                if (!"\u5ba1\u6279\u9a73\u56de".equals(nodeDO.getCompleteresult())) continue;
                targetNodes.add(((ProcessNodeDO)branchUnCompleteNodes.get(0)).getNodecode());
                continue block0;
            }
        }
    }

    public void delete(WorkflowRetractLockDTO dto) {
        String bizCode = dto.getBizcode();
        Assert.hasText((String)bizCode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        WorkflowRetractLockDO retractLockDO = new WorkflowRetractLockDO();
        retractLockDO.setBizcode(bizCode);
        retractLockDO.setLocknode(dto.getLocknode());
        String subProcessBranch = dto.getSubprocessbranch();
        if (StringUtils.hasText(subProcessBranch)) {
            retractLockDO.setSubprocessbranch(subProcessBranch);
        }
        this.workflowRetractLockDao.delete(retractLockDO);
    }

    public void reduceCount(WorkflowRetractLockDTO dto) {
        String bizCode = dto.getBizcode();
        Assert.hasText((String)bizCode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        String lockNode = dto.getLocknode();
        Assert.hasText((String)lockNode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "lockNode"));
        WorkflowRetractLockDTO lockDTO = new WorkflowRetractLockDTO();
        lockDTO.setBizcode(bizCode);
        lockDTO.setLocknode(lockNode);
        String subprocessbranch = dto.getSubprocessbranch();
        if (StringUtils.hasText(subprocessbranch)) {
            lockDTO.setSubprocessbranch(subprocessbranch);
        }
        lockDTO.setLockcount(Integer.valueOf(0));
        this.workflowRetractLockDao.updateCount(lockDTO);
    }

    public int count(WorkflowRetractLockDTO dto) {
        WorkflowRetractLockDO retractLockDO = this.buildRetractQueryDTO(dto);
        return this.workflowRetractLockDao.selectCount(retractLockDO);
    }

    private WorkflowRetractLockDO buildRetractQueryDTO(WorkflowRetractLockDTO dto) {
        String bizCode = dto.getBizcode();
        Assert.hasText((String)bizCode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "bizCode"));
        String lockNode = dto.getLocknode();
        Assert.hasText((String)lockNode, (String)(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + "lockNode"));
        WorkflowRetractLockDO retractLockDO = new WorkflowRetractLockDO();
        retractLockDO.setBizcode(bizCode);
        retractLockDO.setLocknode(lockNode);
        String subProcessBranch = dto.getSubprocessbranch();
        if (StringUtils.hasText(subProcessBranch)) {
            retractLockDO.setSubprocessbranch(subProcessBranch);
        }
        return retractLockDO;
    }
}

