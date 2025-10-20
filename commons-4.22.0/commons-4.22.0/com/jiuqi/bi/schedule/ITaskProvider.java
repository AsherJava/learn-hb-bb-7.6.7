/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.Task;
import java.util.Iterator;

public interface ITaskProvider {
    public Iterator<Task> getTasks();
}

