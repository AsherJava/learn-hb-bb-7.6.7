/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.JoinMode;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubTable
implements Serializable,
Cloneable,
ISQLPrintable {
    private static final long serialVersionUID = 5567324554873348552L;
    private ISQLTable table;
    private JoinMode joinMode;
    private List<FieldMap> fieldMaps;
    private List<ISQLFilter> filters;
    private int joinOptions;
    public static final int OPT_HASH_JOIN = 1;

    public SubTable() {
        this(null, JoinMode.INNER);
    }

    public SubTable(ISQLTable table) {
        this(table, JoinMode.INNER);
    }

    public SubTable(ISQLTable table, JoinMode joinMode) {
        this.table = table;
        this.joinMode = joinMode;
        this.fieldMaps = new ArrayList<FieldMap>();
        this.filters = new ArrayList<ISQLFilter>();
    }

    public ISQLTable table() {
        return this.table;
    }

    public void setTable(ISQLTable table) {
        this.table = table;
    }

    public JoinMode joinMode() {
        return this.joinMode;
    }

    public void setJoinMode(JoinMode joinMode) {
        this.joinMode = joinMode;
    }

    public List<FieldMap> fieldMaps() {
        return this.fieldMaps;
    }

    public List<ISQLFilter> filters() {
        return this.filters;
    }

    public int joinOptions() {
        return this.joinOptions;
    }

    public void setJoinOptions(int joinOptions) {
        this.joinOptions = joinOptions;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        this.toSQL(buffer, database, null);
    }

    public void toSQL(StringBuilder buffer, IDatabase database, JoinedTable owner) throws SQLModelException {
        if (this.joinMode == JoinMode.FULL && !database.getDescriptor().supportFullJoin()) {
            throw new SQLModelException(database.getTitle() + "\u5f53\u524d\u6570\u636e\u5e93\u4e0d\u652f\u6301FULL JOIN\u5173\u8054\u3002");
        }
        buffer.append(' ').append(this.getJoinKeyword(database)).append(' ');
        if (this.table.isSimpleMode()) {
            this.table.toSQL(buffer, database, 0);
        } else {
            buffer.append('(');
            this.table.toSQL(buffer, database, 1);
            buffer.append(')');
            SQLHelper.printAlias(database, this.table.alias(), 0, buffer);
        }
        buffer.append(" ON ");
        boolean started = false;
        for (FieldMap map : this.fieldMaps) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            map.toSQL(buffer, database, owner);
        }
        for (ISQLFilter filter : this.filters) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            filter.toSQL(buffer, database, 1);
        }
        if (!started) {
            buffer.append("1=1");
        }
    }

    private String getJoinKeyword(IDatabase database) {
        if (database.isDatabase("MSSQL") && (this.joinOptions & 1) != 0) {
            String expr = this.joinMode.sql();
            int p = expr.lastIndexOf(32);
            return expr.substring(0, p) + " HASH" + expr.substring(p);
        }
        return this.joinMode.sql();
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
    }

    public FieldMap addMap(ISQLField left, ISQLField right) {
        return this.addMap(left, right, 0);
    }

    public FieldMap addMap(ISQLField left, ISQLField right, boolean nullable) {
        FieldMap map = new FieldMap(left, right, nullable);
        this.fieldMaps.add(map);
        return map;
    }

    public FieldMap addMap(ISQLField left, ISQLField right, int offset) {
        if (left == null || right == null) {
            throw new NullPointerException("\u6620\u5c04\u5b57\u6bb5\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
        }
        FieldMap map = new FieldMap(left, right, offset);
        this.fieldMaps.add(map);
        return map;
    }

    public ISQLField addBaseField(String name, String alias) {
        return this.table.createField(name, alias);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("join mode: ").append((Object)this.joinMode).append(StringUtils.LINE_SEPARATOR);
        buffer.append("table : ").append(this.table).append(StringUtils.LINE_SEPARATOR);
        buffer.append("maps: ").append(this.fieldMaps).append(StringUtils.LINE_SEPARATOR);
        buffer.append("filters: ").append(this.filters);
        return buffer.toString();
    }

    public Object clone() {
        SubTable subTable;
        try {
            subTable = (SubTable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        subTable.fieldMaps = new ArrayList<FieldMap>(this.fieldMaps.size());
        for (FieldMap map : this.fieldMaps) {
            subTable.fieldMaps.add((FieldMap)map.clone());
        }
        subTable.filters = new ArrayList<ISQLFilter>(this.filters);
        return subTable;
    }
}

