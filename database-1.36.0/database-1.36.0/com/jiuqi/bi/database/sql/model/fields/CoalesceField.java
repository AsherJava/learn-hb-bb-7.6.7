/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.fields;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.IGroupConvertor;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoalesceField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = -6745323284091659326L;
    private List<ISQLField> fields;
    private String alias;
    private GroupMode groupMode;
    private ISQLTable owner;
    private boolean visible;

    public CoalesceField() {
        this(null, null);
    }

    public CoalesceField(ISQLTable owner) {
        this(owner, null);
    }

    public CoalesceField(ISQLTable owner, String alias) {
        this.owner = owner;
        this.alias = alias;
        this.fields = new ArrayList<ISQLField>();
        this.visible = true;
    }

    public List<ISQLField> fields() {
        return this.fields;
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

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.fields.size() < 2) {
            throw new SQLModelException("COALESCE()\u64cd\u4f5c\u7684\u5b57\u6bb5\u6570\u4e0d\u80fd\u5c11\u4e8e2\u4e2a\u3002");
        }
        buffer.append("COALESCE(");
        boolean started = false;
        for (ISQLField field : this.fields) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            SQLHelper.printRefField(database, field, buffer);
        }
        buffer.append(')');
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
    }

    @Override
    public String fieldName() {
        try {
            return StringUtils.isEmpty((String)this.alias) ? this.toSQL(DatabaseManager.getInstance().getDefaultDatabase(), 1) : this.alias;
        }
        catch (SQLModelException e) {
            return null;
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("COALESCE(");
        boolean started = false;
        for (ISQLField field : this.fields) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            buffer.append('[').append(field).append(']');
        }
        buffer.append(')');
        return buffer.toString();
    }

    public Object clone() {
        try {
            CoalesceField ret = (CoalesceField)super.clone();
            ret.fields = new ArrayList<ISQLField>(this.fields);
            return ret;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }
}

