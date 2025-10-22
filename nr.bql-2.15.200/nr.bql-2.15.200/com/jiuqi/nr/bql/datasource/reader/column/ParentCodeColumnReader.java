/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.reader.column;

import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.reader.QueryColumnInfo;
import com.jiuqi.nr.bql.datasource.reader.column.QueryColumnReader;
import java.util.List;

public class ParentCodeColumnReader
extends QueryColumnReader {
    private int objectCodeIndex = -1;

    public ParentCodeColumnReader(QueryContext qContext, QueryColumnInfo columnInfo, List<QueryColumnInfo> columnInfos) {
        super(qContext, columnInfo);
        for (int index = 0; index < columnInfos.size(); ++index) {
            QueryColumnInfo info = columnInfos.get(index);
            if (!info.isObjectCode() || !info.getEntityDefine().getId().equals(columnInfo.getEntityDefine().getId())) continue;
            this.objectCodeIndex = info.getFieldIndex();
        }
    }

    @Override
    public Object readData(IDataRow dataRow) {
        Object value = super.readData(dataRow);
        String parentCode = null;
        if (this.columnInfo.isResetParentCode() && this.objectCodeIndex >= 0) {
            String objectcode = (String)dataRow.getValue(this.objectCodeIndex, this.dataType);
            parentCode = this.columnInfo.getParentObjectCode(objectcode);
        } else if (value != null) {
            parentCode = (String)value;
        }
        if ("-".equals(parentCode)) {
            return null;
        }
        return parentCode;
    }
}

