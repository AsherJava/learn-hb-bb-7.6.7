/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBatchAccessResult {
    protected AccessParam param;
    protected String taskKey;
    protected String formSchemeKey;
    protected DimensionCollection masterKeys;
    protected List<String> formKeys;
    protected Map<String, IBatchAccess> batchAccessMap;
    protected AccessType accessType;
    protected AccessCacheManager accessCacheManager;

    public AbstractBatchAccessResult(String taskKey, String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys, AccessType accessType, AccessCacheManager accessCacheManager) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.masterKeys = masterKeys;
        this.formKeys = formKeys;
        this.accessType = accessType;
        this.batchAccessMap = new HashMap<String, IBatchAccess>();
        this.accessCacheManager = accessCacheManager;
    }

    public AbstractBatchAccessResult(AccessParam param, String taskKey, String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys, AccessType accessType, AccessCacheManager accessCacheManager) {
        this.param = param;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.masterKeys = masterKeys;
        this.formKeys = formKeys;
        this.accessType = accessType;
        this.batchAccessMap = new HashMap<String, IBatchAccess>();
        this.accessCacheManager = accessCacheManager;
    }
}

