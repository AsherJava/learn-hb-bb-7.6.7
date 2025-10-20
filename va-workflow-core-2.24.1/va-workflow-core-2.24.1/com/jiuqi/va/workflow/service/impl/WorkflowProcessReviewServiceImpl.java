/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDO
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDO;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.workflow.dao.WorkflowProcessReviewDao;
import com.jiuqi.va.workflow.service.WorkflowProcessReviewService;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowProcessReviewServiceImpl
implements WorkflowProcessReviewService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowProcessReviewServiceImpl.class);
    private static final String SUBMIT = "submit";
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowProcessReviewDao workflowProcessReviewDao;
    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public List<WorkflowProcessReviewDTO> getRejectInfos(WorkflowProcessReviewDTO workflowProcessReviewDTO) {
        ProcessNodeDO processNodeDO;
        List processNodeDOS;
        String processId;
        String bizCode = workflowProcessReviewDTO.getBizcode();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setSort("ordernum");
        processNodeDTO.setOrder("desc");
        processNodeDTO.setBizcode(bizCode);
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO process = this.vaWorkflowProcessService.get(processDTO);
        String nodeGroup = null;
        boolean autoNode = false;
        if (process != null) {
            processId = process.getId();
            processNodeDTO.setProcessid(processId);
            processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO).stream().filter(o -> o.getProcessid() != null && o.getCompletetime() != null).collect(Collectors.toList());
            processNodeDO = (ProcessNodeDO)processNodeDOS.get(0);
        } else {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                return null;
            }
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
            processId = processHistoryDO.getId();
            processNodeDTO.setProcessid(processId);
            processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
            processNodeDO = (ProcessNodeDO)processNodeDOS.get(0);
            if ("\u81ea\u52a8\u9a73\u56de".equals(processNodeDO.getCompletecomment()) && processNodeDO.getNodecode() == null) {
                autoNode = true;
                for (ProcessNodeDO nodeDO : processNodeDOS) {
                    if (nodeDO.getNodegroup() == null) continue;
                    nodeGroup = nodeDO.getNodegroup();
                    break;
                }
                if (nodeGroup == null) {
                    return null;
                }
            }
        }
        if (processNodeDO.getRejectstatus() == null && !autoNode) {
            return null;
        }
        ArrayList<WorkflowProcessReviewDTO> result = new ArrayList<WorkflowProcessReviewDTO>();
        try {
            List reviewDOList;
            WorkflowProcessReviewDO workflowProcessReviewDO;
            if (autoNode) {
                workflowProcessReviewDO = new WorkflowProcessReviewDO();
                workflowProcessReviewDO.setBizcode(bizCode);
                reviewDOList = this.workflowProcessReviewDao.select(workflowProcessReviewDO);
                String finalNodeGroup = nodeGroup;
                List extendNodes = processNodeDOS.stream().filter(o -> finalNodeGroup.equals(o.getNodegroup()) && "REVIEW002".equals(o.getReviewmode())).collect(Collectors.toList());
                for (ProcessNodeDO extendNode : extendNodes) {
                    WorkflowProcessReviewDTO reviewDTO = new WorkflowProcessReviewDTO();
                    this.setReviewInfo(reviewDOList, extendNode, reviewDTO, workflowProcessReviewDTO);
                    result.add(reviewDTO);
                }
                return result;
            }
            workflowProcessReviewDO = new WorkflowProcessReviewDO();
            workflowProcessReviewDO.setBizcode(bizCode);
            reviewDOList = this.workflowProcessReviewDao.select(workflowProcessReviewDO);
            WorkflowProcessReviewDTO reviewDTO = new WorkflowProcessReviewDTO();
            this.setReviewInfo(reviewDOList, processNodeDO, reviewDTO, workflowProcessReviewDTO);
            result.add(reviewDTO);
            return result;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private void setReviewInfo(List<WorkflowProcessReviewDO> reviewDOList, ProcessNodeDO processNodeDO, WorkflowProcessReviewDTO reviewDTO, WorkflowProcessReviewDTO workflowProcessReviewDTO) throws Exception {
        UserDO userDO;
        WorkflowProcessReviewDO oldReview = null;
        for (WorkflowProcessReviewDO processReviewDO : reviewDOList) {
            ProcessNodeDO rejectedNode;
            ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setNodeid(processReviewDO.getRejectnodeid());
            processNodeDTO.setBizcode(processReviewDO.getBizcode());
            ProcessNodeDO rejectNode = this.workflowProcessNodeService.get(processNodeDTO);
            if (rejectNode == null) continue;
            if (SUBMIT.equals(processReviewDO.getRejectednodeid())) {
                rejectedNode = new ProcessNodeDO();
            } else {
                processNodeDTO.setNodeid(processReviewDO.getRejectednodeid());
                rejectedNode = this.workflowProcessNodeService.get(processNodeDTO);
            }
            processNodeDTO.setCompleteresult(null);
            processNodeDTO.setCompleteuserid(ShiroUtil.getUser().getId());
            processNodeDTO.setNodeid(workflowProcessReviewDTO.getRejectednodeid());
            ProcessNodeDO curRejectedNode = this.workflowProcessNodeService.get(processNodeDTO);
            if (!rejectNode.getNodecode().equals(processNodeDO.getNodecode()) || (curRejectedNode != null || !SUBMIT.equals(processReviewDO.getRejectednodeid())) && (curRejectedNode == null || !curRejectedNode.getNodecode().equals(rejectedNode.getNodecode())) || !ShiroUtil.getUser().getId().equals(processReviewDO.getUsername()) || !processReviewDO.getRejectprocessid().equals(processNodeDO.getProcessid())) continue;
            ProcessNodeDTO queryNodeLastOpt = new ProcessNodeDTO();
            queryNodeLastOpt.setBizcode(processReviewDO.getBizcode());
            queryNodeLastOpt.setProcessid(processNodeDO.getProcessid());
            queryNodeLastOpt.setCompleteuserid(ShiroUtil.getUser().getId());
            if (curRejectedNode == null) {
                oldReview = processReviewDO;
                break;
            }
            queryNodeLastOpt.setNodecode(curRejectedNode.getNodecode());
            List nodeLastOpt = this.workflowProcessNodeService.queryNodeLastOpt(queryNodeLastOpt);
            if (!CollectionUtils.isEmpty(nodeLastOpt) && !"\u53d6\u56de".equals(((ProcessNodeDO)nodeLastOpt.get(0)).getCompleteresult())) continue;
            oldReview = processReviewDO;
            break;
        }
        if (oldReview != null) {
            BeanUtils.copyProperties(oldReview, reviewDTO);
        } else {
            reviewDTO.setBizcode(processNodeDO.getBizcode());
            if (SUBMIT.equals(workflowProcessReviewDTO.getRejectednodeid())) {
                reviewDTO.setRejectednodeid(SUBMIT);
            } else {
                reviewDTO.setRejectednodeid(workflowProcessReviewDTO.getRejectednodeid());
            }
        }
        reviewDTO.setRejectnodeid(processNodeDO.getNodeid());
        reviewDTO.setRejectprocessid(processNodeDO.getProcessid());
        reviewDTO.setRejectcomment(processNodeDO.getCompletecomment());
        reviewDTO.setRejecttime(processNodeDO.getCompletetime());
        String completeuserid = processNodeDO.getCompleteuserid();
        String username = processNodeDO.getCompleteusername();
        if (StringUtils.hasText(completeuserid) && (userDO = VaWorkFlowDataUtils.getOneUserData(null, completeuserid)) != null) {
            username = userDO.getName();
        }
        reviewDTO.setRejectusername(username);
        reviewDTO.setRequired(workflowProcessReviewDTO.getRequired());
        reviewDTO.setRejectnodename(processNodeDO.getProcessnodename());
    }

    @Override
    public R syncReviews(List<WorkflowProcessReviewDTO> reviewDTOS) {
        try {
            for (WorkflowProcessReviewDTO reviewDTO : reviewDTOS) {
                WorkflowProcessReviewDO workflowProcessReviewDO = new WorkflowProcessReviewDO();
                BeanUtils.copyProperties(reviewDTO, workflowProcessReviewDO);
                workflowProcessReviewDO.setModifytime(new Date());
                workflowProcessReviewDO.setUsername(ShiroUtil.getUser().getId());
                if (reviewDTO.getId() == null) {
                    workflowProcessReviewDO.setId(UUID.randomUUID().toString());
                    this.workflowProcessReviewDao.insert(workflowProcessReviewDO);
                    continue;
                }
                this.workflowProcessReviewDao.update(workflowProcessReviewDO);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @Override
    public List<WorkflowProcessReviewDTO> listReviews(WorkflowProcessReviewDTO workflowProcessReviewDTO) {
        String bizcode = workflowProcessReviewDTO.getBizcode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizcode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO == null) {
            return null;
        }
        ProcessHistoryDO processHistoryDO = null;
        List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
        if (!CollectionUtils.isEmpty(processHistoryDOS)) {
            processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setSort("ordernum");
        processNodeDTO.setOrder("desc");
        processNodeDTO.setBizcode(bizcode);
        List processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        if (CollectionUtils.isEmpty(processNodeDOS)) {
            return null;
        }
        String nodecode = ((ProcessNodeDO)processNodeDOS.get(0)).getNodecode();
        ProcessNodeDO rejectProcessNodeDO = null;
        for (int i = 1; i < processNodeDOS.size(); ++i) {
            ProcessNodeDO processNodeDO = (ProcessNodeDO)processNodeDOS.get(i);
            if (!nodecode.equals(processNodeDO.getNodecode()) || !ShiroUtil.getUser().getId().equals(processNodeDO.getCompleteuserid()) || processNodeDO.getCompletetime() == null) continue;
            if ((processNodeDO.getRejectstatus() == null || !"\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) && !"REVIEW002".equals(processNodeDO.getReviewmode())) break;
            rejectProcessNodeDO = processNodeDO;
            break;
        }
        if (rejectProcessNodeDO == null) {
            return null;
        }
        ArrayList<WorkflowProcessReviewDTO> reviewList = new ArrayList<WorkflowProcessReviewDTO>();
        String rejectNodecode = rejectProcessNodeDO.getNodecode();
        ArrayList<Object> rejectNodes = new ArrayList<Object>();
        for (Object processNodeDO : processNodeDOS) {
            if (!processNodeDO.getNodecode().equals(rejectNodecode) || processNodeDO.getRejectstatus() == null || !"\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) continue;
            rejectNodes.add(processNodeDO);
        }
        WorkflowProcessReviewDO query = new WorkflowProcessReviewDO();
        query.setBizcode(bizcode);
        for (ProcessNodeDO processNodeDO : rejectNodes) {
            query.setRejectnodeid(processNodeDO.getNodeid());
            reviewList.addAll(this.workflowProcessReviewDao.queryReviewInfos(query));
        }
        HashMap users = new HashMap();
        for (WorkflowProcessReviewDTO processReviewDTO : reviewList) {
            if (SUBMIT.equals(processReviewDTO.getRejectednodeid())) {
                processReviewDTO.setNodename("\u63d0\u4ea4");
            } else {
                ProcessNodeDTO nodeDTO = new ProcessNodeDTO();
                nodeDTO.setBizcode(bizcode);
                nodeDTO.setNodeid(processReviewDTO.getRejectednodeid());
                ProcessNodeDO processNodeDO = this.workflowProcessNodeService.get(nodeDTO);
                if (processNodeDO != null) {
                    processReviewDTO.setNodename(processNodeDO.getProcessnodename());
                }
            }
            try {
                UserDO userDO;
                String username = processReviewDTO.getUsername();
                if (users.containsKey(username)) {
                    userDO = (UserDO)users.get(username);
                } else {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(username);
                    userDO = this.authUserClient.get(userDTO);
                }
                if (userDO == null) continue;
                processReviewDTO.setUsername(userDO.getName());
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        if (CollectionUtils.isEmpty(reviewList)) {
            return null;
        }
        return reviewList;
    }

    @Override
    public List<WorkflowProcessReviewDTO> listRejects(WorkflowProcessReviewDTO workflowProcessReviewDTO) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(workflowProcessReviewDTO.getBizcode());
        processNodeDTO.setSort("ordernum");
        processNodeDTO.setOrder("desc");
        List collect = this.workflowProcessNodeService.listProcessNode(processNodeDTO).stream().filter(o -> "\u5ba1\u6279\u9a73\u56de".equals(o.getCompleteresult())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return null;
        }
        ArrayList<WorkflowProcessReviewDTO> list = new ArrayList<WorkflowProcessReviewDTO>();
        HashMap<String, String> userMap = new HashMap<String, String>();
        for (ProcessNodeDO processNodeDO : collect) {
            WorkflowProcessReviewDTO reviewDTO = new WorkflowProcessReviewDTO();
            reviewDTO.setRejecttime(processNodeDO.getCompletetime());
            String completeusername = processNodeDO.getCompleteusername();
            String completeuserid = processNodeDO.getCompleteuserid();
            if (StringUtils.hasText(completeuserid)) {
                if (userMap.containsKey(completeuserid)) {
                    completeusername = (String)userMap.get(completeuserid);
                } else {
                    UserDO userDO = VaWorkFlowDataUtils.getOneUserData(null, completeuserid);
                    if (userDO != null) {
                        completeusername = userDO.getName();
                        userMap.put(completeuserid, completeusername);
                    }
                }
            }
            reviewDTO.setRejectusername(completeusername);
            reviewDTO.setRejectcomment(processNodeDO.getCompletecomment());
            reviewDTO.setRejectnodename(processNodeDO.getProcessnodename());
            list.add(reviewDTO);
        }
        return list;
    }
}

