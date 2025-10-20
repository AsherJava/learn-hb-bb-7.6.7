/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.assbalance;

import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FetchFloatRowResultExtractor
implements ResultSetExtractor<FetchFloatRowResult> {
    public FetchFloatRowResult extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<String[]> rowDatas = new ArrayList<String[]>();
        String[] rowData = null;
        LinkedHashMap<String, Integer> columns = new LinkedHashMap<String, Integer>(rs.getMetaData().getColumnCount());
        for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
            columns.put(rs.getMetaData().getColumnLabel(colIdx).toUpperCase(), colIdx - 1);
        }
        while (rs.next()) {
            rowData = new String[columns.size()];
            for (int i = 0; i < columns.size(); ++i) {
                rowData[i] = rs.getObject(i + 1) == null ? null : rs.getString(i + 1);
            }
            rowDatas.add(rowData);
        }
        FetchFloatRowResult fetchQueryResultVO = new FetchFloatRowResult();
        fetchQueryResultVO.setFloatColumns(columns);
        fetchQueryResultVO.setRowDatas(rowDatas);
        return fetchQueryResultVO;
    }
}

