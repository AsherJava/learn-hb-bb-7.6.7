/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.config;

import nr.midstore.design.web.MidstoreDesginController;
import nr.midstore.design.web.MidstoreReportParamQueryController;
import nr.midstore.design.web.ReportMidstoreDesginController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.midstore.design.service.impl"})
@Configuration
public class MidstoreDesignConfig {
    @Bean
    public MidstoreDesginController getMidstoreDesginController() {
        return new MidstoreDesginController();
    }

    @Bean
    public MidstoreReportParamQueryController getMidstoreReportParamQueryController() {
        return new MidstoreReportParamQueryController();
    }

    @Bean
    public ReportMidstoreDesginController getReportMidstoreDesginController() {
        return new ReportMidstoreDesginController();
    }
}

