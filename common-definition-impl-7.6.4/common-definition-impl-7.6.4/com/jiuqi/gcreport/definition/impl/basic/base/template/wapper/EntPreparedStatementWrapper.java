/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template.wapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class EntPreparedStatementWrapper {
    private PreparedStatement ps;

    public EntPreparedStatementWrapper(PreparedStatement ps) {
        this.ps = ps;
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        if (x == null) {
            this.ps.setObject(parameterIndex, null);
        } else if (x instanceof UUID) {
            x = EntPreparedStatementWrapper.toBytes((UUID)x);
            this.ps.setObject(parameterIndex, x);
        } else if (x instanceof byte[]) {
            this.ps.setString(parameterIndex, new String((byte[])x));
        } else if (x instanceof Date) {
            this.ps.setObject(parameterIndex, new Timestamp(((Date)x).getTime()));
        } else if (x instanceof Boolean) {
            this.ps.setBoolean(parameterIndex, (Boolean)x);
        } else {
            this.ps.setObject(parameterIndex, x);
        }
    }

    private static byte[] toBytes(UUID value) {
        int i;
        if (value == null) {
            return null;
        }
        byte[] bytes = new byte[16];
        long sb = value.getLeastSignificantBits();
        for (i = 15; i >= 8; --i) {
            bytes[i] = (byte)sb;
            sb >>>= 8;
        }
        sb = value.getMostSignificantBits();
        for (i = 7; i >= 0; --i) {
            bytes[i] = (byte)sb;
            sb >>>= 8;
        }
        return bytes;
    }
}

