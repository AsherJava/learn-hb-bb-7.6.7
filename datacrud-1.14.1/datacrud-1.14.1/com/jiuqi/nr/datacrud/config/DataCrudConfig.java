/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datacrud.config;

import com.jiuqi.nr.datacrud.impl.crud.inner.DwDataClearLis;
import com.jiuqi.nr.datacrud.impl.fillenum.EnumFillDataProvider;
import com.jiuqi.nr.datacrud.impl.service.impl.DefaultEntityTableFactory;
import com.jiuqi.nr.datacrud.impl.service.impl.DefaultExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.FillDataProvider;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.datacrud"})
public class DataCrudConfig {
    @Bean
    @ConditionalOnMissingBean(value={FillDataProvider.class})
    public FillDataProvider fillDataProvider() {
        return new EnumFillDataProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value={IEntityTableFactory.class})
    public IEntityTableFactory getIEntityTableFactory() {
        return new DefaultEntityTableFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value={DwDataClearLis.class})
    public DwDataClearLis getDwDataClearLis(IRuntimeDataSchemeService runtimeDataSchemeService, NrdbHelper nrdbHelper, JdbcTemplate jdbcTemplate, INvwaDataAccessProvider dataAccessProvider, DataModelService dataModelService) {
        DwDataClearLis dwDataClearLis = new DwDataClearLis();
        dwDataClearLis.setRuntimeDataSchemeService(runtimeDataSchemeService);
        dwDataClearLis.setNrdbHelper(nrdbHelper);
        dwDataClearLis.setJdbcTemplate(jdbcTemplate);
        dwDataClearLis.setDataModelService(dataModelService);
        dwDataClearLis.setDataAccessProvider(dataAccessProvider);
        return dwDataClearLis;
    }

    @Bean
    @ConditionalOnMissingBean(value={IExecutorContextFactory.class})
    public IExecutorContextFactory getIExecutorContextFactory() {
        return new DefaultExecutorContextFactory();
    }

    @Bean(name={"com.jiuqi.nr.crud.CrudMessageSource"})
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasenames("messages/messages", "messages/DataCrud");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

