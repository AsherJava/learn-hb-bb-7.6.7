/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy
 *  com.jiuqi.bi.adhoc.datasource.reader.DataTable
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.datasource.reader;

import com.jiuqi.bi.adhoc.datasource.reader.DataOrderBy;
import com.jiuqi.bi.adhoc.datasource.reader.DataTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;

public class EntityOrderItem {
    public ColumnModelDefine column;
    public List<DataOrderBy> orderBys = new ArrayList<DataOrderBy>();
    public DataTable entityDataTable;
    public String dimensionName;
}

