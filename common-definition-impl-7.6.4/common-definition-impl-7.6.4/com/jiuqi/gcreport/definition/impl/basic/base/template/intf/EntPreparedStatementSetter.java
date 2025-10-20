/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.template.wapper.EntPreparedStatementWrapper;
import java.sql.SQLException;

@FunctionalInterface
public interface EntPreparedStatementSetter {
    public void setValues(EntPreparedStatementWrapper var1) throws SQLException;
}

