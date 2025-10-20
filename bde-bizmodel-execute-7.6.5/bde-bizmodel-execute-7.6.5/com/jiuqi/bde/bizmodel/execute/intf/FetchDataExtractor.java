/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FetchDataExtractor
implements ResultSetExtractor<FetchData> {
    public FetchData extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] rowData = null;
        LinkedHashMap<String, Integer> columns = new LinkedHashMap<String, Integer>(rs.getMetaData().getColumnCount());
        for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
            columns.put(rs.getMetaData().getColumnLabel(colIdx).toUpperCase(), colIdx - 1);
        }
        while (rs.next()) {
            rowData = new Object[columns.size()];
            for (int i = 0; i < columns.size(); ++i) {
                rowData[i] = rs.getObject(i + 1) == null ? null : (rs.getObject(i + 1) instanceof BigDecimal ? new BigDecimal(rs.getObject(i + 1).toString()) : (rs.getObject(i + 1) instanceof Integer ? Integer.valueOf(rs.getObject(i + 1).toString()) : rs.getObject(i + 1).toString()));
            }
            rowDatas.add(rowData);
        }
        return new FetchData(columns, rowDatas);
    }
}

