/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogic;

public class RepeatableReadResultSetProxyLogic
extends ProxyLogicSupport
implements ResultSetProxyLogic {
    private static final Set<String> METHODS_TO_INTERCEPT = Collections.unmodifiableSet(new HashSet<String>(){
        {
            for (Method method : ResultSet.class.getDeclaredMethods()) {
                this.add(method.getName());
            }
            this.add("toString");
            this.add("getTarget");
            this.add("getProxyConfig");
        }
    });
    private static final Map<String, Method> NUMBER_X_VALUE_METHOD_PER_NUMERIC_TYPE = Collections.unmodifiableMap(new HashMap<String, Method>(){
        private static final String METHOD_SUFFIX = "Value";
        {
            for (Method method : Number.class.getDeclaredMethods()) {
                String methodName = method.getName();
                if (!methodName.endsWith(METHOD_SUFFIX)) continue;
                this.put(methodName.substring(0, methodName.indexOf(METHOD_SUFFIX)), method);
            }
        }
    });
    private static final Object UNCONSUMED_RESULT_COLUMN = new Object();
    private Map<String, Integer> columnNameToIndex;
    private ResultSet resultSet;
    private ConnectionInfo connectionInfo;
    private int columnCount;
    private ProxyConfig proxyConfig;
    private int resultPointer;
    private boolean resultSetConsumed;
    private boolean closed;
    private Object[] currentResult;
    private final List<Object[]> cachedResults = new ArrayList<Object[]>();
    private boolean wasNull;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.proceedMethodExecution(this.proxyConfig, this.resultSet, this.connectionInfo, proxy, method, args);
    }

    @Override
    protected Object performProxyLogic(Object proxy, Method method, Object[] args, MethodExecutionContext methodContext) throws Throwable {
        String methodName = method.getName();
        if (!METHODS_TO_INTERCEPT.contains(methodName)) {
            return this.proceedExecution(method, this.resultSet, args);
        }
        if (this.isCommonMethod(methodName)) {
            return this.handleCommonMethod(methodName, this.resultSet, this.proxyConfig, args);
        }
        if (methodName.equals("getMetaData")) {
            return this.proceedExecution(method, this.resultSet, args);
        }
        if (methodName.equals("close")) {
            this.closed = true;
            return this.proceedExecution(method, this.resultSet, args);
        }
        if (methodName.equals("isClosed")) {
            return this.proceedExecution(method, this.resultSet, args);
        }
        if (this.closed) {
            throw new SQLException("Already closed");
        }
        if (this.resultSetConsumed) {
            if (this.isWasNullMethod(method)) {
                return this.wasNull;
            }
            if (this.isGetMethod(method)) {
                return this.handleGetMethodUsingCache(method, args);
            }
            if (this.isNextMethod(method)) {
                return this.handleNextMethodUsingCache();
            }
        } else {
            if (this.isWasNullMethod(method)) {
                return this.proceedExecution(method, this.resultSet, args);
            }
            if (this.isGetMethod(method)) {
                return this.handleGetMethodByDelegating(method, args);
            }
            boolean isNextMethod = this.isNextMethod(method);
            if (isNextMethod || this.isBeforeFirstMethod(method)) {
                this.beforeNextOrBeforeFirst();
            }
            if (isNextMethod) {
                return this.handleNextMethodByDelegating(method, args);
            }
            if (this.isBeforeFirstMethod(method)) {
                this.resultPointer = -1;
                this.resultSetConsumed = true;
                return null;
            }
        }
        throw new UnsupportedOperationException(String.format("Method '%s' is not supported by this proxy", method));
    }

    private void beforeNextOrBeforeFirst() throws SQLException {
        if (this.currentResult == null) {
            return;
        }
        for (int i = 1; i < this.currentResult.length; ++i) {
            Object resultColumn = this.currentResult[i];
            if (resultColumn != UNCONSUMED_RESULT_COLUMN) continue;
            this.currentResult[i] = this.resultSet.getObject(i);
        }
    }

    private Object handleNextMethodByDelegating(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        Object result = method.invoke(this.resultSet, args);
        if (Boolean.TRUE.equals(result)) {
            this.currentResult = new Object[this.columnCount + 1];
            Arrays.fill(this.currentResult, UNCONSUMED_RESULT_COLUMN);
            this.cachedResults.add(this.currentResult);
        }
        return result;
    }

    private Object handleGetMethodByDelegating(Method method, Object[] args) throws SQLException, IllegalAccessException, InvocationTargetException {
        Object result;
        int columnIndex = this.determineColumnIndex(args);
        this.currentResult[columnIndex] = result = method.invoke(this.resultSet, args);
        return result;
    }

    private Object handleNextMethodUsingCache() {
        if (this.resultPointer < this.cachedResults.size() - 1) {
            ++this.resultPointer;
            this.currentResult = this.cachedResults.get(this.resultPointer);
            return true;
        }
        ++this.resultPointer;
        this.currentResult = null;
        return false;
    }

    private Object handleGetMethodUsingCache(Method method, Object[] args) throws SQLException, InvocationTargetException, IllegalAccessException {
        if (this.resultPointer == -1) {
            throw new SQLException("Result set not advanced. Call next before any get method!");
        }
        if (this.resultPointer < this.cachedResults.size()) {
            int columnIndex = this.determineColumnIndex(args);
            Object columnValue = this.currentResult[columnIndex];
            this.wasNull = this.isNullValue(columnValue, method, args);
            if (!(columnValue instanceof Number)) {
                return columnValue;
            }
            return this.convertNumberToExpectedType(method.getName(), (Number)columnValue);
        }
        throw new SQLException(String.format("Result set exhausted. There were %d result(s) only", this.cachedResults.size()));
    }

    protected boolean isNullValue(Object value, Method method, Object[] args) {
        return value == null;
    }

    private Object convertNumberToExpectedType(String getMethodName, Number value) throws InvocationTargetException, IllegalAccessException {
        String targetTypeName = getMethodName.substring("get".length()).toLowerCase();
        Method converter = NUMBER_X_VALUE_METHOD_PER_NUMERIC_TYPE.get(targetTypeName);
        if (converter == null) {
            return value;
        }
        return converter.invoke(value, new Object[0]);
    }

    private boolean isGetMethod(Method method) {
        return method.getName().startsWith("get") && method.getParameterTypes().length > 0;
    }

    private boolean isNextMethod(Method method) {
        return method.getName().equals("next");
    }

    private boolean isBeforeFirstMethod(Method method) {
        return method.getName().equals("beforeFirst");
    }

    private boolean isWasNullMethod(Method method) {
        return method.getName().equals("wasNull");
    }

    private int determineColumnIndex(Object[] args) throws SQLException {
        Object lookup = args[0];
        if (lookup instanceof Integer) {
            return (Integer)lookup;
        }
        String columnName = (String)lookup;
        Integer indexForColumnName = this.columnNameToIndex(columnName);
        if (indexForColumnName != null) {
            return indexForColumnName;
        }
        throw new SQLException(String.format("Unknown column name '%s'", columnName));
    }

    private Integer columnNameToIndex(String columnName) {
        return this.columnNameToIndex.get(columnName.toUpperCase());
    }

    public static class Builder {
        private ResultSet resultSet;
        private ConnectionInfo connectionInfo;
        private ProxyConfig proxyConfig;
        private Map<String, Integer> columnNameToIndex;
        private int columnCount;

        public static Builder create() {
            return new Builder();
        }

        public RepeatableReadResultSetProxyLogic build() {
            RepeatableReadResultSetProxyLogic logic = new RepeatableReadResultSetProxyLogic();
            logic.resultSet = this.resultSet;
            logic.connectionInfo = this.connectionInfo;
            logic.proxyConfig = this.proxyConfig;
            logic.columnNameToIndex = this.columnNameToIndex;
            logic.columnCount = this.columnCount;
            return logic;
        }

        public Builder resultSet(ResultSet resultSet) {
            this.resultSet = resultSet;
            return this;
        }

        public Builder connectionInfo(ConnectionInfo connectionInfo) {
            this.connectionInfo = connectionInfo;
            return this;
        }

        public Builder proxyConfig(ProxyConfig proxyConfig) {
            this.proxyConfig = proxyConfig;
            return this;
        }

        public Builder columnNameToIndex(Map<String, Integer> columnNameToIndex) {
            this.columnNameToIndex = columnNameToIndex;
            return this;
        }

        public Builder columnCount(int columnCount) {
            this.columnCount = columnCount;
            return this;
        }
    }
}

