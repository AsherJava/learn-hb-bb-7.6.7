/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import java.util.HashMap;
import java.util.List;

public class QueryFilterValueClassify {
    private HashMap<String, QueryTableColFilterValues> sqlFilters = new HashMap();
    public HashMap<String, List<Object>> dimensionFilterValues;

    public final QueryTableColFilterValues getSqlColFilterValues(QueryTable queryTable) {
        QueryTableColFilterValues result = this.sqlFilters.get(queryTable.getAlias());
        return result;
    }

    public final QueryTableColFilterValues getSqlColFilterValuesByTable(QueryTable queryTable) {
        QueryTableColFilterValues result = this.sqlFilters.get(queryTable.getTableName());
        return result;
    }

    public final void addSqlColFilterValues(QueryField queryField, List<Object> filterValues) {
        QueryTable queryTable = queryField.getTable();
        QueryTableColFilterValues result = this.sqlFilters.get(queryTable.getAlias());
        if (result == null) {
            result = new QueryTableColFilterValues();
            this.sqlFilters.put(queryTable.getAlias(), result);
        }
        result.addColFilterValues(queryField, filterValues);
    }

    public final void addDimensionFilterValues(String dimension, List<Object> filterValues) {
        List<Object> filters;
        if (filterValues == null) {
            return;
        }
        if (this.dimensionFilterValues == null) {
            this.dimensionFilterValues = new HashMap();
        }
        if ((filters = this.dimensionFilterValues.get(dimension)) == null) {
            this.dimensionFilterValues.put(dimension, filterValues);
        } else {
            filters.retainAll(filterValues);
            this.dimensionFilterValues.put(dimension, filters);
        }
    }

    public final List<Object> getDimensionColFilterValues(String dimension) {
        if (this.dimensionFilterValues == null) {
            this.dimensionFilterValues = new HashMap();
        }
        if (!this.dimensionFilterValues.containsKey(dimension)) {
            return null;
        }
        return this.dimensionFilterValues.get(dimension);
    }
}

