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
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityBuildParam
implements IBulidParam {
    private static final Logger logger = LoggerFactory.getLogger(EntityBuildParam.class);
    @Autowired
    private SettingUtil untilsMethod;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private BusinessGenerator businessGenerator;

    @Override
    public int getType() {
        return WorkFlowType.ENTITY.getValue();
    }

    @Override
    public Map<BusinessKey, String> buildBusinessKeyMap(StateChangeObj stateChange, WorkflowStatus flowType, boolean start) {
        HashMap<BusinessKey, String> startParam = new HashMap<BusinessKey, String>();
        FormSchemeDefine formScheme = this.untilsMethod.getFormScheme(stateChange.getFormSchemeId());
        Set<String> unitids = stateChange.getDataObj();
        Map<String, Set<String>> alreadyStartFlow = this.untilsMethod.queryAlreadyStart(stateChange.getFormSchemeId(), unitids, null, stateChange.getPeriod(), stateChange.getAdjust());
        String defaultFormId = this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId());
        String workflowKey = this.untilsMethod.queryFlowDefineKey(stateChange.getFormSchemeId());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(stateChange.getFormSchemeId(), unitids, stateChange.getPeriod(), stateChange.getAdjust(), stateChange.isSelectAll());
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        StringBuffer sb = null;
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            DimensionSet dimensionSet = dimensionValue.getDimensionSet();
            sb = new StringBuffer();
            sb.append("'");
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String dimension = dimensionSet.get(i);
                sb.append(dimensionValue.getValue(dimension).toString()).append(";");
            }
            sb.append("'");
            if (alreadyStartFlow.containsKey(sb.toString())) continue;
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(stateChange.getFormSchemeId(), dimensionValue, defaultFormId, defaultFormId);
            startParam.put(businessKey, workflowKey);
        }
        return startParam;
    }

    @Override
    public List<BusinessKey> buildBusinessKey(StateChangeObj stateChange, boolean start) {
        ArrayList<BusinessKey> businessKeyList = new ArrayList<BusinessKey>();
        FormSchemeDefine formScheme = this.untilsMethod.getFormScheme(stateChange.getFormSchemeId());
        String defaultFormId = this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(stateChange.getFormSchemeId(), stateChange.getDataObj(), stateChange.getPeriod(), stateChange.getAdjust(), stateChange.isSelectAll());
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(stateChange.getFormSchemeId(), dimensionValue, defaultFormId, defaultFormId);
            businessKeyList.add(businessKey);
        }
        return businessKeyList;
    }

    @Override
    public Map<BusinessKey, String> buildBusinessKeyMap(StateChangeObj stateChange, boolean start) {
        HashMap<BusinessKey, String> startParam = new HashMap<BusinessKey, String>();
        FormSchemeDefine formScheme = this.untilsMethod.getFormScheme(stateChange.getFormSchemeId());
        String defaultFormId = this.untilsMethod.getDefaultFormId(stateChange.getFormSchemeId());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(stateChange.getFormSchemeId(), stateChange.getDataObj(), stateChange.getPeriod(), stateChange.getAdjust(), stateChange.isSelectAll());
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValue = dimensionCombination.toDimensionValueSet();
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(stateChange.getFormSchemeId(), dimensionValue, defaultFormId, defaultFormId);
            Object value = dimensionValue.getValue(this.dimensionUtil.getDwMainDimName(stateChange.getFormSchemeId()));
            startParam.put(businessKey, value.toString());
        }
        return startParam;
    }
}

