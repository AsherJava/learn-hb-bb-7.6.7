/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskBufferQueue
 *  com.jiuqi.np.asynctask.ParamConverter
 *  com.jiuqi.np.core.application.NpApplication
 */
package com.jiuqi.np.asynctask.config;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskBufferQueue;
import com.jiuqi.np.asynctask.ParamConverter;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.dao.DBQueueDao;
import com.jiuqi.np.asynctask.impl.dao.AsyncTaskDaoImpl;
import com.jiuqi.np.asynctask.impl.dao.DBQueueDaoImpl;
import com.jiuqi.np.asynctask.impl.option.AsyncTaskParallelDeclare;
import com.jiuqi.np.asynctask.impl.option.AsyncTaskQueueDeclare;
import com.jiuqi.np.asynctask.impl.service.AsyncTaskManagerImpl;
import com.jiuqi.np.asynctask.impl.service.DBAsyncTaskBufferQueue;
import com.jiuqi.np.asynctask.impl.service.SimpleAsyncTaskDispatcher;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.application.NpApplication;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.np.asynctask.impl"})
public class AsyncTaskConfiguration
implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskConfiguration.class);
    @Autowired
    private NpApplication npApplication;
    private String serverName;
    @Value(value="${jiuqi.np.asynctask.executor-serverid:NULL}")
    private String SERVE_ID;
    @Value(value="${jiuqi.np.asynctask.immediately-pool-core-size:20}")
    private Integer immediatelyPoolCoreSize;
    @Value(value="${jiuqi.np.asynctask.immediately-pool-max-size:100}")
    private Integer immediatelyPoolMaxSize;
    @Value(value="${jiuqi.np.asynctask.immediately-pool-blockingqueue-size:10}")
    private Integer immediatelyPoolBlockingQueueSize;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.serverName = this.npApplication.getApplicationName();
        if (StringUtils.isEmpty((String)this.serverName)) {
            this.serverName = DistributionManager.getInstance().getIp().replace("-", "@");
        }
        if (this.SERVE_ID == null || this.SERVE_ID.length() == 0 || this.SERVE_ID.equals("NULL")) {
            this.SERVE_ID = UUID.randomUUID().toString();
        }
    }

    @Bean
    public SimpleAsyncTaskDispatcher asyncTaskDispatcher() {
        String serveId = String.join((CharSequence)":", this.SERVE_ID, this.serverName);
        return new SimpleAsyncTaskDispatcher(this.serverName, serveId);
    }

    @Bean
    public AsyncTaskDao asyncTaskDao() {
        return new AsyncTaskDaoImpl(this.serverName);
    }

    @Bean
    public ParamConverter asyncTaskParamConverter() {
        return new SimpleParamConverter();
    }

    @Bean
    public AsyncTaskBufferQueue asyncTaskBufferQueue() {
        return new DBAsyncTaskBufferQueue();
    }

    @Bean
    public AsyncTaskManagerImpl asyncTaskManager() {
        String concatId = String.join((CharSequence)":", this.SERVE_ID, this.serverName);
        return new AsyncTaskManagerImpl(concatId, this.immediatelyPoolCoreSize, this.immediatelyPoolMaxSize, this.immediatelyPoolBlockingQueueSize);
    }

    @Bean
    public DBQueueDao dBQueueDao() {
        return new DBQueueDaoImpl(this.serverName);
    }

    @Bean
    @ConditionalOnProperty(name={"jiuqi.np.asynctask.nr-asynctask-frame.enable"}, havingValue="true")
    public AsyncTaskParallelDeclare asyncTaskParallelDeclare() {
        return new AsyncTaskParallelDeclare();
    }

    @Bean
    @ConditionalOnProperty(name={"jiuqi.np.asynctask.nr-asynctask-frame.enable"}, havingValue="true")
    public AsyncTaskQueueDeclare asyncTaskQueueDeclare() {
        return new AsyncTaskQueueDeclare();
    }
}

