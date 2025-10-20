/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.dao;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;

public interface EntSqlDao {
    public int execute(EntDmlSql var1);

    public int[] executeBatch(EntDmlBatchSql var1);
}

