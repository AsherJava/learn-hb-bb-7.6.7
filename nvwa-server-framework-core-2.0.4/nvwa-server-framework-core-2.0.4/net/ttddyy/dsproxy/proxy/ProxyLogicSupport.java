/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.listener.CompositeMethodListener;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ProxyJdbcObject;
import net.ttddyy.dsproxy.proxy.Stopwatch;

public abstract class ProxyLogicSupport {
    protected static final Set<String> COMMON_METHOD_NAMES = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("getTarget", "getProxyConfig", "getDataSourceName", "unwrap", "isWrapperFor", "toString", "equals", "hashCode")));

    protected boolean isCommonMethod(String methodName) {
        return COMMON_METHOD_NAMES.contains(methodName);
    }

    protected Object handleCommonMethod(String methodName, Object original, ProxyConfig proxyConfig, Object[] args) throws SQLException {
        if ("toString".equals(methodName)) {
            StringBuilder sb = new StringBuilder();
            sb.append(original.getClass().getSimpleName());
            sb.append(" [");
            sb.append(original);
            sb.append("]");
            return sb.toString();
        }
        if ("equals".equals(methodName)) {
            return original.equals(args[0]) || args[0] instanceof ProxyJdbcObject && original.equals(((ProxyJdbcObject)args[0]).getTarget());
        }
        if ("hashCode".equals(methodName)) {
            return original.hashCode();
        }
        if ("getDataSourceName".equals(methodName)) {
            return proxyConfig.getDataSourceName();
        }
        if ("getTarget".equals(methodName)) {
            return original;
        }
        if ("getProxyConfig".equals(methodName)) {
            return proxyConfig;
        }
        if ("unwrap".equals(methodName)) {
            Class clazz = (Class)args[0];
            return ((Wrapper)original).unwrap(clazz);
        }
        if ("isWrapperFor".equals(methodName)) {
            Class clazz = (Class)args[0];
            return ((Wrapper)original).isWrapperFor(clazz);
        }
        throw new IllegalStateException(methodName + " does not match with " + COMMON_METHOD_NAMES);
    }

    protected Object proceedExecution(Method method, Object target, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        }
        catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    protected Object proceedMethodExecution(ProxyConfig proxyConfig, Object original, ConnectionInfo connectionInfo, Object proxy, Method method, Object[] args) throws Throwable {
        MethodExecutionContext methodContext = MethodExecutionContext.Builder.create().target(original).proxy(proxy).method(method).methodArgs(args).connectionInfo(connectionInfo).proxyConfig(proxyConfig).build();
        CompositeMethodListener methodExecutionListener = proxyConfig.getMethodListener();
        methodExecutionListener.beforeMethod(methodContext);
        Method methodToInvoke = methodContext.getMethod();
        Object[] methodArgsToInvoke = methodContext.getMethodArgs();
        Stopwatch stopwatch = proxyConfig.getStopwatchFactory().create().start();
        Object result = null;
        Throwable thrown = null;
        try {
            result = this.performProxyLogic(proxy, methodToInvoke, methodArgsToInvoke, methodContext);
        }
        catch (Throwable throwable) {
            thrown = throwable;
            throw throwable;
        }
        finally {
            long elapsedTime = stopwatch.getElapsedTime();
            methodContext.setElapsedTime(elapsedTime);
            methodContext.setResult(result);
            methodContext.setThrown(thrown);
            methodExecutionListener.afterMethod(methodContext);
        }
        return result;
    }

    protected abstract Object performProxyLogic(Object var1, Method var2, Object[] var3, MethodExecutionContext var4) throws Throwable;
}

