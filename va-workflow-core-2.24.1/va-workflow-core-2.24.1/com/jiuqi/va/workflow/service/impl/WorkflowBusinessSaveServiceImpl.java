/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.meta.MetaState
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.intf.meta.MetaState;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDesignDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDraftDao;
import com.jiuqi.va.workflow.service.WorkflowBusinessSaveService;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowBusinessSaveServiceImpl
implements WorkflowBusinessSaveService {
    @Autowired
    private WorkflowBusinessRelDraftDao workflowBusinessRelDraftDao;
    @Autowired
    private WorkflowBusinessRelDesignDao workflowBusinessRelDesignDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(WorkflowBusinessDO workflowBusinessDO) {
        String businessCode = workflowBusinessDO.getBusinesscode();
        String workflowDefineKey = workflowBusinessDO.getWorkflowdefinekey();
        Long workflowDefineVersion = workflowBusinessDO.getWorkflowdefineversion();
        String designData = workflowBusinessDO.getDesigndata();
        WorkflowBusinessDO selectDO = new WorkflowBusinessDO();
        selectDO.setBusinesscode(businessCode);
        selectDO.setWorkflowdefinekey(workflowDefineKey);
        selectDO.setWorkflowdefineversion(workflowDefineVersion);
        WorkflowBusinessDO updateDraftDO = this.workflowBusinessRelDraftDao.selectOne(selectDO);
        if (updateDraftDO == null) {
            WorkflowBusinessDO insertDraftDO = new WorkflowBusinessDO();
            UUID newId = UUID.randomUUID();
            insertDraftDO.setId(newId);
            insertDraftDO.setBusinesscode(businessCode);
            insertDraftDO.setWorkflowdefinekey(workflowDefineKey);
            insertDraftDO.setWorkflowdefineversion(workflowDefineVersion);
            insertDraftDO.setModifytime(new Date());
            insertDraftDO.setRelversion(workflowBusinessDO.getRelversion());
            insertDraftDO.setDesignstate(Integer.valueOf(MetaState.MODIFIED.getValue()));
            insertDraftDO.setBusinesstype(workflowBusinessDO.getBusinesstype());
            insertDraftDO.setLockflag(Integer.valueOf(0));
            insertDraftDO.setModulename(workflowBusinessDO.getModulename());
            this.workflowBusinessRelDraftDao.insert(insertDraftDO);
            WorkflowBusinessRelDesignDO insertDesignDO = new WorkflowBusinessRelDesignDO();
            insertDesignDO.setId(newId);
            insertDesignDO.setDesignData(designData);
            this.workflowBusinessRelDesignDao.insert(insertDesignDO);
        } else {
            updateDraftDO.setModifytime(new Date());
            this.workflowBusinessRelDraftDao.update(updateDraftDO);
            WorkflowBusinessRelDesignDO updateDesignDO = new WorkflowBusinessRelDesignDO();
            updateDesignDO.setId(updateDraftDO.getId());
            updateDesignDO.setDesignData(designData);
            this.workflowBusinessRelDesignDao.updateByPrimaryKey(updateDesignDO);
        }
    }
}

