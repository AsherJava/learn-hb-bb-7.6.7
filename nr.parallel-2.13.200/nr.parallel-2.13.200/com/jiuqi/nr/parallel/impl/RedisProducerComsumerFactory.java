/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.IParallelTaskComsumer;
import com.jiuqi.nr.parallel.IParallelTaskProducer;
import com.jiuqi.nr.parallel.IProducerComsumerFactory;
import com.jiuqi.nr.parallel.impl.RedisComsumer;
import com.jiuqi.nr.parallel.impl.RedisProducer;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class RedisProducerComsumerFactory
implements IProducerComsumerFactory {
    @Autowired
    private RedisComsumer comsumer;
    @Autowired
    private RedisProducer producer;

    @Override
    public IParallelTaskComsumer getComsumer() {
        return this.comsumer;
    }

    @Override
    public IParallelTaskProducer getProducer() {
        return this.producer;
    }

    @Override
    public void regQueue(String type) {
        this.comsumer.regQueue(type);
    }
}

