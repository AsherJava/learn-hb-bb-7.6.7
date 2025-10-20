/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.fields;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.IGroupConvertor;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.JoinMode;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import java.io.Serializable;

public class LookupField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = -3684895486522217502L;
    private ISQLTable owner;
    private String alias;
    private GroupMode groupMode;
    private ISQLField retField;
    private SubTable table;
    private boolean distinct;
    private boolean visible;

    public LookupField() {
        this(null, null);
    }

    public LookupField(ISQLTable owner) {
        this(owner, null);
    }

    public LookupField(ISQLTable owner, String alias) {
        this.owner = owner;
        this.alias = alias;
        this.table = new SubTable();
        this.table.setJoinMode(JoinMode.INNER);
        this.distinct = false;
        this.visible = true;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String alias() {
        return this.alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public ISQLTable owner() {
        return this.owner;
    }

    public void setOwner(ISQLTable owner) {
        this.owner = owner;
    }

    @Override
    public GroupMode groupMode() {
        return this.groupMode;
    }

    @Override
    public void setGroupMode(GroupMode mode) {
        this.groupMode = mode;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public IGroupConvertor getGroupConvertor() {
        return null;
    }

    @Override
    public void setGroupConvertor(IGroupConvertor convertor) {
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public ISQLField retField() {
        return this.retField;
    }

    public void setRetField(ISQLField retField) {
        this.retField = retField;
    }

    public SubTable table() {
        return this.table;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("(SELECT ");
        this.printField(buffer, database);
        buffer.append(" FROM ");
        this.table().table().toSQL(buffer, database, 0);
        if (!this.table().fieldMaps().isEmpty() || !this.table().filters().isEmpty()) {
            buffer.append(" WHERE ");
            boolean started = false;
            for (FieldMap map : this.table().fieldMaps()) {
                if (started) {
                    buffer.append(" AND ");
                } else {
                    started = true;
                }
                this.printMap(map, buffer, database);
            }
            for (ISQLFilter filter : this.table().filters()) {
                if (started) {
                    buffer.append(" AND ");
                } else {
                    started = true;
                }
                filter.toSQL(buffer, database, 1);
            }
        }
        buffer.append(')');
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    private void printField(StringBuilder buffer, IDatabase database) throws SQLModelException {
        if (this.distinct) {
            buffer.append("DISTINCT ");
            this.retField().toSQL(buffer, database, 1);
        } else if (this.groupMode != null && this.groupMode != GroupMode.GROUP) {
            buffer.append((Object)this.groupMode).append('(');
            this.retField().toSQL(buffer, database, 1);
            buffer.append(')');
        } else {
            this.retField().toSQL(buffer, database, 1);
        }
    }

    public void printMap(FieldMap map, StringBuilder buffer, IDatabase database) throws SQLModelException {
        boolean nullJoin;
        boolean bl = nullJoin = map.isNullable() && map.offset() == 0;
        if (nullJoin) {
            buffer.append('(');
        }
        this.printMapField(buffer, map.left(), database);
        buffer.append('=');
        this.printMapField(buffer, map.right(), database);
        if (map.offset() > 0) {
            buffer.append('+').append(map.offset());
        } else if (map.offset() < 0) {
            buffer.append(map.offset());
        }
        if (nullJoin) {
            buffer.append(" OR (");
            this.printMapField(buffer, map.left(), database);
            buffer.append(" IS NULL AND ");
            this.printMapField(buffer, map.right(), database);
            buffer.append(" IS NULL))");
        }
    }

    private void printMapField(StringBuilder buffer, ISQLField field, IDatabase database) throws SQLModelException {
        if (field instanceof EvalField && field.owner() == this.owner) {
            field.toSQL(buffer, database, 1);
        } else {
            FieldMap.printMapField(buffer, field, database, this.owner);
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
    }

    @Override
    public String fieldName() {
        return this.alias;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("field: ").append(this.retField).append(", from :").append(this.table);
        return buffer.toString();
    }
}

