/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;

public class SortField
implements Serializable,
Cloneable,
ISQLPrintable {
    private static final long serialVersionUID = 7735854632696358571L;
    private ISQLField field;
    private int sortMode;
    private int nullsMode;
    private ISQLTable owner;
    public static final int ASC = 0;
    public static final int DESC = 1;
    public static final int NULLS_DEFAULT = 0;
    public static final int NULLS_FIRST = 1;
    public static final int NULLS_LAST = 2;

    public SortField() {
        this(null, 0);
    }

    public SortField(ISQLTable owner) {
        this(owner, null, 0);
    }

    public SortField(ISQLField field) {
        this(field, 0);
    }

    public SortField(ISQLTable owner, ISQLField field) {
        this(owner, field, 0);
    }

    public SortField(ISQLField field, int sortMode) {
        this.field = field;
        this.sortMode = sortMode;
        this.nullsMode = 0;
    }

    public SortField(ISQLTable owner, ISQLField field, int sortMode) {
        this.owner = owner;
        this.field = field;
        this.sortMode = sortMode;
        this.nullsMode = 0;
    }

    public ISQLTable owner() {
        return this.owner;
    }

    public void setOwner(ISQLTable owner) {
        this.owner = owner;
    }

    public ISQLField field() {
        return this.field;
    }

    public void setField(ISQLField field) {
        this.field = field;
    }

    public int sortMode() {
        return this.sortMode;
    }

    public void setSortMode(int sortMode) {
        this.sortMode = sortMode;
    }

    public int nullsMode() {
        return this.nullsMode;
    }

    public void setNullsMode(int nullsMode) {
        this.nullsMode = nullsMode;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        this.toSQL(buffer, database, options, false);
    }

    public void toSQL(StringBuilder buffer, IDatabase database, int options, boolean inline) throws SQLModelException {
        if (!database.getDescriptor().supportOrderNulls()) {
            if ("MYSQL".equalsIgnoreCase(database.getName())) {
                switch (this.nullsMode) {
                    case 1: {
                        buffer.append("ISNULL(");
                        this.printOrderByField(buffer, database, options, inline);
                        buffer.append(") DESC,");
                        break;
                    }
                    case 2: {
                        buffer.append("ISNULL(");
                        this.printOrderByField(buffer, database, options, inline);
                        buffer.append(") ASC,");
                    }
                }
            } else {
                switch (this.nullsMode) {
                    case 1: {
                        buffer.append("CASE WHEN ");
                        this.printOrderByField(buffer, database, options, inline);
                        buffer.append(" IS NULL THEN 0 ELSE 1 END,");
                        break;
                    }
                    case 2: {
                        buffer.append("CASE WHEN ");
                        this.printOrderByField(buffer, database, options, inline);
                        buffer.append(" IS NULL THEN 1 ELSE 0 END,");
                    }
                }
            }
        }
        this.printOrderByField(buffer, database, options, inline);
        if (this.sortMode == 1) {
            buffer.append(" DESC");
        }
        if (database.getDescriptor().supportOrderNulls()) {
            switch (this.nullsMode) {
                case 1: {
                    buffer.append(" NULLS FIRST");
                    break;
                }
                case 2: {
                    buffer.append(" NULLS LAST");
                }
            }
        }
    }

    public void printOrderByField(StringBuilder buffer, IDatabase database, int options, boolean inline) throws SQLModelException {
        if (this.owner == null) {
            SQLHelper.printField(database, this.field.owner().tableName(), this.field.fieldName(), buffer);
        } else if (this.owner != this.field.owner()) {
            if (this.field.owner() instanceof SimpleTable) {
                this.field.toSQL(buffer, database, 1);
            } else {
                SQLHelper.printField(database, this.field.owner().tableName(), this.field.fieldName(), buffer);
            }
        } else if (StringUtils.isEmpty((String)this.field.alias()) || inline) {
            this.field.toSQL(buffer, database, 1);
        } else {
            SQLHelper.printName(database, this.field.alias(), buffer);
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
        buffer.append(this.field).append(" [");
        if (this.sortMode == 1) {
            buffer.append("DESC");
        }
        switch (this.nullsMode) {
            case 1: {
                buffer.append(", NULLS FIRST");
                break;
            }
            case 2: {
                buffer.append(", NULLS LAST");
            }
        }
        buffer.append(']');
        return buffer.toString();
    }
}

