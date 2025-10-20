/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.assbalance;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SeniorFetchFloatRowResultExtractor
implements ResultSetExtractor<FetchFloatRowResult> {
    public FetchFloatRowResult extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<String[]> rowDatas = new ArrayList<String[]>();
        String[] rowData = null;
        LinkedHashMap<String, Integer> columns = new LinkedHashMap<String, Integer>(rs.getMetaData().getColumnCount());
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
            columns.put(rs.getMetaData().getColumnLabel(colIdx).toUpperCase(), colIdx - 1);
            map.put(rs.getMetaData().getColumnLabel(colIdx).toUpperCase(), rs.getMetaData().getColumnType(colIdx));
        }
        while (rs.next()) {
            rowData = new String[columns.size()];
            for (int i = 0; i < columns.size(); ++i) {
                rowData[i] = rs.getObject(i + 1) == null ? null : rs.getString(i + 1);
            }
            rowDatas.add(rowData);
        }
        HashMap fieldTypeMap = new HashMap();
        block7: for (Map.Entry entry : map.entrySet()) {
            switch ((Integer)entry.getValue()) {
                case 4: {
                    fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.INT);
                    continue block7;
                }
                case 2: 
                case 3: 
                case 6: 
                case 8: {
                    fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.NUMBER);
                    continue block7;
                }
            }
            fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.STRING);
        }
        FetchFloatRowResult fetchQueryResultVO = new FetchFloatRowResult();
        fetchQueryResultVO.setFloatColumns(columns);
        fetchQueryResultVO.setRowDatas(rowDatas);
        fetchQueryResultVO.setFloatColumnsType(fieldTypeMap);
        return fetchQueryResultVO;
    }
}

