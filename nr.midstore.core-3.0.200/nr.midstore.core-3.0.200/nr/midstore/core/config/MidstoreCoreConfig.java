/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.config;

import nr.midstore.core.asyn.MidstoreBatchAsyncTaskExecutor;
import nr.midstore.core.asyn.MidstoreBatchController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.midstore.core.internal.definition.dao", "nr.midstore.core.internal.definition.service", "nr.midstore.core.internal.publish.service", "nr.midstore.core.internal.param.service", "nr.midstore.core.internal.work.service", "nr.midstore.core.internal.work.service.org", "nr.midstore.core.internal.work.service.data", "nr.midstore.core.internal.job.service", "nr.midstore.core.internal.auto.service", "nr.midstore.core.internal.dataset", "nr.midstore.core.internal.dataset.clear", "nr.midstore.core.internal.util.service", "nr.midstore.core.internal.util.service.auth"})
@Configuration
public class MidstoreCoreConfig {
    @Bean
    public MidstoreBatchController getMidstoreBatchController() {
        return new MidstoreBatchController();
    }

    @Bean
    public MidstoreBatchAsyncTaskExecutor getMidstoreBatchAsyncTaskExecutor() {
        return new MidstoreBatchAsyncTaskExecutor();
    }
}

