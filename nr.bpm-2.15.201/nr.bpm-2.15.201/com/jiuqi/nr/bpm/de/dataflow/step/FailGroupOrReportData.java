/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.BaseUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.inter.IResultData;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.StepQueryState;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FailGroupOrReportData
extends BaseUpload
implements IResultData {
    @Override
    public int getType() {
        return WorkFlowType.FORM.getValue();
    }

    @Override
    public void resultData(BatchStepByStepParam stepByOptParam, List<StepTree> subList, String period, StepQueryState stepQueryState, BatchStepByStepResult upload, boolean isUpload, boolean stepByStepBackAll) {
        if (stepByStepBackAll && !isUpload) {
            this.resultDataAll(stepByOptParam, subList, period, stepQueryState, upload);
        } else {
            HashMap<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap = new HashMap<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>>();
            HashSet<String> operateUnits = new HashSet<String>();
            HashMap<String, LinkedHashSet<String>> unitAndForms = new HashMap<String, LinkedHashSet<String>>();
            HashMap<String, LinkedHashSet<String>> unitAndGroups = new HashMap<String, LinkedHashSet<String>>();
            ArrayList<BatchNoOperate> childrenOperate = null;
            HashMap childrenMap = null;
            Map<Object, Object> queryState = new HashMap();
            ArrayList<IEntityRow> directData = new ArrayList();
            for (StepTree stepTree : subList) {
                childrenMap = new HashMap();
                LinkedHashSet<String> selectResourceMap = stepTree.getResourceMap();
                List directChildRows = stepTree.getEntityTable().getChildRows(stepTree.getId());
                BatchNoOperate parentOperate = new BatchNoOperate();
                parentOperate.setId(stepTree.getId());
                parentOperate.setCode(stepTree.getCode());
                parentOperate.setName(stepTree.getTitle());
                if (isUpload) {
                    queryState = stepQueryState.queryState(directChildRows, selectResourceMap);
                    directData = directChildRows;
                } else if (stepTree.getParentId() != null) {
                    String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
                    DimensionValueSet parentDim = new DimensionValueSet();
                    parentDim.setValue("DATATIME", (Object)period);
                    parentDim.setValue(mainDimName, (Object)stepTree.getParentId());
                    List<IEntityRow> parentData = this.deEntityHelper.getEntityRow(stepByOptParam.getFormSchemeKey(), parentDim, AuthorityType.None);
                    queryState = this.getParentState(stepQueryState, parentData, selectResourceMap);
                    directData = parentData;
                }
                LinkedHashSet<String> temp = new LinkedHashSet<String>();
                if (queryState != null && queryState.size() > 0) {
                    for (Map.Entry<Object, Object> actionState : queryState.entrySet()) {
                        IEntityRow entityRow = (IEntityRow)actionState.getKey();
                        Map resourceState = (Map)actionState.getValue();
                        BatchNoOperate batchNoOperate = new BatchNoOperate();
                        batchNoOperate.setId(entityRow.getEntityKeyData());
                        batchNoOperate.setCode(entityRow.getCode());
                        batchNoOperate.setName(entityRow.getTitle());
                        for (Map.Entry resource : resourceState.entrySet()) {
                            String resourceId = (String)resource.getKey();
                            ActionStateBean value = (ActionStateBean)resource.getValue();
                            boolean fliterConditions = this.isFliterConditions(stepByOptParam.getFormSchemeKey(), period, entityRow.getEntityKeyData(), directData, resourceId, stepByOptParam.getStepFormKeyMap(), stepByOptParam.getStepGroupKeyMap());
                            if (fliterConditions || value.getCode() != null) {
                                BatchNoOperate resourceOp = this.commonUtil.getFormByKey(stepByOptParam.getFormSchemeKey(), resourceId);
                                if (childrenMap.get(resourceOp) == null) {
                                    childrenOperate = new ArrayList<BatchNoOperate>();
                                    childrenOperate.add(batchNoOperate);
                                    childrenMap.put(resourceOp, childrenOperate);
                                } else {
                                    ((List)childrenMap.get(resourceOp)).add(batchNoOperate);
                                }
                                temp.add(resourceId);
                            }
                            operateUnits.add(stepTree.getId());
                        }
                        noOperateGroupOrFormMap.put(parentOperate, childrenMap);
                    }
                    if (temp.size() > 0) {
                        selectResourceMap.removeAll(temp);
                        if (selectResourceMap != null && selectResourceMap.size() > 0) {
                            if (unitAndForms.get(stepTree.getId()) == null) {
                                unitAndForms.put(stepTree.getId(), selectResourceMap);
                            }
                            if (unitAndGroups.get(stepTree.getId()) == null) {
                                unitAndGroups.put(stepTree.getId(), selectResourceMap);
                            }
                        }
                    } else {
                        unitAndForms.put(stepTree.getId(), selectResourceMap);
                        unitAndGroups.put(stepTree.getId(), selectResourceMap);
                    }
                } else {
                    operateUnits.add(stepTree.getId());
                    if (unitAndForms.get(stepTree.getId()) == null) {
                        unitAndForms.put(stepTree.getId(), selectResourceMap);
                    }
                    if (unitAndGroups.get(stepTree.getId()) == null) {
                        unitAndGroups.put(stepTree.getId(), selectResourceMap);
                    }
                }
                if (noOperateGroupOrFormMap != null && noOperateGroupOrFormMap.size() != 0) {
                    if (upload.getNoOperateGroupOrFormMap() != null && childrenMap.size() > 0) {
                        upload.getNoOperateGroupOrFormMap().put(parentOperate, childrenMap);
                    } else {
                        upload.setNoOperateGroupOrFormMap(noOperateGroupOrFormMap);
                    }
                }
                if (upload.getOperateUnits() != null) {
                    upload.getOperateUnits().addAll(operateUnits);
                } else {
                    upload.setOperateUnits(operateUnits);
                }
                if (upload.getOperateUnitAndForms() != null) {
                    upload.getOperateUnitAndForms().put(stepTree.getId(), selectResourceMap);
                } else {
                    upload.setOperateUnitAndForms(unitAndForms);
                }
                if (upload.getOperateUnitAndGroups() != null) {
                    upload.getOperateUnitAndGroups().put(stepTree.getId(), selectResourceMap);
                    continue;
                }
                upload.setOperateUnitAndGroups(unitAndGroups);
            }
            CompleteMsg executeAction = new CompleteMsg();
            if (operateUnits != null && operateUnits.size() > 0 && unitAndForms != null && unitAndForms.size() > 0 && unitAndGroups != null && unitAndGroups.size() > 0) {
                executeAction = this.executeAction(stepByOptParam, period, operateUnits, unitAndForms, unitAndGroups);
            }
            upload.setCompleteMsg(executeAction);
        }
    }

    public void resultDataAll(BatchStepByStepParam stepByOptParam, List<StepTree> subList, String period, StepQueryState stepQueryState, BatchStepByStepResult upload) {
        HashMap<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap = new HashMap<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>>();
        HashSet<String> operateUnits = new HashSet<String>();
        HashMap<String, LinkedHashSet<String>> unitAndForms = new HashMap<String, LinkedHashSet<String>>();
        HashMap<String, LinkedHashSet<String>> unitAndGroups = new HashMap<String, LinkedHashSet<String>>();
        EntityViewDefine dwEntityView = this.dimensionUtil.getDwEntityView(stepByOptParam.getFormSchemeKey());
        ArrayList<BatchNoOperate> childrenOperate = null;
        HashMap childrenMap = null;
        Map<Object, Object> queryState = new HashMap();
        ArrayList<IEntityRow> directData = new ArrayList();
        FormSchemeDefine formSchemeDefine = this.stepUtil.getFormSchemeDefine(stepByOptParam.getFormSchemeKey());
        String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
        for (StepTree stepTree : subList) {
            childrenMap = new HashMap();
            LinkedHashSet<String> selectResourceMap = stepTree.getResourceMap();
            BatchNoOperate parentOperate = new BatchNoOperate();
            parentOperate.setId(stepTree.getId());
            parentOperate.setCode(stepTree.getCode());
            parentOperate.setName(stepTree.getTitle());
            DimensionValueSet dimension = new DimensionValueSet();
            dimension.setValue(mainDimName, (Object)stepTree.getId());
            dimension.setValue("DATATIME", (Object)period);
            String[] parentId = this.deEntityHelper.getAllParent(dimension, formSchemeDefine);
            if (parentId != null && parentId.length > 0) {
                ArrayList parentList = new ArrayList(parentId.length);
                Collections.addAll(parentList, parentId);
                DimensionValueSet parentDim = new DimensionValueSet();
                parentDim.setValue("DATATIME", (Object)period);
                parentDim.setValue(mainDimName, parentList);
                List<IEntityRow> parentData = this.deEntityHelper.getEntityRow(stepByOptParam.getFormSchemeKey(), parentDim, AuthorityType.None);
                queryState = this.getParentState(stepQueryState, parentData, selectResourceMap);
                directData = parentData;
            }
            boolean canRturn = true;
            if (queryState != null && queryState.size() > 0) {
                for (Map.Entry actionState : queryState.entrySet()) {
                    IEntityRow entityRow = (IEntityRow)actionState.getKey();
                    Map resourceState = (Map)actionState.getValue();
                    BatchNoOperate batchNoOperate = new BatchNoOperate();
                    batchNoOperate.setId(entityRow.getEntityKeyData());
                    batchNoOperate.setCode(entityRow.getCode());
                    batchNoOperate.setName(entityRow.getTitle());
                    for (Map.Entry resource : resourceState.entrySet()) {
                        String resourceId = (String)resource.getKey();
                        ActionStateBean actionStateValue = (ActionStateBean)resource.getValue();
                        boolean fliterConditions = this.isFliterConditions(stepByOptParam.getFormSchemeKey(), period, entityRow.getEntityKeyData(), directData, resourceId, stepByOptParam.getStepFormKeyMap(), stepByOptParam.getStepGroupKeyMap());
                        if (!fliterConditions && actionStateValue.getCode() == null) continue;
                        boolean hasUnitAuditOperation = this.hasUnitAuditOperation(dwEntityView.getEntityId(), entityRow.getEntityKeyData(), formSchemeDefine.getDateTime(), period);
                        if (hasUnitAuditOperation && canRturn) {
                            operateUnits.add(entityRow.getEntityKeyData());
                            if (unitAndForms.get(entityRow.getEntityKeyData()) == null) {
                                unitAndForms.put(entityRow.getEntityKeyData(), selectResourceMap);
                            }
                            if (unitAndGroups.get(entityRow.getEntityKeyData()) == null) {
                                unitAndGroups.put(entityRow.getEntityKeyData(), selectResourceMap);
                            }
                            operateUnits.add(stepTree.getId());
                            continue;
                        }
                        BatchNoOperate resourceOp = this.commonUtil.getFormByKey(stepByOptParam.getFormSchemeKey(), resourceId);
                        if (childrenMap.get(resourceOp) == null) {
                            childrenOperate = new ArrayList<BatchNoOperate>();
                            childrenOperate.add(batchNoOperate);
                            childrenMap.put(resourceOp, childrenOperate);
                        } else {
                            ((List)childrenMap.get(resourceOp)).add(batchNoOperate);
                        }
                        noOperateGroupOrFormMap.put(parentOperate, childrenMap);
                        canRturn = false;
                    }
                }
            } else {
                operateUnits.add(stepTree.getId());
            }
            if (unitAndForms.get(stepTree.getId()) == null) {
                unitAndForms.put(stepTree.getId(), selectResourceMap);
            }
            if (unitAndGroups.get(stepTree.getId()) == null) {
                unitAndGroups.put(stepTree.getId(), selectResourceMap);
            }
            if (noOperateGroupOrFormMap != null && noOperateGroupOrFormMap.size() != 0) {
                if (upload.getNoOperateGroupOrFormMap() != null && childrenMap.size() > 0) {
                    upload.getNoOperateGroupOrFormMap().put(parentOperate, childrenMap);
                } else {
                    upload.setNoOperateGroupOrFormMap(noOperateGroupOrFormMap);
                }
            }
            if (upload.getOperateUnits() != null) {
                upload.getOperateUnits().addAll(operateUnits);
            } else {
                upload.setOperateUnits(operateUnits);
            }
            if (upload.getOperateUnitAndForms() != null) {
                upload.getOperateUnitAndForms().put(stepTree.getId(), selectResourceMap);
            } else {
                upload.setOperateUnitAndForms(unitAndForms);
            }
            if (upload.getOperateUnitAndGroups() != null) {
                upload.getOperateUnitAndGroups().put(stepTree.getId(), selectResourceMap);
                continue;
            }
            upload.setOperateUnitAndGroups(unitAndGroups);
        }
        Map<String, Object> otherDim = this.getOtherDim(stepByOptParam.getStepUnit(), mainDimName);
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        DimensionValueSet dimension = null;
        for (String unitKey : operateUnits) {
            dimension = new DimensionValueSet();
            dimension.setValue("DATATIME", (Object)period);
            dimension.setValue(mainDimName, (Object)unitKey);
            if (otherDim != null && otherDim.size() > 0) {
                for (Map.Entry<String, Object> other : otherDim.entrySet()) {
                    dimension.setValue(other.getKey(), other.getValue());
                }
            }
            dims.add(dimension);
        }
        CompleteMsg executeAction = this.executeActionAll(stepByOptParam, period, operateUnits, unitAndForms, unitAndGroups, dims);
        upload.setCompleteMsg(executeAction);
    }

    private CompleteMsg execute(String formSchemeKey, DimensionValueSet dimension, String period, String resourceId, String acitonId, String taskNodeCode, String comment) {
        CompleteMsg executeTask = new CompleteMsg();
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setFormSchemeKey(formSchemeKey);
        executeParam.setDimSet(dimension);
        executeParam.setActionId(acitonId);
        executeParam.setPeriod(period);
        executeParam.setNodeId(taskNodeCode);
        executeParam.setTaskId(taskNodeCode);
        executeParam.setFormKey(resourceId);
        executeParam.setGroupKey(resourceId);
        executeParam.setComment(comment);
        executeTask = this.dataFlowService.executeTask(executeParam);
        return executeTask;
    }

    private CompleteMsg execute(String formSchemeKey, DimensionValueSet dimension, String period, LinkedHashSet<String> selectResourceMap, String acitonId, String taskNodeCode, String comment) {
        CompleteMsg executeTask = new CompleteMsg();
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setFormSchemeKey(formSchemeKey);
        executeParam.setDimSet(dimension);
        executeParam.setActionId(acitonId);
        executeParam.setPeriod(period);
        executeParam.setNodeId(taskNodeCode);
        executeParam.setTaskId(taskNodeCode);
        executeParam.setComment(comment);
        for (String key : selectResourceMap) {
            executeParam.setFormKey(key);
            executeParam.setGroupKey(key);
            executeTask = this.dataFlowService.executeTask(executeParam);
        }
        return executeTask;
    }
}

