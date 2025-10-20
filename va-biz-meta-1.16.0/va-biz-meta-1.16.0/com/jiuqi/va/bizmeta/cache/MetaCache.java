/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 */
package com.jiuqi.va.bizmeta.cache;

import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class MetaCache {
    public static final Map<String, Map<UUID, MetaDataDTO>> tenantDesignCache = new ConcurrentHashMap<String, Map<UUID, MetaDataDTO>>();
    public static final ConcurrentHashMap<String, Map<String, Map<Long, MetaInfoDTO>>> tenantMetaInfoCache = new ConcurrentHashMap();
    public static final ConcurrentHashMap<String, Map<String, Map<Long, Integer>>> tenantSyncStatusCache = new ConcurrentHashMap();
}

