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
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;

public class SQLField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String alias;
    private ISQLTable owner;
    private GroupMode groupMode;
    private boolean visible;
    private IGroupConvertor groupConvertor;

    public SQLField() {
        this(null, null, null);
    }

    public SQLField(ISQLTable owner, String name) {
        this(owner, name, null);
    }

    public SQLField(ISQLTable owner, String name, String alias) {
        this.owner = owner;
        this.name = name;
        this.alias = alias;
        this.visible = true;
    }

    @Override
    public String name() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
        return this.groupConvertor;
    }

    @Override
    public void setGroupConvertor(IGroupConvertor convertor) {
        this.groupConvertor = convertor;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.groupConvertor == null) {
            GroupMode.toSQL(buffer, this.groupMode(), database, this.getSQLFieldName(database, options));
        } else {
            this.groupConvertor.toSQL(buffer, this.groupMode(), database, this.getSQLFieldName(database, options));
        }
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    private String getSQLFieldName(IDatabase database, int options) {
        StringBuilder buffer = new StringBuilder();
        if ((options & 2) == 0) {
            if (!StringUtils.isEmpty((String)this.owner().alias())) {
                SQLHelper.printName(database, this.owner().alias(), buffer);
                buffer.append('.');
            } else if (!StringUtils.isEmpty((String)this.owner().name())) {
                SQLHelper.printName(database, this.owner().name(), buffer);
                buffer.append('.');
            }
        }
        SQLHelper.printName(database, this.name(), buffer);
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
        return StringUtils.isEmpty((String)this.alias) || this.owner instanceof SimpleTable ? this.name : this.alias;
    }

    public String toString() {
        try {
            return this.toSQL(DatabaseManager.getInstance().getDefaultDatabase(), 0);
        }
        catch (SQLModelException e) {
            return this.name;
        }
    }
}

