/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.config;

import com.jiuqi.nr.data.logic.facade.service.ICheckSchemeRecordService;
import com.jiuqi.nr.data.logic.internal.impl.ckd.DefCkdRuleProvider;
import com.jiuqi.nr.data.logic.internal.provider.impl.DefFmlProviderFactory;
import com.jiuqi.nr.data.logic.internal.service.impl.CheckSchemeRecordServiceImpl;
import com.jiuqi.nr.data.logic.spi.ICKDRuleProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"com.jiuqi.nr.data.logic"})
@Configuration
public class DataLogicConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={ICheckSchemeRecordService.class})
    public ICheckSchemeRecordService getICheckSchemeRecordService() {
        return new CheckSchemeRecordServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value={DefFmlProviderFactory.class})
    public DefFmlProviderFactory getDefFmlProviderFactory() {
        return new DefFmlProviderFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value={com.jiuqi.nr.data.logic.internal.impl.DefFmlProviderFactory.class})
    public com.jiuqi.nr.data.logic.internal.impl.DefFmlProviderFactory getApiDefFmlProviderFactory() {
        return new com.jiuqi.nr.data.logic.internal.impl.DefFmlProviderFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value={ICKDRuleProvider.class})
    public ICKDRuleProvider getICKDRuleProvider() {
        return new DefCkdRuleProvider();
    }
}

