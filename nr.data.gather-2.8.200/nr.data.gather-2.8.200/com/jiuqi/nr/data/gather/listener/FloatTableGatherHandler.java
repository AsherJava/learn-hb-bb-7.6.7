/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.listener;

import com.jiuqi.nr.data.gather.listener.DefaultFloatTableGatherProvider;
import com.jiuqi.nr.data.gather.listener.FloatTableGatherProvider;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FloatTableGatherHandler {
    @Autowired
    @Qualifier(value="defaultFloatTableGatherProvider")
    private DefaultFloatTableGatherProvider defaultFloatTableGatherProvider;
    @Autowired
    private List<FloatTableGatherProvider> floatTableGatherProviders;
    private boolean sorted = false;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public FloatTableGatherProvider getFloatTableGatherProvider() {
        if (!this.sorted) {
            try {
                this.reentrantLock.lock();
                if (!this.sorted) {
                    this.floatTableGatherProviders.sort(Comparator.comparing(FloatTableGatherProvider::getOrder));
                    this.sorted = true;
                }
            }
            finally {
                this.reentrantLock.unlock();
            }
        }
        for (FloatTableGatherProvider provider : this.floatTableGatherProviders) {
            if (!provider.isEnable()) continue;
            return provider;
        }
        return this.defaultFloatTableGatherProvider;
    }
}

