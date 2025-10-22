/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.config;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.config.DataEngineProperties;
import com.jiuqi.np.dataengine.config.TempTableMeta;
import com.jiuqi.np.dataengine.event.OperateRowEventHandler;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.impl.DataAccessProviderImpl;
import com.jiuqi.np.dataengine.impl.TwoKeyTempTableMeta;
import com.jiuqi.np.dataengine.intf.DataVersionService;
import com.jiuqi.np.dataengine.intf.EntityParentsService;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.impl.EntityParentsServiceImpl;
import com.jiuqi.np.dataengine.intf.impl.EntityResetCacheServiceImpl;
import com.jiuqi.np.dataengine.intf.impl.NpDataVersionServiceImpl;
import com.jiuqi.np.dataengine.intf.impl.OperateRowEventHandlerImpl;
import com.jiuqi.np.dataengine.query.account.AccountTempTable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value={DataEngineProperties.class})
public class DataEngineConfiguration {
    @Bean(name={"originalDataProvider"})
    public IDataAccessProvider getDataAccessProvider() {
        return new DataAccessProviderImpl();
    }

    @Bean
    public DataVersionService getDataVersionService() {
        return new NpDataVersionServiceImpl();
    }

    @Bean
    public OperateRowEventListener getOperateRowEventListener() {
        return new OperateRowEventListener();
    }

    @Bean
    public EntityParentsService getEntityParentsService() {
        return new EntityParentsServiceImpl();
    }

    @Bean
    public OperateRowEventHandler getEntityParentsUpdateHandler() {
        return new OperateRowEventHandlerImpl();
    }

    @Bean
    public EntityResetCacheService getEntityResetCacheService() {
        return new EntityResetCacheServiceImpl();
    }

    @Bean
    public TwoKeyTempTableMeta getKeyValueTempTableMeta() {
        return new TwoKeyTempTableMeta();
    }

    @Bean
    public AccountTempTable getAccountTempTable() {
        return new AccountTempTable();
    }

    @Bean
    public TempTableMeta getTempTableMeta() {
        return new TempTableMeta();
    }
}

