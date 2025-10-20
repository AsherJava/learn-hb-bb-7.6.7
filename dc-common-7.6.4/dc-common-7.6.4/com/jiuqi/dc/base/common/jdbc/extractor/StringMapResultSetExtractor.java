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
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StringMapResultSetExtractor
implements ResultSetExtractor<Map<String, String>> {
    public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, String> result = new HashMap<String, String>();
        while (rs.next()) {
            result.put(rs.getString(1), rs.getString(2));
        }
        return result;
    }
}

