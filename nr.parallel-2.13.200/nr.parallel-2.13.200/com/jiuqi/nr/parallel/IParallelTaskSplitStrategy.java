/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import java.util.List;

public interface IParallelTaskSplitStrategy {
    public String getName();

    public String getTitle();

    public String getDesc();

    public List<BatchParallelExeTask> doSplit(BatchParallelExeTask var1, int var2);
}

