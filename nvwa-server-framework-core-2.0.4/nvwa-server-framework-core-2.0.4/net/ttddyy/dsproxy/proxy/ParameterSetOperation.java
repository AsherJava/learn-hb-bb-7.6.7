/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.Method;

public class ParameterSetOperation {
    private Method method;
    private Object[] args;

    public static boolean isRegisterOutParameterOperation(ParameterSetOperation operation) {
        String methodName = operation.getMethod().getName();
        return "registerOutParameter".equals(methodName);
    }

    public static boolean isSetNullParameterOperation(ParameterSetOperation operation) {
        String methodName = operation.getMethod().getName();
        return "setNull".equals(methodName);
    }

    public ParameterSetOperation() {
    }

    public ParameterSetOperation(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}

