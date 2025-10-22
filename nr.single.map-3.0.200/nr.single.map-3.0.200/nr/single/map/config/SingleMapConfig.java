/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.config;

import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.internal.SingleMapNrController;
import nr.single.map.data.internal.SingleMapNrService;
import nr.single.map.data.internal.util.SingleMapEntityUtilImplFMDM;
import nr.single.map.data.util.SingleMapEntityUtil;
import nr.single.map.param.internal.service.SingleParamFileServiceImpl;
import nr.single.map.param.service.SingleParamFileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"nr.single.map.data.internal.service"})
@Configuration
public class SingleMapConfig {
    @Bean
    public ISingleMapNrController getSingleMapNrController() {
        return new SingleMapNrController();
    }

    @Bean
    public SingleMapNrService getSingleMapNrService() {
        return new SingleMapNrService();
    }

    @Bean
    public SingleMapEntityUtil getSingleMapEntityUtil() {
        return new SingleMapEntityUtilImplFMDM();
    }

    @Bean
    public SingleParamFileService getSingleParamFileService() {
        return new SingleParamFileServiceImpl();
    }
}

