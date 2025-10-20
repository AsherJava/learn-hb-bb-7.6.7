/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener.lifecycle;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.sql.CommonDataSource;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.listener.lifecycle.JdbcLifecycleEventListener;

public class JdbcLifecycleEventListenerUtils {
    private static final Map<String, Method> beforeLifecycleMethodsByMethodName = new HashMap<String, Method>();
    private static final Map<String, Method> afterLifecycleMethodsByMethodName = new HashMap<String, Method>();

    private static Map<String, Method> getLifecycleMethodByMethodName(Set<String> methodNames, boolean isBefore) {
        HashMap<String, Method> lifecycleMethodByName = new HashMap<String, Method>();
        for (Method method : JdbcLifecycleEventListener.class.getMethods()) {
            lifecycleMethodByName.put(method.getName(), method);
        }
        HashMap<String, Method> result = new HashMap<String, Method>();
        for (String methodName : methodNames) {
            String lifecycleMethodName = (isBefore ? "before" : "after") + JdbcLifecycleEventListenerUtils.capitalize(methodName);
            Method targetLifecycleMethod = (Method)lifecycleMethodByName.get(lifecycleMethodName);
            result.put(methodName, targetLifecycleMethod);
        }
        return result;
    }

    private static String capitalize(String methodName) {
        StringBuilder sb = new StringBuilder(methodName.length());
        sb.append(Character.toUpperCase(methodName.charAt(0)));
        sb.append(methodName.substring(1));
        return sb.toString();
    }

    private static Set<String> getDeclaredMethodNames(Class<?> ... classes) {
        HashSet<String> names = new HashSet<String>();
        for (Class<?> clazz : classes) {
            names.addAll(JdbcLifecycleEventListenerUtils.getDeclaredMethodNames(clazz));
        }
        return names;
    }

    private static Set<String> getDeclaredMethodNames(Class<?> clazz) {
        HashSet<String> names = new HashSet<String>();
        for (Method method : clazz.getDeclaredMethods()) {
            names.add(method.getName());
        }
        return names;
    }

    public static Method getListenerMethod(String invokedMethodName, boolean isBefore) {
        if (isBefore) {
            return beforeLifecycleMethodsByMethodName.get(invokedMethodName);
        }
        return afterLifecycleMethodsByMethodName.get(invokedMethodName);
    }

    static {
        Set<String> methodNames = JdbcLifecycleEventListenerUtils.getDeclaredMethodNames(Wrapper.class, DataSource.class, CommonDataSource.class, Connection.class, Statement.class, PreparedStatement.class, CallableStatement.class, ResultSet.class);
        beforeLifecycleMethodsByMethodName.putAll(JdbcLifecycleEventListenerUtils.getLifecycleMethodByMethodName(methodNames, true));
        afterLifecycleMethodsByMethodName.putAll(JdbcLifecycleEventListenerUtils.getLifecycleMethodByMethodName(methodNames, false));
    }
}

