/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 *  org.activiti.engine.history.HistoricProcessInstance
 */
package com.jiuqi.nr.bpm.movedata.impl;

import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.movedata.ActivitiTable;
import com.jiuqi.nr.bpm.movedata.IActivitiTableMoveDataForSyncService;
import com.jiuqi.nr.bpm.movedata.IActivitiTableMoveDataService;
import com.jiuqi.nr.bpm.movedata.IHistoricActivitiTableMoveDataService;
import com.jiuqi.nr.bpm.movedata.NrActvityGeneralByteArray;
import com.jiuqi.nr.bpm.movedata.NrDeadLetterJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrEventSubscrEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricActivityInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricAttachmentImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricCommentImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricDetailImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricTaskInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrIdentityLinkEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrSuspendedJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrTaskEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrTimerJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiTableMoveDataForSyncService
implements IActivitiTableMoveDataForSyncService {
    @Autowired
    IActivitiTableMoveDataService activitiTableMoveDataService;
    @Autowired
    IHistoricActivitiTableMoveDataService historicActivitiTableMoveDataService;
    @Autowired
    NrParameterUtils nrParameterUtils;

    @Override
    public void importActivitiTable(ActivitiTable activitiTable, String businessKey) {
        String formSchemeKey = BusinessKeyFormatter.parsingFromString(businessKey).getFormSchemeKey();
        RunTimeService runTimeService = this.nrParameterUtils.getProcessEngine(formSchemeKey).get().getRunTimeService();
        Optional<ProcessInstance> processInstance = runTimeService.queryInstanceByBusinessKey(businessKey, false);
        String processInstanceId = null;
        if (processInstance.isPresent()) {
            processInstanceId = processInstance.get().getId();
        }
        if (processInstanceId != null) {
            ArrayList<String> taskIds = activitiTable.getNrTaskEntityImpl() == null ? new ArrayList<String>() : activitiTable.getNrTaskEntityImpl().stream().map(o -> o.getId()).collect(Collectors.toList());
            this.activitiTableMoveDataService.deleteByProcessId(processInstanceId, taskIds);
        }
        this.activitiTableMoveDataService.importExecutionEntity(activitiTable.getNrExecutionEntityImpl());
        this.activitiTableMoveDataService.importTaskEntityImpl(activitiTable.getNrTaskEntityImpl());
        this.activitiTableMoveDataService.importIdentityLinkEntityImpl(activitiTable.getNrIdentityLinkEntityImpl());
        this.activitiTableMoveDataService.importNrActvityGeneralByteArray(activitiTable.getNrActvityGeneralByteArray());
        this.activitiTableMoveDataService.importVariableInstanceEntityImpl(activitiTable.getNrVariableInstanceEntityImpl());
        this.activitiTableMoveDataService.importNrDeadLetterJobEntityImpl(activitiTable.getNrDeadLetterJobEntityImpl());
        this.activitiTableMoveDataService.importNrEventSubscrEntityImpl(activitiTable.getNrEventSubscrEntityImpl());
        this.activitiTableMoveDataService.importNrJobEntityImpl(activitiTable.getNrJobEntityImpl());
        this.activitiTableMoveDataService.importNrSuspendedJobEntityImpl(activitiTable.getNrSuspendedJobEntityImpl());
        this.activitiTableMoveDataService.importNrTimerJobEntityImpl(activitiTable.getNrTimerJobEntityImpl());
        this.historicActivitiTableMoveDataService.importHistoricActivityInstanceEntityImpl(activitiTable.getNrHistoricActivityInstanceEntityImpl(), processInstanceId);
        this.historicActivitiTableMoveDataService.importHistoricProcessInstanceEntityImpl(activitiTable.getNrHistoricProcessInstanceEntityImpl(), processInstanceId);
        this.historicActivitiTableMoveDataService.importHistoricTaskInstanceEntityImpl(activitiTable.getNrHistoricTaskInstanceEntityImpl(), processInstanceId);
        this.historicActivitiTableMoveDataService.importHistoricVariableInstanceEntityImpl(activitiTable.getNrHistoricVariableInstanceEntityImpl(), processInstanceId);
        this.historicActivitiTableMoveDataService.importHistoricAttachmentEntityImpl(activitiTable.getNrHistoricAttachmentImpl(), processInstanceId);
        this.historicActivitiTableMoveDataService.importHistoricCommentEntityImpl(activitiTable.getNrHistoricCommentImpl(), processInstanceId);
        this.historicActivitiTableMoveDataService.importHistoricDetailEntityImpl(activitiTable.getNrHistoricDetailImpl(), processInstanceId);
    }

    @Override
    public ActivitiTable exportActivitiTable(String businessKey) {
        List<NrActvityGeneralByteArray> nrActvityGeneralByteArrayList;
        String byteArrayId;
        List<NrTimerJobEntityImpl> nrTimerJobEntityImpl;
        ActivitiTable activitiTable = new ActivitiTable();
        String formSchemeKey = BusinessKeyFormatter.parsingFromString(businessKey).getFormSchemeKey();
        RunTimeService runTimeService = this.nrParameterUtils.getProcessEngine(formSchemeKey).get().getRunTimeService();
        Optional<ProcessInstance> processInstance = runTimeService.queryInstanceByBusinessKey(businessKey, false);
        String processInstanceId = null;
        if (processInstance.isPresent()) {
            processInstanceId = processInstance.get().getId();
        }
        List<NrExecutionEntityImpl> nrExecutionEntityImpl = StringUtils.isEmpty(processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportExecutionEntity(processInstanceId);
        List<NrIdentityLinkEntityImpl> nrIdentityLinkEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportIdentityLinkEntityImpl(processInstanceId);
        List<NrTaskEntityImpl> nrTaskEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportTaskEntityImpl(processInstanceId);
        List<NrVariableInstanceEntityImpl> nrVariableInstanceEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportVariableInstanceEntityImpl(processInstanceId);
        List<NrDeadLetterJobEntityImpl> nrDeadLetterJobEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportNrDeadLetterJobEntityImpl(processInstanceId);
        List<NrEventSubscrEntityImpl> nrEventSubscrEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportNrEventSubscrEntityImpl(processInstanceId);
        List<NrJobEntityImpl> nrJobEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportNrJobEntityImpl(processInstanceId);
        List<NrSuspendedJobEntityImpl> nrSuspendedJobEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportNrSuspendedJobEntityImpl(processInstanceId);
        List<NrTimerJobEntityImpl> list = nrTimerJobEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.activitiTableMoveDataService.exportNrTimerJobEntityImpl(processInstanceId);
        if (StringUtils.isEmpty((String)processInstanceId)) {
            HistoryService historyService = this.nrParameterUtils.getProcessEngine(formSchemeKey).get().getHistoryService();
            List<Object> processInstances = historyService.queryHistoricProcessInstanceByBusinessKey(businessKey);
            if ((processInstances = processInstances.stream().filter(o -> o.getEndTime() != null).collect(Collectors.toList())).size() > 0) {
                if (processInstances.size() == 1) {
                    processInstanceId = ((HistoricProcessInstance)processInstances.get(0)).getId();
                } else {
                    Collections.sort(processInstances, new Comparator<HistoricProcessInstance>(){

                        @Override
                        public int compare(HistoricProcessInstance arg0, HistoricProcessInstance arg1) {
                            return arg1.getEndTime().compareTo(arg0.getEndTime());
                        }
                    });
                    processInstanceId = ((HistoricProcessInstance)processInstances.get(0)).getId();
                }
            }
        }
        List<NrHistoricActivityInstanceEntityImpl> nrHistoricActivityInstanceEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricActivityInstanceEntityImpl(processInstanceId);
        List<NrHistoricProcessInstanceEntityImpl> nrHistoricProcessInstanceEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricProcessInstanceEntityImpl(processInstanceId);
        List<NrHistoricTaskInstanceEntityImpl> nrHistoricTaskInstanceEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricTaskInstanceEntityImpl(processInstanceId);
        List<NrHistoricVariableInstanceEntityImpl> nrHistoricVariableInstanceEntityImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricVariableInstanceEntityImpl(processInstanceId);
        List<NrHistoricAttachmentImpl> nrHistoricAttachmentImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricAttachmentEntityImpl(processInstanceId);
        List<NrHistoricCommentImpl> nrHistoricCommentImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricCommentEntityImpl(processInstanceId);
        List<NrHistoricDetailImpl> nrHistoricDetailImpl = StringUtils.isEmpty((String)processInstanceId) ? Collections.emptyList() : this.historicActivitiTableMoveDataService.exportHistoricDetailEntityImpl(processInstanceId);
        activitiTable.setNrHistoricActivityInstanceEntityImpl(nrHistoricActivityInstanceEntityImpl);
        activitiTable.setNrHistoricProcessInstanceEntityImpl(nrHistoricProcessInstanceEntityImpl);
        activitiTable.setNrHistoricTaskInstanceEntityImpl(nrHistoricTaskInstanceEntityImpl);
        activitiTable.setNrHistoricVariableInstanceEntityImpl(nrHistoricVariableInstanceEntityImpl);
        activitiTable.setNrHistoricAttachmentImpl(nrHistoricAttachmentImpl);
        activitiTable.setNrHistoricCommentImpl(nrHistoricCommentImpl);
        activitiTable.setNrHistoricDetailImpl(nrHistoricDetailImpl);
        activitiTable.setNrExecutionEntityImpl(nrExecutionEntityImpl);
        activitiTable.setNrIdentityLinkEntityImpl(nrIdentityLinkEntityImpl);
        activitiTable.setNrTaskEntityImpl(nrTaskEntityImpl);
        activitiTable.setNrVariableInstanceEntityImpl(nrVariableInstanceEntityImpl);
        activitiTable.setNrDeadLetterJobEntityImpl(nrDeadLetterJobEntityImpl);
        activitiTable.setNrEventSubscrEntityImpl(nrEventSubscrEntityImpl);
        activitiTable.setNrSuspendedJobEntityImpl(nrSuspendedJobEntityImpl);
        activitiTable.setNrJobEntityImpl(nrJobEntityImpl);
        activitiTable.setNrTimerJobEntityImpl(nrTimerJobEntityImpl);
        ArrayList<NrActvityGeneralByteArray> nrActvityGeneralByteArrayLists = new ArrayList<NrActvityGeneralByteArray>();
        for (NrVariableInstanceEntityImpl nrVariableInstanceEntity : nrVariableInstanceEntityImpl) {
            byteArrayId = nrVariableInstanceEntity.getByteArrayId();
            if (byteArrayId == null || StringUtils.isEmpty((String)byteArrayId) || (nrActvityGeneralByteArrayList = this.activitiTableMoveDataService.exportNrActvityGeneralByteArray(byteArrayId)) == null) continue;
            nrActvityGeneralByteArrayLists.addAll(nrActvityGeneralByteArrayList);
        }
        for (NrHistoricVariableInstanceEntityImpl nrHistoricVariableInstanceEntity : nrHistoricVariableInstanceEntityImpl) {
            byteArrayId = nrHistoricVariableInstanceEntity.getByteArrayId();
            if (byteArrayId == null || StringUtils.isEmpty((String)byteArrayId) || (nrActvityGeneralByteArrayList = this.activitiTableMoveDataService.exportNrActvityGeneralByteArray(byteArrayId)) == null) continue;
            nrActvityGeneralByteArrayLists.addAll(nrActvityGeneralByteArrayList);
        }
        activitiTable.setNrActvityGeneralByteArray(nrActvityGeneralByteArrayLists);
        return activitiTable;
    }
}

