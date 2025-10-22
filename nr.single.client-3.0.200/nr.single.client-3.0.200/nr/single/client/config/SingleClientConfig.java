/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.config;

import java.util.HashMap;
import java.util.Map;
import nr.single.client.internal.service.SingleDataCheckServiceImpl;
import nr.single.client.internal.service.SingleFuncExecuteServiceImpl;
import nr.single.client.internal.service.SingleSplictServiceImpl;
import nr.single.client.service.ISingleDataCheckService;
import nr.single.client.service.ISingleExportService;
import nr.single.client.service.ISingleFuncExecuteService;
import nr.single.client.service.ISingleSplictService;
import nr.single.client.web.DataentryActionController;
import nr.single.client.web.SingleAuthController;
import nr.single.client.web.SingleClientController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"nr.single.client.internal.service.upload", "nr.single.client.internal.service.upload.notice", "nr.single.client.internal.service.export", "nr.single.client.internal.service.entity", "nr.single.client.internal.service.querycheck", "package nr.single.client.internal.service.upload.select"})
public class SingleClientConfig {
    @Bean
    public SingleClientController getSingleClientController() {
        return new SingleClientController();
    }

    @Bean
    public ISingleFuncExecuteService getISingleFuncExecuteService() {
        return new SingleFuncExecuteServiceImpl();
    }

    @Bean
    public Map<String, ISingleExportService> getSingleExportServices() {
        return new HashMap<String, ISingleExportService>();
    }

    @Bean
    public SingleAuthController getSingleAuthController() {
        return new SingleAuthController();
    }

    @Bean
    public DataentryActionController getDataentryActionController() {
        return new DataentryActionController();
    }

    @Bean
    public ISingleSplictService getSingleSplictService() {
        return new SingleSplictServiceImpl();
    }

    @Bean
    public ISingleDataCheckService getSingleDataCheckService() {
        return new SingleDataCheckServiceImpl();
    }
}

