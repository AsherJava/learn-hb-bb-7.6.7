/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.assbalance;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FetchDataResultExtractor
implements ResultSetExtractor<FetchData> {
    public FetchData extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        LinkedHashMap<String, Integer> columns = new LinkedHashMap<String, Integer>(rs.getMetaData().getColumnCount());
        for (int colIdx = 0; colIdx < rs.getMetaData().getColumnCount(); ++colIdx) {
            columns.put(rs.getMetaData().getColumnLabel(colIdx + 1).toUpperCase(), colIdx);
        }
        int size = columns.size();
        while (rs.next()) {
            Object[] rowData = new Object[size];
            for (Map.Entry entry : columns.entrySet()) {
                String name = ((String)entry.getKey()).toUpperCase();
                int index = (Integer)columns.get(name);
                if (rs.getObject(index + 1) == null) {
                    rowData[index] = null;
                    continue;
                }
                if (rs.getObject(index + 1) instanceof BigDecimal) {
                    rowData[index] = rs.getBigDecimal(index + 1);
                    continue;
                }
                if (rs.getObject(index + 1) instanceof Integer) {
                    rowData[index] = rs.getInt(index + 1);
                    continue;
                }
                rowData[index] = rs.getObject(index + 1).toString();
            }
            rowDatas.add(rowData);
        }
        return new FetchData(columns, rowDatas);
    }
}

