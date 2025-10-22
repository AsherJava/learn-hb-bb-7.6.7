/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.BatchAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import java.util.Map;

public class ZbAccessCaches {
    private final Map<String, IBatchMergeAccess> batchAccessMap;
    private final BatchAccessFormMerge batchAccessFormMerge;
    private AccessCode accessCode;

    public ZbAccessCaches(BatchAccessFormMerge batchAccessFormMerge, Map<String, IBatchMergeAccess> batchAccessMap, AccessCode accessCode) {
        this.batchAccessFormMerge = batchAccessFormMerge;
        this.batchAccessMap = batchAccessMap;
        this.accessCode = accessCode;
    }

    public Map<String, IBatchMergeAccess> getBatchAccessMap() {
        return this.batchAccessMap;
    }

    public void setAccessCode(AccessCode accessCode) {
        this.accessCode = accessCode;
    }

    public AccessCode getAccessCode() {
        return this.accessCode;
    }

    public BatchAccessFormMerge getBatchAccessFormMerge() {
        return this.batchAccessFormMerge;
    }
}

