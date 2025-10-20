/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowProcessReviewDTO;
import java.util.List;

public interface WorkflowProcessReviewService {
    public List<WorkflowProcessReviewDTO> getRejectInfos(WorkflowProcessReviewDTO var1);

    public R syncReviews(List<WorkflowProcessReviewDTO> var1);

    public List<WorkflowProcessReviewDTO> listReviews(WorkflowProcessReviewDTO var1);

    public List<WorkflowProcessReviewDTO> listRejects(WorkflowProcessReviewDTO var1);
}

