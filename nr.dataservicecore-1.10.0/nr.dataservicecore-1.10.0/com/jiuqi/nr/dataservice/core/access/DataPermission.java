/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import java.io.Serializable;
import java.util.Collection;

public class DataPermission
implements Serializable {
    private static final long serialVersionUID = -2801673635525003912L;
    private Collection<String> resourceIds;
    private Collection<DataPermissionResource> accessResources;
    private Collection<DataPermissionResource> unAccessResources;

    public Collection<String> getResourceIds() {
        return this.resourceIds;
    }

    public void setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

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
}

