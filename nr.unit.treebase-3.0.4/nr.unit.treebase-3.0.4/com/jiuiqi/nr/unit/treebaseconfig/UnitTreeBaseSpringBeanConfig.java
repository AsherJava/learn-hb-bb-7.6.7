/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebaseconfig;

import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceID;
import com.jiuiqi.nr.unit.treebase.source.def.DefaultUnitTreeDataSourceID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuiqi.nr.unit.treebase"})
public class UnitTreeBaseSpringBeanConfig {
    @Bean
    @ConditionalOnMissingBean
    public IUnitTreeDataSourceID getDefaultUnitTreeDataSourceID() {
        return new DefaultUnitTreeDataSourceID();
    }
}

