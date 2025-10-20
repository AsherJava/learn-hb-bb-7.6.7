/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

class CustomMemoryResultSetExtractor
implements ResultSetExtractor<List<Object[]>> {
    private Map<String, Integer> columnsMap;
    private Map<String, ColumnTypeEnum> fieldType;

    public CustomMemoryResultSetExtractor(Map<String, Integer> columnsMap, Map<String, ColumnTypeEnum> fieldType) {
        this.columnsMap = columnsMap;
        this.fieldType = fieldType;
    }

    public List<Object[]> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        block4: for (Map.Entry<String, Integer> column : this.columnsMap.entrySet()) {
            switch (rs.getMetaData().getColumnType(column.getValue())) {
                case 4: {
                    this.fieldType.put(column.getKey(), ColumnTypeEnum.INT);
                    continue block4;
                }
                case 2: 
                case 3: 
                case 6: 
                case 8: {
                    this.fieldType.put(column.getKey(), ColumnTypeEnum.NUMBER);
                    continue block4;
                }
            }
            this.fieldType.put(column.getKey(), ColumnTypeEnum.STRING);
        }
        while (rs.next()) {
            Object[] rowData = new Object[this.columnsMap.size()];
            for (int i = 1; i <= this.columnsMap.size(); ++i) {
                rowData[i - 1] = rs.getObject(i);
            }
            rowDatas.add(rowData);
        }
        return rowDatas;
    }
}

