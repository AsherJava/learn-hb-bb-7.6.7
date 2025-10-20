/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.sql.model.IInnerTableProvider;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLObject;
import com.jiuqi.bi.database.sql.model.SortField;
import java.util.Collection;
import java.util.List;

public interface ISQLTable
extends ISQLObject,
IInnerTableProvider {
    public List<ISQLField> fields();

    public Collection<ISQLFilter> whereFilters();

    public Collection<ISQLFilter> havingFilters();

    public List<SortField> sortFields();

    public boolean isSimpleMode();

    public boolean isGroupMode();

    public ISQLField findField(String var1);

    public ISQLField findByFieldName(String var1);

    public ISQLField createField(String var1, String var2);

    public ISQLField createField(String var1);

    public String tableName();

    public boolean containsParameter();

    @Override
    public List<ISQLTable> getInnerTables();
}

