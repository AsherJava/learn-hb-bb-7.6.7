/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.dc.base.common.jdbc.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StringResultSetExtractor
implements ResultSetExtractor<String> {
    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
}

