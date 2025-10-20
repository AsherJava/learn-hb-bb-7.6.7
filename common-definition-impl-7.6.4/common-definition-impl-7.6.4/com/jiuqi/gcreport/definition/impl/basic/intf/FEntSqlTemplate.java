/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.EntDqlSql;
import com.jiuqi.gcreport.definition.impl.exception.DefinitionSqlException;
import java.util.List;

public interface FEntSqlTemplate {
    public <K> List<K> queryByPaging(EntDqlSql<K> var1, int var2, int var3) throws DefinitionSqlException;

    public <K> K query(EntDqlSql<K> var1) throws DefinitionSqlException;

    public int execute(EntDmlSql var1) throws DefinitionSqlException;

    public int[] executeBatch(EntDmlBatchSql var1) throws DefinitionSqlException;

    public void executeCustomSql(String var1) throws DefinitionSqlException;

    public <T> List<T> queryFirstColumn(Class<T> var1, String var2, int var3, int var4, Object ... var5);
}

