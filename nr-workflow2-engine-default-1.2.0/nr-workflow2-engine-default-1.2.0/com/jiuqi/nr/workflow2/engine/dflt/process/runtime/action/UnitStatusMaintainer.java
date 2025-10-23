/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationUtil
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.utils.LogUtils;
import java.io.Closeable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class UnitStatusMaintainer
implements Closeable {
    private ProcessInstanceDO unitInstance;
    private ProcessInstanceLockManager.ProcessInstanceLock unitInstanceLock;
    protected IBusinessKey formOrGroupbusinessKey;
    protected IActor actor;
    protected String taskKey;
    protected String formSchemeKey;
    protected ProcessInstanceQuery istQuery;
    protected WorkflowSettingsDO workflowSettings;
    protected ProcessInstanceLockManager instanceLockManager;
    private IFormConditionAccessService formAccessService;
    private IRunTimeViewController viewController;
    protected TransactionUtil transactionUtil;

    public void setFormOrGroupbusinessKey(IBusinessKey formOrGroupbusinessKey) {
        this.formOrGroupbusinessKey = formOrGroupbusinessKey;
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
        if (this.workflowSettings.getWorkflowObjectType() != WorkflowObjectType.FORM && this.workflowSettings.getWorkflowObjectType() != WorkflowObjectType.FORM_GROUP) {
            return;
        }
        this.queryUnitInstance();
        this.lockUnitInstance();
        this.modifyUnitInstance();
        this.unLockUnitInstance();
    }

    private IBusinessKey createUnitBusinessKey() {
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM) {
            return new BusinessKey(this.taskKey, (IBusinessObject)new FormObject(this.formOrGroupbusinessKey.getBusinessObject().getDimensions(), "_NULL_"));
        }
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM_GROUP) {
            return new BusinessKey(this.taskKey, (IBusinessObject)new FormGroupObject(this.formOrGroupbusinessKey.getBusinessObject().getDimensions(), "_NULL_"));
        }
        throw new RuntimeException("\u9519\u8bef\u7684\u65b9\u6cd5\u8c03\u7528\uff1a\u4e0d\u652f\u6301\u7684\u6d41\u7a0b\u62a5\u9001\u7c7b\u578b\u3002");
    }

    private void queryUnitInstance() {
        block4: {
            IBusinessKey unitBusinessKey = this.createUnitBusinessKey();
            this.unitInstance = this.istQuery.queryInstance(unitBusinessKey);
            if (this.unitInstance == null) {
                this.unitInstance = ProcessRepositoryUtil.newUnitInstance(this.workflowSettings.getWorkflowDefine(), unitBusinessKey, this.actor);
                try {
                    this.transactionUtil.runWithinNewTransaction(() -> this.istQuery.insertInstance(this.unitInstance));
                }
                catch (Exception e) {
                    if (!(e instanceof SQLException)) {
                        throw e;
                    }
                    this.unitInstance = this.istQuery.queryInstance(unitBusinessKey);
                    if (this.unitInstance != null) break block4;
                    throw e;
                }
            }
        }
    }

    private void lockUnitInstance() {
        this.unitInstanceLock = this.instanceLockManager.createLock(this.istQuery.getQueryModel(), Arrays.asList(this.unitInstance.getId()));
        try {
            this.instanceLockManager.lockAll(this.unitInstanceLock, 5000L);
        }
        catch (TimeoutException e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u9501\u5b9a\u6d41\u7a0b\u5b9e\u4f8b\u8d85\u65f6\u3002", (Throwable)e);
        }
    }

    private void modifyUnitInstance() {
        Map<String, ProcessInstanceDO> formOrGroupInstances = this.istQuery.queryFormOrGroupInstance(this.unitInstance.getBusinessKey());
        if (formOrGroupInstances == null || formOrGroupInstances.isEmpty() || formOrGroupInstances.size() == 1 && formOrGroupInstances.containsKey("_NULL_")) {
            this.istQuery.deleteInstance(this.unitInstance.getId());
            return;
        }
        Set<String> allFormOrGroupKeys = this.getAllFormOrGroupKeys();
        Set<String> formOrGroupStatusSet = formOrGroupInstances.entrySet().stream().filter(t -> allFormOrGroupKeys.contains(t.getKey())).map(t -> ((ProcessInstanceDO)t.getValue()).getCurStatus()).collect(Collectors.toSet());
        String unitStatus = UnitStatusMaintainer.CalcUnitState(formOrGroupStatusSet);
        this.unitInstance.setCurStatus(unitStatus);
        Set<String> formOrGroupNodeSet = formOrGroupInstances.entrySet().stream().filter(t -> allFormOrGroupKeys.contains(t.getKey())).map(t -> ((ProcessInstanceDO)t.getValue()).getCurNode()).collect(Collectors.toSet());
        String unitNode = UnitStatusMaintainer.CalcUnitNode(formOrGroupNodeSet);
        this.unitInstance.setCurNode(unitNode);
        this.unitInstance.setUpdateTime(Calendar.getInstance());
        this.istQuery.modifyInstance(this.unitInstance, this.unitInstanceLock.getLockId());
    }

    static String CalcUnitState(Set<String> formOrGroupStatusSet) {
        String unitStatus = null;
        if (formOrGroupStatusSet.size() == 1) {
            unitStatus = (String)formOrGroupStatusSet.stream().findFirst().get();
        } else if (formOrGroupStatusSet.contains("rejected")) {
            unitStatus = "rejected";
        } else if (formOrGroupStatusSet.contains("backed")) {
            unitStatus = "backed";
        } else if (formOrGroupStatusSet.contains("submited")) {
            unitStatus = "part-submited";
        } else if (formOrGroupStatusSet.contains("confirmed")) {
            unitStatus = "part-confirmed";
        } else if (formOrGroupStatusSet.contains("reported")) {
            unitStatus = "part-reported";
        }
        return unitStatus;
    }

    static String CalcUnitNode(Set<String> formOrGroupNodeSet) {
        String unitNode = null;
        unitNode = formOrGroupNodeSet.contains("tsk_submit") ? "tsk_submit" : (formOrGroupNodeSet.contains("tsk_upload") ? "tsk_upload" : (formOrGroupNodeSet.contains("tsk_audit") ? "tsk_audit" : "tsk_audit_after_confirm"));
        return unitNode;
    }

    private Set<String> getAllFormOrGroupKeys() {
        List forms = this.viewController.listFormByFormScheme(this.formSchemeKey);
        List formKeys = forms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        DimensionCollection dimensions = DimensionCombinationUtil.convertToCollection((DimensionCombination)this.unitInstance.getBusinessKey().getBusinessObject().getDimensions());
        IBatchAccess batchAccess = this.formAccessService.getBatchVisible(this.formSchemeKey, dimensions, formKeys);
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM) {
            HashSet<String> allFormKeys = new HashSet<String>(formKeys.size());
            DimensionCombination dimension = this.unitInstance.getBusinessKey().getBusinessObject().getDimensions();
            for (String formKey : formKeys) {
                AccessCode accessCode = batchAccess.getAccessCode(dimension, formKey);
                if (!"1".equals(accessCode.getCode())) continue;
                allFormKeys.add(formKey);
            }
            return allFormKeys;
        }
        if (this.workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM_GROUP) {
            HashSet<String> allFormGroupKeys = new HashSet<String>();
            DimensionCombination dimension = this.unitInstance.getBusinessKey().getBusinessObject().getDimensions();
            for (FormDefine form : forms) {
                AccessCode accessCode = batchAccess.getAccessCode(dimension, form.getKey());
                if (!"1".equals(accessCode.getCode())) continue;
                Set linkGroupKeys = this.viewController.listFormGroupByForm(form.getKey(), this.formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
                allFormGroupKeys.addAll(linkGroupKeys);
            }
            return allFormGroupKeys;
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
        try {
            this.unLockUnitInstance();
        }
        catch (Exception e) {
            LogUtils.LOGGER.error("close starter runner fail.", e);
        }
    }
}

