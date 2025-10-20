/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql;

import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntPreparedStatementSetter;

public interface EntSql {
    public String getSql();

    public EntPreparedStatementSetter getPreStatementSetter();
}

