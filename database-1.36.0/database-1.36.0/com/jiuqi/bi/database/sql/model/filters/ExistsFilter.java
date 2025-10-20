/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.IInnerTableProvider;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ExistsFilter
implements ISQLFilter,
IInnerTableProvider {
    private ISQLTable filterTable;
    private List<FieldMap> fieldMaps;
    private List<ISQLFilter> filters;
    private List<SubTable> subTables;

    public ExistsFilter() {
        this(null);
    }

    public ExistsFilter(ISQLTable filterTable) {
        this.filterTable = filterTable;
        this.fieldMaps = new ArrayList<FieldMap>();
        this.filters = new ArrayList<ISQLFilter>();
        this.subTables = new ArrayList<SubTable>();
    }

    public ISQLTable filterTable() {
        return this.filterTable;
    }

    public void setFilterTable(ISQLTable mainTable) {
        this.filterTable = mainTable;
    }

    public List<FieldMap> fieldMaps() {
        return this.fieldMaps;
    }

    public FieldMap addMap(ISQLField left, ISQLField right) {
        FieldMap map = new FieldMap(left, right);
        this.fieldMaps.add(map);
        return map;
    }

    public List<ISQLFilter> filters() {
        return this.filters;
    }

    public List<SubTable> subTables() {
        return this.subTables;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("EXISTS(SELECT 1 FROM ");
        if (this.filterTable.isSimpleMode()) {
            this.filterTable.toSQL(buffer, database, 0);
        } else {
            buffer.append('(');
            this.filterTable.toSQL(buffer, database, 1);
            buffer.append(')');
            SQLHelper.printAlias(database, this.filterTable.alias(), 0, buffer);
        }
        for (SubTable subTable : this.subTables) {
            subTable.toSQL(buffer, database, 0);
        }
        boolean started = false;
        for (FieldMap map : this.fieldMaps) {
            if (started) {
                buffer.append(" AND ");
            } else {
                buffer.append(" WHERE ");
                started = true;
            }
            map.toSQL(buffer, database, 0);
        }
        for (ISQLFilter filter : this.filters) {
            if (started) {
                buffer.append(" AND ");
            } else {
                buffer.append(" WHERE ");
                started = true;
            }
            filter.toSQL(buffer, database, 0);
        }
        buffer.append(')');
    }

    @Override
    public String toSQL(IDatabase dbType, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, dbType, options);
        return buffer.toString();
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        ArrayList<ISQLTable> innerTables = new ArrayList<ISQLTable>();
        innerTables.add(this.filterTable);
        for (SubTable subTable : this.subTables) {
            innerTables.add(subTable.table());
        }
        for (ISQLFilter filter : this.filters) {
            if (!(filter instanceof IInnerTableProvider)) continue;
            List<ISQLTable> filterTables = ((IInnerTableProvider)((Object)filter)).getInnerTables();
            innerTables.addAll(filterTables);
        }
        return innerTables;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("main table: ").append(this.filterTable).append(StringUtils.LINE_SEPARATOR);
        buffer.append("field maps: ").append(this.fieldMaps).append(StringUtils.LINE_SEPARATOR);
        buffer.append("filters: ").append(this.filters).append(StringUtils.LINE_SEPARATOR);
        buffer.append("sub tables: ").append(this.subTables);
        return buffer.toString();
    }
}

