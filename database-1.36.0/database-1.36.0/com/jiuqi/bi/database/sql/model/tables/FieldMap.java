/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.CoalesceField;
import com.jiuqi.bi.database.sql.model.fields.NVLField;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import java.io.Serializable;

public class FieldMap
implements Serializable,
Cloneable,
ISQLPrintable {
    private static final long serialVersionUID = -466054449749713164L;
    private ISQLField left;
    private ISQLField right;
    private int offset;
    private boolean nullable;

    public FieldMap() {
    }

    public FieldMap(ISQLField left, ISQLField right) {
        this(left, right, 0);
    }

    public FieldMap(ISQLField left, ISQLField right, boolean nullable) {
        this(left, right, 0);
        this.nullable = nullable;
    }

    public FieldMap(ISQLField left, ISQLField right, int offset) {
        if (left == null || right == null) {
            throw new NullPointerException();
        }
        this.left = left;
        this.right = right;
        this.offset = offset;
    }

    public ISQLField left() {
        return this.left;
    }

    public void setLeft(ISQLField left) {
        this.left = left;
    }

    public ISQLField right() {
        return this.right;
    }

    public void setRight(ISQLField right) {
        this.right = right;
    }

    public int offset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        this.toSQL(buffer, database, null);
    }

    public void toSQL(StringBuilder buffer, IDatabase database, JoinedTable owner) throws SQLModelException {
        boolean nullJoin;
        boolean bl = nullJoin = this.nullable && this.offset == 0;
        if (nullJoin) {
            buffer.append('(');
        }
        FieldMap.printMapField(buffer, this.left, database, owner);
        buffer.append('=');
        FieldMap.printMapField(buffer, this.right, database, owner);
        if (this.offset > 0) {
            buffer.append('+').append(this.offset);
        } else if (this.offset < 0) {
            buffer.append(this.offset);
        }
        if (nullJoin) {
            buffer.append(" OR (");
            FieldMap.printMapField(buffer, this.left, database, owner);
            buffer.append(" IS NULL AND ");
            FieldMap.printMapField(buffer, this.right, database, owner);
            buffer.append(" IS NULL))");
        }
    }

    public static void printMapField(StringBuilder buffer, ISQLField field, IDatabase database, ISQLTable owner) throws SQLModelException {
        if ((field instanceof CoalesceField || field instanceof NVLField) && (owner == null || field.owner() == null || field.owner() == owner)) {
            field.toSQL(buffer, database, 1);
        } else if (field.owner() instanceof SimpleTable) {
            SQLHelper.printField(database, field.owner().tableName(), field.name(), buffer);
        } else {
            SQLHelper.printField(database, field.owner().tableName(), field.fieldName(), buffer);
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(this.left).append("]=[").append(this.right).append(']');
        if (this.offset > 0) {
            buffer.append('+').append(this.offset);
        } else if (this.offset < 0) {
            buffer.append(this.offset);
        }
        return buffer.toString();
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }
}

