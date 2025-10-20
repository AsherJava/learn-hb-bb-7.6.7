/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.fields;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.IGroupConvertor;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLModelPrinter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import java.io.Serializable;

public class RowNumField
implements ISQLField,
Serializable,
Cloneable {
    private static final long serialVersionUID = -5378867412054318189L;
    private ISQLTable owner;
    private String alias;
    private boolean visible;
    public static final String DEFAULT_ALIAS = "ROW_NUM";

    public RowNumField(ISQLTable owner, String alias) {
        this.owner = owner;
        this.alias = alias;
        this.visible = true;
    }

    public RowNumField(ISQLTable owner) {
        this(owner, DEFAULT_ALIAS);
    }

    @Override
    public String name() {
        return this.alias();
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
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        ISQLModelPrinter printer = database.createModelPrinter(this);
        if (printer != null) {
            printer.printSQL(buffer, database, this, options);
            return;
        }
        this.toSQL(buffer, database, options, true);
    }

    public void toSQL(StringBuilder buffer, IDatabase database, int options, boolean ignoreOrderBy) throws SQLModelException {
        if (!database.getDescriptor().supportWindowFunctions()) {
            throw new SQLModelException("\u5f53\u524d\u6570\u636e\u5e93\u672a\u652f\u6301\u5e8f\u53f7\u5b57\u6bb5\u3002");
        }
        if (this.owner == null) {
            throw new SQLModelException("\u672a\u6307\u5b9a\u5b57\u6bb5\u6240\u5c5e\u8868\uff0c\u65e0\u6cd5\u751f\u6210\u5e8f\u53f7\u5b57\u6bb5\u3002");
        }
        if (!ignoreOrderBy && this.owner.sortFields().isEmpty()) {
            throw new SQLModelException("\u672a\u6307\u5b9a\u6392\u5e8f\u5b57\u6bb5\u7684\u67e5\u8be2\u8868\u65e0\u6cd5\u751f\u6210\u5e8f\u53f7\u5b57\u6bb5\u3002");
        }
        buffer.append("ROW_NUMBER() OVER (");
        if (!this.owner.sortFields().isEmpty()) {
            buffer.append("ORDER BY ");
            boolean started = false;
            for (SortField sortField : this.owner.sortFields()) {
                if (started) {
                    buffer.append(", ");
                } else {
                    started = true;
                }
                sortField.toSQL(buffer, database, options & 0xFFFFFFFE);
            }
        }
        buffer.append(')');
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    @Override
    public ISQLTable owner() {
        return this.owner;
    }

    @Override
    public GroupMode groupMode() {
        return GroupMode.NONE;
    }

    @Override
    public void setGroupMode(GroupMode groupMode) {
    }

    @Override
    public String fieldName() {
        return this.alias;
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

    public String toString() {
        return this.alias();
    }
}

