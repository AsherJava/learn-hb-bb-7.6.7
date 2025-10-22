/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionImpl;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.internal.obj.QueryConParam;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class QueryConditionUtil {
    private QueryConditionUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static QueryConParam parse(QueryCondition condition) {
        QueryConditionImpl queryConditionImpl;
        List<QueryFilter> queryFilters;
        if (condition instanceof QueryConditionImpl && !CollectionUtils.isEmpty(queryFilters = (queryConditionImpl = (QueryConditionImpl)condition).getQueryFilters())) {
            HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
            Boolean haveDes = null;
            Integer desStateCode = null;
            String ckdKeyWordPattern = null;
            for (QueryFilter queryFilter : queryFilters) {
                QueryCol queryCol = queryFilter.getQueryCol();
                QueryFilterOperator filterOperator = queryFilter.getFilterOperator();
                Object filterValue = queryFilter.getFilterValue();
                if (queryCol == QueryCol.FORMULA_CHECK_TYPE) {
                    if (filterOperator == QueryFilterOperator.EQUALS) {
                        checkTypes.put((Integer)filterValue, null);
                        continue;
                    }
                    if (filterOperator != QueryFilterOperator.IN) continue;
                    List valueList = (List)filterValue;
                    for (Integer checkType : valueList) {
                        checkTypes.put(checkType, null);
                    }
                    continue;
                }
                if (queryCol == QueryCol.CHECK_ERROR_DES) {
                    if (filterOperator == QueryFilterOperator.IS_NOTNULL) {
                        haveDes = true;
                        continue;
                    }
                    if (filterOperator == QueryFilterOperator.ISNULL) {
                        haveDes = false;
                        continue;
                    }
                    if (filterOperator != QueryFilterOperator.LIKE) continue;
                    ckdKeyWordPattern = filterValue.toString();
                    continue;
                }
                if (queryCol != QueryCol.ERROR_DES_STATE || !(filterValue instanceof Integer)) continue;
                desStateCode = (Integer)filterValue;
            }
            for (Integer checkType : checkTypes.keySet()) {
                checkTypes.put(checkType, haveDes);
            }
            return new QueryConParam(checkTypes, desStateCode, ckdKeyWordPattern);
        }
        return new QueryConParam(Collections.emptyMap(), null, null);
    }
}

