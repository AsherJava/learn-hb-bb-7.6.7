/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.config;

import nr.single.para.web.DataSchemeTreeNodeController;
import nr.single.para.web.ReportParamQueryController;
import nr.single.para.web.UploadParamController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.single.para.upload.service"})
@Configuration
public class UploadParamConfig {
    @Bean
    public UploadParamController getUploadParamController() {
        return new UploadParamController();
    }

    @Bean
    public ReportParamQueryController getReportParamQueryController() {
        return new ReportParamQueryController();
    }

    @Bean
    public DataSchemeTreeNodeController getDataSchemeTreeNodeController() {
        return new DataSchemeTreeNodeController();
    }
}

