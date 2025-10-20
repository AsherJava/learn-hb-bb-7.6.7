/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.annotation.ComponentStandaloneScan
 */
package com.jiuqi.bde.floatmodel.plugin;

import com.jiuqi.bde.common.annotation.ComponentStandaloneScan;
import com.jiuqi.bde.floatmodel.plugin.handler.CustomFetchComposeFloatConfigHandler;
import com.jiuqi.bde.floatmodel.plugin.handler.DefinedFloatRegionHandler;
import com.jiuqi.bde.floatmodel.plugin.handler.SeniorDataComposeFloatConfigHandler;
import com.jiuqi.bde.floatmodel.plugin.handler.SimpleDataComposeFloatConfigHandler;
import com.jiuqi.bde.floatmodel.plugin.handler.VaQueryFloatRegionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentStandaloneScan(basePackages={"com.jiuqi.bde.floatmodel.plugin"})
public class BdeFloatModelPluginAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={CustomFetchComposeFloatConfigHandler.class})
    public CustomFetchComposeFloatConfigHandler getCustomFetchComposeFloatConfigHandler() {
        return new CustomFetchComposeFloatConfigHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value={DefinedFloatRegionHandler.class})
    public DefinedFloatRegionHandler getDefinedFloatRegionHandler() {
        return new DefinedFloatRegionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value={SeniorDataComposeFloatConfigHandler.class})
    public SeniorDataComposeFloatConfigHandler getSeniorDataComposeFloatConfigHandler() {
        return new SeniorDataComposeFloatConfigHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value={SimpleDataComposeFloatConfigHandler.class})
    public SimpleDataComposeFloatConfigHandler getSimpleDataComposeFloatConfigHandler() {
        return new SimpleDataComposeFloatConfigHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value={VaQueryFloatRegionHandler.class})
    public VaQueryFloatRegionHandler getVaQueryFloatRegionHandler() {
        return new VaQueryFloatRegionHandler();
    }
}

