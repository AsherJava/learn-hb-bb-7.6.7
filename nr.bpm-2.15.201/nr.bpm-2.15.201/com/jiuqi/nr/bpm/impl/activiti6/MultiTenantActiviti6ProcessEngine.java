/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl
 *  org.activiti.spring.ProcessEngineFactoryBean
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.transaction.PlatformTransactionManager
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.MultiTenantProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.common.ProcessProvider;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.activiti6.Activiti6ProcessEngine;
import com.jiuqi.nr.bpm.impl.activiti6.NrProcessEngineConfiguration;
import com.jiuqi.nr.bpm.impl.activiti6.config.ActivitiEngineConfigurationConfigurer;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.sql.DataSource;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@Primary
class MultiTenantActiviti6ProcessEngine
extends MultiTenantProcessEngine
implements DisposableBean,
ApplicationContextAware {
    private final ConcurrentMap<String, Activiti6ProcessEngine> innerEngines = new ConcurrentHashMap<String, Activiti6ProcessEngine>();
    private ApplicationContext applicationContext;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired(required=false)
    private EventDispatcher actionEventHandler;
    @Autowired
    private List<ProcessProvider> processProviders;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private List<IConditionalExecute> conditionalExecutes;
    @Autowired(required=false)
    private PlatformTransactionManager jdbcTransactionManager;
    @Autowired
    private ActivitiEngineConfigurationConfigurer activitiEngineConfigurationConfigurer;

    MultiTenantActiviti6ProcessEngine() {
    }

    @Override
    public ProcessEngine.ProcessEngineType getType() {
        return ProcessEngine.ProcessEngineType.ACTIVITI;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected ProcessEngine getCurrentProcessEngine() {
        Activiti6ProcessEngine engineFound = (Activiti6ProcessEngine)this.innerEngines.get("__default_tenant__");
        if (engineFound != null) {
            return engineFound;
        }
        MultiTenantActiviti6ProcessEngine multiTenantActiviti6ProcessEngine = this;
        synchronized (multiTenantActiviti6ProcessEngine) {
            engineFound = (Activiti6ProcessEngine)this.innerEngines.get("__default_tenant__");
            if (engineFound != null) {
                return engineFound;
            }
            Activiti6ProcessEngine newEngine = this.buildActiviti6ProcessEngine();
            Activiti6ProcessEngine existEngine = this.innerEngines.putIfAbsent("__default_tenant__", newEngine);
            if (existEngine == null) {
                engineFound = newEngine;
            } else {
                newEngine.close();
                engineFound = existEngine;
            }
        }
        return engineFound;
    }

    protected Activiti6ProcessEngine buildActiviti6ProcessEngine() {
        try {
            ProcessEngineFactoryBean engineFactory = new ProcessEngineFactoryBean();
            engineFactory.setApplicationContext(this.applicationContext);
            NrProcessEngineConfiguration cfg = new NrProcessEngineConfiguration();
            engineFactory.setProcessEngineConfiguration((ProcessEngineConfigurationImpl)cfg);
            this.buildDataSource(cfg);
            String dbType = this.getDataBaseType();
            if (null != dbType) {
                cfg.setDatabaseType(dbType);
                this.activitiEngineConfigurationConfigurer.configure(cfg);
            }
            return new Activiti6ProcessEngine(engineFactory.getObject()).setActorStrategyProvider(this.actorStrategyProvider).setUserActionEventHandler(this.actionEventHandler).setProcessProviders(this.processProviders).setNrParameterUtils(this.nrParameterUtils).setConditionalExecute(this.conditionalExecutes);
        }
        catch (Exception e) {
            throw new BpmException("\u521d\u59cb\u5316Activit\u5f15\u64ce\u5931\u8d25\u3002", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getDataBaseType() throws SQLException {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)dataSource);
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"GAUSSDB100")) {
                String string = "gaussdb100";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"DM")) {
                String string = "dameng";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"Informix") || DataEngineUtil.isDataBase((Connection)conn, (String)"GBase8s")) {
                String string = "gbase";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"OSCAR")) {
                String string = "oscar";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"HANA")) {
                String string = "hana";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"kingbase8") || DataEngineUtil.isDataBase((Connection)conn, (String)"kingbase")) {
                String string = "kingbase";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"DERBY")) {
                String string = "derby";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"GaussDB")) {
                String string = "gaussdb";
                return string;
            }
            if (DataEngineUtil.isDataBase((Connection)conn, (String)"polardb")) {
                String string = "polardb";
                return string;
            }
            String string = null;
            return string;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)dataSource);
        }
    }

    private void buildDataSource(NrProcessEngineConfiguration cfg) throws SQLException {
        PlatformTransactionManager transactionManager = this.jdbcTransactionManager;
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        if (dataSource == null) {
            throw new BpmException("\u521d\u59cb\u5316Activit\u5f15\u64ce\u5931\u8d25\uff0c\u672a\u80fd\u83b7\u53d6\u6570\u636e\u6e90\u3002");
        }
        cfg.setDataSource(dataSource);
        cfg.setTransactionManager(transactionManager);
    }

    @Override
    public void destroy() throws Exception {
        for (Activiti6ProcessEngine engine : this.innerEngines.values()) {
            engine.close();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

