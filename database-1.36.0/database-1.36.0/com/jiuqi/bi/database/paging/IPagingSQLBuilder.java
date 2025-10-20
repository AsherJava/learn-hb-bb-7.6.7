/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.paging;

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.paging.OrderField;
import java.util.List;

public interface IPagingSQLBuilder {
    public String getWithSQL();

    public void setWithSQL(String var1);

    public String getInnerTableAlias();

    public void setInnerTableAlias(String var1);

    public String getRawSQL();

    public void setRawSQL(String var1);

    public String getFilter();

    public void setFilter(String var1);

    public List<OrderField> getOrderFields();

    public String buildSQL(int var1, int var2) throws DBException;
}

