/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade.print.register;

import com.jiuqi.nr.definition.facade.print.common.runtime.FactoryRegisterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class FactoryRegister
implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FactoryRegister.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            FactoryRegisterUtil.init();
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
}

