/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.nr.parallel.IParallelTaskComsumer;
import com.jiuqi.nr.parallel.IParallelTaskProducer;

public interface IProducerComsumerFactory {
    public IParallelTaskComsumer getComsumer();

    public IParallelTaskProducer getProducer();

    public void regQueue(String var1);
}

