/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.bean;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.NrResource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionStatusData {
    private Map<String, Map<String, Authority>> oldPermissionStatus;
    private Map<String, Map<String, Authority>> permissionStatus;
    private List<String> resourceIds;
    private String resCategoryId;
    private Map<String, NrResource> resourceMap = new HashMap<String, NrResource>();

    public Map<String, Map<String, Authority>> getOldPermissionStatus() {
        return this.oldPermissionStatus;
    }

    public void setOldPermissionStatus(Map<String, Map<String, Authority>> oldPermissionStatus) {
        this.oldPermissionStatus = oldPermissionStatus;
    }

    public Map<String, Map<String, Authority>> getPermissionStatus() {
        return this.permissionStatus;
    }

    public void setPermissionStatus(Map<String, Map<String, Authority>> permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public List<String> getResourceIds() {
        return this.resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getResCategoryId() {
        return this.resCategoryId;
    }

    public void setResCategoryId(String resCategoryId) {
        this.resCategoryId = resCategoryId;
    }

    public void addResource(String resourceId, NrResource resource) {
        if (resourceId != null && resource != null) {
            this.resourceMap.put(resourceId, resource);
        }
    }

    public NrResource getResource(String resourceId) {
        if (resourceId != null) {
            return this.resourceMap.get(resourceId);
        }
        return null;
    }
}

