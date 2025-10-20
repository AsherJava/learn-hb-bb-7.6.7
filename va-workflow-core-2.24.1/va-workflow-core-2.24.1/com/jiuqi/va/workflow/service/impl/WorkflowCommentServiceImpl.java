/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.workflow.dao.WorkflowCommentDao;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.service.WorkflowCommentService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowCommentServiceImpl
implements WorkflowCommentService {
    @Autowired
    private WorkflowCommentDao workflowCommentDao;
    @Autowired
    private WorkflowCommentService workflowCommentService;
    private static final int MAXWORKFLOWCOMMNUM = 5;

    @Override
    public void add(WorkflowCommentDTO workflowCommentDTO) {
        WorkflowCommentDTO dto = new WorkflowCommentDTO();
        dto.setUsername(workflowCommentDTO.getUsername());
        List<String> commentsList = this.workflowCommentDao.getComments(dto);
        int count = 0;
        if (!CollectionUtils.isEmpty(commentsList)) {
            count = (int)commentsList.stream().filter(comm -> Objects.equals(workflowCommentDTO.getComment(), comm)).count();
        }
        if (CollectionUtils.isEmpty(commentsList) || count == 0) {
            BigDecimal topflag;
            WorkflowCommentDO workflowCommentDO = new WorkflowCommentDO();
            workflowCommentDO.setId(UUID.randomUUID().toString());
            workflowCommentDO.setUsername(workflowCommentDTO.getUsername());
            workflowCommentDO.setCommoncomment(workflowCommentDTO.getComment());
            BigDecimal dtoTopflag = workflowCommentDTO.getTopflag();
            BigDecimal bigDecimal = topflag = dtoTopflag == null ? WorkflowOption.TopFlag.NOTTOP.getTopFlag() : dtoTopflag;
            if (Objects.equals(topflag, WorkflowOption.TopFlag.TOP.getTopFlag())) {
                WorkflowCommentDO commentDO = new WorkflowCommentDO();
                commentDO.setUsername(workflowCommentDTO.getUsername());
                commentDO.setTopflag(WorkflowOption.TopFlag.TOP.getTopFlag());
                int workflowCommCount = this.workflowCommentDao.selectCount(commentDO);
                if (workflowCommCount > 5) {
                    throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.maxtopcommentnum") + 5 + VaWorkFlowI18nUtils.getInfo("va.workflow.strip"));
                }
            }
            workflowCommentDO.setTopflag(topflag);
            BigDecimal dtoOrdernum = workflowCommentDTO.getOrdernum();
            BigDecimal ordernum = dtoOrdernum == null ? new BigDecimal(System.currentTimeMillis()) : dtoOrdernum;
            workflowCommentDO.setOrdernum(ordernum);
            workflowCommentDO.setCreatetime(new Date());
            this.workflowCommentDao.insertSelective(workflowCommentDO);
        }
    }

    @Override
    public List<String> getComments(WorkflowCommentDTO workflowCommentDTO) {
        return this.workflowCommentDao.getComments(workflowCommentDTO);
    }

    @Override
    public List<WorkflowCommentDO> getCommentByUsername(WorkflowCommentDTO workflowCommentDTO) {
        return this.workflowCommentDao.getCommentsByUsername(workflowCommentDTO);
    }

    @Override
    public void move(WorkflowCommentDTO commentDTO) {
        List moveData = commentDTO.getMoveData();
        if (CollectionUtils.isEmpty(moveData)) {
            return;
        }
        String userId = ShiroUtil.getUser().getId();
        Set ids = moveData.stream().filter(comm -> Objects.equals(WorkflowOption.TopFlag.TOP.getTopFlag(), comm.getTopflag())).map(WorkflowCommentDO::getId).collect(Collectors.toSet());
        if (!ids.isEmpty()) {
            Set notTopIds = moveData.stream().map(WorkflowCommentDO::getId).filter(id -> !ids.contains(id)).collect(Collectors.toSet());
            WorkflowCommentDO workflowCommentDO = new WorkflowCommentDO();
            workflowCommentDO.setUsername(userId);
            workflowCommentDO.setTopflag(WorkflowOption.TopFlag.TOP.getTopFlag());
            List commentDOList = this.workflowCommentDao.select(workflowCommentDO);
            commentDOList.forEach(comm -> {
                if (!notTopIds.contains(comm.getId())) {
                    ids.add(comm.getId());
                }
            });
            if (ids.size() > 5) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.maxtopcommentnum") + 5 + VaWorkFlowI18nUtils.getInfo("va.workflow.strip"));
            }
        }
        int size = moveData.size();
        List<BigDecimal> orderNums = moveData.stream().map(WorkflowCommentDO::getOrdernum).filter(Objects::nonNull).distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        moveData.sort(Comparator.comparing(WorkflowCommentDO::getOrdernum, Comparator.nullsLast(Comparator.reverseOrder())));
        int count = size - orderNums.size();
        if (count > 0) {
            long minOrdernum = orderNums.isEmpty() ? System.currentTimeMillis() : orderNums.get(orderNums.size() - 1).longValue();
            int i = 1;
            while (count > 0) {
                orderNums.add(size - count, new BigDecimal(minOrdernum - (long)i));
                --count;
                ++i;
            }
        }
        this.workflowCommentService.sortWorkflowComments(moveData, orderNums);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void sortWorkflowComments(List<WorkflowCommentDO> moveData, List<BigDecimal> orderNums) {
        int i = 0;
        Iterator<WorkflowCommentDO> iterator = moveData.iterator();
        while (iterator.hasNext()) {
            WorkflowCommentDO commentDO;
            BigDecimal topflag = (commentDO = iterator.next()).getTopflag();
            commentDO.setTopflag(topflag == null ? BigDecimal.ZERO : topflag);
            commentDO.setOrdernum(orderNums.get(i));
            this.workflowCommentDao.updateByPrimaryKeySelective(commentDO);
            ++i;
        }
    }

    @Override
    public void deleteComment(WorkflowCommentDTO workflowCommentDTO) {
        if (StringUtils.hasText(workflowCommentDTO.getId())) {
            this.workflowCommentDao.deleteCommentById(workflowCommentDTO);
        }
    }
}

