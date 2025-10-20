/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.fields;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.IGroupConvertor;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.security.InvalidParameterException;

public class RefField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = 6083785971276224544L;
    private ISQLField field;
    private String alias;
    private ISQLTable owner;
    private GroupMode groupMode;
    private boolean visible;
    private IGroupConvertor groupConvertor;

    public RefField() {
        this(null, null, null);
    }

    public RefField(ISQLTable owner, ISQLField field) {
        this(owner, field, null);
    }

    public RefField(ISQLTable owner, ISQLField field, String alias) {
        if (field == null) {
            throw new InvalidParameterException();
        }
        this.owner = owner;
        this.field = field;
        this.alias = alias;
        this.visible = true;
    }

    @Override
    public String name() {
        return StringUtils.isEmpty((String)this.field.alias()) ? this.field.name() : this.field.alias();
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

    public ISQLField field() {
        return this.field;
    }

    public void setField(ISQLField field) {
        this.field = field;
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
        return this.groupConvertor;
    }

    @Override
    public void setGroupConvertor(IGroupConvertor convertor) {
        this.groupConvertor = convertor;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.groupConvertor == null) {
            GroupMode.toSQL(buffer, this.groupMode(), database, this.getRefFieldName(database, options));
        } else {
            this.groupConvertor.toSQL(buffer, this.groupMode(), database, this.getRefFieldName(database, options));
        }
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    private String getRefFieldName(IDatabase database, int options) {
        StringBuilder buffer = new StringBuilder();
        if ((options & 2) == 0) {
            SQLHelper.printField(database, this.field.owner().tableName(), this.field.fieldName(), buffer);
        } else {
            SQLHelper.printName(database, this.field.fieldName(), buffer);
        }
        return buffer.toString();
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
    }

    @Override
    public String fieldName() {
        return StringUtils.isEmpty((String)this.alias) ? this.field.fieldName() : this.alias;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("field: ").append(this.field);
        if (!StringUtils.isEmpty((String)this.alias)) {
            buffer.append(", ").append(this.alias);
        }
        return buffer.toString();
    }
}

