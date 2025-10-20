/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.catche;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class MetaInfoCache {
    public static final Map<String, Map<String, Map<String, Object>>> tenantDesignTableCache = new ConcurrentHashMap<String, Map<String, Map<String, Object>>>();
}

