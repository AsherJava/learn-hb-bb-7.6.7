/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.config;

import com.jiuqi.nr.single.core.service.SingleFileHelper;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.service.internal.SingleFileHelperImpl;
import com.jiuqi.nr.single.core.service.internal.SingleFileParserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingleCoreConfig {
    @Bean
    public SingleFileParserService getSingleFileParserService() {
        return new SingleFileParserServiceImpl();
    }

    @Bean
    public SingleFileHelper getSingleFileHelper() {
        return new SingleFileHelperImpl();
    }
}

