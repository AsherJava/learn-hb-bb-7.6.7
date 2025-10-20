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
import java.util.HashSet;
import java.util.Set;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StringSetResultSetExtractor
implements ResultSetExtractor<Set<String>> {
    public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashSet<String> result = new HashSet<String>();
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }
}

