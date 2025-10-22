/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi.entity;

import com.jiuqi.nr.datacrud.spi.entity.QueryMode;

public class QueryModeImpl
implements QueryMode {
    private int entityQueryMode;
    private boolean desensitized;

    @Override
    public int getEntityQueryMode() {
        return this.entityQueryMode;
    }

    @Override
    public boolean isDesensitized() {
        return this.desensitized;
    }

    public void setEntityQueryMode(int entityQueryMode) {
        this.entityQueryMode = entityQueryMode;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }

    public static QueryMode create(int entityQueryMode, boolean desensitized) {
        QueryModeImpl queryMode = new QueryModeImpl();
        queryMode.setEntityQueryMode(entityQueryMode);
        queryMode.setDesensitized(desensitized);
        return queryMode;
    }

    public static QueryMode create(int entityQueryMode) {
        QueryModeImpl queryMode = new QueryModeImpl();
        queryMode.setEntityQueryMode(entityQueryMode);
        return queryMode;
    }
}

