/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.common;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.ForceControlInfo;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ForceControlContext {
    private IWorkFlowDimensionBuilder dimensionBuilder;
    private String taskKey;

    public ForceControlContext(IWorkFlowDimensionBuilder dimensionBuilder, String taskKey) {
        this.dimensionBuilder = dimensionBuilder;
        this.taskKey = taskKey;
    }

    public Map<String, ForceControlInfo> batchInitForceControlInfo(BatchStepByStepParam batchStepByStepParam, WorkFlowType workFlowType) {
        HashMap<String, ForceControlInfo> forceControlInfoMap = new HashMap<String, ForceControlInfo>();
        List<DimensionValueSet> stepUnit = batchStepByStepParam.getStepUnit();
        Map<String, LinkedHashSet<String>> formKeyMap = batchStepByStepParam.getFormKeys();
        Map<String, LinkedHashSet<String>> groupKeyMap = batchStepByStepParam.getGroupKeys();
        for (DimensionValueSet dimensionValueSet : stepUnit) {
            ForceControlInfo forceControlInfo;
            DimensionCombinationImpl dimensionValues = new DimensionCombinationImpl(dimensionValueSet);
            String unitKey = dimensionValues.getDWDimensionValue().getValue().toString();
            if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                LinkedHashSet<String> formKeys = formKeyMap.get(unitKey);
                for (String formKey : formKeys) {
                    forceControlInfo = new ForceControlInfo();
                    forceControlInfo.setDimensionCombination((DimensionCombination)dimensionValues);
                    forceControlInfo.setFormKey(formKey);
                    this.initCorporateValue(forceControlInfo, dimensionValueSet, this.taskKey);
                    forceControlInfoMap.put(unitKey + ";" + formKey, forceControlInfo);
                }
                continue;
            }
            if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                LinkedHashSet<String> groupKeys = groupKeyMap.get(unitKey);
                for (String groupKey : groupKeys) {
                    forceControlInfo = new ForceControlInfo();
                    forceControlInfo.setDimensionCombination((DimensionCombination)dimensionValues);
                    forceControlInfo.setGroupKey(groupKey);
                    this.initCorporateValue(forceControlInfo, dimensionValueSet, this.taskKey);
                    forceControlInfoMap.put(unitKey + ";" + groupKey, forceControlInfo);
                }
                continue;
            }
            ForceControlInfo forceControlInfo2 = new ForceControlInfo();
            forceControlInfo2.setDimensionCombination((DimensionCombination)dimensionValues);
            this.initCorporateValue(forceControlInfo2, dimensionValueSet, this.taskKey);
            forceControlInfoMap.put(unitKey, forceControlInfo2);
        }
        return forceControlInfoMap;
    }

    private void initCorporateValue(ForceControlInfo forceControlInfo, DimensionValueSet dimensionValueSet, String taskKey) {
        IRunTimeViewController runTimeViewController;
        TaskDefine task;
        IWorkFlowDimensionBuilder dimensionBuilder = (IWorkFlowDimensionBuilder)SpringBeanUtils.getBean(IWorkFlowDimensionBuilder.class);
        String corporateCode = dimensionBuilder.getCorporateEntityCode(task = (runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class)).getTask(taskKey));
        Object value = dimensionValueSet.getValue(corporateCode);
        if (value != null) {
            forceControlInfo.setCurrentCorporateValue(value.toString());
        }
    }

    public void initForceControlInfo(ForceControlInfo forceControlInfo, StepByOptParam stepByOptParam, CorporateData corporateData) {
        DimensionValueSet dimensionValue = stepByOptParam.getDimensionValue();
        String formKey = stepByOptParam.getFormKey();
        String groupKey = stepByOptParam.getGroupKey();
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValue);
        DimensionCombination dimensionCombination = this.dimensionBuilder.buildDimensionCombination(this.taskKey, dimensionSet, corporateData.getKey());
        forceControlInfo.setDimensionCombination(dimensionCombination);
        forceControlInfo.setFormKey(formKey);
        forceControlInfo.setGroupKey(groupKey);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        this.initCorporateValue(forceControlInfo, dimensionValueSet, this.taskKey);
    }

    public List<ForceControlInfo> initForceControlInfo(Map<String, ForceControlInfo> forceControlInfoMap, BatchStepByStepParam stepByOptParam, WorkFlowType workFlowType, String dwDimensionName, CorporateData corporateData) {
        ArrayList<ForceControlInfo> forceControlInfoList = new ArrayList<ForceControlInfo>();
        List<DimensionValueSet> stepUnit = stepByOptParam.getStepUnit();
        List unitKeys = stepUnit.stream().map(e -> e.getValue(dwDimensionName).toString()).collect(Collectors.toList());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)stepByOptParam.getPeriod());
        dimensionValueSet.setValue(dwDimensionName, unitKeys);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.dimensionBuilder.buildDimensionCollection(this.taskKey, dimensionSet, corporateData.getKey());
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        Map<String, LinkedHashSet<String>> formKeyMap = stepByOptParam.getFormKeys();
        Map<String, LinkedHashSet<String>> groupKeyMap = stepByOptParam.getGroupKeys();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            ForceControlInfo forceControlInfo;
            FixedDimensionValue dwDimensionValue = dimensionCombination.getDWDimensionValue();
            String unitKey = dwDimensionValue.getValue().toString();
            if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                LinkedHashSet<String> formKeys = formKeyMap.get(unitKey);
                for (String formKey : formKeys) {
                    forceControlInfo = forceControlInfoMap.get(unitKey + ";" + formKey);
                    if (forceControlInfo == null) {
                        forceControlInfo = new ForceControlInfo();
                    }
                    forceControlInfo.setDimensionCombination(dimensionCombination);
                    forceControlInfoList.add(forceControlInfo);
                }
                continue;
            }
            if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                LinkedHashSet<String> groupKeys = groupKeyMap.get(unitKey);
                for (String groupKey : groupKeys) {
                    forceControlInfo = forceControlInfoMap.get(unitKey + ";" + groupKey);
                    if (forceControlInfo == null) {
                        forceControlInfo = new ForceControlInfo();
                    }
                    forceControlInfo.setDimensionCombination(dimensionCombination);
                    forceControlInfoList.add(forceControlInfo);
                }
                continue;
            }
            ForceControlInfo forceControlInfo2 = forceControlInfoMap.get(unitKey);
            if (forceControlInfo2 == null) {
                forceControlInfo2 = new ForceControlInfo();
            }
            forceControlInfo2.setDimensionCombination(dimensionCombination);
            forceControlInfoList.add(forceControlInfo2);
        }
        return forceControlInfoList;
    }
}

