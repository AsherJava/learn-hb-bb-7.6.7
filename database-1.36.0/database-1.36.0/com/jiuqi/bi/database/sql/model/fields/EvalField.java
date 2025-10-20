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

public class EvalField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = 3218225107406067219L;
    private ISQLTable owner;
    private String expression;
    private String alias;
    private GroupMode groupMode;
    private boolean visible;
    private IGroupConvertor groupConvertor;

    public EvalField(ISQLTable owner) {
        this(owner, null, null);
    }

    public EvalField(ISQLTable owner, String expression) {
        this(owner, expression, null);
    }

    public EvalField(ISQLTable owner, String expression, String alias) {
        this.owner = owner;
        this.expression = expression;
        this.alias = alias;
        this.visible = true;
    }

    public String expression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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
            GroupMode.toSQL(buffer, this.groupMode(), database, this.expression());
        } else {
            this.groupConvertor.toSQL(buffer, this.groupMode(), database, this.expression());
        }
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
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
    public void setGroupMode(GroupMode groupMode) {
        this.groupMode = groupMode;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }

    @Override
    public String fieldName() {
        return StringUtils.isEmpty((String)this.alias) ? this.expression : this.alias;
    }

    public String toString() {
        return this.expression;
    }
}

