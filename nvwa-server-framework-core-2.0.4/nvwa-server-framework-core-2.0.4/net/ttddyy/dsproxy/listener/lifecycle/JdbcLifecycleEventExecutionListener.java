/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener.lifecycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import net.ttddyy.dsproxy.DataSourceProxyException;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.listener.MethodExecutionListener;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.listener.lifecycle.JdbcLifecycleEventListener;
import net.ttddyy.dsproxy.listener.lifecycle.JdbcLifecycleEventListenerUtils;

public class JdbcLifecycleEventExecutionListener
implements MethodExecutionListener,
QueryExecutionListener {
    private JdbcLifecycleEventListener delegate;

    public JdbcLifecycleEventExecutionListener(JdbcLifecycleEventListener delegate) {
        this.delegate = delegate;
    }

    @Override
    public void beforeMethod(MethodExecutionContext executionContext) {
        this.delegate.beforeMethod(executionContext);
        this.methodCallback(executionContext, true);
    }

    @Override
    public void afterMethod(MethodExecutionContext executionContext) {
        this.methodCallback(executionContext, false);
        this.delegate.afterMethod(executionContext);
    }

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        this.delegate.beforeQuery(execInfo, queryInfoList);
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        this.delegate.afterQuery(execInfo, queryInfoList);
    }

    private void methodCallback(MethodExecutionContext methodContext, boolean isBefore) {
        String methodName = methodContext.getMethod().getName();
        Method lifecycleMethod = JdbcLifecycleEventListenerUtils.getListenerMethod(methodName, isBefore);
        if (lifecycleMethod == null) {
            return;
        }
        try {
            lifecycleMethod.invoke(this.delegate, methodContext);
        }
        catch (InvocationTargetException ex) {
            throw new DataSourceProxyException(ex.getTargetException());
        }
        catch (Exception ex) {
            throw new DataSourceProxyException(ex);
        }
    }

    public void setDelegate(JdbcLifecycleEventListener delegate) {
        this.delegate = delegate;
    }

    public JdbcLifecycleEventListener getDelegate() {
        return this.delegate;
    }
}

