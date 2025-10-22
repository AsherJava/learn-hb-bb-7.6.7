/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService
 */
package com.jiuqi.gcreport.bde.bill.extract.impl;

import com.jiuqi.gcreport.bde.bill.extract.impl.service.impl.DefaultBillExtractSchemeConfigService;
import com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gcreport.bde.bill.extract.impl"})
public class GcBdeBillExtractImplAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={IBillExtractSchemeConfigService.class})
    public DefaultBillExtractSchemeConfigService getExtractSchemeConfig() {
        return new DefaultBillExtractSchemeConfigService();
    }
}

