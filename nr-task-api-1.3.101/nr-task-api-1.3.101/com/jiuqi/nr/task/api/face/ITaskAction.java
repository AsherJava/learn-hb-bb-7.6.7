/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.face;

import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.common.Constants;

public interface ITaskAction {
    public String code();

    public String title();

    public double order();

    public ComponentDefine component();

    default public boolean apply(Constants.ActionType type, String key) {
        return true;
    }

    default public boolean enable(Constants.ActionType type, String key) {
        return true;
    }
}

