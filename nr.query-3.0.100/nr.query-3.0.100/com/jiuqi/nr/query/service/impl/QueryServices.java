/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.service.GridType;
import com.jiuqi.nr.query.service.QueryGridDefination;
import com.jiuqi.nr.query.service.impl.QueryData;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QueryServices {
    public static boolean hasQueryData(QueryBlockDefine block) {
        QueryData qdata = new QueryData();
        IGroupingTable table = qdata.getData(block, GridType.DETAIL, true);
        int count = table.getTotalCount();
        int detailCount = 0;
        for (int i = 0; i < count; ++i) {
            IDataRow row = table.getItem(i);
            if (row.getGroupingFlag() > 0) continue;
            QueryGridDefination.RowDataType valType = qdata.checkRowData(row);
            if (!block.isShowNullRow() && valType == QueryGridDefination.RowDataType.ALLNULL || !block.isShowZeroRow() && valType == QueryGridDefination.RowDataType.ALLZERO) continue;
            ++detailCount;
            break;
        }
        return detailCount > 0;
    }

    public static boolean hasQueryData(QueryModalDefine modal) {
        if (modal == null) {
            return false;
        }
        List<QueryBlockDefine> blocks = modal.getBlocks();
        if (modal.getBlocks() == null || modal.getBlocks().size() == 0) {
            return false;
        }
        return QueryServices.hasQueryData(blocks.get(0));
    }
}

