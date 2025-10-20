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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class NVLField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = -3374495435852159282L;
    private List<ISQLField> fields;
    private String alias;
    private GroupMode groupMode;
    private ISQLTable owner;
    private boolean visible;

    public NVLField() {
        this(null, null);
    }

    public NVLField(ISQLTable owner) {
        this(owner, null);
    }

    public NVLField(ISQLTable owner, String alias) {
        this.owner = owner;
        this.alias = alias;
        this.fields = new ArrayList<ISQLField>();
        this.visible = true;
    }

    public NVLField(ISQLTable owner, String alias, Collection<ISQLField> fields) {
        this.owner = owner;
        this.alias = alias;
        this.fields = new ArrayList<ISQLField>(fields);
        this.visible = true;
    }

    public NVLField(ISQLTable owner, String alias, ISQLField ... fields) {
        this(owner, alias, Arrays.asList(fields));
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
            throw new SQLModelException("NVL()\u64cd\u4f5c\u7684\u5b57\u6bb5\u6570\u4e0d\u80fd\u5c11\u4e8e2\u4e2a\u3002");
        }
        if (this.groupMode == null || this.groupMode == GroupMode.GROUP) {
            this.printNVL(buffer, database, options, this.fields);
        } else {
            String expr = this.printNVL(database, options, this.fields);
            GroupMode.toSQL(buffer, this.groupMode, database, expr);
        }
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    private void printNVL(StringBuilder buffer, IDatabase database, int options, List<ISQLField> fields) throws SQLModelException {
        buffer.append(this.getNVLName(database));
        buffer.append('(');
        this.printField(database, buffer, fields.get(0));
        buffer.append(',');
        if (fields.size() == 2) {
            this.printField(database, buffer, fields.get(1));
        } else {
            this.printNVL(buffer, database, options, fields.subList(1, fields.size()));
        }
        buffer.append(')');
    }

    private String printNVL(IDatabase database, int options, List<ISQLField> fields) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.printNVL(buffer, database, options, fields);
        return buffer.toString();
    }

    private void printField(IDatabase database, StringBuilder buffer, ISQLField field) throws SQLModelException {
        if (field.owner() != this.owner && this.owner != null && field.owner() != null) {
            SQLHelper.printField(database, field.owner().tableName(), field.fieldName(), buffer);
        } else {
            SQLHelper.printRefField(database, field, buffer);
        }
    }

    private Object getNVLName(IDatabase database) throws SQLModelException {
        return database.getDescriptor().getNVLName();
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
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder("NVL(");
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
            NVLField ret = (NVLField)super.clone();
            ret.fields = new ArrayList<ISQLField>(this.fields);
            return ret;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }
}

