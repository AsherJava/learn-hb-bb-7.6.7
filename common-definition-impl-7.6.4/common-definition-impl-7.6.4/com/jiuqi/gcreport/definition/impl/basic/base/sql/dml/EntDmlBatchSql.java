/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dml;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntBatchPreparedStatementSetter;

public interface EntDmlBatchSql
extends EntDmlSql {
    public EntBatchPreparedStatementSetter getBatchPreStatementSetter();
}

