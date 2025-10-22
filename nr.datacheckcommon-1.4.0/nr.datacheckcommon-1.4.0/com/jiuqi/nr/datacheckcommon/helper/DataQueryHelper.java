/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 */
package com.jiuqi.nr.datacheckcommon.helper;

import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataQueryHelper {
    private static final Logger logger = LoggerFactory.getLogger(DataQueryHelper.class);
    @Autowired
    private SubDatabaseTableNamesProvider subTableNamesProvider;

    public String getLibraryTableName(String taskKey, String tableName) {
        try {
            String libraryTableName;
            if (this.subTableNamesProvider != null && !(libraryTableName = this.subTableNamesProvider.getSubDatabaseTableName(taskKey, tableName)).isEmpty()) {
                return libraryTableName;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return tableName;
    }
}

