/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.common.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ExistsResultSetExtractor
implements ResultSetExtractor<Boolean> {
    public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return true;
        }
        return false;
    }
}

