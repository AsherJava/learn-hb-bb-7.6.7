/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;
import java.util.HashMap;
import java.util.List;

public class MainDimTitleColumnReader
extends QueryColumnReader {
    private HashMap<String, String> titleMap;
    private int entityKeyIndex = -1;

    public MainDimTitleColumnReader(QueryContext qContext, QueryColumnInfo columnInfo, List<QueryColumnInfo> columnInfos) {
        super(qContext, columnInfo);
        this.titleMap = qContext.getMainDimTitlesCache();
        for (int index = 0; index < columnInfos.size(); ++index) {
            QueryColumnInfo info = columnInfos.get(index);
            if (!info.isEntityKey() || !info.getDataColumnModel().getID().equals(columnInfo.getDataColumnModel().getID())) continue;
            this.entityKeyIndex = info.getFieldIndex();
        }
    }

    @Override
    public Object readData(IDataRow dataRow) {
        String entityKeyData;
        String catchedTitle;
        Object value = super.readData(dataRow);
        if (this.entityKeyIndex >= 0 && (catchedTitle = this.titleMap.get(entityKeyData = (String)dataRow.getValue(this.entityKeyIndex, this.dataType))) != null) {
            return catchedTitle;
        }
        return value;
    }
}

