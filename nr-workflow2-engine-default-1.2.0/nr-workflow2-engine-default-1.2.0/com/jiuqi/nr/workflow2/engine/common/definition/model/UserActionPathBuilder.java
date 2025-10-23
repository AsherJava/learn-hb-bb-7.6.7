/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatus;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionBuilder;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskBuilder;

class UserActionPathBuilder {
    private UserActionBuilder action;
    private UserTaskBuilder destUserTask;
    private ProcessStatus destStatus;

    public UserActionPathBuilder(UserActionBuilder action, UserTaskBuilder destUserTask, ProcessStatus destStatus) {
        this.action = action;
        this.destUserTask = destUserTask;
        this.destStatus = destStatus;
    }

    public UserActionPath build() {
        return new UserActionPath(this.action.build(), this.destUserTask.userTask, this.destStatus);
    }
}

