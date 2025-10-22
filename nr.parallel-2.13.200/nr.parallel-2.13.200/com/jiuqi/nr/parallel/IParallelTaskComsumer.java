/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.nr.parallel.BatchParallelExeTask;

public interface IParallelTaskComsumer {
    public void consume() throws Exception;

    public BatchParallelExeTask getTask(String var1);
}

