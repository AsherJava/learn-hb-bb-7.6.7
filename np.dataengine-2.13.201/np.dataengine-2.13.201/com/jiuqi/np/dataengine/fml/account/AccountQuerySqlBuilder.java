/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import java.sql.SQLException;

public class AccountQuerySqlBuilder
extends QuerySqlBuilder {
    @Override
    public void doInit(QueryContext context) throws ParseException {
    }

    @Override
    public String buildQuerySql(QueryContext context) throws InterpretException, SQLException, ParseException, Exception {
        return null;
    }

    @Override
    public DataSet<QueryField> runQuery(QueryContext qContext) throws Exception {
        return null;
    }
}

