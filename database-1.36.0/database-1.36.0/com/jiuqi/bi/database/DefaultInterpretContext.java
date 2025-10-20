/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database;

import com.jiuqi.bi.database.IInterpretContext;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Deprecated
public class DefaultInterpretContext
implements IInterpretContext {
    private int majorVer;
    private int minorVer;

    public DefaultInterpretContext(int majorVer, int minorVer) {
        this.majorVer = majorVer;
        this.minorVer = minorVer;
    }

    public DefaultInterpretContext(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        this.majorVer = metaData.getDatabaseMajorVersion();
        this.minorVer = metaData.getDatabaseMinorVersion();
    }

    @Override
    public int getMajorVer() {
        return this.majorVer;
    }

    @Override
    public int getMinorVer() {
        return this.minorVer;
    }
}

