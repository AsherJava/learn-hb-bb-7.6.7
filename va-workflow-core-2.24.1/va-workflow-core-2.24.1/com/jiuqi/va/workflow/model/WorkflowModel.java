/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 */
package com.jiuqi.va.workflow.model;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import java.util.Map;
import java.util.Set;

public interface WorkflowModel
extends Model {
    public R startProcess(WorkflowDTO var1);

    public R completeTask(WorkflowDTO var1);

    public R retractTask(WorkflowDTO var1);

    public R retractProcess(WorkflowDTO var1);

    public R retractRejectProcess(WorkflowDTO var1);

    public R refreshParticipant(WorkflowDTO var1);

    public R plusApproval(WorkflowDTO var1);

    public R trigger(WorkflowDTO var1);

    public Map<String, Object> currNodeProperties(WorkflowDTO var1);

    public R reStartProcess(WorkflowDTO var1, UserLoginDTO var2);

    public Map<String, Map<String, Object>> getNodesProperty(String var1, int var2, Set<String> var3);

    public R reopenLastNode(WorkflowDTO var1);

    public void checkRetract(WorkflowDTO var1);
}

