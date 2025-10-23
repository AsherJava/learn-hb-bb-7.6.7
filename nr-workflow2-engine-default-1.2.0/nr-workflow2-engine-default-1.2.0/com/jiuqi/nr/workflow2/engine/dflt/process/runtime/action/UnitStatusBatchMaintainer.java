/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class UnitStatusBatchMaintainer
implements Closeable {
    private List<ProcessInstanceDO> unitInstances;
    private ProcessInstanceLockManager.ProcessInstanceLock unitInstanceLock;
    private IBusinessObjectCollection unitBusinessObjects;
    protected Collection<IBusinessKey> formOrGroupbusinessKeys;
    protected IActor actor;
    protected String taskKey;
    protected String formSchemeKey;
    protected ProcessInstanceQuery istQuery;
    protected WorkflowSettingsDO workflowSettings;
    protected ProcessInstanceLockManager instanceLockManager;
    private IFormConditionAccessService formAccessService;
    private IRunTimeViewController viewController;
    protected TransactionUtil transactionUtil;

    public void setFormOrGroupbusinessKeys(Collection<IBusinessKey> formOrGroupbusinessKeys) {
        this.formOrGroupbusinessKeys = formOrGroupbusinessKeys;
    }

    public void setActor(IActor actor) {
        this.actor = actor;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setInstanceQuery(ProcessInstanceQuery istQuery) {
        this.istQuery = istQuery;
    }

    public void setWorkflowSettings(WorkflowSettingsDO workflowSettings) {
        this.workflowSettings = workflowSettings;
    }

    public void setProcessInstanceLockManager(ProcessInstanceLockManager instanceLockManager) {
        this.instanceLockManager = instanceLockManager;
    }

    public void setFormConditionAccessService(IFormConditionAccessService formAccessService) {
        this.formAccessService = formAccessService;
    }

    public void setRunTimeViewController(IRunTimeViewController viewController) {
        this.viewController = viewController;
    }

    public void setTransactionUtil(TransactionUtil transactionUtil) {
        this.transactionUtil = transactionUtil;
    }

    public void run() {
        if (this.formOrGroupbusinessKeys.isEmpty()) {
            return;
        }
        if (this.workflowSettings.getWorkflowObjectType() != WorkflowObjectType.FORM && this.workflowSettings.getWorkflowObjectType() != WorkflowObjectType.FORM_GROUP) {
            return;
        }
        this.createUnitBusinessObjectCollection();
        this.transactionUtil.runWithinNewTransaction(() -> this.queryUnitInstance());
        this.lockUnitInstance();
        this.modifyUnitInstance();
        this.unLockUnitInstance();
    }

    private void createUnitBusinessObjectCollection() {
        Set<DimensionCombination> unitDimensions = this.formOrGroupbusinessKeys.stream().map(t -> t.getBusinessObject().getDimensions()).collect(Collectors.toSet());
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM) {
            this.unitBusinessObjects = UnitBusinessObjectCollection.buildFormObjectCollection(unitDimensions);
            return;
        }
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM_GROUP) {
            this.unitBusinessObjects = UnitBusinessObjectCollection.buildFormGroupObjectCollection(unitDimensions);
            return;
        }
        throw new RuntimeException("\u9519\u8bef\u7684\u65b9\u6cd5\u8c03\u7528\uff1a\u4e0d\u652f\u6301\u7684\u6d41\u7a0b\u62a5\u9001\u7c7b\u578b\u3002");
    }

    private void queryUnitInstance() {
        BusinessKeyCollection unitBusinessKeys = new BusinessKeyCollection(this.taskKey, this.unitBusinessObjects);
        Map<IBusinessObject, ProcessInstanceDO> existsUnitInstances = this.istQuery.queryInstances((IBusinessKeyCollection)unitBusinessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
        ArrayList<ProcessInstanceDO> newUnitInstances = new ArrayList<ProcessInstanceDO>();
        for (IBusinessObject businessObject : this.unitBusinessObjects) {
            if (existsUnitInstances.containsKey(businessObject)) continue;
            BusinessKey businessKey = new BusinessKey(this.taskKey, businessObject);
            ProcessInstanceDO ist = ProcessRepositoryUtil.newUnitInstance(this.workflowSettings.getWorkflowDefine(), (IBusinessKey)businessKey, this.actor);
            newUnitInstances.add(ist);
        }
        try {
            this.istQuery.insertInstances(newUnitInstances);
            for (ProcessInstanceDO instance : newUnitInstances) {
                existsUnitInstances.put(instance.getBusinessKey().getBusinessObject(), instance);
            }
        }
        catch (Exception batchInsertErr) {
            if (!(batchInsertErr instanceof SQLException)) {
                throw batchInsertErr;
            }
            for (ProcessInstanceDO instance : newUnitInstances) {
                try {
                    this.istQuery.insertInstance(instance);
                    existsUnitInstances.put(instance.getBusinessKey().getBusinessObject(), instance);
                }
                catch (Exception eachInsertErr) {
                    if (!(eachInsertErr instanceof SQLException)) {
                        throw eachInsertErr;
                    }
                    ProcessInstanceDO existsInstance = this.istQuery.queryInstance(instance.getBusinessKey());
                    if (existsInstance == null) {
                        throw eachInsertErr;
                    }
                    existsUnitInstances.put(instance.getBusinessKey().getBusinessObject(), existsInstance);
                }
            }
        }
        this.unitInstances = new ArrayList<ProcessInstanceDO>(existsUnitInstances.values());
    }

    private void lockUnitInstance() {
        Set<String> needLockIds = this.unitInstances.stream().map(ProcessInstanceDO::getId).collect(Collectors.toSet());
        this.unitInstanceLock = this.instanceLockManager.createLock(this.istQuery.getQueryModel(), needLockIds);
        try {
            this.instanceLockManager.lockAll(this.unitInstanceLock, 5000L);
        }
        catch (TimeoutException e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u9501\u5b9a\u6d41\u7a0b\u5b9e\u4f8b\u8d85\u65f6\u3002", (Throwable)e);
        }
    }

    private void modifyUnitInstance() {
        BusinessKeyCollection unitBusinessKeys = new BusinessKeyCollection(this.taskKey, this.unitBusinessObjects);
        Map<DimensionCombination, Map<String, ProcessInstanceDO>> formOrGroupInstances = this.istQuery.batchQueryFormOrGroupInstance((IBusinessKeyCollection)unitBusinessKeys);
        Map<DimensionCombination, Set<String>> allFormOrGroupKeys2Dim = this.getAllFormOrGroupKeys();
        HashSet<String> deleteIstIds = new HashSet<String>();
        for (ProcessInstanceDO unitInstance : this.unitInstances) {
            Map<String, ProcessInstanceDO> ist2Dim = formOrGroupInstances.get(unitInstance.getBusinessKey().getBusinessObject().getDimensions());
            if (ist2Dim == null || ist2Dim.isEmpty() || ist2Dim.size() == 1 && ist2Dim.containsKey("_NULL_")) {
                deleteIstIds.add(unitInstance.getId());
                continue;
            }
            Set<String> allFormOrGroupKeys = allFormOrGroupKeys2Dim.get(unitInstance.getBusinessKey().getBusinessObject().getDimensions());
            Set<String> formOrGroupStatusSet = ist2Dim.entrySet().stream().filter(t -> allFormOrGroupKeys.contains(t.getKey())).map(t -> ((ProcessInstanceDO)t.getValue()).getCurStatus()).collect(Collectors.toSet());
            String unitStatus = UnitStatusMaintainer.CalcUnitState(formOrGroupStatusSet);
            unitInstance.setCurStatus(unitStatus);
            Set<String> formOrGroupNodeSet = ist2Dim.entrySet().stream().filter(t -> allFormOrGroupKeys.contains(t.getKey())).map(t -> ((ProcessInstanceDO)t.getValue()).getCurNode()).collect(Collectors.toSet());
            String unitNode = UnitStatusMaintainer.CalcUnitNode(formOrGroupNodeSet);
            unitInstance.setCurNode(unitNode);
            unitInstance.setUpdateTime(Calendar.getInstance());
        }
        if (deleteIstIds.isEmpty()) {
            this.istQuery.modifyInstances(this.unitInstances, this.unitInstanceLock.getLockId());
        } else {
            this.istQuery.deleteInstances(deleteIstIds);
            List<ProcessInstanceDO> modifyIsts = this.unitInstances.stream().filter(o -> !deleteIstIds.contains(o.getId())).collect(Collectors.toList());
            if (!modifyIsts.isEmpty()) {
                this.istQuery.modifyInstances(modifyIsts, this.unitInstanceLock.getLockId());
            }
        }
    }

    private Map<DimensionCombination, Set<String>> getAllFormOrGroupKeys() {
        List forms = this.viewController.listFormByFormScheme(this.formSchemeKey);
        List formKeys = forms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        IBatchAccess batchAccess = this.formAccessService.getBatchVisible(this.formSchemeKey, this.unitBusinessObjects.getDimensions(), formKeys);
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM) {
            HashMap<DimensionCombination, Set<String>> allForm2Dim = new HashMap<DimensionCombination, Set<String>>();
            for (ProcessInstanceDO instance : this.unitInstances) {
                HashSet<String> allFormKeys = new HashSet<String>(formKeys.size());
                DimensionCombination dimensions = instance.getBusinessKey().getBusinessObject().getDimensions();
                for (String formKey : formKeys) {
                    AccessCode accessCode = batchAccess.getAccessCode(dimensions, formKey);
                    if (!"1".equals(accessCode.getCode())) continue;
                    allFormKeys.add(formKey);
                }
                allForm2Dim.put(dimensions, allFormKeys);
            }
            return allForm2Dim;
        }
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM_GROUP) {
            HashMap<DimensionCombination, Set<String>> allFormGroup2Dim = new HashMap<DimensionCombination, Set<String>>();
            for (ProcessInstanceDO instance : this.unitInstances) {
                HashSet allFormGroupKeys = new HashSet();
                DimensionCombination dimensions = instance.getBusinessKey().getBusinessObject().getDimensions();
                for (FormDefine form : forms) {
                    AccessCode accessCode = batchAccess.getAccessCode(dimensions, form.getKey());
                    if (!"1".equals(accessCode.getCode())) continue;
                    Set linkGroupKeys = this.viewController.listFormGroupByForm(form.getKey(), this.formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
                    allFormGroupKeys.addAll(linkGroupKeys);
                }
                allFormGroup2Dim.put(dimensions, allFormGroupKeys);
            }
            return allFormGroup2Dim;
        }
        throw new RuntimeException("\u9519\u8bef\u7684\u65b9\u6cd5\u8c03\u7528\uff1a\u4e0d\u652f\u6301\u7684\u6d41\u7a0b\u62a5\u9001\u7c7b\u578b\u3002");
    }

    private void unLockUnitInstance() {
        if (this.unitInstanceLock != null) {
            this.instanceLockManager.unlock(this.unitInstanceLock);
            this.unitInstanceLock = null;
        }
    }

    @Override
    public void close() {
        this.unLockUnitInstance();
    }
}

