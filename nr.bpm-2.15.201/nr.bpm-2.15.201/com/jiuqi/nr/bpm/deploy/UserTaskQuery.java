/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.common.UserTask;
import java.util.List;

public interface UserTaskQuery {
    public UserTask getUserTask(String var1, String var2);

    public List<UserTask> getUserTasks(String var1);
}

