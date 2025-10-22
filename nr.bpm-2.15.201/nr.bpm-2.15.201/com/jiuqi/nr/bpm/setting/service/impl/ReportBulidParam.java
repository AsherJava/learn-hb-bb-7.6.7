/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.service.IBulidParam;
import com.jiuqi.nr.bpm.setting.service.impl.UnitToFormOrGroupMapUtils;
import com.jiuqi.nr.bpm.setting.utils.BpmQueryEntityData;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportBulidParam
implements IBulidParam {
    private static final Logger logger = LoggerFactory.getLogger(ReportBulidParam.class);
    @Autowired
    private SettingUtil untilsMethod;
    @Resource
    private UnitToFormOrGroupMapUtils unitToFormOrGroupMapUtils;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private BusinessGenerator businessGenerator;

    @Override
    public int getType() {
        return WorkFlowType.FORM.getValue();
    }

    @Override
    public Map<BusinessKey, String> buildBusinessKeyMap(StateChangeObj stateChange, WorkflowStatus flowType, boolean start) {
        Map<BusinessKey, String> startParam = new HashMap<BusinessKey, String>();
        try {
            Set<String> unitIds = this.getUnitIds(stateChange);
            startParam = this.buildStartParam(stateChange, unitIds, flowType, start);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return startParam;
    }

    private Set<String> getUnitIds(StateChangeObj stateChange) {
        Set<Object> unitIds = new HashSet();
        unitIds = stateChange.isSelectAll() ? this.getEntityData(stateChange) : stateChange.getDataObj();
        return unitIds;
    }

    private Map<BusinessKey, String> buildStartParam(StateChangeObj stateChange, Set<String> unitIds, WorkflowStatus flowType, boolean start) {
        HashMap<BusinessKey, String> startParam = new HashMap<BusinessKey, String>();
        Map<String, List<String>> reportIdsMap = this.getReportIds(stateChange, unitIds, start);
        FormSchemeDefine formScheme = this.untilsMethod.getFormScheme(stateChange.getFormSchemeId());
        String defaultFormId = this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(stateChange.getFormSchemeId(), unitIds, stateChange.getPeriod(), stateChange.getAdjust(), false);
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        Map<String, Set<String>> alreadyStartFlow = this.alreadyStartFlow(stateChange, unitIds, reportIdsMap);
        StringBuffer sb = null;
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            sb = new StringBuffer();
            DimensionSet dimensionSet = dimensionValue.getDimensionSet();
            sb.append("'");
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String dimension = dimensionSet.get(i);
                sb.append(dimensionValue.getValue(dimension).toString()).append(";");
            }
            sb.append("'");
            String unitkey = dimensionValue.getValue(this.dimensionUtil.getDwMainDimName(stateChange.getFormSchemeId())).toString();
            List<String> list = reportIdsMap.get(unitkey);
            for (String reportId : list) {
                if (alreadyStartFlow.containsKey(sb.toString()) && alreadyStartFlow.get(sb.toString()).contains(reportId)) continue;
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(stateChange.getFormSchemeId(), dimensionValue, reportId, reportId);
                startParam.put(businessKey, unitkey);
            }
        }
        return startParam;
    }

    private Map<String, Set<String>> alreadyStartFlow(StateChangeObj stateChange, Set<String> unitIds, Map<String, List<String>> reportIdsMap) {
        HashSet<String> reportIds = new HashSet<String>();
        for (Map.Entry<String, List<String>> map : reportIdsMap.entrySet()) {
            reportIds.addAll((Collection)map.getValue());
        }
        Map<String, Set<String>> alreadyStartFlow = this.untilsMethod.queryAlreadyStart(stateChange.getFormSchemeId(), unitIds, reportIds, stateChange.getPeriod(), stateChange.getAdjust());
        return alreadyStartFlow;
    }

    private Map<String, List<String>> getReportIds(StateChangeObj stateChange, Set<String> unitIds, boolean start) {
        Map<Object, Object> reports = new HashMap();
        WorkFlowType startType = this.untilsMethod.queryStartType(stateChange.getFormSchemeId());
        UnitToFormOrGroupMapUtils.BuildParam buildParam = new UnitToFormOrGroupMapUtils.BuildParam();
        buildParam.setFormSchemeKey(stateChange.getFormSchemeId());
        buildParam.setPeriod(stateChange.getPeriod());
        buildParam.setUnitIds(unitIds);
        buildParam.setFormIds(stateChange.getReportList());
        buildParam.setCheckAllForm(stateChange.isReportAll() || stateChange.isSelectAll());
        buildParam.setAdjustId(stateChange.getAdjust());
        if (!start) {
            buildParam.setMergeIds(new HashSet<String>(Collections.singletonList("11111111-1111-1111-1111-111111111111")));
        }
        if (WorkFlowType.FORM.equals((Object)startType)) {
            reports = this.unitToFormOrGroupMapUtils.mapToForms(buildParam);
        } else if (WorkFlowType.GROUP.equals((Object)startType)) {
            try {
                reports = this.unitToFormOrGroupMapUtils.mapToGroups(buildParam);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (WorkFlowType.ENTITY.equals((Object)startType)) {
            for (String unit : unitIds) {
                reports.put(unit, Collections.singletonList(this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId())));
            }
        }
        return reports;
    }

    private Set<String> getEntityData(StateChangeObj stateChange) {
        Set<String> ids = new HashSet<String>();
        BpmQueryEntityData queryEntityData = new BpmQueryEntityData();
        try {
            List<IEntityRow> entityData = queryEntityData.getEntityData(stateChange.getFormSchemeId(), stateChange.getPeriod());
            ids = entityData.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toSet());
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return ids;
    }

    @Override
    public List<BusinessKey> buildBusinessKey(StateChangeObj stateChange, boolean start) {
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        Set<String> unitIds = this.getUnitIds(stateChange);
        Map<String, List<String>> reportIds = this.getReportIds(stateChange, unitIds, start);
        FormSchemeDefine formScheme = this.untilsMethod.getFormScheme(stateChange.getFormSchemeId());
        String defaultFormId = this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(stateChange.getFormSchemeId(), unitIds, stateChange.getPeriod(), stateChange.getAdjust(), false);
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            String unitkey = dimensionValue.getValue(this.dimensionUtil.getDwMainDimName(stateChange.getFormSchemeId())).toString();
            List<String> list = reportIds.get(unitkey);
            for (String reportId : list) {
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(stateChange.getFormSchemeId(), dimensionValue, reportId, reportId);
                businessKeys.add(businessKey);
            }
        }
        return businessKeys;
    }

    @Override
    public Map<BusinessKey, String> buildBusinessKeyMap(StateChangeObj stateChange, boolean start) {
        HashMap<BusinessKey, String> startParam = new HashMap<BusinessKey, String>();
        Set<String> unitIds = this.getUnitIds(stateChange);
        Map<String, List<String>> reportIdsMap = this.getReportIds(stateChange, unitIds, start);
        FormSchemeDefine formScheme = this.untilsMethod.getFormScheme(stateChange.getFormSchemeId());
        String defaultFormId = this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(stateChange.getFormSchemeId(), unitIds, stateChange.getPeriod(), stateChange.getAdjust(), false);
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            String unitkey = dimensionValue.getValue(this.dimensionUtil.getDwMainDimName(stateChange.getFormSchemeId())).toString();
            List<String> list = reportIdsMap.get(unitkey);
            for (String reportId : list) {
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(stateChange.getFormSchemeId(), dimensionValue, reportId, reportId);
                startParam.put(businessKey, unitkey);
            }
        }
        return startParam;
    }
}

