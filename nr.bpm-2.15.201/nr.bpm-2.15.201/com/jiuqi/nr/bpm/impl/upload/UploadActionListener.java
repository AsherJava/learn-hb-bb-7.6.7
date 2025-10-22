/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bpm.impl.upload;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySetImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionEventListener;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.UserActionBatchCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.event.UserActionCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.process.util.ProcessUtil;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class UploadActionListener
implements UserActionEventListener {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private ProcessUtil processUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private SystemUserService systemUserService;
    static final Set<String> LISTENINGACTIONS = new HashSet<String>();

    public void insertHiRecord(UserActionCompleteEvent event) {
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(event.getBusinessKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme == null) {
            return;
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        String actorId = event.getActorId();
        User systemUser = this.getSystemUser();
        if (systemUser != null && actorId.equals(systemUser.getId())) {
            actorId = "00000000-0000-0000-0000-000000000000";
        }
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, businessKey, businessKey.getFormKey());
        UserActionCompleteEventImpl completeEvent = (UserActionCompleteEventImpl)event;
        TaskContext taskContext = completeEvent.getContext();
        Boolean forceState = false;
        Integer exeOrder = 0;
        Object force = taskContext.get(this.nrParameterUtils.getForceMapKey());
        Object order = taskContext.get(this.nrParameterUtils.getExecuteOrder());
        if (force != null && force instanceof Boolean) {
            forceState = (Boolean)force;
        }
        if (order != null) {
            exeOrder = (Integer)order;
        }
        Object returnTypeString = taskContext.get("returnType");
        this.nrParameterUtils.commitHiQuery(formScheme, masterKeys, event.getActionId(), event.getUserTaskId(), event.getComment(), actorId, event.getOperationId(), forceState, exeOrder, event.getTaskId(), returnTypeString == null ? "" : returnTypeString.toString());
    }

    public void batchInsertHiRecord(UserActionBatchCompleteEvent event) {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        BusinessKeySetImpl bussinessKeySet = (BusinessKeySetImpl)event.getBusinessKeySet();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(bussinessKeySet.getFormSchemeKey());
        if (formScheme == null) {
            return;
        }
        TableModelDefine table = this.nrParameterUtils.getTableByCode(TableConstant.getSysUploadRecordTableName(formScheme));
        List<ColumnModelDefine> allColumns = this.nrParameterUtils.getAllFieldsInTable(table.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        ColumnModelDefine processFieldDefine = null;
        for (ColumnModelDefine columnDefine : allColumns) {
            if (columnDefine.getCode().equals("PROCESSKEY")) {
                processFieldDefine = columnDefine;
            }
            queryModel.getColumns().add(new NvwaQueryColumn(columnDefine));
        }
        List<DimensionValueSet> masterKeys = this.buildMasterKeys(bussinessKeySet);
        DimensionValueSet queryKey = this.processUtil.buildBatchMasterKey(bussinessKeySet.getMasterEntitySet(), bussinessKeySet);
        String actorId = event.getActor().getIdentityId();
        User systemUser = this.getSystemUser();
        if (systemUser != null && actorId.equals(systemUser.getId())) {
            actorId = "00000000-0000-0000-0000-000000000000";
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(table.getName());
        if (processFieldDefine != null) {
            String dimensionName = dimensionChanger.getDimensionName(processFieldDefine);
            for (DimensionValueSet masterKey : masterKeys) {
                masterKey.setValue(dimensionName, (Object)this.nrParameterUtils.getProcessKey(formScheme.getKey()));
            }
            queryKey.setValue(dimensionName, (Object)this.nrParameterUtils.getProcessKey(formScheme.getKey()));
        }
        UserActionBatchCompleteEventImpl completeEvent = (UserActionBatchCompleteEventImpl)event;
        TaskContext taskContext = completeEvent.getContext();
        Boolean forceState = false;
        Integer exeOrder = 0;
        Object force = taskContext.get(this.nrParameterUtils.getForceMapKey());
        Object order = taskContext.get(this.nrParameterUtils.getExecuteOrder());
        if (force != null && force instanceof Boolean) {
            forceState = (Boolean)force;
        }
        if (order != null) {
            exeOrder = (Integer)order;
        }
        Object returnTypeString = taskContext.get("returnType");
        this.nrParameterUtils.batchCommitHiQuery(queryModel, context, table.getName(), masterKeys, allColumns, event.getActionId(), event.getUserTaskId(), event.getComment(), actorId, event.getOperationId(), forceState, exeOrder, bussinessKeySet.getFormSchemeKey(), event.getTaskId(), returnTypeString == null ? "" : returnTypeString.toString());
    }

    private List<DimensionValueSet> buildMasterKeys(BusinessKeySet businessKeySet) {
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        MasterEntitySet masterEntitySet = businessKeySet.getMasterEntitySet();
        masterEntitySet.reset();
        Set<String> formKeys = businessKeySet.getFormKey();
        if (!CollectionUtils.isEmpty(formKeys)) {
            while (masterEntitySet.next()) {
                MasterEntityInfo masterEntity = masterEntitySet.getCurrent();
                for (String formKey : formKeys) {
                    BusinessKeyImpl businessKeyInfo = new BusinessKeyImpl();
                    businessKeyInfo.setFormKey(formKey);
                    businessKeyInfo.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                    businessKeyInfo.setMasterEntity(masterEntity);
                    businessKeyInfo.setPeriod(businessKeySet.getPeriod());
                    DimensionValueSet masterKey = this.processUtil.buildUploadMasterKey(businessKeyInfo);
                    dimensionValueSets.add(masterKey);
                }
            }
        } else {
            while (masterEntitySet.next()) {
                MasterEntityInfo masterEntity = masterEntitySet.getCurrent();
                BusinessKeyImpl businessKeyInfo = new BusinessKeyImpl();
                businessKeyInfo.setFormSchemeKey(businessKeySet.getFormSchemeKey());
                businessKeyInfo.setMasterEntity(masterEntity);
                businessKeyInfo.setPeriod(businessKeySet.getPeriod());
                DimensionValueSet masterKey = this.processUtil.buildUploadMasterKey(businessKeyInfo);
                dimensionValueSets.add(masterKey);
            }
        }
        masterEntitySet.reset();
        return dimensionValueSets;
    }

    @Override
    public Set<String> getListeningActionId() {
        return null;
    }

    @Override
    public Integer getSequence() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onPrepare(UserActionPrepareEvent event) throws Exception {
    }

    @Override
    public void onComplete(UserActionCompleteEvent event) throws Exception {
        this.insertHiRecord(event);
    }

    @Override
    public void onBatchPrepare(UserActionBatchPrepareEvent event) throws Exception {
    }

    @Override
    public void onProgrocessChanged(UserActionProgressEvent event) throws Exception {
    }

    @Override
    public void onBatchComplete(UserActionBatchCompleteEvent event) throws Exception {
        this.batchInsertHiRecord(event);
    }

    private User getSystemUser() {
        List users = this.systemUserService.getUsers();
        if (users != null && users.size() > 0) {
            return (User)users.get(0);
        }
        return null;
    }
}

