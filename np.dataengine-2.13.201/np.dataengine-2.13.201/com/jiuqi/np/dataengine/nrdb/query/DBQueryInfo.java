/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.nrdb.query.GroupQueryInfo;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;

public class DBQueryInfo {
    public QueryFields QueryFields;
    public QueryTable primaryTable;
    public IASTNode rowFilterNode;
    public DimensionSet loopDimensions;
    public HashMap<String, ColumnModelDefine> dimensionFields;
    public HashMap<String, Object> unitKeyMap;
    public QueryFilterValueClassify colValueFilters;
    public boolean needMemoryFilter;
    public boolean useDefaultOrderBy = true;
    public boolean ignoreDefaultOrderBy = false;
    public ArrayList<OrderByItem> orderByItems;
    public GroupQueryInfo groupQueryInfo;
    public boolean queryMode = false;

    public QueryTableColFilterValues getColFilterValues() {
        if (this.colValueFilters != null) {
            return this.colValueFilters.getSqlColFilterValues(this.primaryTable);
        }
        return null;
    }
}

