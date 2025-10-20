/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.StatementType;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.proxy.GeneratedKeysUtils;
import net.ttddyy.dsproxy.proxy.JdbcProxyFactory;
import net.ttddyy.dsproxy.proxy.ParameterKey;
import net.ttddyy.dsproxy.proxy.ParameterSetOperation;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;
import net.ttddyy.dsproxy.proxy.StatementMethodNames;
import net.ttddyy.dsproxy.proxy.Stopwatch;
import net.ttddyy.dsproxy.transform.ParameterReplacer;
import net.ttddyy.dsproxy.transform.ParameterTransformer;
import net.ttddyy.dsproxy.transform.QueryTransformer;
import net.ttddyy.dsproxy.transform.TransformInfo;

public class StatementProxyLogic
extends ProxyLogicSupport {
    private Statement statement;
    private StatementType statementType;
    private String query;
    private ConnectionInfo connectionInfo;
    private Map<ParameterKey, ParameterSetOperation> parameters = new LinkedHashMap<ParameterKey, ParameterSetOperation>();
    private List<String> batchQueries = new ArrayList<String>();
    private List<Map<ParameterKey, ParameterSetOperation>> batchParameters = new ArrayList<Map<ParameterKey, ParameterSetOperation>>();
    private Connection proxyConnection;
    private ProxyConfig proxyConfig;
    private ResultSet generatedKeys;
    private boolean generateKey;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.proceedMethodExecution(this.proxyConfig, this.statement, this.connectionInfo, proxy, method, args);
    }

    @Override
    protected Object performProxyLogic(Object proxy, Method method, Object[] args, MethodExecutionContext methodContext) throws Throwable {
        boolean performQueryListener;
        QueryInfo queryInfo;
        String methodName = method.getName();
        if (!StatementMethodNames.METHODS_TO_INTERCEPT.contains(methodName)) {
            return this.proceedExecution(method, this.statement, args);
        }
        QueryTransformer queryTransformer = this.proxyConfig.getQueryTransformer();
        ParameterTransformer parameterTransformer = this.proxyConfig.getParameterTransformer();
        ChainListener queryListener = this.proxyConfig.getQueryListener();
        JdbcProxyFactory proxyFactory = this.proxyConfig.getJdbcProxyFactory();
        if (this.isCommonMethod(methodName)) {
            return this.handleCommonMethod(methodName, this.statement, this.proxyConfig, args);
        }
        if ("getConnection".equals(methodName)) {
            return this.proxyConnection;
        }
        if (StatementType.STATEMENT == this.statementType) {
            if ("addBatch".equals(methodName) || "clearBatch".equals(methodName)) {
                if ("addBatch".equals(methodName)) {
                    String query = (String)args[0];
                    Class<Statement> clazz = Statement.class;
                    int batchCount = this.batchQueries.size();
                    TransformInfo transformInfo = new TransformInfo(clazz, this.connectionInfo.getDataSourceName(), query, true, batchCount);
                    String string = queryTransformer.transformQuery(transformInfo);
                    args[0] = string;
                    this.batchQueries.add(string);
                } else {
                    this.batchQueries.clear();
                }
                return this.proceedExecution(method, this.statement, args);
            }
        } else {
            PreparedStatement ps = (PreparedStatement)this.statement;
            if (StatementMethodNames.METHODS_TO_OPERATE_PARAMETER.contains(methodName)) {
                if (StatementMethodNames.PARAMETER_METHODS.contains(methodName)) {
                    if ("clearParameters".equals(methodName)) {
                        this.parameters.clear();
                    } else {
                        ParameterKey parameterKey;
                        if (args[0] instanceof Integer) {
                            parameterKey = new ParameterKey((Integer)args[0]);
                        } else if (args[0] instanceof String) {
                            parameterKey = new ParameterKey((String)args[0]);
                        } else {
                            return this.proceedExecution(method, ps, args);
                        }
                        this.parameters.put(parameterKey, new ParameterSetOperation(method, args));
                    }
                } else if (StatementMethodNames.BATCH_PARAM_METHODS.contains(methodName)) {
                    if ("addBatch".equals(methodName)) {
                        this.transformParameters(parameterTransformer, ps, true, this.batchParameters.size());
                        LinkedHashMap<ParameterKey, ParameterSetOperation> newParams = new LinkedHashMap<ParameterKey, ParameterSetOperation>(this.parameters);
                        this.batchParameters.add(newParams);
                        this.parameters.clear();
                    } else if ("clearBatch".equals(methodName)) {
                        this.batchParameters.clear();
                    }
                }
                return this.proceedExecution(method, ps, args);
            }
        }
        ArrayList<QueryInfo> queries = new ArrayList<QueryInfo>();
        boolean isBatchExecution = StatementMethodNames.BATCH_EXEC_METHODS.contains(methodName);
        int batchSize = 0;
        if (isBatchExecution) {
            if (StatementType.STATEMENT == this.statementType) {
                for (String string : this.batchQueries) {
                    queries.add(new QueryInfo(string));
                }
                batchSize = this.batchQueries.size();
                this.batchQueries.clear();
            } else {
                queryInfo = new QueryInfo(this.query);
                for (Map<ParameterKey, ParameterSetOperation> map : this.batchParameters) {
                    queryInfo.getParametersList().add(new ArrayList<ParameterSetOperation>(map.values()));
                }
                queries.add(queryInfo);
                batchSize = this.batchParameters.size();
                this.batchParameters.clear();
            }
        } else if (StatementMethodNames.QUERY_EXEC_METHODS.contains(methodName)) {
            if (StatementType.STATEMENT == this.statementType) {
                String string = (String)args[0];
                TransformInfo transformInfo = new TransformInfo(Statement.class, this.connectionInfo.getDataSourceName(), string, false, 0);
                String transformedQuery = queryTransformer.transformQuery(transformInfo);
                args[0] = transformedQuery;
                queryInfo = new QueryInfo(transformedQuery);
            } else {
                PreparedStatement preparedStatement = (PreparedStatement)this.statement;
                this.transformParameters(parameterTransformer, preparedStatement, false, 0);
                queryInfo = new QueryInfo(this.query);
                queryInfo.getParametersList().add(new ArrayList<ParameterSetOperation>(this.parameters.values()));
            }
            queries.add(queryInfo);
        }
        boolean isGetGeneratedKeysMethod = "getGeneratedKeys".equals(methodName);
        if (isGetGeneratedKeysMethod && this.generatedKeys != null) {
            if (this.generatedKeys.isClosed()) {
                this.generatedKeys = null;
            } else {
                return this.generatedKeys;
            }
        }
        ExecutionInfo executionInfo = new ExecutionInfo(this.connectionInfo, this.statement, isBatchExecution, batchSize, method, args);
        boolean bl = "getResultSet".equals(methodName);
        boolean bl2 = performQueryListener = !isGetGeneratedKeysMethod && !bl;
        if (performQueryListener) {
            queryListener.beforeQuery(executionInfo, queries);
        }
        Stopwatch stopwatch = this.proxyConfig.getStopwatchFactory().create().start();
        try {
            boolean isCreateResultSetProxy;
            Object retVal = method.invoke(this.statement, args);
            long elapsedTime = stopwatch.getElapsedTime();
            boolean isResultSetReturningMethod = !isGetGeneratedKeysMethod && StatementMethodNames.METHODS_TO_RETURN_RESULTSET.contains(methodName);
            boolean isCreateGeneratedKeysProxy = isGetGeneratedKeysMethod && this.proxyConfig.isGeneratedKeysProxyEnabled();
            boolean bl3 = isCreateResultSetProxy = isResultSetReturningMethod && this.proxyConfig.isResultSetProxyEnabled();
            if (isCreateGeneratedKeysProxy) {
                retVal = proxyFactory.createGeneratedKeys((ResultSet)retVal, this.connectionInfo, this.proxyConfig);
            } else if (isCreateResultSetProxy) {
                retVal = proxyFactory.createResultSet((ResultSet)retVal, this.connectionInfo, this.proxyConfig);
            }
            if (this.proxyConfig.isAutoRetrieveGeneratedKeys()) {
                if (isGetGeneratedKeysMethod) {
                    this.generatedKeys = (ResultSet)retVal;
                } else if (GeneratedKeysUtils.isMethodToRetrieveGeneratedKeys(method)) {
                    boolean isTypeStatement;
                    boolean bl4 = isTypeStatement = StatementType.STATEMENT == this.statementType;
                    boolean retrieveGeneratedKey = isBatchExecution ? (isTypeStatement ? this.proxyConfig.isRetrieveGeneratedKeysForBatchStatement() : this.generateKey && this.proxyConfig.isRetrieveGeneratedKeysForBatchPreparedOrCallable()) : (isTypeStatement ? GeneratedKeysUtils.isAutoGenerateEnabledParameters(args) : this.generateKey);
                    if (retrieveGeneratedKey) {
                        ResultSet generatedKeysResultSet = this.statement.getGeneratedKeys();
                        if (this.proxyConfig.isGeneratedKeysProxyEnabled()) {
                            generatedKeysResultSet = proxyFactory.createGeneratedKeys(generatedKeysResultSet, this.connectionInfo, this.proxyConfig);
                        }
                        this.generatedKeys = generatedKeysResultSet;
                    }
                }
            }
            executionInfo.setResult(retVal);
            executionInfo.setGeneratedKeys(this.generatedKeys);
            executionInfo.setElapsedTime(elapsedTime);
            executionInfo.setSuccess(true);
            Object object = retVal;
            return object;
        }
        catch (InvocationTargetException ex) {
            long elapsedTime = stopwatch.getElapsedTime();
            executionInfo.setElapsedTime(elapsedTime);
            executionInfo.setThrowable(ex.getTargetException());
            executionInfo.setSuccess(false);
            throw ex.getTargetException();
        }
        finally {
            if (performQueryListener) {
                queryListener.afterQuery(executionInfo, queries);
            }
            if (!isGetGeneratedKeysMethod && this.proxyConfig.isAutoCloseGeneratedKeys() && this.generatedKeys != null && !this.generatedKeys.isClosed()) {
                this.generatedKeys.close();
                this.generatedKeys = null;
            }
        }
    }

    private void transformParameters(ParameterTransformer parameterTransformer, PreparedStatement ps, boolean isBatch, int count) throws SQLException, IllegalAccessException, InvocationTargetException {
        ParameterReplacer parameterReplacer = new ParameterReplacer(this.parameters);
        TransformInfo transformInfo = new TransformInfo(ps.getClass(), this.connectionInfo.getDataSourceName(), this.query, isBatch, count);
        parameterTransformer.transformParameters(parameterReplacer, transformInfo);
        if (parameterReplacer.isModified()) {
            ps.clearParameters();
            Map<ParameterKey, ParameterSetOperation> modifiedParameters = parameterReplacer.getModifiedParameters();
            for (ParameterSetOperation operation : modifiedParameters.values()) {
                Method paramMethod = operation.getMethod();
                Object[] paramArgs = operation.getArgs();
                paramMethod.invoke(ps, paramArgs);
            }
            this.parameters = modifiedParameters;
        }
    }

    public static class Builder {
        private Statement statement;
        private StatementType statementType;
        private String query;
        private ConnectionInfo connectionInfo;
        private Connection proxyConnection;
        private ProxyConfig proxyConfig;
        private boolean generateKey;

        public static Builder create() {
            return new Builder();
        }

        public StatementProxyLogic build() {
            StatementProxyLogic logic = new StatementProxyLogic();
            logic.statement = this.statement;
            logic.query = this.query;
            logic.connectionInfo = this.connectionInfo;
            logic.proxyConnection = this.proxyConnection;
            logic.proxyConfig = this.proxyConfig;
            logic.statementType = this.statementType;
            logic.generateKey = this.generateKey;
            return logic;
        }

        public Builder statement(Statement statement, StatementType statementType) {
            this.statement = statement;
            this.statementType = statementType;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder connectionInfo(ConnectionInfo connectionInfo) {
            this.connectionInfo = connectionInfo;
            return this;
        }

        public Builder proxyConnection(Connection proxyConnection) {
            this.proxyConnection = proxyConnection;
            return this;
        }

        public Builder proxyConfig(ProxyConfig proxyConfig) {
            this.proxyConfig = proxyConfig;
            return this;
        }

        public Builder generateKey(boolean generateKey) {
            this.generateKey = generateKey;
            return this;
        }
    }
}

