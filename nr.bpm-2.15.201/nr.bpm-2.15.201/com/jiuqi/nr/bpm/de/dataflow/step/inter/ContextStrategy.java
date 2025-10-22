/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.step.inter;

import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.bpm.de.dataflow.step.FailGroupOrReportData;
import com.jiuqi.nr.bpm.de.dataflow.step.FailUnitData;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.inter.IResultData;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.StepQueryState;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ContextStrategy {
    public void resultData(WorkFlowType startType, BatchStepByStepParam stepByOptParam, List<StepTree> subList, String period, StepQueryState stepQueryState, BatchStepByStepResult upload, boolean isUpload, boolean stepByStepBackAll) {
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            IResultData iResultData = (IResultData)BeanUtils.getBean(FailUnitData.class);
            iResultData.resultData(stepByOptParam, subList, period, stepQueryState, upload, isUpload, stepByStepBackAll);
        } else if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            IResultData iResultData = (IResultData)BeanUtils.getBean(FailGroupOrReportData.class);
            iResultData.resultData(stepByOptParam, subList, period, stepQueryState, upload, isUpload, stepByStepBackAll);
        }
    }
}

