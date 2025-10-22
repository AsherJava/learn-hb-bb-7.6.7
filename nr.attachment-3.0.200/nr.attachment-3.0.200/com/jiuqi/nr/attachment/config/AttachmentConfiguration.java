/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.util.FileCalculateService
 */
package com.jiuqi.nr.attachment.config;

import com.jiuqi.nr.attachment.service.impl.FileSumServiceImpl;
import com.jiuqi.nr.data.engine.gather.util.FileCalculateService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.attachment"})
public class AttachmentConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={FileSumServiceImpl.class})
    public FileCalculateService secondServiceImpl() {
        return new FileSumServiceImpl();
    }
}

