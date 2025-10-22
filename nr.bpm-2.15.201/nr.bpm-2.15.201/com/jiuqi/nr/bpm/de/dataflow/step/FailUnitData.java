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
public class FailUnitData
extends BaseUpload
implements IResultData {
    @Override
    public int getType() {
        return WorkFlowType.ENTITY.getValue();
    }

    @Override
    public void resultData(BatchStepByStepParam stepByOptParam, List<StepTree> subList, String period, StepQueryState stepQueryState, BatchStepByStepResult upload, boolean isUpload, boolean stepByStepBackAll) {
        if (stepByStepBackAll && !isUpload) {
            this.stepByStepBackAll(stepByOptParam, subList, period, stepQueryState, upload);
        } else {
            HashMap<BatchNoOperate, List<BatchNoOperate>> noOperateUnits = new HashMap<BatchNoOperate, List<BatchNoOperate>>();
            HashSet<String> operateUnits = new HashSet<String>();
            HashMap<String, LinkedHashSet<String>> unitAndForms = new HashMap<String, LinkedHashSet<String>>();
            HashMap<String, LinkedHashSet<String>> unitAndGroups = new HashMap<String, LinkedHashSet<String>>();
            ArrayList<BatchNoOperate> childrenOperate = null;
            Map<Object, Object> queryState = new HashMap();
            ArrayList<IEntityRow> directData = new ArrayList();
            if (subList != null && subList.size() > 0) {
                for (StepTree stepTree : subList) {
                    LinkedHashSet<String> selectResourceMap = stepTree.getResourceMap();
                    List directChildRows = stepTree.getEntityTable().getChildRows(stepTree.getId());
                    BatchNoOperate parentOperate = new BatchNoOperate();
                    parentOperate.setId(stepTree.getId());
                    parentOperate.setCode(stepTree.getCode());
                    parentOperate.setName(stepTree.getTitle());
                    if (isUpload) {
                        queryState = stepQueryState.queryState(directChildRows, selectResourceMap);
                        directData = directChildRows;
                    } else {
                        String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
                        DimensionValueSet parentDim = new DimensionValueSet();
                        parentDim.setValue("DATATIME", (Object)period);
                        parentDim.setValue(mainDimName, (Object)stepTree.getParentId());
                        List<IEntityRow> parentData = this.deEntityHelper.getEntityRow(stepByOptParam.getFormSchemeKey(), parentDim, AuthorityType.None);
                        queryState = this.getParentState(stepQueryState, parentData, selectResourceMap);
                        directData = parentData;
                    }
                    childrenOperate = new ArrayList<BatchNoOperate>();
                    if (queryState != null && queryState.size() > 0) {
                        for (Map.Entry<Object, Object> state : queryState.entrySet()) {
                            IEntityRow entityRow = (IEntityRow)state.getKey();
                            BatchNoOperate batchNoOperate = new BatchNoOperate();
                            batchNoOperate.setId(entityRow.getEntityKeyData());
                            batchNoOperate.setCode(entityRow.getCode());
                            batchNoOperate.setName(entityRow.getTitle());
                            String resourceId = null;
                            boolean fliterConditions = this.isFliterConditions(stepByOptParam.getFormSchemeKey(), period, entityRow.getEntityKeyData(), directData, resourceId, stepByOptParam.getStepFormKeyMap(), stepByOptParam.getStepGroupKeyMap());
                            if (!fliterConditions) continue;
                            childrenOperate.add(batchNoOperate);
                        }
                        noOperateUnits.put(parentOperate, childrenOperate);
                    } else {
                        operateUnits.add(stepTree.getId());
                        unitAndForms.put(stepTree.getId(), selectResourceMap);
                        unitAndGroups.put(stepTree.getId(), selectResourceMap);
                    }
                    if (noOperateUnits != null && noOperateUnits.size() != 0) {
                        if (upload.getNoOperateUnitMap() != null && childrenOperate.size() > 0) {
                            upload.getNoOperateUnitMap().put(parentOperate, childrenOperate);
                        } else {
                            upload.setNoOperateUnitMap(noOperateUnits);
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
            }
            CompleteMsg executeAction = this.executeAction(stepByOptParam, period, operateUnits, unitAndForms, unitAndGroups);
            upload.setCompleteMsg(executeAction);
        }
    }

    public void stepByStepBackAll(BatchStepByStepParam stepByOptParam, List<StepTree> subList, String period, StepQueryState stepQueryState, BatchStepByStepResult upload) {
        CompleteMsg execute = new CompleteMsg();
        HashMap<BatchNoOperate, List<BatchNoOperate>> noOperateUnits = new HashMap<BatchNoOperate, List<BatchNoOperate>>();
        HashSet<String> operateUnits = new HashSet<String>();
        HashMap<String, LinkedHashSet<String>> unitAndForms = new HashMap<String, LinkedHashSet<String>>();
        HashMap<String, LinkedHashSet<String>> unitAndGroups = new HashMap<String, LinkedHashSet<String>>();
        ArrayList<BatchNoOperate> childrenOperate = null;
        EntityViewDefine dwEntityView = this.dimensionUtil.getDwEntityView(stepByOptParam.getFormSchemeKey());
        FormSchemeDefine formSchemeDefine = this.stepUtil.getFormSchemeDefine(stepByOptParam.getFormSchemeKey());
        String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
        Map<String, Object> otherDim = this.getOtherDim(stepByOptParam.getStepUnit(), mainDimName);
        for (StepTree stepTree : subList) {
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
                Map<IEntityRow, Map<String, ActionStateBean>> queryState = this.getParentState(stepQueryState, parentData, selectResourceMap);
                childrenOperate = new ArrayList<BatchNoOperate>();
                boolean canRturn = true;
                if (queryState != null && queryState.size() > 0) {
                    for (Map.Entry<IEntityRow, Map<String, ActionStateBean>> stateMap : queryState.entrySet()) {
                        IEntityRow entityRow = stateMap.getKey();
                        Map<String, ActionStateBean> actionState = stateMap.getValue();
                        BatchNoOperate batchNoOperate = new BatchNoOperate();
                        batchNoOperate.setId(entityRow.getEntityKeyData());
                        batchNoOperate.setCode(entityRow.getCode());
                        batchNoOperate.setName(entityRow.getTitle());
                        String resourceId = null;
                        boolean fliterConditions = this.isFliterConditions(stepByOptParam.getFormSchemeKey(), period, entityRow.getEntityKeyData(), parentData, resourceId, stepByOptParam.getStepFormKeyMap(), stepByOptParam.getStepGroupKeyMap());
                        if (!fliterConditions) continue;
                        boolean hasUnitAuditOperation = this.hasUnitAuditOperation(dwEntityView.getEntityId(), entityRow.getEntityKeyData(), formSchemeDefine.getDateTime(), period);
                        if (hasUnitAuditOperation && canRturn) {
                            DimensionValueSet dimensionValueSet = new DimensionValueSet();
                            dimensionValueSet.setValue("DATATIME", (Object)period);
                            dimensionValueSet.setValue(mainDimName, (Object)entityRow.getEntityKeyData());
                            if (otherDim != null && otherDim.size() > 0) {
                                for (Map.Entry<String, Object> entry : otherDim.entrySet()) {
                                    dimensionValueSet.setValue(entry.getKey(), entry.getValue());
                                }
                            }
                            for (Map.Entry<String, Object> entry : actionState.entrySet()) {
                                ActionStateBean value = (ActionStateBean)entry.getValue();
                                this.execute(stepByOptParam.getFormSchemeKey(), dimensionValueSet, period, selectResourceMap, stepByOptParam.getActionId(), value.getTaskKey(), stepByOptParam.getContent());
                            }
                            operateUnits.add(entityRow.getEntityKeyData());
                            unitAndForms.put(entityRow.getEntityKeyData(), selectResourceMap);
                            unitAndGroups.put(entityRow.getEntityKeyData(), selectResourceMap);
                            continue;
                        }
                        canRturn = false;
                        childrenOperate.add(batchNoOperate);
                        noOperateUnits.put(parentOperate, childrenOperate);
                    }
                    if (noOperateUnits != null && noOperateUnits.size() != 0) {
                        if (upload.getNoOperateUnitMap() != null && childrenOperate.size() > 0) {
                            upload.getNoOperateUnitMap().put(parentOperate, childrenOperate);
                        } else {
                            upload.setNoOperateUnitMap(noOperateUnits);
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
                    } else {
                        upload.setOperateUnitAndGroups(unitAndGroups);
                    }
                }
            }
            if (noOperateUnits != null && noOperateUnits.size() != 0) continue;
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            dimensionValueSet.setValue(mainDimName, (Object)stepTree.getId());
            if (otherDim != null && otherDim.size() > 0) {
                for (Map.Entry<String, Object> other : otherDim.entrySet()) {
                    dimensionValueSet.setValue(other.getKey(), other.getValue());
                }
            }
            execute = this.execute(stepByOptParam.getFormSchemeKey(), dimensionValueSet, period, selectResourceMap, stepByOptParam.getActionId(), stepByOptParam.getTaskId(), stepByOptParam.getContent());
        }
        upload.setCompleteMsg(execute);
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
        if (selectResourceMap != null && selectResourceMap.size() > 0) {
            for (String key : selectResourceMap) {
                executeParam.setFormKey(key);
                executeParam.setGroupKey(key);
                executeTask = this.dataFlowService.executeTask(executeParam);
            }
        } else {
            executeParam.setFormKey(null);
            executeParam.setGroupKey(null);
            executeTask = this.dataFlowService.executeTask(executeParam);
        }
        return executeTask;
    }
}

