/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.BatchTaskExecuteFactoryMgr;
import com.jiuqi.nr.parallel.IBatchTaskExecuteFactory;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BatchTaskExecuteFactoryRegistor
implements InitializingBean {
    @Autowired(required=false)
    private List<IBatchTaskExecuteFactory> factories;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.factories != null) {
            BatchTaskExecuteFactoryMgr factoryMgr = BatchTaskExecuteFactoryMgr.getInstance();
            for (IBatchTaskExecuteFactory factory : this.factories) {
                factoryMgr.regFactory(factory);
            }
        }
    }
}

