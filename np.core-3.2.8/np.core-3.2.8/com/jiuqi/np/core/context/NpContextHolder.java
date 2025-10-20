/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolderStrategy;
import com.jiuqi.np.core.context.ThreadLocalNpContextHolderStrategy;
import java.lang.reflect.Constructor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class NpContextHolder {
    public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
    public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
    public static final String MODE_GLOBAL = "MODE_GLOBAL";
    public static final String SYSTEM_PROPERTY = "spring.security.strategy";
    private static String strategyName = System.getProperty("spring.security.strategy");
    private static NpContextHolderStrategy strategy;
    private static int initializeCount;

    public static void clearContext() {
        strategy.clearContext();
    }

    public static NpContext getContext() {
        return strategy.getContext();
    }

    public static int getInitializeCount() {
        return initializeCount;
    }

    private static void initialize() {
        if (!StringUtils.hasText(strategyName)) {
            strategyName = MODE_THREADLOCAL;
        }
        if (strategyName.equals(MODE_THREADLOCAL)) {
            strategy = new ThreadLocalNpContextHolderStrategy();
        } else if (!strategyName.equals(MODE_INHERITABLETHREADLOCAL) && !strategyName.equals(MODE_GLOBAL)) {
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor(new Class[0]);
                strategy = (NpContextHolderStrategy)customStrategy.newInstance(new Object[0]);
            }
            catch (Exception ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
        }
        ++initializeCount;
    }

    public static void setContext(NpContext context) {
        strategy.setContext(context);
    }

    public static void setStrategyName(String strategyName) {
        NpContextHolder.strategyName = strategyName;
        NpContextHolder.initialize();
    }

    public static NpContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    public static NpContext createEmptyContext() {
        return strategy.createEmptyContext();
    }

    public String toString() {
        return "SecurityContextHolder[strategy='" + strategyName + "'; initializeCount=" + initializeCount + "]";
    }

    static {
        initializeCount = 0;
        NpContextHolder.initialize();
    }
}

