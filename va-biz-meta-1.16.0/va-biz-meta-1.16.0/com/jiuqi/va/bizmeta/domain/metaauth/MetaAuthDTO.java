/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.metaauth;

import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDO;

public class MetaAuthDTO
extends MetaAuthDO {
    private static final long serialVersionUID = 1L;
    private String sqlCondition;

    public String getSqlCondition() {
        return this.sqlCondition;
    }

    public void setSqlCondition(String sqlCondition) {
        this.sqlCondition = sqlCondition;
    }
}

