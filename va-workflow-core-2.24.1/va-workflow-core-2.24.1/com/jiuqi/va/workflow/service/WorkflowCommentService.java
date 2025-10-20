/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO
 *  com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDO;
import com.jiuqi.va.domain.workflow.comment.WorkflowCommentDTO;
import java.math.BigDecimal;
import java.util.List;

public interface WorkflowCommentService {
    public void add(WorkflowCommentDTO var1);

    public List<String> getComments(WorkflowCommentDTO var1);

    public List<WorkflowCommentDO> getCommentByUsername(WorkflowCommentDTO var1);

    public void move(WorkflowCommentDTO var1);

    public void sortWorkflowComments(List<WorkflowCommentDO> var1, List<BigDecimal> var2);

    public void deleteComment(WorkflowCommentDTO var1);
}

