/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.define.config;

import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IOrgMappingTypeProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.impl.FieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.impl.OrgMappingTypeProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.impl.PluginTypeGather;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationSchemeDefineConfig {
    @Bean
    @ConditionalOnMissingBean(value={IPluginTypeGather.class})
    public IPluginTypeGather getPluginTypeGather() {
        return new PluginTypeGather();
    }

    @Bean
    @ConditionalOnMissingBean(value={IFieldMappingProviderGather.class})
    public IFieldMappingProviderGather getFieldMappingProviderGather() {
        return new FieldMappingProviderGather();
    }

    @Bean
    @ConditionalOnMissingBean(value={IOrgMappingTypeProviderGather.class})
    public IOrgMappingTypeProviderGather getOrgMappingTypeProviderGather() {
        return new OrgMappingTypeProviderGather();
    }
}

