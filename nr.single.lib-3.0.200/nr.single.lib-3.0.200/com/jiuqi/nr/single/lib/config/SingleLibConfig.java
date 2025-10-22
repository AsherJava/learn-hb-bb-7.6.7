/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.config;

import com.jiuqi.nr.single.lib.reg.internal.service.OrderNumServiceImpl;
import com.jiuqi.nr.single.lib.reg.internal.service.RoRegisterServiceImpl;
import com.jiuqi.nr.single.lib.reg.service.OrderNumService;
import com.jiuqi.nr.single.lib.reg.service.RoRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingleLibConfig {
    @Bean
    public RoRegisterService getRoRegisterService() {
        return new RoRegisterServiceImpl();
    }

    @Bean
    public OrderNumService getOrderNumService() {
        return new OrderNumServiceImpl();
    }
}

