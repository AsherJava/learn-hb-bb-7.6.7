/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.config;

import nr.single.para.configurations.service.FileAnalysisService;
import nr.single.para.configurations.service.impl.FileAnalysisServiceImpl;
import nr.single.para.web.FileTransmissionController;
import nr.single.para.web.SchemeConfigController;
import org.springframework.context.annotation.Bean;

public class ParaConfigurationMappingConfig {
    @Bean
    public SchemeConfigController getSchemeConfigurationController() {
        return new SchemeConfigController();
    }

    @Bean
    public FileTransmissionController getFileTransmissionController() {
        return new FileTransmissionController();
    }

    @Bean
    public FileAnalysisService getFileAnalysisService() {
        return new FileAnalysisServiceImpl();
    }
}

