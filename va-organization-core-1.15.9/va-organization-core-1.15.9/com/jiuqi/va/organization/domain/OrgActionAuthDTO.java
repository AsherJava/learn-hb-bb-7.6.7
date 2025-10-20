/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.organization.domain.OrgActionAuthDO;

public class OrgActionAuthDTO
extends OrgActionAuthDO {
    private static final long serialVersionUID = 1L;
    private String sqlCondition;

    public String getSqlCondition() {
        return this.sqlCondition;
    }

    public void setSqlCondition(String sqlCondition) {
        this.sqlCondition = sqlCondition;
    }
}

