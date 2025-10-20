/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO;
import com.jiuqi.va.workflow.dao.WorkflowCommonUserDao;
import com.jiuqi.va.workflow.service.WorkflowCommonUserService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowCommonUserServiceImpl
implements WorkflowCommonUserService {
    @Autowired
    private WorkflowCommonUserDao workflowCommonUserDao;

    @Override
    public List<WorkflowCommonUserDO> list(WorkflowCommonUserDTO workflowCommonUserDTO) {
        this.validateParam(workflowCommonUserDTO);
        return this.workflowCommonUserDao.list(workflowCommonUserDTO);
    }

    @Override
    public void add(WorkflowCommonUserDTO workflowCommonUserDTO) {
        this.validateParam(workflowCommonUserDTO);
        List addCommonUserIdList = workflowCommonUserDTO.getAddCommonUserIdList();
        if (!CollectionUtils.isEmpty(addCommonUserIdList)) {
            List<WorkflowCommonUserDO> commonUserDOList = this.workflowCommonUserDao.list(workflowCommonUserDTO);
            List idList = commonUserDOList.stream().map(WorkflowCommonUserDO::getCommonUserId).collect(Collectors.toList());
            for (String id : addCommonUserIdList) {
                if (!StringUtils.hasText(id) || idList.contains(id)) continue;
                workflowCommonUserDTO.setId(UUID.randomUUID().toString());
                workflowCommonUserDTO.setCommonUserId(id);
                workflowCommonUserDTO.setCreateTime(new Date());
                this.workflowCommonUserDao.insert(workflowCommonUserDTO);
            }
        }
    }

    @Override
    public void delete(WorkflowCommonUserDTO workflowCommonUserDTO) {
        this.validateParam(workflowCommonUserDTO);
        List deleteCommonUserIdList = workflowCommonUserDTO.getDeleteCommonUserIdList();
        if (!CollectionUtils.isEmpty(deleteCommonUserIdList)) {
            List<WorkflowCommonUserDO> commonUserDOList = this.workflowCommonUserDao.list(workflowCommonUserDTO);
            Map<String, WorkflowCommonUserDO> commonUserDOMap = commonUserDOList.stream().collect(Collectors.toMap(WorkflowCommonUserDO::getCommonUserId, o -> o, (o1, o2) -> o1));
            for (String id : deleteCommonUserIdList) {
                if (!commonUserDOMap.containsKey(id)) continue;
                this.workflowCommonUserDao.delete(commonUserDOMap.get(id));
            }
        }
    }

    private void validateParam(WorkflowCommonUserDTO workflowCommonUserDTO) {
        String userId = workflowCommonUserDTO.getUserId();
        String workflowDefineKey = workflowCommonUserDTO.getWorkflowDefineKey();
        String nodeCode = workflowCommonUserDTO.getNodeCode();
        if (!StringUtils.hasText(userId)) {
            workflowCommonUserDTO.setUserId(ShiroUtil.getUser().getId());
        }
        if (!StringUtils.hasText(workflowDefineKey)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ": workflowDefineKey");
        }
        if (!StringUtils.hasText(nodeCode)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams") + ": nodeCode");
        }
    }
}

