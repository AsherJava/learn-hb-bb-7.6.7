/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.support;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ConnectionIdManager;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.listener.MethodExecutionListener;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.listener.TracingMethodListener;
import net.ttddyy.dsproxy.listener.lifecycle.JdbcLifecycleEventExecutionListener;
import net.ttddyy.dsproxy.listener.lifecycle.JdbcLifecycleEventListener;
import net.ttddyy.dsproxy.proxy.DefaultConnectionIdManager;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.RepeatableReadResultSetProxyLogicFactory;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogicFactory;
import net.ttddyy.dsproxy.support.ProxyDataSource;

public class ProxyDataSourceBuilder {
    private DataSource dataSource;
    private String dataSourceName;
    private boolean createTracingMethodListener;
    private TracingMethodListener.TracingCondition tracingCondition;
    private TracingMethodListener.TracingMessageConsumer tracingMessageConsumer;
    private boolean retrieveIsolation;
    private List<QueryExecutionListener> queryExecutionListeners = new ArrayList<QueryExecutionListener>();
    private JdbcProxyFactory jdbcProxyFactory;
    private ConnectionIdManager connectionIdManager;
    private ResultSetProxyLogicFactory resultSetProxyLogicFactory;
    private boolean autoRetrieveGeneratedKeys;
    private Boolean retrieveGeneratedKeysForBatchStatement;
    private Boolean retrieveGeneratedKeysForBatchPreparedOrCallable;
    private boolean autoCloseGeneratedKeys;
    private ResultSetProxyLogicFactory generatedKeysProxyLogicFactory;
    private List<MethodExecutionListener> methodExecutionListeners = new ArrayList<MethodExecutionListener>();

    public static ProxyDataSourceBuilder create() {
        return new ProxyDataSourceBuilder();
    }

    public static ProxyDataSourceBuilder create(DataSource dataSource) {
        return new ProxyDataSourceBuilder(dataSource);
    }

    public static ProxyDataSourceBuilder create(String dataSourceName, DataSource dataSource) {
        return new ProxyDataSourceBuilder(dataSource).name(dataSourceName);
    }

    public ProxyDataSourceBuilder() {
    }

    public ProxyDataSourceBuilder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ProxyDataSourceBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public ProxyDataSourceBuilder listener(QueryExecutionListener listener) {
        this.queryExecutionListeners.add(listener);
        return this;
    }

    public ProxyDataSourceBuilder listener(JdbcLifecycleEventListener listener) {
        JdbcLifecycleEventExecutionListener executionListener = new JdbcLifecycleEventExecutionListener(listener);
        this.queryExecutionListeners.add(executionListener);
        this.methodExecutionListeners.add(executionListener);
        return this;
    }

    public ProxyDataSourceBuilder name(String dataSourceName) {
        this.dataSourceName = dataSourceName;
        return this;
    }

    public ProxyDataSourceBuilder retrieveIsolation() {
        this.retrieveIsolation = true;
        return this;
    }

    public ProxyDataSourceBuilder jdbcProxyFactory(JdbcProxyFactory jdbcProxyFactory) {
        this.jdbcProxyFactory = jdbcProxyFactory;
        return this;
    }

    public ProxyDataSourceBuilder connectionIdManager(ConnectionIdManager connectionIdManager) {
        this.connectionIdManager = connectionIdManager;
        return this;
    }

    public ProxyDataSourceBuilder proxyResultSet() {
        this.resultSetProxyLogicFactory = ResultSetProxyLogicFactory.DEFAULT;
        return this;
    }

    public ProxyDataSourceBuilder proxyResultSet(ResultSetProxyLogicFactory factory) {
        this.resultSetProxyLogicFactory = factory;
        return this;
    }

    public ProxyDataSourceBuilder proxyGeneratedKeys() {
        this.generatedKeysProxyLogicFactory = ResultSetProxyLogicFactory.DEFAULT;
        return this;
    }

    public ProxyDataSourceBuilder proxyGeneratedKeys(ResultSetProxyLogicFactory factory) {
        this.generatedKeysProxyLogicFactory = factory;
        return this;
    }

    public ProxyDataSourceBuilder repeatableReadGeneratedKeys() {
        this.generatedKeysProxyLogicFactory = new RepeatableReadResultSetProxyLogicFactory();
        return this;
    }

    public ProxyDataSourceBuilder autoRetrieveGeneratedKeys(boolean autoClose) {
        this.autoRetrieveGeneratedKeys = true;
        this.autoCloseGeneratedKeys = autoClose;
        return this;
    }

    public ProxyDataSourceBuilder autoRetrieveGeneratedKeys(boolean autoClose, ResultSetProxyLogicFactory factory) {
        this.autoRetrieveGeneratedKeys = true;
        this.autoCloseGeneratedKeys = autoClose;
        this.generatedKeysProxyLogicFactory = factory;
        return this;
    }

    public ProxyDataSourceBuilder autoRetrieveGeneratedKeysWithRepeatableReadProxy(boolean autoClose) {
        this.autoRetrieveGeneratedKeys = true;
        this.autoCloseGeneratedKeys = autoClose;
        this.generatedKeysProxyLogicFactory = new RepeatableReadResultSetProxyLogicFactory();
        return this;
    }

    public ProxyDataSourceBuilder retrieveGeneratedKeysForBatch(boolean forStatement, boolean forPreparedOrCallable) {
        this.retrieveGeneratedKeysForBatchStatement = forStatement;
        this.retrieveGeneratedKeysForBatchPreparedOrCallable = forPreparedOrCallable;
        return this;
    }

