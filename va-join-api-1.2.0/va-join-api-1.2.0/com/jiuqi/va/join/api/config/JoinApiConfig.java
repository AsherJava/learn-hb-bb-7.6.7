/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 */
package com.jiuqi.va.join.api.config;

import com.jiuqi.va.domain.common.EnvConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@Lazy(value=false)
@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.join.api"})
@PropertySource(value={"classpath:va-join-api.properties"})
public class JoinApiConfig {
    private static String appName = null;
    private static String primaryTemplate = null;
    private static boolean joinNameCheck = true;
    private static int listenerPrefetch = 10;

    @Value(value="${spring.application.name:NVWA}")
    private void setAppName(String name) {
        if (name != null) {
            if (!name.matches("^[A-Za-z0-9_-]{1,30}$")) {
                throw new RuntimeException("\u542f\u52a8\u53c2\u6570spring.application.name=" + name + "\u4e0d\u7b26\u5408\u89c4\u5219: ^[A-Za-z0-9_-]{1,30}$");
            }
            appName = name.toUpperCase().replace("-", "_");
        }
    }

    @Value(value="${va-join.mq-primary:}")
    private void setPrimaryTemplate(String mqPrimary) {
        primaryTemplate = mqPrimary;
    }

    @Value(value="${va-join.name-check:true}")
    private void setCheckJoinName(boolean nameCheck) {
        joinNameCheck = nameCheck;
    }

    @Value(value="${va-join.listener.prefetch:10}")
    private void setListenerPrefetch(int prefetch) {
        listenerPrefetch = prefetch;
    }

    public static String getAppName() {
        return appName;
    }

    public static String getAppInstanceId() {
        return EnvConfig.getCurrNodeId();
    }

    public static String getPrimaryTemplate() {
        return primaryTemplate;
    }

    public static boolean isCheckJoinName() {
        return joinNameCheck;
    }

    public static int getListenerPrefetch() {
        return listenerPrefetch;
    }
}

