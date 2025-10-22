/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.common;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.BatchForceControlResult;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.ForceControlInfo;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.SingleForceControlResult;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.util.RejectStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CalculateStateData {
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    private CommonUtil commonUtil = (CommonUtil)SpringBeanUtils.getBean(CommonUtil.class);
    private String dwDimensionName;
    private String currentCorporate;
    private String corporateCode;
    private FormSchemeDefine formSchemeDefine;

    public CalculateStateData(String formSchemeKey, String dwDimensionName) {
        this.batchQueryUploadStateService = (IBatchQueryUploadStateService)SpringBeanUtils.getBean(IBatchQueryUploadStateService.class);
        IRunTimeViewController viewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        this.formSchemeDefine = viewController.getFormScheme(formSchemeKey);
        TaskDefine task = viewController.getTask(this.formSchemeDefine.getTaskKey());
        this.dwDimensionName = dwDimensionName;
        IWorkFlowDimensionBuilder dimensionBuilder = (IWorkFlowDimensionBuilder)SpringBeanUtils.getBean(IWorkFlowDimensionBuilder.class);
        this.currentCorporate = dimensionBuilder.getContextMainDimId(task.getDw());
        this.corporateCode = dimensionBuilder.getCorporateEntityCode(task);
    }

    public void updateState(ForceControlInfo forceControlInfo, CorporateData corporateData, SingleForceControlResult singleForceControlResult) {
        boolean canReject = true;
        IEntityRow parentEntityRow = forceControlInfo.getParentEntityRow();
        if (this.currentCorporate.equals(corporateData.getKey())) {
            if (parentEntityRow != null) {
                UploadStateNew uploadStateNew = this.batchQueryUploadStateService.queryUploadState(this.formSchemeDefine, forceControlInfo.getParentDimensionValue(), forceControlInfo.getFormKey(), forceControlInfo.getGroupKey(), corporateData.getKey());
                canReject = this.canReject(uploadStateNew);
            }
        } else {
            String currentCorporateValue = forceControlInfo.getCurrentCorporateValue();
            DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
            FixedDimensionValue fixedDimensionValue = dimensionCombination.getFixedDimensionValue(this.corporateCode);
            if (currentCorporateValue != null && fixedDimensionValue != null && currentCorporateValue.equals(fixedDimensionValue.getValue().toString()) && parentEntityRow != null) {
                UploadStateNew uploadStateNew = this.batchQueryUploadStateService.queryUploadState(this.formSchemeDefine, forceControlInfo.getParentDimensionValue(), forceControlInfo.getFormKey(), forceControlInfo.getGroupKey(), corporateData.getKey());
                canReject = this.canReject(uploadStateNew);
            }
        }
        if (canReject) {
            forceControlInfo.setCurrent_state(RejectStateEnum.PASS.getValue());
        } else {
            List<StepByStepCheckItem> tips;
            forceControlInfo.setCurrent_state(RejectStateEnum.FAIL.getValue());
            StepByStepCheckItem stepByStepCheckItem = null;
            StringBuilder sb = new StringBuilder();
            if (parentEntityRow != null) {
                stepByStepCheckItem = new StepByStepCheckItem();
                stepByStepCheckItem.setUnitId(parentEntityRow.getEntityKeyData());
                stepByStepCheckItem.setUnitCode(parentEntityRow.getCode());
                sb.append(parentEntityRow.getTitle());
                sb.append("(").append(corporateData.getTitle()).append(")");
                stepByStepCheckItem.setUnitTitle(sb.toString());
            }
            if ((tips = singleForceControlResult.getTips()) == null) {
                tips = new LinkedList<StepByStepCheckItem>();
            }
            if (stepByStepCheckItem != null) {
                tips.add(stepByStepCheckItem);
            }
            singleForceControlResult.setTips(tips);
        }
    }

    public void batchUpdateState(List<ForceControlInfo> forceControlInfos, CorporateData corporateData, WorkFlowType workFlowType, BatchForceControlResult batchForceControlResult) {
        ForceControlInfo maxForceControl = forceControlInfos.stream().max(Comparator.comparingInt(ForceControlInfo::getLevel)).get();
        ForceControlInfo minForceControl = forceControlInfos.stream().min(Comparator.comparingInt(ForceControlInfo::getLevel)).get();
        int maxLevel = maxForceControl.getLevel();
        int minLevel = minForceControl.getLevel();
        FixedDimensionValue periodDimensionValue = minForceControl.getDimensionCombination().getPeriodDimensionValue();
        String period = periodDimensionValue.getValue().toString();
        ArrayList<ForceControlInfo> rootData = new ArrayList<ForceControlInfo>();
        Map<String, UploadStateNew> parentUploadStateNewMap = this.batchQueryParentUploadState(forceControlInfos, corporateData, workFlowType, period, rootData);
        if (this.currentCorporate.equals(corporateData.getKey())) {
            this.currentCorporateStateUpdate(forceControlInfos, rootData, parentUploadStateNewMap, corporateData, workFlowType, batchForceControlResult, maxLevel, minLevel);
        } else {
            this.corporateStateUpdate(forceControlInfos, parentUploadStateNewMap, corporateData, workFlowType, batchForceControlResult, maxLevel, minLevel);
        }
    }

    private void currentCorporateStateUpdate(List<ForceControlInfo> forceControlInfos, List<ForceControlInfo> rootData, Map<String, UploadStateNew> uploadStateNewMap, CorporateData corporateData, WorkFlowType workFlowType, BatchForceControlResult batchForceControlResult, int maxLevel, int minLevel) {
        int maxLevelTemp = maxLevel;
        if (rootData != null && rootData.size() > 0) {
            this.updateState(rootData, uploadStateNewMap, workFlowType, corporateData, batchForceControlResult, true);
            maxLevelTemp = maxLevel - 1;
        }
        int i = maxLevelTemp;
        while (i >= minLevel) {
            int finalI = i--;
            List<ForceControlInfo> datas = forceControlInfos.stream().filter(e -> e.getLevel() == finalI).collect(Collectors.toList());
            List<ForceControlInfo> infos = this.fliterSelectUnit(datas, forceControlInfos, workFlowType, corporateData, batchForceControlResult, uploadStateNewMap);
            this.updateState(infos, uploadStateNewMap, workFlowType, corporateData, batchForceControlResult, false);
        }
    }

    private void corporateStateUpdate(List<ForceControlInfo> forceControlInfos, Map<String, UploadStateNew> uploadStateNewMap, CorporateData corporateData, WorkFlowType workFlowType, BatchForceControlResult batchForceControlResult, int maxLevel, int minLevel) {
        int i = maxLevel;
        while (i >= minLevel) {
            int finalI = i--;
            List<ForceControlInfo> datas = forceControlInfos.stream().filter(e -> e.getLevel() == finalI).collect(Collectors.toList());
            List<ForceControlInfo> infos = this.fliterSelectUnit(datas, forceControlInfos, workFlowType, corporateData, batchForceControlResult);
            this.updateState(infos, uploadStateNewMap, workFlowType, corporateData, batchForceControlResult, false);
        }
    }

    private List<ForceControlInfo> fliterSelectUnit(List<ForceControlInfo> dataLevel, List<ForceControlInfo> allForceControlInfos, WorkFlowType workFlowType, CorporateData corporateData, BatchForceControlResult batchForceControlResult, Map<String, UploadStateNew> uploadStateNewMap) {
        ArrayList<ForceControlInfo> unSelectUnits = new ArrayList<ForceControlInfo>();
        HashMap<String, ForceControlInfo> selectUnitMap = new HashMap<String, ForceControlInfo>();
        for (ForceControlInfo forceControlInfo : allForceControlInfos) {
            DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
            selectUnitMap.put(dimensionCombination.getDWDimensionValue().getValue().toString(), forceControlInfo);
        }
        for (ForceControlInfo forceControlInfo : dataLevel) {
            String parentKey = forceControlInfo.getParentKey();
            ForceControlInfo parentIn = (ForceControlInfo)selectUnitMap.get(parentKey);
            if (parentIn == null) {
                unSelectUnits.add(forceControlInfo);
                continue;
            }
            DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
            String unitKey = dimensionCombination.getDWDimensionValue().getValue().toString();
            int currentState = parentIn.getCurrent_state();
            boolean canReject = true;
            if (RejectStateEnum.FAIL.getValue() == currentState || RejectStateEnum.INIT.getValue() == currentState) {
                canReject = false;
            }
            this.buildResult(unitKey, forceControlInfo, canReject, workFlowType, corporateData, batchForceControlResult, false);
        }
        return unSelectUnits;
    }

    private List<ForceControlInfo> fliterSelectUnit(List<ForceControlInfo> dataLevel, List<ForceControlInfo> allForceControlInfos, WorkFlowType workFlowType, CorporateData corporateData, BatchForceControlResult batchForceControlResult) {
        ArrayList<ForceControlInfo> unSelectUnits = new ArrayList<ForceControlInfo>();
        HashMap<String, ForceControlInfo> selectUnitMap = new HashMap<String, ForceControlInfo>();
        for (ForceControlInfo forceControlInfo : allForceControlInfos) {
            DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
            String unitKey = dimensionCombination.getDWDimensionValue().getValue().toString();
            if (this.corporateCode != null) {
                FixedDimensionValue mdGcorgtype = dimensionCombination.getFixedDimensionValue(this.corporateCode);
                if (mdGcorgtype != null) {
                    String corporateValue = mdGcorgtype.getValue().toString();
                    selectUnitMap.put(unitKey + ";" + corporateValue, forceControlInfo);
                    continue;
                }
                selectUnitMap.put(unitKey, forceControlInfo);
                continue;
            }
            selectUnitMap.put(unitKey, forceControlInfo);
        }
        for (ForceControlInfo forceControlInfo : dataLevel) {
            ForceControlInfo parentIn = null;
            String parentKey = forceControlInfo.getParentKey();
            String parentKeyWithCorporateValue = forceControlInfo.getParentKeyWithCorporateValue();
            parentIn = parentKey != null ? (ForceControlInfo)selectUnitMap.get(parentKeyWithCorporateValue) : (ForceControlInfo)selectUnitMap.get(parentKey);
            if (parentIn == null) {
                unSelectUnits.add(forceControlInfo);
                continue;
            }
            DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
            String unitKey = dimensionCombination.getDWDimensionValue().getValue().toString();
            int currentState = parentIn.getCurrent_state();
            boolean canReject = true;
            if (RejectStateEnum.FAIL.getValue() == currentState || RejectStateEnum.INIT.getValue() == currentState) {
                canReject = false;
            }
            this.buildResult(unitKey, forceControlInfo, canReject, workFlowType, corporateData, batchForceControlResult, false);
        }
        return unSelectUnits;
    }

    private void updateState(List<ForceControlInfo> levelDatas, Map<String, UploadStateNew> uploadStateNewMap, WorkFlowType workFlowType, CorporateData corporateData, BatchForceControlResult forceControlResult, boolean rootData) {
        for (ForceControlInfo forceControl : levelDatas) {
            Object value;
            FixedDimensionValue fixedDimensionValue;
            String currentCorporateValue;
            DimensionCombination dimensionCombination = forceControl.getDimensionCombination();
            FixedDimensionValue dwDimensionValue = dimensionCombination.getDWDimensionValue();
            String unitKey = dwDimensionValue.getValue().toString();
            String parentKey = forceControl.getParentKey();
            String uniqueKey = this.uniqueKey(parentKey, forceControl.getFormKey(), forceControl.getGroupKey(), workFlowType);
            UploadStateNew uploadStateNew = uploadStateNewMap.get(uniqueKey);
            boolean canReject = true;
            if (!rootData) {
                canReject = this.canReject(uploadStateNew);
            }
            if ((currentCorporateValue = forceControl.getCurrentCorporateValue()) != null && (fixedDimensionValue = dimensionCombination.getFixedDimensionValue(this.corporateCode)) != null && !currentCorporateValue.equals((value = fixedDimensionValue.getValue()).toString())) {
                canReject = true;
            }
            this.buildResult(unitKey, forceControl, canReject, workFlowType, corporateData, forceControlResult, rootData);
        }
    }

    private void buildResult(String unitKey, ForceControlInfo forceControl, boolean canReject, WorkFlowType workFlowType, CorporateData corporateData, BatchForceControlResult forceControlResult, boolean rootData) {
        if (RejectStateEnum.FAIL.getValue() == forceControl.getCurrent_state() && canReject && !rootData) {
            return;
        }
        if (canReject) {
            forceControl.setCurrent_state(RejectStateEnum.PASS.getValue());
            Set<String> canRejectUnitKeys = forceControlResult.getCanRejectUnitKeys();
            if (canRejectUnitKeys == null) {
                canRejectUnitKeys = new HashSet<String>();
            }
            canRejectUnitKeys.add(unitKey);
            forceControlResult.setCanRejectUnitKeys(canRejectUnitKeys);
            if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                LinkedHashSet<String> formKeys;
                Map<String, LinkedHashSet<String>> canRejectUnitToForms = forceControlResult.getCanRejectUnitToForms();
                if (canRejectUnitToForms == null) {
                    canRejectUnitToForms = new HashMap<String, LinkedHashSet<String>>();
                }
                if ((formKeys = canRejectUnitToForms.get(unitKey)) == null) {
                    formKeys = new LinkedHashSet();
                }
                formKeys.add(forceControl.getFormKey());
                canRejectUnitToForms.put(unitKey, formKeys);
                forceControlResult.setCanRejectUnitToForms(canRejectUnitToForms);
            }
            if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                LinkedHashSet<String> groupKeys;
                Map<String, LinkedHashSet<String>> canRejectUnitToGroups = forceControlResult.getCanRejectUnitToGroups();
                if (canRejectUnitToGroups == null) {
                    canRejectUnitToGroups = new HashMap<String, LinkedHashSet<String>>();
                }
                if ((groupKeys = canRejectUnitToGroups.get(unitKey)) == null) {
                    groupKeys = new LinkedHashSet();
                }
                groupKeys.add(forceControl.getGroupKey());
                canRejectUnitToGroups.put(unitKey, groupKeys);
                forceControlResult.setCanRejectUnitToGroups(canRejectUnitToGroups);
            }
        } else {
            Map<String, LinkedHashSet<String>> canRejectUnitToGroups;
            forceControl.setCurrent_state(RejectStateEnum.FAIL.getValue());
            Map<BatchNoOperate, List<BatchNoOperate>> noOperateUnitMap = forceControlResult.getNoOperateUnitMap();
            if (noOperateUnitMap == null) {
                noOperateUnitMap = new HashMap<BatchNoOperate, List<BatchNoOperate>>();
            }
            BatchNoOperate currentUnit = new BatchNoOperate();
            currentUnit.setCode(forceControl.getUnitCode());
            currentUnit.setId(unitKey);
            currentUnit.setName(forceControl.getUnitName());
            List<BatchNoOperate> batchNoOperates = noOperateUnitMap.get(currentUnit);
            if (batchNoOperates == null) {
                batchNoOperates = new LinkedList<BatchNoOperate>();
            }
            BatchNoOperate parentUnit = new BatchNoOperate();
            IEntityRow parentEntityRow = forceControl.getParentEntityRow();
            parentUnit.setId(parentEntityRow.getEntityKeyData());
            parentUnit.setCode(parentEntityRow.getCode());
            parentUnit.setName(parentEntityRow.getTitle() + "(" + corporateData.getTitle() + ")");
            batchNoOperates.add(parentUnit);
            noOperateUnitMap.put(currentUnit, batchNoOperates);
            forceControlResult.setNoOperateUnitMap(noOperateUnitMap);
            if (WorkFlowType.FORM.equals((Object)workFlowType) || WorkFlowType.GROUP.equals((Object)workFlowType)) {
                BatchNoOperate resurceData;
                List<BatchNoOperate> batchNoOperateList;
                Map<BatchNoOperate, List<BatchNoOperate>> batchNoOperateListMap;
                String formOrGroupKey = WorkFlowType.FORM.equals((Object)workFlowType) ? forceControl.getFormKey() : forceControl.getGroupKey();
                Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap = forceControlResult.getNoOperateGroupOrFormMap();
                if (noOperateGroupOrFormMap == null) {
                    noOperateGroupOrFormMap = new HashMap<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>>();
                }
                if ((batchNoOperateListMap = noOperateGroupOrFormMap.get(currentUnit)) == null) {
                    batchNoOperateListMap = new HashMap<BatchNoOperate, List<BatchNoOperate>>();
                }
                if ((batchNoOperateList = batchNoOperateListMap.get(resurceData = this.commonUtil.getFormByKey(this.formSchemeDefine.getKey(), formOrGroupKey))) == null) {
                    batchNoOperateList = new LinkedList<BatchNoOperate>();
                }
                batchNoOperateList.add(parentUnit);
                batchNoOperateListMap.put(resurceData, batchNoOperateList);
                noOperateGroupOrFormMap.put(currentUnit, batchNoOperateListMap);
                forceControlResult.setNoOperateGroupOrFormMap(noOperateGroupOrFormMap);
            }
            if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
                if (forceControlResult.getCanRejectUnitKeys() != null) {
                    forceControlResult.getCanRejectUnitKeys().remove(unitKey);
                }
            } else if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                Map<String, LinkedHashSet<String>> canRejectUnitToForms = forceControlResult.getCanRejectUnitToForms();
                if (canRejectUnitToForms != null) {
                    LinkedHashSet<String> formKeys = canRejectUnitToForms.get(unitKey);
                    if (formKeys != null) {
                        formKeys.remove(forceControl.getFormKey());
                    }
                    if ((formKeys == null || formKeys.size() == 0) && forceControlResult.getCanRejectUnitKeys() != null && forceControlResult.getCanRejectUnitKeys().size() > 0) {
                        forceControlResult.getCanRejectUnitKeys().remove(unitKey);
                    }
                }
            } else if (WorkFlowType.GROUP.equals((Object)workFlowType) && (canRejectUnitToGroups = forceControlResult.getCanRejectUnitToGroups()) != null) {
                LinkedHashSet<String> groupKeys = canRejectUnitToGroups.get(unitKey);
                if (groupKeys != null) {
                    groupKeys.remove(forceControl.getGroupKey());
                }
                if ((groupKeys == null || groupKeys.size() == 0) && forceControlResult.getCanRejectUnitKeys() != null && forceControlResult.getCanRejectUnitKeys().size() > 0) {
                    forceControlResult.getCanRejectUnitKeys().remove(unitKey);
                }
            }
        }
    }

    private Map<String, UploadStateNew> batchQueryParentUploadState(List<ForceControlInfo> forceControlInfos, CorporateData corporateData, WorkFlowType workFlowType, String period, List<ForceControlInfo> rootData) {
        HashMap<String, UploadStateNew> uploadStateNewMap = new HashMap<String, UploadStateNew>();
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList<String> groupKeys = new ArrayList<String>();
        ArrayList<String> parentKeys = new ArrayList<String>();
        for (ForceControlInfo forceControlInfo : forceControlInfos) {
            formKeys.add(forceControlInfo.getFormKey());
            groupKeys.add(forceControlInfo.getGroupKey());
            String parentKey = forceControlInfo.getParentKey();
            if ("-".equals(parentKey)) {
                rootData.add(forceControlInfo);
                continue;
            }
            if (parentKey == null) continue;
            parentKeys.add(parentKey);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(this.dwDimensionName, parentKeys);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        IWorkFlowDimensionBuilder workFlowDimensionBuilder = (IWorkFlowDimensionBuilder)SpringBeanUtils.getBean(IWorkFlowDimensionBuilder.class);
        DimensionCollection dimensionValueSets = workFlowDimensionBuilder.buildDimensionCollection(this.formSchemeDefine.getTaskKey(), dimensionSet, corporateData.getKey());
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dimensionValueSetList.add(dimensionCombination.toDimensionValueSet());
        }
        DimensionValueSet dimensionValue = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSetList);
        List<UploadStateNew> uploadStateNews = this.batchQueryUploadStateService.queryUploadState(this.formSchemeDefine, dimensionValue, formKeys, groupKeys, corporateData.getKey());
        for (UploadStateNew uploadStateNew : uploadStateNews) {
            DimensionValueSet entities = uploadStateNew.getEntities();
            String unitKey = entities.getValue(this.dwDimensionName).toString();
            String uniqueKey = this.uniqueKey(unitKey, uploadStateNew.getFormId(), uploadStateNew.getFormId(), workFlowType);
            uploadStateNewMap.put(uniqueKey, uploadStateNew);
        }
        return uploadStateNewMap;
    }

    private Map<String, UploadStateNew> batchQueryUnitUploadState(List<ForceControlInfo> maxDatas, CorporateData corporateData, WorkFlowType workFlowType, String period) {
        HashMap<String, UploadStateNew> uploadStateNewMap = new HashMap<String, UploadStateNew>();
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList<String> groupKeys = new ArrayList<String>();
        ArrayList<String> unitKeys = new ArrayList<String>();
        for (ForceControlInfo forceControlInfo : maxDatas) {
            formKeys.add(forceControlInfo.getFormKey());
            groupKeys.add(forceControlInfo.getGroupKey());
            DimensionCombination dimensionCombination = forceControlInfo.getDimensionCombination();
            String unitKey = dimensionCombination.getDWDimensionValue().getValue().toString();
            unitKeys.add(unitKey);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(this.dwDimensionName, unitKeys);
        dimensionValueSet.setValue("DATATIME", (Object)period);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        IWorkFlowDimensionBuilder workFlowDimensionBuilder = (IWorkFlowDimensionBuilder)SpringBeanUtils.getBean(IWorkFlowDimensionBuilder.class);
        DimensionCollection dimensionValueSets = workFlowDimensionBuilder.buildDimensionCollection(this.formSchemeDefine.getTaskKey(), dimensionSet, corporateData.getKey());
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dimensionValueSetList.add(dimensionCombination.toDimensionValueSet());
        }
        DimensionValueSet dimensionValue = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSetList);
        List<UploadStateNew> uploadStateNews = this.batchQueryUploadStateService.queryUploadState(this.formSchemeDefine, dimensionValue, formKeys, groupKeys, corporateData.getKey());
        for (UploadStateNew uploadStateNew : uploadStateNews) {
            DimensionValueSet entities = uploadStateNew.getEntities();
            String unitKey = entities.getValue(this.dwDimensionName).toString();
            String uniqueKey = this.uniqueKey(unitKey, uploadStateNew.getFormId(), uploadStateNew.getFormId(), workFlowType);
            uploadStateNewMap.put(uniqueKey, uploadStateNew);
        }
        return uploadStateNewMap;
    }

    private String uniqueKey(String unitKey, String formKey, String groupKey, WorkFlowType workFlowType) {
        if (WorkFlowType.FORM.equals((Object)workFlowType)) {
            return unitKey + ";" + formKey;
        }
        if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            return unitKey + ";" + groupKey;
        }
        return unitKey;
    }

    private boolean canReject(UploadStateNew uploadStateNew) {
        if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
            ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
            String code = actionStateBean.getCode();
            return UploadState.REJECTED.toString().equals(code) || UploadState.SUBMITED.toString().equals(code) || UploadState.RETURNED.toString().equals(code) || UploadState.ORIGINAL_SUBMIT.toString().equals(code) || UploadState.ORIGINAL_UPLOAD.toString().equals(code);
        }
        return true;
    }
}

