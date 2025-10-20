/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.intf;

import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CustomFetchBalancePenetrateResultExtractor
implements ResultSetExtractor<List<PenetrateBalance>> {
    public List<PenetrateBalance> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<PenetrateBalance> rowDatas = new ArrayList<PenetrateBalance>();
        LinkedHashMap<String, Integer> columns = new LinkedHashMap<String, Integer>(rs.getMetaData().getColumnCount());
        for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
            columns.put(rs.getMetaData().getColumnLabel(colIdx).toUpperCase(), colIdx - 1);
        }
        while (rs.next()) {
            PenetrateBalance rowData = new PenetrateBalance();
            int i = 0;
            for (String key : columns.keySet()) {
                if (rs.getObject(i + 1) == null) {
                    rowData.put(key, null);
                } else {
                    rowData.put(key, rs.getObject(i + 1));
                }
                ++i;
            }
            rowDatas.add(rowData);
        }
        return rowDatas;
    }
}

