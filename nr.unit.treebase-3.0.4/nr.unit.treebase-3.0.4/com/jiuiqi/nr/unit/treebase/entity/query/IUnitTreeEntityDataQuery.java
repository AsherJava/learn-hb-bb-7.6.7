/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.List;

public interface IUnitTreeEntityDataQuery {
    public IEntityTable makeIEntityTable(IUnitTreeContext var1);

    public IEntityTable makeIEntityTable(IUnitTreeContext var1, boolean var2, boolean var3);

    public IEntityTable makeIEntityTable(IUnitTreeContext var1, List<String> var2);

    public IEntityTable makeIEntityTable(IUnitTreeContext var1, String ... var2);

    public IEntityTable makeIEntityTable(IUnitTreeContext var1, List<String> var2, String ... var3);

    public IEntityTable makeFullTreeData(IUnitTreeContext var1);

    public IEntityTable makeRangeFullTreeData(IUnitTreeContext var1, List<String> var2);

    public IEntityTable makeRangeFullTreeData(IUnitTreeContext var1, List<String> var2, boolean var3);

    public static String buildFilters(IUnitTreeContext context, String ... filters) {
        if (context.getRowFilterExpression() == null || context.getRowFilterExpression().isEmpty()) {
            return IUnitTreeEntityDataQuery.buildFilters(filters);
        }
        String[] filterWithExpression = new String[filters.length + 1];
        System.arraycopy(filters, 0, filterWithExpression, 0, filters.length);
        filterWithExpression[filters.length] = context.getRowFilterExpression();
        return IUnitTreeEntityDataQuery.buildFilters(filterWithExpression);
    }

    public static String buildFilters(String ... filters) {
        if (filters != null && filters.length > 0) {
            StringBuilder expression = new StringBuilder("(" + filters[0] + ")");
            for (int i = 1; i < filters.length; ++i) {
                expression.append(" AND (").append(filters[i]).append(") ");
            }
            return expression.toString();
        }
        return null;
    }
}

