/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dql;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;

public interface EntDqlSql<R>
extends EntSql {
    public EntResultSetExtractor<R> getResultSetExtractor();
}

