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
import java.util.Date;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DateResultSetExtractor
implements ResultSetExtractor<Date> {
    public Date extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next() && rs.getTimestamp(1) != null) {
            return new Date(rs.getTimestamp(1).getTime());
        }
        return null;
    }
}

