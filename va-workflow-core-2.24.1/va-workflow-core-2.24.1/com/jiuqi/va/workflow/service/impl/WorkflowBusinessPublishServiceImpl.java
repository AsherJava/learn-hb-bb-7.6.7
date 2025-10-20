/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.meta.MetaState
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishItem
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.intf.meta.MetaState;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishItem;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO;
import com.jiuqi.va.workflow.dao.WorkflowBusinessDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDesignDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDraftDao;
import com.jiuqi.va.workflow.service.WorkflowBusinessPublishService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class WorkflowBusinessPublishServiceImpl
implements WorkflowBusinessPublishService {
    @Autowired
    private WorkflowBusinessDao workflowBusinessDao;
    @Autowired
    private WorkflowBusinessRelDraftDao workflowBusinessRelDraftDao;
    @Autowired
    private WorkflowBusinessRelDesignDao workflowBusinessRelDesignDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public WorkflowBusinessDO doPublish(WorkflowBusinessPublishItem publishItem) {
        WorkflowBusinessDO selectOneDO = new WorkflowBusinessDO();
        selectOneDO.setId(publishItem.getId());
        WorkflowBusinessDO designDO = this.workflowBusinessRelDraftDao.selectOneWithDesignData(selectOneDO);
        if (designDO == null || !Objects.equals(designDO.getDesignstate(), publishItem.getDesignState())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
        }
        Integer lockFlag = designDO.getLockflag();
        String lockUser = designDO.getLockuser();
        String currentUser = ShiroUtil.getUser().getId();
        if (lockFlag == 1 && !lockUser.equals(currentUser)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
        }
        if (MetaState.APPENDED.getValue() == designDO.getDesignstate().intValue()) {
            this.workflowBusinessDao.add(designDO);
            this.workflowBusinessRelDraftDao.delete(designDO);
            WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
            workflowBusinessDTO.setStopflag(Integer.valueOf(1));
            workflowBusinessDTO.setWorkflowdefinekey(designDO.getWorkflowdefinekey());
            workflowBusinessDTO.setBusinesscode(designDO.getBusinesscode());
            List<WorkflowBusinessDO> workflowBusinessDOList = this.workflowBusinessDao.select(workflowBusinessDTO);
            if (!CollectionUtils.isEmpty(workflowBusinessDOList)) {
                WorkflowBusinessDO latestWorkflowBusinessDO = workflowBusinessDOList.get(0);
                if (Objects.equals(latestWorkflowBusinessDO.getWorkflowdefineversion(), designDO.getWorkflowdefineversion())) {
                    this.workflowBusinessDao.delete(latestWorkflowBusinessDO);
                    WorkflowBusinessRelDesignDO deleteDO = new WorkflowBusinessRelDesignDO();
                    deleteDO.setId(latestWorkflowBusinessDO.getId());
                    this.workflowBusinessRelDesignDao.delete(deleteDO);
                }
                workflowBusinessDTO.setStopflag(Integer.valueOf(0));
                workflowBusinessDTO.setRelversion(designDO.getRelversion());
                workflowBusinessDTO.setLockflag(designDO.getLockflag());
                workflowBusinessDTO.setLockuser(designDO.getLockuser());
                this.workflowBusinessDao.update((WorkflowBusinessDO)workflowBusinessDTO);
            }
        } else {
            WorkflowBusinessDTO selectDTO = new WorkflowBusinessDTO();
            selectDTO.setBusinesscode(designDO.getBusinesscode());
            selectDTO.setWorkflowdefinekey(designDO.getWorkflowdefinekey());
            selectDTO.setWorkflowdefineversion(designDO.getWorkflowdefineversion());
            WorkflowBusinessDO runtimeDO = this.workflowBusinessDao.selectOne(selectDTO);
            if (runtimeDO == null) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
            }
            Long runtimeVersion = runtimeDO.getRelversion();
            Long designVersion = designDO.getRelversion();
            if (runtimeVersion > designVersion) {
                throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.versionoutdated"));
            }
            if (MetaState.MODIFIED.getValue() == designDO.getDesignstate().intValue()) {
                runtimeDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
                this.workflowBusinessDao.update(runtimeDO);
                WorkflowBusinessRelDesignDO selectDO = new WorkflowBusinessRelDesignDO();
                selectDO.setId(runtimeDO.getId());
                WorkflowBusinessRelDesignDO runtimeDesignDO = (WorkflowBusinessRelDesignDO)this.workflowBusinessRelDesignDao.selectOne(selectDO);
                if (runtimeDesignDO != null) {
                    runtimeDesignDO.setDesignData(designDO.getDesigndata());
                    this.workflowBusinessRelDesignDao.updateByPrimaryKey(runtimeDesignDO);
                } else {
                    WorkflowBusinessRelDesignDO insertDO = new WorkflowBusinessRelDesignDO();
                    insertDO.setId(runtimeDO.getId());
                    insertDO.setDesignData(designDO.getDesigndata());
                    this.workflowBusinessRelDesignDao.insert(insertDO);
                }
                WorkflowBusinessRelDesignDO deleteDO = new WorkflowBusinessRelDesignDO();
                deleteDO.setId(designDO.getId());
                this.workflowBusinessRelDesignDao.delete(deleteDO);
                this.workflowBusinessRelDraftDao.delete(designDO);
            } else if (MetaState.DELETED.getValue() == designDO.getDesignstate().intValue()) {
                WorkflowBusinessDO updateRuntimeDO = new WorkflowBusinessDO();
                updateRuntimeDO.setStopflag(Integer.valueOf(1));
                updateRuntimeDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
                updateRuntimeDO.setWorkflowdefinekey(runtimeDO.getWorkflowdefinekey());
                updateRuntimeDO.setBusinesscode(runtimeDO.getBusinesscode());
                this.workflowBusinessDao.update(updateRuntimeDO);
                this.workflowBusinessRelDraftDao.delete(designDO);
            }
        }
        return designDO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public WorkflowBusinessDO doUpdate(WorkflowBusinessDO currentDO, Long version) {
        int designState;
        WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
        workflowBusinessDTO.setBusinesscode(currentDO.getBusinesscode());
        workflowBusinessDTO.setWorkflowdefinekey(currentDO.getWorkflowdefinekey());
        workflowBusinessDTO.setWorkflowdefineversion(version);
        WorkflowBusinessDO targetDO = this.workflowBusinessDao.selectOne(workflowBusinessDTO);
        if (targetDO == null) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.datahaschanged"));
        }
        Integer lockFlag = targetDO.getLockflag();
        String lockUser = targetDO.getLockuser();
        String currentUser = ShiroUtil.getUser().getId();
        if (lockFlag == 1 && !lockUser.equals(currentUser)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.hasbeenlocked"));
        }
        int n = designState = currentDO.getDesignstate() == null ? 0 : currentDO.getDesignstate();
        if (MetaState.MODIFIED.getValue() == designState || MetaState.DEPLOYED.getValue() == designState) {
            targetDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
            this.workflowBusinessDao.update(targetDO);
            WorkflowBusinessRelDesignDO selectOneDO = new WorkflowBusinessRelDesignDO();
            selectOneDO.setId(targetDO.getId());
            WorkflowBusinessRelDesignDO runtimeDesignDO = (WorkflowBusinessRelDesignDO)this.workflowBusinessRelDesignDao.selectOne(selectOneDO);
            if (runtimeDesignDO != null) {
                runtimeDesignDO.setDesignData(currentDO.getDesigndata());
                this.workflowBusinessRelDesignDao.updateByPrimaryKey(runtimeDesignDO);
            } else {
                WorkflowBusinessRelDesignDO insertDO = new WorkflowBusinessRelDesignDO();
                insertDO.setId(targetDO.getId());
                insertDO.setDesignData(currentDO.getDesigndata());
                this.workflowBusinessRelDesignDao.insert(insertDO);
            }
        }
        return targetDO;
    }
}

