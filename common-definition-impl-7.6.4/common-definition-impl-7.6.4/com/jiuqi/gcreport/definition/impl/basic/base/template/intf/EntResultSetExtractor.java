/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.template.wapper.EntResultSetWrapper;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface EntResultSetExtractor<T> {
    @Nullable
    public T extractData(EntResultSetWrapper var1) throws SQLException, DataAccessException;
}

