/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.template.wapper.EntPreparedStatementWrapper;
import java.sql.SQLException;

public interface EntBatchPreparedStatementSetter {
    public void setValues(EntPreparedStatementWrapper var1, int var2) throws SQLException;

    public int getBatchSize();
}

