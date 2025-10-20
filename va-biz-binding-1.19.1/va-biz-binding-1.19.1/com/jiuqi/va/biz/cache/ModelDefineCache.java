/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.cache;

import com.jiuqi.va.biz.intf.model.ModelDefine;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ModelDefineCache {
    public static final Map<String, Map<String, Map<Long, ModelDefine>>> tenantBuffer = new ConcurrentHashMap<String, Map<String, Map<Long, ModelDefine>>>();
    public static final Map<String, Map<String, Map<Long, Long>>> tenantVerMap = new ConcurrentHashMap<String, Map<String, Map<Long, Long>>>();
    public static final Map<String, Map<String, Set<String>>> tenantDefineTableMap = new ConcurrentHashMap<String, Map<String, Set<String>>>();
    public static final Map<String, Map<String, ModelDefine>> tenantExternalBuffer = new ConcurrentHashMap<String, Map<String, ModelDefine>>();
    public static final Map<String, Map<String, Long>> tenantExternalVer = new ConcurrentHashMap<String, Map<String, Long>>();
    public static final Map<String, Map<String, String>> tenantExternalPcVer = new ConcurrentHashMap<String, Map<String, String>>();
    public static final ConcurrentHashMap<String, Map<String, Map<Long, Integer>>> tenantSyncStatusCache = new ConcurrentHashMap();
}

