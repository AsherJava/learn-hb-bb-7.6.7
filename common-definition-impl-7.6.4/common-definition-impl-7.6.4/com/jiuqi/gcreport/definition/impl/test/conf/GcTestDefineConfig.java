/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.test.conf;

import com.jiuqi.gcreport.definition.impl.test.dao.GcTestDefineDao;
import com.jiuqi.gcreport.definition.impl.test.service.GcTestDefineService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name={"jiuqi.gcreport.env.gctest"}, havingValue="true")
@ComponentScan(value={"com.jiuqi.gcreport.definition.impl.test"})
public class GcTestDefineConfig {
    @Bean
    public GcTestDefineDao initGcTestDefineDao() {
        return new GcTestDefineDao();
    }

    @Bean
    public GcTestDefineService initGcTestDefineService() {
        return new GcTestDefineService();
    }
}

