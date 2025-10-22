/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AbstractBatchAccessResult;
import com.jiuqi.nr.data.access.param.AccessResultByCache;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.List;

public class BatchAccessResult
extends AbstractBatchAccessResult
implements IBatchAccessResult {
    public BatchAccessResult(String taskKey, String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys, AccessType accessType, AccessCacheManager accessCacheManager) {
        super(taskKey, formSchemeKey, masterKeys, formKeys, accessType, accessCacheManager);
    }

    @Override
    public IAccessResult getAccess(DimensionCombination masterKey, String formKey) {
        return new AccessResultByCache(this.taskKey, this.formSchemeKey, masterKey, formKey, this.batchAccessMap, this.masterKeys, this.formKeys, this.accessType, this.accessCacheManager);
    }

    @Override
    public List<String> getAccessForm(DimensionCombination masterKey) {
        ArrayList<String> res = new ArrayList<String>();
        try {
            for (String formKey : this.formKeys) {
                IAccessResult access = this.getAccess(masterKey, formKey);
                if (!access.haveAccess()) continue;
                res.add(formKey);
            }
        }
        catch (Exception e) {
            throw new AccessException(e);
        }
        return res;
    }
}

