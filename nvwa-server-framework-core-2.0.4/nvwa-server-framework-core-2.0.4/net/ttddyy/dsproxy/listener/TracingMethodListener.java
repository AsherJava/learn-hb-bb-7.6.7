/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.listener.MethodExecutionListener;

public class TracingMethodListener
implements MethodExecutionListener {
    private static final int DEFAULT_DISPLAY_PARAM_LENGTH = 50;
    private AtomicLong sequenceNumber = new AtomicLong(1L);
    protected int parameterDisplayLength = 50;
    protected TracingCondition tracingCondition = new TracingCondition(){

        @Override
        public boolean getAsBoolean() {
            return true;
        }
    };
    protected TracingMessageConsumer tracingMessageConsumer = new TracingMessageConsumer(){

        @Override
        public void accept(String logMessage) {
            System.out.println(logMessage);
        }
    };

    @Override
    public void beforeMethod(MethodExecutionContext executionContext) {
    }

    @Override
    public void afterMethod(MethodExecutionContext executionContext) {
        if (!this.tracingCondition.getAsBoolean()) {
            return;
        }
        Method method = executionContext.getMethod();
        Class<?> targetClass = executionContext.getTarget().getClass();
        Throwable thrown = executionContext.getThrown();
        long execTime = executionContext.getElapsedTime();
        ConnectionInfo connectionInfo = executionContext.getConnectionInfo();
        String connectionId = "-1";
        if (connectionInfo != null) {
            connectionId = connectionInfo.getConnectionId();
        }
        long seq = this.sequenceNumber.getAndIncrement();
        String args = this.getArguments(executionContext.getMethodArgs());
        String message = this.constructMessage(seq, thrown, execTime, connectionId, targetClass, method, args);
        this.logMessage(message);
    }

    protected String getArguments(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (args.length == 1) {
            Object arg = args[0];
            String param = this.getSingleArgParameterAsString(arg);
            String displayParam = this.getSingleArgDisplayParameter(param);
            if (arg instanceof String) {
                sb.append("\"");
                sb.append(displayParam);
                sb.append("\"");
            } else {
                sb.append(displayParam);
            }
        } else {
            for (int i = 0; i < args.length; ++i) {
                Object arg = args[i];
                boolean lastArg = i == args.length - 1;
                String param = this.getParameterAsString(arg);
                String displayParam = this.getDisplayParameter(param);
                if (arg instanceof String) {
                    sb.append("\"");
                    sb.append(displayParam);
                    sb.append("\"");
                } else {
                    sb.append(displayParam);
                }
                if (lastArg) continue;
                sb.append(",");
            }
        }
        return sb.toString();
    }

    protected String getSingleArgParameterAsString(Object arg) {
        if (arg instanceof String) {
            return (String)arg;
        }
        return arg == null ? "null" : arg.toString();
    }

    protected String getSingleArgDisplayParameter(String parameter) {
        return parameter;
    }

    protected String getParameterAsString(Object arg) {
        if (arg instanceof String) {
            return (String)arg;
        }
        return arg == null ? "null" : arg.toString();
    }

    protected String getDisplayParameter(String parameter) {
        if (parameter.length() <= this.parameterDisplayLength) {
            return parameter;
        }
        return parameter.substring(0, this.parameterDisplayLength - 3) + "...";
    }

    protected String constructMessage(long seq, Throwable thrown, long execTime, String connectionId, Class<?> targetClass, Method method, String args) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(seq);
        sb.append("]");
        sb.append("[");
        sb.append(thrown == null ? "success" : "fail");
        sb.append("]");
        sb.append("[");
        sb.append(execTime);
        sb.append("ms]");
        sb.append("[conn=");
        sb.append(connectionId);
        sb.append("]");
        if (thrown != null) {
            sb.append("[error=");
            sb.append(thrown.getMessage());
            sb.append("]");
        }
        sb.append(" ");
        sb.append(targetClass.getSimpleName());
        sb.append("#");
        sb.append(method.getName());
        sb.append("(");
        sb.append(args);
        sb.append(")");
        return sb.toString();
    }

    protected void logMessage(String message) {
        this.tracingMessageConsumer.accept(message);
    }

    public void setParameterDisplayLength(int parameterDisplayLength) {
        this.parameterDisplayLength = parameterDisplayLength;
    }

    public TracingCondition getTracingCondition() {
        return this.tracingCondition;
    }

    public void setTracingCondition(TracingCondition tracingCondition) {
        this.tracingCondition = tracingCondition;
    }

    public TracingMessageConsumer getTracingMessageConsumer() {
        return this.tracingMessageConsumer;
    }

    public void setTracingMessageConsumer(TracingMessageConsumer tracingMessageConsumer) {
        this.tracingMessageConsumer = tracingMessageConsumer;
    }

    public static interface TracingMessageConsumer {
        public void accept(String var1);
    }

    public static interface TracingCondition {
        public boolean getAsBoolean();
    }
}

