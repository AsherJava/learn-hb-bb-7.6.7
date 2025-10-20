/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import net.ttddyy.dsproxy.ConnectionIdManager;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.CompositeMethodListener;
import net.ttddyy.dsproxy.listener.MethodExecutionListener;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.DefaultConnectionIdManager;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogicFactory;
import net.ttddyy.dsproxy.proxy.StopwatchFactory;
import net.ttddyy.dsproxy.proxy.SystemStopwatchFactory;
import net.ttddyy.dsproxy.transform.ParameterTransformer;
import net.ttddyy.dsproxy.transform.QueryTransformer;

public class ProxyConfig {
    private String dataSourceName;
    private ChainListener queryListener;
    private QueryTransformer queryTransformer;
    private ParameterTransformer parameterTransformer;
    private JdbcProxyFactory jdbcProxyFactory;
    private ResultSetProxyLogicFactory resultSetProxyLogicFactory;
    private ConnectionIdManager connectionIdManager;
    private CompositeMethodListener methodListener;
    private GeneratedKeysConfig generatedKeysConfig = new GeneratedKeysConfig();
    private StopwatchFactory stopwatchFactory;
    private boolean retrieveIsolationLevel;

    public String getDataSourceName() {
        return this.dataSourceName;
    }

    public ChainListener getQueryListener() {
        return this.queryListener;
    }

    public QueryTransformer getQueryTransformer() {
        return this.queryTransformer;
    }

    public ParameterTransformer getParameterTransformer() {
        return this.parameterTransformer;
    }

    public JdbcProxyFactory getJdbcProxyFactory() {
        return this.jdbcProxyFactory;
    }

    public ResultSetProxyLogicFactory getResultSetProxyLogicFactory() {
        return this.resultSetProxyLogicFactory;
    }

    public boolean isResultSetProxyEnabled() {
        return this.resultSetProxyLogicFactory != null;
    }

    public ResultSetProxyLogicFactory getGeneratedKeysProxyLogicFactory() {
        return this.generatedKeysConfig.proxyLogicFactory;
    }

    public boolean isAutoRetrieveGeneratedKeys() {
        return this.generatedKeysConfig.autoRetrieve;
    }

    public boolean isGeneratedKeysProxyEnabled() {
        return this.generatedKeysConfig.proxyLogicFactory != null;
    }

    public boolean isAutoCloseGeneratedKeys() {
        return this.generatedKeysConfig.autoClose;
    }

    public boolean isRetrieveGeneratedKeysForBatchStatement() {
        return this.generatedKeysConfig.retrieveForBatchStatement;
    }

    public boolean isRetrieveGeneratedKeysForBatchPreparedOrCallable() {
        return this.generatedKeysConfig.retrieveForBatchPreparedOrCallable;
    }

    public ConnectionIdManager getConnectionIdManager() {
        return this.connectionIdManager;
    }

    public CompositeMethodListener getMethodListener() {
        return this.methodListener;
    }

    public StopwatchFactory getStopwatchFactory() {
        return this.stopwatchFactory;
    }

    public boolean isRetrieveIsolationLevel() {
        return this.retrieveIsolationLevel;
    }

    public void setRetrieveIsolationLevel(boolean retrieveIsolationLevel) {
        this.retrieveIsolationLevel = retrieveIsolationLevel;
    }

    public static class Builder {
        private String dataSourceName = "";
        private ChainListener queryListener = new ChainListener();
        private QueryTransformer queryTransformer = QueryTransformer.DEFAULT;
        private ParameterTransformer parameterTransformer = new ParameterTransformer(){};
        private JdbcProxyFactory jdbcProxyFactory = JdbcProxyFactory.DEFAULT;
        private ResultSetProxyLogicFactory resultSetProxyLogicFactory;
        private ConnectionIdManager connectionIdManager = new DefaultConnectionIdManager();
        private CompositeMethodListener methodListener = new CompositeMethodListener();
        private GeneratedKeysConfig generatedKeysConfig = new GeneratedKeysConfig();
        private StopwatchFactory stopwatchFactory = new SystemStopwatchFactory();
        private boolean retrieveIsolationLevel;

        public static Builder create() {
            return new Builder();
        }

        public static Builder from(ProxyConfig proxyConfig) {
            return new Builder().dataSourceName(proxyConfig.dataSourceName).queryListener(proxyConfig.queryListener).queryTransformer(proxyConfig.queryTransformer).parameterTransformer(proxyConfig.parameterTransformer).jdbcProxyFactory(proxyConfig.jdbcProxyFactory).resultSetProxyLogicFactory(proxyConfig.resultSetProxyLogicFactory).connectionIdManager(proxyConfig.connectionIdManager).methodListener(proxyConfig.methodListener).stopwatchFactory(proxyConfig.stopwatchFactory).generatedKeysProxyLogicFactory(proxyConfig.generatedKeysConfig.proxyLogicFactory).autoRetrieveGeneratedKeys(proxyConfig.generatedKeysConfig.autoRetrieve).retrieveGeneratedKeysForBatchStatement(proxyConfig.generatedKeysConfig.retrieveForBatchStatement).retrieveGeneratedKeysForBatchPreparedOrCallable(proxyConfig.generatedKeysConfig.retrieveForBatchPreparedOrCallable).autoCloseGeneratedKeys(proxyConfig.generatedKeysConfig.autoClose).retrieveIsolationLevel(proxyConfig.retrieveIsolationLevel);
        }

