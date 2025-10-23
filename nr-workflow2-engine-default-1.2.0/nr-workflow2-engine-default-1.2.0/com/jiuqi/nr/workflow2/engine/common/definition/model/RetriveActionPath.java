/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserAction;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;

public class RetriveActionPath {
    private final UserAction action;
    private final UserTask destUserTask;

    public RetriveActionPath(UserAction action, UserTask destUserTask) {
        this.action = action;
        this.destUserTask = destUserTask;
    }

    public UserAction getUserAction() {
        return this.action;
    }

    public UserTask getDestUserTask() {
        return this.destUserTask;
    }
}

