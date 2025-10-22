/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.common.CustomDesignWorkflow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component(value="11")
public class Test1
implements CustomDesignWorkflow {
    @Override
    public boolean isApply(String processDefinitionKey) {
        return false;
    }

    @Override
    public List<WorkFlowTreeState> queryWorkflowTreeState(String processDefinitionKey) {
        ArrayList<WorkFlowTreeState> aa = new ArrayList<WorkFlowTreeState>();
        WorkFlowTreeState workFlowTreeState = new WorkFlowTreeState();
        workFlowTreeState.setCode("11");
        workFlowTreeState.setTitle("11111");
        aa.add(workFlowTreeState);
        return aa;
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> queryWorkflowTreeUploadState(DimensionValueSet dimSet, String formKey, String formSchemeKey, String processDefinitionKey) {
        return null;
    }

    @Override
    public ActionState getState(String p0, DimensionValueSet dimSet, String p2) {
        return null;
    }
}

