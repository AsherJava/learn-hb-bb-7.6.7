/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy
 *  com.jiuqi.bi.query.model.SortMode
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.bql.datasource.reader;

import com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy;
import com.jiuqi.bi.query.model.SortMode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EntityRowComparator
implements Comparator<IEntityRow> {
    private List<DataOrderBy> orderBys;
    private Map<String, Integer> orderCache;

    public EntityRowComparator(List<DataOrderBy> orderBys, Map<String, Integer> orderCache) {
        this.orderBys = orderBys;
        this.orderCache = orderCache;
    }

    @Override
    public int compare(IEntityRow o1, IEntityRow o2) {
        for (int i = 0; i < this.orderBys.size(); ++i) {
            Object value2;
            Object value1;
            DataOrderBy dataOrderBy = this.orderBys.get(i);
            String fieldName = dataOrderBy.getField().getPhysicalName();
            if (this.orderCache != null && this.orderCache.size() > 0 && dataOrderBy.getField().getName().equals("H_ORDER")) {
                value1 = this.orderCache.get(o1.getEntityKeyData());
                value2 = this.orderCache.get(o2.getEntityKeyData());
            } else {
                value1 = o1.getValue(fieldName);
                value2 = o2.getValue(fieldName);
            }
            int result = DataType.compareObject((Object)value1, (Object)value2);
            if (result == 0) continue;
            if (dataOrderBy.getMode() == SortMode.DESC) {
                result = 0 - result;
            }
            return result;
        }
        return 0;
    }
}

