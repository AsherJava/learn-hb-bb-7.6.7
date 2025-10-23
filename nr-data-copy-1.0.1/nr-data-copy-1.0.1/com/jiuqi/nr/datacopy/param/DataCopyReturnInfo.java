/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DataCopyReturnInfo {
    private Collection<DataPermissionResource> accessResources;
    private Collection<DataPermissionResource> unAccessResources;
    private Set<String> failFormKeys;
    private Map<String, String> bizKeyOrderMap;

    public Collection<DataPermissionResource> getAccessResources() {
        return this.accessResources;
    }

    public void setAccessResources(Collection<DataPermissionResource> accessResources) {
        this.accessResources = accessResources;
    }

    public Collection<DataPermissionResource> getUnAccessResources() {
        return this.unAccessResources;
    }

    public void setUnAccessResources(Collection<DataPermissionResource> unAccessResources) {
        this.unAccessResources = unAccessResources;
    }

    public Set<String> getFailFormKeys() {
        return this.failFormKeys;
    }

    public void setFailFormKeys(Set<String> failFormKeys) {
        this.failFormKeys = failFormKeys;
    }

    public Map<String, String> getBizKeyOrderMap() {
        return this.bizKeyOrderMap;
    }

    public void setBizKeyOrderMap(Map<String, String> bizKeyOrderMap) {
        this.bizKeyOrderMap = bizKeyOrderMap;
    }
}

