/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator$MachineInfo
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.core.nodekeeper.IApplicationDetailProvider
 *  com.jiuqi.bi.core.nodekeeper.IConnectionProvider
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.bi.sql.IConnectionProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.type.GUID
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.redis.core.RedisOperations
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.core.SessionCallback
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.bi.authz.licence.MachineCodeGenerator;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.IApplicationDetailProvider;
import com.jiuqi.bi.core.nodekeeper.IConnectionProvider;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.NvwaDynamicConnectionProvicer;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StartUp {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate<String, String> rt;

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        this.initFramework(applicationContext);
    }

    public void initFramework(ApplicationContext applicationContext) {
        Framework framework = Framework.getInstance();
        this.setFrameworkEnv(applicationContext);
        try {
            framework.init();
        }
        catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }
    }

    public void registNode(ApplicationContext applicationContext) {
        Framework framework = Framework.getInstance();
        this.setFrameworkEnv(applicationContext);
        try {
            framework.registNode();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setFrameworkEnv(final ApplicationContext applicationContext) {
        Framework framework = Framework.getInstance();
        GlobalConnectionProviderManager.setConnectionProvider((IConnectionProvider)new IConnectionProvider(){

            public Connection openConnection() throws SQLException {
                return StartUp.this.jdbcTemplate.getDataSource().getConnection();
            }

            public Connection openHostedConnection() throws SQLException {
                return StartUp.this.jdbcTemplate.getDataSource().getConnection();
            }
        });
        framework.setConnectionProvider(() -> this.jdbcTemplate.getDataSource().getConnection());
        TempTableProviderFactory.getInstance().setConnectionProvider((com.jiuqi.bi.sql.IConnectionProvider)new NvwaDynamicConnectionProvicer());
        if (!"false".equalsIgnoreCase(applicationContext.getEnvironment().getProperty("spring.redis.enabled"))) {
            framework.setGroupProvider(() -> (String)this.rt.execute((SessionCallback)new SessionCallback<String>(){

                public String execute(RedisOperations oper) throws DataAccessException {
                    oper.opsForValue().setIfAbsent((Object)"__redis_node_group", (Object)GUID.newGUID());
                    return (String)oper.opsForValue().get((Object)"__redis_node_group");
                }
            }));
        }
        framework.setApplicationDetailProvider(new IApplicationDetailProvider(){

            public String getName() {
                return applicationContext.getEnvironment().getProperty("spring.application.name");
            }

            public String getMachineCode() {
                try {
                    if (StringUtils.isEmpty((String)Framework.getInstance().getProductId())) {
                        Framework.getInstance().loadProductFile();
                    }
                    MachineCodeGenerator.MachineInfo machineCode = FrameworkOperator.getMachineCode(Framework.getInstance().getProductId());
                    return machineCode.getMachineCode();
                }
                catch (Exception e) {
                    LoggerFactory.getLogger(this.getClass()).error("\u673a\u5668\u7801\u83b7\u53d6\u5931\u8d25\uff1a" + e.getMessage(), e);
                    return super.getMachineCode();
                }
            }
        });
    }
}

