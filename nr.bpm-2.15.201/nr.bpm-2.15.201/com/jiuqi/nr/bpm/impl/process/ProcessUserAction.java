/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.common.UserAction;

public class ProcessUserAction
implements UserAction {
    private String actionId;
    private String name;
    private Boolean needComment;

    public ProcessUserAction(String actionId, String name, Boolean needComment) {
        this.actionId = actionId;
        this.name = name;
        this.needComment = needComment;
    }

    @Override
    public String getId() {
        return this.actionId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isNeedComment() {
        return this.needComment;
    }
}