        public ProxyConfig build() {
            ProxyConfig proxyConfig = new ProxyConfig();
            proxyConfig.dataSourceName = this.dataSourceName;
            proxyConfig.queryListener = this.queryListener;
            proxyConfig.queryTransformer = this.queryTransformer;
            proxyConfig.parameterTransformer = this.parameterTransformer;
            proxyConfig.jdbcProxyFactory = this.jdbcProxyFactory;
            proxyConfig.resultSetProxyLogicFactory = this.resultSetProxyLogicFactory;
            proxyConfig.connectionIdManager = this.connectionIdManager;
            proxyConfig.methodListener = this.methodListener;
            proxyConfig.stopwatchFactory = this.stopwatchFactory;
            proxyConfig.retrieveIsolationLevel = this.retrieveIsolationLevel;
            proxyConfig.generatedKeysConfig.proxyLogicFactory = this.generatedKeysConfig.proxyLogicFactory;
            proxyConfig.generatedKeysConfig.autoRetrieve = this.generatedKeysConfig.autoRetrieve;
            proxyConfig.generatedKeysConfig.autoClose = this.generatedKeysConfig.autoClose;
            proxyConfig.generatedKeysConfig.retrieveForBatchStatement = this.generatedKeysConfig.retrieveForBatchStatement;
            proxyConfig.generatedKeysConfig.retrieveForBatchPreparedOrCallable = this.generatedKeysConfig.retrieveForBatchPreparedOrCallable;
            return proxyConfig;
        }

        public Builder dataSourceName(String dataSourceName) {
            this.dataSourceName = dataSourceName;
            return this;
        }

        public Builder queryListener(QueryExecutionListener queryListener) {
            if (queryListener instanceof ChainListener) {
                for (QueryExecutionListener listener : ((ChainListener)queryListener).getListeners()) {
                    this.queryListener.addListener(listener);
                }
            } else {
                this.queryListener.addListener(queryListener);
            }
            return this;
        }

        public Builder queryTransformer(QueryTransformer queryTransformer) {
            this.queryTransformer = queryTransformer;
            return this;
        }

        public Builder parameterTransformer(ParameterTransformer parameterTransformer) {
            this.parameterTransformer = parameterTransformer;
            return this;
        }

        public Builder jdbcProxyFactory(JdbcProxyFactory jdbcProxyFactory) {
            this.jdbcProxyFactory = jdbcProxyFactory;
            return this;
        }

        public Builder resultSetProxyLogicFactory(ResultSetProxyLogicFactory resultSetProxyLogicFactory) {
            this.resultSetProxyLogicFactory = resultSetProxyLogicFactory;
            return this;
        }

        public Builder autoRetrieveGeneratedKeys(boolean autoRetrieveGeneratedKeys) {
            this.generatedKeysConfig.autoRetrieve = autoRetrieveGeneratedKeys;
            return this;
        }

        public Builder autoCloseGeneratedKeys(boolean autoCloseGeneratedKeys) {
            this.generatedKeysConfig.autoClose = autoCloseGeneratedKeys;
            return this;
        }

        public Builder retrieveGeneratedKeysForBatchStatement(boolean retrieveForBatchStatement) {
            this.generatedKeysConfig.retrieveForBatchStatement = retrieveForBatchStatement;
            return this;
        }

        public Builder retrieveGeneratedKeysForBatchPreparedOrCallable(boolean retrieveForBatchPreparedOrCallable) {
            this.generatedKeysConfig.retrieveForBatchPreparedOrCallable = retrieveForBatchPreparedOrCallable;
            return this;
        }

        public Builder generatedKeysProxyLogicFactory(ResultSetProxyLogicFactory generatedKeysProxyLogicFactory) {
            this.generatedKeysConfig.proxyLogicFactory = generatedKeysProxyLogicFactory;
            return this;
        }

        public Builder connectionIdManager(ConnectionIdManager connectionIdManager) {
            this.connectionIdManager = connectionIdManager;
            return this;
        }

        public Builder methodListener(MethodExecutionListener methodListener) {
            if (methodListener instanceof CompositeMethodListener) {
                for (MethodExecutionListener listener : ((CompositeMethodListener)methodListener).getListeners()) {
                    this.methodListener.addListener(listener);
                }
            } else {
                this.methodListener.addListener(methodListener);
            }
            return this;
        }

        public Builder stopwatchFactory(StopwatchFactory stopwatchFactory) {
            this.stopwatchFactory = stopwatchFactory;
            return this;
        }

        public Builder retrieveIsolationLevel(boolean retrieveIsolationLevel) {
            this.retrieveIsolationLevel = retrieveIsolationLevel;
            return this;
        }
    }

    private static class GeneratedKeysConfig {
        private ResultSetProxyLogicFactory proxyLogicFactory;
        private boolean autoRetrieve;
        private boolean retrieveForBatchStatement = false;
        private boolean retrieveForBatchPreparedOrCallable = true;
        private boolean autoClose;

        private GeneratedKeysConfig() {
        }
    }
}

