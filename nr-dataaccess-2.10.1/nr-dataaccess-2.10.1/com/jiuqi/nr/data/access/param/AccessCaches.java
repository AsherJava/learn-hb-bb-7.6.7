/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import java.util.Map;

public class AccessCaches {
    private Map<String, IBatchAccess> batchAccessMap;
    private AccessCode accessCode;

    public AccessCaches(Map<String, IBatchAccess> batchAccessMap, AccessCode accessCode) {
        this.batchAccessMap = batchAccessMap;
        this.accessCode = accessCode;
    }

    public Map<String, IBatchAccess> getBatchAccessMap() {
        return this.batchAccessMap;
    }

    public AccessCode getAccessCode() {
        return this.accessCode;
    }
}

