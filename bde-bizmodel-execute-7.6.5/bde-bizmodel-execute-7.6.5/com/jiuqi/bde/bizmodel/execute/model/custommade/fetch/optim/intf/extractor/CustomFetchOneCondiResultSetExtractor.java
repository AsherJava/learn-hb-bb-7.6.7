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
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchOneCondiResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;

public class CustomFetchOneCondiResultSetExtractor
extends AbstractCustomFetchResultSetExtractor {
    public CustomFetchOneCondiResultSetExtractor(CustomBizModelDTO customBizModel, CustomFetchArgs fetchArgs, ICustomFetchResultSetFilter filter) {
        super(customBizModel, fetchArgs, filter);
    }

    public CustomFetchOneCondiResult extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, List<Object[]>> oneKeyDataMap = new HashMap<String, List<Object[]>>(64);
        Pair<Map<String, Integer>, Map<String, ResultColumnType>> columnPair = this.buildColumnType();
        CustomFetchOneCondiResult fetchResult = new CustomFetchOneCondiResult((Map)columnPair.getFirst(), (Map)columnPair.getSecond());
        Object[] rowData = null;
        int rowCt = 0;
        String condiKey = this.getFetchArgs().getCondiList().get(0);
        ICustomFetchResultSetFilter filter = this.getFilter();
        while (rs.next()) {
            if (filter != null && filter.filter(rs)) continue;
            this.checkLimit(++rowCt);
            rowData = new Object[fetchResult.getColumnMap().size()];
            String condiValue = null;
            for (Map.Entry<String, Integer> columnEntry : fetchResult.getColumnMap().entrySet()) {
                int index = columnEntry.getValue();
                rowData[index] = this.parseColData(rs, index, columnEntry.getKey());
                if (!columnEntry.getKey().equals(condiKey)) continue;
                condiValue = this.getCondiValue(rowData[index]);
            }
            oneKeyDataMap.computeIfAbsent(condiValue, key -> new ArrayList(128));
            ((List)oneKeyDataMap.get(condiValue)).add(rowData);
        }
        fetchResult.setCondiKey(condiKey);
        fetchResult.setDataMap(oneKeyDataMap);
        return fetchResult;
    }
}

