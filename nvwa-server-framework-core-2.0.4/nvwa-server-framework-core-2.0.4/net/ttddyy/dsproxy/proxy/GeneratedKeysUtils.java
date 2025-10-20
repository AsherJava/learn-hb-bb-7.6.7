/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GeneratedKeysUtils {
    private static final Set<String> METHOD_NAMES_TO_CHECK;

    public static boolean isMethodToRetrieveGeneratedKeys(Method method) {
        return METHOD_NAMES_TO_CHECK.contains(method.getName());
    }

    public static boolean isAutoGenerateEnabledParameters(Object[] args) {
        if (args == null || args.length != 2 || args[1] == null) {
            return false;
        }
        Object arg = args[1];
        if (arg instanceof Integer) {
            return (Integer)arg == 1;
        }
        if (arg instanceof int[]) {
            return ((int[])arg).length != 0;
        }
        if (arg instanceof String[]) {
            return ((String[])arg).length != 0;
        }
        return false;
    }

    static {
        HashSet<String> methodNames = new HashSet<String>();
        methodNames.add("prepareStatement");
        methodNames.add("execute");
        methodNames.add("executeUpdate");
        methodNames.add("executeLargeUpdate");
        methodNames.add("executeBatch");
        methodNames.add("executeLargeBatch");
        METHOD_NAMES_TO_CHECK = Collections.unmodifiableSet(methodNames);
    }
}

