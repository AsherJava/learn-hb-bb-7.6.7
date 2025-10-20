/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.util.Pair
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchArgs;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ICustomFetchResultSetFilter;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor.AbstractCustomFetchResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchSimpleResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;

public class CustomFetchSimpleResultSetExtractor
extends AbstractCustomFetchResultSetExtractor {
    public CustomFetchSimpleResultSetExtractor(CustomBizModelDTO customBizModel, CustomFetchArgs fetchArgs, ICustomFetchResultSetFilter filter) {
        super(customBizModel, fetchArgs, filter);
    }

    public CustomFetchSimpleResult extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>(512);
        Pair<Map<String, Integer>, Map<String, ResultColumnType>> columnPair = this.buildColumnType();
        CustomFetchSimpleResult customFetchResult = new CustomFetchSimpleResult((Map)columnPair.getFirst(), (Map)columnPair.getSecond());
        Object[] rowData = null;
        int rowCt = 0;
        ICustomFetchResultSetFilter filter = this.getFilter();
        while (rs.next()) {
            if (filter != null && filter.filter(rs)) continue;
            this.checkLimit(++rowCt);
            rowData = new Object[customFetchResult.getColumnMap().size()];
            for (Map.Entry<String, Integer> columnEntry : customFetchResult.getColumnMap().entrySet()) {
                int index = columnEntry.getValue();
                rowData[index] = this.parseColData(rs, index, columnEntry.getKey());
            }
            rowDatas.add(rowData);
        }
        customFetchResult.setRowDatas(rowDatas);
        return customFetchResult;
    }
}

