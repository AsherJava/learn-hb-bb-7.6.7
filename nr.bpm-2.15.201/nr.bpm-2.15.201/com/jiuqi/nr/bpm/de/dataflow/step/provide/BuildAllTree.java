/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.step.provide;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.inter.IBuildResourceTree;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.BuildUnitTree;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class BuildAllTree
extends BuildUnitTree
implements IBuildResourceTree {
    private Map<String, LinkedHashSet<String>> resourceIdMap = new HashMap<String, LinkedHashSet<String>>();

    public BuildAllTree() {
    }

    public BuildAllTree(Map<String, LinkedHashSet<String>> resourceIdMap, String formSchemeKey, DimensionValueSet dim, DimensionUtil dimensionUtil, DeEntityHelper deEntityHelper, CommonUtil commonUtil) {
        super(formSchemeKey, dim, dimensionUtil, deEntityHelper, commonUtil);
        this.resourceIdMap = resourceIdMap;
    }

    @Override
    public List<StepTree> buildResourceTree() {
        List<StepTree> unitData = this.buildUnitData();
        for (StepTree unit : unitData) {
            if (this.resourceIdMap.get(unit.getId()) == null || this.resourceIdMap.get(unit.getId()).size() <= 0) continue;
            unit.setResourceMap(this.resourceIdMap.get(unit.getId()));
        }
        return unitData;
    }
}

