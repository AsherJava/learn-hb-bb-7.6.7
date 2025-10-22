/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskSplitStrategy;
import java.util.List;

public abstract class AbstractSplitStrategy
implements IParallelTaskSplitStrategy {
    public static final int DEFAULT_SIZE = 500;
    private String name;
    private String title;
    private String desc;

    public AbstractSplitStrategy(String name, String title, String desc) {
        this.name = name;
        this.title = title;
        this.desc = desc;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public abstract List<BatchParallelExeTask> doSplit(BatchParallelExeTask var1, int var2);
}

