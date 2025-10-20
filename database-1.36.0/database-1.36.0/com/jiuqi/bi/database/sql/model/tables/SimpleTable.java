/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;

public class SimpleTable
extends AbstractTable {
    private static final long serialVersionUID = -1403780938301122333L;

    public SimpleTable() {
    }

    public SimpleTable(String name) {
        super(name);
    }

    public SimpleTable(String name, String alias) {
        super(name, alias);
    }

    @Override
    public boolean isSimpleMode() {
        return true;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(this.name());
        if (!StringUtils.isEmpty((String)this.alias())) {
            buffer.append(' ').append(this.alias());
        }
        return buffer.toString();
    }

    @Override
    public boolean containsParameter() {
        return false;
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        return null;
    }
}

