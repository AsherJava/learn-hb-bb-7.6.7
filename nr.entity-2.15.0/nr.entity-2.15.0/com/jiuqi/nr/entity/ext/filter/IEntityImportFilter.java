/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.ext.filter;

public interface IEntityImportFilter {
    default public boolean importEntityDefine(String entityId) {
        return true;
    }

    default public boolean importEntityData(String entityId) {
        return true;
    }
}

