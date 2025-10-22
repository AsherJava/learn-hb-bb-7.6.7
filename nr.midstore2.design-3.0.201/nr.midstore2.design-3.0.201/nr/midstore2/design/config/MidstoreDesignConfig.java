/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.design.config;

import nr.midstore2.design.web.ReportMidstoreDesginController2;
import nr.midstore2.design.web.ReportMidstoreParamQueryController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.midstore2.design.service.impl"})
@Configuration
public class MidstoreDesignConfig {
    @Bean
    public ReportMidstoreParamQueryController getReportMidstoreParamQueryController() {
        return new ReportMidstoreParamQueryController();
    }

    @Bean
    public ReportMidstoreDesginController2 getReportMidstoreDesginController2() {
        return new ReportMidstoreDesginController2();
    }
}

