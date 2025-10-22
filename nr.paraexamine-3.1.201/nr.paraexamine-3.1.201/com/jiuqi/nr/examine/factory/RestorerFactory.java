/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.factory;

import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.restorer.AbstractRestorer;
import com.jiuqi.nr.examine.restorer.TaskRestorer;
import com.jiuqi.nr.examine.task.RestorTask;

public class RestorerFactory {
    public static AbstractRestorer getRestorer(RestorTask task) {
        ParaType type = task.getParaType();
        switch (type) {
            case TASK: {
                return new TaskRestorer(task);
            }
        }
        return null;
    }
}

