/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl
 */
package com.jiuqi.nr.portal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl;
import java.util.Map;

public class PortalBatchPrivilegeWebImpl {
    @JsonInclude
    private PrivilegeWebImpl allow;
    @JsonInclude
    private PrivilegeWebImpl none;
    @JsonInclude
    private Map<String, Boolean> allowOwner;
    @JsonInclude
    private Map<String, Boolean> noneOwner;

    public PrivilegeWebImpl getAllow() {
        return this.allow;
    }

    public void setAllow(PrivilegeWebImpl allow) {
        this.allow = allow;
    }

    public PrivilegeWebImpl getNone() {
        return this.none;
    }

    public void setNone(PrivilegeWebImpl none) {
        this.none = none;
    }

    public Map<String, Boolean> getAllowOwner() {
        return this.allowOwner;
    }

    public void setAllowOwner(Map<String, Boolean> allowOwner) {
        this.allowOwner = allowOwner;
    }

    public Map<String, Boolean> getNoneOwner() {
        return this.noneOwner;
    }

    public void setNoneOwner(Map<String, Boolean> noneOwner) {
        this.noneOwner = noneOwner;
    }
}

