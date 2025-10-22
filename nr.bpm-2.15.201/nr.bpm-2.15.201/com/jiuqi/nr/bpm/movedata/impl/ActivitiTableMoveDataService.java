/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.movedata.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.movedata.IActivitiTableMoveDataService;
import com.jiuqi.nr.bpm.movedata.NrActvityGeneralByteArray;
import com.jiuqi.nr.bpm.movedata.NrDeadLetterJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrEventSubscrEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrIdentityLinkEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrSuspendedJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrTaskEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrTimerJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.ge.IActvityGeneralByteArrayDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.DeadLetterJobDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.EventSubscrDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.ExecutionDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.IdentityLinkDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.JobDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.SuspendedJobDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.TaskDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.TimerJobDao;
import com.jiuqi.nr.bpm.movedata.runtime.dao.impl.VariableDaoImpl;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiTableMoveDataService
implements IActivitiTableMoveDataService {
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    DeadLetterJobDao deadLetterJobDao;
    @Autowired
    EventSubscrDao eventSubscrDao;
    @Autowired
    ExecutionDao executionDao;
    @Autowired
    IdentityLinkDao identityLinkDao;
    @Autowired
    JobDao jobDao;
    @Autowired
    SuspendedJobDao suspendedJobDao;
    @Autowired
    TaskDao taskDao;
    @Autowired
    TimerJobDao timerJobDao;
    @Autowired
    VariableDaoImpl variableDao;
    @Autowired
    IActvityGeneralByteArrayDao actvityGeneralByteArrayDao;

    @Override
    public UploadStateNew exportUploadStateNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        return this.batchQueryUploadStateService.queryUploadStateNew(dimensionValueSet, formKey, formScheme);
    }

    @Override
    public List<UploadRecordNew> exportUploadActionsNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        return this.batchQueryUploadStateService.queryUploadActionsNew(dimensionValueSet, formKey, formScheme);
    }

    @Override
    public void importUploadStateNew(BusinessKey businessKey, UploadStateNew uploadStateNew) {
        if (uploadStateNew != null) {
            this.batchQueryUploadStateService.deleteUploadState(businessKey);
            this.batchQueryUploadStateService.insertUploadState(businessKey, uploadStateNew);
        }
    }

    @Override
    public void importUploadActionsNew(BusinessKey businessKey, List<UploadRecordNew> uploadRecords) {
        if (uploadRecords != null) {
            this.batchQueryUploadStateService.deleteUploadRecord(businessKey);
            for (UploadRecordNew uploadRecordNew : uploadRecords) {
                this.batchQueryUploadStateService.insertUploadRecord(businessKey, uploadRecordNew);
            }
        }
    }

    @Override
    public void importExecutionEntity(List<NrExecutionEntityImpl> nrExecutionEntityImpl) {
        this.executionDao.batchInsert(nrExecutionEntityImpl);
    }

    @Override
    public List<NrExecutionEntityImpl> exportExecutionEntity(String processInstanceId) {
        return this.executionDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importTaskEntityImpl(List<NrTaskEntityImpl> nrTaskEntityImpl) {
        this.taskDao.batchInsert(nrTaskEntityImpl);
    }

    @Override
    public List<NrTaskEntityImpl> exportTaskEntityImpl(String processInstanceId) {
        return this.taskDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importIdentityLinkEntityImpl(List<NrIdentityLinkEntityImpl> nrIdentityLinkEntityImpl) {
        this.identityLinkDao.batchInsert(nrIdentityLinkEntityImpl);
    }

    @Override
    public List<NrIdentityLinkEntityImpl> exportIdentityLinkEntityImpl(String processInstanceId) {
        return this.identityLinkDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importVariableInstanceEntityImpl(List<NrVariableInstanceEntityImpl> nrVariableInstanceEntityImpl) {
        this.variableDao.change(nrVariableInstanceEntityImpl);
    }

    @Override
    public List<NrVariableInstanceEntityImpl> exportVariableInstanceEntityImpl(String processInstanceId) {
        return this.variableDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importNrDeadLetterJobEntityImpl(List<NrDeadLetterJobEntityImpl> nrDeadLetterJobEntityImpl) {
        this.deadLetterJobDao.batchInsert(nrDeadLetterJobEntityImpl);
    }

    @Override
    public List<NrDeadLetterJobEntityImpl> exportNrDeadLetterJobEntityImpl(String processInstanceId) {
        return this.deadLetterJobDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importNrJobEntityImpl(List<NrJobEntityImpl> nrJobEntityImpl) {
        this.jobDao.batchInsert(nrJobEntityImpl);
    }

    @Override
    public List<NrJobEntityImpl> exportNrJobEntityImpl(String processInstanceId) {
        return this.jobDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importNrSuspendedJobEntityImpl(List<NrSuspendedJobEntityImpl> nrSuspendedJobEntityImpl) {
        this.suspendedJobDao.batchInsert(nrSuspendedJobEntityImpl);
    }

    @Override
    public List<NrSuspendedJobEntityImpl> exportNrSuspendedJobEntityImpl(String processInstanceId) {
        return this.suspendedJobDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importNrTimerJobEntityImpl(List<NrTimerJobEntityImpl> nrTimerJobEntityImpl) {
        this.timerJobDao.batchInsert(nrTimerJobEntityImpl);
    }

    @Override
    public List<NrTimerJobEntityImpl> exportNrTimerJobEntityImpl(String processInstanceId) {
        return this.timerJobDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void importNrEventSubscrEntityImpl(List<NrEventSubscrEntityImpl> nrEventSubscrEntityImpl) {
        this.eventSubscrDao.batchInsert(nrEventSubscrEntityImpl);
    }

    @Override
    public List<NrEventSubscrEntityImpl> exportNrEventSubscrEntityImpl(String processInstanceId) {
        return this.eventSubscrDao.queryByProcInstId(processInstanceId);
    }

    @Override
    public void deleteByProcessId(String processInstanceId, List<String> taskIds) {
        if (processInstanceId != null) {
            this.eventSubscrDao.deleteByProcInstId(processInstanceId);
            this.timerJobDao.deleteByProcInstId(processInstanceId);
            this.suspendedJobDao.deleteByProcInstId(processInstanceId);
            this.jobDao.deleteByProcInstId(processInstanceId);
            this.deadLetterJobDao.deleteByProcInstId(processInstanceId);
            this.identityLinkDao.deleteByProcInstId(processInstanceId);
            if (taskIds.size() > 0) {
                for (String taskId : taskIds) {
                    this.identityLinkDao.deleteByTaskId(taskId);
                }
            }
            this.taskDao.deleteByProcInstId(processInstanceId);
            this.executionDao.deleteByProcInstId(processInstanceId);
        }
    }

    @Override
    public void importNrActvityGeneralByteArray(List<NrActvityGeneralByteArray> nrActvityGeneralByteArraies) {
        for (NrActvityGeneralByteArray nrActvityGeneralByteArray : nrActvityGeneralByteArraies) {
            this.actvityGeneralByteArrayDao.deleteById(nrActvityGeneralByteArray.getId());
        }
        this.actvityGeneralByteArrayDao.batchInsert(nrActvityGeneralByteArraies);
    }

    @Override
    public List<NrActvityGeneralByteArray> exportNrActvityGeneralByteArray(String id) {
        return this.actvityGeneralByteArrayDao.queryById(id);
    }
}