    public ProxyDataSourceBuilder repeatableReadResultSet() {
        this.resultSetProxyLogicFactory = new RepeatableReadResultSetProxyLogicFactory();
        return this;
    }

    public ProxyDataSourceBuilder methodListener(MethodExecutionListener listener) {
        this.methodExecutionListeners.add(listener);
        return this;
    }

    public ProxyDataSourceBuilder beforeMethod(final SingleMethodExecution callback) {
        MethodExecutionListener listener = new MethodExecutionListener(){

            @Override
            public void beforeMethod(MethodExecutionContext executionContext) {
                callback.execute(executionContext);
            }
        };
        this.methodExecutionListeners.add(listener);
        return this;
    }

    public ProxyDataSourceBuilder afterMethod(final SingleMethodExecution callback) {
        MethodExecutionListener listener = new MethodExecutionListener(){

            @Override
            public void afterMethod(MethodExecutionContext executionContext) {
                callback.execute(executionContext);
            }
        };
        this.methodExecutionListeners.add(listener);
        return this;
    }

    public ProxyDataSourceBuilder traceMethods() {
        this.createTracingMethodListener = true;
        return this;
    }

    public ProxyDataSourceBuilder traceMethods(TracingMethodListener.TracingMessageConsumer messageConsumer) {
        this.createTracingMethodListener = true;
        this.tracingMessageConsumer = messageConsumer;
        return this;
    }

    public ProxyDataSourceBuilder traceMethodsWhen(TracingMethodListener.TracingCondition condition) {
        this.createTracingMethodListener = true;
        this.tracingCondition = condition;
        return this;
    }

    public ProxyDataSourceBuilder traceMethodsWhen(TracingMethodListener.TracingCondition condition, TracingMethodListener.TracingMessageConsumer messageConsumer) {
        this.createTracingMethodListener = true;
        this.tracingCondition = condition;
        this.tracingMessageConsumer = messageConsumer;
        return this;
    }

    public DataSource buildProxy() {
        ProxyConfig proxyConfig = this.buildProxyConfig();
        return proxyConfig.getJdbcProxyFactory().createDataSource(this.dataSource, proxyConfig);
    }

    public ProxyDataSource build() {
        ProxyConfig proxyConfig = this.buildProxyConfig();
        ProxyDataSource proxyDataSource = new ProxyDataSource();
        if (this.dataSource != null) {
            proxyDataSource.setDataSource(this.dataSource);
        }
        proxyDataSource.setProxyConfig(proxyConfig);
        return proxyDataSource;
    }

    private ProxyConfig buildProxyConfig() {
        ArrayList<QueryExecutionListener> listeners = new ArrayList<QueryExecutionListener>();
        if (this.createTracingMethodListener) {
            this.methodExecutionListeners.add(this.buildTracingMethodListener());
        }
        listeners.addAll(this.queryExecutionListeners);
        ProxyConfig.Builder proxyConfigBuilder = ProxyConfig.Builder.create();
        for (QueryExecutionListener listener : listeners) {
            proxyConfigBuilder.queryListener(listener);
        }
        for (MethodExecutionListener methodListener : this.methodExecutionListeners) {
            proxyConfigBuilder.methodListener(methodListener);
        }
        if (this.dataSourceName != null) {
            proxyConfigBuilder.dataSourceName(this.dataSourceName);
        }
        if (this.jdbcProxyFactory != null) {
            proxyConfigBuilder.jdbcProxyFactory(this.jdbcProxyFactory);
        } else {
            proxyConfigBuilder.jdbcProxyFactory(JdbcProxyFactory.DEFAULT);
        }
        if (this.connectionIdManager != null) {
            proxyConfigBuilder.connectionIdManager(this.connectionIdManager);
        } else {
            proxyConfigBuilder.connectionIdManager(new DefaultConnectionIdManager());
        }
        proxyConfigBuilder.resultSetProxyLogicFactory(this.resultSetProxyLogicFactory);
        proxyConfigBuilder.autoRetrieveGeneratedKeys(this.autoRetrieveGeneratedKeys);
        if (this.retrieveGeneratedKeysForBatchStatement != null) {
            proxyConfigBuilder.retrieveGeneratedKeysForBatchStatement(this.retrieveGeneratedKeysForBatchStatement);
        }
        if (this.retrieveGeneratedKeysForBatchPreparedOrCallable != null) {
            proxyConfigBuilder.retrieveGeneratedKeysForBatchPreparedOrCallable(this.retrieveGeneratedKeysForBatchPreparedOrCallable);
        }
        proxyConfigBuilder.autoCloseGeneratedKeys(this.autoCloseGeneratedKeys);
        proxyConfigBuilder.generatedKeysProxyLogicFactory(this.generatedKeysProxyLogicFactory);
        proxyConfigBuilder.retrieveIsolationLevel(this.retrieveIsolation);
        return proxyConfigBuilder.build();
    }

    private TracingMethodListener buildTracingMethodListener() {
        TracingMethodListener listener = new TracingMethodListener();
        if (this.tracingMessageConsumer != null) {
            listener.setTracingMessageConsumer(this.tracingMessageConsumer);
        }
        if (this.tracingCondition != null) {
            listener.setTracingCondition(this.tracingCondition);
        }
        return listener;
    }

    public static interface FormatQueryCallback {
        public String format(String var1);
    }

    public static interface SingleMethodExecution {
        public void execute(MethodExecutionContext var1);
    }
}

