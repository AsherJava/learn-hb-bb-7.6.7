/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import java.io.Serializable;
import java.util.List;

public class MergeDataPermission
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> resourceIds;
    private List<UnitPermission> accessResources;
    private List<UnitPermission> unAccessResources;

    public List<String> getResourceIds() {
        return this.resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public List<UnitPermission> getAccessResources() {
        return this.accessResources;
    }

    public void setAccessResources(List<UnitPermission> accessResources) {
        this.accessResources = accessResources;
    }

    public List<UnitPermission> getUnAccessResources() {
        return this.unAccessResources;
    }

    public void setUnAccessResources(List<UnitPermission> unAccessResources) {
        this.unAccessResources = unAccessResources;
    }
}

