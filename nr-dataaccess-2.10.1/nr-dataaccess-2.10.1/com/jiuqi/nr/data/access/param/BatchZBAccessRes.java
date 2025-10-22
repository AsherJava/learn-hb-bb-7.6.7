/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.param.ZbAccessCaches;
import com.jiuqi.nr.data.access.param.ZbAccessResByCache;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;
import java.util.function.Supplier;

public class BatchZBAccessRes
implements IBatchZBAccessResult {
    private final DimensionCollection masterKeys;
    private final List<String> zbKeys;
    private final AccessType accessType;
    private Supplier<ZbAccessCaches> zbAccessCachesSupplier;
    private final AccessCacheManager accessManager;

    public BatchZBAccessRes(DimensionCollection masterKeys, List<String> zbKeys, AccessType accessType, AccessCacheManager accessManager) {
        this.masterKeys = masterKeys;
        this.zbKeys = zbKeys;
        this.accessType = accessType;
        this.accessManager = accessManager;
    }

    @Override
    public IAccessResult getAccess(DimensionCombination masterKey, String zbKey) {
        ZbAccessCaches zbAccessCaches = null;
        if (this.zbAccessCachesSupplier != null) {
            zbAccessCaches = this.zbAccessCachesSupplier.get();
        }
        ZbAccessResByCache zbAccessResByCache = new ZbAccessResByCache(this.accessType, this.masterKeys, masterKey, zbKey, this.zbKeys, zbAccessCaches, this.accessManager);
        this.zbAccessCachesSupplier = zbAccessResByCache.zbAccessCachesSupplier();
        return zbAccessResByCache;
    }
}

