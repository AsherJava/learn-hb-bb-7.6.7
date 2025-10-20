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
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchThreeCondiResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;

public class CustomFetchThreeCondiResultSetExtractor
extends AbstractCustomFetchResultSetExtractor {
    public CustomFetchThreeCondiResultSetExtractor(CustomBizModelDTO customBizModel, CustomFetchArgs fetchArgs, ICustomFetchResultSetFilter filter) {
        super(customBizModel, fetchArgs, filter);
    }

    public CustomFetchThreeCondiResult extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<String, Map<String, Map<String, List<Object[]>>>> dataMap = new HashMap<String, Map<String, Map<String, List<Object[]>>>>(64);
        Pair<Map<String, Integer>, Map<String, ResultColumnType>> columnPair = this.buildColumnType();
        CustomFetchThreeCondiResult fetchResult = new CustomFetchThreeCondiResult((Map)columnPair.getFirst(), (Map)columnPair.getSecond());
        Object[] rowData = null;
        int rowCt = 0;
        String condiKey1 = this.getFetchArgs().getCondiList().get(0);
        String condiKey2 = this.getFetchArgs().getCondiList().get(1);
        String condiKey3 = this.getFetchArgs().getCondiList().get(2);
        String condiValue1 = null;
        String condiValue2 = null;
        String condiValue3 = null;
        ICustomFetchResultSetFilter filter = this.getFilter();
        while (rs.next()) {
            if (filter != null && filter.filter(rs)) continue;
            this.checkLimit(++rowCt);
            rowData = new Object[fetchResult.getColumnMap().size()];
            for (Map.Entry<String, Integer> columnEntry : fetchResult.getColumnMap().entrySet()) {
                int index = columnEntry.getValue();
                rowData[index] = this.parseColData(rs, index, columnEntry.getKey());
                if (columnEntry.getKey().equals(condiKey1)) {
                    condiValue1 = this.getCondiValue(rowData[index]);
                    continue;
                }
                if (columnEntry.getKey().equals(condiKey2)) {
                    condiValue2 = this.getCondiValue(rowData[index]);
                    continue;
                }
                if (!columnEntry.getKey().equals(condiKey3)) continue;
                condiValue3 = this.getCondiValue(rowData[index]);
            }
            dataMap.computeIfAbsent(condiValue1, key -> new HashMap(32));
            ((Map)dataMap.get(condiValue1)).computeIfAbsent(condiValue2, key -> new HashMap(32));
            ((Map)((Map)dataMap.get(condiValue1)).get(condiValue2)).computeIfAbsent(condiValue3, key -> new ArrayList(64));
            ((List)((Map)((Map)dataMap.get(condiValue1)).get(condiValue2)).get(condiValue3)).add(rowData);
        }
        fetchResult.setCondiKey1(condiKey1);
        fetchResult.setCondiKey2(condiKey2);
        fetchResult.setCondiKey3(condiKey3);
        fetchResult.setDataMap(dataMap);
        return fetchResult;
    }
}

