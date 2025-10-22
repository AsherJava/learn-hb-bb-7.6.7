/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ISendMessage {
    public void evaluateTodo(Task var1, UserTask var2, BusinessKey var3, String var4);

    public void send(Task var1, Set<String> var2, String var3, String var4);

    public void send(Task var1, Set<String> var2, String var3, String var4, WorkFlowLine var5);

    public void send(Task var1, BusinessKey var2, String var3, String var4, boolean var5, String var6);

    public void send(Task var1, BusinessKey var2, String var3, String var4, boolean var5, int var6, Set<String> var7, String var8);

    public void sendRetrieveMessage(Task var1, Optional<UserTask> var2, BusinessKey var3, DimensionValueSet var4, String var5, Task var6, String var7);

    public void sendApplyReturnTodo(Task var1, Optional<UserTask> var2, BusinessKey var3, String var4, Map<String, String> var5, String var6);

    public void sendApplyReturnTodoDisAgreed(Task var1, Optional<UserTask> var2, BusinessKey var3, String var4, Map<String, String> var5, String var6);

    public void sendApplyReturnMessage(Task var1, Optional<UserTask> var2, BusinessKey var3, String var4, Map<String, String> var5, String var6);
}

