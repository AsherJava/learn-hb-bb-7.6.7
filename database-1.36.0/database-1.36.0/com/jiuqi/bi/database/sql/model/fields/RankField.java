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
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class RankField
implements ISQLField {
    private ISQLTable owner;
    private boolean dense;
    private String alias;
    private boolean visible;
    private List<ISQLField> partitionFields;
    private List<SortField> orderFields;

    public RankField(ISQLTable owner, String alias) {
        this(owner, alias, false);
    }

    public RankField(ISQLTable owner, String alias, boolean dense) {
        this.owner = owner;
        this.dense = dense;
        this.alias = alias;
        this.visible = true;
        this.partitionFields = new ArrayList<ISQLField>();
        this.orderFields = new ArrayList<SortField>();
    }

    public boolean isDense() {
        return this.dense;
    }

    public void setDense(boolean dense) {
        this.dense = dense;
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
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        boolean flag;
        if (!database.getDescriptor().supportWindowFunctions()) {
            throw new SQLModelException("\u5f53\u524d\u6570\u636e\u4e0d\u652f\u6301\u6392\u540d\u64cd\u4f5c\uff1a" + database.getName());
        }
        buffer.append(this.dense ? "DENSE_RANK" : "RANK").append("() OVER (");
        if (!this.partitionFields.isEmpty()) {
            buffer.append("PARTITION BY ");
            flag = false;
            for (ISQLField field : this.partitionFields) {
                if (flag) {
                    buffer.append(',');
                } else {
                    flag = true;
                }
                field.toSQL(buffer, database, 1);
            }
            buffer.append(' ');
        }
        if (this.orderFields.isEmpty()) {
            throw new SQLModelException("\u6392\u540d\u67e5\u8be2\u65f6\u672a\u6307\u5b9a\u6392\u5e8f\u5b57\u6bb5\u3002");
        }
        buffer.append("ORDER BY ");
        flag = false;
        for (SortField sortField : this.orderFields) {
            if (flag) {
                buffer.append(',');
            } else {
                flag = true;
            }
            sortField.toSQL(buffer, database, 1, true);
        }
        buffer.append(')');
        if (!StringUtils.isEmpty((String)this.alias) && (options & 1) == 0) {
            buffer.append(' ').append(this.alias);
        }
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
        if (groupMode != GroupMode.NONE) {
            throw new RuntimeException("\u6392\u540d\u5b57\u6bb5\u7981\u6b62\u8fdb\u884c\u5206\u7ec4\u7edf\u8ba1");
        }
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

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public IGroupConvertor getGroupConvertor() {
        return null;
    }

    @Override
    public void setGroupConvertor(IGroupConvertor convertor) {
    }

    public List<ISQLField> partitionFields() {
        return this.partitionFields;
    }

    public List<SortField> orderFields() {
        return this.orderFields;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.dense ? "DENSE_RANK" : "RANK").append("(partions:").append(this.partitionFields).append(" order by:").append(this.orderFields).append(")");
        return buffer.toString();
    }
}

