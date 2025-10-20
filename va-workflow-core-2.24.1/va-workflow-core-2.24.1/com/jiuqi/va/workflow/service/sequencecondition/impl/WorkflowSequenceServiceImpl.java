/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.sequencecondition.SequenceConditionView
 *  com.jiuqi.va.domain.workflow.service.WorkflowSequenceService
 */
package com.jiuqi.va.workflow.service.sequencecondition.impl;

import com.jiuqi.va.domain.workflow.sequencecondition.SequenceConditionView;
import com.jiuqi.va.domain.workflow.service.WorkflowSequenceService;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import org.springframework.stereotype.Service;

@Service
public class WorkflowSequenceServiceImpl
implements WorkflowSequenceService {
    public boolean executeConditionView(SequenceConditionView conditionView) {
        return SequenceConditionUtils.executeConditionView(conditionView);
    }
}

