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
import java.util.List;

public class BatchPrivilegeWebImpl {
    @JsonInclude
    private PrivilegeWebImpl webImpl;
    @JsonInclude
    private List<String> ownerList;

    public PrivilegeWebImpl getWebImpl() {
        return this.webImpl;
    }

    public void setWebImpl(PrivilegeWebImpl webImpl) {
        this.webImpl = webImpl;
    }

    public List<String> getOwnerList() {
        return this.ownerList;
    }

    public void setOwnerList(List<String> ownerList) {
        this.ownerList = ownerList;
    }
}

