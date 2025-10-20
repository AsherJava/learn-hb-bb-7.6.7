/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dql;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.SelectSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;

public class SelectCountSql
extends SelectSql<Integer> {
    public SelectCountSql(String tableName) {
        super(tableName, null);
        this.addColumn("COUNT(*)", "DATANUM");
    }

    @Override
    public EntResultSetExtractor<Integer> getResultSetExtractor() {
        return rs -> rs.getObject(1, Integer.class);
    }
}

