/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.proxy.ProxyConfig;

public class MethodExecutionContext {
    private Object target;
    private Method method;
    private Object[] methodArgs;
    private Object result;
    private Throwable thrown;
    private long elapsedTime;
    private ConnectionInfo connectionInfo;
    private ProxyConfig proxyConfig;
    private Object proxy;
    private Map<String, Object> customValues = new HashMap<String, Object>();

    public Object getTarget() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getMethodArgs() {
        return this.methodArgs;
    }

    public void setMethodArgs(Object[] methodArgs) {
        this.methodArgs = methodArgs;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getThrown() {
        return this.thrown;
    }

    public void setThrown(Throwable thrown) {
        this.thrown = thrown;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public ConnectionInfo getConnectionInfo() {
        return this.connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public ProxyConfig getProxyConfig() {
        return this.proxyConfig;
    }

    public void setProxyConfig(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    public Object getProxy() {
        return this.proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public void addCustomValue(String key, Object value) {
        this.customValues.put(key, value);
    }

    public <T> T getCustomValue(String key, Class<T> type) {
        return type.cast(this.customValues.get(key));
    }

    static /* synthetic */ Object[] access$202(MethodExecutionContext x0, Object[] x1) {
        x0.methodArgs = x1;
        return x1;
    }

    public static class Builder {
        private Object target;
        private Method method;
        private Object[] methodArgs;
        private Object result;
        private Throwable thrown;
        private long elapsedTime;
        private ConnectionInfo connectionInfo;
        private ProxyConfig proxyConfig;
        private Object proxy;

        public static Builder create() {
            return new Builder();
        }

        public MethodExecutionContext build() {
            MethodExecutionContext context = new MethodExecutionContext();
            context.target = this.target;
            context.method = this.method;
            MethodExecutionContext.access$202(context, this.methodArgs);
            context.result = this.result;
            context.thrown = this.thrown;
            context.elapsedTime = this.elapsedTime;
            context.connectionInfo = this.connectionInfo;
            context.proxyConfig = this.proxyConfig;
            context.proxy = this.proxy;
            return context;
        }

        public Builder target(Object target) {
            this.target = target;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder methodArgs(Object[] methodArgs) {
            this.methodArgs = methodArgs;
            return this;
        }

        public Builder result(Object result) {
            this.result = result;
            return this;
        }

        public Builder thrown(Throwable thrown) {
            this.thrown = thrown;
            return this;
        }

        public Builder elapsedTime(long elapsedTime) {
            this.elapsedTime = elapsedTime;
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

        public Builder proxy(Object proxy) {
            this.proxy = proxy;
            return this;
        }
    }
}

