/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.common.UserAction;
import java.util.List;
import java.util.Set;

public interface UserTask {
    public String getId();

    public String getName();

    public String getTodoTemplate();

    public boolean isFormEditable();

    public boolean isRetrivable();

    public List<UserAction> getActions();

    public List<ActorStrategyInstance> getActorStrategies();

    public boolean isNeedNotice();

    public Set<String> getNoticeChannles();
}

