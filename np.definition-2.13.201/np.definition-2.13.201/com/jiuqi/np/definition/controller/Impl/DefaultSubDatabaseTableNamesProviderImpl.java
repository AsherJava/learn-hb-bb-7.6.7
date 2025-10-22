/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.controller.Impl;

import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;

public class DefaultSubDatabaseTableNamesProviderImpl
implements SubDatabaseTableNamesProvider {
    @Override
    public String getSubDatabaseTableName(String taskKey, String tableName) {
        return tableName;
    }
}

