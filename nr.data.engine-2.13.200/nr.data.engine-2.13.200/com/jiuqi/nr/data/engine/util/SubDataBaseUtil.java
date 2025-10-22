/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 */
package com.jiuqi.nr.data.engine.util;

import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubDataBaseUtil {
    @Autowired
    private SubDatabaseTableNamesProvider subDatabaseTableNamesProvider;

    public String getRealTableName(String tableName, String taskKey) {
        return this.subDatabaseTableNamesProvider.getSubDatabaseTableName(taskKey, tableName);
    }
}

