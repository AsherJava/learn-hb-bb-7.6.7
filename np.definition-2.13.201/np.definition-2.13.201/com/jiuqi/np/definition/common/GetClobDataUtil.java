/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.nr.common.db.DatabaseInstance
 */
package com.jiuqi.np.definition.common;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.definition.internal.db.TransUtil;
import com.jiuqi.nr.common.db.DatabaseInstance;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetClobDataUtil {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getClobFieldData(ResultSet rs, String dbField) throws SQLException {
        if (!GetClobDataUtil.isSpecialDB()) {
            Clob o = rs.getClob(dbField);
            return TransUtil.transClob(o);
        }
        try (InputStream inputStream = rs.getBinaryStream(dbField);){
            String string = TransUtil.transClob(inputStream);
            return string;
        }
        catch (IOException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    public static boolean isSpecialDB() {
        IDatabase database = DatabaseInstance.getDatabase();
        return database.isDatabase("polardb") || database.isDatabase("POSTGRESQL");
    }
}

