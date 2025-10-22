/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package nr.single.map.config;

import com.jiuqi.nr.file.FileAreaConfig;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.dao.FileInfoDao;
import nr.single.map.configurations.dao.impl.ConfigDaoImpl;
import nr.single.map.configurations.dao.impl.FileInfoDaoImpl;
import nr.single.map.configurations.file.config.SingleFileAreaConfig;
import nr.single.map.configurations.service.FormulaSchemeService;
import nr.single.map.configurations.service.HandleUpdateConfigService;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.configurations.service.MappingFileService;
import nr.single.map.configurations.service.ParseMapping;
import nr.single.map.configurations.service.impl.EntityMappingServiceImpl;
import nr.single.map.configurations.service.impl.FormulaMappingServiceImpl;
import nr.single.map.configurations.service.impl.FormulaSchemeServiceImpl;
import nr.single.map.configurations.service.impl.HandleUpdateConfigServiceImpl;
import nr.single.map.configurations.service.impl.MappingConfigServiceImpl;
import nr.single.map.configurations.service.impl.MappingFileServiceImpl;
import nr.single.map.configurations.service.impl.ZbMappingServiceImpl;
import org.springframework.context.annotation.Bean;

public class ConfigurationMappingConfig {
    @Bean(name={"zbMappingServiceImpl"})
    public ParseMapping zbMappingServiceImpl() {
        return new ZbMappingServiceImpl();
    }

    @Bean(name={"entityMappingServiceImpl"})
    public ParseMapping entityMappingServiceImpl() {
        return new EntityMappingServiceImpl();
    }

    @Bean(name={"formulaMappingServiceImpl"})
    public ParseMapping formulaMappingServiceImpl() {
        return new FormulaMappingServiceImpl();
    }

    @Bean
    public MappingFileService getMappingFileService() {
        return new MappingFileServiceImpl();
    }

    @Bean
    public FileInfoDao getFileInfoDao() {
        return new FileInfoDaoImpl();
    }

    @Bean
    public ConfigDao getConfigDao() {
        return new ConfigDaoImpl();
    }

    @Bean
    public MappingConfigService getMappingConfigService() {
        return new MappingConfigServiceImpl();
    }

    @Bean
    public FileAreaConfig getSingleFileAreaConfig() {
        return new SingleFileAreaConfig();
    }

    @Bean
    public FormulaSchemeService getFormulaSchemeService() {
        return new FormulaSchemeServiceImpl();
    }

    @Bean
    public HandleUpdateConfigService getHandleUpdateConfigService() {
        return new HandleUpdateConfigServiceImpl();
    }
}

