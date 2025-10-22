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
import java.util.LinkedHashSet;
import java.util.List;

public class BuildSingleTree
extends BuildUnitTree
implements IBuildResourceTree {
    private LinkedHashSet<String> resourceid;

    public BuildSingleTree() {
    }

    public BuildSingleTree(LinkedHashSet<String> resourceid, String formSchemeKey, DimensionValueSet dim, DimensionUtil dimensionUtil, DeEntityHelper deEntityHelper, CommonUtil commonUtil) {
        super(formSchemeKey, dim, dimensionUtil, deEntityHelper, commonUtil);
        this.resourceid = resourceid;
    }

    @Override
    public List<StepTree> buildResourceTree() {
        List<StepTree> unitData = this.buildUnitData();
        for (StepTree unit : unitData) {
            unit.setResourceMap(this.resourceid);
        }
        return unitData;
    }
}

