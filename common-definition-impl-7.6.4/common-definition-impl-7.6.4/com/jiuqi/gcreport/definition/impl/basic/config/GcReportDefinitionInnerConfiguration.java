/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.service.IDataSchemeChecker
 */
package com.jiuqi.gcreport.definition.impl.basic.config;

import com.jiuqi.budget.param.hypermodel.service.IDataSchemeChecker;
import com.jiuqi.gcreport.definition.impl.basic.init.DbBeanFactoryPostProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.GcCustomSchemeChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.gcreport.definition.impl.basic"})
public class GcReportDefinitionInnerConfiguration {
    @Bean(value={"com.jiuqi.gcreport.definition.impl.basic.init.DbBeanFactoryPostProcessor"})
    @ConditionalOnMissingBean(value={DbBeanFactoryPostProcessor.class})
    public static DbBeanFactoryPostProcessor initGcBeanFactoryPostProcessor() {
        return new DbBeanFactoryPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(value={IDataSchemeChecker.class})
    public GcCustomSchemeChecker initGcCustomSchemeChecker() {
        return new GcCustomSchemeChecker();
    }
}

