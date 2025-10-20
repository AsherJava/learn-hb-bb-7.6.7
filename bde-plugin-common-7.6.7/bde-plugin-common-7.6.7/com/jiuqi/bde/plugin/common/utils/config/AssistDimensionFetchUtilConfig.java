/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AssistDimensionFetchUtilConfig {
    private static int numPartitions = 2;
    private static int maxNumPartitions = 8;
    private static int corePoolSize = 10;
    private static int maxPoolSize = 50;
    private static long keepAliveTime = 60L;
    private static int queueSize = 100;

    public static int getQueueSize() {
        return queueSize;
    }

    @Value(value="${bde.assist.dimension.fetch.util.queueSize:100}")
    public static void setQueueSize(int queueSize) {
        AssistDimensionFetchUtilConfig.queueSize = queueSize;
    }

    public static int getCorePoolSize() {
        return corePoolSize;
    }

    @Value(value="${bde.assist.dimension.fetch.util.corePoolSize:10}")
    public static void setCorePoolSize(int corePoolSize) {
        AssistDimensionFetchUtilConfig.corePoolSize = corePoolSize;
    }

    public static int getMaxPoolSize() {
        return maxPoolSize;
    }

    @Value(value="${bde.assist.dimension.fetch.util.maxPoolSize:50}")
    public static void setMaxPoolSize(int maxPoolSize) {
        AssistDimensionFetchUtilConfig.maxPoolSize = maxPoolSize;
    }

    public static long getKeepAliveTime() {
        return keepAliveTime;
    }

    @Value(value="${bde.assist.dimension.fetch.util.keepAliveTime:60}")
    public static void setKeepAliveTime(long keepAliveTime) {
        AssistDimensionFetchUtilConfig.keepAliveTime = keepAliveTime;
    }

    public static int getMaxNumPartitions() {
        return maxNumPartitions;
    }

    @Value(value="${bde.assist.dimension.fetch.util.maxNumPartitions:8}")
    public static void setMaxNumPartitions(int maxNumPartitions) {
        AssistDimensionFetchUtilConfig.maxNumPartitions = Math.min(maxNumPartitions, 16);
    }

    public static int getNumPartitions() {
        return numPartitions;
    }

    @Value(value="${bde.assist.dimension.fetch.util.numPartitions:2}")
    public static void setNumPartitions(int numPartitions) {
        AssistDimensionFetchUtilConfig.numPartitions = Math.min(numPartitions, 8);
    }
}

