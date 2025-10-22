/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.event;

import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;
import java.util.HashSet;
import java.util.Set;

public class GcOrgDataAuthzChangeEvent
extends GcOrgDataCacheBaseEvent {
    private static final long serialVersionUID = 1L;
    Set<String> cacheNames = new HashSet<String>();
    Set<String> users = new HashSet<String>();

    public void addUser(String user) {
        this.users.add(user);
    }

    public void addType(String orgType) {
        this.cacheNames.add(orgType);
    }

    public Set<String> getCacheNames() {
        return this.cacheNames;
    }

    public Set<String> getUsers() {
        return this.users;
    }
}

