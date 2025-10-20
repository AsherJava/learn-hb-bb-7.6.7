/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.nvwa.sf.adapter.spring.NvwaModuleShutdownListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NvwaModuleShutdownConfig {
    @Bean
    public ServletListenerRegistrationBean<NvwaModuleShutdownListener> myListenerRegistration() {
        ServletListenerRegistrationBean<NvwaModuleShutdownListener> registration = new ServletListenerRegistrationBean<NvwaModuleShutdownListener>();
        registration.setListener(new NvwaModuleShutdownListener());
        return registration;
    }
}

