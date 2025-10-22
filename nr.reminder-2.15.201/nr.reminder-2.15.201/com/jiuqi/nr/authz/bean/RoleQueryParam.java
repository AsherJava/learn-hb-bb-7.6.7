/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.bean.QueryParam;

public class RoleQueryParam
extends QueryParam {
    private boolean group;

    public boolean isGroup() {
        return this.group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public RoleQueryParam() {
    }

    public RoleQueryParam(String keyword) {
        this.keyword = keyword;
    }
}

