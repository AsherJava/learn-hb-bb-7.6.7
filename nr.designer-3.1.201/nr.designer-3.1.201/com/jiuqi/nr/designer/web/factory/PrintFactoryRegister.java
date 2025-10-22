/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.factory;

import com.jiuqi.nr.designer.web.factory.PrintFactoryRegisterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class PrintFactoryRegister
implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(PrintFactoryRegister.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            PrintFactoryRegisterUtil.init();
        }
        catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }
}

